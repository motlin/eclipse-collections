/*
 * Copyright (c) 2015 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test;

import java.util.Iterator;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.test.collection.mutable.MutableCollectionTestCase;
import org.junit.Assert;
import org.junit.Test;

public interface MutableSortedNaturalOrderTestCase extends SortedNaturalOrderTestCase, MutableOrderedIterableTestCase  //, MutableCollectionTestCase
{
    @Test
    @Override
    default void Iterable_remove()
    {
        Iterable<Integer> iterable = this.newWith(1, 1, 1, 2, 2, 3);
        Iterator<Integer> iterator = iterable.iterator();
        iterator.next();
        iterator.remove();
        IterableTestCase.assertEquals(this.newWith(1, 1, 2, 2, 3), iterable);
    }
}
