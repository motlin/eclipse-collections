/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.map.immutable;

import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MapIterable;

/**
 * ImmutableUnmodifiableMap is a marker interface that indicates a map is both immutable
 * and can serve as an unmodifiable view of a mutable map.
 * 
 * This interface does NOT extend UnmodifiableMutableMap directly due to return type conflicts.
 * Instead, concrete implementations should:
 * 1. Extend their respective immutable base class (e.g., AbstractImmutableMap)
 * 2. Implement this marker interface
 * 3. Provide implementations of mutable methods that throw UnsupportedOperationException
 * 
 * The purpose is to allow immutable maps to be used where UnmodifiableMutableMap types are expected,
 * without causing compilation errors due to incompatible return types between ImmutableMap and MutableMap.
 * 
 * @since 12.0
 */
public interface ImmutableUnmodifiableMap<K, V> extends ImmutableMap<K, V>
{
    /**
     * Returns this map as a MapIterable. This method is provided to allow
     * immutable maps to be used in contexts that expect UnmodifiableMapIterable,
     * which extends MutableMapIterable.
     */
    default MapIterable<K, V> asUnmodifiable()
    {
        return this;
    }
}