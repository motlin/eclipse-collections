/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test;

import java.util.Iterator;

import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.test.set.sorted.SortedSetTestCase;
import org.junit.Test;

import static org.eclipse.collections.impl.test.Verify.assertThrows;
import static org.junit.Assert.assertArrayEquals;

public interface MutableSortedNaturalOrderUniqueTestCase extends SortedSetTestCase, SortedNaturalOrderUniqueTestCase, MutableSortedIterableTestCase//, MutableCollectionTestCase
{
    @Override
    <T> MutableSortedSet<T> newWith(T... elements);

    @Override
    @Test
    default void Iterable_next()
    {
        SortedNaturalOrderUniqueTestCase.super.Iterable_next();
    }

    @Test
    @Override
    default void Iterable_remove()
    {
        Iterable<Integer> iterable = this.newWith(1, 2, 3);
        Iterator<Integer> iterator = iterable.iterator();
        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

    @Override
    @Test
    default void RichIterable_toArray()
    {
        Object[] array = this.newWith(1, 2, 3).toArray();
        assertArrayEquals(new Object[]{1, 2, 3}, array);
    }
}
