/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.set.sorted.immutable;

import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.impl.collection.immutable.ImmutableUnmodifiableCollection;

/**
 * ImmutableUnmodifiableSortedSet is a marker interface that indicates a sorted set is both immutable
 * and can serve as an unmodifiable view of a mutable sorted set.
 * 
 * This interface does NOT extend UnmodifiableMutableSet directly due to return type conflicts.
 * Instead, concrete implementations should:
 * 1. Extend their respective immutable base class (e.g., AbstractImmutableSortedSet)
 * 2. Implement this marker interface
 * 3. Provide implementations of mutable methods that throw UnsupportedOperationException
 * 
 * The purpose is to allow immutable sorted sets to be used where UnmodifiableMutableSet types are expected,
 * without causing compilation errors due to incompatible return types between ImmutableSortedSet and MutableSet.
 * 
 * @since 12.0
 */
public interface ImmutableUnmodifiableSortedSet<T> extends ImmutableUnmodifiableCollection<T>, ImmutableSortedSet<T>
{
}