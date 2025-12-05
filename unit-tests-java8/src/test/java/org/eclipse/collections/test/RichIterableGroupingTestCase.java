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
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface RichIterableGroupingTestCase extends InternalIterableTestCase
{
    @Test
    default void RichIterable_groupBy()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);
        Function<Integer, Boolean> groupByFunction = object -> IntegerPredicates.isOdd().accept(object);

        MutableMap<Boolean, RichIterable<Integer>> groupByExpected =
                UnifiedMap.newWithKeysValues(
                        Boolean.TRUE, this.newMutableForFilter(3, 1),
                        Boolean.FALSE, this.newMutableForFilter(4, 2));

        assertIterablesEqual(groupByExpected, iterable.groupBy(groupByFunction).toMap());

        Function<Integer, Boolean> function = (Integer object) -> true;
        MutableMultimap<Boolean, Integer> target = this.<Integer>newWith().groupBy(function).toMutable();
        MutableMultimap<Boolean, Integer> multimap2 = iterable.groupBy(groupByFunction, target);
        assertIterablesEqual(groupByExpected, multimap2.toMap());
        assertSame(target, multimap2);

        Function<Integer, Iterable<Integer>> groupByEachFunction = integer -> Interval.fromTo(-1, -integer);

        MutableMap<Integer, RichIterable<Integer>> expectedGroupByEach =
                UnifiedMap.newWithKeysValues(
                        -4, this.newMutableForFilter(4),
                        -3, this.newMutableForFilter(4, 3),
                        -2, this.newMutableForFilter(4, 3, 2),
                        -1, this.newMutableForFilter(4, 3, 2, 1));

        assertIterablesEqual(expectedGroupByEach, iterable.groupByEach(groupByEachFunction).toMap());

        MutableMultimap<Integer, Integer> target2 = this.<Integer>newWith().groupByEach(groupByEachFunction).toMutable();
        Multimap<Integer, Integer> actualWithTarget = iterable.groupByEach(groupByEachFunction, target2);
        assertIterablesEqual(expectedGroupByEach, actualWithTarget.toMap());
        assertSame(target2, actualWithTarget);

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        MutableMap<Boolean, RichIterable<Integer>> expectedGroupByDup =
                UnifiedMap.newWithKeysValues(
                        Boolean.TRUE, this.newMutableForFilter(3, 3, 3, 1),
                        Boolean.FALSE, this.newMutableForFilter(4, 4, 4, 4, 2, 2));

        assertIterablesEqual(expectedGroupByDup, iterableDup.groupBy(groupByFunction).toMap());

        MutableMultimap<Boolean, Integer> targetDup = this.<Integer>newWith().groupBy(function).toMutable();
        MutableMultimap<Boolean, Integer> multimap2Dup = iterableDup.groupBy(groupByFunction, targetDup);
        assertIterablesEqual(expectedGroupByDup, multimap2Dup.toMap());
        assertSame(targetDup, multimap2Dup);

        MutableMap<Integer, RichIterable<Integer>> expectedGroupByEachDup =
                UnifiedMap.newWithKeysValues(
                        -4, this.newMutableForFilter(4, 4, 4, 4),
                        -3, this.newMutableForFilter(4, 4, 4, 4, 3, 3, 3),
                        -2, this.newMutableForFilter(4, 4, 4, 4, 3, 3, 3, 2, 2),
                        -1, this.newMutableForFilter(4, 4, 4, 4, 3, 3, 3, 2, 2, 1));

        assertIterablesEqual(expectedGroupByEachDup, iterableDup.groupByEach(groupByEachFunction).toMap());

        MutableMultimap<Integer, Integer> target2Dup = this.<Integer>newWith().groupByEach(groupByEachFunction).toMutable();
        Multimap<Integer, Integer> actualWithTargetDup = iterableDup.groupByEach(groupByEachFunction, target2Dup);
        assertIterablesEqual(expectedGroupByEachDup, actualWithTargetDup.toMap());
        assertSame(target2Dup, actualWithTargetDup);
    }

    @Test
    default void RichIterable_groupByAndCollect()
    {
        RichIterable<Integer> iterable = this.newWith(10, 9, 8, 7, 6, 5, 4, 3, 2, 1);
        Function<Integer, Boolean> groupByFunction = integer -> IntegerPredicates.isOdd().accept(integer);
        Function<Integer, Integer> collectFunction = integer -> integer + 2;

        FastList<Integer> expectedOddNumberList = FastList.newListWith(3, 5, 7, 9, 11);
        FastList<Integer> expectedEvenNumberList = FastList.newListWith(4, 6, 8, 10, 12);

        MutableListMultimap<Boolean, Integer> targetResult = iterable.groupByAndCollect(groupByFunction, collectFunction, Multimaps.mutable.list.empty());

        assertTrue(expectedOddNumberList.containsAll(targetResult.get(Boolean.TRUE)));
        assertTrue(expectedEvenNumberList.containsAll(targetResult.get(Boolean.FALSE)));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);
        FastList<Integer> expectedOddNumberListDup = FastList.newListWith(3, 5, 5, 5, 5, 5, 5, 5);
        FastList<Integer> expectedEvenNumberListDup = FastList.newListWith(4, 4, 6, 6, 6, 6);

        MutableListMultimap<Boolean, Integer> targetResultDup = iterableDup.groupByAndCollect(groupByFunction, collectFunction, Multimaps.mutable.list.empty());

        assertTrue(expectedOddNumberListDup.containsAll(targetResultDup.get(Boolean.TRUE)));
        assertTrue(expectedEvenNumberListDup.containsAll(targetResultDup.get(Boolean.FALSE)));
    }
}
