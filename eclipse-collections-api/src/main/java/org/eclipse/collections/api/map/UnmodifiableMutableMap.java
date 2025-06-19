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

import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.tuple.Pair;

/**
 * UnmodifiableMutableMap is an unmodifiable view of a hash-based map. It extends both UnmodifiableMapIterable 
 * and MutableMap interfaces. All mutation methods throw UnsupportedOperationException.
 * 
 * @since 12.0
 */
public interface UnmodifiableMutableMap<K, V> extends UnmodifiableMapIterable<K, V>, MutableMap<K, V>
{
    // Override builder methods to resolve return type conflicts
    
    @Override
    default UnmodifiableMutableMap<K, V> withKeyValue(K key, V value)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableMap");
    }

    @Override
    default UnmodifiableMutableMap<K, V> withMap(Map<? extends K, ? extends V> map)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableMap");
    }

    @Override
    default UnmodifiableMutableMap<K, V> withMapIterable(MapIterable<? extends K, ? extends V> mapIterable)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableMap");
    }

    @Override
    default UnmodifiableMutableMap<K, V> withAllKeyValues(Iterable<? extends Pair<? extends K, ? extends V>> keyValues)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableMap");
    }

    @Override
    default UnmodifiableMutableMap<K, V> withAllKeyValueArguments(Pair<? extends K, ? extends V>... keyValuePairs)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableMap");
    }

    @Override
    default UnmodifiableMutableMap<K, V> withoutKey(K key)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableMap");
    }

    @Override
    default UnmodifiableMutableMap<K, V> withoutAllKeys(Iterable<? extends K> keys)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableMap");
    }

    // View methods - return self since already unmodifiable
    
    @Override
    default UnmodifiableMutableMap<K, V> asUnmodifiable()
    {
        return this;
    }

    @Override
    default UnmodifiableMutableMap<K, V> tap(Procedure<? super V> procedure)
    {
        this.forEach(procedure);
        return this;
    }
}