/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.map.sorted.immutable;

import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.sorted.ImmutableSortedMap;

/**
 * ImmutableUnmodifiableSortedMap is a marker interface that indicates a sorted map is both immutable
 * and can serve as an unmodifiable view of a mutable sorted map.
 * 
 * This interface does NOT extend UnmodifiableMutableMap or UnmodifiableMutableSortedMap directly due to return type conflicts.
 * Instead, concrete implementations should:
 * 1. Extend their respective immutable base class (e.g., AbstractImmutableSortedMap)
 * 2. Implement this marker interface
 * 3. Provide implementations of mutable methods that throw UnsupportedOperationException
 * 
 * The purpose is to allow immutable sorted maps to be used where UnmodifiableMutableMap types are expected,
 * without causing compilation errors due to incompatible return types between ImmutableSortedMap and MutableSortedMap.
 * 
 * @since 12.0
 */
public interface ImmutableUnmodifiableSortedMap<K, V> extends ImmutableSortedMap<K, V>
{
    /**
     * Returns this sorted map as a MapIterable. This method is provided to allow
     * immutable sorted maps to be used in contexts that expect UnmodifiableMapIterable,
     * which extends MutableMapIterable.
     */
    default MapIterable<K, V> asUnmodifiable()
    {
        return this;
    }
}