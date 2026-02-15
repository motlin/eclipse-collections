/*
 * Copyright (c) 2021 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.lazy;

import java.util.Iterator;

import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.lazy.CompositeIterable;
import org.eclipse.collections.impl.lazy.LazyIterableAdapter;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.eclipse.collections.test.LazyNoIteratorTestCase;
import org.eclipse.collections.test.list.mutable.FastListNoIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CompositeIterableTestNoIteratorTest implements LazyNoIteratorTestCase
{
    @Override
    @Test
    public void Iterable_remove()
    {
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(3, 2, 1).iterator().next());
    }
    @Override
    public <T> LazyIterable<T> newWith(T... elements)
    {
        RichIterable<MutableList<T>> noIteratorLists = ArrayIterate.chunk(elements, 3).collect(chunk -> new FastListNoIterator<T>().withAll(chunk));
        Iterable<T>[] iterables = (Iterable<T>[]) noIteratorLists.toArray(new Iterable[]{});
        return new LazyIterableAdapter<>(CompositeIterable.with(iterables))
        {
            @Override
            public Iterator<T> iterator()
            {
                throw new UnsupportedOperationException("No iteration patterns should delegate to iterator()");
            }
        };
    }

    @Override
    @Test
    public void RichIterable_anySatisfy_allSatisfy_noneSatisfy()
    {
        assertThrows(UnsupportedOperationException.class, () -> LazyNoIteratorTestCase.super.RichIterable_anySatisfy_allSatisfy_noneSatisfy());
    }

    @Override
    @Test
    public void RichIterable_contains()
    {
        assertThrows(UnsupportedOperationException.class, () -> LazyNoIteratorTestCase.super.RichIterable_contains());
    }

    @Override
    @Test
    public void RichIterable_containsAll()
    {
        assertThrows(UnsupportedOperationException.class, () -> LazyNoIteratorTestCase.super.RichIterable_containsAll());
    }

    @Override
    @Test
    public void RichIterable_containsAllArguments()
    {
        assertThrows(UnsupportedOperationException.class, () -> LazyNoIteratorTestCase.super.RichIterable_containsAllArguments());
    }

    @Override
    @Test
    public void RichIterable_containsAllIterable()
    {
        assertThrows(UnsupportedOperationException.class, () -> LazyNoIteratorTestCase.super.RichIterable_containsAllIterable());
    }

    @Override
    @Test
    public void RichIterable_containsAny()
    {
        assertThrows(UnsupportedOperationException.class, () -> LazyNoIteratorTestCase.super.RichIterable_containsAny());
    }

    @Override
    @Test
    public void RichIterable_containsNone()
    {
        assertThrows(UnsupportedOperationException.class, () -> LazyNoIteratorTestCase.super.RichIterable_containsNone());
    }

    @Override
    @Test
    public void RichIterable_detect()
    {
        assertThrows(UnsupportedOperationException.class, () -> LazyNoIteratorTestCase.super.RichIterable_detect());
    }

    @Override
    @Test
    public void RichIterable_detectOptionalNull()
    {
        assertThrows(NullPointerException.class, () -> this.newWith(3, 3, 3, 2, 2, 1).detectOptional(null));
    }

    @Override
    @Test
    public void RichIterable_iterationOrder()
    {
        assertThrows(UnsupportedOperationException.class, () -> LazyNoIteratorTestCase.super.RichIterable_iterationOrder());
    }
}
