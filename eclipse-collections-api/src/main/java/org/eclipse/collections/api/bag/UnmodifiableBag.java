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

/**
 * UnmodifiableBag is an interface for an unmodifiable hash-based bag.
 * It extends UnmodifiableMutableBagIterable and MutableBag, representing a specific
 * implementation of an unmodifiable bag that uses hashing.
 * 
 * @since 12.0
 */
public interface UnmodifiableBag<T> extends UnmodifiableMutableBagIterable<T>, MutableBag<T>
{
    @Override
    UnmodifiableBag<T> asUnmodifiable();
    
    @Override
    default UnmodifiableBag<T> tap(Procedure<? super T> procedure)
    {
        this.forEach(procedure);
        return this;
    }
    
    @Override
    default UnmodifiableBag<T> with(T element)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableBag");
    }
    
    @Override
    default UnmodifiableBag<T> without(T element)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableBag");
    }
    
    @Override
    default UnmodifiableBag<T> withAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableBag");
    }
    
    @Override
    default UnmodifiableBag<T> withoutAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableBag");
    }
    
    @Override
    default UnmodifiableBag<T> withOccurrences(T element, int occurrences)
    {
        throw new UnsupportedOperationException("Cannot add occurrences to an UnmodifiableBag");
    }
    
    @Override
    default UnmodifiableBag<T> withoutOccurrences(T element, int occurrences)
    {
        throw new UnsupportedOperationException("Cannot remove occurrences from an UnmodifiableBag");
    }
}