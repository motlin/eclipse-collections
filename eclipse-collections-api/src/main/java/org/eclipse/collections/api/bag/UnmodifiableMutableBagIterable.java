/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.bag;

import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.collection.UnmodifiableMutableCollection;

/**
 * UnmodifiableMutableBagIterable is an interface that extends both UnmodifiableMutableCollection and MutableBagIterable,
 * providing an unmodifiable view of a bag. All mutating operations throw UnsupportedOperationException.
 * 
 * @since 12.0
 */
public interface UnmodifiableMutableBagIterable<T> extends UnmodifiableMutableCollection<T>, MutableBagIterable<T>
{
    // Bag-specific mutating methods that should throw UnsupportedOperationException
    
    @Override
    default int addOccurrences(T item, int occurrences)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableBag");
    }

    @Override
    default boolean removeOccurrences(Object item, int occurrences)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableBag");
    }

    @Override
    default boolean setOccurrences(T item, int occurrences)
    {
        throw new UnsupportedOperationException("Cannot set occurrences in an UnmodifiableMutableBag");
    }

    // Fluent API methods for bag-specific operations
    
    @Override
    default UnmodifiableMutableBagIterable<T> withOccurrences(T element, int occurrences)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableBag");
    }

    @Override
    default UnmodifiableMutableBagIterable<T> withoutOccurrences(T element, int occurrences)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableBag");
    }

    // Covariant return types for builder methods
    
    @Override
    default UnmodifiableMutableBagIterable<T> with(T element)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableBag");
    }

    @Override
    default UnmodifiableMutableBagIterable<T> without(T element)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableBag");
    }

    @Override
    default UnmodifiableMutableBagIterable<T> withAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableBag");
    }

    @Override
    default UnmodifiableMutableBagIterable<T> withoutAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableBag");
    }

    // View methods - return self since already unmodifiable
    
    @Override
    default UnmodifiableMutableBagIterable<T> asUnmodifiable()
    {
        return this;
    }

    // Resolve return type conflict for tap() method
    
    @Override
    default UnmodifiableMutableBagIterable<T> tap(Procedure<? super T> procedure)
    {
        this.forEach(procedure);
        return this;
    }
}