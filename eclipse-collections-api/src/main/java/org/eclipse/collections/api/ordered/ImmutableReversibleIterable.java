/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.ordered;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.partition.ordered.PartitionImmutableReversibleIterable;

/**
 * ImmutableReversibleIterable is an interface that combines ImmutableOrderedIterable and ReversibleIterable, providing
 * immutable operations with preserved order semantics and efficient reverse iteration capabilities.
 * 
 * Note: This interface currently cannot be compiled due to conflicting method signatures in the parent interfaces.
 * Specifically, zipWithIndex() methods have incompatible return types between ImmutableIterable 
 * and ReversibleIterable. These methods are deprecated in ImmutableIterable as of version 6.0. Once these deprecated 
 * methods are removed from ImmutableIterable, this interface will be compilable.
 *
 * @since 13.0
 */
public interface ImmutableReversibleIterable<T> extends ImmutableOrderedIterable<T>, ReversibleIterable<T>
{
    @Override
    ImmutableReversibleIterable<T> tap(Procedure<? super T> procedure);

    @Override
    ImmutableReversibleIterable<T> select(Predicate<? super T> predicate);

    @Override
    <P> ImmutableReversibleIterable<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    ImmutableReversibleIterable<T> reject(Predicate<? super T> predicate);

    @Override
    <P> ImmutableReversibleIterable<T> rejectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    <S> ImmutableReversibleIterable<S> selectInstancesOf(Class<S> clazz);

    @Override
    PartitionImmutableReversibleIterable<T> partition(Predicate<? super T> predicate);

    @Override
    <P> PartitionImmutableReversibleIterable<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    <V> ImmutableReversibleIterable<V> collect(Function<? super T, ? extends V> function);

    @Override
    <P, V> ImmutableReversibleIterable<V> collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter);

    @Override
    <V> ImmutableReversibleIterable<V> collectIf(Predicate<? super T> predicate, Function<? super T, ? extends V> function);

    @Override
    <V> ImmutableReversibleIterable<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);

    @Override
    default <P, V> ImmutableReversibleIterable<V> flatCollectWith(Function2<? super T, ? super P, ? extends Iterable<V>> function, P parameter)
    {
        return this.flatCollect(each -> function.value(each, parameter));
    }

    @Override
    <V> ImmutableListMultimap<V, T> groupBy(Function<? super T, ? extends V> function);

    @Override
    <V> ImmutableListMultimap<V, T> groupByEach(Function<? super T, ? extends Iterable<V>> function);
}