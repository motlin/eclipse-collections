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

/**
 * MutableSortedIterable is an interface that provides mutable operations with sorted order semantics.
 *
 * @since 13.0
 */
public interface MutableSortedIterable<T> extends SortedIterable<T>
{
    @Override
    MutableSortedIterable<T> tap(Procedure<? super T> procedure);

    @Override
    MutableSortedIterable<T> select(Predicate<? super T> predicate);

    @Override
    <P> MutableSortedIterable<T> selectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    MutableSortedIterable<T> reject(Predicate<? super T> predicate);

    @Override
    <P> MutableSortedIterable<T> rejectWith(Predicate2<? super T, ? super P> predicate, P parameter);

    @Override
    <S> MutableSortedIterable<S> selectInstancesOf(Class<S> clazz);

    @Override
    <V> MutableSortedIterable<V> collect(Function<? super T, ? extends V> function);

    @Override
    <P, V> MutableSortedIterable<V> collectWith(Function2<? super T, ? super P, ? extends V> function, P parameter);

    @Override
    <V> MutableSortedIterable<V> collectIf(Predicate<? super T> predicate, Function<? super T, ? extends V> function);

    @Override
    <V> MutableSortedIterable<V> flatCollect(Function<? super T, ? extends Iterable<V>> function);

    @Override
    <P, V> MutableSortedIterable<V> flatCollectWith(Function2<? super T, ? super P, ? extends Iterable<V>> function, P parameter);


    @Override
    MutableSortedIterable<T> takeWhile(Predicate<? super T> predicate);

    @Override
    MutableSortedIterable<T> dropWhile(Predicate<? super T> predicate);

    @Override
    MutableSortedIterable<T> distinct();
}
