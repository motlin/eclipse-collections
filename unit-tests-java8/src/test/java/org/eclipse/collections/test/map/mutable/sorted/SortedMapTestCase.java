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
        SortedMap<Integer, String> emptyMap = this.newWithKeysValues();
        Comparator<? super Integer> emptyMapComparator = emptyMap.comparator();

        SortedMap<Integer, String> singleElementMap = this.newWithKeysValues(42, "FortyTwo");
        Comparator<? super Integer> singleElementComparator = singleElementMap.comparator();

        SortedMap<Integer, String> map = this.newWithKeysValues(3, "Three", 1, "One", 2, "Two");
        Comparator<? super Integer> comparator = map.comparator();

        SortedMap<Integer, String> largeMap = this.newWithKeysValues(
                10, "Ten", 20, "Twenty", 30, "Thirty", 40, "Forty", 50, "Fifty",
                60, "Sixty", 70, "Seventy", 80, "Eighty", 90, "Ninety", 100, "Hundred");
        Comparator<? super Integer> largeMapComparator = largeMap.comparator();

        boolean isNaturalOrder = comparator == null;
        boolean isReverseOrder = comparator != null &&
                (comparator.equals(Collections.reverseOrder()) || comparator.equals(Comparators.reverseNaturalOrder()));

        org.junit.jupiter.api.Assertions.assertTrue(isNaturalOrder || isReverseOrder,
                "Comparator must be either null (natural order) or reverse order");

        if (isNaturalOrder)
        {
            org.junit.jupiter.api.Assertions.assertNull(comparator, "Natural order comparator should be null");
            org.junit.jupiter.api.Assertions.assertNull(emptyMapComparator, "Empty map should have same comparator");
            org.junit.jupiter.api.Assertions.assertNull(singleElementComparator, "Single element map should have same comparator");
            org.junit.jupiter.api.Assertions.assertNull(largeMapComparator, "Large map should have same comparator");

            assertIterablesEqual(this.newWithKeysValues(1, "One", 2, "Two", 3, "Three"), map);
            assertIterablesEqual(this.newWithKeysValues(42, "FortyTwo"), singleElementMap);
            assertIterablesEqual(this.newWithKeysValues(
                    10, "Ten", 20, "Twenty", 30, "Thirty", 40, "Forty", 50, "Fifty",
                    60, "Sixty", 70, "Seventy", 80, "Eighty", 90, "Ninety", 100, "Hundred"), largeMap);
        }
        else
        {
            org.junit.jupiter.api.Assertions.assertNotNull(comparator, "Reverse order comparator should not be null");
            org.assertj.core.api.Assertions.assertThat(comparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());

            org.junit.jupiter.api.Assertions.assertNotNull(emptyMapComparator, "Empty map should have same comparator");
            org.assertj.core.api.Assertions.assertThat(emptyMapComparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());

            org.junit.jupiter.api.Assertions.assertNotNull(singleElementComparator, "Single element map should have same comparator");
            org.assertj.core.api.Assertions.assertThat(singleElementComparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());

            org.junit.jupiter.api.Assertions.assertNotNull(largeMapComparator, "Large map should have same comparator");
            org.assertj.core.api.Assertions.assertThat(largeMapComparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());

            assertIterablesEqual(this.newWithKeysValues(3, "Three", 2, "Two", 1, "One"), map);
            assertIterablesEqual(this.newWithKeysValues(42, "FortyTwo"), singleElementMap);
            assertIterablesEqual(this.newWithKeysValues(
                    100, "Hundred", 90, "Ninety", 80, "Eighty", 70, "Seventy", 60, "Sixty",
                    50, "Fifty", 40, "Forty", 30, "Thirty", 20, "Twenty", 10, "Ten"), largeMap);
        }

        SortedMap<Integer, String> subMap = map.subMap(isNaturalOrder ? 1 : 3, isNaturalOrder ? 3 : 1);
        Comparator<? super Integer> subMapComparator = subMap.comparator();
        if (isNaturalOrder)
        {
            org.junit.jupiter.api.Assertions.assertNull(subMapComparator, "SubMap should inherit null comparator");
            assertIterablesEqual(this.newWithKeysValues(1, "One", 2, "Two"), subMap);
        }
        else
        {
            org.junit.jupiter.api.Assertions.assertNotNull(subMapComparator, "SubMap should inherit non-null comparator");
            org.assertj.core.api.Assertions.assertThat(subMapComparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());
            assertIterablesEqual(this.newWithKeysValues(3, "Three", 2, "Two"), subMap);
        }

        SortedMap<Integer, String> headMap = map.headMap(isNaturalOrder ? 3 : 1);
        Comparator<? super Integer> headMapComparator = headMap.comparator();
        if (isNaturalOrder)
        {
            org.junit.jupiter.api.Assertions.assertNull(headMapComparator, "HeadMap should inherit null comparator");
            assertIterablesEqual(this.newWithKeysValues(1, "One", 2, "Two"), headMap);
        }
        else
        {
            org.junit.jupiter.api.Assertions.assertNotNull(headMapComparator, "HeadMap should inherit non-null comparator");
            org.assertj.core.api.Assertions.assertThat(headMapComparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());
            assertIterablesEqual(this.newWithKeysValues(3, "Three", 2, "Two"), headMap);
        }

        SortedMap<Integer, String> tailMap = map.tailMap(isNaturalOrder ? 2 : 2);
        Comparator<? super Integer> tailMapComparator = tailMap.comparator();
        if (isNaturalOrder)
        {
            org.junit.jupiter.api.Assertions.assertNull(tailMapComparator, "TailMap should inherit null comparator");
            assertIterablesEqual(this.newWithKeysValues(2, "Two", 3, "Three"), tailMap);
        }
        else
        {
            org.junit.jupiter.api.Assertions.assertNotNull(tailMapComparator, "TailMap should inherit non-null comparator");
            org.assertj.core.api.Assertions.assertThat(tailMapComparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());
            assertIterablesEqual(this.newWithKeysValues(2, "Two", 1, "One"), tailMap);
        }

        SortedMap<Integer, String> nestedSubMap = subMap.subMap(isNaturalOrder ? 1 : 3, isNaturalOrder ? 2 : 2);
        Comparator<? super Integer> nestedSubMapComparator = nestedSubMap.comparator();
        if (isNaturalOrder)
        {
            org.junit.jupiter.api.Assertions.assertNull(nestedSubMapComparator, "Nested subMap should inherit null comparator");
            assertIterablesEqual(this.newWithKeysValues(1, "One"), nestedSubMap);
        }
        else
        {
            org.junit.jupiter.api.Assertions.assertNotNull(nestedSubMapComparator, "Nested subMap should inherit non-null comparator");
            org.assertj.core.api.Assertions.assertThat(nestedSubMapComparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());
            assertIterablesEqual(this.newWithKeysValues(3, "Three"), nestedSubMap);
        }

        SortedMap<Integer, String> boundaryMap = this.newWithKeysValues(
                Integer.MIN_VALUE, "Min", 0, "Zero", Integer.MAX_VALUE, "Max");
        Comparator<? super Integer> boundaryMapComparator = boundaryMap.comparator();
        if (isNaturalOrder)
        {
            org.junit.jupiter.api.Assertions.assertNull(boundaryMapComparator, "Map with boundary values should have same comparator");
            assertIterablesEqual(this.newWithKeysValues(Integer.MIN_VALUE, "Min", 0, "Zero", Integer.MAX_VALUE, "Max"), boundaryMap);
        }
        else
        {
            org.junit.jupiter.api.Assertions.assertNotNull(boundaryMapComparator, "Map with boundary values should have same comparator");
            org.assertj.core.api.Assertions.assertThat(boundaryMapComparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());
            assertIterablesEqual(this.newWithKeysValues(Integer.MAX_VALUE, "Max", 0, "Zero", Integer.MIN_VALUE, "Min"), boundaryMap);
        }

        SortedMap<Integer, String> consecutiveMap = this.newWithKeysValues(1, "A", 2, "B", 3, "C", 4, "D", 5, "E");
        Comparator<? super Integer> consecutiveMapComparator = consecutiveMap.comparator();
        if (isNaturalOrder)
        {
            org.junit.jupiter.api.Assertions.assertNull(consecutiveMapComparator, "Map with consecutive keys should have same comparator");
            assertIterablesEqual(this.newWithKeysValues(1, "A", 2, "B", 3, "C", 4, "D", 5, "E"), consecutiveMap);
        }
        else
        {
            org.junit.jupiter.api.Assertions.assertNotNull(consecutiveMapComparator, "Map with consecutive keys should have same comparator");
            org.assertj.core.api.Assertions.assertThat(consecutiveMapComparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());
            assertIterablesEqual(this.newWithKeysValues(5, "E", 4, "D", 3, "C", 2, "B", 1, "A"), consecutiveMap);
        }

        SortedMap<Integer, String> gapMap = this.newWithKeysValues(10, "Ten", 100, "Hundred", 1000, "Thousand");
        Comparator<? super Integer> gapMapComparator = gapMap.comparator();
        if (isNaturalOrder)
        {
            org.junit.jupiter.api.Assertions.assertNull(gapMapComparator, "Map with gaps should have same comparator");
            assertIterablesEqual(this.newWithKeysValues(10, "Ten", 100, "Hundred", 1000, "Thousand"), gapMap);
        }
        else
        {
            org.junit.jupiter.api.Assertions.assertNotNull(gapMapComparator, "Map with gaps should have same comparator");
            org.assertj.core.api.Assertions.assertThat(gapMapComparator)
                    .isIn(Collections.reverseOrder(), Comparators.reverseNaturalOrder());
            assertIterablesEqual(this.newWithKeysValues(1000, "Thousand", 100, "Hundred", 10, "Ten"), gapMap);
        }
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

            assertThrows(IllegalArgumentException.class, () -> map.subMap(10, 5));
            assertThrows(IllegalArgumentException.class, () -> map.subMap(7, 2));
            assertThrows(IllegalArgumentException.class, () -> map.subMap(Integer.MAX_VALUE, Integer.MIN_VALUE));
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

            assertThrows(IllegalArgumentException.class, () -> map.subMap(5, 10));
            assertThrows(IllegalArgumentException.class, () -> map.subMap(2, 7));
            assertThrows(IllegalArgumentException.class, () -> map.subMap(1, 9));
            assertThrows(IllegalArgumentException.class, () -> map.subMap(Integer.MIN_VALUE, Integer.MAX_VALUE));
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

        SortedMap<Integer, String> singleElementMap = this.newWithKeysValues(42, "FortyTwo");
        assertIterablesEqual(this.newWithKeysValues(42, "FortyTwo"), singleElementMap.subMap(isNaturalOrder ? 0 : 100, isNaturalOrder ? 100 : 0));
        assertIterablesEqual(this.newWithKeysValues(42, "FortyTwo"), singleElementMap.subMap(42, isNaturalOrder ? 43 : 41));
        assertIterablesEqual(this.newWithKeysValues(), singleElementMap.subMap(isNaturalOrder ? 43 : 41, isNaturalOrder ? 50 : 0));
        assertIterablesEqual(this.newWithKeysValues(42, "FortyTwo"), singleElementMap.headMap(isNaturalOrder ? 50 : 0));
        assertIterablesEqual(this.newWithKeysValues(), singleElementMap.headMap(isNaturalOrder ? 42 : 50));
        assertIterablesEqual(this.newWithKeysValues(42, "FortyTwo"), singleElementMap.tailMap(isNaturalOrder ? 42 : 50));
        assertIterablesEqual(this.newWithKeysValues(), singleElementMap.tailMap(isNaturalOrder ? 50 : 0));

        SortedMap<Integer, String> emptyMap = this.newWithKeysValues();
        assertIterablesEqual(this.newWithKeysValues(), emptyMap.subMap(1, isNaturalOrder ? 10 : -10));
        assertIterablesEqual(this.newWithKeysValues(), emptyMap.headMap(5));
        assertIterablesEqual(this.newWithKeysValues(), emptyMap.tailMap(5));

        SortedMap<Integer, String> boundaryMap = this.newWithKeysValues(
                Integer.MIN_VALUE, "Min", -100, "NegativeHundred", 0, "Zero", 100, "Hundred", Integer.MAX_VALUE, "Max");

        if (isNaturalOrder)
        {
            assertIterablesEqual(this.newWithKeysValues(-100, "NegativeHundred", 0, "Zero", 100, "Hundred"), boundaryMap.subMap(-100, Integer.MAX_VALUE));
            assertIterablesEqual(this.newWithKeysValues(Integer.MIN_VALUE, "Min", -100, "NegativeHundred", 0, "Zero"), boundaryMap.headMap(100));
            assertIterablesEqual(this.newWithKeysValues(0, "Zero", 100, "Hundred", Integer.MAX_VALUE, "Max"), boundaryMap.tailMap(0));
            assertIterablesEqual(this.newWithKeysValues(Integer.MIN_VALUE, "Min"), boundaryMap.subMap(Integer.MIN_VALUE, -100));
            assertIterablesEqual(this.newWithKeysValues(Integer.MAX_VALUE, "Max"), boundaryMap.subMap(Integer.MAX_VALUE, Integer.MAX_VALUE + 1));
        }
        else
        {
            assertIterablesEqual(this.newWithKeysValues(Integer.MAX_VALUE, "Max", 100, "Hundred", 0, "Zero"), boundaryMap.subMap(Integer.MAX_VALUE, -100));
            assertIterablesEqual(this.newWithKeysValues(Integer.MAX_VALUE, "Max", 100, "Hundred", 0, "Zero"), boundaryMap.headMap(-100));
            assertIterablesEqual(this.newWithKeysValues(0, "Zero", -100, "NegativeHundred", Integer.MIN_VALUE, "Min"), boundaryMap.tailMap(0));
            assertIterablesEqual(this.newWithKeysValues(-100, "NegativeHundred"), boundaryMap.subMap(-100, Integer.MIN_VALUE));
            assertIterablesEqual(this.newWithKeysValues(Integer.MAX_VALUE, "Max"), boundaryMap.subMap(Integer.MAX_VALUE, Integer.MAX_VALUE - 1));
        }

        SortedMap<Integer, String> consecutiveMap = this.newWithKeysValues(1, "A", 2, "B", 3, "C", 4, "D", 5, "E", 6, "F", 7, "G");
        SortedMap<Integer, String> middleSubMap = consecutiveMap.subMap(isNaturalOrder ? 3 : 5, isNaturalOrder ? 6 : 2);
        assertIterablesEqual(this.newWithKeysValues(isNaturalOrder ? 3 : 5, isNaturalOrder ? "C" : "E",
                isNaturalOrder ? 4 : 4, "D", isNaturalOrder ? 5 : 3, isNaturalOrder ? "E" : "C"), middleSubMap);

        SortedMap<Integer, String> nestedHeadMap = middleSubMap.headMap(isNaturalOrder ? 5 : 3);
        assertIterablesEqual(this.newWithKeysValues(isNaturalOrder ? 3 : 5, isNaturalOrder ? "C" : "E",
                isNaturalOrder ? 4 : 4, "D"), nestedHeadMap);

        SortedMap<Integer, String> nestedTailMap = middleSubMap.tailMap(isNaturalOrder ? 4 : 4);
        assertIterablesEqual(this.newWithKeysValues(4, "D", isNaturalOrder ? 5 : 3, isNaturalOrder ? "E" : "C"), nestedTailMap);

        SortedMap<Integer, String> deeplyNestedSubMap = middleSubMap.subMap(isNaturalOrder ? 3 : 5, isNaturalOrder ? 5 : 3);
        assertIterablesEqual(this.newWithKeysValues(isNaturalOrder ? 3 : 5, isNaturalOrder ? "C" : "E", 4, "D"), deeplyNestedSubMap);

        assertEquals(isNaturalOrder ? 3 : 5, middleSubMap.firstKey());
        assertEquals(isNaturalOrder ? 5 : 3, middleSubMap.lastKey());
        assertEquals(isNaturalOrder ? 3 : 5, nestedHeadMap.firstKey());
        assertEquals(isNaturalOrder ? 4 : 4, nestedHeadMap.lastKey());
        assertEquals(isNaturalOrder ? 4 : 4, nestedTailMap.firstKey());
        assertEquals(isNaturalOrder ? 5 : 3, nestedTailMap.lastKey());
    }

    @Test
    default void SortedMap_firstKey_lastKey()
    {
        assertThrows(NoSuchElementException.class, () -> this.newWith().firstKey());
        assertThrows(NoSuchElementException.class, () -> this.newWith().lastKey());

        SortedMap<Integer, String> singleElementMap = this.newWithKeysValues(42, "FortyTwo");
        assertEquals(Integer.valueOf(42), singleElementMap.firstKey());
        assertEquals(Integer.valueOf(42), singleElementMap.lastKey());

        SortedMap<Integer, String> twoElementMap = this.newWithKeysValues(1, "One", 2, "Two");
        Comparator<? super Integer> twoElementComparator = twoElementMap.comparator();
        boolean isNaturalOrder = twoElementComparator == null;

        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(1), twoElementMap.firstKey());
            assertEquals(Integer.valueOf(2), twoElementMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(2), twoElementMap.firstKey());
            assertEquals(Integer.valueOf(1), twoElementMap.lastKey());
        }

        SortedMap<Integer, String> threeElementMap = this.newWithKeysValues(1, "One", 2, "Two", 3, "Three");
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(1), threeElementMap.firstKey());
            assertEquals(Integer.valueOf(3), threeElementMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(3), threeElementMap.firstKey());
            assertEquals(Integer.valueOf(1), threeElementMap.lastKey());
        }

        SortedMap<Integer, String> unorderedInsertionMap = this.newWithKeysValues(5, "Five", 1, "One", 3, "Three", 2, "Two", 4, "Four");
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(1), unorderedInsertionMap.firstKey());
            assertEquals(Integer.valueOf(5), unorderedInsertionMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(5), unorderedInsertionMap.firstKey());
            assertEquals(Integer.valueOf(1), unorderedInsertionMap.lastKey());
        }

        SortedMap<Integer, String> largeMap = this.newWithKeysValues(
                10, "Ten", 20, "Twenty", 30, "Thirty", 40, "Forty", 50, "Fifty",
                60, "Sixty", 70, "Seventy", 80, "Eighty", 90, "Ninety", 100, "Hundred");
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(10), largeMap.firstKey());
            assertEquals(Integer.valueOf(100), largeMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(100), largeMap.firstKey());
            assertEquals(Integer.valueOf(10), largeMap.lastKey());
        }

        SortedMap<Integer, String> boundaryMap = this.newWithKeysValues(
                Integer.MIN_VALUE, "Min", 0, "Zero", Integer.MAX_VALUE, "Max");
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(Integer.MIN_VALUE), boundaryMap.firstKey());
            assertEquals(Integer.valueOf(Integer.MAX_VALUE), boundaryMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(Integer.MAX_VALUE), boundaryMap.firstKey());
            assertEquals(Integer.valueOf(Integer.MIN_VALUE), boundaryMap.lastKey());
        }

        SortedMap<Integer, String> consecutiveMap = this.newWithKeysValues(1, "A", 2, "B", 3, "C", 4, "D", 5, "E");
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(1), consecutiveMap.firstKey());
            assertEquals(Integer.valueOf(5), consecutiveMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(5), consecutiveMap.firstKey());
            assertEquals(Integer.valueOf(1), consecutiveMap.lastKey());
        }

        SortedMap<Integer, String> gapMap = this.newWithKeysValues(10, "Ten", 100, "Hundred", 1000, "Thousand");
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(10), gapMap.firstKey());
            assertEquals(Integer.valueOf(1000), gapMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(1000), gapMap.firstKey());
            assertEquals(Integer.valueOf(10), gapMap.lastKey());
        }

        SortedMap<Integer, String> negativePositiveMap = this.newWithKeysValues(-5, "NegativeFive", -1, "NegativeOne", 1, "One", 5, "Five");
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(-5), negativePositiveMap.firstKey());
            assertEquals(Integer.valueOf(5), negativePositiveMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(5), negativePositiveMap.firstKey());
            assertEquals(Integer.valueOf(-5), negativePositiveMap.lastKey());
        }

        SortedMap<Integer, String> duplicateValuesMap = this.newWithKeysValues(10, "Same", 20, "Same", 30, "Same");
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(10), duplicateValuesMap.firstKey());
            assertEquals(Integer.valueOf(30), duplicateValuesMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(30), duplicateValuesMap.firstKey());
            assertEquals(Integer.valueOf(10), duplicateValuesMap.lastKey());
        }

        SortedMap<Integer, String> fullRangeMap = this.newWithKeysValues(
                1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five",
                6, "Six", 7, "Seven", 8, "Eight", 9, "Nine", 10, "Ten");

        SortedMap<Integer, String> subMapMiddle = fullRangeMap.subMap(isNaturalOrder ? 3 : 8, isNaturalOrder ? 8 : 3);
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(3), subMapMiddle.firstKey());
            assertEquals(Integer.valueOf(7), subMapMiddle.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(8), subMapMiddle.firstKey());
            assertEquals(Integer.valueOf(4), subMapMiddle.lastKey());
        }

        SortedMap<Integer, String> headMapFive = fullRangeMap.headMap(isNaturalOrder ? 6 : 5);
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(1), headMapFive.firstKey());
            assertEquals(Integer.valueOf(5), headMapFive.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(10), headMapFive.firstKey());
            assertEquals(Integer.valueOf(6), headMapFive.lastKey());
        }

        SortedMap<Integer, String> tailMapSix = fullRangeMap.tailMap(isNaturalOrder ? 6 : 5);
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(6), tailMapSix.firstKey());
            assertEquals(Integer.valueOf(10), tailMapSix.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(5), tailMapSix.firstKey());
            assertEquals(Integer.valueOf(1), tailMapSix.lastKey());
        }

        SortedMap<Integer, String> singleElementSubMap = fullRangeMap.subMap(isNaturalOrder ? 5 : 5, isNaturalOrder ? 6 : 4);
        assertEquals(Integer.valueOf(5), singleElementSubMap.firstKey());
        assertEquals(Integer.valueOf(5), singleElementSubMap.lastKey());

        SortedMap<Integer, String> emptySubMap = fullRangeMap.subMap(isNaturalOrder ? 5 : 6, isNaturalOrder ? 5 : 6);
        assertThrows(NoSuchElementException.class, () -> emptySubMap.firstKey());
        assertThrows(NoSuchElementException.class, () -> emptySubMap.lastKey());

        SortedMap<Integer, String> emptyHeadMap = fullRangeMap.headMap(isNaturalOrder ? 1 : 11);
        assertThrows(NoSuchElementException.class, () -> emptyHeadMap.firstKey());
        assertThrows(NoSuchElementException.class, () -> emptyHeadMap.lastKey());

        SortedMap<Integer, String> emptyTailMap = fullRangeMap.tailMap(isNaturalOrder ? 11 : 0);
        assertThrows(NoSuchElementException.class, () -> emptyTailMap.firstKey());
        assertThrows(NoSuchElementException.class, () -> emptyTailMap.lastKey());

        SortedMap<Integer, String> nestedSubMap = subMapMiddle.subMap(isNaturalOrder ? 4 : 7, isNaturalOrder ? 7 : 4);
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(4), nestedSubMap.firstKey());
            assertEquals(Integer.valueOf(6), nestedSubMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(7), nestedSubMap.firstKey());
            assertEquals(Integer.valueOf(5), nestedSubMap.lastKey());
        }

        SortedMap<Integer, String> extremeMap = this.newWithKeysValues(
                Integer.MIN_VALUE, "Min", Integer.MIN_VALUE + 1, "MinPlusOne",
                Integer.MAX_VALUE - 1, "MaxMinusOne", Integer.MAX_VALUE, "Max");
        if (isNaturalOrder)
        {
            assertEquals(Integer.valueOf(Integer.MIN_VALUE), extremeMap.firstKey());
            assertEquals(Integer.valueOf(Integer.MAX_VALUE), extremeMap.lastKey());
        }
        else
        {
            assertEquals(Integer.valueOf(Integer.MAX_VALUE), extremeMap.firstKey());
            assertEquals(Integer.valueOf(Integer.MIN_VALUE), extremeMap.lastKey());
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
