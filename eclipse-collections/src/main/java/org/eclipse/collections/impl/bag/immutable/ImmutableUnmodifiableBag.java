/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.bag.immutable;

import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.impl.collection.immutable.ImmutableUnmodifiableCollection;

/**
 * ImmutableUnmodifiableBag is a marker interface that indicates a bag is both immutable
 * and can serve as an unmodifiable view of a mutable bag.
 * 
 * This interface does NOT extend UnmodifiableMutableBag directly due to return type conflicts.
 * Instead, concrete implementations should:
 * 1. Extend their respective immutable base class (e.g., AbstractImmutableBag)
 * 2. Implement this marker interface
 * 3. Provide implementations of mutable methods that throw UnsupportedOperationException
 * 
 * The purpose is to allow immutable bags to be used where UnmodifiableMutableBag types are expected,
 * without causing compilation errors due to incompatible return types between ImmutableBag and MutableBag.
 * 
 * @since 12.0
 */
public interface ImmutableUnmodifiableBag<T> extends ImmutableUnmodifiableCollection<T>, ImmutableBag<T>
{
}