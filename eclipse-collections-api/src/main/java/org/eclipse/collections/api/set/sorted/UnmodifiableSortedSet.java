/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.set.sorted;

import java.util.Collection;

import org.eclipse.collections.api.UnmodifiableIterable;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;

/**
 * UnmodifiableSortedSet is an interface that extends both UnmodifiableIterable and MutableSortedSet,
 * providing an unmodifiable view of a sorted set. All mutating operations throw UnsupportedOperationException.
 * 
 * This interface does not extend UnmodifiableSet due to conflicting powerSet() method signatures 
 * between MutableSet (which returns MutableSet<UnsortedSetIterable<T>>) and MutableSortedSet 
 * (which returns MutableSortedSet<SortedSetIterable<T>>).
 * 
 * @since 12.0
 */
public interface UnmodifiableSortedSet<T> extends UnmodifiableIterable<T>, MutableSortedSet<T>
{
    // Covariant return types for builder methods
    
    @Override
    default UnmodifiableSortedSet<T> with(T element)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> without(T element)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> withAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> withoutAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }

    // View methods - return self since already unmodifiable
    
    @Override
    default UnmodifiableSortedSet<T> asUnmodifiable()
    {
        return this;
    }

    // Resolve method conflicts from multiple inheritance
    
    @Override
    default UnmodifiableSortedSet<T> newEmpty()
    {
        throw new UnsupportedOperationException("Cannot create a new empty UnmodifiableSortedSet");
    }
    
    // Mutating operations from Collection/Set that throw UnsupportedOperationException
    
    @Override
    default boolean add(T item)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSortedSet");
    }
    
    @Override
    default boolean remove(Object item)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }
    
    @Override
    default boolean addAll(Collection<? extends T> collection)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableSortedSet");
    }
    
    @Override
    default boolean removeAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }
    
    @Override
    default boolean retainAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }
    
    @Override
    default void clear()
    {
        throw new UnsupportedOperationException("Cannot clear an UnmodifiableSortedSet");
    }
    
    @Override
    default boolean removeIf(Predicate<? super T> predicate)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }
    
    @Override
    default <P> boolean removeIfWith(Predicate2<? super T, ? super P> predicate, P parameter)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> tap(Procedure<? super T> procedure)
    {
        this.forEach(procedure);
        return this;
    }

    // SortedSet-specific methods that need to return UnmodifiableSortedSet

    @Override
    default UnmodifiableSortedSet<T> toReversed()
    {
        throw new UnsupportedOperationException("Cannot create reversed view of UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> take(int count)
    {
        throw new UnsupportedOperationException("Cannot create take view of UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> drop(int count)
    {
        throw new UnsupportedOperationException("Cannot create drop view of UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> takeWhile(Predicate<? super T> predicate)
    {
        throw new UnsupportedOperationException("Cannot create takeWhile view of UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> dropWhile(Predicate<? super T> predicate)
    {
        throw new UnsupportedOperationException("Cannot create dropWhile view of UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> distinct()
    {
        return this; // A set is already distinct
    }

    @Override
    default UnmodifiableSortedSet<T> subSet(T fromElement, T toElement)
    {
        throw new UnsupportedOperationException("Cannot create subSet view of UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> headSet(T toElement)
    {
        throw new UnsupportedOperationException("Cannot create headSet view of UnmodifiableSortedSet");
    }

    @Override
    default UnmodifiableSortedSet<T> tailSet(T fromElement)
    {
        throw new UnsupportedOperationException("Cannot create tailSet view of UnmodifiableSortedSet");
    }
}