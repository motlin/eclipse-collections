/*
 * Copyright (c) 2022 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.set.sorted.mutable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ExecutorService;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.bimap.MutableBiMap;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.Function3;
import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
import org.eclipse.collections.api.block.function.primitive.ByteFunction;
import org.eclipse.collections.api.block.function.primitive.CharFunction;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.function.primitive.DoubleObjectToDoubleFunction;
import org.eclipse.collections.api.block.function.primitive.FloatFunction;
import org.eclipse.collections.api.block.function.primitive.FloatObjectToFloatFunction;
import org.eclipse.collections.api.block.function.primitive.IntFunction;
import org.eclipse.collections.api.block.function.primitive.IntObjectToIntFunction;
import org.eclipse.collections.api.block.function.primitive.LongFunction;
import org.eclipse.collections.api.block.function.primitive.LongObjectToLongFunction;
import org.eclipse.collections.api.block.function.primitive.ObjectIntToObjectFunction;
import org.eclipse.collections.api.block.function.primitive.ShortFunction;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.block.procedure.primitive.ObjectIntProcedure;
import org.eclipse.collections.api.factory.SortedSets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.api.list.primitive.MutableByteList;
import org.eclipse.collections.api.list.primitive.MutableCharList;
import org.eclipse.collections.api.list.primitive.MutableDoubleList;
import org.eclipse.collections.api.list.primitive.MutableFloatList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.api.list.primitive.MutableShortList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.multimap.sortedset.MutableSortedSetMultimap;
import org.eclipse.collections.api.ordered.OrderedIterable;
import org.eclipse.collections.api.partition.set.sorted.PartitionMutableSortedSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.SetIterable;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.set.sorted.ParallelSortedSetIterable;
import org.eclipse.collections.api.set.sorted.SortedSetIterable;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.UnmodifiableIteratorAdapter;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.api.factory.primitive.ObjectDoubleMaps;
import org.eclipse.collections.api.factory.primitive.ObjectLongMaps;
import org.eclipse.collections.api.map.primitive.MutableObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.MutableObjectLongMap;
import org.eclipse.collections.impl.collection.mutable.UnmodifiableCollectionSerializationProxy;

/**
 * An unmodifiable view of a SortedSet.
 *
 * @see MutableSortedSet#asUnmodifiable()
 */
public class UnmodifiableSortedSet<T>
        implements org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T>, Serializable
{
    private static final long serialVersionUID = 1L;
    private final MutableSortedSet<T> sortedSet;
    
    UnmodifiableSortedSet(MutableSortedSet<? extends T> sortedSet)
    {
        if (sortedSet == null)
        {
            throw new IllegalArgumentException("Cannot create an UnmodifiableSortedSet on a null sorted set");
        }
        this.sortedSet = (MutableSortedSet<T>) sortedSet;
    }

    /**
     * This method will take a MutableSortedSet and wrap it directly in a UnmodifiableSortedSet. It will
     * take any other non-Eclipse-Collections SortedSet and first adapt it will a SortedSetAdapter, and then return a
     * UnmodifiableSortedSet that wraps the adapter.
     */
    public static <E, S extends SortedSet<E>> UnmodifiableSortedSet<E> of(S set)
    {
        if (set == null)
        {
            throw new IllegalArgumentException("cannot create an UnmodifiableSortedSet for null");
        }
        return new UnmodifiableSortedSet<>(SortedSetAdapter.adapt(set));
    }

    protected MutableSortedSet<T> getSortedSet()
    {
        return this.sortedSet;
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> asUnmodifiable()
    {
        return this;
    }

    @Override
    public MutableSortedSet<T> asSynchronized()
    {
        return SynchronizedSortedSet.of(this);
    }

    @Override
    public ImmutableSortedSet<T> toImmutable()
    {
        return SortedSets.immutable.withSortedSet(this.getSortedSet());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this)
        {
            return true;
        }

        return this.getSortedSet().equals(obj);
    }

    @Override
    public int hashCode()
    {
        return this.getSortedSet().hashCode();
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> clone()
    {
        return this;
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> newEmpty()
    {
        throw new UnsupportedOperationException("Cannot create a new empty UnmodifiableMutableSortedSet");
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> tap(Procedure<? super T> procedure)
    {
        this.forEach(procedure);
        return this;
    }

    @Override
    public MutableSortedSet<T> select(Predicate<? super T> predicate)
    {
        return this.getSortedSet().select(predicate);
    }
    
    @Override
    public <R extends Collection<T>> R select(Predicate<? super T> predicate, R target)
    {
        return this.getSortedSet().select(predicate, target);
    }

    @Override
    public <P> MutableSortedSet<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getSortedSet().selectWith(predicate, parameter);
    }
    
    @Override
    public <P, R extends Collection<T>> R selectWith(Predicate2<? super T, ? super P> predicate, P parameter, R target)
    {
        return this.getSortedSet().selectWith(predicate, parameter, target);
    }

    @Override
    public MutableSortedSet<T> reject(Predicate<? super T> predicate)
    {
        return this.getSortedSet().reject(predicate);
    }
    
    @Override
    public <R extends Collection<T>> R reject(Predicate<? super T> predicate, R target)
    {
        return this.getSortedSet().reject(predicate, target);
    }

    @Override
    public <P> MutableSortedSet<T> rejectWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getSortedSet().rejectWith(predicate, parameter);
    }
    
    @Override
    public <P, R extends Collection<T>> R rejectWith(Predicate2<? super T, ? super P> predicate, P parameter, R target)
    {
        return this.getSortedSet().rejectWith(predicate, parameter, target);
    }

    @Override
    public PartitionMutableSortedSet<T> partition(Predicate<? super T> predicate)
    {
        return this.getSortedSet().partition(predicate);
    }

    @Override
    public <P> PartitionMutableSortedSet<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getSortedSet().partitionWith(predicate, parameter);
    }

    @Override
    public PartitionMutableSortedSet<T> partitionWhile(Predicate<? super T> predicate)
    {
        return this.getSortedSet().partitionWhile(predicate);
    }

    @Override
    public <S> MutableSortedSet<S> selectInstancesOf(Class<S> clazz)
    {
        return this.getSortedSet().selectInstancesOf(clazz);
    }

    @Override
    public <V> MutableList<V> collect(Function<? super T, ? extends V> function)
    {
        return this.getSortedSet().collect(function);
    }
    
    @Override
    public <V, R extends Collection<V>> R collect(Function<? super T, ? extends V> function, R target)
    {
        return this.getSortedSet().collect(function, target);
    }

    @Override
    public MutableBooleanList collectBoolean(BooleanFunction<? super T> booleanFunction)
    {
        return this.getSortedSet().collectBoolean(booleanFunction);
    }

    @Override
    public MutableByteList collectByte(ByteFunction<? super T> byteFunction)
    {
        return this.getSortedSet().collectByte(byteFunction);
    }

    @Override
    public MutableCharList collectChar(CharFunction<? super T> charFunction)
    {
        return this.getSortedSet().collectChar(charFunction);
    }

    @Override
    public MutableDoubleList collectDouble(DoubleFunction<? super T> doubleFunction)
    {
        return this.getSortedSet().collectDouble(doubleFunction);
    }

    @Override
    public MutableFloatList collectFloat(FloatFunction<? super T> floatFunction)
    {
        return this.getSortedSet().collectFloat(floatFunction);
    }

    @Override
    public MutableIntList collectInt(IntFunction<? super T> intFunction)
    {
        return this.getSortedSet().collectInt(intFunction);
    }

    @Override
    public MutableLongList collectLong(LongFunction<? super T> longFunction)
    {
        return this.getSortedSet().collectLong(longFunction);
    }

    @Override
    public MutableShortList collectShort(ShortFunction<? super T> shortFunction)
    {
        return this.getSortedSet().collectShort(shortFunction);
    }

    @Override
    public <V> MutableList<V> flatCollect(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.getSortedSet().flatCollect(function);
    }
    
    @Override
    public <V, R extends Collection<V>> R flatCollect(Function<? super T, ? extends Iterable<V>> function, R target)
    {
        return this.getSortedSet().flatCollect(function, target);
    }

    @Override
    public <P, A> MutableList<A> collectWith(Function2<? super T, ? super P, ? extends A> function, P parameter)
    {
        return this.getSortedSet().collectWith(function, parameter);
    }
    
    @Override
    public <P, V, R extends Collection<V>> R collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter, R target)
    {
        return this.getSortedSet().collectWith(function, parameter, target);
    }

    /**
     * @since 9.1
     */
    @Override
    public <V> MutableList<V> collectWithIndex(ObjectIntToObjectFunction<? super T, ? extends V> function)
    {
        return this.getSortedSet().collectWithIndex(function);
    }

    /**
     * @since 9.1
     */
    @Override
    public <V, R extends Collection<V>> R collectWithIndex(ObjectIntToObjectFunction<? super T, ? extends V> function, R target)
    {
        return this.getSortedSet().collectWithIndex(function, target);
    }

    @Override
    public <V> MutableList<V> collectIf(
            Predicate<? super T> predicate,
            Function<? super T, ? extends V> function)
    {
        return this.getSortedSet().collectIf(predicate, function);
    }
    
    @Override
    public <V, R extends Collection<V>> R collectIf(
            Predicate<? super T> predicate,
            Function<? super T, ? extends V> function,
            R target)
    {
        return this.getSortedSet().collectIf(predicate, function, target);
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> distinct()
    {
        return this; // A set is already distinct
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> takeWhile(Predicate<? super T> predicate)
    {
        throw new UnsupportedOperationException("Cannot create takeWhile view of UnmodifiableMutableSortedSet");
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> dropWhile(Predicate<? super T> predicate)
    {
        throw new UnsupportedOperationException("Cannot create dropWhile view of UnmodifiableMutableSortedSet");
    }

    @Override
    public int detectIndex(Predicate<? super T> predicate)
    {
        return this.getSortedSet().detectIndex(predicate);
    }

    @Override
    public <V> MutableSortedSetMultimap<V, T> groupBy(Function<? super T, ? extends V> function)
    {
        return this.getSortedSet().groupBy(function);
    }

    @Override
    public <V> MutableSortedSetMultimap<V, T> groupByEach(Function<? super T, ? extends Iterable<V>> function)
    {
        return this.getSortedSet().groupByEach(function);
    }

    @Override
    public <S> MutableList<Pair<T, S>> zip(Iterable<S> that)
    {
        return this.getSortedSet().zip(that);
    }

    @Override
    public <S, R extends Collection<Pair<T, S>>> R zip(Iterable<S> that, R target)
    {
        return this.getSortedSet().zip(that, target);
    }

    @Override
    public MutableSortedSet<Pair<T, Integer>> zipWithIndex()
    {
        return this.getSortedSet().zipWithIndex();
    }

    @Override
    public <R extends Collection<Pair<T, Integer>>> R zipWithIndex(R target)
    {
        return this.getSortedSet().zipWithIndex(target);
    }

    @Override
    public Comparator<? super T> comparator()
    {
        return this.getSortedSet().comparator();
    }

    @Override
    public MutableSortedSet<T> union(SetIterable<? extends T> set)
    {
        return this.getSortedSet().union(set);
    }

    @Override
    public <R extends Set<T>> R unionInto(SetIterable<? extends T> set, R targetSet)
    {
        return this.getSortedSet().unionInto(set, targetSet);
    }

    @Override
    public MutableSortedSet<T> intersect(SetIterable<? extends T> set)
    {
        return this.getSortedSet().intersect(set);
    }

    @Override
    public <R extends Set<T>> R intersectInto(SetIterable<? extends T> set, R targetSet)
    {
        return this.getSortedSet().intersectInto(set, targetSet);
    }

    @Override
    public MutableSortedSet<T> difference(SetIterable<? extends T> subtrahendSet)
    {
        return this.getSortedSet().difference(subtrahendSet);
    }

    @Override
    public <R extends Set<T>> R differenceInto(SetIterable<? extends T> subtrahendSet, R targetSet)
    {
        return this.getSortedSet().differenceInto(subtrahendSet, targetSet);
    }

    @Override
    public MutableSortedSet<T> symmetricDifference(SetIterable<? extends T> setB)
    {
        return this.getSortedSet().symmetricDifference(setB);
    }

    @Override
    public <R extends Set<T>> R symmetricDifferenceInto(SetIterable<? extends T> set, R targetSet)
    {
        return this.getSortedSet().symmetricDifferenceInto(set, targetSet);
    }

    @Override
    public boolean isSubsetOf(SetIterable<? extends T> candidateSuperset)
    {
        return this.getSortedSet().isSubsetOf(candidateSuperset);
    }

    @Override
    public boolean isProperSubsetOf(SetIterable<? extends T> candidateSuperset)
    {
        return this.getSortedSet().isProperSubsetOf(candidateSuperset);
    }

    @Override
    public MutableSortedSet<SortedSetIterable<T>> powerSet()
    {
        return this.getSortedSet().powerSet();
    }

    @Override
    public <B> LazyIterable<Pair<T, B>> cartesianProduct(SetIterable<B> set)
    {
        return this.getSortedSet().cartesianProduct(set);
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> subSet(T fromElement, T toElement)
    {
        throw new UnsupportedOperationException("Cannot create subSet view of UnmodifiableMutableSortedSet");
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> headSet(T toElement)
    {
        throw new UnsupportedOperationException("Cannot create headSet view of UnmodifiableMutableSortedSet");
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> tailSet(T fromElement)
    {
        throw new UnsupportedOperationException("Cannot create tailSet view of UnmodifiableMutableSortedSet");
    }

    @Override
    public T first()
    {
        return this.getSortedSet().first();
    }

    @Override
    public T last()
    {
        return this.getSortedSet().last();
    }

    @Override
    public int compareTo(SortedSetIterable<T> o)
    {
        return this.getSortedSet().compareTo(o);
    }

    @Override
    public int indexOf(Object object)
    {
        return this.getSortedSet().indexOf(object);
    }

    @Override
    public <S> boolean corresponds(OrderedIterable<S> other, Predicate2<? super T, ? super S> predicate)
    {
        return this.getSortedSet().corresponds(other, predicate);
    }

    @Override
    public void forEach(int startIndex, int endIndex, Procedure<? super T> procedure)
    {
        this.getSortedSet().forEach(startIndex, endIndex, procedure);
    }

    @Override
    public void forEachWithIndex(int fromIndex, int toIndex, ObjectIntProcedure<? super T> objectIntProcedure)
    {
        this.getSortedSet().forEachWithIndex(fromIndex, toIndex, objectIntProcedure);
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> toReversed()
    {
        throw new UnsupportedOperationException("Cannot create reversed view of UnmodifiableMutableSortedSet");
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> take(int count)
    {
        throw new UnsupportedOperationException("Cannot create take view of UnmodifiableMutableSortedSet");
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> drop(int count)
    {
        throw new UnsupportedOperationException("Cannot create drop view of UnmodifiableMutableSortedSet");
    }

    @Override
    public void reverseForEach(Procedure<? super T> procedure)
    {
        this.getSortedSet().reverseForEach(procedure);
    }

    @Override
    public void reverseForEachWithIndex(ObjectIntProcedure<? super T> procedure)
    {
        this.getSortedSet().reverseForEachWithIndex(procedure);
    }

    @Override
    public LazyIterable<T> asReversed()
    {
        return this.getSortedSet().asReversed();
    }

    @Override
    public int detectLastIndex(Predicate<? super T> predicate)
    {
        return this.getSortedSet().detectLastIndex(predicate);
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> with(T element)
    {
        throw new UnsupportedOperationException("Cannot call with() on " + this.getClass().getSimpleName());
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> without(T element)
    {
        throw new UnsupportedOperationException("Cannot call without() on " + this.getClass().getSimpleName());
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> withAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot call withAll() on " + this.getClass().getSimpleName());
    }

    @Override
    public org.eclipse.collections.api.set.sorted.UnmodifiableMutableSortedSet<T> withoutAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot call withoutAll() on " + this.getClass().getSimpleName());
    }

    protected Object writeReplace()
    {
        return new UnmodifiableCollectionSerializationProxy<>(this.getSortedSet());
    }

    @Override
    public ParallelSortedSetIterable<T> asParallel(ExecutorService executorService, int batchSize)
    {
        return this.getSortedSet().asParallel(executorService, batchSize);
    }
    
    // Methods from Collection interface that need to throw UnsupportedOperationException
    
    @Override
    public boolean add(T element)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSortedSet");
    }
    
    @Override
    public boolean remove(Object element)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }
    
    @Override
    public boolean addAll(Collection<? extends T> collection)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSortedSet");
    }
    
    @Override
    public boolean addAllIterable(Iterable<? extends T> iterable)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSortedSet");
    }
    
    @Override
    public boolean removeAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }
    
    @Override
    public boolean removeAllIterable(Iterable<?> iterable)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }
    
    @Override
    public boolean retainAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }
    
    @Override
    public boolean retainAllIterable(Iterable<?> iterable)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }
    
    @Override
    public void clear()
    {
        throw new UnsupportedOperationException("Cannot clear an UnmodifiableSortedSet");
    }
    
    // Delegating methods for reading operations
    
    @Override
    public int size()
    {
        return this.getSortedSet().size();
    }
    
    @Override
    public boolean isEmpty()
    {
        return this.getSortedSet().isEmpty();
    }
    
    @Override
    public boolean contains(Object o)
    {
        return this.getSortedSet().contains(o);
    }
    
    @Override
    public Iterator<T> iterator()
    {
        return new UnmodifiableIteratorAdapter<>(this.getSortedSet().iterator());
    }
    
    @Override
    public Object[] toArray()
    {
        return this.getSortedSet().toArray();
    }
    
    @Override
    public <S> S[] toArray(S[] a)
    {
        return this.getSortedSet().toArray(a);
    }
    
    @Override
    public boolean containsAll(Collection<?> c)
    {
        return this.getSortedSet().containsAll(c);
    }
    
    @Override
    public boolean containsAllArguments(Object... elements)
    {
        return this.getSortedSet().containsAllArguments(elements);
    }
    
    @Override
    public boolean containsAllIterable(Iterable<?> source)
    {
        return this.getSortedSet().containsAllIterable(source);
    }
    
    @Override
    public void forEach(Procedure<? super T> procedure)
    {
        this.getSortedSet().forEach(procedure);
    }
    
    @Override
    public void each(Procedure<? super T> procedure)
    {
        this.getSortedSet().each(procedure);
    }
    
    @Override
    public String makeString()
    {
        return this.getSortedSet().makeString();
    }
    
    @Override
    public String makeString(String separator)
    {
        return this.getSortedSet().makeString(separator);
    }
    
    @Override
    public String makeString(String start, String separator, String end)
    {
        return this.getSortedSet().makeString(start, separator, end);
    }
    
    @Override
    public void appendString(Appendable appendable)
    {
        this.getSortedSet().appendString(appendable);
    }
    
    @Override
    public void appendString(Appendable appendable, String separator)
    {
        this.getSortedSet().appendString(appendable, separator);
    }
    
    @Override
    public void appendString(Appendable appendable, String start, String separator, String end)
    {
        this.getSortedSet().appendString(appendable, start, separator, end);
    }
    
    @Override
    public <V> MutableObjectLongMap<V> sumByInt(Function<? super T, ? extends V> groupBy, IntFunction<? super T> function)
    {
        MutableObjectLongMap<V> result = ObjectLongMaps.mutable.empty();
        return this.injectInto(result, PrimitiveFunctions.sumByIntFunction(groupBy, function));
    }
    
    @Override
    public <V> MutableObjectDoubleMap<V> sumByFloat(Function<? super T, ? extends V> groupBy, FloatFunction<? super T> function)
    {
        MutableObjectDoubleMap<V> result = ObjectDoubleMaps.mutable.empty();
        return this.injectInto(result, PrimitiveFunctions.sumByFloatFunction(groupBy, function));
    }
    
    @Override
    public <V> MutableObjectLongMap<V> sumByLong(Function<? super T, ? extends V> groupBy, LongFunction<? super T> function)
    {
        MutableObjectLongMap<V> result = ObjectLongMaps.mutable.empty();
        return this.injectInto(result, PrimitiveFunctions.sumByLongFunction(groupBy, function));
    }
    
    @Override
    public <V> MutableObjectDoubleMap<V> sumByDouble(Function<? super T, ? extends V> groupBy, DoubleFunction<? super T> function)
    {
        MutableObjectDoubleMap<V> result = ObjectDoubleMaps.mutable.empty();
        return this.injectInto(result, PrimitiveFunctions.sumByDoubleFunction(groupBy, function));
    }
    
    // Additional delegating methods
    
    @Override
    public MutableList<T> toList()
    {
        return this.getSortedSet().toList();
    }
    
    @Override
    public MutableList<T> toSortedList()
    {
        return this.getSortedSet().toSortedList();
    }
    
    @Override
    public MutableList<T> toSortedList(Comparator<? super T> comparator)
    {
        return this.getSortedSet().toSortedList(comparator);
    }
    
    @Override
    public MutableSet<T> toSet()
    {
        return this.getSortedSet().toSet();
    }
    
    @Override
    public MutableSortedSet<T> toSortedSet()
    {
        return this.getSortedSet().toSortedSet();
    }
    
    @Override
    public MutableSortedSet<T> toSortedSet(Comparator<? super T> comparator)
    {
        return this.getSortedSet().toSortedSet(comparator);
    }
    
    @Override
    public MutableBag<T> toBag()
    {
        return this.getSortedSet().toBag();
    }
    
    @Override
    public MutableSortedBag<T> toSortedBag()
    {
        return this.getSortedSet().toSortedBag();
    }
    
    @Override
    public MutableSortedBag<T> toSortedBag(Comparator<? super T> comparator)
    {
        return this.getSortedSet().toSortedBag(comparator);
    }
    
    @Override
    public <NK, NV> MutableMap<NK, NV> toMap(Function<? super T, ? extends NK> keyFunction, Function<? super T, ? extends NV> valueFunction)
    {
        return this.getSortedSet().toMap(keyFunction, valueFunction);
    }
    
    @Override
    public <NK, NV, R extends Map<NK, NV>> R toMap(Function<? super T, ? extends NK> keyFunction, Function<? super T, ? extends NV> valueFunction, R target)
    {
        return this.getSortedSet().toMap(keyFunction, valueFunction, target);
    }
    
    @Override
    public <NK, NV> MutableSortedMap<NK, NV> toSortedMap(Function<? super T, ? extends NK> keyFunction, Function<? super T, ? extends NV> valueFunction)
    {
        return this.getSortedSet().toSortedMap(keyFunction, valueFunction);
    }
    
    @Override
    public <NK, NV> MutableSortedMap<NK, NV> toSortedMap(Comparator<? super NK> comparator, Function<? super T, ? extends NK> keyFunction, Function<? super T, ? extends NV> valueFunction)
    {
        return this.getSortedSet().toSortedMap(comparator, keyFunction, valueFunction);
    }
    
    @Override
    public <NK, NV> MutableBiMap<NK, NV> toBiMap(Function<? super T, ? extends NK> keyFunction, Function<? super T, ? extends NV> valueFunction)
    {
        return this.getSortedSet().toBiMap(keyFunction, valueFunction);
    }
    
    @Override
    public LazyIterable<T> asLazy()
    {
        return this.getSortedSet().asLazy();
    }
    
    @Override
    public <R extends Collection<T>> R into(R target)
    {
        return this.getSortedSet().into(target);
    }
    
    @Override
    public T min()
    {
        return this.getSortedSet().min();
    }
    
    @Override
    public T max()
    {
        return this.getSortedSet().max();
    }
    
    @Override
    public T min(Comparator<? super T> comparator)
    {
        return this.getSortedSet().min(comparator);
    }
    
    @Override
    public T max(Comparator<? super T> comparator)
    {
        return this.getSortedSet().max(comparator);
    }
    
    @Override
    public <V extends Comparable<? super V>> T minBy(Function<? super T, ? extends V> function)
    {
        return this.getSortedSet().minBy(function);
    }
    
    @Override
    public <V extends Comparable<? super V>> T maxBy(Function<? super T, ? extends V> function)
    {
        return this.getSortedSet().maxBy(function);
    }
    
    @Override
    public long sumOfInt(IntFunction<? super T> function)
    {
        return this.getSortedSet().sumOfInt(function);
    }
    
    @Override
    public double sumOfFloat(FloatFunction<? super T> function)
    {
        return this.getSortedSet().sumOfFloat(function);
    }
    
    @Override
    public long sumOfLong(LongFunction<? super T> function)
    {
        return this.getSortedSet().sumOfLong(function);
    }
    
    @Override
    public double sumOfDouble(DoubleFunction<? super T> function)
    {
        return this.getSortedSet().sumOfDouble(function);
    }
    
    @Override
    public T detect(Predicate<? super T> predicate)
    {
        return this.getSortedSet().detect(predicate);
    }
    
    @Override
    public <P> T detectWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getSortedSet().detectWith(predicate, parameter);
    }
    
    @Override
    public Optional<T> detectOptional(Predicate<? super T> predicate)
    {
        return this.getSortedSet().detectOptional(predicate);
    }
    
    @Override
    public <P> Optional<T> detectWithOptional(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getSortedSet().detectWithOptional(predicate, parameter);
    }
    
    @Override
    public T detectIfNone(Predicate<? super T> predicate, Function0<? extends T> function)
    {
        return this.getSortedSet().detectIfNone(predicate, function);
    }
    
    @Override
    public <P> T detectWithIfNone(Predicate2<? super T, ? super P> predicate, P parameter, Function0<? extends T> function)
    {
        return this.getSortedSet().detectWithIfNone(predicate, parameter, function);
    }
    
    @Override
    public int count(Predicate<? super T> predicate)
    {
        return this.getSortedSet().count(predicate);
    }
    
    @Override
    public <P> int countWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getSortedSet().countWith(predicate, parameter);
    }
    
    @Override
    public boolean anySatisfy(Predicate<? super T> predicate)
    {
        return this.getSortedSet().anySatisfy(predicate);
    }
    
    @Override
    public <P> boolean anySatisfyWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getSortedSet().anySatisfyWith(predicate, parameter);
    }
    
    @Override
    public boolean allSatisfy(Predicate<? super T> predicate)
    {
        return this.getSortedSet().allSatisfy(predicate);
    }
    
    @Override
    public <P> boolean allSatisfyWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getSortedSet().allSatisfyWith(predicate, parameter);
    }
    
    @Override
    public boolean noneSatisfy(Predicate<? super T> predicate)
    {
        return this.getSortedSet().noneSatisfy(predicate);
    }
    
    @Override
    public <P> boolean noneSatisfyWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getSortedSet().noneSatisfyWith(predicate, parameter);
    }
    
    @Override
    public <IV> IV injectInto(IV injectedValue, Function2<? super IV, ? super T, ? extends IV> function)
    {
        return this.getSortedSet().injectInto(injectedValue, function);
    }
    
    @Override
    public int injectInto(int injectedValue, IntObjectToIntFunction<? super T> function)
    {
        return this.getSortedSet().injectInto(injectedValue, function);
    }
    
    @Override
    public long injectInto(long injectedValue, LongObjectToLongFunction<? super T> function)
    {
        return this.getSortedSet().injectInto(injectedValue, function);
    }
    
    @Override
    public float injectInto(float injectedValue, FloatObjectToFloatFunction<? super T> function)
    {
        return this.getSortedSet().injectInto(injectedValue, function);
    }
    
    @Override
    public double injectInto(double injectedValue, DoubleObjectToDoubleFunction<? super T> function)
    {
        return this.getSortedSet().injectInto(injectedValue, function);
    }
    
    @Override
    public <IV, P> IV injectIntoWith(IV injectValue, Function3<? super IV, ? super T, ? super P, ? extends IV> function, P parameter)
    {
        return this.getSortedSet().injectIntoWith(injectValue, function, parameter);
    }
    
    @Override
    public RichIterable<RichIterable<T>> chunk(int size)
    {
        return this.getSortedSet().chunk(size);
    }
    
    @Override
    public T getFirst()
    {
        return this.getSortedSet().getFirst();
    }
    
    @Override
    public T getLast()
    {
        return this.getSortedSet().getLast();
    }
    
    @Override
    public T getOnly()
    {
        return this.getSortedSet().getOnly();
    }
    
    @Override
    public <P> void forEachWith(Procedure2<? super T, ? super P> procedure, P parameter)
    {
        this.getSortedSet().forEachWith(procedure, parameter);
    }
    
    @Override
    public <P> org.eclipse.collections.api.tuple.Twin<MutableList<T>> selectAndRejectWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        return this.getSortedSet().selectAndRejectWith(predicate, parameter);
    }
    
    @Override
    public void forEachWithIndex(ObjectIntProcedure<? super T> objectIntProcedure)
    {
        this.getSortedSet().forEachWithIndex(objectIntProcedure);
    }

    @Override
    public <V, R extends org.eclipse.collections.api.multimap.MutableMultimap<V, T>> R groupBy(
            Function<? super T, ? extends V> function,
            R target)
    {
        return this.getSortedSet().groupBy(function, target);
    }

    @Override
    public <V, R extends org.eclipse.collections.api.multimap.MutableMultimap<V, T>> R groupByEach(
            Function<? super T, ? extends Iterable<V>> function,
            R target)
    {
        return this.getSortedSet().groupByEach(function, target);
    }

    @Override
    public <V> org.eclipse.collections.api.map.MutableMap<V, T> groupByUniqueKey(Function<? super T, ? extends V> function)
    {
        return this.getSortedSet().groupByUniqueKey(function);
    }

    @Override
    public <V, R extends org.eclipse.collections.api.map.MutableMapIterable<V, T>> R groupByUniqueKey(
            Function<? super T, ? extends V> function,
            R target)
    {
        return this.getSortedSet().groupByUniqueKey(function, target);
    }
}
