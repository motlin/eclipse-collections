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

import org.eclipse.collections.api.MutableIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.partition.list.PartitionMutableList;

/**
 * MutableOrderedIterable is an interface that combines MutableIterable and OrderedIterable, providing
 * mutable operations with preserved order semantics.
 *
 * @since 13.0
 */
public interface MutableOrderedIterable<T> extends MutableIterable<T>, OrderedIterable<T>
{
    @Override
    MutableOrderedIterable<T> tap(Procedure<? super T> procedure);

    @Override
    MutableOrderedIterable<T> select(Predicate<? super T> predicate);

    @Override
    <P> MutableOrderedIterable<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    MutableOrderedIterable<T> reject(Predicate<? super T> predicate);

    @Override
    <P> MutableOrderedIterable<T> rejectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    <S> MutableOrderedIterable<S> selectInstancesOf(Class<S> clazz);

    @Override
    <V> MutableOrderedIterable<V> collect(Function<? super T, ? extends V> function);

    @Override
    <P, V> MutableOrderedIterable<V> collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter);

    @Override
    <V> MutableOrderedIterable<V> collectIf(Predicate<? super T> predicate, Function<? super T, ? extends V> function);

    @Override
    <V> MutableOrderedIterable<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);

    @Override
    <P, V> MutableOrderedIterable<V> flatCollectWith(Function2<? super T, ? super P, ? extends Iterable<V>> function, P parameter);

    @Override
    PartitionMutableList<T> partition(Predicate<? super T> predicate);

    @Override
    <P> PartitionMutableList<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    <V> MutableListMultimap<V, T> groupBy(Function<? super T, ? extends V> function);

    @Override
    <V> MutableListMultimap<V, T> groupByEach(Function<? super T, ? extends Iterable<V>> function);
}