/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.set.mutable.sorted;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.MutableBooleanList;
import org.eclipse.collections.api.list.primitive.MutableByteList;
import org.eclipse.collections.api.list.primitive.MutableCharList;
import org.eclipse.collections.api.list.primitive.MutableDoubleList;
import org.eclipse.collections.api.list.primitive.MutableFloatList;
import org.eclipse.collections.api.list.primitive.MutableIntList;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.api.list.primitive.MutableShortList;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.eclipse.collections.impl.factory.primitive.ShortLists;
import org.eclipse.collections.impl.set.sorted.mutable.ScapegoatTreeSet;
import org.eclipse.collections.impl.test.junit.Java8Runner;
import org.eclipse.collections.test.IterableTestCase;
import org.eclipse.collections.test.bag.mutable.sorted.OrderedIterableNoIteratorTest;
import org.junit.runner.RunWith;

@RunWith(Java8Runner.class)
public class ScapegoatTreeSetTest implements MutableSortedSetTestCase, MutableSortedSetNoComparatorTestCase, OrderedIterableNoIteratorTest
{
    @SafeVarargs
    @Override
    public final <T> MutableSortedSet<T> newWith(T... elements)
    {
        // Cast assumes T extends Comparable<? super T>
        MutableSortedSet<T> result = (MutableSortedSet<T>) new ScapegoatTreeSet<>();
        IterableTestCase.addAllTo(elements, result);
        return result;
    }

    @Override
    public <T> MutableSortedSet<T> getExpectedFiltered(T... elements)
    {
        return SortedSets.mutable.with(elements);
    }

    @Override
    public <T> MutableSortedSet<T> newMutableForFilter(T... elements)
    {
        return SortedSets.mutable.with(elements);
    }

    @Override
    public <T> MutableList<T> getExpectedTransformed(T... elements)
    {
        return Lists.mutable.with(elements);
    }

    @Override
    public <T> MutableList<T> newMutableForTransform(T... elements)
    {
        return Lists.mutable.with(elements);
    }

    @Override
    public MutableBooleanList newBooleanForTransform(boolean... elements)
    {
        return BooleanLists.mutable.with(elements);
    }

    @Override
    public MutableByteList newByteForTransform(byte... elements)
    {
        return ByteLists.mutable.with(elements);
    }

    @Override
    public MutableCharList newCharForTransform(char... elements)
    {
        return CharLists.mutable.with(elements);
    }

    @Override
    public MutableDoubleList newDoubleForTransform(double... elements)
    {
        return DoubleLists.mutable.with(elements);
    }

    @Override
    public MutableFloatList newFloatForTransform(float... elements)
    {
        return FloatLists.mutable.with(elements);
    }

    @Override
    public MutableIntList newIntForTransform(int... elements)
    {
        return IntLists.mutable.with(elements);
    }

    @Override
    public MutableLongList newLongForTransform(long... elements)
    {
        return LongLists.mutable.with(elements);
    }

    @Override
    public MutableShortList newShortForTransform(short... elements)
    {
        return ShortLists.mutable.with(elements);
    }

    @Override
    public void Iterable_next()
    {
        OrderedIterableNoIteratorTest.super.Iterable_next();
    }

    @Override
    public void Iterable_remove()
    {
        OrderedIterableNoIteratorTest.super.Iterable_remove();
    }

    @Override
    public void RichIterable_getFirst()
    {
        OrderedIterableNoIteratorTest.super.RichIterable_getFirst();
    }

    @Override
    public void RichIterable_getLast()
    {
        OrderedIterableNoIteratorTest.super.RichIterable_getLast();
    }

    @Override
    public void OrderedIterable_next()
    {
        OrderedIterableNoIteratorTest.super.OrderedIterable_next();
    }
}
