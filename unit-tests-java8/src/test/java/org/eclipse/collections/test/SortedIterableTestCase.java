/*
 * Copyright (c) 2021 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test;

import org.eclipse.collections.api.ordered.SortedIterable;
import org.eclipse.collections.api.partition.ordered.PartitionSortedIterable;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public interface SortedIterableTestCase extends OrderedIterableTestCase, NoDetectOptionalNullTestCase
{
    @Override
    <T> SortedIterable<T> newWith(T... elements);

    @Override
    @Test
    default void RichIterable_min_max_non_comparable()
    {
        assertThrows(ClassCastException.class, () -> this.newWith(new Object()));
    }

    @Override
    @Test
    default void RichIterable_minOptional_maxOptional_non_comparable()
    {
        assertThrows(ClassCastException.class, () -> this.newWith(new Object()));
    }

    @Test
    default void OrderedIterable_partitionWhile()
    {
        // Sorted iterables reorder elements, so we need a monotonic predicate that respects the sort order.
        switch (this.getOrderingType())
        {
            case SORTED_REVERSE_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 5, 4, 3, 2, 1);
                PartitionSortedIterable<Integer> partition1 = iterable.partitionWhile(each -> each > 2);
                assertIterablesEqual(this.newWith(6, 5, 4, 3), partition1.getSelected());
                assertIterablesEqual(this.newWith(2, 1), partition1.getRejected());
            }
            case SORTED_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 5, 4, 3, 2, 1);
                PartitionSortedIterable<Integer> partition1 = iterable.partitionWhile(each -> each < 3);
                assertIterablesEqual(this.newWith(1, 2), partition1.getSelected());
                assertIterablesEqual(this.newWith(3, 4, 5, 6), partition1.getRejected());
            }
            default -> fail("Unexpected value: " + this.getOrderingType());
        }

        SortedIterable<Integer> iterable2 = this.newWith(6, 5, 4, 3, 2, 1);
        PartitionSortedIterable<Integer> partition2 = iterable2.partitionWhile(each -> true);
        assertIterablesEqual(iterable2, partition2.getSelected());
        assertIterablesEqual(this.newWith(), partition2.getRejected());

        PartitionSortedIterable<Integer> partition3 = iterable2.partitionWhile(each -> false);
        assertIterablesEqual(this.newWith(), partition3.getSelected());
        assertIterablesEqual(iterable2, partition3.getRejected());

        SortedIterable<Object> empty = this.newWith();
        PartitionSortedIterable<Object> partition4 = empty.partitionWhile(each -> {
            fail("Should not evaluate the predicate");
            return true;
        });
        assertIterablesEqual(this.newWith(), partition4.getSelected());
        assertIterablesEqual(this.newWith(), partition4.getRejected());

        if (!this.allowsDuplicates())
        {
            return;
        }

        switch (this.getOrderingType())
        {
            case SORTED_REVERSE_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1);
                PartitionSortedIterable<Integer> partition1 = iterable.partitionWhile(each -> each > 2);
                assertIterablesEqual(this.newWith(6, 6, 5, 5, 4, 4, 3, 3), partition1.getSelected());
                assertIterablesEqual(this.newWith(2, 2, 1, 1), partition1.getRejected());
            }
            case SORTED_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 6, 5, 5, 4, 4, 3, 3, 2, 2, 1, 1);
                PartitionSortedIterable<Integer> partition1 = iterable.partitionWhile(each -> each < 3);
                assertIterablesEqual(this.newWith(1, 1, 2, 2), partition1.getSelected());
                assertIterablesEqual(this.newWith(3, 3, 4, 4, 5, 5, 6, 6), partition1.getRejected());
            }
            default -> fail("Unexpected value: " + this.getOrderingType());
        }
    }

    @Test
    default void OrderedIterable_takeWhile()
    {
        // Sorted iterables reorder elements, so we need a monotonic predicate that respects the sort order.
        switch (this.getOrderingType())
        {
            case SORTED_REVERSE_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 5, 4, 3);
                SortedIterable<Integer> take1 = iterable.takeWhile(each -> each > 2);
                assertIterablesEqual(this.newWith(6, 5, 4, 3), take1);
                assertEquals(iterable.comparator(), take1.comparator());
            }
            case SORTED_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 5, 4, 3);
                SortedIterable<Integer> take1 = iterable.takeWhile(each -> each < 6);
                assertIterablesEqual(this.newWith(3, 4, 5), take1);
                assertEquals(iterable.comparator(), take1.comparator());
            }
            default -> fail("Unexpected value: " + this.getOrderingType());
        }

        SortedIterable<Integer> iterable2 = this.newWith(6, 5, 4, 3);
        SortedIterable<Integer> take2 = iterable2.takeWhile(each -> true);
        assertIterablesEqual(iterable2, take2);
        assertEquals(iterable2.comparator(), take2.comparator());

        SortedIterable<Integer> take3 = iterable2.takeWhile(each -> false);
        assertIterablesEqual(this.newWith(), take3);

        SortedIterable<Object> empty = this.newWith();
        SortedIterable<Object> take4 = empty.takeWhile(each -> {
            fail("Should not evaluate the predicate");
            return true;
        });
        assertIterablesEqual(this.newWith(), take4);

        if (!this.allowsDuplicates())
        {
            return;
        }

        switch (this.getOrderingType())
        {
            case SORTED_REVERSE_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 6, 5, 5, 4, 4, 3, 3);
                SortedIterable<Integer> take1 = iterable.takeWhile(each -> each > 2);
                assertIterablesEqual(this.newWith(6, 6, 5, 5, 4, 4, 3, 3), take1);
                assertEquals(iterable.comparator(), take1.comparator());
            }
            case SORTED_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 6, 5, 5, 4, 4, 3, 3);
                SortedIterable<Integer> take1 = iterable.takeWhile(each -> each < 6);
                assertIterablesEqual(this.newWith(3, 3, 4, 4, 5, 5), take1);
                assertEquals(iterable.comparator(), take1.comparator());
            }
            default -> fail("Unexpected value: " + this.getOrderingType());
        }
    }

    @Test
    default void OrderedIterable_dropWhile()
    {
        // Sorted iterables reorder elements, so we need a monotonic predicate that respects the sort order.
        switch (this.getOrderingType())
        {
            case SORTED_REVERSE_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 5, 4, 3, 2, 1);
                SortedIterable<Integer> drop1 = iterable.dropWhile(each -> each > 2);
                assertIterablesEqual(this.newWith(2, 1), drop1);
            }
            case SORTED_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 5, 4, 3, 2, 1);
                SortedIterable<Integer> drop1 = iterable.dropWhile(each -> each < 3);
                assertIterablesEqual(this.newWith(3, 4, 5, 6), drop1);
            }
            default -> fail("Unexpected value: " + this.getOrderingType());
        }

        SortedIterable<Integer> iterable2 = this.newWith(6, 5, 4, 3, 2, 1);
        SortedIterable<Integer> drop2 = iterable2.dropWhile(each -> true);
        assertIterablesEqual(this.newWith(), drop2);

        SortedIterable<Integer> drop3 = iterable2.dropWhile(each -> false);
        assertIterablesEqual(iterable2, drop3);

        SortedIterable<Object> empty = this.newWith();
        SortedIterable<Object> drop4 = empty.dropWhile(each -> {
            fail("Should not evaluate the predicate");
            return true;
        });
        assertIterablesEqual(this.newWith(), drop4);

        if (!this.allowsDuplicates())
        {
            return;
        }

        switch (this.getOrderingType())
        {
            case SORTED_REVERSE_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 6, 4, 4, 5, 5, 3, 3, 2, 2, 1, 1);
                SortedIterable<Integer> drop1 = iterable.dropWhile(each -> each > 2);
                assertIterablesEqual(this.newWith(2, 2, 1, 1), drop1);
            }
            case SORTED_NATURAL ->
            {
                SortedIterable<Integer> iterable = this.newWith(6, 6, 4, 4, 5, 5, 3, 3, 2, 2, 1, 1);
                SortedIterable<Integer> drop1 = iterable.dropWhile(each -> each < 3);
                assertIterablesEqual(this.newWith(3, 3, 4, 4, 5, 5, 6, 6), drop1);
            }
            default -> fail("Unexpected value: " + this.getOrderingType());
        }
    }
}
