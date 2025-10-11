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

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
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
        Set<Integer> iterable = this.newWith(3, 2, 1);

        Iterator<Integer> iterator = iterable.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(3), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(2), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(1), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Override
    @Test
    default void Iterable_remove()
    {
        Iterable<Integer> iterable = this.newWith(3, 2, 1);
        Iterator<Integer> iterator = iterable.iterator();
        assertEquals(Integer.valueOf(3), iterator.next());
        iterator.remove();
        assertIterablesEqual(this.newWith(2, 1), iterable);
    }

    @Override
    @Test
    default void Collection_add()
    {
        Collection<Integer> collection = this.newWith(1, 2, 3);
        assertFalse(collection.add(3));
    }

    @Override
    @Test
    default void Collection_size()
    {
        assertThat(this.newWith(3, 2, 1), hasSize(3));
        assertThat(this.newWith(), hasSize(0));
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

        // TODO 2025-10-11: Refactor this to check whether we're using null, reverse order, or something else. Fail if something else.
        if (comparator == null)
        {
            // TODO 2025-10-11: refactor these to have specific assertions where the first index is before the beginning, at the beginning, middle, end, past end, and then the same thing for the end index. Also add tests where the first/end index are the same, and the end index is before the first index.

            SortedSet<Integer> subSet = set.subSet(3, 8);
            // TODO 2025-10-11: It's unnecessary to check the size if we're checking the contents
            assertEquals(5, subSet.size());
            assertIterablesEqual(this.newWith(3, 4, 5, 6, 7), subSet);

            SortedSet<Integer> headSet = set.headSet(5);
            assertEquals(4, headSet.size());
            assertIterablesEqual(this.newWith(1, 2, 3, 4), headSet);

            SortedSet<Integer> tailSet = set.tailSet(7);
            assertEquals(4, tailSet.size());
            assertIterablesEqual(this.newWith(7, 8, 9, 10), tailSet);

            SortedSet<Integer> emptySubSet = set.subSet(5, 5);
            assertEquals(0, emptySubSet.size());
            assertFalse(emptySubSet.iterator().hasNext());

            SortedSet<Integer> singleElementSubSet = set.subSet(5, 6);
            assertEquals(1, singleElementSubSet.size());
            assertIterablesEqual(this.newWith(5), singleElementSubSet);

            SortedSet<Integer> fullSubSet = set.subSet(1, 11);
            assertEquals(10, fullSubSet.size());
            assertIterablesEqual(set, fullSubSet);

            SortedSet<Integer> emptyHeadSet = set.headSet(1);
            assertEquals(0, emptyHeadSet.size());
            assertFalse(emptyHeadSet.iterator().hasNext());

            SortedSet<Integer> fullHeadSet = set.headSet(11);
            assertEquals(10, fullHeadSet.size());
            assertIterablesEqual(set, fullHeadSet);

            SortedSet<Integer> emptyTailSet = set.tailSet(11);
            assertEquals(0, emptyTailSet.size());
            assertFalse(emptyTailSet.iterator().hasNext());

            SortedSet<Integer> fullTailSet = set.tailSet(1);
            assertEquals(10, fullTailSet.size());
            assertIterablesEqual(set, fullTailSet);

            SortedSet<Integer> subSetWithNonExistentBounds = set.subSet(0, 15);
            assertEquals(10, subSetWithNonExistentBounds.size());
            assertIterablesEqual(set, subSetWithNonExistentBounds);

            SortedSet<Integer> headSetWithNonExistentBound = set.headSet(0);
            assertEquals(0, headSetWithNonExistentBound.size());

            SortedSet<Integer> tailSetWithNonExistentBound = set.tailSet(0);
            assertEquals(10, tailSetWithNonExistentBound.size());
            assertIterablesEqual(set, tailSetWithNonExistentBound);

            SortedSet<Integer> subSetBetweenElements = set.subSet(2, 9);
            assertEquals(7, subSetBetweenElements.size());
            assertIterablesEqual(this.newWith(2, 3, 4, 5, 6, 7, 8), subSetBetweenElements);

            SortedSet<Integer> nestedSubSet = subSetBetweenElements.subSet(4, 7);
            assertEquals(3, nestedSubSet.size());
            assertIterablesEqual(this.newWith(4, 5, 6), nestedSubSet);

            SortedSet<Integer> nestedHeadSet = subSetBetweenElements.headSet(6);
            assertEquals(4, nestedHeadSet.size());
            assertIterablesEqual(this.newWith(2, 3, 4, 5), nestedHeadSet);

            SortedSet<Integer> nestedTailSet = subSetBetweenElements.tailSet(5);
            assertEquals(4, nestedTailSet.size());
            assertIterablesEqual(this.newWith(5, 6, 7, 8), nestedTailSet);
        }
        else
        {
            // TODO 2025-10-11: refactor these to have specific assertions where the first index is before the beginning, at the beginning, middle, end, past end, and then the same thing for the end index. Also add tests where the first/end index are the same, and the end index is before the first index.

            SortedSet<Integer> subSet = set.subSet(8, 3);
            assertEquals(5, subSet.size());
            Iterator<Integer> subSetIterator = subSet.iterator();
            Integer previous = subSetIterator.next();
            while (subSetIterator.hasNext())
            {
                Integer current = subSetIterator.next();
                assertEquals(-1, Integer.signum(comparator.compare(previous, current)));
                previous = current;
            }

            SortedSet<Integer> headSet = set.headSet(5);
            assertEquals(5, headSet.size());
            Iterator<Integer> headSetIterator = headSet.iterator();
            previous = headSetIterator.next();
            while (headSetIterator.hasNext())
            {
                Integer current = headSetIterator.next();
                assertEquals(-1, Integer.signum(comparator.compare(previous, current)));
                previous = current;
            }

            SortedSet<Integer> tailSet = set.tailSet(7);
            assertEquals(7, tailSet.size());
            Iterator<Integer> tailSetIterator = tailSet.iterator();
            previous = tailSetIterator.next();
            while (tailSetIterator.hasNext())
            {
                Integer current = tailSetIterator.next();
                assertEquals(-1, Integer.signum(comparator.compare(previous, current)));
                previous = current;
            }

            SortedSet<Integer> emptySubSet = set.subSet(5, 5);
            assertEquals(0, emptySubSet.size());
            assertFalse(emptySubSet.iterator().hasNext());

            SortedSet<Integer> singleElementSubSet = set.subSet(6, 5);
            assertEquals(1, singleElementSubSet.size());

            SortedSet<Integer> fullSubSet = set.subSet(11, 0);
            assertEquals(10, fullSubSet.size());

            SortedSet<Integer> emptyHeadSet = set.headSet(11);
            assertEquals(0, emptyHeadSet.size());
            assertFalse(emptyHeadSet.iterator().hasNext());

            SortedSet<Integer> fullHeadSet = set.headSet(0);
            assertEquals(10, fullHeadSet.size());

            SortedSet<Integer> emptyTailSet = set.tailSet(-1);
            assertEquals(0, emptyTailSet.size());
            assertFalse(emptyTailSet.iterator().hasNext());

            SortedSet<Integer> fullTailSet = set.tailSet(11);
            assertEquals(10, fullTailSet.size());

            SortedSet<Integer> subSetWithNonExistentBounds = set.subSet(15, 0);
            assertEquals(10, subSetWithNonExistentBounds.size());

            SortedSet<Integer> headSetWithNonExistentBound = set.headSet(0);
            assertEquals(10, headSetWithNonExistentBound.size());

            SortedSet<Integer> tailSetWithNonExistentBound = set.tailSet(15);
            assertEquals(10, tailSetWithNonExistentBound.size());

            SortedSet<Integer> subSetBetweenElements = set.subSet(9, 2);
            assertEquals(7, subSetBetweenElements.size());

            SortedSet<Integer> nestedSubSet = subSetBetweenElements.subSet(7, 4);
            assertEquals(3, nestedSubSet.size());

            SortedSet<Integer> nestedHeadSet = subSetBetweenElements.headSet(6);
            assertEquals(3, nestedHeadSet.size());

            SortedSet<Integer> nestedTailSet = subSetBetweenElements.tailSet(5);
            assertEquals(3, nestedTailSet.size());
        }

        // TODO 2025-10-11: no need to check for strings and integers separately. Pick one and do it well. Just integers
        SortedSet<String> stringSet = this.newWith("alice", "bob", "charlie", "diana", "eve");
        Comparator<? super String> stringComparator = stringSet.comparator();

        if (stringComparator == null)
        {
            SortedSet<String> stringSubSet = stringSet.subSet("bob", "eve");
            assertEquals(3, stringSubSet.size());
            assertIterablesEqual(this.newWith("bob", "charlie", "diana"), stringSubSet);

            SortedSet<String> stringHeadSet = stringSet.headSet("charlie");
            assertEquals(2, stringHeadSet.size());
            assertIterablesEqual(this.newWith("alice", "bob"), stringHeadSet);

            SortedSet<String> stringTailSet = stringSet.tailSet("charlie");
            assertEquals(3, stringTailSet.size());
            assertIterablesEqual(this.newWith("charlie", "diana", "eve"), stringTailSet);
        }
        else
        {
            SortedSet<String> stringSubSet = stringSet.subSet("eve", "bob");
            assertEquals(3, stringSubSet.size());
            Iterator<String> stringSubSetIterator = stringSubSet.iterator();
            String previous = stringSubSetIterator.next();
            while (stringSubSetIterator.hasNext())
            {
                String current = stringSubSetIterator.next();
                assertEquals(-1, Integer.signum(stringComparator.compare(previous, current)));
                previous = current;
            }

            SortedSet<String> stringHeadSet = stringSet.headSet("charlie");
            assertEquals(2, stringHeadSet.size());

            SortedSet<String> stringTailSet = stringSet.tailSet("charlie");
            assertEquals(3, stringTailSet.size());
        }

        SortedSet<Integer> largeSet = this.newWith(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);

        if (comparator == null)
        {
            SortedSet<Integer> largeSubSet1 = largeSet.subSet(20, 80);
            assertEquals(6, largeSubSet1.size());
            assertIterablesEqual(this.newWith(20, 30, 40, 50, 60, 70), largeSubSet1);

            SortedSet<Integer> largeSubSet2 = largeSet.subSet(25, 75);
            assertEquals(4, largeSubSet2.size());
            assertIterablesEqual(this.newWith(30, 40, 50, 60), largeSubSet2);

            SortedSet<Integer> largeHeadSet = largeSet.headSet(55);
            assertEquals(5, largeHeadSet.size());
            assertIterablesEqual(this.newWith(10, 20, 30, 40, 50), largeHeadSet);

            SortedSet<Integer> largeTailSet = largeSet.tailSet(55);
            assertEquals(5, largeTailSet.size());
            assertIterablesEqual(this.newWith(60, 70, 80, 90, 100), largeTailSet);
        }
        else
        {
            SortedSet<Integer> largeSubSet1 = largeSet.subSet(80, 20);
            assertEquals(6, largeSubSet1.size());

            SortedSet<Integer> largeSubSet2 = largeSet.subSet(75, 25);
            assertEquals(5, largeSubSet2.size());

            SortedSet<Integer> largeHeadSet = largeSet.headSet(55);
            assertEquals(5, largeHeadSet.size());

            SortedSet<Integer> largeTailSet = largeSet.tailSet(55);
            assertEquals(5, largeTailSet.size());
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
