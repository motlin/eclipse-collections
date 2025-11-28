/*
 * Copyright (c) 2021 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.set.sorted;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.SortedSets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.ordered.OrderedIterable;
import org.eclipse.collections.api.ordered.SortedIterable;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.set.sorted.SortedSetIterable;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.test.SortedIterableTestCase;
import org.eclipse.collections.test.domain.A;
import org.eclipse.collections.test.domain.B;
import org.eclipse.collections.test.domain.C;
import org.eclipse.collections.test.list.TransformsToListTrait;
import org.eclipse.collections.test.set.SetIterableTestCase;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface SortedSetIterableTestCase extends SetIterableTestCase, SortedIterableTestCase, TransformsToListTrait
{
    @Override
    <T> SortedSetIterable<T> newWith(T... elements);

    @Override
    default boolean allowsNull()
    {
        return false;
    }

    @Override
    default <T> SortedSetIterable<T> getExpectedFiltered(T... elements)
    {
        return switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> SortedSets.immutable.with(elements);
            case SORTED_REVERSE_NATURAL -> SortedSets.immutable.with(Comparators.reverseNaturalOrder(), elements);
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        };
    }

    @Override
    default <T> MutableSortedSet<T> newMutableForFilter(T... elements)
    {
        return switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> SortedSets.mutable.with(elements);
            case SORTED_REVERSE_NATURAL -> SortedSets.mutable.with(Comparators.reverseNaturalOrder(), elements);
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        };
    }

    @Test
    default void SortedSetIterable_union()
    {
        SortedSetIterable<Integer> union = this.newWith(1, 2, 3).union(this.newWith(3, 4, 5));
        assertIterablesEqual(this.getExpectedFiltered(5, 4, 3, 2, 1), union);
    }

    @Override
    @Test
    default void SortedIterable_comparator()
    {
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertNull(this.newWith().comparator());
            case SORTED_REVERSE_NATURAL -> assertSame(Comparators.reverseNaturalOrder(), this.newWith().comparator());
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }
    }

    @Override
    @Test
    default void RichIterable_getFirst_empty_null()
    {
        assertThrows(NoSuchElementException.class, () -> this.newWith().getFirst());
    }

    @Override
    @Test
    default void RichIterable_getLast_empty_null()
    {
        assertThrows(NoSuchElementException.class, () -> this.newWith().getLast());
    }

    @Override
    @Test
    default void RichIterable_selectInstancesOf()
    {
        // Must test with two classes that are mutually Comparable

        SortedSetIterable<A> numbers = this.newWith(new C(4.0), new B(3), new C(2.0), new B(1));
        assertIterablesEqual(this.getExpectedFiltered(new B(3), new B(1)), numbers.selectInstancesOf(B.class));
        assertIterablesEqual(this.getExpectedFiltered(new C(4.0), new B(3), new C(2.0), new B(1)), numbers.selectInstancesOf(A.class));
    }

    @Override
    @Test
    default void OrderedIterable_getFirstOptional()
    {
        SortedIterableTestCase.super.OrderedIterable_getFirstOptional();
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertEquals(Optional.of(1), ((OrderedIterable<?>) this.newWith(3, 2, 1)).getFirstOptional());
            case SORTED_REVERSE_NATURAL -> assertEquals(Optional.of(3), ((OrderedIterable<?>) this.newWith(3, 2, 1)).getFirstOptional());
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }
    }

    @Override
    @Test
    default void OrderedIterable_getLastOptional()
    {
        SortedIterableTestCase.super.OrderedIterable_getLastOptional();
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertEquals(Optional.of(Integer.valueOf(3)), ((OrderedIterable<?>) this.newWith(3, 2, 1)).getLastOptional());
            case SORTED_REVERSE_NATURAL -> assertEquals(Optional.of(Integer.valueOf(1)), ((OrderedIterable<?>) this.newWith(3, 2, 1)).getLastOptional());
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }
    }

    @Override
    @Test
    default void RichIterable_getFirst()
    {
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertEquals(Integer.valueOf(1), this.newWith(3, 2, 1).getFirst());
            case SORTED_REVERSE_NATURAL -> assertEquals(Integer.valueOf(3), this.newWith(3, 2, 1).getFirst());
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }
    }

    @Override
    @Test
    default void RichIterable_getLast()
    {
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertEquals(Integer.valueOf(3), this.newWith(3, 2, 1).getLast());
            case SORTED_REVERSE_NATURAL -> assertEquals(Integer.valueOf(1), this.newWith(3, 2, 1).getLast());
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }
    }

    @Override
    @Test
    default void OrderedIterable_zipWithIndex()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertEquals(
                    Lists.immutable.with(
                            Tuples.pair(1, 0),
                            Tuples.pair(2, 1),
                            Tuples.pair(3, 2),
                            Tuples.pair(4, 3)),
                    iterable.zipWithIndex().toList());
            case SORTED_REVERSE_NATURAL -> assertEquals(
                    Lists.immutable.with(
                            Tuples.pair(4, 0),
                            Tuples.pair(3, 1),
                            Tuples.pair(2, 2),
                            Tuples.pair(1, 3)),
                    iterable.zipWithIndex().toList());
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }
    }

    @Override
    @Test
    default void OrderedIterable_zipWithIndex_target()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertEquals(
                    Lists.immutable.with(
                            Tuples.pair(1, 0),
                            Tuples.pair(2, 1),
                            Tuples.pair(3, 2),
                            Tuples.pair(4, 3)),
                    iterable.zipWithIndex(Lists.mutable.empty()));
            case SORTED_REVERSE_NATURAL -> assertEquals(
                    Lists.immutable.with(
                            Tuples.pair(4, 0),
                            Tuples.pair(3, 1),
                            Tuples.pair(2, 2),
                            Tuples.pair(1, 3)),
                    iterable.zipWithIndex(Lists.mutable.empty()));
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }
    }

    @Test
    default void OrderedIterable_forEach_from_to()
    {
        SortedIterable<Integer> integers = this.newWith(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

        MutableList<Integer> result = Lists.mutable.empty();
        integers.forEach(5, 7, result::add);
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertIterablesEqual(Lists.immutable.with(5, 6, 7), result);
            case SORTED_REVERSE_NATURAL -> assertIterablesEqual(Lists.immutable.with(4, 3, 2), result);
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }

        MutableList<Integer> result2 = Lists.mutable.empty();
        integers.forEach(5, 5, result2::add);
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertIterablesEqual(Lists.immutable.with(5), result2);
            case SORTED_REVERSE_NATURAL -> assertIterablesEqual(Lists.immutable.with(4), result2);
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }

        MutableList<Integer> result3 = Lists.mutable.empty();
        integers.forEach(0, 9, result3::add);
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertIterablesEqual(Lists.immutable.with(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), result3);
            case SORTED_REVERSE_NATURAL -> assertIterablesEqual(Lists.immutable.with(9, 8, 7, 6, 5, 4, 3, 2, 1, 0), result3);
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }

        MutableList<Integer> result4 = Lists.mutable.empty();
        integers.forEach(0, 0, result4::add);
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertIterablesEqual(Lists.immutable.with(0), result4);
            case SORTED_REVERSE_NATURAL -> assertIterablesEqual(Lists.immutable.with(9), result4);
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }

        MutableList<Integer> result5 = Lists.mutable.empty();
        integers.forEach(9, 9, result5::add);
        switch (this.getOrderingType())
        {
            case SORTED_NATURAL -> assertIterablesEqual(Lists.immutable.with(9), result5);
            case SORTED_REVERSE_NATURAL -> assertIterablesEqual(Lists.immutable.with(0), result5);
            default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
        }

        assertThrows(IndexOutOfBoundsException.class, () -> integers.forEach(-1, 0, result::add));
        assertThrows(IndexOutOfBoundsException.class, () -> integers.forEach(0, -1, result::add));
        assertThrows(IndexOutOfBoundsException.class, () -> integers.forEach(0, 10, result::add));
        assertThrows(IndexOutOfBoundsException.class, () -> integers.forEach(10, 0, result::add));
    }

    @Test
    default void OrderedIterable_forEach_from_to_reverse_order()
    {
        OrderedIterable<Integer> integers = (OrderedIterable<Integer>) this.newWith(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);
        MutableList<Integer> result = Lists.mutable.empty();
        integers.forEach(7, 5, result::add);
        assertIterablesEqual(
                switch (this.getOrderingType())
                {
                    case SORTED_NATURAL -> Lists.immutable.with(7, 6, 5);
                    case SORTED_REVERSE_NATURAL -> Lists.immutable.with(2, 3, 4);
                    default -> throw new AssertionError("Unexpected ordering type for SortedSetIterable: " + this.getOrderingType());
                },
                result);
    }
}
