/*
 * Copyright (c) 2024 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.map.mutable.sorted;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedMap;

import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.test.map.mutable.MapTestCase;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface SortedMapTestCase extends MapTestCase
{
    @Override
    <T> SortedMap<Object, T> newWith(T... elements);

    @Override
    <K, V> SortedMap<K, V> newWithKeysValues(Object... elements);

    @Override
    default boolean supportsNullKeys()
    {
        return false;
    }

    @Test
    default void SortedMap_comparator()
    {
        SortedMap<Integer, String> map = this.newWithKeysValues(3, "Three", 1, "One", 2, "Two");
        Comparator<? super Integer> comparator = map.comparator();

        assertIterablesEqual(this.newWithKeysValues(3, "Three", 2, "Two", 1, "One"), map);

        org.assertj.core.api.Assertions.assertThat(comparator)
                .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());
    }

    @Test
    default void SortedMap_subMap_headMap_tailMap()
    {
        SortedMap<Integer, String> map = this.newWithKeysValues(
                1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five",
                6, "Six", 7, "Seven", 8, "Eight", 9, "Nine", 10, "Ten");
        Comparator<? super Integer> comparator = map.comparator();

        boolean isNaturalOrder = comparator == null;
        boolean isReverseOrder = comparator != null &&
                (comparator.equals(Collections.reverseOrder()) || comparator.equals(Comparators.reverseNaturalOrder()));

        org.junit.jupiter.api.Assertions.assertTrue(isNaturalOrder || isReverseOrder,
                "Comparator must be either null (natural order) or reverse order");

        if (isNaturalOrder)
        {
            assertIterablesEqual(this.newWithKeysValues(5, "Five", 6, "Six"), map.subMap(5, 7));
            assertIterablesEqual(this.newWithKeysValues(), map.subMap(5, 5));
            assertIterablesEqual(this.newWithKeysValues(
                    1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five",
                    6, "Six", 7, "Seven", 8, "Eight", 9, "Nine", 10, "Ten"), map.subMap(1, 11));
            assertIterablesEqual(this.newWithKeysValues(
                    1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five",
                    6, "Six", 7, "Seven", 8, "Eight", 9, "Nine", 10, "Ten"), map.subMap(0, 15));

            assertIterablesEqual(this.newWithKeysValues(1, "One"), map.subMap(1, 2));
            assertIterablesEqual(this.newWithKeysValues(10, "Ten"), map.subMap(10, 11));
            assertIterablesEqual(this.newWithKeysValues(), map.subMap(0, 1));
            assertIterablesEqual(this.newWithKeysValues(), map.subMap(11, 12));

            assertIterablesEqual(this.newWithKeysValues(3, "Three", 4, "Four", 5, "Five"), map.subMap(3, 6));
            assertIterablesEqual(this.newWithKeysValues(6, "Six", 7, "Seven", 8, "Eight"), map.subMap(6, 9));

            assertIterablesEqual(this.newWithKeysValues(2, "Two", 3, "Three", 4, "Four", 5, "Five", 6, "Six", 7, "Seven", 8, "Eight"), map.subMap(2, 9));

            assertIterablesEqual(this.newWithKeysValues(1, "One", 2, "Two", 3, "Three", 4, "Four"), map.headMap(5));
            assertIterablesEqual(this.newWithKeysValues(), map.headMap(1));
            assertIterablesEqual(this.newWithKeysValues(
                    1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five",
                    6, "Six", 7, "Seven", 8, "Eight", 9, "Nine", 10, "Ten"), map.headMap(11));
            assertIterablesEqual(this.newWithKeysValues(), map.headMap(0));

            assertIterablesEqual(this.newWithKeysValues(7, "Seven", 8, "Eight", 9, "Nine", 10, "Ten"), map.tailMap(7));
            assertIterablesEqual(this.newWithKeysValues(
                    1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five",
                    6, "Six", 7, "Seven", 8, "Eight", 9, "Nine", 10, "Ten"), map.tailMap(1));
            assertIterablesEqual(this.newWithKeysValues(), map.tailMap(11));
            assertIterablesEqual(this.newWithKeysValues(
                    1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five",
                    6, "Six", 7, "Seven", 8, "Eight", 9, "Nine", 10, "Ten"), map.tailMap(0));

            SortedMap<Integer, String> subMapBetweenElements = map.subMap(2, 9);
            assertIterablesEqual(this.newWithKeysValues(4, "Four", 5, "Five", 6, "Six"), subMapBetweenElements.subMap(4, 7));
            assertIterablesEqual(this.newWithKeysValues(2, "Two", 3, "Three", 4, "Four", 5, "Five"), subMapBetweenElements.headMap(6));
            assertIterablesEqual(this.newWithKeysValues(5, "Five", 6, "Six", 7, "Seven", 8, "Eight"), subMapBetweenElements.tailMap(5));
        }
        else
        {
            assertIterablesEqual(this.newWithKeysValues(7, "Seven", 6, "Six"), map.subMap(7, 5));
            assertIterablesEqual(this.newWithKeysValues(), map.subMap(5, 5));
            assertIterablesEqual(this.newWithKeysValues(
                    10, "Ten", 9, "Nine", 8, "Eight", 7, "Seven", 6, "Six",
                    5, "Five", 4, "Four", 3, "Three", 2, "Two"), map.subMap(11, 1));
            assertIterablesEqual(this.newWithKeysValues(
                    10, "Ten", 9, "Nine", 8, "Eight", 7, "Seven", 6, "Six",
                    5, "Five", 4, "Four", 3, "Three", 2, "Two", 1, "One"), map.subMap(15, 0));

            assertIterablesEqual(this.newWithKeysValues(10, "Ten"), map.subMap(10, 9));
            assertIterablesEqual(this.newWithKeysValues(2, "Two"), map.subMap(2, 1));
            assertIterablesEqual(this.newWithKeysValues(), map.subMap(12, 11));
            assertIterablesEqual(this.newWithKeysValues(1, "One"), map.subMap(1, 0));

            assertIterablesEqual(this.newWithKeysValues(6, "Six", 5, "Five", 4, "Four"), map.subMap(6, 3));
            assertIterablesEqual(this.newWithKeysValues(9, "Nine", 8, "Eight", 7, "Seven"), map.subMap(9, 6));

            assertIterablesEqual(this.newWithKeysValues(9, "Nine", 8, "Eight", 7, "Seven", 6, "Six", 5, "Five", 4, "Four", 3, "Three"), map.subMap(9, 2));

            assertIterablesEqual(this.newWithKeysValues(10, "Ten", 9, "Nine", 8, "Eight", 7, "Seven", 6, "Six"), map.headMap(5));
            assertIterablesEqual(this.newWithKeysValues(), map.headMap(11));
            assertIterablesEqual(this.newWithKeysValues(
                    10, "Ten", 9, "Nine", 8, "Eight", 7, "Seven", 6, "Six",
                    5, "Five", 4, "Four", 3, "Three", 2, "Two"), map.headMap(1));
            assertIterablesEqual(this.newWithKeysValues(
                    10, "Ten", 9, "Nine", 8, "Eight", 7, "Seven", 6, "Six",
                    5, "Five", 4, "Four", 3, "Three", 2, "Two", 1, "One"), map.headMap(0));

            assertIterablesEqual(this.newWithKeysValues(7, "Seven", 6, "Six", 5, "Five", 4, "Four", 3, "Three", 2, "Two", 1, "One"), map.tailMap(7));
            assertIterablesEqual(this.newWithKeysValues(
                    10, "Ten", 9, "Nine", 8, "Eight", 7, "Seven", 6, "Six",
                    5, "Five", 4, "Four", 3, "Three", 2, "Two", 1, "One"), map.tailMap(10));
            assertIterablesEqual(this.newWithKeysValues(), map.tailMap(0));
            assertIterablesEqual(this.newWithKeysValues(
                    10, "Ten", 9, "Nine", 8, "Eight", 7, "Seven", 6, "Six",
                    5, "Five", 4, "Four", 3, "Three", 2, "Two", 1, "One"), map.tailMap(15));

            SortedMap<Integer, String> subMapBetweenElements = map.subMap(9, 2);
            assertIterablesEqual(this.newWithKeysValues(7, "Seven", 6, "Six", 5, "Five"), subMapBetweenElements.subMap(7, 4));
            assertIterablesEqual(this.newWithKeysValues(9, "Nine", 8, "Eight", 7, "Seven"), subMapBetweenElements.headMap(6));
            assertIterablesEqual(this.newWithKeysValues(5, "Five", 4, "Four", 3, "Three"), subMapBetweenElements.tailMap(5));
        }

        SortedMap<Integer, String> largeMap = this.newWithKeysValues(
                10, "Ten", 20, "Twenty", 30, "Thirty", 40, "Forty", 50, "Fifty",
                60, "Sixty", 70, "Seventy", 80, "Eighty", 90, "Ninety", 100, "Hundred");

        if (isNaturalOrder)
        {
            assertIterablesEqual(this.newWithKeysValues(20, "Twenty", 30, "Thirty", 40, "Forty", 50, "Fifty", 60, "Sixty", 70, "Seventy"), largeMap.subMap(20, 80));
            assertIterablesEqual(this.newWithKeysValues(30, "Thirty", 40, "Forty", 50, "Fifty", 60, "Sixty"), largeMap.subMap(25, 75));
            assertIterablesEqual(this.newWithKeysValues(10, "Ten", 20, "Twenty", 30, "Thirty", 40, "Forty", 50, "Fifty"), largeMap.headMap(55));
            assertIterablesEqual(this.newWithKeysValues(60, "Sixty", 70, "Seventy", 80, "Eighty", 90, "Ninety", 100, "Hundred"), largeMap.tailMap(55));
        }
        else
        {
            assertIterablesEqual(this.newWithKeysValues(80, "Eighty", 70, "Seventy", 60, "Sixty", 50, "Fifty", 40, "Forty", 30, "Thirty"), largeMap.subMap(80, 20));
            assertIterablesEqual(this.newWithKeysValues(70, "Seventy", 60, "Sixty", 50, "Fifty", 40, "Forty", 30, "Thirty"), largeMap.subMap(75, 25));
            assertIterablesEqual(this.newWithKeysValues(100, "Hundred", 90, "Ninety", 80, "Eighty", 70, "Seventy", 60, "Sixty"), largeMap.headMap(55));
            assertIterablesEqual(this.newWithKeysValues(50, "Fifty", 40, "Forty", 30, "Thirty", 20, "Twenty", 10, "Ten"), largeMap.tailMap(55));
        }
    }

    @Test
    default void SortedMap_firstKey_lastKey()
    {
        assertThrows(NoSuchElementException.class, () -> this.newWith().firstKey());
        assertThrows(NoSuchElementException.class, () -> this.newWith().lastKey());

        SortedMap<Integer, String> map1 = this.newWithKeysValues(42, "FortyTwo");
        assertEquals(Integer.valueOf(42), map1.firstKey());
        assertEquals(Integer.valueOf(42), map1.lastKey());

        SortedMap<Integer, String> map2 = this.newWithKeysValues(1, "One", 2, "Two");
        if (map2.comparator() == null)
        {
            assertEquals(Integer.valueOf(1), map2.firstKey());
            assertEquals(Integer.valueOf(2), map2.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(2), map2.firstKey());
            assertEquals(Integer.valueOf(1), map2.lastKey());
        }

        SortedMap<Integer, String> map3 = this.newWithKeysValues(1, "One", 2, "Two", 3, "Three");
        if (map3.comparator() == null)
        {
            assertEquals(Integer.valueOf(1), map3.firstKey());
            assertEquals(Integer.valueOf(3), map3.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(3), map3.firstKey());
            assertEquals(Integer.valueOf(1), map3.lastKey());
        }
    }

    @Test
    default void SortedMap_entrySet_iterator_order()
    {
        SortedMap<Integer, String> map = this.newWithKeysValues(3, "Three", 1, "One", 2, "Two");
        Comparator<? super Integer> comparator = map.comparator();

        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();

        if (comparator == null)
        {
            Map.Entry<Integer, String> entry1 = iterator.next();
            assertEquals(Integer.valueOf(1), entry1.getKey());
            assertEquals("One", entry1.getValue());

            Map.Entry<Integer, String> entry2 = iterator.next();
            assertEquals(Integer.valueOf(2), entry2.getKey());
            assertEquals("Two", entry2.getValue());

            Map.Entry<Integer, String> entry3 = iterator.next();
            assertEquals(Integer.valueOf(3), entry3.getKey());
            assertEquals("Three", entry3.getValue());
        }
        else
        {
            Map.Entry<Integer, String> entry1 = iterator.next();
            assertEquals(Integer.valueOf(3), entry1.getKey());
            assertEquals("Three", entry1.getValue());

            Map.Entry<Integer, String> entry2 = iterator.next();
            assertEquals(Integer.valueOf(2), entry2.getKey());
            assertEquals("Two", entry2.getValue());

            Map.Entry<Integer, String> entry3 = iterator.next();
            assertEquals(Integer.valueOf(1), entry3.getKey());
            assertEquals("One", entry3.getValue());
        }
    }

    @Test
    default void SortedMap_keySet_iterator_order()
    {
        SortedMap<Integer, String> map = this.newWithKeysValues(5, "Five", 1, "One", 3, "Three");
        Comparator<? super Integer> comparator = map.comparator();

        Iterator<Integer> iterator = map.keySet().iterator();

        if (comparator == null)
        {
            assertEquals(Integer.valueOf(1), iterator.next());
            assertEquals(Integer.valueOf(3), iterator.next());
            assertEquals(Integer.valueOf(5), iterator.next());
        }
        else
        {
            assertEquals(Integer.valueOf(5), iterator.next());
            assertEquals(Integer.valueOf(3), iterator.next());
            assertEquals(Integer.valueOf(1), iterator.next());
        }
    }

    @Test
    default void SortedMap_values_iterator_order()
    {
        SortedMap<Integer, String> map = this.newWithKeysValues(5, "Five", 1, "One", 3, "Three");
        Comparator<? super Integer> comparator = map.comparator();

        Iterator<String> iterator = map.values().iterator();

        if (comparator == null)
        {
            assertEquals("One", iterator.next());
            assertEquals("Three", iterator.next());
            assertEquals("Five", iterator.next());
        }
        else
        {
            assertEquals("Five", iterator.next());
            assertEquals("Three", iterator.next());
            assertEquals("One", iterator.next());
        }
    }
}
