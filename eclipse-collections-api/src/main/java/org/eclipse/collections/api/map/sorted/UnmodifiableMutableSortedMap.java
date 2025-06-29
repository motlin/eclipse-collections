/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.map.sorted;

import java.util.Map;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.UnmodifiableMapIterable;
import org.eclipse.collections.api.tuple.Pair;

/**
 * UnmodifiableMutableSortedMap is an interface that extends both UnmodifiableMapIterable and MutableSortedMap,
 * providing an unmodifiable view of a sorted map. All mutating operations throw UnsupportedOperationException.
 * 
 * @since 12.0
 */
public interface UnmodifiableMutableSortedMap<K, V> extends UnmodifiableMapIterable<K, V>, MutableSortedMap<K, V>
{
    // Override builder methods to resolve return type conflicts
    
    @Override
    default UnmodifiableMutableSortedMap<K, V> withKeyValue(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableSortedMap");
    }

    @Override
    default UnmodifiableMutableSortedMap<K, V> withAllKeyValues(Iterable<? extends Pair<? extends K, ? extends V>> keyValues)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableSortedMap");
    }

    @Override
    default UnmodifiableMutableSortedMap<K, V> withAllKeyValueArguments(Pair<? extends K, ? extends V>... keyValuePairs)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableSortedMap");
    }

    @Override
    default UnmodifiableMutableSortedMap<K, V> withMap(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableSortedMap");
    }

    @Override
    default UnmodifiableMutableSortedMap<K, V> withMapIterable(MapIterable<? extends K, ? extends V> mapIterable)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableSortedMap");
    }

    @Override
    default UnmodifiableMutableSortedMap<K, V> withoutKey(K key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableSortedMap");
    }

    @Override
    default UnmodifiableMutableSortedMap<K, V> withoutAllKeys(Iterable<? extends K> keys)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableSortedMap");
    }

    // Override conditional put operations to resolve conflicts
    
    @Override
    default <P> V getIfAbsentPutWith(K key, Function<? super P, ? extends V> function, P parameter)
    {
        throw new UnsupportedOperationException("Cannot put into an UnmodifiableMutableSortedMap");
    }

    // Override asUnmodifiable to return self
    
    @Override
    default UnmodifiableMutableSortedMap<K, V> asUnmodifiable()
    {
        return this;
    }

    // Override tap to return UnmodifiableMutableSortedMap
    
    @Override
    default UnmodifiableMutableSortedMap<K, V> tap(Procedure<? super V> procedure)
    {
        this.forEach(procedure);
        return this;
    }

    // Override view methods to return UnmodifiableMutableSortedMap
    
    @Override
    UnmodifiableMutableSortedMap<K, V> headMap(K toKey);

    @Override
    UnmodifiableMutableSortedMap<K, V> tailMap(K fromKey);

    @Override
    UnmodifiableMutableSortedMap<K, V> subMap(K fromKey, K toKey);
}