/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.bimap;

import java.util.Map;
import java.util.Set;

import org.eclipse.collections.api.UnmodifiableIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.tuple.Pair;

/**
 * UnmodifiableBiMap is an interface that extends both UnmodifiableIterable and MutableBiMap,
 * providing an unmodifiable view of a bidirectional map. All mutating operations throw UnsupportedOperationException.
 * 
 * @since 12.0
 */
public interface UnmodifiableBiMap<K, V> extends UnmodifiableIterable<V>, MutableBiMap<K, V>
{
    // BiMap-specific mutating methods that should throw UnsupportedOperationException
    
    @Override
    default V forcePut(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot forcePut into an UnmodifiableBiMap");
    }

    // Map entry manipulation methods that should throw UnsupportedOperationException
    
    @Override
    default V put(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableBiMap");
    }

    @Override
    default V putPair(Pair<? extends K, ? extends V> keyValuePair)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableBiMap");
    }

    @Override
    default V add(Pair<? extends K, ? extends V> keyValuePair)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableBiMap");
    }

    @Override
    default V remove(Object key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableBiMap");
    }

    @Override
    default V removeKey(K key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableBiMap");
    }

    @Override
    default boolean removeAllKeys(Set<? extends K> keys)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableBiMap");
    }

    @Override
    default boolean removeIf(Predicate2<? super K, ? super V> predicate)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableBiMap");
    }

    @Override
    default void putAll(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableBiMap");
    }

    @Override
    default void putAllMapIterable(MapIterable<? extends K, ? extends V> mapIterable)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableBiMap");
    }

    @Override
    default void clear()
    {
        throw new UnsupportedOperationException("Cannot clear an UnmodifiableBiMap");
    }

    // Conditional put operations
    
    @Override
    default V getIfAbsentPut(K key, Function0<? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableBiMap");
    }

    @Override
    default V getIfAbsentPut(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableBiMap");
    }

    @Override
    default V getIfAbsentPutWithKey(K key, Function<? super K, ? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableBiMap");
    }

    @Override
    default <P> V getIfAbsentPutWith(K key, Function<? super P, ? extends V> function, P parameter)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableBiMap");
    }

    // Update operations
    
    @Override
    default V updateValue(K key, Function0<? extends V> factory, Function<? super V, ? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot update an UnmodifiableBiMap");
    }

    @Override
    default <P> V updateValueWith(K key, Function0<? extends V> factory, Function2<? super V, ? super P, ? extends V> function, P parameter)
    {
        throw new UnsupportedOperationException("Cannot update an UnmodifiableBiMap");
    }

    // Fluent builder methods
    
    @Override
    default UnmodifiableBiMap<K, V> withKeyValue(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableBiMap");
    }

    @Override
    default UnmodifiableBiMap<K, V> withMap(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableBiMap");
    }

    @Override
    default UnmodifiableBiMap<K, V> withMapIterable(MapIterable<? extends K, ? extends V> mapIterable)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableBiMap");
    }

    @Override
    default UnmodifiableBiMap<K, V> withAllKeyValues(Iterable<? extends Pair<? extends K, ? extends V>> keyValues)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableBiMap");
    }

    @Override
    default UnmodifiableBiMap<K, V> withAllKeyValueArguments(Pair<? extends K, ? extends V>... keyValuePairs)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableBiMap");
    }

    @Override
    default UnmodifiableBiMap<K, V> withoutKey(K key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableBiMap");
    }

    @Override
    default UnmodifiableBiMap<K, V> withoutAllKeys(Iterable<? extends K> keys)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableBiMap");
    }

    // View methods - return self since already unmodifiable
    
    @Override
    default UnmodifiableBiMap<K, V> asUnmodifiable()
    {
        return this;
    }

    // Resolve return type conflict for tap() method
    
    @Override
    default UnmodifiableBiMap<K, V> tap(Procedure<? super V> procedure)
    {
        this.forEach(procedure);
        return this;
    }
}