/*
 * Copyright (c) 2021 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.set.sorted;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

import org.assertj.core.api.Assertions;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.test.CollectionTestCase;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface SortedSetTestCase extends CollectionTestCase
{
    default boolean allowsRemove()
    {
        return true;
    }

    @Override
    <T> SortedSet<T> newWith(T... elements);

    @Override
    @Test
    default void Iterable_next()
    {
        CollectionTestCase.super.Iterable_next();

        SortedSet<Integer> set = this.newWith(3, 2, 1);
        Iterator<Integer> iterator = set.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(3), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(2), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(1), iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);

        Iterator<Integer> iterator2 = set.iterator();
        assertEquals(Integer.valueOf(3), iterator2.next());
        assertEquals(Integer.valueOf(2), iterator2.next());
        assertEquals(Integer.valueOf(1), iterator2.next());
        assertThrows(NoSuchElementException.class, iterator2::next);

        SortedSet<Integer> emptySet = this.newWith();
        Iterator<Integer> emptyIterator = emptySet.iterator();
        assertFalse(emptyIterator.hasNext());
        assertThrows(NoSuchElementException.class, emptyIterator::next);
    }

    @Override
    @Test
    default void Iterable_remove()
    {
        SortedSet<Integer> set = this.newWith(3, 2, 1);
        Iterator<Integer> iterator = set.iterator();
        assertEquals(Integer.valueOf(3), iterator.next());
        iterator.remove();
        assertIterablesEqual(this.newWith(2, 1), set);

        SortedSet<Integer> set2 = this.newWith(5, 3, 1, 4, 2);
        Iterator<Integer> iterator2 = set2.iterator();
        iterator2.next();
        iterator2.next();
        iterator2.remove();
        assertIterablesEqual(this.newWith(5, 3, 2, 1), set2);

        SortedSet<Integer> set3 = this.newWith(1, 2, 3, 4, 5);
        Iterator<Integer> iterator3 = set3.iterator();
        iterator3.next();
        iterator3.remove();
        iterator3.next();
        iterator3.remove();
        iterator3.next();
        iterator3.remove();
        assertIterablesEqual(this.newWith(2, 1), set3);

        SortedSet<Integer> set4 = this.newWith(42);
        Iterator<Integer> iterator4 = set4.iterator();
        iterator4.next();
        iterator4.remove();
        assertIterablesEqual(this.newWith(), set4);
        assertFalse(iterator4.hasNext());

        SortedSet<Integer> set5 = this.newWith(1, 2, 3);
        Iterator<Integer> iterator5 = set5.iterator();
        assertThrows(IllegalStateException.class, iterator5::remove);

        SortedSet<Integer> set6 = this.newWith(1, 2, 3);
        Iterator<Integer> iterator6 = set6.iterator();
        iterator6.next();
        iterator6.remove();
        assertThrows(IllegalStateException.class, iterator6::remove);
    }

    @Test
    default void SortedSet_comparator()
    {
        SortedSet<Integer> set = this.newWith(3, 1, 2);
        Comparator<? super Integer> comparator = set.comparator();

        assertIterablesEqual(this.newWith(3, 2, 1), set);

        Assertions.assertThat(comparator).isIn(Comparators.reverseNaturalOrder());
    }

    @Test
    default void SortedSet_subSet_headSet_tailSet()
    {
        SortedSet<Integer> set = this.newWith(1, 3, 5, 7, 9);

        // before to before
        assertIterablesEqual(this.newWith(), set.subSet(10, 10));

        // before to beginning
        assertIterablesEqual(this.newWith(), set.subSet(10, 9));

        // before to between
        assertIterablesEqual(this.newWith(9), set.subSet(10, 8));

        // before to inside
        assertIterablesEqual(this.newWith(9), set.subSet(10, 7));

        // before to end
        assertIterablesEqual(this.newWith(9, 7, 5, 3), set.subSet(10, 1));

        // before to after
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set.subSet(10, 0));

        assertThrows(IllegalArgumentException.class, () -> set.subSet(1, 9));

        // beginning to beginning
        assertIterablesEqual(this.newWith(), set.subSet(9, 9));

        // beginning to between
        assertIterablesEqual(this.newWith(9), set.subSet(9, 8));

        // beginning to inside
        assertIterablesEqual(this.newWith(9, 7, 5), set.subSet(9, 3));

        // beginning to end
        assertIterablesEqual(this.newWith(9, 7, 5, 3), set.subSet(9, 1));

        // beginning to after
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set.subSet(9, 0));

        // between to between
        assertIterablesEqual(this.newWith(), set.subSet(8, 8));

        // between to inside
        assertIterablesEqual(this.newWith(7, 5), set.subSet(8, 3));

        // inside to inside
        assertIterablesEqual(this.newWith(7, 5), set.subSet(7, 3));

        // inside to between
        assertIterablesEqual(this.newWith(7, 5, 3), set.subSet(7, 2));

        // inside to end
        assertIterablesEqual(this.newWith(7, 5, 3), set.subSet(7, 1));

        // inside to after
        assertIterablesEqual(this.newWith(7, 5, 3, 1), set.subSet(7, 0));

        // end to end
        assertIterablesEqual(this.newWith(), set.subSet(1, 1));

        // end to after
        assertIterablesEqual(this.newWith(1), set.subSet(1, 0));

        // headSet: before
        assertIterablesEqual(this.newWith(), set.headSet(10));

        // headSet: beginning
        assertIterablesEqual(this.newWith(), set.headSet(9));

        // headSet: between
        assertIterablesEqual(this.newWith(9), set.headSet(8));

        // headSet: inside
        assertIterablesEqual(this.newWith(9, 7, 5), set.headSet(3));

        // headSet: end
        assertIterablesEqual(this.newWith(9, 7, 5, 3), set.headSet(1));

        // headSet: after
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set.headSet(0));

        // tailSet: before
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set.tailSet(10));

        // tailSet: beginning
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set.tailSet(9));

        // tailSet: between
        assertIterablesEqual(this.newWith(7, 5, 3, 1), set.tailSet(8));

        // tailSet: inside
        assertIterablesEqual(this.newWith(5, 3, 1), set.tailSet(5));

        // tailSet: end
        assertIterablesEqual(this.newWith(1), set.tailSet(1));

        // tailSet: after
        assertIterablesEqual(this.newWith(), set.tailSet(0));
    }

    @Test
    default void SortedSet_subSet_subSet()
    {
        SortedSet<Integer> set = this.newWith(9, 7, 5, 3, 1);

        SortedSet<Integer> subset = set.subSet(9, 1);
        assertIterablesEqual(this.newWith(9, 7, 5, 3), subset);
        assertIterablesEqual(this.newWith(7, 5), subset.subSet(7, 3));
        assertIterablesEqual(this.newWith(9, 7, 5), subset.subSet(9, 3));
        assertIterablesEqual(this.newWith(5, 3), subset.subSet(5, 2));

        assertThrows(IllegalArgumentException.class, () -> subset.subSet(10, 5));
        assertThrows(IllegalArgumentException.class, () -> subset.subSet(5, 0));

        assertIterablesEqual(this.newWith(9, 7, 5), subset.headSet(3));
        assertIterablesEqual(this.newWith(9), subset.headSet(8));
        assertIterablesEqual(this.newWith(9, 7, 5, 3), subset.headSet(1));

        assertThrows(IllegalArgumentException.class, () -> subset.headSet(0));

        assertIterablesEqual(this.newWith(7, 5, 3), subset.tailSet(7));
        assertIterablesEqual(this.newWith(5, 3), subset.tailSet(6));
        assertIterablesEqual(this.newWith(9, 7, 5, 3), subset.tailSet(9));

        SortedSet<Integer> headView = set.headSet(3);
        assertIterablesEqual(this.newWith(9, 7, 5), headView);
        assertIterablesEqual(this.newWith(9), headView.headSet(7));
        assertIterablesEqual(this.newWith(9, 7, 5), headView.headSet(3));

        assertThrows(IllegalArgumentException.class, () -> headView.headSet(2));

        assertIterablesEqual(this.newWith(7, 5), headView.subSet(7, 3));
        assertIterablesEqual(this.newWith(9, 7, 5), headView.subSet(10, 3));

        assertIterablesEqual(this.newWith(7, 5), headView.tailSet(7));
        assertIterablesEqual(this.newWith(5), headView.tailSet(6));

        SortedSet<Integer> tailView = set.tailSet(3);
        assertIterablesEqual(this.newWith(3, 1), tailView);
        assertIterablesEqual(this.newWith(1), tailView.tailSet(1));
        assertIterablesEqual(this.newWith(3, 1), tailView.tailSet(3));

        assertIterablesEqual(this.newWith(3), tailView.subSet(3, 1));
        assertIterablesEqual(this.newWith(3, 1), tailView.subSet(3, 0));

        assertIterablesEqual(this.newWith(3), tailView.headSet(1));
        assertIterablesEqual(this.newWith(3, 1), tailView.headSet(-1));

        assertThrows(IllegalArgumentException.class, () -> tailView.headSet(4));
    }

    @Test
    default void SortedSet_subSet_subSet_remove()
    {
        if (!this.allowsRemove())
        {
            return;
        }

        SortedSet<Integer> set = this.newWith(9, 7, 5, 3, 1);
        SortedSet<Integer> subset = set.subSet(9, 1);
        SortedSet<Integer> subset2 = subset.subSet(7, 3);
        assertIterablesEqual(this.newWith(9, 7, 5, 3), subset);
        assertIterablesEqual(this.newWith(7, 5), subset2);
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set);

        subset2.add(6);

        assertIterablesEqual(this.newWith(9, 7, 6, 5, 3), subset);
        assertIterablesEqual(this.newWith(7, 6, 5), subset2);
        assertIterablesEqual(this.newWith(9, 7, 6, 5, 3, 1), set);

        assertTrue(subset2.remove(6));
        assertIterablesEqual(this.newWith(9, 7, 5, 3), subset);
        assertIterablesEqual(this.newWith(7, 5), subset2);
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set);

        SortedSet<Integer> headView = set.headSet(3);
        assertIterablesEqual(this.newWith(9, 7, 5), headView);
        headView.add(8);
        assertIterablesEqual(this.newWith(9, 8, 7, 5, 3, 1), set);
        assertIterablesEqual(this.newWith(9, 8, 7, 5), headView);
        assertTrue(headView.remove(8));
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set);

        SortedSet<Integer> tailView = set.tailSet(5);
        assertIterablesEqual(this.newWith(5, 3, 1), tailView);
        tailView.add(4);
        assertIterablesEqual(this.newWith(9, 7, 5, 4, 3, 1), set);
        assertIterablesEqual(this.newWith(5, 4, 3, 1), tailView);
        assertTrue(tailView.remove(4));
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set);
    }

    @Test
    default void SortedSet_subSet_subSet_iterator_remove()
    {
        if (!this.allowsRemove())
        {
            return;
        }

        SortedSet<Integer> set = this.newWith(9, 7, 5, 3, 1);
        SortedSet<Integer> subset = set.subSet(9, 1);
        SortedSet<Integer> subset2 = subset.subSet(7, 3);
        assertIterablesEqual(this.newWith(9, 7, 5, 3), subset);
        assertIterablesEqual(this.newWith(7, 5), subset2);
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set);

        subset2.add(6);
        assertIterablesEqual(this.newWith(9, 7, 6, 5, 3), subset);
        assertIterablesEqual(this.newWith(7, 6, 5), subset2);
        assertIterablesEqual(this.newWith(9, 7, 6, 5, 3, 1), set);

        Iterator<Integer> iterator = subset2.iterator();
        iterator.next();
        iterator.remove();
        assertIterablesEqual(this.newWith(9, 6, 5, 3), subset);
        assertIterablesEqual(this.newWith(6, 5), subset2);
        assertIterablesEqual(this.newWith(9, 6, 5, 3, 1), set);

        SortedSet<Integer> headView = set.headSet(3);
        SortedSet<Integer> headView2 = headView.headSet(6);
        assertIterablesEqual(this.newWith(9, 6, 5), headView);
        assertIterablesEqual(this.newWith(9), headView2);
        Iterator<Integer> headIterator = headView2.iterator();
        headIterator.next();
        headIterator.remove();
        assertIterablesEqual(this.newWith(6, 5, 3, 1), set);
        assertIterablesEqual(this.newWith(6, 5), headView);
        assertIterablesEqual(this.newWith(), headView2);

        SortedSet<Integer> tailView = set.tailSet(5);
        Iterator<Integer> tailIterator = tailView.iterator();
        tailIterator.next();
        tailIterator.remove();
        assertIterablesEqual(this.newWith(6, 3, 1), set);
        assertIterablesEqual(this.newWith(3, 1), tailView);
    }

    @Test
    default void SortedSet_subSet_subSet_addAll()
    {
        if (!this.allowsRemove())
        {
            return;
        }

        SortedSet<Integer> set = this.newWith(9, 7, 5, 3, 1);
        SortedSet<Integer> subset = set.subSet(9, 1);
        SortedSet<Integer> subset2 = subset.subSet(7, 3);
        assertIterablesEqual(this.newWith(9, 7, 5, 3), subset);
        assertIterablesEqual(this.newWith(7, 5), subset2);
        assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), set);

        subset2.addAll(this.newWith(6, 4));
        assertIterablesEqual(this.newWith(9, 7, 6, 5, 4, 3), subset);
        assertIterablesEqual(this.newWith(7, 6, 5, 4), subset2);
        assertIterablesEqual(this.newWith(9, 7, 6, 5, 4, 3, 1), set);

        subset2.clear();
        assertIterablesEqual(this.newWith(9, 3), subset);
        assertIterablesEqual(this.newWith(), subset2);
        assertIterablesEqual(this.newWith(9, 3, 1), set);

        SortedSet<Integer> headView = set.headSet(1);
        assertIterablesEqual(this.newWith(9, 3), headView);
        headView.addAll(this.newWith(8, 7));
        assertIterablesEqual(this.newWith(9, 8, 7, 3, 1), set);
        assertIterablesEqual(this.newWith(9, 8, 7, 3), headView);

        SortedSet<Integer> tailView = set.tailSet(7);
        assertIterablesEqual(this.newWith(7, 3, 1), tailView);
        tailView.addAll(this.newWith(6, 5));
        assertIterablesEqual(this.newWith(9, 8, 7, 6, 5, 3, 1), set);
        assertIterablesEqual(this.newWith(7, 6, 5, 3, 1), tailView);
    }

    @Test
    default void SortedSet_subSet_subSet_clear()
    {
        if (!this.allowsRemove())
        {
            return;
        }

        SortedSet<Integer> set = this.newWith(11, 9, 7, 5, 3, 1);
        SortedSet<Integer> subset = set.subSet(11, 5);
        SortedSet<Integer> subset2 = subset.subSet(9, 5);
        assertIterablesEqual(this.newWith(11, 9, 7), subset);
        assertIterablesEqual(this.newWith(9, 7), subset2);
        assertIterablesEqual(this.newWith(11, 9, 7, 5, 3, 1), set);

        subset2.clear();
        assertIterablesEqual(this.newWith(1, 3, 5, 11), set);
        assertIterablesEqual(this.newWith(11), subset);
        assertIterablesEqual(this.newWith(), subset2);

        subset2.add(8);
        assertIterablesEqual(this.newWith(11, 8, 5, 3, 1), set);
        assertIterablesEqual(this.newWith(11, 8), subset);
        assertIterablesEqual(this.newWith(8), subset2);

        SortedSet<Integer> headView = set.headSet(5);
        assertIterablesEqual(this.newWith(11, 8), headView);
        headView.clear();
        assertIterablesEqual(this.newWith(5, 3, 1), set);

        SortedSet<Integer> tailView = set.tailSet(3);
        assertIterablesEqual(this.newWith(3, 1), tailView);
        tailView.clear();
        assertIterablesEqual(this.newWith(5), set);
    }

    @Test
    default void SortedSet_first_last()
    {
        assertThrows(NoSuchElementException.class, () -> this.newWith().first());
        assertThrows(NoSuchElementException.class, () -> this.newWith().last());

        SortedSet<Integer> set1 = this.newWith(42);
        assertEquals(Integer.valueOf(42), set1.first());
        assertEquals(Integer.valueOf(42), set1.last());

        SortedSet<Integer> set2 = this.newWith(1, 2);
        assertEquals(Integer.valueOf(2), set2.first());
        assertEquals(Integer.valueOf(1), set2.last());

        SortedSet<Integer> set3 = this.newWith(1, 2, 3);
        assertEquals(Integer.valueOf(3), set3.first());
        assertEquals(Integer.valueOf(1), set3.last());
    }
}
