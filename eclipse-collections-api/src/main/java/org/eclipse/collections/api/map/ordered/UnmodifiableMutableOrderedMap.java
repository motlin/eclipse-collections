/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.map.ordered;

import java.util.Map;

import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableOrderedMap;
import org.eclipse.collections.api.map.UnmodifiableMutableMapIterable;
import org.eclipse.collections.api.tuple.Pair;

/**
 * UnmodifiableMutableOrderedMap is an interface that extends both UnmodifiableMutableMapIterable and MutableOrderedMap,
 * providing an unmodifiable view of an ordered map. All mutating operations throw UnsupportedOperationException.
 * 
 * @since 12.0
 */
public interface UnmodifiableMutableOrderedMap<K, V> extends UnmodifiableMutableMapIterable<K, V>, MutableOrderedMap<K, V>
{
    // Override builder methods that are specific to MutableOrderedMap to ensure they return UnmodifiableMutableOrderedMap
    
    @Override
    default UnmodifiableMutableOrderedMap<K, V> withKeyValue(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableOrderedMap");
    }

    @Override
    default UnmodifiableMutableOrderedMap<K, V> withMap(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableOrderedMap");
    }

    @Override
    default UnmodifiableMutableOrderedMap<K, V> withMapIterable(MapIterable<? extends K, ? extends V> mapIterable)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableOrderedMap");
    }

    @Override
    default UnmodifiableMutableOrderedMap<K, V> withAllKeyValues(Iterable<? extends Pair<? extends K, ? extends V>> keyValues)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableOrderedMap");
    }

    @Override
    default UnmodifiableMutableOrderedMap<K, V> withAllKeyValueArguments(Pair<? extends K, ? extends V>... keyValuePairs)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableOrderedMap");
    }

    @Override
    default UnmodifiableMutableOrderedMap<K, V> withoutKey(K key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableOrderedMap");
    }

    @Override
    default UnmodifiableMutableOrderedMap<K, V> withoutAllKeys(Iterable<? extends K> keys)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableOrderedMap");
    }

    // View methods - return self since already unmodifiable
    
    @Override
    default UnmodifiableMutableOrderedMap<K, V> asUnmodifiable()
    {
        return this;
    }

    @Override
    default UnmodifiableMutableOrderedMap<K, V> tap(Procedure<? super V> procedure)
    {
        this.forEach(procedure);
        return this;
    }
}