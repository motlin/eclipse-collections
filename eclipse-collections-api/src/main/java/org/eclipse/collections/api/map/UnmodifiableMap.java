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

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.tuple.Pair;

/**
 * UnmodifiableMap is an interface that extends both UnmodifiableMapIterable and MutableMap,
 * providing an unmodifiable view of a map. All mutating operations throw UnsupportedOperationException.
 * 
 * @since 12.0
 */
public interface UnmodifiableMap<K, V> extends UnmodifiableMapIterable<K, V>, MutableMap<K, V>
{
    // Covariant return types for builder methods
    
    @Override
    default UnmodifiableMap<K, V> withKeyValue(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMap");
    }

    @Override
    default UnmodifiableMap<K, V> withMap(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMap");
    }

    @Override
    default UnmodifiableMap<K, V> withMapIterable(MapIterable<? extends K, ? extends V> mapIterable)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMap");
    }

    @Override
    default UnmodifiableMap<K, V> withAllKeyValues(Iterable<? extends Pair<? extends K, ? extends V>> keyValues)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMap");
    }

    @Override
    default UnmodifiableMap<K, V> withAllKeyValueArguments(Pair<? extends K, ? extends V>... keyValuePairs)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMap");
    }

    @Override
    default UnmodifiableMap<K, V> withoutKey(K key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMap");
    }

    @Override
    default UnmodifiableMap<K, V> withoutAllKeys(Iterable<? extends K> keys)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMap");
    }

    // View methods - return self since already unmodifiable
    
    @Override
    default UnmodifiableMap<K, V> asUnmodifiable()
    {
        return this;
    }

    // Resolve method conflicts from multiple inheritance
    
    @Override
    default void clear()
    {
        throw new UnsupportedOperationException("Cannot clear an UnmodifiableMap");
    }
    
    @Override
    default V put(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMap");
    }

    @Override
    default V putPair(Pair<? extends K, ? extends V> keyValuePair)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMap");
    }

    @Override
    default V add(Pair<? extends K, ? extends V> keyValuePair)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMap");
    }

    @Override
    default V remove(Object key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMap");
    }

    @Override
    default V removeKey(K key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMap");
    }

    @Override
    default boolean removeAllKeys(Set<? extends K> keys)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMap");
    }

    @Override
    default boolean removeIf(Predicate2<? super K, ? super V> predicate)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMap");
    }

    @Override
    default void putAll(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMap");
    }

    @Override
    default void putAllMapIterable(MapIterable<? extends K, ? extends V> mapIterable)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMap");
    }

    @Override
    default V getIfAbsentPut(K key, Function0<? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMap");
    }

    @Override
    default V getIfAbsentPut(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMap");
    }

    @Override
    default V getIfAbsentPutWithKey(K key, Function<? super K, ? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMap");
    }

    @Override
    default <P> V getIfAbsentPutWith(K key, Function<? super P, ? extends V> function, P parameter)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMap");
    }

    @Override
    default V updateValue(K key, Function0<? extends V> factory, Function<? super V, ? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot update an UnmodifiableMap");
    }

    @Override
    default <P> V updateValueWith(K key, Function0<? extends V> factory, Function2<? super V, ? super P, ? extends V> function, P parameter)
    {
        throw new UnsupportedOperationException("Cannot update an UnmodifiableMap");
    }
    
    @Override
    default UnmodifiableMap<K, V> tap(Procedure<? super V> procedure)
    {
        this.forEach(procedure);
        return this;
    }

    @Override
    UnmodifiableMap<K, V> newEmpty();
}