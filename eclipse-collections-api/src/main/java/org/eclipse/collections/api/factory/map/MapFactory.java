/*
 * Copyright (c) 2020 Goldman Sachs and others.
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

public interface MapFactory
{
    <K, V> MapIterable<K, V> empty();

    /**
     * Same as {@link #empty()}.
     */
    <K, V> MapIterable<K, V> of();

    /**
     * Same as {@link #empty()}.
     */
    <K, V> MapIterable<K, V> with();

    /**
     * Same as {@link #empty()}. but takes in an initial capacity
     */
    <K, V> MapIterable<K, V> ofInitialCapacity(int capacity);

    /**
     * Same as {@link #empty()}. but takes in an initial capacity
     */
    <K, V> MapIterable<K, V> withInitialCapacity(int capacity);

    /**
     * Same as {@link #with(Object, Object)}.
     */
    <K, V> MapIterable<K, V> of(K key, V value);

    <K, V> MapIterable<K, V> with(K key, V value);

    /**
     * Same as {@link #with(Object, Object, Object, Object)}.
     */
    <K, V> MapIterable<K, V> of(K key1, V value1, K key2, V value2);

    <K, V> MapIterable<K, V> with(K key1, V value1, K key2, V value2);

    /**
     * Same as {@link #with(Object, Object, Object, Object, Object, Object)}.
     */
    <K, V> MapIterable<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3);

    <K, V> MapIterable<K, V> with(K key1, V value1, K key2, V value2, K key3, V value3);

    /**
     * Same as {@link #with(Object, Object, Object, Object, Object, Object, Object, Object)}.
     */
    <K, V> MapIterable<K, V> of(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4);

    <K, V> MapIterable<K, V> with(K key1, V value1, K key2, V value2, K key3, V value3, K key4, V value4);

    <K, V> MapIterable<K, V> ofMap(Map<? extends K, ? extends V> map);

    <K, V> MapIterable<K, V> withMap(Map<? extends K, ? extends V> map);

    <K, V> MapIterable<K, V> ofMapIterable(MapIterable<? extends K, ? extends V> mapIterable);

    <K, V> MapIterable<K, V> withMapIterable(MapIterable<? extends K, ? extends V> mapIterable);
}
