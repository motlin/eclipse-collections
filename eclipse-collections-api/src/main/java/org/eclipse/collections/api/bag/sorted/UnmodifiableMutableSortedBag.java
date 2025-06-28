/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.bag.sorted;

import org.eclipse.collections.api.bag.UnmodifiableMutableBagIterable;
import org.eclipse.collections.api.block.procedure.Procedure;

/**
 * UnmodifiableMutableSortedBag is an interface that extends both UnmodifiableMutableBagIterable and MutableSortedBag,
 * providing an unmodifiable view of a sorted bag. All mutating operations throw UnsupportedOperationException.
 * 
 * @since 12.0
 */
public interface UnmodifiableMutableSortedBag<T> extends UnmodifiableMutableBagIterable<T>, MutableSortedBag<T>
{
    // Covariant return types for builder methods that return 'this'
    
    @Override
    default UnmodifiableMutableSortedBag<T> with(T element)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableSortedBag");
    }

    @Override
    default UnmodifiableMutableSortedBag<T> without(T element)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableSortedBag");
    }

    @Override
    default UnmodifiableMutableSortedBag<T> withAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableMutableSortedBag");
    }

    @Override
    default UnmodifiableMutableSortedBag<T> withoutAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableMutableSortedBag");
    }

    @Override
    default UnmodifiableMutableSortedBag<T> withOccurrences(T element, int occurrences)
    {
        throw new UnsupportedOperationException("Cannot add occurrences to an UnmodifiableMutableSortedBag");
    }

    @Override
    default UnmodifiableMutableSortedBag<T> withoutOccurrences(T element, int occurrences)
    {
        throw new UnsupportedOperationException("Cannot remove occurrences from an UnmodifiableMutableSortedBag");
    }

    // View methods - return self since already unmodifiable
    
    @Override
    default UnmodifiableMutableSortedBag<T> asUnmodifiable()
    {
        return this;
    }

    @Override
    default UnmodifiableMutableSortedBag<T> tap(Procedure<? super T> procedure)
    {
        this.forEach(procedure);
        return this;
    }

    // Note: Methods like select(), reject(), take(), drop(), newEmpty(), clone(), asSynchronized() etc. 
    // that return new collections are NOT overridden here because they return different collection instances, 
    // not 'this'. The underlying implementation handles these correctly.
}