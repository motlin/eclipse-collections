/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.map;

import java.util.Map;
import java.util.Set;

import org.eclipse.collections.api.UnmodifiableIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.tuple.Pair;

/**
 * UnmodifiableMapIterable is an interface that extends both UnmodifiableIterable and MutableMapIterable,
 * providing an unmodifiable view of a map-like collection. All mutating operations throw UnsupportedOperationException.
 *
 * @since 12.0
 */
public interface UnmodifiableMapIterable<K, V> extends UnmodifiableIterable<V>, MutableMapIterable<K, V>
{
    // Map entry manipulation methods that should throw UnsupportedOperationException

    @Override
    default V put(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMapIterable");
    }

    @Override
    default V putPair(Pair<? extends K, ? extends V> keyValuePair)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMapIterable");
    }

    @Override
    default V add(Pair<? extends K, ? extends V> keyValuePair)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMapIterable");
    }

    @Override
    default V remove(Object key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMapIterable");
    }

    @Override
    default V removeKey(K key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMapIterable");
    }

    @Override
    default boolean removeAllKeys(Set<? extends K> keys)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMapIterable");
    }

    @Override
    default boolean removeIf(Predicate2<? super K, ? super V> predicate)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMapIterable");
    }

    @Override
    default void putAll(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMapIterable");
    }

    @Override
    default void putAllMapIterable(MapIterable<? extends K, ? extends V> mapIterable)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMapIterable");
    }

    @Override
    default void clear()
    {
        throw new UnsupportedOperationException("Cannot clear an UnmodifiableMapIterable");
    }

    // Conditional put operations

    @Override
    default V getIfAbsentPut(K key, Function0<? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMapIterable");
    }

    @Override
    default V getIfAbsentPut(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMapIterable");
    }

    @Override
    default V getIfAbsentPutWithKey(K key, Function<? super K, ? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMapIterable");
    }

    @Override
    default <P> V getIfAbsentPutWith(K key, Function<? super P, ? extends V> function, P parameter)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMapIterable");
    }

    // Update operations

    @Override
    default V updateValue(K key, Function0<? extends V> factory, Function<? super V, ? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot update an UnmodifiableMapIterable");
    }

    @Override
    default <P> V updateValueWith(K key, Function0<? extends V> factory, Function2<? super V, ? super P, ? extends V> function, P parameter)
    {
        throw new UnsupportedOperationException("Cannot update an UnmodifiableMapIterable");
    }

    // Fluent builder methods

    @Override
    default UnmodifiableMapIterable<K, V> withKeyValue(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMapIterable");
    }

    @Override
    default UnmodifiableMapIterable<K, V> withMap(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMapIterable");
    }

    @Override
    default UnmodifiableMapIterable<K, V> withMapIterable(MapIterable<? extends K, ? extends V> mapIterable)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMapIterable");
    }

    @Override
    default UnmodifiableMapIterable<K, V> withAllKeyValues(Iterable<? extends Pair<? extends K, ? extends V>> keyValues)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMapIterable");
    }

    @Override
    default UnmodifiableMapIterable<K, V> withAllKeyValueArguments(Pair<? extends K, ? extends V>... keyValuePairs)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMapIterable");
    }

    @Override
    default UnmodifiableMapIterable<K, V> withoutKey(K key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMapIterable");
    }

    @Override
    default UnmodifiableMapIterable<K, V> withoutAllKeys(Iterable<? extends K> keys)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMapIterable");
    }

    // View methods - return self since already unmodifiable

    @Override
    default UnmodifiableMapIterable<K, V> asUnmodifiable()
    {
        return this;
    }

    // Resolve return type conflict for tap() method

    @Override
    default UnmodifiableMapIterable<K, V> tap(Procedure<? super V> procedure)
    {
        this.forEach(procedure);
        return this;
    }
}