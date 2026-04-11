/*
 * Copyright (c) 2021 Two Sigma and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.map.mutable.ordered;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.collections.api.map.ImmutableOrderedMap;
import org.eclipse.collections.api.map.MutableOrderedMap;
import org.eclipse.collections.test.MutableOrderedIterableTestCase;
import org.eclipse.collections.test.map.OrderedMapIterableTestCase;
import org.eclipse.collections.test.map.mutable.MutableMapIterableTestCase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public interface MutableOrderedMapTestCase extends OrderedMapIterableTestCase, MutableMapIterableTestCase, MutableOrderedIterableTestCase
{
    @Override
    <T> MutableOrderedMap<Object, T> newWith(T... elements);

    @Override
    <K, V> MutableOrderedMap<K, V> newWithKeysValues(Object... elements);

    @Override
    @Test
    default void Iterable_remove()
    {
        MutableOrderedIterableTestCase.super.Iterable_remove();
    }

    @Override
    @Test
    default void MutableMapIterable_removeKey()
    {
        MutableMapIterableTestCase.super.MutableMapIterable_removeKey();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3, "D", 4);
        map.removeKey("B");
        List<String> keys = new ArrayList<>(map.keySet());
        assertEquals(List.of("A", "C", "D"), keys);
    }

    @Override
    @Test
    default void Map_keySet_forEach()
    {
        MutableMapIterableTestCase.super.Map_keySet_forEach();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        List<String> keys = new ArrayList<>();
        map.keySet().forEach(keys::add);
        assertEquals(List.of("A", "B", "C"), keys);
    }

    @Override
    @Test
    default void Map_entrySet_forEach()
    {
        MutableMapIterableTestCase.super.Map_entrySet_forEach();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        map.entrySet().forEach(entry ->
        {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        });
        assertEquals(List.of("A", "B", "C"), keys);
        assertEquals(List.of(1, 2, 3), values);
    }

    @Test
    default void Map_values_forEach()
    {
        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        List<Integer> values = new ArrayList<>();
        map.values().forEach(values::add);
        assertEquals(List.of(1, 2, 3), values);
    }

    @Override
    @Test
    default void Map_merge()
    {
        MutableMapIterableTestCase.super.Map_merge();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);

        // merge new key adds at end
        map.merge("D", 4, Integer::sum);
        assertEquals(List.of("A", "B", "C", "D"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(4), map.get("D"));

        // merge existing key updates value without changing order
        map.merge("B", 10, Integer::sum);
        assertEquals(List.of("A", "B", "C", "D"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(12), map.get("B"));

        // merge returning null removes key and remaining order is preserved
        map.merge("C", 0, (oldVal, newVal) -> null);
        assertEquals(List.of("A", "B", "D"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(1), map.get("A"));
        assertEquals(Integer.valueOf(12), map.get("B"));
        assertEquals(Integer.valueOf(4), map.get("D"));
    }

    @Override
    @Test
    default void Map_replaceAll()
    {
        MutableMapIterableTestCase.super.Map_replaceAll();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        map.replaceAll((k, v) -> v * 10);
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));
        assertEquals(List.of(10, 20, 30), new ArrayList<>(map.values()));
    }

    @Override
    @Test
    default void Map_put()
    {
        MutableMapIterableTestCase.super.Map_put();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        map.put("B", 99);
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(99), map.get("B"));

        map.put("D", 4);
        assertEquals(List.of("A", "B", "C", "D"), new ArrayList<>(map.keySet()));
    }

    @Override
    @Test
    default void Map_putAll()
    {
        MutableMapIterableTestCase.super.Map_putAll();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2);
        LinkedHashMap<String, Integer> source = new LinkedHashMap<>();
        source.put("D", 4);
        source.put("C", 3);
        map.putAll(source);
        assertEquals(List.of("A", "B", "D", "C"), new ArrayList<>(map.keySet()));

        // putAll with overlapping keys updates values, positions unchanged
        LinkedHashMap<String, Integer> overlap = new LinkedHashMap<>();
        overlap.put("B", 20);
        overlap.put("E", 5);
        map.putAll(overlap);
        assertEquals(List.of("A", "B", "D", "C", "E"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(20), map.get("B"));
    }

    @Override
    @Test
    default void Map_compute()
    {
        MutableMapIterableTestCase.super.Map_compute();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        map.compute("B", (k, v) -> v + 10);
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(12), map.get("B"));

        map.compute("D", (k, v) -> 4);
        assertEquals(List.of("A", "B", "C", "D"), new ArrayList<>(map.keySet()));

        map.compute("B", (k, v) -> null);
        assertEquals(List.of("A", "C", "D"), new ArrayList<>(map.keySet()));
    }

    @Override
    @Test
    default void Map_computeIfAbsent()
    {
        MutableMapIterableTestCase.super.Map_computeIfAbsent();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2);
        map.computeIfAbsent("C", k -> 3);
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));

        map.computeIfAbsent("B", k -> 99);
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(2), map.get("B"));
    }

    @Override
    @Test
    default void Map_computeIfPresent()
    {
        MutableMapIterableTestCase.super.Map_computeIfPresent();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        map.computeIfPresent("B", (k, v) -> v + 10);
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(12), map.get("B"));

        map.computeIfPresent("D", (k, v) -> 4);
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));
        assertNull(map.get("D"));
    }

    @Override
    @Test
    default void MutableMapIterable_getIfAbsentPut()
    {
        MutableMapIterableTestCase.super.MutableMapIterable_getIfAbsentPut();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2);
        map.getIfAbsentPut("C", () -> 3);
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));

        map.getIfAbsentPut("B", () -> 99);
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(2), map.get("B"));
    }

    @Override
    @Test
    default void MutableMapIterable_updateValue()
    {
        MutableMapIterableTestCase.super.MutableMapIterable_updateValue();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2);
        map.updateValue("B", () -> 0, v -> v + 10);
        assertEquals(List.of("A", "B"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(12), map.get("B"));

        map.updateValue("C", () -> 0, v -> v + 3);
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));
        assertEquals(Integer.valueOf(3), map.get("C"));
    }

    @Override
    @Test
    default void Map_entrySet_setValue()
    {
        MutableMapIterableTestCase.super.Map_entrySet_setValue();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            if ("B".equals(entry.getKey()))
            {
                entry.setValue(20);
            }
        }
        assertEquals(List.of("A", "B", "C"), new ArrayList<>(map.keySet()));
        assertEquals(List.of(1, 20, 3), new ArrayList<>(map.values()));
    }

    @Override
    @Test
    default void MutableMapIterable_removeIf()
    {
        MutableMapIterableTestCase.super.MutableMapIterable_removeIf();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3, "D", 4, "E", 5);
        map.removeIf((k, v) -> v % 2 == 0);
        assertEquals(List.of("A", "C", "E"), new ArrayList<>(map.keySet()));
        assertEquals(List.of(1, 3, 5), new ArrayList<>(map.values()));
    }

    @Override
    @Test
    default void MapIterable_forEachKeyValue()
    {
        MutableMapIterableTestCase.super.MapIterable_forEachKeyValue();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        map.forEachKeyValue((k, v) ->
        {
            keys.add(k);
            values.add(v);
        });
        assertEquals(List.of("A", "B", "C"), keys);
        assertEquals(List.of(1, 2, 3), values);
    }

    @Override
    @Test
    default void MapIterable_forEachKey()
    {
        MutableMapIterableTestCase.super.MapIterable_forEachKey();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        List<String> keys = new ArrayList<>();
        map.forEachKey(keys::add);
        assertEquals(List.of("A", "B", "C"), keys);
    }

    @Override
    @Test
    default void MapIterable_forEachValue()
    {
        MutableMapIterableTestCase.super.MapIterable_forEachValue();

        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        List<Integer> values = new ArrayList<>();
        map.forEachValue(values::add);
        assertEquals(List.of(1, 2, 3), values);
    }

    @Test
    default void InternalIterable_forEachWithIndex()
    {
        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        List<Integer> values = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        map.forEachWithIndex((v, i) ->
        {
            values.add(v);
            indices.add(i);
        });
        assertEquals(List.of(1, 2, 3), values);
        assertEquals(List.of(0, 1, 2), indices);
    }

    @Test
    default void RichIterable_iterator_iterationOrder()
    {
        MutableOrderedMap<String, Integer> map = this.newWithKeysValues("A", 1, "B", 2, "C", 3);
        List<Integer> values = new ArrayList<>();
        for (Integer v : map)
        {
            values.add(v);
        }
        assertEquals(List.of(1, 2, 3), values);
    }

    @Test
    default void MutableOrderedMap_toImmutable()
    {
        MutableOrderedMap<String, Integer> mutable = this.newWithKeysValues("A", 1, "B", 2);
        ImmutableOrderedMap<String, Integer> immutable = mutable.toImmutable();
        mutable.put("C", 3);
        assertEquals(3, mutable.size());
        assertEquals(2, immutable.size());
    }
}
