/*
 * Copyright (c) 2026 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.map;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.test.CollectionTestCase;
import org.eclipse.collections.test.IterableTestCase;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesNotEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface MapValuesCollectionTestCase extends CollectionTestCase
{
    @Override
    default boolean allowsDuplicates()
    {
        return true;
    }

    @Override
    default boolean allowsAdd()
    {
        return false;
    }

    @Override
    default IterableTestCase.OrderingType getOrderingType()
    {
        return IterableTestCase.OrderingType.UNORDERED;
    }

    @Override
    @Test
    default void Object_equalsAndHashCode()
    {
        assertIterablesNotEqual(this.newWith(4, 3, 2, 1), this.newWith(3, 2, 1));
    }

    @Test
    @Override
    default void Iterable_toString()
    {
        assertThat(this.newWith(2, 1).toString(), isOneOf("[1, 2]", "[2, 1]"));
        assertThat(this.newWith(3, 2, 1).toString(), isOneOf(
                "[3, 2, 1]", "[3, 1, 2]", "[2, 3, 1]", "[2, 1, 3]", "[1, 3, 2]", "[1, 2, 3]"));
    }

    @Test
    @Override
    default void Iterable_remove()
    {
        if (!this.allowsRemove())
        {
            Collection<Integer> collection = this.newWith(3, 2, 1);
            Iterator<Integer> iterator = collection.iterator();
            iterator.next();
            assertThrows(UnsupportedOperationException.class, iterator::remove);
            return;
        }

        Collection<Integer> collection = this.newWith(3, 2, 1);
        Iterator<Integer> iterator = collection.iterator();
        iterator.next();
        iterator.remove();
        assertThat(Bags.mutable.withAll(collection), isOneOf(
                Bags.mutable.with(1, 2),
                Bags.mutable.with(1, 3),
                Bags.mutable.with(2, 3)));
    }
}
