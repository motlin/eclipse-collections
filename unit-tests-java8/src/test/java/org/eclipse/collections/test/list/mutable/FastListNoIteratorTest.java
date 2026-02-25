/*
 * Copyright (c) 2021 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.list.mutable;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.mutable.ReversedMutableList;
import org.eclipse.collections.impl.list.mutable.ReversedRandomAccessMutableList;
import org.eclipse.collections.impl.list.mutable.SynchronizedMutableList;
import org.eclipse.collections.impl.list.mutable.UnmodifiableMutableList;
import org.eclipse.collections.test.IterableTestCase;
import org.eclipse.collections.test.NoIteratorTestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class FastListNoIteratorTest implements MutableListTestCase, NoIteratorTestCase
{
    @SafeVarargs
    @Override
    public final <T> MutableList<T> newWith(T... elements)
    {
        MutableList<T> result = new FastListNoIterator<>();
        IterableTestCase.addAllTo(elements, result);
        return result;
    }

    @Override
    @Test
    public void Iterable_remove()
    {
        NoIteratorTestCase.super.Iterable_remove();
    }

    @Override
    @Test
    public void List_subList_subList_iterator_add_remove()
    {
        // Not applicable
    }

    @Override
    @Test
    public void OrderedIterable_next()
    {
        // Not applicable
    }

    // Default test uses assertIterablesEqual() which triggers listIterator(), but FastListNoIterator throws on listIterator().
    @Override
    @Test
    public void MutableList_wrapping_order()
    {
        MutableList<Integer> list = this.newWith(1, 2, 3, 4, 5);

        assertEquals(UnmodifiableMutableList.class, list.asUnmodifiable().asSynchronized().getClass());
        assertEquals(UnmodifiableMutableList.class, list.asSynchronized().asUnmodifiable().getClass());
        assertEquals(ReversedRandomAccessMutableList.class, list.asUnmodifiable().reversed().getClass());
        assertEquals(ReversedRandomAccessMutableList.class, list.reversed().asUnmodifiable().getClass());
        assertInstanceOf(UnmodifiableMutableList.class, list.asUnmodifiable().subList(1, 4));
        assertInstanceOf(UnmodifiableMutableList.class, list.subList(1, 4).asUnmodifiable());
        assertEquals(ReversedMutableList.class, list.asSynchronized().reversed().getClass());
        assertEquals(ReversedMutableList.class, list.reversed().asSynchronized().getClass());
        assertEquals(SynchronizedMutableList.class, list.asSynchronized().subList(1, 4).getClass());
        assertEquals(SynchronizedMutableList.class, list.subList(1, 4).asSynchronized().getClass());
        assertEquals(ReversedRandomAccessMutableList.class, list.reversed().subList(1, 4).getClass());
        assertEquals(ReversedRandomAccessMutableList.class, list.subList(1, 4).reversed().getClass());
    }
}
