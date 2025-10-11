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

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;

import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.test.CollectionTestCase;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface SortedSetTestCase extends CollectionTestCase
{
    @Override
    <T> SortedSet<T> newWith(T... elements);

    @Override
    @Test
    default void Iterable_next()
    {
        SortedSet<Integer> set = this.newWith(3, 2, 1);
        Comparator<? super Integer> comparator = set.comparator();

        boolean isNaturalOrder = comparator == null;
        boolean isReverseOrder = comparator != null &&
                (comparator.equals(Collections.reverseOrder()) || comparator.equals(Comparators.reverseNaturalOrder()));

        assertTrue(isNaturalOrder || isReverseOrder,
                "Comparator must be either null (natural order) or reverse order");

        Iterator<Integer> iterator = set.iterator();
        if (isNaturalOrder)
        {
            assertTrue(iterator.hasNext());
            assertEquals(Integer.valueOf(1), iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals(Integer.valueOf(2), iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals(Integer.valueOf(3), iterator.next());
            assertFalse(iterator.hasNext());
        }
        else
        {
            assertTrue(iterator.hasNext());
            assertEquals(Integer.valueOf(3), iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals(Integer.valueOf(2), iterator.next());
            assertTrue(iterator.hasNext());
            assertEquals(Integer.valueOf(1), iterator.next());
            assertFalse(iterator.hasNext());
        }

        assertThrows(NoSuchElementException.class, iterator::next);

        SortedSet<Integer> singleElement = this.newWith(42);
        Iterator<Integer> singleIterator = singleElement.iterator();
        assertTrue(singleIterator.hasNext());
        assertEquals(Integer.valueOf(42), singleIterator.next());
        assertFalse(singleIterator.hasNext());
        assertThrows(NoSuchElementException.class, singleIterator::next);

        SortedSet<Integer> emptySet = this.newWith();
        Iterator<Integer> emptyIterator = emptySet.iterator();
        assertFalse(emptyIterator.hasNext());
        assertThrows(NoSuchElementException.class, emptyIterator::next);

        SortedSet<Integer> largeSet = this.newWith(5, 1, 9, 3, 7);
        Iterator<Integer> largeIterator = largeSet.iterator();
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(1), largeIterator.next());
            assertEquals(Integer.valueOf(3), largeIterator.next());
            assertEquals(Integer.valueOf(5), largeIterator.next());
            assertEquals(Integer.valueOf(7), largeIterator.next());
            assertEquals(Integer.valueOf(9), largeIterator.next());
        }
        else
        {
            assertEquals(Integer.valueOf(9), largeIterator.next());
            assertEquals(Integer.valueOf(7), largeIterator.next());
            assertEquals(Integer.valueOf(5), largeIterator.next());
            assertEquals(Integer.valueOf(3), largeIterator.next());
            assertEquals(Integer.valueOf(1), largeIterator.next());
        }
        assertFalse(largeIterator.hasNext());
    }

    @Override
    @Test
    default void Iterable_remove()
    {
        SortedSet<Integer> set = this.newWith(3, 2, 1);
        Comparator<? super Integer> comparator = set.comparator();

        boolean isNaturalOrder = comparator == null;
        boolean isReverseOrder = comparator != null &&
                (comparator.equals(Collections.reverseOrder()) || comparator.equals(Comparators.reverseNaturalOrder()));

        assertTrue(isNaturalOrder || isReverseOrder,
                "Comparator must be either null (natural order) or reverse order");

        Iterator<Integer> iterator = set.iterator();
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(1), iterator.next());
            iterator.remove();
            assertIterablesEqual(this.newWith(2, 3), set);
        }
        else
        {
            assertEquals(Integer.valueOf(3), iterator.next());
            iterator.remove();
            assertIterablesEqual(this.newWith(2, 1), set);
        }

        SortedSet<Integer> set2 = this.newWith(5, 3, 1, 4, 2);
        Iterator<Integer> iterator2 = set2.iterator();
        iterator2.next();
        iterator2.next();
        iterator2.remove();
        if (isNaturalOrder)
        {
            assertIterablesEqual(this.newWith(1, 3, 4, 5), set2);
        }
        else
        {
            assertIterablesEqual(this.newWith(5, 3, 2, 1), set2);
        }

        SortedSet<Integer> set3 = this.newWith(1, 2, 3, 4, 5);
        Iterator<Integer> iterator3 = set3.iterator();
        iterator3.next();
        iterator3.remove();
        iterator3.next();
        iterator3.remove();
        iterator3.next();
        iterator3.remove();
        if (isNaturalOrder)
        {
            assertIterablesEqual(this.newWith(4, 5), set3);
        }
        else
        {
            assertIterablesEqual(this.newWith(2, 1), set3);
        }

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

    @Override
    @Test
    default void Collection_add()
    {
        SortedSet<Integer> set = this.newWith(1, 2, 3);
        Comparator<? super Integer> comparator = set.comparator();

        boolean isNaturalOrder = comparator == null;
        boolean isReverseOrder = comparator != null &&
                (comparator.equals(Collections.reverseOrder()) || comparator.equals(Comparators.reverseNaturalOrder()));

        assertTrue(isNaturalOrder || isReverseOrder,
                "Comparator must be either null (natural order) or reverse order");

        assertFalse(set.add(3));
        assertFalse(set.add(2));
        assertFalse(set.add(1));
        assertIterablesEqual(this.newWith(1, 2, 3), set);

        assertTrue(set.add(4));
        assertIterablesEqual(this.newWith(1, 2, 3, 4), set);

        assertTrue(set.add(0));
        assertIterablesEqual(this.newWith(0, 1, 2, 3, 4), set);

        SortedSet<Integer> set2 = this.newWith();
        assertTrue(set2.add(5));
        if (isNaturalOrder)
        {
            assertIterablesEqual(this.newWith(5), set2);
        }
        else
        {
            assertIterablesEqual(this.newWith(5), set2);
        }

        assertTrue(set2.add(3));
        assertTrue(set2.add(7));
        if (isNaturalOrder)
        {
            assertIterablesEqual(this.newWith(3, 5, 7), set2);
        }
        else
        {
            assertIterablesEqual(this.newWith(7, 5, 3), set2);
        }

        assertFalse(set2.add(5));
        if (isNaturalOrder)
        {
            assertIterablesEqual(this.newWith(3, 5, 7), set2);
        }
        else
        {
            assertIterablesEqual(this.newWith(7, 5, 3), set2);
        }

        SortedSet<Integer> set3 = this.newWith(10, 20, 30);
        assertTrue(set3.add(15));
        assertTrue(set3.add(25));
        if (isNaturalOrder)
        {
            Iterator<Integer> iterator = set3.iterator();
            assertEquals(Integer.valueOf(10), iterator.next());
            assertEquals(Integer.valueOf(15), iterator.next());
            assertEquals(Integer.valueOf(20), iterator.next());
            assertEquals(Integer.valueOf(25), iterator.next());
            assertEquals(Integer.valueOf(30), iterator.next());
        }
        else
        {
            Iterator<Integer> iterator = set3.iterator();
            assertEquals(Integer.valueOf(30), iterator.next());
            assertEquals(Integer.valueOf(25), iterator.next());
            assertEquals(Integer.valueOf(20), iterator.next());
            assertEquals(Integer.valueOf(15), iterator.next());
            assertEquals(Integer.valueOf(10), iterator.next());
        }
    }

    @Override
    @Test
    default void Collection_size()
    {
        assertThat(this.newWith(3, 2, 1), hasSize(3));
        assertThat(this.newWith(), hasSize(0));
        assertThat(this.newWith(1), hasSize(1));
        assertThat(this.newWith(1, 2), hasSize(2));
        assertThat(this.newWith(5, 4, 3, 2, 1), hasSize(5));

        SortedSet<Integer> set = this.newWith(1, 2, 3);
        assertThat(set, hasSize(3));
        set.add(4);
        assertThat(set, hasSize(4));
        set.add(4);
        assertThat(set, hasSize(4));
        set.add(5);
        assertThat(set, hasSize(5));

        SortedSet<Integer> set2 = this.newWith(1, 2, 3, 4, 5);
        assertThat(set2, hasSize(5));
        set2.remove(3);
        assertThat(set2, hasSize(4));
        set2.remove(1);
        assertThat(set2, hasSize(3));
        set2.remove(5);
        assertThat(set2, hasSize(2));
        set2.remove(2);
        assertThat(set2, hasSize(1));
        set2.remove(4);
        assertThat(set2, hasSize(0));

        SortedSet<Integer> set3 = this.newWith();
        assertThat(set3, hasSize(0));
        set3.add(1);
        assertThat(set3, hasSize(1));
        set3.clear();
        assertThat(set3, hasSize(0));

        SortedSet<Integer> set4 = this.newWith(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
        assertThat(set4, hasSize(10));
        Iterator<Integer> iterator = set4.iterator();
        iterator.next();
        iterator.remove();
        assertThat(set4, hasSize(9));
        iterator.next();
        iterator.remove();
        assertThat(set4, hasSize(8));
    }

    @Test
    default void SortedSet_comparator()
    {
        SortedSet<Integer> set = this.newWith(3, 1, 2);
        Comparator<? super Integer> comparator = set.comparator();

        assertIterablesEqual(this.newWith(3, 2, 1), set);

        org.assertj.core.api.Assertions.assertThat(comparator)
                .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());
    }

    @Test
    default void SortedSet_subSet_headSet_tailSet()
    {
        SortedSet<Integer> set = this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Comparator<? super Integer> comparator = set.comparator();

        boolean isNaturalOrder = comparator == null;
        boolean isReverseOrder = comparator != null &&
                (comparator.equals(Collections.reverseOrder()) || comparator.equals(Comparators.reverseNaturalOrder()));

        assertTrue(isNaturalOrder || isReverseOrder,
                "Comparator must be either null (natural order) or reverse order");

        if (isNaturalOrder)
        {
            assertIterablesEqual(this.newWith(5, 6), set.subSet(5, 7));
            assertIterablesEqual(this.newWith(), set.subSet(5, 5));
            assertIterablesEqual(this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), set.subSet(1, 11));
            assertIterablesEqual(this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), set.subSet(0, 15));

            assertIterablesEqual(this.newWith(1), set.subSet(1, 2));
            assertIterablesEqual(this.newWith(10), set.subSet(10, 11));
            assertIterablesEqual(this.newWith(), set.subSet(0, 1));
            assertIterablesEqual(this.newWith(), set.subSet(11, 12));

            assertIterablesEqual(this.newWith(3, 4, 5), set.subSet(3, 6));
            assertIterablesEqual(this.newWith(6, 7, 8), set.subSet(6, 9));

            assertIterablesEqual(this.newWith(2, 3, 4, 5, 6, 7, 8), set.subSet(2, 9));

            assertIterablesEqual(this.newWith(1, 2, 3, 4), set.headSet(5));
            assertIterablesEqual(this.newWith(), set.headSet(1));
            assertIterablesEqual(this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), set.headSet(11));
            assertIterablesEqual(this.newWith(), set.headSet(0));

            assertIterablesEqual(this.newWith(7, 8, 9, 10), set.tailSet(7));
            assertIterablesEqual(this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), set.tailSet(1));
            assertIterablesEqual(this.newWith(), set.tailSet(11));
            assertIterablesEqual(this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), set.tailSet(0));

            SortedSet<Integer> subSetBetweenElements = set.subSet(2, 9);
            assertIterablesEqual(this.newWith(4, 5, 6), subSetBetweenElements.subSet(4, 7));
            assertIterablesEqual(this.newWith(2, 3, 4, 5), subSetBetweenElements.headSet(6));
            assertIterablesEqual(this.newWith(5, 6, 7, 8), subSetBetweenElements.tailSet(5));
        }
        else
        {
            assertIterablesEqual(this.newWith(7, 6), set.subSet(7, 5));
            assertIterablesEqual(this.newWith(), set.subSet(5, 5));
            assertIterablesEqual(this.newWith(10, 9, 8, 7, 6, 5, 4, 3, 2), set.subSet(11, 1));
            assertIterablesEqual(this.newWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1), set.subSet(15, 0));

            assertIterablesEqual(this.newWith(10), set.subSet(10, 9));
            assertIterablesEqual(this.newWith(2), set.subSet(2, 1));
            assertIterablesEqual(this.newWith(), set.subSet(12, 11));
            assertIterablesEqual(this.newWith(1), set.subSet(1, 0));

            assertIterablesEqual(this.newWith(6, 5, 4), set.subSet(6, 3));
            assertIterablesEqual(this.newWith(9, 8, 7), set.subSet(9, 6));

            assertIterablesEqual(this.newWith(9, 8, 7, 6, 5, 4, 3), set.subSet(9, 2));

            assertIterablesEqual(this.newWith(10, 9, 8, 7, 6), set.headSet(5));
            assertIterablesEqual(this.newWith(), set.headSet(11));
            assertIterablesEqual(this.newWith(10, 9, 8, 7, 6, 5, 4, 3, 2), set.headSet(1));
            assertIterablesEqual(this.newWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1), set.headSet(0));

            assertIterablesEqual(this.newWith(7, 6, 5, 4, 3, 2, 1), set.tailSet(7));
            assertIterablesEqual(this.newWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1), set.tailSet(10));
            assertIterablesEqual(this.newWith(), set.tailSet(0));
            assertIterablesEqual(this.newWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1), set.tailSet(15));

            SortedSet<Integer> subSetBetweenElements = set.subSet(9, 2);
            assertIterablesEqual(this.newWith(7, 6, 5), subSetBetweenElements.subSet(7, 4));
            assertIterablesEqual(this.newWith(9, 8, 7), subSetBetweenElements.headSet(6));
            assertIterablesEqual(this.newWith(5, 4, 3), subSetBetweenElements.tailSet(5));
        }

        SortedSet<Integer> largeSet = this.newWith(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);

        if (isNaturalOrder)
        {
            assertIterablesEqual(this.newWith(20, 30, 40, 50, 60, 70), largeSet.subSet(20, 80));
            assertIterablesEqual(this.newWith(30, 40, 50, 60), largeSet.subSet(25, 75));
            assertIterablesEqual(this.newWith(10, 20, 30, 40, 50), largeSet.headSet(55));
            assertIterablesEqual(this.newWith(60, 70, 80, 90, 100), largeSet.tailSet(55));
        }
        else
        {
            assertIterablesEqual(this.newWith(80, 70, 60, 50, 40, 30), largeSet.subSet(80, 20));
            assertIterablesEqual(this.newWith(70, 60, 50, 40, 30), largeSet.subSet(75, 25));
            assertIterablesEqual(this.newWith(100, 90, 80, 70, 60), largeSet.headSet(55));
            assertIterablesEqual(this.newWith(50, 40, 30, 20, 10), largeSet.tailSet(55));
        }
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
        if (set2.comparator() == null)
        {
            assertEquals(Integer.valueOf(1), set2.first());
            assertEquals(Integer.valueOf(2), set2.last());
        }
        else
        {
            assertEquals(Integer.valueOf(2), set2.first());
            assertEquals(Integer.valueOf(1), set2.last());
        }

        SortedSet<Integer> set3 = this.newWith(1, 2, 3);
        if (set3.comparator() == null)
        {
            assertEquals(Integer.valueOf(1), set3.first());
            assertEquals(Integer.valueOf(3), set3.last());
        }
        else
        {
            assertEquals(Integer.valueOf(3), set3.first());
            assertEquals(Integer.valueOf(1), set3.last());
        }
    }
}
