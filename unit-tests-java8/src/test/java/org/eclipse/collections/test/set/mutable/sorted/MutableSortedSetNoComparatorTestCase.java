/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.set.mutable.sorted;

import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.ordered.SortedIterable;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.set.sorted.SortedSetIterable;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.set.sorted.mutable.ScapegoatTreeSet;
import org.eclipse.collections.test.IterableTestCase;
import org.eclipse.collections.test.MutableSortedNaturalOrderUniqueTestCase;
import org.eclipse.collections.test.collection.mutable.MutableCollectionUniqueTestCase;
import org.junit.Test;

import static org.eclipse.collections.impl.test.Verify.assertThrows;
import static org.eclipse.collections.test.IterableTestCase.addAllTo;
import static org.eclipse.collections.test.IterableTestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public interface MutableSortedSetNoComparatorTestCase extends MutableSortedNaturalOrderUniqueTestCase, MutableCollectionUniqueTestCase
{
    @Override
    <T> MutableSortedSet<T> newWith(T... elements);

    @Override
    default <T> MutableSortedSet<T> getExpectedFiltered(T... elements)
    {
        return this.newMutableForFilter(elements);
    }

    @Override
    default <T> MutableSortedSet<T> newMutableForFilter(T... elements)
    {
        MutableSortedSet<T> result = (MutableSortedSet<T>) new ScapegoatTreeSet<>();
        addAllTo(elements, result);
        return result;
    }

    @Override
    @Test
    default void SortedSetIterable_union()
    {
        SortedSetIterable<Integer> union = this.newWith(1, 2, 3).union(this.newWith(3, 4, 5));
        assertEquals(SortedSets.immutable.with(1, 2, 3, 4, 5), union);
    }

    @Override
    default void OrderedIterable_getFirst()
    {
        assertEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getFirst());
    }

    @Override
    default void OrderedIterable_getLast()
    {
        assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getLast());
    }

    @Override
    default void RichIterable_getFirst()
    {
        assertEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getFirst());
    }

    @Override
    default void RichIterable_getLast()
    {
        assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getLast());
    }

    @Override
    default void OrderedIterable_min()
    {
        // Cannot contain duplicates
    }

    @Override
    default void OrderedIterable_max()
    {
        // Cannot contain duplicates
    }

    @Override
    default void OrderedIterable_min_comparator()
    {
        // Cannot contain duplicates
    }

    @Override
    default void OrderedIterable_max_comparator()
    {
        // Cannot contain duplicates
    }

    @Override
    @Test
    default void OrderedIterable_forEach_from_to()
    {
        SortedIterable<Integer> integers = this.newWith(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        MutableList<Integer> result = Lists.mutable.empty();
        integers.forEach(5, 7, result::add);
        assertEquals(Lists.immutable.with(5, 6, 7), result);

        MutableList<Integer> result2 = Lists.mutable.empty();
        integers.forEach(5, 5, result2::add);
        assertEquals(Lists.immutable.with(5), result2);

        MutableList<Integer> result3 = Lists.mutable.empty();
        integers.forEach(0, 9, result3::add);
        assertEquals(Lists.immutable.with(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), result3);

        MutableList<Integer> result4 = Lists.mutable.empty();
        integers.forEach(0, 0, result4::add);
        assertEquals(Lists.immutable.with(0), result4);

        MutableList<Integer> result5 = Lists.mutable.empty();
        integers.forEach(9, 9, result5::add);
        assertEquals(Lists.immutable.with(9), result5);

        assertThrows(IndexOutOfBoundsException.class, () -> integers.forEach(-1, 0, result::add));
        assertThrows(IndexOutOfBoundsException.class, () -> integers.forEach(0, -1, result::add));
        assertThrows(IndexOutOfBoundsException.class, () -> integers.forEach(0, 10, result::add));
        assertThrows(IndexOutOfBoundsException.class, () -> integers.forEach(10, 0, result::add));
    }

    @Override
    @Test
    default void OrderedIterable_forEach_from_to_reverse_order()
    {
        SortedIterable<Integer> integers = this.newWith(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        MutableList<Integer> result = Lists.mutable.empty();
        assertThrows(IllegalArgumentException.class, () -> integers.forEach(7, 5, result::add));
    }

    @Override
    @Test
    default void MutableCollection_iterationOrder()
    {
        MutableCollection<Integer> injectIntoWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().injectIntoWith(
                0,
                (a, b, c) ->
                {
                    injectIntoWithIterationOrder.add(b);
                    return 0;
                },
                0);
        IterableTestCase.assertEquals(this.newMutableForFilter(4, 3, 2, 1), injectIntoWithIterationOrder);
    }

    @Override
    @Test
    default void MutableCollection_removeIf()
    {
        MutableSortedSet<Integer> collection1 = this.newWith(1, 2, 3, 4, 5);
        assertTrue(collection1.removeIf(Predicates.cast(each -> each % 2 == 0)));
        IterableTestCase.assertEquals(this.getExpectedFiltered(1, 3, 5), collection1);

        MutableSortedSet<Integer> collection2 = this.newWith(1, 2, 3, 4);
        assertFalse(collection2.removeIf(Predicates.equal(5)));
        assertTrue(collection2.removeIf(Predicates.greaterThan(0)));
        assertEquals(this.newWith(), collection2);
        assertFalse(collection2.removeIf(Predicates.greaterThan(2)));

        Predicate<Object> predicate = null;
        assertThrows(NullPointerException.class, () -> this.newWith(1, 4, 5, 7).removeIf(predicate));

        MutableSortedSet<Integer> collection3 = this.newWith(1, 2, 3, 4, 5);
        assertTrue(collection3.removeIf(Predicates.<Integer>in(5, 3, 1)));
        assertEquals(this.getExpectedFiltered(2, 4), collection3);

        MutableSortedSet<Integer> collection4 = this.newWith(1, 2, 3, 4);
        assertFalse(collection4.removeIf(Predicates.equal(5)));
        assertTrue(collection4.removeIf(Predicates.greaterThan(0)));
        assertEquals(this.newWith(), collection4);
        assertFalse(collection4.removeIf(Predicates.greaterThan(2)));
    }

    @Override
    @Test
    default void MutableCollection_removeIfWith()
    {
        MutableSortedSet<Integer> collection1 = this.newWith(1, 2, 3, 4, 5);
        assertTrue(collection1.removeIfWith(Predicates2.<Integer>in(), Lists.immutable.with(5, 3, 1)));
        assertEquals(this.getExpectedFiltered(2, 4), collection1);
        assertThrows(NullPointerException.class, () -> this.newWith(7, 4, 5, 1).removeIfWith(null, this));

        MutableSortedSet<Integer> collection2 = this.newWith(1, 2, 3, 4);
        assertFalse(collection2.removeIfWith(Predicates2.equal(), 5));
        assertTrue(collection2.removeIfWith(Predicates2.greaterThan(), 0));
        assertEquals(this.newWith(), collection2);
        assertFalse(collection2.removeIfWith(Predicates2.greaterThan(), 2));

        assertThrows(NullPointerException.class, () -> this.newWith(1, 4, 5, 7).removeIfWith(null, null));
    }
}
