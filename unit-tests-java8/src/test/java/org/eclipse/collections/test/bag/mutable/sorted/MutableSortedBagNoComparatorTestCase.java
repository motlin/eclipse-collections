/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.bag.mutable.sorted;

import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.bag.sorted.SortedBag;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.test.IterableTestCase;
import org.eclipse.collections.test.MutableSortedNaturalOrderTestCase;
import org.eclipse.collections.test.collection.mutable.MutableCollectionTestCase;
import org.junit.Test;

import static org.eclipse.collections.test.IterableTestCase.addAllTo;
import static org.eclipse.collections.test.IterableTestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public interface MutableSortedBagNoComparatorTestCase extends SortedBagTestCase, MutableBagIterableTestCase, MutableSortedNaturalOrderTestCase, MutableCollectionTestCase
{
    @Override
    <T> MutableSortedBag<T> newWith(T... elements);

    @Override
    default <T> SortedBag<T> getExpectedFiltered(T... elements)
    {
        return this.newMutableForFilter(elements);
    }

    @Override
    default <T> MutableSortedBag<T> newMutableForFilter(T... elements)
    {
        TreeBag<T> result = new TreeBag<>();
        addAllTo(elements, result);
        return result;
    }
    @Override
    @Test
    default void MutableCollection_removeIf()
    {
        MutableCollection<Integer> collection1 = this.newWith(1, 1, 2, 2, 3, 3, 4, 4, 5, 5);
        assertTrue(collection1.removeIf(Predicates.cast(each -> each % 2 == 0)));
        IterableTestCase.assertEquals(this.getExpectedFiltered(1, 1, 3, 3, 5, 5), collection1);

        MutableCollection<Integer> collection2 = this.newWith(1, 2, 3, 4);
        assertFalse(collection2.removeIf(Predicates.equal(5)));
        assertTrue(collection2.removeIf(Predicates.greaterThan(0)));
        assertEquals(this.newWith(), collection2);
        assertFalse(collection2.removeIf(Predicates.greaterThan(2)));

        Predicate<Object> predicate = null;
        Verify.assertThrows(NullPointerException.class, () -> this.newWith(1, 4, 5, 7).removeIf(predicate));
    }

    @Override
    @Test
    default void MutableCollection_removeIfWith()
    {
        MutableCollection<Integer> collection = this.newWith(1, 1, 2, 2, 3, 3, 4, 4, 5, 5);
        assertTrue(collection.removeIfWith(Predicates2.<Integer>in(), Lists.immutable.with(5, 3, 1)));
        IterableTestCase.assertEquals(this.getExpectedFiltered(2, 2, 4, 4), collection);
        Verify.assertThrows(NullPointerException.class, () -> this.newWith(7, 4, 5, 1).removeIfWith(null, this));

        MutableCollection<Integer> collection2 = this.newWith(1, 2, 3, 4);
        assertFalse(collection2.removeIfWith(Predicates2.equal(), 5));
        assertTrue(collection2.removeIfWith(Predicates2.greaterThan(), 0));
        assertEquals(this.newWith(), collection2);
        assertFalse(collection2.removeIfWith(Predicates2.greaterThan(), 2));

        Verify.assertThrows(NullPointerException.class, () -> this.newWith(1, 4, 5, 7).removeIfWith(null, null));
    }

    @Override
    @Test
    default void Bag_toStringOfItemToCount()
    {
        assertEquals("{}", this.newWith().toStringOfItemToCount());
        assertEquals("{1=1, 2=2, 3=3}", this.newWith(3, 3, 3, 2, 2, 1).toStringOfItemToCount());
    }

    @Override
    @Test
    default void MutableBagIterable_addOccurrences()
    {
        MutableSortedBag<Integer> mutableSortedBag = this.newWith(1, 2, 2, 3, 3, 3);
        assertEquals(4, mutableSortedBag.addOccurrences(4, 4));
        assertEquals(TreeBag.newBagWith(1, 2, 2, 3, 3, 3, 4, 4, 4, 4), mutableSortedBag);
        assertEquals(3, mutableSortedBag.addOccurrences(1, 2));
        assertEquals(TreeBag.newBagWith(1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4), mutableSortedBag);
        assertEquals(3, mutableSortedBag.addOccurrences(1, 0));
        assertEquals(TreeBag.newBagWith(1, 1, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4), mutableSortedBag);
    }

    @Override
    @Test
    default void MutableBagIterable_removeOccurrences()
    {
        MutableSortedBag<Integer> mutableBag = this.newWith(1, 2, 2, 3, 3, 3);
        assertFalse(mutableBag.removeOccurrences(4, 4));
        assertEquals(TreeBag.newBagWith(1, 2, 2, 3, 3, 3), mutableBag);
        assertFalse(mutableBag.removeOccurrences(3, 0));
        assertEquals(TreeBag.newBagWith(1, 2, 2, 3, 3, 3), mutableBag);
        assertTrue(mutableBag.removeOccurrences(1, 2));
        assertEquals(TreeBag.newBagWith(2, 2, 3, 3, 3), mutableBag);
        assertTrue(mutableBag.removeOccurrences(3, 2));
        assertEquals(TreeBag.newBagWith(2, 2, 3), mutableBag);
        assertTrue(mutableBag.removeOccurrences(2, 1));
        assertEquals(TreeBag.newBagWith(2, 3), mutableBag);
        assertTrue(mutableBag.removeOccurrences(2, 2));
        assertEquals(TreeBag.newBagWith(3), mutableBag);
    }

    @Override
    @Test
    default void SortedBag_forEachWith()
    {
        SortedBag<Integer> bag = this.newWith(1, 2, 2, 3, 3, 3);
        MutableList<Integer> result = Lists.mutable.with();
        bag.forEachWith((argument1, argument2) -> {
            result.add(argument1);
            result.add(argument2);
        }, 0);
        assertEquals(Lists.immutable.with(1, 0, 2, 0, 2, 0, 3, 0, 3, 0, 3, 0), result);
    }

    @Override
    default void SortedIterable_comparator()
    {
        MutableSortedNaturalOrderTestCase.super.SortedIterable_comparator();
    }
}
