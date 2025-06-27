/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.collection;

import java.util.Collection;

import org.eclipse.collections.api.UnmodifiableIterable;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;

/**
 * UnmodifiableMutableCollection is an interface that extends both UnmodifiableIterable and MutableCollection,
 * providing an unmodifiable view of a collection. All mutating operations throw UnsupportedOperationException.
 * 
 * @since 12.0
 */
public interface UnmodifiableMutableCollection<T> extends UnmodifiableIterable<T>, MutableCollection<T>
{
    @Override
    default boolean add(T item)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableCollection");
    }

    @Override
    default boolean remove(Object item)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableCollection");
    }

    @Override
    default boolean addAll(Collection<? extends T> collection)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableCollection");
    }

    @Override
    default boolean addAllIterable(Iterable<? extends T> iterable)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableCollection");
    }

    @Override
    default boolean removeAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableCollection");
    }

    @Override
    default boolean removeAllIterable(Iterable<?> iterable)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableCollection");
    }

    @Override
    default boolean retainAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableCollection");
    }

    @Override
    default boolean retainAllIterable(Iterable<?> iterable)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableCollection");
    }

    @Override
    default void clear()
    {
        throw new UnsupportedOperationException("Cannot clear an UnmodifiableMutableCollection");
    }

    @Override
    default boolean removeIf(Predicate<? super T> predicate)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableCollection");
    }

    @Override
    default <P> boolean removeIfWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableCollection");
    }

    // Note: with, without, withAll, withoutAll, and asUnmodifiable methods are not defined here
    // to avoid return type conflicts with specific unmodifiable collection interfaces.
    // Each specific unmodifiable collection type (UnmodifiableMutableBag, UnmodifiableMutableSet, etc.)
    // must provide its own implementation of these methods with the appropriate return type.
    
    @Override
    UnmodifiableMutableCollection<T> tap(Procedure<? super T> procedure);

    @Override
    default boolean removeIf(java.util.function.Predicate<? super T> filter)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableCollection");
    }
}