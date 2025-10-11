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
import java.util.NavigableSet;

import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface NavigableSetTestCase extends SortedSetTestCase
{
    @Override
    <T> NavigableSet<T> newWith(T... elements);

    @Test
    default void NavigableSet_lower_floor_ceiling_higher()
    {
        NavigableSet<Integer> emptySet = this.newWith();
        assertNull(emptySet.lower(5));
        assertNull(emptySet.floor(5));
        assertNull(emptySet.ceiling(5));
        assertNull(emptySet.higher(5));

        NavigableSet<Integer> singletonSet = this.newWith(5);
        Comparator<? super Integer> singletonComparator = singletonSet.comparator();

        if (singletonComparator == null)
        {
            assertNull(singletonSet.lower(5));
            assertEquals(Integer.valueOf(5), singletonSet.floor(5));
            assertEquals(Integer.valueOf(5), singletonSet.ceiling(5));
            assertNull(singletonSet.higher(5));

            assertNull(singletonSet.lower(4));
            assertNull(singletonSet.floor(4));
            assertEquals(Integer.valueOf(5), singletonSet.ceiling(4));
            assertEquals(Integer.valueOf(5), singletonSet.higher(4));

            assertEquals(Integer.valueOf(5), singletonSet.lower(6));
            assertEquals(Integer.valueOf(5), singletonSet.floor(6));
            assertNull(singletonSet.ceiling(6));
            assertNull(singletonSet.higher(6));
        }
        else
        {
            assertNull(singletonSet.lower(5));
            assertEquals(Integer.valueOf(5), singletonSet.floor(5));
            assertEquals(Integer.valueOf(5), singletonSet.ceiling(5));
            assertNull(singletonSet.higher(5));

            assertEquals(Integer.valueOf(5), singletonSet.lower(4));
            assertEquals(Integer.valueOf(5), singletonSet.floor(4));
            assertNull(singletonSet.ceiling(4));
            assertNull(singletonSet.higher(4));

            assertNull(singletonSet.lower(6));
            assertNull(singletonSet.floor(6));
            assertEquals(Integer.valueOf(5), singletonSet.ceiling(6));
            assertEquals(Integer.valueOf(5), singletonSet.higher(6));
        }

        NavigableSet<Integer> set = this.newWith(1, 3, 5, 7, 9);
        Comparator<? super Integer> comparator = set.comparator();
        assertEquals(5, set.size());

        if (comparator == null)
        {
            assertIterablesEqual(this.newWith(1, 3, 5, 7, 9), set);

            assertNull(set.lower(1));
            assertEquals(Integer.valueOf(1), set.floor(1));
            assertEquals(Integer.valueOf(1), set.ceiling(1));
            assertEquals(Integer.valueOf(3), set.higher(1));

            assertNull(set.lower(0));
            assertNull(set.floor(0));
            assertEquals(Integer.valueOf(1), set.ceiling(0));
            assertEquals(Integer.valueOf(1), set.higher(0));

            assertEquals(Integer.valueOf(1), set.lower(2));
            assertEquals(Integer.valueOf(1), set.floor(2));
            assertEquals(Integer.valueOf(3), set.ceiling(2));
            assertEquals(Integer.valueOf(3), set.higher(2));

            assertEquals(Integer.valueOf(1), set.lower(3));
            assertEquals(Integer.valueOf(3), set.floor(3));
            assertEquals(Integer.valueOf(3), set.ceiling(3));
            assertEquals(Integer.valueOf(5), set.higher(3));

            assertEquals(Integer.valueOf(3), set.lower(4));
            assertEquals(Integer.valueOf(3), set.floor(4));
            assertEquals(Integer.valueOf(5), set.ceiling(4));
            assertEquals(Integer.valueOf(5), set.higher(4));

            assertEquals(Integer.valueOf(3), set.lower(5));
            assertEquals(Integer.valueOf(5), set.floor(5));
            assertEquals(Integer.valueOf(5), set.ceiling(5));
            assertEquals(Integer.valueOf(7), set.higher(5));

            assertEquals(Integer.valueOf(5), set.lower(6));
            assertEquals(Integer.valueOf(5), set.floor(6));
            assertEquals(Integer.valueOf(7), set.ceiling(6));
            assertEquals(Integer.valueOf(7), set.higher(6));

            assertEquals(Integer.valueOf(5), set.lower(7));
            assertEquals(Integer.valueOf(7), set.floor(7));
            assertEquals(Integer.valueOf(7), set.ceiling(7));
            assertEquals(Integer.valueOf(9), set.higher(7));

            assertEquals(Integer.valueOf(7), set.lower(8));
            assertEquals(Integer.valueOf(7), set.floor(8));
            assertEquals(Integer.valueOf(9), set.ceiling(8));
            assertEquals(Integer.valueOf(9), set.higher(8));

            assertEquals(Integer.valueOf(7), set.lower(9));
            assertEquals(Integer.valueOf(9), set.floor(9));
            assertEquals(Integer.valueOf(9), set.ceiling(9));
            assertNull(set.higher(9));

            assertEquals(Integer.valueOf(9), set.lower(10));
            assertEquals(Integer.valueOf(9), set.floor(10));
            assertNull(set.ceiling(10));
            assertNull(set.higher(10));

            assertEquals(Integer.valueOf(9), set.lower(100));
            assertEquals(Integer.valueOf(9), set.floor(100));
            assertNull(set.ceiling(100));
            assertNull(set.higher(100));
        }
        else
        {
            Iterator<Integer> iterator = set.iterator();
            Integer first = iterator.next();
            Integer second = iterator.next();
            Integer third = iterator.next();
            Integer fourth = iterator.next();
            Integer fifth = iterator.next();

            assertEquals(-1, Integer.signum(comparator.compare(first, second)));
            assertEquals(-1, Integer.signum(comparator.compare(second, third)));
            assertEquals(-1, Integer.signum(comparator.compare(third, fourth)));
            assertEquals(-1, Integer.signum(comparator.compare(fourth, fifth)));

            assertNull(set.lower(first));
            assertEquals(first, set.floor(first));
            assertEquals(first, set.ceiling(first));
            assertEquals(second, set.higher(first));

            assertEquals(first, set.lower(second));
            assertEquals(second, set.floor(second));
            assertEquals(second, set.ceiling(second));
            assertEquals(third, set.higher(second));

            assertEquals(second, set.lower(third));
            assertEquals(third, set.floor(third));
            assertEquals(third, set.ceiling(third));
            assertEquals(fourth, set.higher(third));

            assertEquals(third, set.lower(fourth));
            assertEquals(fourth, set.floor(fourth));
            assertEquals(fourth, set.ceiling(fourth));
            assertEquals(fifth, set.higher(fourth));

            assertEquals(fourth, set.lower(fifth));
            assertEquals(fifth, set.floor(fifth));
            assertEquals(fifth, set.ceiling(fifth));
            assertNull(set.higher(fifth));
        }

        NavigableSet<Integer> twoElementSet = this.newWith(10, 20);
        Comparator<? super Integer> twoElementComparator = twoElementSet.comparator();

        if (twoElementComparator == null)
        {
            assertNull(twoElementSet.lower(10));
            assertEquals(Integer.valueOf(10), twoElementSet.floor(10));
            assertEquals(Integer.valueOf(10), twoElementSet.ceiling(10));
            assertEquals(Integer.valueOf(20), twoElementSet.higher(10));

            assertNull(twoElementSet.lower(5));
            assertNull(twoElementSet.floor(5));
            assertEquals(Integer.valueOf(10), twoElementSet.ceiling(5));
            assertEquals(Integer.valueOf(10), twoElementSet.higher(5));

            assertEquals(Integer.valueOf(10), twoElementSet.lower(15));
            assertEquals(Integer.valueOf(10), twoElementSet.floor(15));
            assertEquals(Integer.valueOf(20), twoElementSet.ceiling(15));
            assertEquals(Integer.valueOf(20), twoElementSet.higher(15));

            assertEquals(Integer.valueOf(10), twoElementSet.lower(20));
            assertEquals(Integer.valueOf(20), twoElementSet.floor(20));
            assertEquals(Integer.valueOf(20), twoElementSet.ceiling(20));
            assertNull(twoElementSet.higher(20));

            assertEquals(Integer.valueOf(20), twoElementSet.lower(25));
            assertEquals(Integer.valueOf(20), twoElementSet.floor(25));
            assertNull(twoElementSet.ceiling(25));
            assertNull(twoElementSet.higher(25));
        }
        else
        {
            assertEquals(Integer.valueOf(10), twoElementSet.lower(20));
            assertEquals(Integer.valueOf(20), twoElementSet.floor(20));
            assertEquals(Integer.valueOf(20), twoElementSet.ceiling(20));
            assertNull(twoElementSet.higher(20));

            assertEquals(Integer.valueOf(20), twoElementSet.lower(25));
            assertEquals(Integer.valueOf(20), twoElementSet.floor(25));
            assertNull(twoElementSet.ceiling(25));
            assertNull(twoElementSet.higher(25));

            assertEquals(Integer.valueOf(20), twoElementSet.lower(15));
            assertEquals(Integer.valueOf(20), twoElementSet.floor(15));
            assertEquals(Integer.valueOf(10), twoElementSet.ceiling(15));
            assertEquals(Integer.valueOf(10), twoElementSet.higher(15));

            assertNull(twoElementSet.lower(10));
            assertEquals(Integer.valueOf(10), twoElementSet.floor(10));
            assertEquals(Integer.valueOf(10), twoElementSet.ceiling(10));
            assertNull(twoElementSet.higher(10));

            assertNull(twoElementSet.lower(5));
            assertNull(twoElementSet.floor(5));
            assertEquals(Integer.valueOf(20), twoElementSet.ceiling(5));
            assertEquals(Integer.valueOf(20), twoElementSet.higher(5));
        }

        NavigableSet<Integer> largeSet = this.newWith(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
        Comparator<? super Integer> largeComparator = largeSet.comparator();
        assertEquals(10, largeSet.size());

        if (largeComparator == null)
        {
            assertNull(largeSet.lower(10));
            assertEquals(Integer.valueOf(10), largeSet.floor(10));
            assertEquals(Integer.valueOf(10), largeSet.ceiling(10));
            assertEquals(Integer.valueOf(20), largeSet.higher(10));

            assertNull(largeSet.lower(5));
            assertNull(largeSet.floor(5));
            assertEquals(Integer.valueOf(10), largeSet.ceiling(5));
            assertEquals(Integer.valueOf(10), largeSet.higher(5));

            assertEquals(Integer.valueOf(40), largeSet.lower(50));
            assertEquals(Integer.valueOf(50), largeSet.floor(50));
            assertEquals(Integer.valueOf(50), largeSet.ceiling(50));
            assertEquals(Integer.valueOf(60), largeSet.higher(50));

            assertEquals(Integer.valueOf(50), largeSet.lower(55));
            assertEquals(Integer.valueOf(50), largeSet.floor(55));
            assertEquals(Integer.valueOf(60), largeSet.ceiling(55));
            assertEquals(Integer.valueOf(60), largeSet.higher(55));

            assertEquals(Integer.valueOf(90), largeSet.lower(100));
            assertEquals(Integer.valueOf(100), largeSet.floor(100));
            assertEquals(Integer.valueOf(100), largeSet.ceiling(100));
            assertNull(largeSet.higher(100));

            assertEquals(Integer.valueOf(100), largeSet.lower(105));
            assertEquals(Integer.valueOf(100), largeSet.floor(105));
            assertNull(largeSet.ceiling(105));
            assertNull(largeSet.higher(105));
        }
        else
        {
            Iterator<Integer> largeIterator = largeSet.iterator();
            Integer firstElement = largeIterator.next();

            assertNull(largeSet.lower(firstElement));
            assertEquals(firstElement, largeSet.floor(firstElement));
            assertEquals(firstElement, largeSet.ceiling(firstElement));

            Integer secondElement = largeIterator.next();
            assertEquals(firstElement, largeSet.lower(secondElement));
            assertEquals(secondElement, largeSet.floor(secondElement));
            assertEquals(secondElement, largeSet.ceiling(secondElement));
            assertEquals(-1, Integer.signum(largeComparator.compare(largeSet.lower(secondElement), secondElement)));

            Integer lastElement = null;
            while (largeIterator.hasNext())
            {
                lastElement = largeIterator.next();
            }

            assertEquals(lastElement, largeSet.floor(lastElement));
            assertEquals(lastElement, largeSet.ceiling(lastElement));
            assertNull(largeSet.higher(lastElement));
        }

        NavigableSet<String> stringSet = this.newWith("apple", "banana", "cherry", "date", "elderberry");
        Comparator<? super String> stringComparator = stringSet.comparator();
        assertEquals(5, stringSet.size());

        if (stringComparator == null)
        {
            assertNull(stringSet.lower("apple"));
            assertEquals("apple", stringSet.floor("apple"));
            assertEquals("apple", stringSet.ceiling("apple"));
            assertEquals("banana", stringSet.higher("apple"));

            assertNull(stringSet.lower("aardvark"));
            assertNull(stringSet.floor("aardvark"));
            assertEquals("apple", stringSet.ceiling("aardvark"));
            assertEquals("apple", stringSet.higher("aardvark"));

            assertEquals("banana", stringSet.lower("cherry"));
            assertEquals("cherry", stringSet.floor("cherry"));
            assertEquals("cherry", stringSet.ceiling("cherry"));
            assertEquals("date", stringSet.higher("cherry"));

            assertEquals("banana", stringSet.lower("blueberry"));
            assertEquals("banana", stringSet.floor("blueberry"));
            assertEquals("cherry", stringSet.ceiling("blueberry"));
            assertEquals("cherry", stringSet.higher("blueberry"));

            assertEquals("date", stringSet.lower("elderberry"));
            assertEquals("elderberry", stringSet.floor("elderberry"));
            assertEquals("elderberry", stringSet.ceiling("elderberry"));
            assertNull(stringSet.higher("elderberry"));

            assertEquals("elderberry", stringSet.lower("fig"));
            assertEquals("elderberry", stringSet.floor("fig"));
            assertNull(stringSet.ceiling("fig"));
            assertNull(stringSet.higher("fig"));
        }
        else
        {
            Iterator<String> stringIterator = stringSet.iterator();
            String firstString = stringIterator.next();
            String secondString = stringIterator.next();
            String thirdString = stringIterator.next();
            String fourthString = stringIterator.next();
            String fifthString = stringIterator.next();

            assertEquals(-1, Integer.signum(stringComparator.compare(firstString, secondString)));
            assertEquals(-1, Integer.signum(stringComparator.compare(secondString, thirdString)));
            assertEquals(-1, Integer.signum(stringComparator.compare(thirdString, fourthString)));
            assertEquals(-1, Integer.signum(stringComparator.compare(fourthString, fifthString)));

            assertNull(stringSet.lower(firstString));
            assertEquals(firstString, stringSet.floor(firstString));
            assertEquals(firstString, stringSet.ceiling(firstString));
            assertEquals(secondString, stringSet.higher(firstString));

            assertEquals(firstString, stringSet.lower(secondString));
            assertEquals(secondString, stringSet.floor(secondString));
            assertEquals(secondString, stringSet.ceiling(secondString));
            assertEquals(thirdString, stringSet.higher(secondString));

            assertEquals(secondString, stringSet.lower(thirdString));
            assertEquals(thirdString, stringSet.floor(thirdString));
            assertEquals(thirdString, stringSet.ceiling(thirdString));
            assertEquals(fourthString, stringSet.higher(thirdString));

            assertEquals(thirdString, stringSet.lower(fourthString));
            assertEquals(fourthString, stringSet.floor(fourthString));
            assertEquals(fourthString, stringSet.ceiling(fourthString));
            assertEquals(fifthString, stringSet.higher(fourthString));

            assertEquals(fourthString, stringSet.lower(fifthString));
            assertEquals(fifthString, stringSet.floor(fifthString));
            assertEquals(fifthString, stringSet.ceiling(fifthString));
            assertNull(stringSet.higher(fifthString));
        }

        NavigableSet<Integer> negativeSet = this.newWith(-50, -30, -10, 0, 10, 30, 50);
        Comparator<? super Integer> negativeComparator = negativeSet.comparator();
        assertEquals(7, negativeSet.size());

        if (negativeComparator == null)
        {
            assertNull(negativeSet.lower(-50));
            assertEquals(Integer.valueOf(-50), negativeSet.floor(-50));
            assertEquals(Integer.valueOf(-50), negativeSet.ceiling(-50));
            assertEquals(Integer.valueOf(-30), negativeSet.higher(-50));

            assertEquals(Integer.valueOf(-50), negativeSet.lower(-40));
            assertEquals(Integer.valueOf(-50), negativeSet.floor(-40));
            assertEquals(Integer.valueOf(-30), negativeSet.ceiling(-40));
            assertEquals(Integer.valueOf(-30), negativeSet.higher(-40));

            assertEquals(Integer.valueOf(-10), negativeSet.lower(0));
            assertEquals(Integer.valueOf(0), negativeSet.floor(0));
            assertEquals(Integer.valueOf(0), negativeSet.ceiling(0));
            assertEquals(Integer.valueOf(10), negativeSet.higher(0));

            assertEquals(Integer.valueOf(0), negativeSet.lower(5));
            assertEquals(Integer.valueOf(0), negativeSet.floor(5));
            assertEquals(Integer.valueOf(10), negativeSet.ceiling(5));
            assertEquals(Integer.valueOf(10), negativeSet.higher(5));

            assertEquals(Integer.valueOf(30), negativeSet.lower(50));
            assertEquals(Integer.valueOf(50), negativeSet.floor(50));
            assertEquals(Integer.valueOf(50), negativeSet.ceiling(50));
            assertNull(negativeSet.higher(50));

            assertEquals(Integer.valueOf(50), negativeSet.lower(60));
            assertEquals(Integer.valueOf(50), negativeSet.floor(60));
            assertNull(negativeSet.ceiling(60));
            assertNull(negativeSet.higher(60));

            assertNull(negativeSet.lower(-100));
            assertNull(negativeSet.floor(-100));
            assertEquals(Integer.valueOf(-50), negativeSet.ceiling(-100));
            assertEquals(Integer.valueOf(-50), negativeSet.higher(-100));
        }
        else
        {
            Iterator<Integer> negativeIterator = negativeSet.iterator();
            Integer firstNegative = negativeIterator.next();

            assertNull(negativeSet.lower(firstNegative));
            assertEquals(firstNegative, negativeSet.floor(firstNegative));
            assertEquals(firstNegative, negativeSet.ceiling(firstNegative));

            Integer secondNegative = negativeIterator.next();
            assertEquals(firstNegative, negativeSet.lower(secondNegative));
            assertEquals(secondNegative, negativeSet.floor(secondNegative));

            Integer lastNegative = null;
            while (negativeIterator.hasNext())
            {
                lastNegative = negativeIterator.next();
            }

            assertEquals(lastNegative, negativeSet.floor(lastNegative));
            assertEquals(lastNegative, negativeSet.ceiling(lastNegative));
            assertNull(negativeSet.higher(lastNegative));
        }
    }

    @Test
    default void NavigableSet_pollFirst_pollLast()
    {
        NavigableSet<Integer> emptySet = this.newWith();
        assertNull(emptySet.pollFirst());
        assertNull(emptySet.pollLast());
        assertEquals(0, emptySet.size());

        NavigableSet<Integer> singletonSet = this.newWith(5);
        Comparator<? super Integer> singletonComparator = singletonSet.comparator();
        assertEquals(1, singletonSet.size());

        if (singletonComparator == null)
        {
            assertEquals(Integer.valueOf(5), singletonSet.pollFirst());
            assertEquals(0, singletonSet.size());
            assertFalse(singletonSet.contains(5));
            assertNull(singletonSet.pollFirst());
            assertNull(singletonSet.pollLast());
        }
        else
        {
            assertEquals(Integer.valueOf(5), singletonSet.pollLast());
            assertEquals(0, singletonSet.size());
            assertFalse(singletonSet.contains(5));
            assertNull(singletonSet.pollFirst());
            assertNull(singletonSet.pollLast());
        }

        NavigableSet<Integer> twoElementSet = this.newWith(10, 20);
        Comparator<? super Integer> twoElementComparator = twoElementSet.comparator();
        assertEquals(2, twoElementSet.size());

        if (twoElementComparator == null)
        {
            assertEquals(Integer.valueOf(10), twoElementSet.pollFirst());
            assertEquals(1, twoElementSet.size());
            assertFalse(twoElementSet.contains(10));
            assertTrue(twoElementSet.contains(20));
            assertIterablesEqual(this.newWith(20), twoElementSet);

            assertEquals(Integer.valueOf(20), twoElementSet.pollLast());
            assertEquals(0, twoElementSet.size());
            assertFalse(twoElementSet.contains(20));
            assertNull(twoElementSet.pollFirst());
            assertNull(twoElementSet.pollLast());
        }
        else
        {
            assertEquals(Integer.valueOf(20), twoElementSet.pollFirst());
            assertEquals(1, twoElementSet.size());
            assertFalse(twoElementSet.contains(20));
            assertTrue(twoElementSet.contains(10));
            assertIterablesEqual(this.newWith(10), twoElementSet);

            assertEquals(Integer.valueOf(10), twoElementSet.pollLast());
            assertEquals(0, twoElementSet.size());
            assertFalse(twoElementSet.contains(10));
            assertNull(twoElementSet.pollFirst());
            assertNull(twoElementSet.pollLast());
        }

        NavigableSet<Integer> set = this.newWith(1, 3, 5, 7, 9);
        Comparator<? super Integer> comparator = set.comparator();
        assertEquals(5, set.size());

        if (comparator == null)
        {
            assertIterablesEqual(this.newWith(1, 3, 5, 7, 9), set);

            assertEquals(Integer.valueOf(1), set.pollFirst());
            assertEquals(4, set.size());
            assertFalse(set.contains(1));
            assertIterablesEqual(this.newWith(3, 5, 7, 9), set);

            assertEquals(Integer.valueOf(9), set.pollLast());
            assertEquals(3, set.size());
            assertFalse(set.contains(9));
            assertIterablesEqual(this.newWith(3, 5, 7), set);

            assertEquals(Integer.valueOf(3), set.pollFirst());
            assertEquals(2, set.size());
            assertFalse(set.contains(3));
            assertIterablesEqual(this.newWith(5, 7), set);

            assertEquals(Integer.valueOf(7), set.pollLast());
            assertEquals(1, set.size());
            assertFalse(set.contains(7));
            assertIterablesEqual(this.newWith(5), set);

            assertEquals(Integer.valueOf(5), set.pollFirst());
            assertEquals(0, set.size());
            assertFalse(set.contains(5));
            assertNull(set.pollFirst());
            assertNull(set.pollLast());
        }
        else
        {
            Iterator<Integer> iterator = set.iterator();
            Integer first = iterator.next();
            Integer second = iterator.next();
            Integer third = iterator.next();
            Integer fourth = iterator.next();
            Integer fifth = iterator.next();

            assertEquals(-1, Integer.signum(comparator.compare(first, second)));
            assertEquals(-1, Integer.signum(comparator.compare(second, third)));
            assertEquals(-1, Integer.signum(comparator.compare(third, fourth)));
            assertEquals(-1, Integer.signum(comparator.compare(fourth, fifth)));

            assertEquals(first, set.pollFirst());
            assertEquals(4, set.size());
            assertFalse(set.contains(first));

            assertEquals(fifth, set.pollLast());
            assertEquals(3, set.size());
            assertFalse(set.contains(fifth));

            assertEquals(second, set.pollFirst());
            assertEquals(2, set.size());
            assertFalse(set.contains(second));

            assertEquals(fourth, set.pollLast());
            assertEquals(1, set.size());
            assertFalse(set.contains(fourth));

            assertEquals(third, set.pollFirst());
            assertEquals(0, set.size());
            assertFalse(set.contains(third));

            assertNull(set.pollFirst());
            assertNull(set.pollLast());
        }

        NavigableSet<Integer> largeSet = this.newWith(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
        Comparator<? super Integer> largeComparator = largeSet.comparator();
        assertEquals(10, largeSet.size());

        if (largeComparator == null)
        {
            assertIterablesEqual(this.newWith(10, 20, 30, 40, 50, 60, 70, 80, 90, 100), largeSet);

            assertEquals(Integer.valueOf(10), largeSet.pollFirst());
            assertEquals(9, largeSet.size());
            assertFalse(largeSet.contains(10));
            assertIterablesEqual(this.newWith(20, 30, 40, 50, 60, 70, 80, 90, 100), largeSet);

            assertEquals(Integer.valueOf(100), largeSet.pollLast());
            assertEquals(8, largeSet.size());
            assertFalse(largeSet.contains(100));
            assertIterablesEqual(this.newWith(20, 30, 40, 50, 60, 70, 80, 90), largeSet);

            assertEquals(Integer.valueOf(20), largeSet.pollFirst());
            assertEquals(7, largeSet.size());
            assertFalse(largeSet.contains(20));
            assertIterablesEqual(this.newWith(30, 40, 50, 60, 70, 80, 90), largeSet);

            assertEquals(Integer.valueOf(90), largeSet.pollLast());
            assertEquals(6, largeSet.size());
            assertFalse(largeSet.contains(90));
            assertIterablesEqual(this.newWith(30, 40, 50, 60, 70, 80), largeSet);

            assertEquals(Integer.valueOf(30), largeSet.pollFirst());
            assertEquals(5, largeSet.size());
            assertIterablesEqual(this.newWith(40, 50, 60, 70, 80), largeSet);

            assertEquals(Integer.valueOf(80), largeSet.pollLast());
            assertEquals(4, largeSet.size());
            assertIterablesEqual(this.newWith(40, 50, 60, 70), largeSet);

            assertEquals(Integer.valueOf(40), largeSet.pollFirst());
            assertEquals(3, largeSet.size());
            assertIterablesEqual(this.newWith(50, 60, 70), largeSet);

            assertEquals(Integer.valueOf(70), largeSet.pollLast());
            assertEquals(2, largeSet.size());
            assertIterablesEqual(this.newWith(50, 60), largeSet);

            assertEquals(Integer.valueOf(50), largeSet.pollFirst());
            assertEquals(1, largeSet.size());
            assertIterablesEqual(this.newWith(60), largeSet);

            assertEquals(Integer.valueOf(60), largeSet.pollLast());
            assertEquals(0, largeSet.size());

            assertNull(largeSet.pollFirst());
            assertNull(largeSet.pollLast());
        }
        else
        {
            Iterator<Integer> largeIterator = largeSet.iterator();
            Integer firstElement = largeIterator.next();
            Integer secondElement = largeIterator.next();
            Integer thirdElement = largeIterator.next();
            Integer fourthElement = largeIterator.next();
            Integer fifthElement = largeIterator.next();
            Integer sixthElement = largeIterator.next();
            Integer seventhElement = largeIterator.next();
            Integer eighthElement = largeIterator.next();
            Integer ninthElement = largeIterator.next();
            Integer tenthElement = largeIterator.next();

            assertEquals(-1, Integer.signum(largeComparator.compare(firstElement, secondElement)));
            assertEquals(-1, Integer.signum(largeComparator.compare(secondElement, thirdElement)));

            assertEquals(firstElement, largeSet.pollFirst());
            assertEquals(9, largeSet.size());
            assertFalse(largeSet.contains(firstElement));

            assertEquals(tenthElement, largeSet.pollLast());
            assertEquals(8, largeSet.size());
            assertFalse(largeSet.contains(tenthElement));

            assertEquals(secondElement, largeSet.pollFirst());
            assertEquals(7, largeSet.size());
            assertFalse(largeSet.contains(secondElement));

            assertEquals(ninthElement, largeSet.pollLast());
            assertEquals(6, largeSet.size());
            assertFalse(largeSet.contains(ninthElement));

            assertEquals(thirdElement, largeSet.pollFirst());
            assertEquals(5, largeSet.size());
            assertFalse(largeSet.contains(thirdElement));

            assertEquals(eighthElement, largeSet.pollLast());
            assertEquals(4, largeSet.size());
            assertFalse(largeSet.contains(eighthElement));

            assertEquals(fourthElement, largeSet.pollFirst());
            assertEquals(3, largeSet.size());
            assertFalse(largeSet.contains(fourthElement));

            assertEquals(seventhElement, largeSet.pollLast());
            assertEquals(2, largeSet.size());
            assertFalse(largeSet.contains(seventhElement));

            assertEquals(fifthElement, largeSet.pollFirst());
            assertEquals(1, largeSet.size());
            assertFalse(largeSet.contains(fifthElement));

            assertEquals(sixthElement, largeSet.pollLast());
            assertEquals(0, largeSet.size());
            assertFalse(largeSet.contains(sixthElement));

            assertNull(largeSet.pollFirst());
            assertNull(largeSet.pollLast());
        }

        NavigableSet<String> stringSet = this.newWith("apple", "banana", "cherry", "date", "elderberry");
        Comparator<? super String> stringComparator = stringSet.comparator();
        assertEquals(5, stringSet.size());

        if (stringComparator == null)
        {
            assertIterablesEqual(this.newWith("apple", "banana", "cherry", "date", "elderberry"), stringSet);

            assertEquals("apple", stringSet.pollFirst());
            assertEquals(4, stringSet.size());
            assertFalse(stringSet.contains("apple"));
            assertIterablesEqual(this.newWith("banana", "cherry", "date", "elderberry"), stringSet);

            assertEquals("elderberry", stringSet.pollLast());
            assertEquals(3, stringSet.size());
            assertFalse(stringSet.contains("elderberry"));
            assertIterablesEqual(this.newWith("banana", "cherry", "date"), stringSet);

            assertEquals("banana", stringSet.pollFirst());
            assertEquals(2, stringSet.size());
            assertFalse(stringSet.contains("banana"));
            assertIterablesEqual(this.newWith("cherry", "date"), stringSet);

            assertEquals("date", stringSet.pollLast());
            assertEquals(1, stringSet.size());
            assertFalse(stringSet.contains("date"));
            assertIterablesEqual(this.newWith("cherry"), stringSet);

            assertEquals("cherry", stringSet.pollFirst());
            assertEquals(0, stringSet.size());
            assertFalse(stringSet.contains("cherry"));

            assertNull(stringSet.pollFirst());
            assertNull(stringSet.pollLast());
        }
        else
        {
            Iterator<String> stringIterator = stringSet.iterator();
            String firstString = stringIterator.next();
            String secondString = stringIterator.next();
            String thirdString = stringIterator.next();
            String fourthString = stringIterator.next();
            String fifthString = stringIterator.next();

            assertEquals(-1, Integer.signum(stringComparator.compare(firstString, secondString)));
            assertEquals(-1, Integer.signum(stringComparator.compare(secondString, thirdString)));
            assertEquals(-1, Integer.signum(stringComparator.compare(thirdString, fourthString)));
            assertEquals(-1, Integer.signum(stringComparator.compare(fourthString, fifthString)));

            assertEquals(firstString, stringSet.pollFirst());
            assertEquals(4, stringSet.size());
            assertFalse(stringSet.contains(firstString));

            assertEquals(fifthString, stringSet.pollLast());
            assertEquals(3, stringSet.size());
            assertFalse(stringSet.contains(fifthString));

            assertEquals(secondString, stringSet.pollFirst());
            assertEquals(2, stringSet.size());
            assertFalse(stringSet.contains(secondString));

            assertEquals(fourthString, stringSet.pollLast());
            assertEquals(1, stringSet.size());
            assertFalse(stringSet.contains(fourthString));

            assertEquals(thirdString, stringSet.pollFirst());
            assertEquals(0, stringSet.size());
            assertFalse(stringSet.contains(thirdString));

            assertNull(stringSet.pollFirst());
            assertNull(stringSet.pollLast());
        }

        NavigableSet<Integer> negativeSet = this.newWith(-50, -30, -10, 0, 10, 30, 50);
        Comparator<? super Integer> negativeComparator = negativeSet.comparator();
        assertEquals(7, negativeSet.size());

        if (negativeComparator == null)
        {
            assertIterablesEqual(this.newWith(-50, -30, -10, 0, 10, 30, 50), negativeSet);

            assertEquals(Integer.valueOf(-50), negativeSet.pollFirst());
            assertEquals(6, negativeSet.size());
            assertFalse(negativeSet.contains(-50));
            assertIterablesEqual(this.newWith(-30, -10, 0, 10, 30, 50), negativeSet);

            assertEquals(Integer.valueOf(50), negativeSet.pollLast());
            assertEquals(5, negativeSet.size());
            assertFalse(negativeSet.contains(50));
            assertIterablesEqual(this.newWith(-30, -10, 0, 10, 30), negativeSet);

            assertEquals(Integer.valueOf(-30), negativeSet.pollFirst());
            assertEquals(4, negativeSet.size());
            assertFalse(negativeSet.contains(-30));
            assertIterablesEqual(this.newWith(-10, 0, 10, 30), negativeSet);

            assertEquals(Integer.valueOf(30), negativeSet.pollLast());
            assertEquals(3, negativeSet.size());
            assertFalse(negativeSet.contains(30));
            assertIterablesEqual(this.newWith(-10, 0, 10), negativeSet);

            assertEquals(Integer.valueOf(-10), negativeSet.pollFirst());
            assertEquals(2, negativeSet.size());
            assertFalse(negativeSet.contains(-10));
            assertIterablesEqual(this.newWith(0, 10), negativeSet);

            assertEquals(Integer.valueOf(10), negativeSet.pollLast());
            assertEquals(1, negativeSet.size());
            assertFalse(negativeSet.contains(10));
            assertIterablesEqual(this.newWith(0), negativeSet);

            assertEquals(Integer.valueOf(0), negativeSet.pollFirst());
            assertEquals(0, negativeSet.size());
            assertFalse(negativeSet.contains(0));

            assertNull(negativeSet.pollFirst());
            assertNull(negativeSet.pollLast());
        }
        else
        {
            Iterator<Integer> negativeIterator = negativeSet.iterator();
            Integer firstNegative = negativeIterator.next();
            Integer secondNegative = negativeIterator.next();
            Integer thirdNegative = negativeIterator.next();
            Integer fourthNegative = negativeIterator.next();
            Integer fifthNegative = negativeIterator.next();
            Integer sixthNegative = negativeIterator.next();
            Integer seventhNegative = negativeIterator.next();

            assertEquals(-1, Integer.signum(negativeComparator.compare(firstNegative, secondNegative)));
            assertEquals(-1, Integer.signum(negativeComparator.compare(secondNegative, thirdNegative)));

            assertEquals(firstNegative, negativeSet.pollFirst());
            assertEquals(6, negativeSet.size());
            assertFalse(negativeSet.contains(firstNegative));

            assertEquals(seventhNegative, negativeSet.pollLast());
            assertEquals(5, negativeSet.size());
            assertFalse(negativeSet.contains(seventhNegative));

            assertEquals(secondNegative, negativeSet.pollFirst());
            assertEquals(4, negativeSet.size());
            assertFalse(negativeSet.contains(secondNegative));

            assertEquals(sixthNegative, negativeSet.pollLast());
            assertEquals(3, negativeSet.size());
            assertFalse(negativeSet.contains(sixthNegative));

            assertEquals(thirdNegative, negativeSet.pollFirst());
            assertEquals(2, negativeSet.size());
            assertFalse(negativeSet.contains(thirdNegative));

            assertEquals(fifthNegative, negativeSet.pollLast());
            assertEquals(1, negativeSet.size());
            assertFalse(negativeSet.contains(fifthNegative));

            assertEquals(fourthNegative, negativeSet.pollFirst());
            assertEquals(0, negativeSet.size());
            assertFalse(negativeSet.contains(fourthNegative));

            assertNull(negativeSet.pollFirst());
            assertNull(negativeSet.pollLast());
        }

        NavigableSet<Integer> exhaustivePollSet = this.newWith(1, 2, 3);
        Comparator<? super Integer> exhaustiveComparator = exhaustivePollSet.comparator();
        assertEquals(3, exhaustivePollSet.size());

        if (exhaustiveComparator == null)
        {
            while (!exhaustivePollSet.isEmpty())
            {
                Integer polled = exhaustivePollSet.pollFirst();
                assertFalse(exhaustivePollSet.contains(polled));
            }
            assertEquals(0, exhaustivePollSet.size());
            assertNull(exhaustivePollSet.pollFirst());
            assertNull(exhaustivePollSet.pollLast());
        }
        else
        {
            while (!exhaustivePollSet.isEmpty())
            {
                Integer polled = exhaustivePollSet.pollLast();
                assertFalse(exhaustivePollSet.contains(polled));
            }
            assertEquals(0, exhaustivePollSet.size());
            assertNull(exhaustivePollSet.pollFirst());
            assertNull(exhaustivePollSet.pollLast());
        }
    }

    @Test
    default void NavigableSet_descendingIterator()
    {
        NavigableSet<Integer> emptySet = this.newWith();
        Iterator<Integer> emptyIterator = emptySet.descendingIterator();
        assertFalse(emptyIterator.hasNext());

        NavigableSet<Integer> singletonSet = this.newWith(5);
        Iterator<Integer> singletonIterator = singletonSet.descendingIterator();
        assertTrue(singletonIterator.hasNext());
        assertEquals(Integer.valueOf(5), singletonIterator.next());
        assertFalse(singletonIterator.hasNext());

        NavigableSet<Integer> twoElementSet = this.newWith(10, 20);
        Comparator<? super Integer> twoElementComparator = twoElementSet.comparator();
        Iterator<Integer> twoElementIterator = twoElementSet.descendingIterator();
        assertTrue(twoElementIterator.hasNext());

        if (twoElementComparator == null)
        {
            assertEquals(Integer.valueOf(20), twoElementIterator.next());
            assertTrue(twoElementIterator.hasNext());
            assertEquals(Integer.valueOf(10), twoElementIterator.next());
            assertFalse(twoElementIterator.hasNext());
        }
        else
        {
            assertEquals(Integer.valueOf(10), twoElementIterator.next());
            assertTrue(twoElementIterator.hasNext());
            assertEquals(Integer.valueOf(20), twoElementIterator.next());
            assertFalse(twoElementIterator.hasNext());
        }

        NavigableSet<Integer> set = this.newWith(1, 3, 5, 7, 9);
        Comparator<? super Integer> comparator = set.comparator();
        Iterator<Integer> descendingIterator = set.descendingIterator();
        assertEquals(5, set.size());
        assertTrue(descendingIterator.hasNext());

        if (comparator == null)
        {
            assertEquals(Integer.valueOf(9), descendingIterator.next());
            assertTrue(descendingIterator.hasNext());
            assertEquals(Integer.valueOf(7), descendingIterator.next());
            assertTrue(descendingIterator.hasNext());
            assertEquals(Integer.valueOf(5), descendingIterator.next());
            assertTrue(descendingIterator.hasNext());
            assertEquals(Integer.valueOf(3), descendingIterator.next());
            assertTrue(descendingIterator.hasNext());
            assertEquals(Integer.valueOf(1), descendingIterator.next());
            assertFalse(descendingIterator.hasNext());
        }
        else
        {
            Iterator<Integer> ascendingIterator = set.iterator();
            Integer first = ascendingIterator.next();
            Integer second = ascendingIterator.next();
            Integer third = ascendingIterator.next();
            Integer fourth = ascendingIterator.next();
            Integer fifth = ascendingIterator.next();

            assertEquals(fifth, descendingIterator.next());
            assertTrue(descendingIterator.hasNext());
            assertEquals(fourth, descendingIterator.next());
            assertTrue(descendingIterator.hasNext());
            assertEquals(third, descendingIterator.next());
            assertTrue(descendingIterator.hasNext());
            assertEquals(second, descendingIterator.next());
            assertTrue(descendingIterator.hasNext());
            assertEquals(first, descendingIterator.next());
            assertFalse(descendingIterator.hasNext());
        }

        NavigableSet<Integer> largeSet = this.newWith(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
        Comparator<? super Integer> largeComparator = largeSet.comparator();
        Iterator<Integer> largeDescendingIterator = largeSet.descendingIterator();
        assertEquals(10, largeSet.size());
        assertTrue(largeDescendingIterator.hasNext());

        if (largeComparator == null)
        {
            assertEquals(Integer.valueOf(100), largeDescendingIterator.next());
            assertEquals(Integer.valueOf(90), largeDescendingIterator.next());
            assertEquals(Integer.valueOf(80), largeDescendingIterator.next());
            assertEquals(Integer.valueOf(70), largeDescendingIterator.next());
            assertEquals(Integer.valueOf(60), largeDescendingIterator.next());
            assertEquals(Integer.valueOf(50), largeDescendingIterator.next());
            assertEquals(Integer.valueOf(40), largeDescendingIterator.next());
            assertEquals(Integer.valueOf(30), largeDescendingIterator.next());
            assertEquals(Integer.valueOf(20), largeDescendingIterator.next());
            assertEquals(Integer.valueOf(10), largeDescendingIterator.next());
            assertFalse(largeDescendingIterator.hasNext());
        }
        else
        {
            Iterator<Integer> largeAscendingIterator = largeSet.iterator();
            Integer[] elements = new Integer[10];
            for (int index = 0; index < 10; index++)
            {
                elements[index] = largeAscendingIterator.next();
            }

            for (int index = 9; index >= 0; index--)
            {
                assertTrue(largeDescendingIterator.hasNext());
                assertEquals(elements[index], largeDescendingIterator.next());
            }
            assertFalse(largeDescendingIterator.hasNext());
        }

        NavigableSet<String> stringSet = this.newWith("apple", "banana", "cherry", "date", "elderberry");
        Comparator<? super String> stringComparator = stringSet.comparator();
        Iterator<String> stringDescendingIterator = stringSet.descendingIterator();
        assertEquals(5, stringSet.size());
        assertTrue(stringDescendingIterator.hasNext());

        if (stringComparator == null)
        {
            assertEquals("elderberry", stringDescendingIterator.next());
            assertTrue(stringDescendingIterator.hasNext());
            assertEquals("date", stringDescendingIterator.next());
            assertTrue(stringDescendingIterator.hasNext());
            assertEquals("cherry", stringDescendingIterator.next());
            assertTrue(stringDescendingIterator.hasNext());
            assertEquals("banana", stringDescendingIterator.next());
            assertTrue(stringDescendingIterator.hasNext());
            assertEquals("apple", stringDescendingIterator.next());
            assertFalse(stringDescendingIterator.hasNext());
        }
        else
        {
            Iterator<String> stringAscendingIterator = stringSet.iterator();
            String firstString = stringAscendingIterator.next();
            String secondString = stringAscendingIterator.next();
            String thirdString = stringAscendingIterator.next();
            String fourthString = stringAscendingIterator.next();
            String fifthString = stringAscendingIterator.next();

            assertEquals(fifthString, stringDescendingIterator.next());
            assertTrue(stringDescendingIterator.hasNext());
            assertEquals(fourthString, stringDescendingIterator.next());
            assertTrue(stringDescendingIterator.hasNext());
            assertEquals(thirdString, stringDescendingIterator.next());
            assertTrue(stringDescendingIterator.hasNext());
            assertEquals(secondString, stringDescendingIterator.next());
            assertTrue(stringDescendingIterator.hasNext());
            assertEquals(firstString, stringDescendingIterator.next());
            assertFalse(stringDescendingIterator.hasNext());
        }

        NavigableSet<Integer> negativeSet = this.newWith(-50, -30, -10, 0, 10, 30, 50);
        Comparator<? super Integer> negativeComparator = negativeSet.comparator();
        Iterator<Integer> negativeDescendingIterator = negativeSet.descendingIterator();
        assertEquals(7, negativeSet.size());
        assertTrue(negativeDescendingIterator.hasNext());

        if (negativeComparator == null)
        {
            assertEquals(Integer.valueOf(50), negativeDescendingIterator.next());
            assertTrue(negativeDescendingIterator.hasNext());
            assertEquals(Integer.valueOf(30), negativeDescendingIterator.next());
            assertTrue(negativeDescendingIterator.hasNext());
            assertEquals(Integer.valueOf(10), negativeDescendingIterator.next());
            assertTrue(negativeDescendingIterator.hasNext());
            assertEquals(Integer.valueOf(0), negativeDescendingIterator.next());
            assertTrue(negativeDescendingIterator.hasNext());
            assertEquals(Integer.valueOf(-10), negativeDescendingIterator.next());
            assertTrue(negativeDescendingIterator.hasNext());
            assertEquals(Integer.valueOf(-30), negativeDescendingIterator.next());
            assertTrue(negativeDescendingIterator.hasNext());
            assertEquals(Integer.valueOf(-50), negativeDescendingIterator.next());
            assertFalse(negativeDescendingIterator.hasNext());
        }
        else
        {
            Iterator<Integer> negativeAscendingIterator = negativeSet.iterator();
            Integer[] negativeElements = new Integer[7];
            for (int index = 0; index < 7; index++)
            {
                negativeElements[index] = negativeAscendingIterator.next();
            }

            for (int index = 6; index >= 0; index--)
            {
                assertTrue(negativeDescendingIterator.hasNext());
                assertEquals(negativeElements[index], negativeDescendingIterator.next());
            }
            assertFalse(negativeDescendingIterator.hasNext());
        }

        NavigableSet<Integer> consecutiveSet = this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
        Comparator<? super Integer> consecutiveComparator = consecutiveSet.comparator();
        Iterator<Integer> consecutiveDescendingIterator = consecutiveSet.descendingIterator();
        assertEquals(15, consecutiveSet.size());

        if (consecutiveComparator == null)
        {
            for (int value = 15; value >= 1; value--)
            {
                assertTrue(consecutiveDescendingIterator.hasNext());
                assertEquals(Integer.valueOf(value), consecutiveDescendingIterator.next());
            }
            assertFalse(consecutiveDescendingIterator.hasNext());
        }
        else
        {
            Iterator<Integer> consecutiveAscendingIterator = consecutiveSet.iterator();
            Integer[] consecutiveElements = new Integer[15];
            for (int index = 0; index < 15; index++)
            {
                consecutiveElements[index] = consecutiveAscendingIterator.next();
            }

            for (int index = 14; index >= 0; index--)
            {
                assertTrue(consecutiveDescendingIterator.hasNext());
                assertEquals(consecutiveElements[index], consecutiveDescendingIterator.next());
            }
            assertFalse(consecutiveDescendingIterator.hasNext());
        }

        NavigableSet<Integer> evenSet = this.newWith(2, 4, 6, 8, 10, 12, 14, 16, 18, 20);
        Comparator<? super Integer> evenComparator = evenSet.comparator();
        Iterator<Integer> evenDescendingIterator = evenSet.descendingIterator();
        assertEquals(10, evenSet.size());
        int evenCount = 0;

        while (evenDescendingIterator.hasNext())
        {
            Integer value = evenDescendingIterator.next();
            assertTrue(evenSet.contains(value));
            evenCount++;
        }
        assertEquals(10, evenCount);
        assertFalse(evenDescendingIterator.hasNext());

        NavigableSet<Integer> oddSet = this.newWith(1, 3, 5, 7, 9, 11, 13, 15, 17, 19);
        Comparator<? super Integer> oddComparator = oddSet.comparator();
        Iterator<Integer> oddDescendingIterator = oddSet.descendingIterator();
        assertEquals(10, oddSet.size());
        int oddCount = 0;
        Integer previousOddValue = null;

        while (oddDescendingIterator.hasNext())
        {
            Integer currentOddValue = oddDescendingIterator.next();
            assertTrue(oddSet.contains(currentOddValue));

            if (previousOddValue != null)
            {
                if (oddComparator == null)
                {
                    assertTrue(currentOddValue < previousOddValue);
                }
                else
                {
                    assertEquals(1, Integer.signum(oddComparator.compare(currentOddValue, previousOddValue)));
                }
            }

            previousOddValue = currentOddValue;
            oddCount++;
        }
        assertEquals(10, oddCount);
        assertFalse(oddDescendingIterator.hasNext());

        NavigableSet<Integer> duplicateAttemptSet = this.newWith(5, 5, 5, 10, 10, 15);
        Iterator<Integer> duplicateDescendingIterator = duplicateAttemptSet.descendingIterator();
        assertTrue(duplicateAttemptSet.size() <= 3);
        int duplicateCount = 0;

        while (duplicateDescendingIterator.hasNext())
        {
            Integer value = duplicateDescendingIterator.next();
            assertTrue(duplicateAttemptSet.contains(value));
            duplicateCount++;
        }
        assertEquals(duplicateAttemptSet.size(), duplicateCount);
        assertFalse(duplicateDescendingIterator.hasNext());
    }

    @Test
    default void NavigableSet_descendingSet()
    {
        NavigableSet<Integer> emptySet = this.newWith();
        NavigableSet<Integer> emptyDescending = emptySet.descendingSet();
        assertEquals(0, emptyDescending.size());
        assertTrue(emptyDescending.isEmpty());
        assertIterablesEqual(this.newWith(), emptyDescending);

        NavigableSet<Integer> singletonSet = this.newWith(5);
        NavigableSet<Integer> singletonDescending = singletonSet.descendingSet();
        assertEquals(1, singletonDescending.size());
        assertFalse(singletonDescending.isEmpty());
        assertTrue(singletonDescending.contains(5));
        assertIterablesEqual(this.newWith(5), singletonDescending);

        NavigableSet<Integer> twoElementSet = this.newWith(10, 20);
        NavigableSet<Integer> twoElementDescending = twoElementSet.descendingSet();
        Comparator<? super Integer> twoElementComparator = twoElementSet.comparator();
        assertEquals(2, twoElementDescending.size());
        assertTrue(twoElementDescending.contains(10));
        assertTrue(twoElementDescending.contains(20));

        if (twoElementComparator == null)
        {
            assertIterablesEqual(this.newWith(20, 10), twoElementDescending);
            assertEquals(Integer.valueOf(20), twoElementDescending.first());
            assertEquals(Integer.valueOf(10), twoElementDescending.last());
        }
        else
        {
            assertIterablesEqual(this.newWith(10, 20), twoElementDescending);
            assertEquals(Integer.valueOf(10), twoElementDescending.first());
            assertEquals(Integer.valueOf(20), twoElementDescending.last());
        }

        NavigableSet<Integer> set = this.newWith(1, 3, 5, 7, 9);
        NavigableSet<Integer> descendingSet = set.descendingSet();
        Comparator<? super Integer> comparator = set.comparator();
        assertEquals(5, set.size());
        assertEquals(5, descendingSet.size());

        if (comparator == null)
        {
            assertIterablesEqual(this.newWith(1, 3, 5, 7, 9), set);
            assertIterablesEqual(this.newWith(9, 7, 5, 3, 1), descendingSet);
            assertEquals(Integer.valueOf(1), set.first());
            assertEquals(Integer.valueOf(9), set.last());
            assertEquals(Integer.valueOf(9), descendingSet.first());
            assertEquals(Integer.valueOf(1), descendingSet.last());

            Iterator<Integer> descendingIterator = descendingSet.iterator();
            assertEquals(Integer.valueOf(9), descendingIterator.next());
            assertEquals(Integer.valueOf(7), descendingIterator.next());
            assertEquals(Integer.valueOf(5), descendingIterator.next());
            assertEquals(Integer.valueOf(3), descendingIterator.next());
            assertEquals(Integer.valueOf(1), descendingIterator.next());
            assertFalse(descendingIterator.hasNext());
        }
        else
        {
            Iterator<Integer> iterator = set.iterator();
            Integer first = iterator.next();
            Integer second = iterator.next();
            Integer third = iterator.next();
            Integer fourth = iterator.next();
            Integer fifth = iterator.next();

            Iterator<Integer> descendingIterator = descendingSet.iterator();
            assertEquals(fifth, descendingIterator.next());
            assertEquals(fourth, descendingIterator.next());
            assertEquals(third, descendingIterator.next());
            assertEquals(second, descendingIterator.next());
            assertEquals(first, descendingIterator.next());
            assertFalse(descendingIterator.hasNext());

            assertEquals(first, set.first());
            assertEquals(fifth, set.last());
            assertEquals(fifth, descendingSet.first());
            assertEquals(first, descendingSet.last());
        }

        NavigableSet<Integer> modificationSet = this.newWith(10, 20, 30);
        NavigableSet<Integer> modificationDescending = modificationSet.descendingSet();
        Comparator<? super Integer> modificationComparator = modificationSet.comparator();
        assertEquals(3, modificationSet.size());
        assertEquals(3, modificationDescending.size());

        modificationDescending.add(25);
        assertEquals(4, modificationSet.size());
        assertEquals(4, modificationDescending.size());
        assertTrue(modificationSet.contains(25));
        assertTrue(modificationDescending.contains(25));

        if (modificationComparator == null)
        {
            assertIterablesEqual(this.newWith(10, 20, 25, 30), modificationSet);
            assertIterablesEqual(this.newWith(30, 25, 20, 10), modificationDescending);
        }
        else
        {
            assertTrue(modificationSet.contains(25));
            assertTrue(modificationDescending.contains(25));
        }

        modificationSet.remove(20);
        assertEquals(3, modificationSet.size());
        assertEquals(3, modificationDescending.size());
        assertFalse(modificationSet.contains(20));
        assertFalse(modificationDescending.contains(20));

        NavigableSet<Integer> doubleDescendingSet = this.newWith(1, 2, 3, 4, 5);
        NavigableSet<Integer> firstDescending = doubleDescendingSet.descendingSet();
        NavigableSet<Integer> secondDescending = firstDescending.descendingSet();
        Comparator<? super Integer> doubleComparator = doubleDescendingSet.comparator();
        assertEquals(5, doubleDescendingSet.size());
        assertEquals(5, firstDescending.size());
        assertEquals(5, secondDescending.size());

        if (doubleComparator == null)
        {
            assertIterablesEqual(this.newWith(1, 2, 3, 4, 5), doubleDescendingSet);
            assertIterablesEqual(this.newWith(5, 4, 3, 2, 1), firstDescending);
            assertIterablesEqual(this.newWith(1, 2, 3, 4, 5), secondDescending);
        }
        else
        {
            assertIterablesEqual(doubleDescendingSet, secondDescending);
        }

        NavigableSet<Integer> largeSet = this.newWith(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);
        NavigableSet<Integer> largeDescending = largeSet.descendingSet();
        Comparator<? super Integer> largeComparator = largeSet.comparator();
        assertEquals(10, largeSet.size());
        assertEquals(10, largeDescending.size());

        if (largeComparator == null)
        {
            assertIterablesEqual(this.newWith(10, 20, 30, 40, 50, 60, 70, 80, 90, 100), largeSet);
            assertIterablesEqual(this.newWith(100, 90, 80, 70, 60, 50, 40, 30, 20, 10), largeDescending);
            assertEquals(Integer.valueOf(10), largeSet.first());
            assertEquals(Integer.valueOf(100), largeSet.last());
            assertEquals(Integer.valueOf(100), largeDescending.first());
            assertEquals(Integer.valueOf(10), largeDescending.last());

            assertEquals(Integer.valueOf(100), largeDescending.lower(105));
            assertEquals(Integer.valueOf(100), largeDescending.floor(105));
            assertNull(largeDescending.ceiling(105));
            assertNull(largeDescending.higher(105));

            assertEquals(Integer.valueOf(100), largeDescending.lower(95));
            assertEquals(Integer.valueOf(100), largeDescending.floor(95));
            assertEquals(Integer.valueOf(90), largeDescending.ceiling(95));
            assertEquals(Integer.valueOf(90), largeDescending.higher(95));

            assertNull(largeDescending.lower(100));
            assertEquals(Integer.valueOf(100), largeDescending.floor(100));
            assertEquals(Integer.valueOf(100), largeDescending.ceiling(100));
            assertEquals(Integer.valueOf(90), largeDescending.higher(100));

            assertEquals(Integer.valueOf(60), largeDescending.lower(50));
            assertEquals(Integer.valueOf(50), largeDescending.floor(50));
            assertEquals(Integer.valueOf(50), largeDescending.ceiling(50));
            assertEquals(Integer.valueOf(40), largeDescending.higher(50));

            assertEquals(Integer.valueOf(20), largeDescending.lower(10));
            assertEquals(Integer.valueOf(10), largeDescending.floor(10));
            assertEquals(Integer.valueOf(10), largeDescending.ceiling(10));
            assertNull(largeDescending.higher(10));

            assertEquals(Integer.valueOf(10), largeDescending.lower(5));
            assertEquals(Integer.valueOf(10), largeDescending.floor(5));
            assertNull(largeDescending.ceiling(5));
            assertNull(largeDescending.higher(5));
        }
        else
        {
            Iterator<Integer> largeIterator = largeSet.iterator();
            Integer firstElement = largeIterator.next();
            Integer lastElement = null;
            while (largeIterator.hasNext())
            {
                lastElement = largeIterator.next();
            }

            assertEquals(firstElement, largeSet.first());
            assertEquals(lastElement, largeSet.last());
            assertEquals(lastElement, largeDescending.first());
            assertEquals(firstElement, largeDescending.last());

            assertNull(largeDescending.lower(lastElement));
            assertEquals(lastElement, largeDescending.floor(lastElement));
            assertEquals(lastElement, largeDescending.ceiling(lastElement));

            assertEquals(lastElement, largeDescending.lower(firstElement));
            assertEquals(firstElement, largeDescending.floor(firstElement));
            assertEquals(firstElement, largeDescending.ceiling(firstElement));
            assertNull(largeDescending.higher(firstElement));
        }

        NavigableSet<String> stringSet = this.newWith("apple", "banana", "cherry", "date", "elderberry");
        NavigableSet<String> stringDescending = stringSet.descendingSet();
        Comparator<? super String> stringComparator = stringSet.comparator();
        assertEquals(5, stringSet.size());
        assertEquals(5, stringDescending.size());

        if (stringComparator == null)
        {
            assertIterablesEqual(this.newWith("apple", "banana", "cherry", "date", "elderberry"), stringSet);
            assertIterablesEqual(this.newWith("elderberry", "date", "cherry", "banana", "apple"), stringDescending);
            assertEquals("apple", stringSet.first());
            assertEquals("elderberry", stringSet.last());
            assertEquals("elderberry", stringDescending.first());
            assertEquals("apple", stringDescending.last());

            Iterator<String> stringIterator = stringDescending.iterator();
            assertEquals("elderberry", stringIterator.next());
            assertEquals("date", stringIterator.next());
            assertEquals("cherry", stringIterator.next());
            assertEquals("banana", stringIterator.next());
            assertEquals("apple", stringIterator.next());
            assertFalse(stringIterator.hasNext());
        }
        else
        {
            Iterator<String> stringIterator = stringSet.iterator();
            String firstString = stringIterator.next();
            String secondString = stringIterator.next();
            String thirdString = stringIterator.next();
            String fourthString = stringIterator.next();
            String fifthString = stringIterator.next();

            Iterator<String> descendingIterator = stringDescending.iterator();
            assertEquals(fifthString, descendingIterator.next());
            assertEquals(fourthString, descendingIterator.next());
            assertEquals(thirdString, descendingIterator.next());
            assertEquals(secondString, descendingIterator.next());
            assertEquals(firstString, descendingIterator.next());
            assertFalse(descendingIterator.hasNext());
        }

        NavigableSet<Integer> negativeSet = this.newWith(-50, -30, -10, 0, 10, 30, 50);
        NavigableSet<Integer> negativeDescending = negativeSet.descendingSet();
        Comparator<? super Integer> negativeComparator = negativeSet.comparator();
        assertEquals(7, negativeSet.size());
        assertEquals(7, negativeDescending.size());

        if (negativeComparator == null)
        {
            assertIterablesEqual(this.newWith(-50, -30, -10, 0, 10, 30, 50), negativeSet);
            assertIterablesEqual(this.newWith(50, 30, 10, 0, -10, -30, -50), negativeDescending);
            assertEquals(Integer.valueOf(-50), negativeSet.first());
            assertEquals(Integer.valueOf(50), negativeSet.last());
            assertEquals(Integer.valueOf(50), negativeDescending.first());
            assertEquals(Integer.valueOf(-50), negativeDescending.last());

            assertEquals(Integer.valueOf(50), negativeDescending.lower(60));
            assertEquals(Integer.valueOf(50), negativeDescending.floor(60));
            assertNull(negativeDescending.ceiling(60));
            assertNull(negativeDescending.higher(60));

            assertNull(negativeDescending.lower(50));
            assertEquals(Integer.valueOf(50), negativeDescending.floor(50));
            assertEquals(Integer.valueOf(50), negativeDescending.ceiling(50));
            assertEquals(Integer.valueOf(30), negativeDescending.higher(50));

            assertEquals(Integer.valueOf(10), negativeDescending.lower(0));
            assertEquals(Integer.valueOf(0), negativeDescending.floor(0));
            assertEquals(Integer.valueOf(0), negativeDescending.ceiling(0));
            assertEquals(Integer.valueOf(-10), negativeDescending.higher(0));

            assertEquals(Integer.valueOf(-30), negativeDescending.lower(-50));
            assertEquals(Integer.valueOf(-50), negativeDescending.floor(-50));
            assertEquals(Integer.valueOf(-50), negativeDescending.ceiling(-50));
            assertNull(negativeDescending.higher(-50));

            assertEquals(Integer.valueOf(-50), negativeDescending.lower(-60));
            assertEquals(Integer.valueOf(-50), negativeDescending.floor(-60));
            assertNull(negativeDescending.ceiling(-60));
            assertNull(negativeDescending.higher(-60));
        }
        else
        {
            Iterator<Integer> negativeIterator = negativeSet.iterator();
            Integer firstNegative = negativeIterator.next();
            Integer lastNegative = null;
            while (negativeIterator.hasNext())
            {
                lastNegative = negativeIterator.next();
            }

            assertEquals(firstNegative, negativeSet.first());
            assertEquals(lastNegative, negativeSet.last());
            assertEquals(lastNegative, negativeDescending.first());
            assertEquals(firstNegative, negativeDescending.last());
        }

        NavigableSet<Integer> pollSet = this.newWith(15, 25, 35, 45, 55);
        NavigableSet<Integer> pollDescending = pollSet.descendingSet();
        Comparator<? super Integer> pollComparator = pollSet.comparator();
        assertEquals(5, pollSet.size());
        assertEquals(5, pollDescending.size());

        if (pollComparator == null)
        {
            assertEquals(Integer.valueOf(55), pollDescending.pollFirst());
            assertEquals(4, pollSet.size());
            assertEquals(4, pollDescending.size());
            assertFalse(pollSet.contains(55));
            assertFalse(pollDescending.contains(55));
            assertIterablesEqual(this.newWith(15, 25, 35, 45), pollSet);
            assertIterablesEqual(this.newWith(45, 35, 25, 15), pollDescending);

            assertEquals(Integer.valueOf(15), pollDescending.pollLast());
            assertEquals(3, pollSet.size());
            assertEquals(3, pollDescending.size());
            assertFalse(pollSet.contains(15));
            assertFalse(pollDescending.contains(15));
            assertIterablesEqual(this.newWith(25, 35, 45), pollSet);
            assertIterablesEqual(this.newWith(45, 35, 25), pollDescending);
        }
        else
        {
            Iterator<Integer> pollIterator = pollSet.iterator();
            Integer firstPoll = pollIterator.next();
            pollIterator.next();
            pollIterator.next();
            pollIterator.next();
            Integer lastPoll = pollIterator.next();

            assertEquals(lastPoll, pollDescending.pollFirst());
            assertEquals(4, pollSet.size());
            assertEquals(4, pollDescending.size());
            assertFalse(pollSet.contains(lastPoll));
            assertFalse(pollDescending.contains(lastPoll));

            assertEquals(firstPoll, pollDescending.pollLast());
            assertEquals(3, pollSet.size());
            assertEquals(3, pollDescending.size());
            assertFalse(pollSet.contains(firstPoll));
            assertFalse(pollDescending.contains(firstPoll));
        }

        NavigableSet<Integer> comparatorVerifySet = this.newWith(100, 200, 300);
        NavigableSet<Integer> comparatorVerifyDescending = comparatorVerifySet.descendingSet();
        Comparator<? super Integer> originalComparator = comparatorVerifySet.comparator();
        Comparator<? super Integer> descendingComparator = comparatorVerifyDescending.comparator();

        if (originalComparator == null)
        {
            assertEquals(1, Integer.signum(descendingComparator.compare(100, 200)));
            assertEquals(-1, Integer.signum(descendingComparator.compare(200, 100)));
            assertEquals(0, descendingComparator.compare(200, 200));
        }
        else
        {
            assertEquals(-1, Integer.signum(originalComparator.compare(comparatorVerifySet.first(), comparatorVerifySet.last())));
            assertEquals(1, Integer.signum(descendingComparator.compare(comparatorVerifyDescending.first(), comparatorVerifyDescending.last())));
        }
    }
}
