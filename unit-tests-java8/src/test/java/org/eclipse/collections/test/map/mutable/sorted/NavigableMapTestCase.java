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

        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(second, true));
        assertThrows(IllegalArgumentException.class, () -> subMapInclusive.tailMap(eighth, false));

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

        if (comparator != null && comparator.compare(first, second) == -1 && first.compareTo(second) == 1)
        {
            assertThrows(IllegalArgumentException.class, () -> map.subMap(ninth, true, first, false));
        }
    }
}
