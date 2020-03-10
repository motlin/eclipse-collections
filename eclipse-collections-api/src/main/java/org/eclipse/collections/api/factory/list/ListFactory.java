/*
 * Copyright (c) 2020 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.factory.list;

import java.util.stream.Stream;

import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.list.ListIterable;

public interface ListFactory
{
    <T> ListIterable<T> empty();

    <T> ListIterable<T> of();

    <T> ListIterable<T> with();

    <T> ListIterable<T> of(T... items);

    <T> ListIterable<T> with(T... items);

    <T> ListIterable<T> ofInitialCapacity(int capacity);

    /**
     * Same as {@link #empty()}. but takes in initial capacity.
     */
    <T> ListIterable<T> withInitialCapacity(int capacity);

    <T> ListIterable<T> ofAll(Iterable<? extends T> iterable);

    <T> ListIterable<T> withAll(Iterable<? extends T> iterable);

    <T> ListIterable<T> fromStream(Stream<? extends T> stream);

    <T> ListIterable<T> withNValues(int size, Function0<? extends T> factory);
}
