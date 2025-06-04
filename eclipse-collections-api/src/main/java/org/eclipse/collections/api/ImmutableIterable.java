/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api;

import org.eclipse.collections.api.bag.ImmutableBagIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.function.primitive.FloatFunction;
import org.eclipse.collections.api.block.function.primitive.IntFunction;
import org.eclipse.collections.api.block.function.primitive.LongFunction;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.map.ImmutableMapIterable;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.MutableMapIterable;
import org.eclipse.collections.api.map.primitive.ImmutableObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.ImmutableObjectLongMap;
import org.eclipse.collections.api.multimap.ImmutableMultimap;
import org.eclipse.collections.api.ordered.OrderedIterable;
import org.eclipse.collections.api.partition.PartitionImmutableIterable;
import org.eclipse.collections.api.tuple.Pair;

/**
 * ImmutableIterable is an interface which extends the base RichIterable interface and adds the covariant
 * return type overrides for methods that can return an ImmutableIterable. This interface represents the objects
 * that are immutable, i.e., objects that cannot be changed after creation.
 */
public interface ImmutableIterable<T> extends RichIterable<T>
{
    //region [Category: Iterating] 🔄

    @Override
    ImmutableIterable<T> tap(Procedure<? super T> procedure);

    //endregion [Category: Iterating] 🔄

    //region [Category: Counting] 🔢

    @Override
    <V> ImmutableBagIterable<V> countBy(Function<? super T, ? extends V> function);

    @Override
    <V, P> ImmutableBagIterable<V> countByWith(Function2<? super T, ? super P, ? extends V> function, P parameter);

    @Override
    <V> ImmutableBagIterable<V> countByEach(Function<? super T, ? extends Iterable<V>> function);

    //endregion [Category: Counting] 🔢

    //region [Category: Filtering] 🚰

    @Override
    ImmutableIterable<T> select(Predicate<? super T> predicate);

    @Override
    <P> ImmutableIterable<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    ImmutableIterable<T> reject(Predicate<? super T> predicate);

    @Override
    <P> ImmutableIterable<T> rejectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    <S> ImmutableIterable<S> selectInstancesOf(Class<S> clazz);

    @Override
    PartitionImmutableIterable<T> partition(Predicate<? super T> predicate);

    @Override
    <P> PartitionImmutableIterable<T> partitionWith(Predicate2<? super T, ? super P> predicate, P parameter);

    //endregion [Category: Filtering] 🚰

    //region [Category: Transforming] 🦋

    @Override
    <V> ImmutableIterable<V> collect(Function<? super T, ? extends V> function);

    @Override
    <P, V> ImmutableIterable<V> collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter);

    @Override
    <V> ImmutableIterable<V> collectIf(Predicate<? super T> predicate, Function<? super T, ? extends V> function);

    @Override
    <V> ImmutableIterable<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);

    @Override
    default <P, V> ImmutableIterable<V> flatCollectWith(Function2<? super T, ? super P, ? extends Iterable<V>> function, P parameter)
    {
        return this.flatCollect(each -> function.apply(each, parameter));
    }

    /**
     * @deprecated in 6.0. Use {@link OrderedIterable#zip(Iterable)} instead.
     */
    @Override
    @Deprecated
    <S> ImmutableIterable<Pair<T, S>> zip(Iterable<S> that);

    /**
     * @deprecated in 6.0. Use {@link OrderedIterable#zipWithIndex()} instead.
     */
    @Override
    @Deprecated
    ImmutableIterable<Pair<T, Integer>> zipWithIndex();

    //endregion [Category: Transforming] 🦋

    //region [Category: Grouping] 🏘️

    @Override
    <V> ImmutableMultimap<V, T> groupBy(Function<? super T, ? extends V> function);

    @Override
    <V> ImmutableMultimap<V, T> groupByEach(Function<? super T, ? extends Iterable<V>> function);

    @Override
    <V> ImmutableMapIterable<V, T> groupByUniqueKey(Function<? super T, ? extends V> function);

    //endregion [Category: Grouping] 🏘️

    //region [Category: Aggregating] 📊

    @Override
    <K, V> ImmutableMapIterable<K, V> aggregateInPlaceBy(
            Function<? super T, ? extends K> groupBy,
            Function0<? extends V> zeroValueFactory,
            Procedure2<? super V, ? super T> mutatingAggregator);

    @Override
    <K, V> ImmutableMapIterable<K, V> aggregateBy(
            Function<? super T, ? extends K> groupBy,
            Function0<? extends V> zeroValueFactory,
            Function2<? super V, ? super T, ? extends V> nonMutatingAggregator);

    @Override
    default <K> ImmutableMapIterable<K, T> reduceBy(
            Function<? super T, ? extends K> groupBy,
            Function2<? super T, ? super T, ? extends T> reduceFunction)
    {
        MutableMapIterable<K, T> result = Maps.mutable.empty();
        return this.reduceBy(groupBy, reduceFunction, result).toImmutable();
    }

    @Override
    <V> ImmutableObjectLongMap<V> sumByInt(Function<? super T, ? extends V> groupBy, IntFunction<? super T> function);

    @Override
    <V> ImmutableObjectDoubleMap<V> sumByFloat(Function<? super T, ? extends V> groupBy, FloatFunction<? super T> function);

    @Override
    <V> ImmutableObjectLongMap<V> sumByLong(Function<? super T, ? extends V> groupBy, LongFunction<? super T> function);

    @Override
    <V> ImmutableObjectDoubleMap<V> sumByDouble(Function<? super T, ? extends V> groupBy, DoubleFunction<? super T> function);

    //endregion [Category: Aggregating] 📊
}