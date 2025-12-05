/*
 * Copyright (c) 2024 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.partition.PartitionIterable;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertSame;

public interface RichIterableFilteringTestCase extends InternalIterableTestCase
{
    @Test
    default void RichIterable_select_reject()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        assertIterablesEqual(
                this.getExpectedFiltered(4, 2),
                iterable.select(IntegerPredicates.isEven()));

        {
            MutableCollection<Integer> target = this.newMutableForFilter();
            MutableCollection<Integer> result = iterable.select(IntegerPredicates.isEven(), target);
            assertIterablesEqual(this.newMutableForFilter(4, 2), result);
            assertSame(target, result);
        }

        assertIterablesEqual(
                this.getExpectedFiltered(4, 3),
                iterable.selectWith(Predicates2.greaterThan(), 2));

        {
            MutableCollection<Integer> target = this.newMutableForFilter();
            MutableCollection<Integer> result = iterable.selectWith(Predicates2.greaterThan(), 2, target);
            assertIterablesEqual(this.newMutableForFilter(4, 3), result);
            assertSame(target, result);
        }

        assertIterablesEqual(
                this.getExpectedFiltered(4, 2),
                iterable.reject(IntegerPredicates.isOdd()));

        {
            MutableCollection<Integer> target = this.newMutableForFilter();
            MutableCollection<Integer> result = iterable.reject(IntegerPredicates.isOdd(), target);
            assertIterablesEqual(this.newMutableForFilter(4, 2), result);
            assertSame(target, result);
        }

        assertIterablesEqual(
                this.getExpectedFiltered(4, 3),
                iterable.rejectWith(Predicates2.lessThan(), 3));

        {
            MutableCollection<Integer> target = this.newMutableForFilter();
            MutableCollection<Integer> result = iterable.rejectWith(Predicates2.lessThan(), 3, target);
            assertIterablesEqual(this.newMutableForFilter(4, 3), result);
            assertSame(target, result);
        }

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableWithDuplicates = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        assertIterablesEqual(
                this.getExpectedFiltered(4, 4, 4, 4, 2, 2),
                iterableWithDuplicates.select(IntegerPredicates.isEven()));

        {
            MutableCollection<Integer> target = this.newMutableForFilter();
            MutableCollection<Integer> result = iterableWithDuplicates.select(IntegerPredicates.isEven(), target);
            assertIterablesEqual(this.newMutableForFilter(4, 4, 4, 4, 2, 2), result);
            assertSame(target, result);
        }

        assertIterablesEqual(
                this.getExpectedFiltered(4, 4, 4, 4, 3, 3, 3),
                iterableWithDuplicates.selectWith(Predicates2.greaterThan(), 2));

        {
            MutableCollection<Integer> target = this.newMutableForFilter();
            MutableCollection<Integer> result = iterableWithDuplicates.selectWith(Predicates2.greaterThan(), 2, target);
            assertIterablesEqual(this.newMutableForFilter(4, 4, 4, 4, 3, 3, 3), result);
            assertSame(target, result);
        }

        assertIterablesEqual(
                this.getExpectedFiltered(4, 4, 4, 4, 2, 2),
                iterableWithDuplicates.reject(IntegerPredicates.isOdd()));

        {
            MutableCollection<Integer> target = this.newMutableForFilter();
            MutableCollection<Integer> result = iterableWithDuplicates.reject(IntegerPredicates.isOdd(), target);
            assertIterablesEqual(this.newMutableForFilter(4, 4, 4, 4, 2, 2), result);
            assertSame(target, result);
        }

        assertIterablesEqual(
                this.getExpectedFiltered(4, 4, 4, 4, 3, 3, 3),
                iterableWithDuplicates.rejectWith(Predicates2.lessThan(), 3));

        {
            MutableCollection<Integer> target = this.newMutableForFilter();
            MutableCollection<Integer> result = iterableWithDuplicates.rejectWith(Predicates2.lessThan(), 3, target);
            assertIterablesEqual(this.newMutableForFilter(4, 4, 4, 4, 3, 3, 3), result);
            assertSame(target, result);
        }
    }

    @Test
    default void RichIterable_partition()
    {
        RichIterable<Integer> iterable = this.newWith(-3, -2, -1, 0, 1, 2, 3);

        PartitionIterable<Integer> partition = iterable.partition(IntegerPredicates.isEven());
        assertIterablesEqual(this.getExpectedFiltered(-2, 0, 2), partition.getSelected());
        assertIterablesEqual(this.getExpectedFiltered(-3, -1, 1, 3), partition.getRejected());

        PartitionIterable<Integer> partitionWith = iterable.partitionWith(Predicates2.greaterThan(), 0);
        assertIterablesEqual(this.getExpectedFiltered(1, 2, 3), partitionWith.getSelected());
        assertIterablesEqual(this.getExpectedFiltered(-3, -2, -1, 0), partitionWith.getRejected());

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableWithDuplicates = this.newWith(-3, -3, -3, -2, -2, -1, 0, 1, 2, 2, 3, 3, 3);
        PartitionIterable<Integer> partitionWithDuplicates = iterableWithDuplicates.partition(IntegerPredicates.isEven());
        assertIterablesEqual(this.getExpectedFiltered(-2, -2, 0, 2, 2), partitionWithDuplicates.getSelected());
        assertIterablesEqual(this.getExpectedFiltered(-3, -3, -3, -1, 1, 3, 3, 3), partitionWithDuplicates.getRejected());

        PartitionIterable<Integer> partitionWithDuplicatesAndPredicate = iterableWithDuplicates.partitionWith(Predicates2.greaterThan(), 0);
        assertIterablesEqual(this.getExpectedFiltered(1, 2, 2, 3, 3, 3), partitionWithDuplicatesAndPredicate.getSelected());
        assertIterablesEqual(this.getExpectedFiltered(-3, -3, -3, -2, -2, -1, 0), partitionWithDuplicatesAndPredicate.getRejected());
    }

    @Test
    default void RichIterable_selectInstancesOf()
    {
        RichIterable<Number> iterable = this.newWith(1, 2.0, 3, 4.0);

        assertIterablesEqual(this.getExpectedFiltered(), iterable.selectInstancesOf(String.class));
        assertIterablesEqual(this.getExpectedFiltered(1, 3), iterable.selectInstancesOf(Integer.class));
        assertIterablesEqual(this.getExpectedFiltered(1, 2.0, 3, 4.0), iterable.selectInstancesOf(Number.class));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Number> iterable2 = this.newWith(1, 2.0, 2.0, 3, 3, 3, 4.0, 4.0, 4.0, 4.0);
        assertIterablesEqual(this.getExpectedFiltered(1, 3, 3, 3), iterable2.selectInstancesOf(Integer.class));
        assertIterablesEqual(this.getExpectedFiltered(1, 2.0, 2.0, 3, 3, 3, 4.0, 4.0, 4.0, 4.0), iterable2.selectInstancesOf(Number.class));
    }
}
