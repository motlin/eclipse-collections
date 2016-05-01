/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.set.sorted.mutable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.block.function.Function3;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.list.ListIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.set.sorted.PartitionMutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.set.sorted.SortedSetIterable;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.Counter;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.procedure.PartitionPredicate2Procedure;
import org.eclipse.collections.impl.block.procedure.PartitionProcedure;
import org.eclipse.collections.impl.block.procedure.checked.CheckedProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.partition.set.sorted.PartitionScapegoatTreeSet;
import org.eclipse.collections.impl.stack.mutable.ArrayStack;
import org.eclipse.collections.impl.utility.ListIterate;
import org.eclipse.collections.impl.utility.internal.IterableIterate;

/**
 * Implementation of Scapegoat tree based on http://people.csail.mit.edu/rivest/pubs/GR93.pdf.
 *
 * Includes enhancements to pack nodes into arrays for memory efficiency.
 */
// TODO look for XXX in addition to TODO
public class ScapegoatTreeSet<T extends Comparable<? super T>> extends AbstractMutableSortedSet<T> implements Externalizable
{
    /* TODO
     */ // Put cursor before line comment and hit enter. Report bug to JetBrains.

    /**
     * A tree with depth <= {@link #INIT_MAX_DEPTH} will not be compacted
     */
    private static final int INIT_MAX_DEPTH = 4;

    /**
     * A rebalanced subtree with size <= {@link #MIN_SIZE_THRESHOLD} will not be compacted into an array
     */
    private static final int MIN_SIZE_THRESHOLD = 3;

    private final float balanceRatio;
    private final float fullRebalanceRatioAdd;
    private final float fullRebalanceRatioRemove;
    private final float balanceDenominator;

    private Node<T> root;
    private int nodeCount;
    private int maxNodeCount;

    private int maxDepth = INIT_MAX_DEPTH;
    private int minSize;
    private int fullRebalanceSizeAdd;

    public ScapegoatTreeSet()
    {
        this(0.66f, 0.66f, 0.5f);
    }

    public ScapegoatTreeSet(float balanceRatio, float fullRebalanceRatioAdd, float fullRebalanceRatioRemove)
    {
        if (balanceRatio <= 0.5 || balanceRatio >= 1.0)
        {
            throw new IllegalArgumentException("balanceRatio must be between 0.5 and 1.0 but was " + balanceRatio);
        }
        if (fullRebalanceRatioRemove < 0 || fullRebalanceRatioRemove >= 1.0)
        {
            throw new IllegalArgumentException("fullRebalanceRatioRemove must be between 0.0 and 1.0 but was " + fullRebalanceRatioRemove);
        }

        this.balanceRatio = balanceRatio;
        this.fullRebalanceRatioAdd = fullRebalanceRatioAdd;
        this.fullRebalanceRatioRemove = fullRebalanceRatioRemove;

        // TODO remove all logarithms
        this.balanceDenominator = (float) StrictMath.log(1 / balanceRatio);

        this.minSize = ScapegoatTreeSet.minSizeForDepth(this.maxDepth, balanceRatio);
    }

    private static double power(double initialBase, int initialExponent)
    {
        double base = initialBase;
        int exponent = initialExponent;
        if (exponent < 0)
        {
            throw new IllegalArgumentException(String.valueOf(exponent));
        }
        double result = 1;
        while (exponent != 0)
        {
            if ((exponent & 1) == 1)
            {
                result *= base;
            }
            exponent >>= 1;
            base *= base;
        }
        return result;
    }

    public static int minSizeForDepth(int maxDepth, double balanceRatio)
    {
        double inverseBalanceRatio = 1 / balanceRatio;
        return (int) Math.ceil(ScapegoatTreeSet.power(inverseBalanceRatio, maxDepth - 1));
    }

    private static <T extends Comparable<? super T>> Node<T> getSibling(Node<T> parentNode, Node<T> siblingNode)
    {
        boolean hasLeftChild = parentNode.hasLeftChild();
        if (hasLeftChild && Comparators.nullSafeEquals(parentNode.getLeft(), siblingNode))
        {
            return parentNode.hasRightChild() ? parentNode.getRight() : null;
        }
        return hasLeftChild ? parentNode.getLeft() : null;
    }

    // Exposed for testing
    // Has a left bias. TODO: Consider alternating or randomizing.
    public static int calculateMidPoint(int start, int segmentSize)
    {
        int midPoint = start + segmentSize / 2;
        if ((segmentSize & 1) == 0 && (midPoint & 1) == 0)
        {
            return midPoint - 1;
        }
        return midPoint;
    }

    public static <T extends Comparable<T>> ScapegoatTreeSet<T> buildFromSortedList(
            ListIterable<T> list,
            float balanceRatio,
            float fullRebalanceRatioAdd,
            float fullRebalanceRatioRemove)
    {
        int size = list.size();
        CompactCompositeInnerNode<T> compactCompositeNodes = ScapegoatTreeSet.buildFromSortedList(list, size);
        ScapegoatTreeSet<T> result = new ScapegoatTreeSet<>(balanceRatio, fullRebalanceRatioAdd, fullRebalanceRatioRemove);
        result.nodeCount = size;
        result.root = compactCompositeNodes;
        return result;
    }

    private static <T extends Comparable<? super T>> CompactCompositeInnerNode<T> buildFromSortedList(ListIterable<T> list, int size)
    {
        BalancedTreeBuilder<T> balancedTreeBuilder = new BalancedTreeBuilder<>(size);
        for (T element : list)
        {
            balancedTreeBuilder.value(element);
        }

        return new CompactCompositeInnerNode<>(balancedTreeBuilder.array, 0);
    }

    private static int leftPosition(int position)
    {
        return position * 2 + 1;
    }

    private static int rightPosition(int position)
    {
        return position * 2 + 2;
    }

    private static int parentPosition(int position)
    {
        return (position - 1) / 2;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new ScapegoatTreeSetIterator();
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (!(object instanceof Set))
        {
            return false;
        }

        Set<?> other = (Set<?>) object;
        return this.size() == other.size() && this.containsAll(other);
    }

    @Override
    public int hashCode()
    {
        Counter hashCode = new Counter();
        this.each(each -> hashCode.add(each.hashCode()));
        return hashCode.getCount();
    }

    @Override
    public boolean add(T value)
    {
        if (value == null)
        {
            throw new NullPointerException();
        }

        if (this.root == null)
        {
            this.root = new SimpleBinaryNode<>(value);
            assert this.nodeCount == 0;
            this.nodeCount = 1;
            this.maxNodeCount = 1;
            return true;
        }

        Node<T> currentNode = this.root;
        int currentDepth = 1;
        while (true)
        {
            currentDepth++;
            int compareTo = value.compareTo(currentNode.getValue());
            if (compareTo < 0)
            {
                if (currentNode.hasLeftChild())
                {
                    currentNode = currentNode.getLeft();
                }
                else
                {
                    currentNode.setLeftValue(value);
                    this.nodeCount++;
                    this.maxNodeCount = Math.max(this.maxNodeCount, this.nodeCount);
                    this.checkRebalance(currentDepth, value);
                    return true;
                }
            }
            else if (compareTo > 0)
            {
                if (currentNode.hasRightChild())
                {
                    currentNode = currentNode.getRight();
                }
                else
                {
                    currentNode.setRightValue(value);
                    this.nodeCount++;
                    this.maxNodeCount = Math.max(this.maxNodeCount, this.nodeCount);
                    this.checkRebalance(currentDepth, value);
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    private void checkRebalance(int currentDepth, T value)
    {
        if (currentDepth < this.maxDepth)
        {
            return;
        }
        if (currentDepth == this.maxDepth && this.nodeCount < this.minSize)
        {
            assert this.goingToRebalanceUsingLog(currentDepth);
            this.rebalance(value);
        }
        else
        {
            if (this.nodeCount >= this.minSize)
            {
                this.maxDepth++;
                this.minSize = ScapegoatTreeSet.minSizeForDepth(this.maxDepth, this.balanceRatio);
            }
            if (this.nodeCount >= this.fullRebalanceSizeAdd)
            {
                this.rebalanceRoot();
                this.resetThresholds();
            }
        }
    }

    private boolean goingToRebalanceUsingLog(int currentDepth)
    {
        return currentDepth >= this.maxDepthUsingLog();
    }

    private double maxDepthUsingLog()
    {
        return StrictMath.log(this.nodeCount) / this.balanceDenominator + 1;
    }

    private void rebalance(T value)
    {
        assert this.root != null;

        // TODO: Replace stack with a custom zipper since compact nodes can support getParent() and binary nodes cannot
        MutableStack<Node<T>> stack = ArrayStack.newStack();
        Node<T> currentNode = this.root;
        stack.push(this.root);
        //noinspection ObjectEquality
        while (currentNode.getValue() != value)
        {
            int compareTo = value.compareTo(currentNode.getValue());
            if (compareTo < 0)
            {
                currentNode = currentNode.getLeft();
            }
            else if (compareTo > 0)
            {
                currentNode = currentNode.getRight();
            }
            else
            {
                throw new IllegalArgumentException("Comparison method violates its general contract!");
            }
            stack.push(currentNode);
        }
        Scapegoat<T> scapegoat = this.findScapegoat(stack);

        this.rebalanceNode(scapegoat);
    }

    private void rebalanceNode(Scapegoat<T> scapegoat)
    {
        int size = scapegoat.getSize();
        Node<T> origin = scapegoat.getOrigin();
        Node<T> parent = scapegoat.getParent();

        MutableList<T> values = FastList.newList(size);
        origin.toList(values);

        if (parent == null)
        {
            this.root = this.constructTree(values, size);
            this.resetThresholds();
        }
        else if (parent instanceof SimpleBinaryNode<?>)
        {
            Node<T> subTree = this.constructTree(values, size);
            if (parent.hasLeftChildEqualTo(origin))
            {
                ((SimpleBinaryNode<T>) parent).setLeftTree(subTree);
            }
            else
            {
                ((SimpleBinaryNode<T>) parent).setRightTree(subTree);
            }
        }
        else if (origin instanceof CompactCompositeInnerNode<?> && ((CompactCompositeInnerNode<T>) origin).isInLastRow())
        {
            Node<T> subTree = this.constructTree(values, size);
            ((CompactCompositeInnerNode<T>) origin).setSubTree(subTree);
        }
        else if (parent.hasLeftChildEqualTo(origin))
        {
            this.populateLeftWithoutRebalance(parent, values, 0, size);
        }
        else
        {
            this.populateRightWithoutRebalance(parent, values, 0, size);
        }
    }

    private Node<T> constructTree(ListIterable<T> values, int size)
    {
        // Power of 2 minus 1
        if (size < (2 << MIN_SIZE_THRESHOLD) - 1)
        {
            return this.constructTree(values, 0, size);
        }

        return ScapegoatTreeSet.buildFromSortedList(values, size);
    }

    private void populateLeftWithoutRebalance(Node<T> node, MutableList<T> values, int start, int end)
    {
        if (end == start)
        {
            node.eraseLeftChild();
            return;
        }

        int segmentSize = end - start;
        if (segmentSize == 1)
        {
            node.replaceLeftTree(values.get(start));
            return;
        }

        int midPoint = ScapegoatTreeSet.calculateMidPoint(start, segmentSize);
        T midPointValue = values.get(midPoint);

        if (node instanceof CompactCompositeInnerNode<?> && ((CompactCompositeInnerNode<T>) node).isLeftChildInLastRow())
        {
            Node<T> leftNodes = this.constructTree(values.subList(start, midPoint), midPoint - start);
            Node<T> rightNodes = this.constructTree(values.subList(midPoint + 1, end), end - midPoint - 1);
            ((CompactCompositeInnerNode<T>) node).setLeftSubTree(new SimpleBinaryNode<>(midPointValue, leftNodes, rightNodes));
        }
        else
        {
            Node<T> left = node.setLeftValue(midPointValue);
            this.populateLeftWithoutRebalance(left, values, start, midPoint);
            this.populateRightWithoutRebalance(left, values, midPoint + 1, end);
        }
    }

    private void populateRightWithoutRebalance(Node<T> node, MutableList<T> values, int start, int end)
    {
        if (end == start)
        {
            node.eraseRightChild();
            return;
        }

        int segmentSize = end - start;
        if (segmentSize == 1)
        {
            node.replaceRightTree(values.get(start));
            return;
        }

        int midPoint = ScapegoatTreeSet.calculateMidPoint(start, segmentSize);
        T midPointValue = values.get(midPoint);

        if (node instanceof CompactCompositeInnerNode<?> && ((CompactCompositeInnerNode<T>) node).isRightChildInLastRow())
        {
            Node<T> leftNodes = this.constructTree(values.subList(start, midPoint), midPoint - start);
            Node<T> rightNodes = this.constructTree(values.subList(midPoint + 1, end), end - midPoint - 1);
            ((CompactCompositeInnerNode<T>) node).setRightSubTree(new SimpleBinaryNode<>(midPointValue, leftNodes, rightNodes));
        }
        else
        {
            Node<T> right = node.setRightValue(midPointValue);
            this.populateLeftWithoutRebalance(right, values, start, midPoint);
            this.populateRightWithoutRebalance(right, values, midPoint + 1, end);
        }
    }

    private Node<T> constructTree(ListIterable<T> values, int start, int end)
    {
        if (end == start)
        {
            return null;
        }

        int segmentSize = end - start;
        if (segmentSize == 1)
        {
            return new SimpleBinaryNode<>(values.get(start));
        }

        int midPoint = ScapegoatTreeSet.calculateMidPoint(start, segmentSize);

        Node<T> left = this.constructTree(values, start, midPoint);
        Node<T> right = this.constructTree(values, midPoint + 1, end);
        return new SimpleBinaryNode<>(values.get(midPoint), left, right);
    }

    // TODO if the scapegoat size is > half the size of the compact group (specifically index 1 or 2), rebalance the whole group at index 0.
    private Scapegoat<T> findScapegoat(MutableStack<Node<T>> stack)
    {
        Node<T> child = stack.pop();
        child.isLeaf();
        Scapegoat<T> result = null;

        int scapegoatSize = 1;
        while (true)
        {
            if (stack.isEmpty())
            {
                assert result != null;
                return result;
            }
            Node<T> scapegoatCandidate = stack.pop();
            Node<T> childSibling = ScapegoatTreeSet.getSibling(scapegoatCandidate, child);

            int childSize = scapegoatSize;

            int siblingSize;

            //noinspection ObjectEquality
            if (scapegoatCandidate == this.root)
            {
                // If we're looking at the root, use arithmetic instead of size()
                siblingSize = this.nodeCount - childSize - 1;
                scapegoatSize = this.nodeCount;
            }
            else
            {
                siblingSize = childSibling == null ? 0 : childSibling.size();
                scapegoatSize += siblingSize;
                scapegoatSize++;
            }

            double weightBalanceThreshold = this.balanceRatio * scapegoatSize;

            if (childSize > weightBalanceThreshold || siblingSize > weightBalanceThreshold)
            {
                result = new Scapegoat<>(scapegoatCandidate, stack.isEmpty() ? null : stack.peek(), scapegoatSize);
            }
            else if (result != null)
            {
                return result;
            }
            child = scapegoatCandidate;
        }
    }

    @Override
    public boolean remove(Object value)
    {
        if (this.root == null)
        {
            return false;
        }

        Node<T> parent = null;
        Node<T> currentNode = this.root;

        while (true)
        {
            int compareTo = ((T) value).compareTo(currentNode.getValue());
            if (compareTo < 0)
            {
                if (!currentNode.hasLeftChild())
                {
                    return false;
                }
                parent = currentNode;
                currentNode = currentNode.getLeft();
            }
            else if (compareTo > 0)
            {
                if (!currentNode.hasRightChild())
                {
                    return false;
                }
                parent = currentNode;
                currentNode = currentNode.getRight();
            }
            else
            {
                this.remove(currentNode, parent);
                return true;
            }
        }
    }

    @Override
    public void clear()
    {
        this.root = null;
        this.nodeCount = 0;
        this.resetThresholds();
    }

    private void remove(Node<T> node, Node<T> parent)
    {
        if (node.hasLeftChild())
        {
            node.setValue(node.getLeft().removeGreatest(node));
        }
        else if (node.hasRightChild())
        {
            node.setValue(node.getRight().removeLeast(node));
        }
        else if (parent == null)
        {
            this.root = null;
        }
        // TODO optimize with special method removeLeafChild(node)
        else if (parent.hasLeftChildEqualTo(node))
        {
            parent.removeLeftLeaf();
        }
        else
        {
            assert parent.hasRightChildEqualTo(node);
            parent.removeRightLeaf();
        }

        this.nodeCount--;
        if (this.root != null && this.nodeCount < this.maxNodeCount * this.fullRebalanceRatioRemove)
        {
            this.rebalanceRoot();
            this.resetThresholds();
        }
    }

    private void rebalanceRoot()
    {
        if (this.nodeCount == 1)
        {
            this.root = new SimpleBinaryNode<>(this.root.getValue());
            return;
        }
        BalancedTreeBuilder<T> balancedTreeBuilder = new BalancedTreeBuilder<>(this.nodeCount);
        this.root.forEach(balancedTreeBuilder);
        this.root = new CompactCompositeInnerNode<>(balancedTreeBuilder.array, 0);
        this.resetThresholds();
    }

    private void resetThresholds()
    {
        this.maxNodeCount = this.nodeCount;
        this.maxDepth = (int) this.maxDepthUsingLog();
        this.minSize = ScapegoatTreeSet.minSizeForDepth(this.maxDepth, this.balanceRatio);
        this.fullRebalanceSizeAdd = (int) Math.ceil(this.nodeCount / this.fullRebalanceRatioAdd);
    }

    @Override
    public boolean contains(Object value)
    {
        if (this.root == null)
        {
            return false;
        }
        return this.root.contains((T) value);
    }

    @Override
    public int size()
    {
        return this.nodeCount;
    }

    @Override
    public boolean isEmpty()
    {
        return this.root == null;
    }

    @Override
    public boolean removeIf(Predicate<? super T> predicate)
    {
        if (this.root == null)
        {
            return false;
        }
        return this.root.removeIf(this, null, predicate);
    }

    @Override
    public <P> boolean removeIfWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.removeIf(Predicates.bind(predicate, parameter));
    }

    @Override
    public T getFirst()
    {
        if (this.isEmpty())
        {
            throw new NoSuchElementException();
        }
        Node<T> node = this.root;
        while (node.hasLeftChild())
        {
            node = node.getLeft();
        }
        return node.getValue();
    }

    @Override
    public T getLast()
    {
        if (this.isEmpty())
        {
            throw new NoSuchElementException();
        }
        Node<T> node = this.root;
        while (node.hasRightChild())
        {
            node = node.getRight();
        }
        return node.getValue();
    }

    public String prettyPrint()
    {
        StringBuilder stringBuilder = new StringBuilder();
        this.prettyPrint(stringBuilder);
        return stringBuilder.toString();
    }

    public void prettyPrint(Appendable appendable)
    {
        if (this.root == null)
        {
            return;
        }

        try
        {
            this.root.printTree(appendable, false, "");
            appendable.append("\n");
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    // Exposed for testing
    public int height()
    {
        if (this.root == null)
        {
            return 0;
        }
        return this.root.height();
    }

    @Override
    public MutableList<T> toList()
    {
        MutableList<T> result = FastList.newList();
        if (this.root == null)
        {
            return result;
        }

        this.root.toList(result);
        return result;
    }

    @Override
    public <IV, P> IV injectIntoWith(
            IV injectValue,
            Function3<? super IV, ? super T, ? super P, ? extends IV> function,
            P parameter)
    {
        return this.injectInto(injectValue, (argument1, argument2) -> function.value(argument1, argument2, parameter));
    }

    @Override
    public void forEach(int fromIndex, int toIndex, Procedure<? super T> procedure)
    {
        ListIterate.rangeCheck(fromIndex, toIndex, this.size());

        if (fromIndex > toIndex)
        {
            throw new IllegalArgumentException("fromIndex must not be greater than toIndex");
        }
        Counter counter = new Counter();
        this.each(each -> {
            if (counter.getCount() >= fromIndex && counter.getCount() <= toIndex)
            {
                procedure.value(each);
            }
            counter.increment();
        });
    }

    public void compact()
    {
        if (this.root == null)
        {
            return;
        }
        this.rebalanceRoot();
        this.resetThresholds();
    }

    private abstract static class Node<T extends Comparable<? super T>>
    {
        public abstract T getValue();

        public abstract void setValue(T value);

        public abstract Node<T> getLeft();

        public abstract Node<T> setLeftValue(T value);

        public abstract Node<T> getRight();

        public abstract Node<T> setRightValue(T value);

        public abstract void removeLeftLeaf();

        public abstract void removeRightLeaf();

        public abstract int size();

        public abstract boolean contains(T value);

        public abstract int height();

        public final void toList(MutableList<T> result)
        {
            if (this.hasLeftChild())
            {
                this.getLeft().toList(result);
            }

            result.add(this.getValue());

            if (this.hasRightChild())
            {
                this.getRight().toList(result);
            }
        }

        public abstract void forEach(Procedure<? super T> procedure);

        protected boolean removeIf(ScapegoatTreeSet<T> set, Node<T> parent, Predicate<? super T> predicate)
        {
            boolean result = false;

            if (this.hasLeftChild())
            {
                boolean leftResult = this.getLeft().removeIf(set, this, predicate);
                if (leftResult)
                {
                    result = true;
                }
            }
            if (this.hasRightChild())
            {
                boolean rightResult = this.getRight().removeIf(set, this, predicate);
                if (rightResult)
                {
                    result = true;
                }
            }
            if (predicate.accept(this.getValue()))
            {
                set.remove(this, parent);
                result = true;
            }

            return result;
        }

        private void printNodeValue(Appendable appendable) throws IOException
        {
            if (this.getValue() == null)
            {
                appendable.append("<null>");
            }
            else
            {
                appendable.append(this.toString());
            }
            appendable.append("\n");
        }

        private void printTree(Appendable appendable, boolean isRight, String indent) throws IOException
        {
            if (this.hasRightChild())
            {
                this.getRight().printTree(appendable, true, indent + (isRight ? "        " : " |      "));
            }
            appendable.append(indent);
            if (isRight)
            {
                appendable.append(" /");
            }
            else
            {
                appendable.append(" \\");
            }
            appendable.append("----- ");
            this.printNodeValue(appendable);
            if (this.hasLeftChild())
            {
                this.getLeft().printTree(appendable, false, indent + (isRight ? " |      " : "        "));
            }
        }

        public abstract T removeGreatest(Node<T> parent);

        public abstract T removeLeast(Node<T> parent);

        @Override
        public String toString()
        {
            return String.valueOf(this.getValue());
        }

        public abstract boolean hasLeftChildEqualTo(Node<T> node);

        public abstract boolean hasRightChildEqualTo(Node<T> node);

        public abstract boolean isLeaf();

        public abstract void replaceLeftTree(T value);

        public abstract void replaceRightTree(T value);

        public abstract boolean hasLeftChild();

        public abstract boolean hasRightChild();

        public abstract void eraseLeftChild();

        public abstract void eraseRightChild();

        public abstract int numberOfSimpleNodes();

        public final boolean anySatisfy(Predicate<? super T> predicate)
        {
            if (this.hasLeftChild() && this.getLeft().anySatisfy(predicate))
            {
                return true;
            }
            if (predicate.accept(this.getValue()))
            {
                return true;
            }
            return this.hasRightChild() && this.getRight().anySatisfy(predicate);
        }

        public boolean allSatisfy(Predicate<? super T> predicate)
        {
            if (this.hasLeftChild() && !this.getLeft().allSatisfy(predicate))
            {
                return false;
            }
            if (!predicate.accept(this.getValue()))
            {
                return false;
            }
            return !(this.hasRightChild() && !this.getRight().allSatisfy(predicate));
        }

        public boolean noneSatisfy(Predicate<? super T> predicate)
        {
            if (this.hasLeftChild() && !this.getLeft().noneSatisfy(predicate))
            {
                return false;
            }
            if (predicate.accept(this.getValue()))
            {
                return false;
            }
            return !(this.hasRightChild() && !this.getRight().noneSatisfy(predicate));
        }

        public Optional<T> detect(Predicate<? super T> predicate)
        {
            if (this.hasLeftChild())
            {
                Optional<T> detectLeft = this.getLeft().detect(predicate);
                if (detectLeft.isPresent())
                {
                    return detectLeft;
                }
            }
            if (predicate.accept(this.getValue()))
            {
                return Optional.of(this.getValue());
            }
            if (this.hasRightChild())
            {
                Optional<T> detectRight = this.getRight().detect(predicate);
                if (detectRight.isPresent())
                {
                    return detectRight;
                }
            }
            return Optional.empty();
        }
    }

    private static final class Scapegoat<T extends Comparable<? super T>>
    {
        private final Node<T> origin;
        private final Node<T> parent;
        private final int size;

        private Scapegoat(Node<T> origin, Node<T> parent, int size)
        {
            this.origin = origin;
            this.parent = parent;
            this.size = size;
        }

        public Node<T> getOrigin()
        {
            return this.origin;
        }

        public Node<T> getParent()
        {
            return this.parent;
        }

        public int getSize()
        {
            return this.size;
        }
    }

    private static final class SimpleBinaryNode<T extends Comparable<? super T>> extends Node<T>
    {
        private T value;
        // TODO: Potential memory optimizations.
        // Make left and right into Object and store T or Node.
        // Don't store empty slots, possibly creating LeafNode, NodeWithLeftChild, NodeWithRightChild.
        private Node<T> left;
        private Node<T> right;

        private SimpleBinaryNode(T value)
        {
            this(value, null, null);
        }

        private SimpleBinaryNode(T value, Node<T> left, Node<T> right)
        {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public T getValue()
        {
            return this.value;
        }

        @Override
        public void setValue(T value)
        {
            this.value = value;
        }

        @Override
        public Node<T> getLeft()
        {
            return this.left;
        }

        public void setLeftTree(Node<T> node)
        {
            assert node != null;
            this.left = node;
        }

        @Override
        public Node<T> getRight()
        {
            return this.right;
        }

        public void setRightTree(Node<T> node)
        {
            assert node != null;
            this.right = node;
        }

        @Override
        public void forEach(Procedure<? super T> procedure)
        {
            if (this.left != null)
            {
                this.left.forEach(procedure);
            }

            procedure.value(this.value);

            if (this.right != null)
            {
                this.right.forEach(procedure);
            }
        }

        @Override
        public int height()
        {
            int leftHeight = this.left == null ? 0 : this.left.height();
            int rightHeight = this.right == null ? 0 : this.right.height();
            return 1 + Math.max(leftHeight, rightHeight);
        }

        @Override
        public T removeGreatest(Node<T> parent)
        {
            Node<T> rightChild = this.right;
            if (this.hasRightChild())
            {
                return rightChild.removeGreatest(this);
            }

            if (this.hasLeftChild())
            {
                T result = this.value;
                this.value = this.left.removeGreatest(this);
                return result;
            }

            if (parent.hasRightChildEqualTo(this))
            {
                parent.removeRightLeaf();
            }
            else
            {
                assert parent.hasLeftChildEqualTo(this);
                parent.removeLeftLeaf();
            }
            return this.value;
        }

        @Override
        public T removeLeast(Node<T> parent)
        {
            if (this.hasLeftChild())
            {
                return this.left.removeLeast(this);
            }

            if (this.hasRightChild())
            {
                T result = this.value;
                this.value = this.right.removeLeast(this);
                return result;
            }

            if (parent.hasLeftChildEqualTo(this))
            {
                parent.removeLeftLeaf();
            }
            else
            {
                assert parent.hasRightChildEqualTo(this);
                parent.removeRightLeaf();
            }
            return this.value;
        }

        @Override
        public Node<T> setLeftValue(T value)
        {
            this.left = new SimpleBinaryNode<>(value);
            return this.left;
        }

        @Override
        public Node<T> setRightValue(T value)
        {
            this.right = new SimpleBinaryNode<>(value);
            return this.right;
        }

        @Override
        public boolean hasLeftChildEqualTo(Node<T> node)
        {
            //noinspection ObjectEquality
            return this.left == node;
        }

        @Override
        public boolean hasRightChildEqualTo(Node<T> node)
        {
            //noinspection ObjectEquality
            return this.right == node;
        }

        @Override
        public void eraseLeftChild()
        {
            // intentionally blank
        }

        @Override
        public void eraseRightChild()
        {
            // intentionally blank
        }

        @Override
        public void replaceLeftTree(T value)
        {
            this.left = new SimpleBinaryNode<>(value);
        }

        @Override
        public void replaceRightTree(T value)
        {
            this.right = new SimpleBinaryNode<>(value);
        }

        @Override
        public boolean hasLeftChild()
        {
            return this.left != null;
        }

        @Override
        public boolean hasRightChild()
        {
            return this.right != null;
        }

        @Override
        public boolean isLeaf()
        {
            return this.left == null && this.right == null;
        }

        @Override
        public void removeLeftLeaf()
        {
            assert this.left != null;
            assert this.left.isLeaf();
            this.left = null;
        }

        @Override
        public void removeRightLeaf()
        {
            assert this.right != null;
            assert this.right.isLeaf();
            this.right = null;
        }

        @Override
        public int size()
        {
            int leftSize = this.left == null ? 0 : this.left.size();
            int rightSize = this.right == null ? 0 : this.right.size();
            return leftSize + rightSize + 1;
        }

        @Override
        public boolean contains(T value)
        {
            int compareTo = value.compareTo(this.value);
            if (compareTo < 0)
            {
                return this.left != null && this.left.contains(value);
            }
            if (compareTo > 0)
            {
                return this.right != null && this.right.contains(value);
            }
            return true;
        }

        @Override
        public int numberOfSimpleNodes()
        {
            int leftSize = this.left == null ? 0 : this.left.numberOfSimpleNodes();
            int rightSize = this.right == null ? 0 : this.right.numberOfSimpleNodes();
            return leftSize + rightSize + 1;
        }
    }

    private static final class CompactCompositeInnerNode<T extends Comparable<? super T>> extends Node<T>
    {
        private final Object[] table;
        private final int position;

        private CompactCompositeInnerNode(Object[] table, int position)
        {
            assert table.length > 1;
            this.table = table;
            this.position = position;
        }

        @Override
        public T getValue()
        {
            Object object = this.getTableEntry();
            assert object != null;
            if (object instanceof Node<?>)
            {
                assert this.isInLastRow();
                return ((Node<T>) object).getValue();
            }
            return (T) object;
        }

        @Override
        public void setValue(T value)
        {
            Object object = this.getTableEntry();
            if (object instanceof Node<?>)
            {
                assert this.isInLastRow();
                ((Node<T>) object).setValue(value);
            }
            else
            {
                this.table[this.position] = value;
            }
        }

        @Override
        public Node<T> getLeft()
        {
            Object object = this.getTableEntry();
            if (object instanceof Node<?>)
            {
                assert this.isInLastRow();
                return ((Node<T>) object).getLeft();
            }
            return this.getChildNode(this.leftPosition());
        }

        @Override
        public Node<T> getRight()
        {
            Object object = this.getTableEntry();
            if (object instanceof Node<?>)
            {
                assert this.isInLastRow();
                return ((Node<T>) object).getRight();
            }
            return this.getChildNode(this.rightPosition());
        }

        @Override
        public Node<T> setLeftValue(T value)
        {
            if (this.isInLastRow())
            {
                Object object = this.getTableEntry();
                assert object != null;
                if (object instanceof Node<?>)
                {
                    return ((Node<T>) object).setLeftValue(value);
                }

                SimpleBinaryNode<T> newNode = new SimpleBinaryNode<>((T) object);
                this.table[this.position] = newNode;
                return newNode.setLeftValue(value);
            }

            int leftPosition = this.leftPosition();
            this.table[leftPosition] = value;
            return new CompactCompositeInnerNode<>(this.table, leftPosition);
        }

        @Override
        public Node<T> setRightValue(T value)
        {
            if (this.isInLastRow())
            {
                Object object = this.getTableEntry();
                assert object != null;
                if (object instanceof Node<?>)
                {
                    return ((Node<T>) object).setRightValue(value);
                }

                SimpleBinaryNode<T> newNode = new SimpleBinaryNode<>((T) object);
                this.table[this.position] = newNode;
                return newNode.setRightValue(value);
            }

            int rightPosition = this.rightPosition();
            this.table[rightPosition] = value;
            return new CompactCompositeInnerNode<>(this.table, rightPosition);
        }

        public void setSubTree(Node<T> subTree)
        {
            assert this.isInLastRow();
            this.table[this.position] = subTree;
        }

        public void setLeftSubTree(Node<T> subTree)
        {
            assert !this.isInLastRow();
            assert this.isLeftChildInLastRow();
            int leftPosition = this.leftPosition();
            this.table[leftPosition] = subTree;
        }

        public void setRightSubTree(Node<T> subTree)
        {
            assert !this.isInLastRow();
            assert this.isRightChildInLastRow();
            int rightPosition = this.rightPosition();
            this.table[rightPosition] = subTree;
        }

        @Override
        public boolean isLeaf()
        {
            int rightPosition = this.rightPosition();
            int leftPosition = rightPosition - 1;

            if (rightPosition < this.table.length)
            {
                return this.table[rightPosition] == null && this.table[leftPosition] == null;
            }

            Object tableEntry = this.getTableEntry();
            if (tableEntry instanceof Node<?>)
            {
                assert this.isInLastRow();
                assert !((Node<T>) tableEntry).isLeaf();
                return false;
            }
            return true;
        }

        @Override
        public boolean hasLeftChild()
        {
            int leftPosition = this.leftPosition();
            if (leftPosition < this.table.length)
            {
                return this.table[leftPosition] != null;
            }

            Object tableEntry = this.getTableEntry();
            if (tableEntry instanceof Node<?>)
            {
                assert this.isInLastRow();
                return ((Node<T>) tableEntry).hasLeftChild();
            }
            return false;
        }

        @Override
        public boolean hasRightChild()
        {
            int rightPosition = this.rightPosition();
            if (rightPosition < this.table.length)
            {
                return this.table[rightPosition] != null;
            }

            Object tableEntry = this.getTableEntry();
            if (tableEntry instanceof Node<?>)
            {
                assert this.isInLastRow();
                return ((Node<T>) tableEntry).hasRightChild();
            }
            return false;
        }

        private Node<T> getChildNode(int childPosition)
        {
            assert childPosition < this.table.length;
            assert this.table[childPosition] != null;

            // TODO potentially put this back in as an optimization
//                if (object instanceof SimpleBinaryNode<?>)
//                {
//                    return (SimpleBinaryNode<T>) object;
//                }

            return new CompactCompositeInnerNode<>(this.table, childPosition);
        }

        @Override
        public void removeLeftLeaf()
        {
            assert this.getLeft().isLeaf();
            if (this.isInLastRow())
            {
                Node<T> node = (Node<T>) this.getTableEntry();
                node.removeLeftLeaf();
                assert !node.hasLeftChild();
                if (!node.hasRightChild())
                {
                    this.table[this.position] = node.getValue();
                }
                return;
            }

            assert this.getLeft() != null;
            assert this.getLeft().isLeaf();
            this.table[this.leftPosition()] = null;
        }

        @Override
        public void removeRightLeaf()
        {
            assert this.getRight().isLeaf();
            if (this.isInLastRow())
            {
                Node<T> node = (Node<T>) this.getTableEntry();
                node.removeRightLeaf();
                assert !node.hasRightChild();
                if (!node.hasLeftChild())
                {
                    this.table[this.position] = node.getValue();
                }
                return;
            }

            assert this.getRight() != null;
            assert this.getRight().isLeaf();
            this.table[this.rightPosition()] = null;
        }

        @Override
        public int size()
        {
            return this.sizePosition(this.position);
        }

        private int sizePosition(int position)
        {
            if (position >= this.table.length)
            {
                return 0;
            }
            Object tableEntry = this.table[position];
            if (tableEntry == null)
            {
                return 0;
            }
            if (tableEntry instanceof Node<?>)
            {
                return ((Node<T>) tableEntry).size();
            }
            int leftSize = this.sizePosition(ScapegoatTreeSet.leftPosition(position));
            int rightSize = this.sizePosition(ScapegoatTreeSet.rightPosition(position));
            return 1 + leftSize + rightSize;
        }

        @Override
        public boolean contains(T value)
        {
            assert this.position == 0;

            return this.contains(value, 0);
        }

        private boolean contains(T value, int position)
        {
            Object tableEntry = this.table[position];
            if (tableEntry == null)
            {
                return false;
            }
            if (tableEntry instanceof Node<?>)
            {
                return ((Node<T>) tableEntry).contains(value);
            }

            int compareTo = value.compareTo((T) tableEntry);

            if (compareTo < 0)
            {
                int leftPosition = ScapegoatTreeSet.leftPosition(position);
                return leftPosition < this.table.length && this.contains(value, leftPosition);
            }
            if (compareTo > 0)
            {
                int rightPosition = ScapegoatTreeSet.rightPosition(position);
                return rightPosition < this.table.length && this.contains(value, rightPosition);
            }
            return true;
        }

        @Override
        public void forEach(Procedure<? super T> procedure)
        {
            assert this.position == 0;

            this.forEach(procedure, this.position);
        }

        private void forEach(Procedure<? super T> procedure, int position)
        {
            int leftPosition = ScapegoatTreeSet.leftPosition(position);
            if (leftPosition < this.table.length)
            {
                Object leftChild = this.table[leftPosition];
                if (leftChild instanceof Node<?>)
                {
                    ((Node<T>) leftChild).forEach(procedure);
                }
                else if (leftChild != null)
                {
                    this.forEach(procedure, leftPosition);
                }
            }

            Object tableEntry = this.table[position];
            assert !(tableEntry instanceof Node<?>);
            assert tableEntry != null;

            procedure.value((T) tableEntry);

            int rightPosition = ScapegoatTreeSet.rightPosition(position);
            if (rightPosition < this.table.length)
            {
                Object rightChild = this.table[rightPosition];
                if (rightChild instanceof Node<?>)
                {
                    ((Node<T>) rightChild).forEach(procedure);
                }
                else if (rightChild != null)
                {
                    this.forEach(procedure, rightPosition);
                }
            }
        }

        @Override
        public int numberOfSimpleNodes()
        {
            int leftSize = this.hasLeftChild() ? this.getLeft().numberOfSimpleNodes() : 0;
            int rightSize = this.hasRightChild() ? this.getRight().numberOfSimpleNodes() : 0;
            return leftSize + rightSize;
        }

        @Override
        public int height()
        {
            // TODO optimize by looking at the end of the array first and by not calling getLeft or getRight
            int leftHeight = this.hasLeftChild() ? this.getLeft().height() : 0;
            int rightHeight = this.hasRightChild() ? this.getRight().height() : 0;
            return 1 + Math.max(leftHeight, rightHeight);
        }

        @Override
        public T removeGreatest(Node<T> parent)
        {
            // TODO optimize
            if (this.hasRightChild())
            {
                return this.getRight().removeGreatest(this);
            }

            return this.removeExtremeValue(parent);
        }

        @Override
        public T removeLeast(Node<T> parent)
        {
            // TODO optimize
            if (this.hasLeftChild())
            {
                return this.getLeft().removeLeast(this);
            }

            return this.removeExtremeValue(parent);
        }

        private T removeExtremeValue(Node<T> parent)
        {
            T result = this.getValue();
            this.removeForInsertWithoutUpdatingNodeCount(parent);
            return result;
        }

        private void removeForInsertWithoutUpdatingNodeCount(Node<T> parent)
        {
            boolean hasLeftChild = this.hasLeftChild();
            boolean hasRightChild = this.hasRightChild();
            if (!hasLeftChild && !hasRightChild)
            {
                if (parent.hasLeftChildEqualTo(this))
                {
                    parent.removeLeftLeaf();
                }
                else
                {
                    assert parent.hasRightChildEqualTo(this);
                    parent.removeRightLeaf();
                }
            }
            else if (hasLeftChild && !hasRightChild)
            {
                this.removeForInsertWithoutUpdatingNodeCountLeft(this.getLeft());
            }
            else // if (hasRightChild && !hasLeftChild)
            {
                this.removeForInsertWithoutUpdatingNodeCountRight(this.getRight());
            }
            // else
            // {
                // TODO alternate greater/less to avoid imbalance
            // this.removeForInsertWithoutUpdatingNodeCountRight(this.getRight());
            // }
        }

        private void removeForInsertWithoutUpdatingNodeCountLeft(Node<T> leftChild)
        {
            assert this.hasLeftChildEqualTo(leftChild);
            Object tableEntry = this.getTableEntry();
            if (tableEntry instanceof Node<?>)
            {
                assert this.isInLastRow();
                Node<T> node = (Node<T>) tableEntry;
                T value = leftChild.removeGreatest(node);
                if (node.isLeaf())
                {
                    this.table[this.position] = value;
                }
                else
                {
                    node.setValue(value);
                }
            }
            else
            {
                this.table[this.position] = leftChild.removeGreatest(this);
            }
        }

        private void removeForInsertWithoutUpdatingNodeCountRight(Node<T> rightChild)
        {
            assert this.hasRightChildEqualTo(rightChild);
            Object tableEntry = this.getTableEntry();
            if (tableEntry instanceof Node<?>)
            {
                assert this.isInLastRow();
                Node<T> node = (Node<T>) tableEntry;
                T value = rightChild.removeLeast(node);
                if (node.isLeaf())
                {
                    this.table[this.position] = value;
                }
                else
                {
                    node.setValue(value);
                }
            }
            else
            {
                this.table[this.position] = rightChild.removeLeast(this);
            }
        }

        private Object getTableEntry()
        {
            return this.table[this.position];
        }

        @Override
        public String toString()
        {
            return super.toString() + this.getSuffix();
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || this.getClass() != o.getClass())
            {
                return false;
            }

            CompactCompositeInnerNode<T> that = (CompactCompositeInnerNode<T>) o;

            return this.position == that.position
                    && this.table == that.table;
        }

        @Override
        public int hashCode()
        {
            return this.position;
        }

        public String getSuffix()
        {
            if (this.position == 0)
            {
                return "* (root)";
            }
            if (this.getTableEntry() instanceof SimpleBinaryNode<?>)
            {
                return "+";
            }
            if (this.getTableEntry() instanceof CompactCompositeInnerNode<?>)
            {
                if (((CompactCompositeInnerNode<T>) this.getTableEntry()).position == 0)
                {
                    return "* (root)";
                }
            }
            return "*";
        }

        private int leftPosition()
        {
            return ScapegoatTreeSet.leftPosition(this.position);
        }

        private int rightPosition()
        {
            return ScapegoatTreeSet.rightPosition(this.position);
        }

        private boolean isInLastRow()
        {
            return this.rightPosition() >= this.table.length;
        }

        @Override
        public boolean hasLeftChildEqualTo(Node<T> leftChild)
        {
            Object tableEntry = this.getTableEntry();
            if (tableEntry instanceof SimpleBinaryNode<?>)
            {
                return ((SimpleBinaryNode<T>) tableEntry).hasLeftChildEqualTo(leftChild);
            }
            if (tableEntry instanceof CompactCompositeInnerNode<?>)
            {
                return ((CompactCompositeInnerNode<T>) tableEntry).hasLeftChildEqualTo(leftChild);
            }

            if (leftChild instanceof CompactCompositeInnerNode<?>)
            {
                return this.leftPosition() == ((CompactCompositeInnerNode<T>) leftChild).position;
            }

            assert leftChild instanceof SimpleBinaryNode;
            return this.getLeft() == leftChild;
        }

        @Override
        public boolean hasRightChildEqualTo(Node<T> rightChild)
        {
            Object tableEntry = this.getTableEntry();
            if (tableEntry instanceof SimpleBinaryNode<?>)
            {
                return ((SimpleBinaryNode<T>) tableEntry).hasRightChildEqualTo(rightChild);
            }
            if (tableEntry instanceof CompactCompositeInnerNode<?>)
            {
                return ((CompactCompositeInnerNode<T>) tableEntry).hasRightChildEqualTo(rightChild);
            }

            if (rightChild instanceof CompactCompositeInnerNode<?>)
            {
                return this.rightPosition() == ((CompactCompositeInnerNode<T>) rightChild).position;
            }
            assert rightChild instanceof SimpleBinaryNode;
            return this.getRight() == rightChild;
        }

        @Override
        public void eraseLeftChild()
        {
            this.erase(this.leftPosition());
        }

        @Override
        public void eraseRightChild()
        {
            this.erase(this.rightPosition());
        }

        private void eraseBelow(int position)
        {
            // TODO only use leftPosition and erase left/right pairs together
            this.erase(ScapegoatTreeSet.leftPosition(position));
            this.erase(ScapegoatTreeSet.rightPosition(position));
        }

        private void erase(int position)
        {
            if (position >= this.table.length)
            {
                return;
            }

            this.table[position] = null;
            this.erase(ScapegoatTreeSet.leftPosition(position));
            this.erase(ScapegoatTreeSet.rightPosition(position));
        }

        @Override
        public void replaceLeftTree(T value)
        {
            if (this.isInLastRow())
            {
                Object object = this.getTableEntry();
                assert object != null;
                if (object instanceof Node<?>)
                {
                    ((Node<T>) object).replaceLeftTree(value);
                    return;
                }

                SimpleBinaryNode<T> newNode = new SimpleBinaryNode<>((T) object, new SimpleBinaryNode<>(value), null);
                this.table[this.position] = newNode;
                return;
            }

            int leftPosition = this.leftPosition();
            this.table[leftPosition] = value;
            this.eraseBelow(leftPosition);
        }

        @Override
        public void replaceRightTree(T value)
        {
            if (this.isInLastRow())
            {
                Object object = this.getTableEntry();
                assert object != null;
                if (object instanceof Node<?>)
                {
                    ((Node<T>) object).replaceRightTree(value);
                    return;
                }

                SimpleBinaryNode<T> newNode = new SimpleBinaryNode<>((T) object, null, new SimpleBinaryNode<>(value));
                this.table[this.position] = newNode;
                return;
            }

            int rightPosition = this.rightPosition();
            this.table[rightPosition] = value;
            this.eraseBelow(rightPosition);
        }

        public boolean isLeftChildInLastRow()
        {
            return !this.isInLastRow() && this.leftPosition() * 2 + 2 >= this.table.length;
        }

        public boolean isRightChildInLastRow()
        {
            return !this.isInLastRow() && this.rightPosition() * 2 + 2 >= this.table.length;
        }
    }

    private static final class BalancedTreeBuilder<T> implements Procedure<T>
    {
        private final int size;
        private final Object[] array;
        private int remaining;

        private int currentPosition;
        private boolean traversingDown;

        private BalancedTreeBuilder(int size)
        {
            this.remaining = size;
            this.size = size;
            this.array = new Object[size + ((size & 1) == 0 ? 1 : 0)];

            while (ScapegoatTreeSet.leftPosition(this.currentPosition) < size)
            {
                this.currentPosition = ScapegoatTreeSet.leftPosition(this.currentPosition);
            }
        }

        @Override
        public void value(T element)
        {
            assert this.currentPosition < this.size;
            assert this.remaining > 0;
            assert this.currentPosition < this.array.length;

            this.array[this.currentPosition] = element;
            this.remaining--;
            if (this.remaining == 0)
            {
                return;
            }

            if (this.traversingDown)
            {
                int rightPosition = ScapegoatTreeSet.rightPosition(this.currentPosition);
                if (rightPosition < this.size)
                {
                    this.currentPosition = rightPosition;
                    while (ScapegoatTreeSet.leftPosition(this.currentPosition) < this.size)
                    {
                        this.currentPosition = ScapegoatTreeSet.leftPosition(this.currentPosition);
                    }
                }

                this.traversingDown = false;
            }
            else
            {
                do
                {
                    this.currentPosition = ScapegoatTreeSet.parentPosition(this.currentPosition);
                }
                while (this.array[this.currentPosition] != null);
                this.traversingDown = ScapegoatTreeSet.rightPosition(this.currentPosition) < this.size;
            }
        }
    }

    @Override
    public MutableSortedSet<T> with(T element)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".with() not implemented yet");
    }

    @Override
    public MutableSortedSet<T> without(T element)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".without() not implemented yet");
    }

    @Override
    public MutableSortedSet<T> withAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".withAll() not implemented yet");
    }

    @Override
    public MutableSortedSet<T> withoutAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".withoutAll() not implemented yet");
    }

    @Override
    public MutableSortedSet<T> newEmpty()
    {
        return new ScapegoatTreeSet<>(this.balanceRatio, this.fullRebalanceRatioAdd, this.fullRebalanceRatioRemove);
    }

    @Override
    public MutableSortedSet<T> clone()
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".clone() not implemented yet");
    }

    @Override
    public MutableSortedSet<T> tap(Procedure<? super T> procedure)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".tap() not implemented yet");
    }

    @Override
    public void each(Procedure<? super T> procedure)
    {
        if (this.root != null)
        {
            this.root.forEach(procedure);
        }
    }

    @Override
    public T detect(Predicate<? super T> predicate)
    {
        if (this.root == null)
        {
            return null;
        }

        return this.root.detect(predicate).orElse(null);
    }

    @Override
    public boolean anySatisfy(Predicate<? super T> predicate)
    {
        if (this.root == null)
        {
            return false;
        }

        return this.root.anySatisfy(predicate);
    }

    @Override
    public boolean allSatisfy(Predicate<? super T> predicate)
    {
        if (this.root == null)
        {
            return true;
        }

        return this.root.allSatisfy(predicate);
    }

    @Override
    public boolean noneSatisfy(Predicate<? super T> predicate)
    {
        if (this.root == null)
        {
            return true;
        }

        return this.root.noneSatisfy(predicate);
    }

    @Override
    public PartitionMutableSortedSet<T> partition(Predicate<? super T> predicate)
    {
        PartitionMutableSortedSet<T> partitionTreeSortedSet = new PartitionScapegoatTreeSet<>();
        this.forEach(new PartitionProcedure<>(predicate, partitionTreeSortedSet));
        return partitionTreeSortedSet;
    }

    @Override
    public <P> PartitionMutableSortedSet<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        PartitionMutableSortedSet<T> partitionTreeSortedSet = new PartitionScapegoatTreeSet<>();
        this.forEach(new PartitionPredicate2Procedure<>(predicate, parameter, partitionTreeSortedSet));
        return partitionTreeSortedSet;
    }

    @Override
    public int indexOf(Object object)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".indexOf() not implemented yet");
    }

    @Override
    public PartitionMutableSortedSet<T> partitionWhile(Predicate<? super T> predicate)
    {
        PartitionMutableSortedSet<T> result = new PartitionScapegoatTreeSet<>();
        return IterableIterate.partitionWhile(this, predicate, result);
    }

    @Override
    public void reverseForEach(Procedure<? super T> procedure)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".reverseForEach() not implemented yet");
    }

    @Override
    public LazyIterable<T> asReversed()
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".asReversed() not implemented yet");
    }

    @Override
    public MutableSortedSet<T> toReversed()
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".toReversed() not implemented yet");
    }

    @Override
    public int detectLastIndex(Predicate<? super T> predicate)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".detectLastIndex() not implemented yet");
    }

    @Override
    public Comparator<? super T> comparator()
    {
        return null;
    }

    @Override
    public MutableSortedSet<SortedSetIterable<T>> powerSet()
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".powerSet() not implemented yet");
    }

    @Override
    public MutableSortedSet<T> subSet(T fromElement, T toElement)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".subSet() not implemented yet");
    }

    @Override
    public MutableSortedSet<T> headSet(T toElement)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".headSet() not implemented yet");
    }

    @Override
    public MutableSortedSet<T> tailSet(T fromElement)
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".tailSet() not implemented yet");
    }

    @Override
    public T first()
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".first() not implemented yet");
    }

    @Override
    public T last()
    {
        throw new UnsupportedOperationException(this.getClass().getSimpleName() + ".last() not implemented yet");
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeInt(this.size());
        this.forEach(new CheckedProcedure<T>()
        {
            public void safeValue(T each) throws Exception
            {
                out.writeObject(each);
            }
        });
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        int size = in.readInt();
        for (int i = 0; i < size; i++)
        {
            this.add((T) in.readObject());
        }
    }

    private final class ScapegoatTreeSetIterator implements Iterator<T>
    {
        private final MutableStack<Node<T>> stack = new ArrayStack<>();
        private Node<T> next;

        private ScapegoatTreeSetIterator()
        {
            if (ScapegoatTreeSet.this.isEmpty())
            {
                return;
            }

            this.next = ScapegoatTreeSet.this.root;
            while (this.next.hasLeftChild())
            {
                this.stack.push(this.next);
                this.next = this.next.getLeft();
            }
        }

        @Override
        public boolean hasNext()
        {
            return this.next != null;
        }

        @Override
        public T next()
        {
            if (this.next == null)
            {
                throw new NoSuchElementException();
            }
            Node<T> result = this.next;

            if (this.next.hasRightChild())
            {
                this.stack.push(this.next);
                this.next = this.next.getRight();

                while (this.next.hasLeftChild())
                {
                    this.stack.push(this.next);
                    this.next = this.next.getLeft();
                }
            }
            else
            {
                while (this.stack.notEmpty())
                {
                    Node<T> oldNode = this.next;
                    this.next = this.stack.pop();
                    boolean cameFromLeft = Objects.equals(oldNode, this.next.getLeft());
                    if (cameFromLeft)
                    {
                        return result.getValue();
                    }
                }
                this.next = null;
            }

            return result.getValue();
        }
    }
}
