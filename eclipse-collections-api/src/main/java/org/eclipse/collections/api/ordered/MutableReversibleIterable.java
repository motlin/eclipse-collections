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
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.partition.list.PartitionMutableList;

/**
 * MutableReversibleIterable is an interface that combines MutableOrderedIterable and ReversibleIterable, providing
 * mutable operations with preserved order semantics and efficient reverse iteration capabilities.
 *
 * @since 13.0
 */
public interface MutableReversibleIterable<T> extends MutableOrderedIterable<T>, ReversibleIterable<T>
{
    @Override
    MutableReversibleIterable<T> tap(Procedure<? super T> procedure);

    @Override
    MutableReversibleIterable<T> select(Predicate<? super T> predicate);

    @Override
    <P> MutableReversibleIterable<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    MutableReversibleIterable<T> reject(Predicate<? super T> predicate);

    @Override
    <P> MutableReversibleIterable<T> rejectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    <S> MutableReversibleIterable<S> selectInstancesOf(Class<S> clazz);

    @Override
    PartitionMutableList<T> partition(Predicate<? super T> predicate);

    @Override
    <P> PartitionMutableList<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    <V> MutableReversibleIterable<V> collect(Function<? super T, ? extends V> function);

    @Override
    <P, V> MutableReversibleIterable<V> collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter);

    @Override
    <V> MutableReversibleIterable<V> collectIf(Predicate<? super T> predicate, Function<? super T, ? extends V> function);

    @Override
    <V> MutableReversibleIterable<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);

    @Override
    default <P, V> MutableReversibleIterable<V> flatCollectWith(Function2<? super T, ? super P, ? extends Iterable<V>> function, P parameter)
    {
        return this.flatCollect(each -> function.value(each, parameter));
    }

    @Override
    <V> MutableListMultimap<V, T> groupBy(Function<? super T, ? extends V> function);

    @Override
    <V> MutableListMultimap<V, T> groupByEach(Function<? super T, ? extends Iterable<V>> function);
}