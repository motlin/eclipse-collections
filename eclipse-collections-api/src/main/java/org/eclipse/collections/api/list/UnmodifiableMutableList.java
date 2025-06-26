/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.Random;
import java.util.function.UnaryOperator;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.primitive.BooleanFunction;
import org.eclipse.collections.api.block.function.primitive.ByteFunction;
import org.eclipse.collections.api.block.function.primitive.CharFunction;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.function.primitive.FloatFunction;
import org.eclipse.collections.api.block.function.primitive.IntFunction;
import org.eclipse.collections.api.block.function.primitive.LongFunction;
import org.eclipse.collections.api.block.function.primitive.ShortFunction;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.collection.UnmodifiableMutableCollection;

/**
 * UnmodifiableMutableList is an interface that extends both UnmodifiableMutableCollection and MutableList,
 * providing an unmodifiable view of a list. All mutating operations throw UnsupportedOperationException.
 * 
 * @since 12.0
 */
public interface UnmodifiableMutableList<T> extends UnmodifiableMutableCollection<T>, MutableList<T>
{
    @Override
    default void add(int index, T element)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableList");
    }

    @Override
    default T set(int index, T element)
    {
        throw new UnsupportedOperationException("Cannot set element in an UnmodifiableList");
    }

    @Override
    default T remove(int index)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableList");
    }

    @Override
    default boolean addAll(int index, Collection<? extends T> c)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableList");
    }

    @Override
    default void replaceAll(UnaryOperator<T> operator)
    {
        throw new UnsupportedOperationException("Cannot replace elements in an UnmodifiableList");
    }

    @Override
    default void sort(Comparator<? super T> c)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    // MutableList-specific mutating methods
    
    @Override
    default UnmodifiableMutableList<T> sortThis(Comparator<? super T> comparator)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> sortThis()
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default <V extends Comparable<? super V>> UnmodifiableMutableList<T> sortThisBy(Function<? super T, ? extends V> function)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> sortThisByInt(IntFunction<? super T> function)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> sortThisByBoolean(BooleanFunction<? super T> function)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> sortThisByChar(CharFunction<? super T> function)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> sortThisByByte(ByteFunction<? super T> function)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> sortThisByShort(ShortFunction<? super T> function)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> sortThisByFloat(FloatFunction<? super T> function)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> sortThisByLong(LongFunction<? super T> function)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> sortThisByDouble(DoubleFunction<? super T> function)
    {
        throw new UnsupportedOperationException("Cannot sort an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> reverseThis()
    {
        throw new UnsupportedOperationException("Cannot reverse an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> shuffleThis()
    {
        throw new UnsupportedOperationException("Cannot shuffle an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> shuffleThis(Random random)
    {
        throw new UnsupportedOperationException("Cannot shuffle an UnmodifiableList");
    }

    // Covariant return types for builder methods

    @Override
    default UnmodifiableMutableList<T> with(T element)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> without(T element)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> withAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> withoutAll(Iterable<? extends T> elements)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableList");
    }

    // View methods - return self since already unmodifiable

    @Override
    default UnmodifiableMutableList<T> asUnmodifiable()
    {
        return this;
    }

    // Resolve method conflicts from multiple inheritance

    @Override
    default void clear()
    {
        throw new UnsupportedOperationException("Cannot clear an UnmodifiableList");
    }

    @Override
    default boolean retainAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableList");
    }

    @Override
    default boolean removeAll(Collection<?> collection)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableList");
    }

    @Override
    default boolean addAll(Collection<? extends T> collection)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableList");
    }

    @Override
    default boolean remove(Object item)
    {
        throw new UnsupportedOperationException("Cannot remove from an UnmodifiableList");
    }

    @Override
    default boolean add(T item)
    {
        throw new UnsupportedOperationException("Cannot add to an UnmodifiableList");
    }

    @Override
    default UnmodifiableMutableList<T> tap(Procedure<? super T> procedure)
    {
        this.forEach(procedure);
        return this;
    }
}