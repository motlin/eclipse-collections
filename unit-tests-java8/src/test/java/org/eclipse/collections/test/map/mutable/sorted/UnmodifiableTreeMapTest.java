/*
 * Copyright (c) 2022 Goldman Sachs and others.
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
import java.util.Map;

import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.map.sorted.mutable.UnmodifiableTreeMap;
import org.eclipse.collections.impl.tuple.ImmutableEntry;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class UnmodifiableTreeMapTest implements UnmodifiableMutableSortedMapTestCase
{
    @Override
    public <T> MutableSortedMap<Object, T> newWith(T... elements)
    {
        int i = elements.length;
        MutableSortedMap<Object, T> result = new TreeSortedMap<>(Comparators.reverseNaturalOrder());
        for (T each : elements)
        {
            assertNull(result.put(i, each));
            i--;
        }
        return UnmodifiableTreeMap.of(result);
    }

    @Override
    public <K, V> MutableSortedMap<K, V> newWithKeysValues(Object... elements)
    {
        if (elements.length % 2 != 0)
        {
            fail(String.valueOf(elements.length));
        }

        MutableSortedMap<K, V> result = new TreeSortedMap<>(Comparators.reverseNaturalOrder());
        for (int i = 0; i < elements.length; i += 2)
        {
            assertNull(result.put((K) elements[i], (V) elements[i + 1]));
        }
        return UnmodifiableTreeMap.of(result);
    }

    @Override
    @Test
    public void Map_clear()
    {
        MutableSortedMap<Object, String> map = this.newWith("Three", "Two", "One");
        assertThrows(UnsupportedOperationException.class, map::clear);
    }

    @Override
    @Test
    public void Map_remove()
    {
        MutableSortedMap<Object, Object> map = this.newWith();
        assertThrows(UnsupportedOperationException.class, () -> map.remove(2));
    }

    @Override
    @Test
    public void Map_entrySet_remove()
    {
        MutableSortedMap<Object, Object> map = this.newWithKeysValues();
        assertThrows(UnsupportedOperationException.class, () -> map.entrySet().remove(ImmutableEntry.of(null, null)));
    }

    @Override
    @Test
    public void Map_entrySet_setValue()
    {
        Map<String, Integer> map = this.newWithKeysValues("3", 3, "2", 2, "1", 1);
        map.entrySet().forEach(each -> assertThrows(UnsupportedOperationException.class, () -> each.setValue(each.getValue() + 1)));
        assertIterablesEqual(this.newWithKeysValues("3", 3, "2", 2, "1", 1), map);
    }

    @Override
    @Test
    public void Map_put()
    {
        MutableSortedMap<Integer, String> map = this.newWithKeysValues(3, "Three", 2, "Two", 1, "One");
        assertThrows(UnsupportedOperationException.class, () -> map.put(4, "Four"));
        assertThrows(UnsupportedOperationException.class, () -> map.put(1, "One"));
        assertThrows(UnsupportedOperationException.class, () -> map.put(5, null));
        assertThrows(UnsupportedOperationException.class, () -> map.put(null, "Six"));
        assertIterablesEqual(this.newWithKeysValues(3, "Three", 2, "Two", 1, "One"), map);
    }

    @Override
    @Test
    public void Map_putAll()
    {
        MutableSortedMap<Integer, String> map = this.newWithKeysValues(3, "Three", 2, "2");
        MutableSortedMap<Integer, String> toAdd = this.newWithKeysValues(2, "Two", 1, "One");

        assertThrows(UnsupportedOperationException.class, () -> map.putAll(toAdd));

        MutableSortedMap<Integer, String> expected = this.newWithKeysValues(3, "Three", 2, "2");
        assertIterablesEqual(expected, map);

        assertThrows(UnsupportedOperationException.class, () -> map.putAll(null));
        assertThrows(UnsupportedOperationException.class, () -> map.putAll(Map.of()));
    }

    @Override
    @Test
    public void Map_merge()
    {
        MutableSortedMap<Integer, String> map = this.newWithKeysValues(1, "1", 2, "2", 3, "3");
        assertThrows(UnsupportedOperationException.class, () -> map.merge(3, "4", (v1, v2) -> {
            fail("Expected lambda not to be called on unmodifiable map");
            return null;
        }));
        assertThrows(UnsupportedOperationException.class, () -> map.merge(4, "4", (v1, v2) -> {
            fail("Expected lambda not to be called on unmodifiable map");
            return null;
        }));
        assertEquals(this.newWithKeysValues(1, "1", 2, "2", 3, "3"), map);
    }

    @Override
    @Test
    public void Map_compute()
    {
        Map<Integer, String> map = this.newWithKeysValues(1, "1", 2, "2", 3, "3");

        assertThrows(UnsupportedOperationException.class, () -> map.compute(1, null));

        assertThrows(UnsupportedOperationException.class, () -> map.compute(1, (k, v) -> {
            fail("Expected lambda not to be called for existing key");
            return "Should not be returned";
        }));
        assertEquals(this.newWithKeysValues(1, "1", 2, "2", 3, "3"), map);

        assertThrows(UnsupportedOperationException.class, () -> map.compute(4, (k, v) -> {
            fail("Expected lambda not to be called for non-existing key");
            return "Should not be returned";
        }));
        assertEquals(this.newWithKeysValues(1, "1", 2, "2", 3, "3"), map);
    }

    @Override
    @Test
    public void Map_computeIfAbsent()
    {
        Map<Integer, String> map = this.newWithKeysValues(1, "1", 2, "2", 3, "3");

        assertThrows(UnsupportedOperationException.class, () -> map.computeIfAbsent(1, null));

        assertThrows(UnsupportedOperationException.class, () -> map.computeIfAbsent(1, k -> {
            fail("Expected lambda not to be called for existing key");
            return "Should not be returned";
        }));
        assertEquals(this.newWithKeysValues(1, "1", 2, "2", 3, "3"), map);

        assertThrows(UnsupportedOperationException.class, () -> map.computeIfAbsent(4, k -> {
            fail("Expected lambda not to be called for non-existing key");
            return "Should not be returned";
        }));
        assertEquals(this.newWithKeysValues(1, "1", 2, "2", 3, "3"), map);
    }

    @Override
    @Test
    public void Map_computeIfPresent()
    {
        Map<Integer, String> map = this.newWithKeysValues(1, "1", 2, "2", 3, "3");

        assertThrows(UnsupportedOperationException.class, () -> map.computeIfPresent(1, null));

        assertThrows(UnsupportedOperationException.class, () -> map.computeIfPresent(1, (k, v) -> {
            fail("Expected lambda not to be called for existing key");
            return "Should not be returned";
        }));
        assertEquals(this.newWithKeysValues(1, "1", 2, "2", 3, "3"), map);

        assertThrows(UnsupportedOperationException.class, () -> map.computeIfPresent(4, (k, v) -> {
            fail("Expected lambda not to be called for non-existing key");
            return "Should not be returned";
        }));
        assertEquals(this.newWithKeysValues(1, "1", 2, "2", 3, "3"), map);
    }

    @Override
    @Test
    public void Map_replaceAll()
    {
        Map<Integer, String> map = this.newWithKeysValues(1, "1", 2, "2", 3, "3");

        assertThrows(UnsupportedOperationException.class, () -> map.replaceAll(null));

        assertThrows(UnsupportedOperationException.class, () -> map.replaceAll((k, v) -> {
            fail("Expected lambda not to be called for existing key");
            return "Should not be returned";
        }));
        assertEquals(this.newWithKeysValues(1, "1", 2, "2", 3, "3"), map);

        Map<Integer, String> emptyMap = this.newWithKeysValues();
        assertThrows(UnsupportedOperationException.class, () -> emptyMap.replaceAll((k, v) -> {
            fail("Expected lambda not to be called for empty map");
            return "Should not be returned";
        }));
        assertEquals(this.newWithKeysValues(), emptyMap);
    }

    /**
     * TODO: UnmodifiableTreeMap differs from JDK behavior for submap views.
     * In JDK's Collections.unmodifiableSortedMap(), calling put() on a submap view
     * with an out-of-range key throws IllegalArgumentException (range validation occurs first),
     * but UnmodifiableTreeMap throws UnsupportedOperationException (mutability check occurs first).
     *
     * This override expects UnsupportedOperationException for out-of-range puts on submap views.
     * To match JDK behavior, UnmodifiableTreeMap.put() should validate the key range
     * before throwing UnsupportedOperationException.
     */
    @Override
    @Test
    public void SortedMap_subMap_headMap_tailMap()
    {
        MutableSortedMap<Integer, String> map = this.newWithKeysValues(
                1, "One", 2, "Two", 3, "Three", 4, "Four", 5, "Five",
                6, "Six", 7, "Seven", 8, "Eight", 9, "Nine", 10, "Ten");
        Comparator<? super Integer> comparator = map.comparator();

        boolean isNaturalOrder = comparator == null;
        boolean isReverseOrder = comparator != null &&
                (comparator.equals(Collections.reverseOrder()) || comparator.equals(Comparators.reverseNaturalOrder()));

        org.junit.jupiter.api.Assertions.assertTrue(isNaturalOrder || isReverseOrder,
                "Comparator must be either null (natural order) or reverse order");

        if (!isReverseOrder)
        {
            UnmodifiableMutableSortedMapTestCase.super.SortedMap_subMap_headMap_tailMap();
            return;
        }

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

        MutableSortedMap<Integer, String> subMapBetweenElements = map.subMap(9, 2);
        assertIterablesEqual(this.newWithKeysValues(7, "Seven", 6, "Six", 5, "Five"), subMapBetweenElements.subMap(7, 4));
        assertIterablesEqual(this.newWithKeysValues(9, "Nine", 8, "Eight", 7, "Seven"), subMapBetweenElements.headMap(6));
        assertIterablesEqual(this.newWithKeysValues(5, "Five", 4, "Four", 3, "Three"), subMapBetweenElements.tailMap(5));

        assertThrows(IllegalArgumentException.class, () -> subMapBetweenElements.subMap(5, 10));
        assertThrows(IllegalArgumentException.class, () -> subMapBetweenElements.subMap(1, 5));

        assertThrows(IllegalArgumentException.class, () -> subMapBetweenElements.headMap(10));
        assertThrows(IllegalArgumentException.class, () -> subMapBetweenElements.headMap(1));

        assertThrows(IllegalArgumentException.class, () -> subMapBetweenElements.tailMap(10));
        assertThrows(IllegalArgumentException.class, () -> subMapBetweenElements.tailMap(1));

        assertThrows(UnsupportedOperationException.class, () -> subMapBetweenElements.put(10, "AboveRange"));
        assertThrows(UnsupportedOperationException.class, () -> subMapBetweenElements.put(2, "AtLowerBound"));
        assertThrows(UnsupportedOperationException.class, () -> subMapBetweenElements.put(1, "BelowRange"));

        assertThrows(IllegalArgumentException.class, () -> map.subMap(5, 10));
        assertThrows(IllegalArgumentException.class, () -> map.subMap(2, 7));
        assertThrows(IllegalArgumentException.class, () -> map.subMap(1, 9));
        assertThrows(IllegalArgumentException.class, () -> map.subMap(Integer.MIN_VALUE, Integer.MAX_VALUE));

        MutableSortedMap<Integer, String> largeMap = this.newWithKeysValues(
                10, "Ten", 20, "Twenty", 30, "Thirty", 40, "Forty", 50, "Fifty",
                60, "Sixty", 70, "Seventy", 80, "Eighty", 90, "Ninety", 100, "Hundred");

        assertIterablesEqual(this.newWithKeysValues(80, "Eighty", 70, "Seventy", 60, "Sixty", 50, "Fifty", 40, "Forty", 30, "Thirty"), largeMap.subMap(80, 20));
        assertIterablesEqual(this.newWithKeysValues(70, "Seventy", 60, "Sixty", 50, "Fifty", 40, "Forty", 30, "Thirty"), largeMap.subMap(75, 25));
        assertIterablesEqual(this.newWithKeysValues(100, "Hundred", 90, "Ninety", 80, "Eighty", 70, "Seventy", 60, "Sixty"), largeMap.headMap(55));
        assertIterablesEqual(this.newWithKeysValues(50, "Fifty", 40, "Forty", 30, "Thirty", 20, "Twenty", 10, "Ten"), largeMap.tailMap(55));

        MutableSortedMap<Integer, String> singleElementMap = this.newWithKeysValues(42, "FortyTwo");
        assertIterablesEqual(this.newWithKeysValues(42, "FortyTwo"), singleElementMap.subMap(100, 0));
        assertIterablesEqual(this.newWithKeysValues(42, "FortyTwo"), singleElementMap.subMap(42, 41));
        assertIterablesEqual(this.newWithKeysValues(), singleElementMap.subMap(41, 0));
        assertIterablesEqual(this.newWithKeysValues(42, "FortyTwo"), singleElementMap.headMap(0));
        assertIterablesEqual(this.newWithKeysValues(), singleElementMap.headMap(50));
        assertIterablesEqual(this.newWithKeysValues(42, "FortyTwo"), singleElementMap.tailMap(50));
        assertIterablesEqual(this.newWithKeysValues(), singleElementMap.tailMap(0));

        MutableSortedMap<Integer, String> emptyMap = this.newWithKeysValues();
        assertIterablesEqual(this.newWithKeysValues(), emptyMap.subMap(1, -10));
        assertIterablesEqual(this.newWithKeysValues(), emptyMap.headMap(5));
        assertIterablesEqual(this.newWithKeysValues(), emptyMap.tailMap(5));

        MutableSortedMap<Integer, String> boundaryMap = this.newWithKeysValues(
                Integer.MIN_VALUE, "Min", -100, "NegativeHundred", 0, "Zero", 100, "Hundred", Integer.MAX_VALUE, "Max");

        assertIterablesEqual(this.newWithKeysValues(Integer.MAX_VALUE, "Max", 100, "Hundred", 0, "Zero"), boundaryMap.subMap(Integer.MAX_VALUE, -100));
        assertIterablesEqual(this.newWithKeysValues(Integer.MAX_VALUE, "Max", 100, "Hundred", 0, "Zero"), boundaryMap.headMap(-100));
        assertIterablesEqual(this.newWithKeysValues(0, "Zero", -100, "NegativeHundred", Integer.MIN_VALUE, "Min"), boundaryMap.tailMap(0));
        assertIterablesEqual(this.newWithKeysValues(-100, "NegativeHundred"), boundaryMap.subMap(-100, Integer.MIN_VALUE));
        assertIterablesEqual(this.newWithKeysValues(Integer.MAX_VALUE, "Max"), boundaryMap.subMap(Integer.MAX_VALUE, Integer.MAX_VALUE - 1));

        MutableSortedMap<Integer, String> consecutiveMap = this.newWithKeysValues(1, "A", 2, "B", 3, "C", 4, "D", 5, "E", 6, "F", 7, "G");
        MutableSortedMap<Integer, String> middleSubMap = consecutiveMap.subMap(5, 2);
        assertIterablesEqual(this.newWithKeysValues(5, "E", 4, "D", 3, "C"), middleSubMap);

        MutableSortedMap<Integer, String> nestedHeadMap = middleSubMap.headMap(3);
        assertIterablesEqual(this.newWithKeysValues(5, "E", 4, "D"), nestedHeadMap);

        MutableSortedMap<Integer, String> nestedTailMap = middleSubMap.tailMap(4);
        assertIterablesEqual(this.newWithKeysValues(4, "D", 3, "C"), nestedTailMap);

        MutableSortedMap<Integer, String> deeplyNestedSubMap = middleSubMap.subMap(5, 3);
        assertIterablesEqual(this.newWithKeysValues(5, "E", 4, "D"), deeplyNestedSubMap);

        assertEquals(5, middleSubMap.firstKey());
        assertEquals(3, middleSubMap.lastKey());
        assertEquals(5, nestedHeadMap.firstKey());
        assertEquals(4, nestedHeadMap.lastKey());
        assertEquals(4, nestedTailMap.firstKey());
        assertEquals(3, nestedTailMap.lastKey());
    }
}
