/*
 * Copyright (c) 2022 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.set.sorted;

import java.util.SortedSet;

import org.eclipse.collections.test.FixedSizeCollectionTestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public interface UnmodifiableSortedSetTestCase extends FixedSizeCollectionTestCase, SortedSetTestCase
{
    @Override
    default boolean allowsSubSetViews()
    {
        return true;
    }

    @Override
    @Test
    default void Iterable_remove()
    {
        FixedSizeCollectionTestCase.super.Iterable_remove();
    }

    @Override
    @Test
    default void Collection_add()
    {
        FixedSizeCollectionTestCase.super.Collection_add();
    }

    @Override
    @Test
    default void SortedSet_subSet_subSet_addAll()
    {
        SortedSet<Integer> set = this.newWith(10, 20, 30, 40);
        SortedSet<Integer> subset = set.subSet(40, 10);
        SortedSet<Integer> subset2 = subset.subSet(30, 10);
        assertThrows(UnsupportedOperationException.class, () -> subset2.addAll(this.newWith(25, 28)));
    }

    @Override
    @Test
    default void SortedSet_subSet_subSet_clear()
    {
        SortedSet<Integer> set = this.newWith(10, 20, 30, 40);
        SortedSet<Integer> subset = set.subSet(40, 10);
        SortedSet<Integer> subset2 = subset.subSet(30, 10);
        assertThrows(UnsupportedOperationException.class, subset2::clear);
    }

    @Override
    @Test
    default void SortedSet_subSet_subSet_iterator_remove()
    {
        SortedSet<Integer> set = this.newWith(10, 20, 30, 40);
        SortedSet<Integer> subset = set.subSet(40, 10);
        SortedSet<Integer> subset2 = subset.subSet(30, 10);
        assertThrows(UnsupportedOperationException.class, () -> subset2.add(25));
    }

    @Override
    @Test
    default void SortedSet_subSet_subSet_remove()
    {
        SortedSet<Integer> set = this.newWith(10, 20, 30, 40);
        SortedSet<Integer> subset = set.subSet(40, 10);
        SortedSet<Integer> subset2 = subset.subSet(30, 10);
        assertThrows(UnsupportedOperationException.class, () -> subset2.add(25));
    }
}
