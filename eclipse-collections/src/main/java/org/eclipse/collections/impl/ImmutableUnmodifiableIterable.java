/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl;

import org.eclipse.collections.api.ImmutableIterable;
import org.eclipse.collections.api.RichIterable;

/**
 * ImmutableUnmodifiableIterable is a marker interface that indicates a collection is both immutable
 * and can serve as an unmodifiable view of a mutable collection.
 * 
 * This interface does NOT extend UnmodifiableIterable directly due to return type conflicts.
 * Instead, concrete implementations should:
 * 1. Extend their respective immutable base class (e.g., AbstractImmutableList)
 * 2. Implement this marker interface
 * 3. Provide implementations of mutable methods that throw UnsupportedOperationException
 * 
 * The purpose is to allow immutable collections to be used where UnmodifiableMutable* types are expected,
 * without causing compilation errors due to incompatible return types between ImmutableIterable and MutableIterable.
 * 
 * @since 12.0
 */
public interface ImmutableUnmodifiableIterable<T> extends ImmutableIterable<T>
{
    /**
     * Returns this collection as a RichIterable. This method is provided to allow
     * immutable collections to be used in contexts that expect UnmodifiableIterable,
     * which extends MutableIterable.
     */
    default RichIterable<T> asUnmodifiable()
    {
        return this;
    }
}