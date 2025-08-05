/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.multimap.ordered;

import org.eclipse.collections.api.ordered.MutableSortedIterable;

/**
 * MutableSortedIterableMultimap is an interface that combines MutableMultimap and SortedIterableMultimap, providing
 * mutable multimap operations with sorted order semantics.
 *
 * @since 13.0
 */
public interface MutableSortedIterableMultimap<K, V> extends SortedIterableMultimap<K, V>
{
    @Override
    MutableSortedIterable<V> get(K key);

    @Override
    MutableSortedIterableMultimap<K, V> newEmpty();
}
