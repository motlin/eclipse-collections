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

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface NavigableMapTestCase extends SortedMapTestCase
{
    @Override
    <T> NavigableMap<Object, T> newWith(T... elements);

    @Override
    <K, V> NavigableMap<K, V> newWithKeysValues(Object... elements);

    @Test
    default void NavigableMap_subMap_headMap_tailMap()
    {
        NavigableMap<Integer, String> map = this.newWithKeysValues(10, "10", 20, "20", 30, "30", 40, "40", 50, "50", 60, "60", 70, "70", 80, "80", 90, "90", 100, "100");
        Comparator<? super Integer> comparator = map.comparator();
        assertEquals(10, map.size());

        Iterator<Map.Entry<Integer, String>> iterator = map.entrySet().iterator();
        Integer first = iterator.next().getKey();
        Integer second = iterator.next().getKey();
        Integer third = iterator.next().getKey();
        Integer fourth = iterator.next().getKey();
        Integer fifth = iterator.next().getKey();
        Integer sixth = iterator.next().getKey();
        Integer seventh = iterator.next().getKey();
        Integer eighth = iterator.next().getKey();
        Integer ninth = iterator.next().getKey();
        Integer tenth = iterator.next().getKey();

        if (comparator != null)
        {
            assertEquals(-1, Integer.signum(comparator.compare(first, second)));
            assertEquals(-1, Integer.signum(comparator.compare(second, third)));
            assertEquals(-1, Integer.signum(comparator.compare(third, fourth)));
            assertEquals(-1, Integer.signum(comparator.compare(fourth, fifth)));
            assertEquals(-1, Integer.signum(comparator.compare(fifth, sixth)));
            assertEquals(-1, Integer.signum(comparator.compare(sixth, seventh)));
            assertEquals(-1, Integer.signum(comparator.compare(seventh, eighth)));
            assertEquals(-1, Integer.signum(comparator.compare(eighth, ninth)));
            assertEquals(-1, Integer.signum(comparator.compare(ninth, tenth)));
        }

        NavigableMap<Integer, String> subMapInclusive = map.subMap(third, true, seventh, true);
        assertEquals(5, subMapInclusive.size());
        assertTrue(subMapInclusive.containsKey(third));
        assertTrue(subMapInclusive.containsKey(seventh));
        assertFalse(subMapInclusive.containsKey(second));
        assertFalse(subMapInclusive.containsKey(eighth));

        NavigableMap<Integer, String> subMapExclusive = map.subMap(third, false, seventh, false);
        assertEquals(3, subMapExclusive.size());
        assertFalse(subMapExclusive.containsKey(third));
        assertFalse(subMapExclusive.containsKey(seventh));
        assertTrue(subMapExclusive.containsKey(fourth));
        assertTrue(subMapExclusive.containsKey(sixth));

        NavigableMap<Integer, String> subMapFromInclusive = map.subMap(third, true, seventh, false);
        assertEquals(4, subMapFromInclusive.size());
        assertTrue(subMapFromInclusive.containsKey(third));
        assertFalse(subMapFromInclusive.containsKey(seventh));

        NavigableMap<Integer, String> subMapToInclusive = map.subMap(third, false, seventh, true);
        assertEquals(4, subMapToInclusive.size());
        assertFalse(subMapToInclusive.containsKey(third));
        assertTrue(subMapToInclusive.containsKey(seventh));

        NavigableMap<Integer, String> subMapSingleElement = map.subMap(fifth, true, fifth, true);
        assertEquals(1, subMapSingleElement.size());
        assertTrue(subMapSingleElement.containsKey(fifth));

        NavigableMap<Integer, String> subMapSingleElementExclusive = map.subMap(fifth, false, fifth, false);
        assertEquals(0, subMapSingleElementExclusive.size());
        assertTrue(subMapSingleElementExclusive.isEmpty());

        NavigableMap<Integer, String> subMapWhole = map.subMap(first, true, tenth, true);
        assertEquals(10, subMapWhole.size());

        NavigableMap<Integer, String> subMapBoundaryLower = map.subMap(first, true, fifth, false);
        assertEquals(4, subMapBoundaryLower.size());
        assertTrue(subMapBoundaryLower.containsKey(first));
        assertFalse(subMapBoundaryLower.containsKey(fifth));

        NavigableMap<Integer, String> subMapBoundaryUpper = map.subMap(fifth, false, tenth, true);
        assertEquals(5, subMapBoundaryUpper.size());
        assertFalse(subMapBoundaryUpper.containsKey(fifth));
        assertTrue(subMapBoundaryUpper.containsKey(tenth));

        NavigableMap<Integer, String> headMapInclusive = map.headMap(fifth, true);
        assertEquals(5, headMapInclusive.size());
        assertTrue(headMapInclusive.containsKey(fifth));
        assertFalse(headMapInclusive.containsKey(sixth));

        NavigableMap<Integer, String> headMapExclusive = map.headMap(fifth, false);
        assertEquals(4, headMapExclusive.size());
        assertFalse(headMapExclusive.containsKey(fifth));
        assertTrue(headMapExclusive.containsKey(fourth));

        NavigableMap<Integer, String> headMapLowerBound = map.headMap(first, true);
        assertEquals(1, headMapLowerBound.size());
        assertTrue(headMapLowerBound.containsKey(first));

        NavigableMap<Integer, String> headMapLowerBoundExclusive = map.headMap(first, false);
        assertEquals(0, headMapLowerBoundExclusive.size());
        assertTrue(headMapLowerBoundExclusive.isEmpty());

        NavigableMap<Integer, String> headMapUpperBound = map.headMap(tenth, true);
        assertEquals(10, headMapUpperBound.size());

        NavigableMap<Integer, String> headMapUpperBoundExclusive = map.headMap(tenth, false);
        assertEquals(9, headMapUpperBoundExclusive.size());

        NavigableMap<Integer, String> tailMapInclusive = map.tailMap(fifth, true);
        assertEquals(6, tailMapInclusive.size());
        assertTrue(tailMapInclusive.containsKey(fifth));
        assertFalse(tailMapInclusive.containsKey(fourth));

        NavigableMap<Integer, String> tailMapExclusive = map.tailMap(fifth, false);
        assertEquals(5, tailMapExclusive.size());
        assertFalse(tailMapExclusive.containsKey(fifth));
        assertTrue(tailMapExclusive.containsKey(sixth));

        NavigableMap<Integer, String> tailMapLowerBound = map.tailMap(first, true);
        assertEquals(10, tailMapLowerBound.size());

        NavigableMap<Integer, String> tailMapLowerBoundExclusive = map.tailMap(first, false);
        assertEquals(9, tailMapLowerBoundExclusive.size());

        NavigableMap<Integer, String> tailMapUpperBound = map.tailMap(tenth, true);
        assertEquals(1, tailMapUpperBound.size());
        assertTrue(tailMapUpperBound.containsKey(tenth));

        NavigableMap<Integer, String> tailMapUpperBoundExclusive = map.tailMap(tenth, false);
        assertEquals(0, tailMapUpperBoundExclusive.size());
        assertTrue(tailMapUpperBoundExclusive.isEmpty());

        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.subMap(second, true, sixth, false));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.subMap(fourth, true, eighth, false));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.subMap(second, false, eighth, true));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.subMap(first, true, fourth, false));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.subMap(sixth, true, tenth, false));

        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.headMap(second, false));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.headMap(eighth, true));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.headMap(first, false));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.headMap(first, true));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.headMap(ninth, false));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.headMap(tenth, true));

        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.headMap(second, false));
        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.headMap(third, true));
        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.headMap(seventh, true));
        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.headMap(eighth, true));

        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(second, true));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(second, false));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(first, true));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(first, false));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(eighth, true));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(eighth, false));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(ninth, true));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(ninth, false));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(tenth, true));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(tenth, false));

        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.tailMap(second, true));
        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.tailMap(third, true));
        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.tailMap(eighth, true));

        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.put(second, "2"));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.put(eighth, "8"));

        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.put(second, "2"));
        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.put(third, "3"));
        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.put(seventh, "7"));
        assertThrows(IllegalArgumentException.class, () -> subMapExclusive.put(eighth, "8"));

        assertThrows(IllegalArgumentException.class, () -> headMapInclusive.put(sixth, "6"));
        assertThrows(IllegalArgumentException.class, () -> headMapInclusive.put(tenth, "10"));

        assertThrows(IllegalArgumentException.class, () -> headMapExclusive.put(fifth, "5"));
        assertThrows(IllegalArgumentException.class, () -> headMapExclusive.put(tenth, "10"));

        assertThrows(IllegalArgumentException.class, () -> tailMapInclusive.put(first, "1"));
        assertThrows(IllegalArgumentException.class, () -> tailMapInclusive.put(fourth, "4"));

        assertThrows(IllegalArgumentException.class, () -> tailMapExclusive.put(first, "1"));
        assertThrows(IllegalArgumentException.class, () -> tailMapExclusive.put(fifth, "5"));

        assertEquals(Map.entry(fourth, map.get(fourth)), subMapInclusive.lowerEntry(fifth));
        assertEquals(Map.entry(third, map.get(third)), subMapInclusive.lowerEntry(fourth));
        assertEquals(null, subMapInclusive.lowerEntry(third));
        assertEquals(Map.entry(sixth, map.get(sixth)), subMapInclusive.lowerEntry(seventh));
        assertEquals(null, subMapInclusive.lowerEntry(second));
        // TreeMap returns boundary entry for keys outside subMap range
        assertEquals(Map.entry(seventh, map.get(seventh)), subMapInclusive.lowerEntry(eighth));

        assertEquals(fourth, subMapInclusive.lowerKey(fifth));
        assertEquals(third, subMapInclusive.lowerKey(fourth));
        assertEquals(null, subMapInclusive.lowerKey(third));
        assertEquals(sixth, subMapInclusive.lowerKey(seventh));
        assertEquals(null, subMapInclusive.lowerKey(second));
        // TreeMap returns boundary key for keys outside subMap range
        assertEquals(seventh, subMapInclusive.lowerKey(eighth));

        assertEquals(Map.entry(fifth, map.get(fifth)), subMapInclusive.floorEntry(fifth));
        assertEquals(Map.entry(fourth, map.get(fourth)), subMapInclusive.floorEntry(fourth));
        assertEquals(Map.entry(third, map.get(third)), subMapInclusive.floorEntry(third));
        assertEquals(Map.entry(seventh, map.get(seventh)), subMapInclusive.floorEntry(seventh));
        assertEquals(null, subMapInclusive.floorEntry(second));
        // TreeMap returns boundary entry for keys outside subMap range
        assertEquals(Map.entry(seventh, map.get(seventh)), subMapInclusive.floorEntry(eighth));

        assertEquals(fifth, subMapInclusive.floorKey(fifth));
        assertEquals(fourth, subMapInclusive.floorKey(fourth));
        assertEquals(third, subMapInclusive.floorKey(third));
        assertEquals(seventh, subMapInclusive.floorKey(seventh));
        assertEquals(null, subMapInclusive.floorKey(second));
        // TreeMap returns boundary key for keys outside subMap range
        assertEquals(seventh, subMapInclusive.floorKey(eighth));

        assertEquals(Map.entry(fifth, map.get(fifth)), subMapInclusive.ceilingEntry(fifth));
        assertEquals(Map.entry(fourth, map.get(fourth)), subMapInclusive.ceilingEntry(fourth));
        assertEquals(Map.entry(third, map.get(third)), subMapInclusive.ceilingEntry(third));
        assertEquals(Map.entry(seventh, map.get(seventh)), subMapInclusive.ceilingEntry(seventh));
        assertEquals(Map.entry(third, map.get(third)), subMapInclusive.ceilingEntry(second));
        assertEquals(null, subMapInclusive.ceilingEntry(eighth));

        assertEquals(fifth, subMapInclusive.ceilingKey(fifth));
        assertEquals(fourth, subMapInclusive.ceilingKey(fourth));
        assertEquals(third, subMapInclusive.ceilingKey(third));
        assertEquals(seventh, subMapInclusive.ceilingKey(seventh));
        assertEquals(third, subMapInclusive.ceilingKey(second));
        assertEquals(null, subMapInclusive.ceilingKey(eighth));

        assertEquals(Map.entry(sixth, map.get(sixth)), subMapInclusive.higherEntry(fifth));
        assertEquals(Map.entry(fifth, map.get(fifth)), subMapInclusive.higherEntry(fourth));
        assertEquals(Map.entry(fourth, map.get(fourth)), subMapInclusive.higherEntry(third));
        assertEquals(null, subMapInclusive.higherEntry(seventh));
        assertEquals(Map.entry(third, map.get(third)), subMapInclusive.higherEntry(second));
        assertEquals(null, subMapInclusive.higherEntry(eighth));

        assertEquals(sixth, subMapInclusive.higherKey(fifth));
        assertEquals(fifth, subMapInclusive.higherKey(fourth));
        assertEquals(fourth, subMapInclusive.higherKey(third));
        assertEquals(null, subMapInclusive.higherKey(seventh));
        assertEquals(third, subMapInclusive.higherKey(second));
        assertEquals(null, subMapInclusive.higherKey(eighth));

        assertEquals(Map.entry(fourth, map.get(fourth)), subMapExclusive.lowerEntry(fifth));
        assertEquals(null, subMapExclusive.lowerEntry(fourth));
        assertEquals(null, subMapExclusive.lowerEntry(third));
        assertEquals(Map.entry(sixth, map.get(sixth)), subMapExclusive.lowerEntry(seventh));

        assertEquals(fourth, subMapExclusive.lowerKey(fifth));
        assertEquals(null, subMapExclusive.lowerKey(fourth));
        assertEquals(null, subMapExclusive.lowerKey(third));
        assertEquals(sixth, subMapExclusive.lowerKey(seventh));

        assertEquals(Map.entry(sixth, map.get(sixth)), subMapExclusive.floorEntry(sixth));
        assertEquals(Map.entry(fifth, map.get(fifth)), subMapExclusive.floorEntry(fifth));
        assertEquals(null, subMapExclusive.floorEntry(third));
        // TreeMap returns boundary entry for keys outside subMap range
        assertEquals(Map.entry(sixth, map.get(sixth)), subMapExclusive.floorEntry(seventh));

        assertEquals(sixth, subMapExclusive.floorKey(sixth));
        assertEquals(fifth, subMapExclusive.floorKey(fifth));
        assertEquals(null, subMapExclusive.floorKey(third));
        // TreeMap returns boundary key for keys outside subMap range
        assertEquals(sixth, subMapExclusive.floorKey(seventh));

        assertEquals(Map.entry(sixth, map.get(sixth)), subMapExclusive.ceilingEntry(sixth));
        assertEquals(Map.entry(fifth, map.get(fifth)), subMapExclusive.ceilingEntry(fifth));
        assertEquals(Map.entry(fourth, map.get(fourth)), subMapExclusive.ceilingEntry(third));
        assertEquals(null, subMapExclusive.ceilingEntry(seventh));

        assertEquals(sixth, subMapExclusive.ceilingKey(sixth));
        assertEquals(fifth, subMapExclusive.ceilingKey(fifth));
        assertEquals(fourth, subMapExclusive.ceilingKey(third));
        assertEquals(null, subMapExclusive.ceilingKey(seventh));

        assertEquals(null, subMapExclusive.higherEntry(sixth));
        assertEquals(Map.entry(sixth, map.get(sixth)), subMapExclusive.higherEntry(fifth));
        assertEquals(Map.entry(fourth, map.get(fourth)), subMapExclusive.higherEntry(third));
        assertEquals(null, subMapExclusive.higherEntry(seventh));

        assertEquals(null, subMapExclusive.higherKey(sixth));
        assertEquals(sixth, subMapExclusive.higherKey(fifth));
        assertEquals(fourth, subMapExclusive.higherKey(third));
        assertEquals(null, subMapExclusive.higherKey(seventh));

        assertEquals(Map.entry(fourth, map.get(fourth)), headMapInclusive.lowerEntry(fifth));
        assertEquals(null, headMapInclusive.lowerEntry(first));
        // TreeMap returns boundary entry for keys outside subMap range
        assertEquals(Map.entry(fifth, map.get(fifth)), headMapInclusive.lowerEntry(sixth));

        assertEquals(fourth, headMapInclusive.lowerKey(fifth));
        assertEquals(null, headMapInclusive.lowerKey(first));
        // TreeMap returns boundary key for keys outside subMap range
        assertEquals(fifth, headMapInclusive.lowerKey(sixth));

        assertEquals(Map.entry(fifth, map.get(fifth)), headMapInclusive.floorEntry(fifth));
        assertEquals(Map.entry(first, map.get(first)), headMapInclusive.floorEntry(first));
        // TreeMap returns boundary entry for keys outside subMap range
        assertEquals(Map.entry(fifth, map.get(fifth)), headMapInclusive.floorEntry(sixth));

        assertEquals(fifth, headMapInclusive.floorKey(fifth));
        assertEquals(first, headMapInclusive.floorKey(first));
        // TreeMap returns boundary key for keys outside subMap range
        assertEquals(fifth, headMapInclusive.floorKey(sixth));

        assertEquals(Map.entry(fifth, map.get(fifth)), headMapInclusive.ceilingEntry(fifth));
        assertEquals(Map.entry(first, map.get(first)), headMapInclusive.ceilingEntry(first));
        assertEquals(null, headMapInclusive.ceilingEntry(sixth));

        assertEquals(fifth, headMapInclusive.ceilingKey(fifth));
        assertEquals(first, headMapInclusive.ceilingKey(first));
        assertEquals(null, headMapInclusive.ceilingKey(sixth));

        assertEquals(null, headMapInclusive.higherEntry(fifth));
        assertEquals(Map.entry(second, map.get(second)), headMapInclusive.higherEntry(first));
        assertEquals(null, headMapInclusive.higherEntry(sixth));

        assertEquals(null, headMapInclusive.higherKey(fifth));
        assertEquals(second, headMapInclusive.higherKey(first));
        assertEquals(null, headMapInclusive.higherKey(sixth));

        assertEquals(Map.entry(third, map.get(third)), headMapExclusive.lowerEntry(fourth));
        assertEquals(null, headMapExclusive.lowerEntry(first));
        assertEquals(Map.entry(fourth, map.get(fourth)), headMapExclusive.lowerEntry(fifth));

        assertEquals(third, headMapExclusive.lowerKey(fourth));
        assertEquals(null, headMapExclusive.lowerKey(first));
        assertEquals(fourth, headMapExclusive.lowerKey(fifth));

        assertEquals(Map.entry(fourth, map.get(fourth)), headMapExclusive.floorEntry(fourth));
        assertEquals(Map.entry(first, map.get(first)), headMapExclusive.floorEntry(first));
        assertEquals(Map.entry(fourth, map.get(fourth)), headMapExclusive.floorEntry(fifth));

        assertEquals(fourth, headMapExclusive.floorKey(fourth));
        assertEquals(first, headMapExclusive.floorKey(first));
        assertEquals(fourth, headMapExclusive.floorKey(fifth));

        assertEquals(Map.entry(fourth, map.get(fourth)), headMapExclusive.ceilingEntry(fourth));
        assertEquals(Map.entry(first, map.get(first)), headMapExclusive.ceilingEntry(first));
        assertEquals(null, headMapExclusive.ceilingEntry(fifth));

        assertEquals(fourth, headMapExclusive.ceilingKey(fourth));
        assertEquals(first, headMapExclusive.ceilingKey(first));
        assertEquals(null, headMapExclusive.ceilingKey(fifth));

        assertEquals(null, headMapExclusive.higherEntry(fourth));
        assertEquals(Map.entry(second, map.get(second)), headMapExclusive.higherEntry(first));
        assertEquals(null, headMapExclusive.higherEntry(fifth));

        assertEquals(null, headMapExclusive.higherKey(fourth));
        assertEquals(second, headMapExclusive.higherKey(first));
        assertEquals(null, headMapExclusive.higherKey(fifth));

        assertEquals(Map.entry(ninth, map.get(ninth)), tailMapInclusive.lowerEntry(tenth));
        assertEquals(null, tailMapInclusive.lowerEntry(fifth));
        assertEquals(null, tailMapInclusive.lowerEntry(fourth));

        assertEquals(ninth, tailMapInclusive.lowerKey(tenth));
        assertEquals(null, tailMapInclusive.lowerKey(fifth));
        assertEquals(null, tailMapInclusive.lowerKey(fourth));

        assertEquals(Map.entry(tenth, map.get(tenth)), tailMapInclusive.floorEntry(tenth));
        assertEquals(Map.entry(fifth, map.get(fifth)), tailMapInclusive.floorEntry(fifth));
        assertEquals(null, tailMapInclusive.floorEntry(fourth));

        assertEquals(tenth, tailMapInclusive.floorKey(tenth));
        assertEquals(fifth, tailMapInclusive.floorKey(fifth));
        assertEquals(null, tailMapInclusive.floorKey(fourth));

        assertEquals(Map.entry(tenth, map.get(tenth)), tailMapInclusive.ceilingEntry(tenth));
        assertEquals(Map.entry(fifth, map.get(fifth)), tailMapInclusive.ceilingEntry(fifth));
        assertEquals(Map.entry(fifth, map.get(fifth)), tailMapInclusive.ceilingEntry(fourth));

        assertEquals(tenth, tailMapInclusive.ceilingKey(tenth));
        assertEquals(fifth, tailMapInclusive.ceilingKey(fifth));
        assertEquals(fifth, tailMapInclusive.ceilingKey(fourth));

        assertEquals(null, tailMapInclusive.higherEntry(tenth));
        assertEquals(Map.entry(sixth, map.get(sixth)), tailMapInclusive.higherEntry(fifth));
        assertEquals(Map.entry(fifth, map.get(fifth)), tailMapInclusive.higherEntry(fourth));

        assertEquals(null, tailMapInclusive.higherKey(tenth));
        assertEquals(sixth, tailMapInclusive.higherKey(fifth));
        assertEquals(fifth, tailMapInclusive.higherKey(fourth));

        assertEquals(Map.entry(ninth, map.get(ninth)), tailMapExclusive.lowerEntry(tenth));
        assertEquals(Map.entry(sixth, map.get(sixth)), tailMapExclusive.lowerEntry(seventh));
        assertEquals(null, tailMapExclusive.lowerEntry(sixth));

        assertEquals(ninth, tailMapExclusive.lowerKey(tenth));
        assertEquals(sixth, tailMapExclusive.lowerKey(seventh));
        assertEquals(null, tailMapExclusive.lowerKey(sixth));

        assertEquals(Map.entry(tenth, map.get(tenth)), tailMapExclusive.floorEntry(tenth));
        assertEquals(Map.entry(seventh, map.get(seventh)), tailMapExclusive.floorEntry(seventh));
        assertEquals(null, tailMapExclusive.floorEntry(fifth));

        assertEquals(tenth, tailMapExclusive.floorKey(tenth));
        assertEquals(seventh, tailMapExclusive.floorKey(seventh));
        assertEquals(null, tailMapExclusive.floorKey(fifth));

        assertEquals(Map.entry(tenth, map.get(tenth)), tailMapExclusive.ceilingEntry(tenth));
        assertEquals(Map.entry(seventh, map.get(seventh)), tailMapExclusive.ceilingEntry(seventh));
        // TreeMap returns boundary entry for keys outside subMap range
        assertEquals(Map.entry(sixth, map.get(sixth)), tailMapExclusive.ceilingEntry(fifth));

        assertEquals(tenth, tailMapExclusive.ceilingKey(tenth));
        assertEquals(seventh, tailMapExclusive.ceilingKey(seventh));
        // TreeMap returns boundary key for keys outside subMap range
        assertEquals(sixth, tailMapExclusive.ceilingKey(fifth));

        assertEquals(null, tailMapExclusive.higherEntry(tenth));
        assertEquals(Map.entry(eighth, map.get(eighth)), tailMapExclusive.higherEntry(seventh));
        assertEquals(Map.entry(sixth, map.get(sixth)), tailMapExclusive.higherEntry(fifth));

        assertEquals(null, tailMapExclusive.higherKey(tenth));
        assertEquals(eighth, tailMapExclusive.higherKey(seventh));
        // TreeMap returns boundary key for keys outside subMap range
        assertEquals(sixth, tailMapExclusive.higherKey(fifth));

        if (comparator != null && comparator.compare(first, second) == -1 && first.compareTo(second) == 1)
        {
            assertThrows(IllegalArgumentException.class, () -> map.subMap(ninth, true, first, false));
        }
    }
}
