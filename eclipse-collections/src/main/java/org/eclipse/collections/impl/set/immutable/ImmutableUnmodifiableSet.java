/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.set.immutable;

import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.collection.immutable.ImmutableUnmodifiableCollection;

/**
 * ImmutableUnmodifiableSet is a marker interface that indicates a set is both immutable
 * and can serve as an unmodifiable view of a mutable set.
 * 
 * This interface does NOT extend UnmodifiableMutableSet directly due to return type conflicts.
 * Instead, concrete implementations should:
 * 1. Extend their respective immutable base class (e.g., AbstractImmutableSet)
 * 2. Implement this marker interface
 * 3. Provide implementations of mutable methods that throw UnsupportedOperationException
 * 
 * The purpose is to allow immutable sets to be used where UnmodifiableMutableSet types are expected,
 * without causing compilation errors due to incompatible return types between ImmutableSet and MutableSet.
 * 
 * @since 12.0
 */
public interface ImmutableUnmodifiableSet<T> extends ImmutableUnmodifiableCollection<T>, ImmutableSet<T>
{
}