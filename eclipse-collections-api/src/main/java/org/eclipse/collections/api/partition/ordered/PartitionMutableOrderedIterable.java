/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.partition.ordered;

import org.eclipse.collections.api.ordered.MutableOrderedIterable;
import org.eclipse.collections.api.partition.PartitionMutableIterable;

/**
 * A PartitionMutableOrderedIterable is the result of splitting a MutableOrderedIterable into two MutableOrderedIterables based on a Predicate.
 * The results that answer true for the Predicate will be returned from the getSelected() method and the results
 * that answer false for the predicate will be returned from the getRejected() method.
 *
 * @since 13.0
 */
public interface PartitionMutableOrderedIterable<T> extends PartitionOrderedIterable<T>, PartitionMutableIterable<T>
{
    @Override
    MutableOrderedIterable<T> getSelected();

    @Override
    MutableOrderedIterable<T> getRejected();
}