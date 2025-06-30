/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.stack;

import java.util.Collection;

import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.list.ListIterable;

/**
 * UnmodifiableMutableStack is an interface that extends MutableStack,
 * providing an unmodifiable view of a stack. All mutating operations throw UnsupportedOperationException.
 * 
 * Note: Unlike other Unmodifiable interfaces in Eclipse Collections, UnmodifiableMutableStack does not extend
 * UnmodifiableIterable because MutableStack does not extend MutableIterable.
 * 
 * @since 12.0
 */
public interface UnmodifiableMutableStack<T> extends MutableStack<T>
{
    @Override
    default void push(T item)
    {
        throw new UnsupportedOperationException("Cannot push onto an UnmodifiableMutableStack");
    }

    @Override
    default T pop()
    {
        throw new UnsupportedOperationException("Cannot pop from an UnmodifiableMutableStack");
    }

    @Override
    default ListIterable<T> pop(int count)
    {
        throw new UnsupportedOperationException("Cannot pop from an UnmodifiableMutableStack");
    }

    @Override
    default <R extends Collection<T>> R pop(int count, R targetCollection)
    {
        throw new UnsupportedOperationException("Cannot pop from an UnmodifiableMutableStack");
    }

    @Override
    default <R extends MutableStack<T>> R pop(int count, R targetStack)
    {
        throw new UnsupportedOperationException("Cannot pop from an UnmodifiableMutableStack");
    }

    @Override
    default void clear()
    {
        throw new UnsupportedOperationException("Cannot clear an UnmodifiableMutableStack");
    }

    @Override
    default UnmodifiableMutableStack<T> asUnmodifiable()
    {
        return this;
    }

    @Override
    default UnmodifiableMutableStack<T> tap(Procedure<? super T> procedure)
    {
        this.forEach(procedure);
        return this;
    }
}