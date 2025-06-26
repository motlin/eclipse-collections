/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.set;

import java.util.Collection;

import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.collection.UnmodifiableMutableCollection;

/**
 * UnmodifiableSet is an interface that extends both UnmodifiableMutableCollection and MutableSet,
 * providing an unmodifiable view of a set. All mutating operations throw UnsupportedOperationException.
 * 
 * @since 12.0
 */
public interface UnmodifiableSet<T> extends UnmodifiableMutableCollection<T>, MutableSet<T>
{
    // Covariant return types for builder methods
    
    @Override
    default UnmodifiableSet<T> with(T element)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSet");
    }

    @Override
    default UnmodifiableSet<T> without(T element)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSet");
    }

    @Override
    default UnmodifiableSet<T> withAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSet");
    }

    @Override
    default UnmodifiableSet<T> withoutAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSet");
    }

    // View methods - return self since already unmodifiable
    
    @Override
    default UnmodifiableSet<T> asUnmodifiable()
    {
        return this;
    }

    // Resolve method conflicts from multiple inheritance
    
    @Override
    default void clear()
    {
        throw new UnsupportedOperationException("Cannot clear an UnmodifiableSet");
    }
    
    @Override
    default boolean retainAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSet");
    }
    
    @Override
    default boolean removeAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSet");
    }
    
    @Override
    default boolean addAll(Collection<? extends T> collection)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSet");
    }
    
    @Override
    default boolean remove(Object item)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSet");
    }
    
    @Override
    default boolean add(T item)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSet");
    }
    
    @Override
    default UnmodifiableSet<T> tap(Procedure<? super T> procedure)
    {
        this.forEach(procedure);
        return this;
    }

    @Override
    UnmodifiableSet<T> newEmpty();
}