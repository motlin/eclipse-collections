/*
 * Copyright (c) 2015 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.factory.map;

import java.util.Map;

import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableMap;

public interface MutableMapFactory
        extends MapFactory
{
    /**
     * @since 6.0
     */
    @Override
    <K, V> MutableMap<K, V> empty();

    /**
     * Same as {@link #empty()}.
     */
    @Override
    <K, V> MutableMap<K, V> of();

    /**
     * Same as {@link #empty()}.
     */
    @Override
    <K, V> MutableMap<K, V> with();

    /**
     * Same as {@link #empty()}. but takes in an initial capacity
     */
    @Override
    <K, V> MutableMap<K, V> ofInitialCapacity(int capacity);

    /**
     * Same as {@link #empty()}. but takes in an initial capacity
     */
    @Override
    <K, V> MutableMap<K, V> withInitialCapacity(int capacity);

    /**
     * Same as {@link #with(Object, Object)}.
     */
    @Override
    <K, V> MutableMap<K, V> of(K key, V value);

    @Override
    <K, V> MutableMap<K, V> with(K key, V value);

    /**
     * Same as {@link #with(Object, Object, Object, Object)}.
     */
    @Override
    <K, V> MutableMap<K, V> of(K key1, V value1, K key2, V value2);

    @Override
    <K, V> MutableMap<K, V> with(K key1, V value1, K key2, V value2);

    /**
     * Same as {@link #with(Object, Object, Object, Object, Object, Object)}.
     */
    @Override
    <K, V> MutableMap<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3);

    @Override
    <K, V> MutableMap<K, V> with(K key1, V value1, K key2, V value2, K key3, V value3);

    /**
     * Same as {@link #with(Object, Object, Object, Object, Object, Object, Object, Object)}.
     */
    @Override
    <K, V> MutableMap<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4);

    @Override
    <K, V> MutableMap<K, V> with(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4);

    @Override
    <K, V> MutableMap<K, V> ofMap(Map<? extends K, ? extends V> map);

    @Override
    <K, V> MutableMap<K, V> withMap(Map<? extends K, ? extends V> map);

    @Override
    <K, V> MutableMap<K, V> ofMapIterable(MapIterable<? extends K, ? extends V> mapIterable);

    @Override
    <K, V> MutableMap<K, V> withMapIterable(MapIterable<? extends K, ? extends V> mapIterable);
}
