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

import java.util.Random;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.list.mutable.ReversedMutableList;
import org.eclipse.collections.impl.list.mutable.UnmodifiableMutableList;
import org.eclipse.collections.test.UnmodifiableMutableCollectionTestCase;
import org.eclipse.collections.test.list.UnmodifiableListTestCase;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface UnmodifiableMutableListTestCase extends UnmodifiableMutableCollectionTestCase, UnmodifiableListTestCase, MutableListTestCase
{
    @Override
    @Test
    default void MutableList_sortThis()
    {
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(5, 1, 4, 2, 3).sortThis());
    }

    @Override
    @Test
    default void MutableList_shuffleThis()
    {
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(5, 1, 4, 2, 3).shuffleThis());
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(5, 1, 4, 2, 3).shuffleThis(new Random(8)));
    }

    @Override
    @Test
    default void Iterable_remove()
    {
        UnmodifiableMutableCollectionTestCase.super.Iterable_remove();
    }

    @Override
    @Test
    default void MutableList_sortThis_comparator()
    {
        assertThrows(UnsupportedOperationException.class, () ->
                this.newWith(5, 1, 4, 2, 3).sortThis(Comparators.reverseNaturalOrder()));
    }

    // Base list is already unmodifiable, so asSynchronized().subList() returns UnmodifiableMutableList, not SynchronizedMutableList.
    @Override
    @Test
    default void MutableList_wrapping_order()
    {
        MutableList<Integer> list = this.newWith(1, 2, 3, 4, 5);

        MutableList<Integer> unmodSync = list.asUnmodifiable().asSynchronized();
        MutableList<Integer> syncUnmod = list.asSynchronized().asUnmodifiable();
        assertEquals(UnmodifiableMutableList.class, unmodSync.getClass());
        assertEquals(UnmodifiableMutableList.class, syncUnmod.getClass());
        assertIterablesEqual(unmodSync, syncUnmod);

        MutableList<Integer> unmodReversed = list.asUnmodifiable().reversed();
        MutableList<Integer> reversedUnmod = list.reversed().asUnmodifiable();
        assertSame(unmodReversed.getClass(), reversedUnmod.getClass());
        assertIterablesEqual(unmodReversed, reversedUnmod);

        MutableList<Integer> unmodSubList = list.asUnmodifiable().subList(1, 4);
        MutableList<Integer> subListUnmod = list.subList(1, 4).asUnmodifiable();
        assertInstanceOf(UnmodifiableMutableList.class, unmodSubList);
        assertInstanceOf(UnmodifiableMutableList.class, subListUnmod);
        assertIterablesEqual(unmodSubList, subListUnmod);

        MutableList<Integer> syncReversed = list.asSynchronized().reversed();
        MutableList<Integer> reversedSync = list.reversed().asSynchronized();
        assertEquals(ReversedMutableList.class, syncReversed.getClass());
        assertEquals(ReversedMutableList.class, reversedSync.getClass());
        assertIterablesEqual(syncReversed, reversedSync);

        MutableList<Integer> syncSubList = list.asSynchronized().subList(1, 4);
        MutableList<Integer> subListSync = list.subList(1, 4).asSynchronized();
        assertInstanceOf(UnmodifiableMutableList.class, syncSubList);
        assertInstanceOf(UnmodifiableMutableList.class, subListSync);
        assertIterablesEqual(syncSubList, subListSync);

        MutableList<Integer> reversedSubList = list.reversed().subList(1, 4);
        MutableList<Integer> subListReversed = list.subList(1, 4).reversed();
        assertSame(reversedSubList.getClass(), subListReversed.getClass());
        assertIterablesEqual(reversedSubList, subListReversed);
    }
}
