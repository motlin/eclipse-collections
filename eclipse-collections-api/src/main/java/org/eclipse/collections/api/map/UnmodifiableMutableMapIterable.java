/*
 * Copyright (c) 2024 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.map;

import java.util.Map;

import org.eclipse.collections.api.UnmodifiableIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.tuple.Pair;

/**
 * An unmodifiable view of a mutable map, extending both MutableMapIterable and UnmodifiableIterable for values.
 *
 * @since 12.0
 */
public interface UnmodifiableMutableMapIterable<K, V> extends MutableMapIterable<K, V>, UnmodifiableIterable<V>
{
    @Override
    UnmodifiableMutableMapIterable<K, V> tap(Procedure<? super V> procedure);

    @Override
    default UnmodifiableMutableMapIterable<K, V> asUnmodifiable()
    {
        return this;
    }

    @Override
    default V put(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default void putAll(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default V remove(Object key)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default V removeKey(K key)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default boolean removeIf(Predicate2<? super K, ? super V> predicate)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default void clear()
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default V putPair(Pair<? extends K, ? extends V> keyValuePair)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default V add(Pair<? extends K, ? extends V> keyValuePair)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default V updateValue(K key, Function0<? extends V> factory, Function<? super V, ? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default <P> V updateValueWith(K key, Function0<? extends V> factory, Function2<? super V, ? super P, ? extends V> function, P parameter)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default V getIfAbsentPut(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default V getIfAbsentPut(K key, Function0<? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default V getIfAbsentPutWithKey(K key, Function<? super K, ? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default <P> V getIfAbsentPutWith(K key, Function<? super P, ? extends V> function, P parameter)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default UnmodifiableMutableMapIterable<K, V> withKeyValue(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default UnmodifiableMutableMapIterable<K, V> withMap(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default UnmodifiableMutableMapIterable<K, V> withMapIterable(MapIterable<? extends K, ? extends V> mapIterable)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default UnmodifiableMutableMapIterable<K, V> withAllKeyValues(Iterable<? extends Pair<? extends K, ? extends V>> keyValues)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default UnmodifiableMutableMapIterable<K, V> withAllKeyValueArguments(Pair<? extends K, ? extends V>... keyValuePairs)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default UnmodifiableMutableMapIterable<K, V> withoutKey(K key)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }

    @Override
    default UnmodifiableMutableMapIterable<K, V> withoutAllKeys(Iterable<? extends K> keys)
    {
        throw new UnsupportedOperationException("Cannot mutate an unmodifiable map");
    }
}