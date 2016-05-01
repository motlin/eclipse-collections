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

import java.util.Iterator;

import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.set.sorted.mutable.ScapegoatTreeSet;
import org.eclipse.collections.impl.test.junit.Java8Runner;
import org.eclipse.collections.test.IterableTestCase;
import org.eclipse.collections.test.bag.mutable.sorted.OrderedIterableNoIteratorTest;
import org.junit.runner.RunWith;

@RunWith(Java8Runner.class)
public class ScapegoatTreeSetNoIteratorTest implements MutableSortedSetTestCase, MutableSortedSetNoComparatorTestCase, OrderedIterableNoIteratorTest
{
    @SafeVarargs
    @Override
    public final <T> MutableSortedSet<T> newWith(T... elements)
    {
        // Cast assumes T extends Comparable<? super T>
        MutableSortedSet<T> result = (MutableSortedSet<T>) new ScapegoatTreeSetNoIterator<>();
        IterableTestCase.addAllTo(elements, result);
        return result;
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

    public static class ScapegoatTreeSetNoIterator<T extends Comparable<? super T>> extends ScapegoatTreeSet<T>
    {
        public ScapegoatTreeSetNoIterator()
        {
            // For serialization
        }

        @Override
        public Iterator<T> iterator()
        {
            throw new AssertionError("No iteration patterns should delegate to iterator()");
        }
    }
}
