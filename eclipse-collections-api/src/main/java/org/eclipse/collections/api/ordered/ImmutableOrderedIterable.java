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

import org.eclipse.collections.api.ImmutableIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.partition.ordered.PartitionImmutableOrderedIterable;

/**
 * ImmutableOrderedIterable is an interface that combines ImmutableIterable and OrderedIterable, providing
 * immutable operations with preserved order semantics.
 *
 * @since 13.0
 */
public interface ImmutableOrderedIterable<T> extends ImmutableIterable<T>, OrderedIterable<T>
{
    @Override
    ImmutableOrderedIterable<T> tap(Procedure<? super T> procedure);

    @Override
    ImmutableOrderedIterable<T> select(Predicate<? super T> predicate);

    @Override
    <P> ImmutableOrderedIterable<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    ImmutableOrderedIterable<T> reject(Predicate<? super T> predicate);

    @Override
    <P> ImmutableOrderedIterable<T> rejectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    <S> ImmutableOrderedIterable<S> selectInstancesOf(Class<S> clazz);

    @Override
    PartitionImmutableOrderedIterable<T> partition(Predicate<? super T> predicate);

    @Override
    <P> PartitionImmutableOrderedIterable<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    <V> ImmutableOrderedIterable<V> collect(Function<? super T, ? extends V> function);

    @Override
    <P, V> ImmutableOrderedIterable<V> collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter);

    @Override
    <V> ImmutableOrderedIterable<V> collectIf(Predicate<? super T> predicate, Function<? super T, ? extends V> function);

    @Override
    <V> ImmutableOrderedIterable<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);

    @Override
    default <P, V> ImmutableOrderedIterable<V> flatCollectWith(Function2<? super T, ? super P, ? extends Iterable<V>> function, P parameter)
    {
        return this.flatCollect(each -> function.value(each, parameter));
    }

    @Override
    <V> ImmutableListMultimap<V, T> groupBy(Function<? super T, ? extends V> function);

    @Override
    <V> ImmutableListMultimap<V, T> groupByEach(Function<? super T, ? extends Iterable<V>> function);
}