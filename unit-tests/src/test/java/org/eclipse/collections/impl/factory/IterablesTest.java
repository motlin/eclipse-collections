/*
 * Copyright (c) 2021 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.factory;

import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.factory.SortedMaps;
import org.eclipse.collections.api.factory.SortedSets;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.sorted.ImmutableSortedMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;

public class IterablesTest
{
    @Test
    public void immutableLists()
    {
        this.assertEqualsAndInstanceOf(Lists.mutable.empty().toImmutable(), Lists.immutable.empty(), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1), Lists.immutable.with(1), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2), Lists.immutable.with(1, 2), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3), Lists.immutable.with(1, 2, 3), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4), Lists.immutable.with(1, 2, 3, 4), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(5), Lists.immutable.with(1, 2, 3, 4, 5), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(6), Lists.immutable.with(1, 2, 3, 4, 5, 6), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(7), Lists.immutable.with(1, 2, 3, 4, 5, 6, 7), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(8), Lists.immutable.with(1, 2, 3, 4, 5, 6, 7, 8), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(9), Lists.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(10), Lists.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), ImmutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(11), Lists.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), ImmutableList.class);
    }

    @Test
    public void mutableLists()
    {
        this.assertEqualsAndInstanceOf(Lists.mutable.empty(), Lists.mutable.empty(), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1), Lists.mutable.with(1), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2), Lists.mutable.with(1, 2), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3), Lists.mutable.with(1, 2, 3), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4), Lists.mutable.with(1, 2, 3, 4), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(5), Lists.mutable.with(1, 2, 3, 4, 5), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(6), Lists.mutable.with(1, 2, 3, 4, 5, 6), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(7), Lists.mutable.with(1, 2, 3, 4, 5, 6, 7), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(8), Lists.mutable.with(1, 2, 3, 4, 5, 6, 7, 8), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(9), Lists.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(10), Lists.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), MutableList.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(11), Lists.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), MutableList.class);
    }

    @Test
    public void immutableSets()
    {
        this.assertEqualsAndInstanceOf(UnifiedSet.newSet().toImmutable(), Sets.immutable.empty(), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSet(), Sets.immutable.with(1), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSet(), Sets.immutable.with(1, 2), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSet(), Sets.immutable.with(1, 2, 3), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSet(), Sets.immutable.with(1, 2, 3, 4), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSet(), Sets.immutable.with(1, 2, 3, 4, 5), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSet(), Sets.immutable.with(1, 2, 3, 4, 5, 6), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSet(), Sets.immutable.with(1, 2, 3, 4, 5, 6, 7), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSet(), Sets.immutable.with(1, 2, 3, 4, 5, 6, 7, 8), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSet(), Sets.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSet(), Sets.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), ImmutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSet(), Sets.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), ImmutableSet.class);
    }

    @Test
    public void mutableSets()
    {
        this.assertEqualsAndInstanceOf(UnifiedSet.newSet(), Sets.mutable.empty(), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSet(), Sets.mutable.with(1), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSet(), Sets.mutable.with(1, 2), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSet(), Sets.mutable.with(1, 2, 3), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSet(), Sets.mutable.with(1, 2, 3, 4), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSet(), Sets.mutable.with(1, 2, 3, 4, 5), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSet(), Sets.mutable.with(1, 2, 3, 4, 5, 6), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSet(), Sets.mutable.with(1, 2, 3, 4, 5, 6, 7), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSet(), Sets.mutable.with(1, 2, 3, 4, 5, 6, 7, 8), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSet(), Sets.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSet(), Sets.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), MutableSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSet(), Sets.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), MutableSet.class);
    }

    @Test
    public void mutableBags()
    {
        this.assertEqualsAndInstanceOf(HashBag.newBag(), Bags.mutable.empty(), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toBag(), Bags.mutable.with(1), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toBag(), Bags.mutable.with(1, 2), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toBag(), Bags.mutable.with(1, 2, 3), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toBag(), Bags.mutable.with(1, 2, 3, 4), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toBag(), Bags.mutable.with(1, 2, 3, 4, 5), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toBag(), Bags.mutable.with(1, 2, 3, 4, 5, 6), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toBag(), Bags.mutable.with(1, 2, 3, 4, 5, 6, 7), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toBag(), Bags.mutable.with(1, 2, 3, 4, 5, 6, 7, 8), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toBag(), Bags.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toBag(), Bags.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), MutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toBag(), Bags.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), MutableBag.class);
    }

    @Test
    public void immutableBags()
    {
        this.assertEqualsAndInstanceOf(HashBag.newBag(), Bags.immutable.empty(), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toBag(), Bags.immutable.with(1), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toBag(), Bags.immutable.with(1, 2), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toBag(), Bags.immutable.with(1, 2, 3), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toBag(), Bags.immutable.with(1, 2, 3, 4), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toBag(), Bags.immutable.with(1, 2, 3, 4, 5), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toBag(), Bags.immutable.with(1, 2, 3, 4, 5, 6), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toBag(), Bags.immutable.with(1, 2, 3, 4, 5, 6, 7), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toBag(), Bags.immutable.with(1, 2, 3, 4, 5, 6, 7, 8), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toBag(), Bags.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toBag(), Bags.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), ImmutableBag.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toBag(), Bags.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), ImmutableBag.class);
    }

    @Test
    public void immutableSortedSets()
    {
        this.assertEqualsAndInstanceOf(TreeSortedSet.newSet(), SortedSets.immutable.empty(), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedSet(), SortedSets.immutable.with(1), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedSet(), SortedSets.immutable.with(1, 2), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedSet(), SortedSets.immutable.with(1, 2, 3), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedSet(), SortedSets.immutable.with(1, 2, 3, 4), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSortedSet(), SortedSets.immutable.with(1, 2, 3, 4, 5), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSortedSet(), SortedSets.immutable.with(1, 2, 3, 4, 5, 6), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSortedSet(), SortedSets.immutable.with(1, 2, 3, 4, 5, 6, 7), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSortedSet(), SortedSets.immutable.with(1, 2, 3, 4, 5, 6, 7, 8), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSortedSet(), SortedSets.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSortedSet(), SortedSets.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSortedSet(), SortedSets.immutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), ImmutableSortedSet.class);
    }

    @Test
    public void immutableSortedSetsWithComparator()
    {
        this.assertEqualsAndInstanceOf(TreeSortedSet.newSet(Comparators.reverseNaturalOrder()), SortedSets.immutable.with(Comparators.reverseNaturalOrder()), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1, 2), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), ImmutableSortedSet.class);
    }

    @Test
    public void mutableSortedSetsWithComparator()
    {
        this.assertEqualsAndInstanceOf(TreeSortedSet.newSet(Comparators.reverseNaturalOrder()), SortedSets.immutable.with(Comparators.reverseNaturalOrder()), ImmutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1, 2), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSortedSet(Comparators.reverseNaturalOrder()),
                SortedSets.mutable.with(Comparators.reverseNaturalOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedSets()
    {
        this.assertEqualsAndInstanceOf(TreeSortedSet.newSet(), SortedSets.mutable.empty(), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedSet(), SortedSets.mutable.with(1), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedSet(), SortedSets.mutable.with(1, 2), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedSet(), SortedSets.mutable.with(1, 2, 3), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedSet(), SortedSets.mutable.with(1, 2, 3, 4), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(5).toSortedSet(), SortedSets.mutable.with(1, 2, 3, 4, 5), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(6).toSortedSet(), SortedSets.mutable.with(1, 2, 3, 4, 5, 6), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(7).toSortedSet(), SortedSets.mutable.with(1, 2, 3, 4, 5, 6, 7), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(8).toSortedSet(), SortedSets.mutable.with(1, 2, 3, 4, 5, 6, 7, 8), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(9).toSortedSet(), SortedSets.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(10).toSortedSet(), SortedSets.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10), MutableSortedSet.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(11).toSortedSet(), SortedSets.mutable.with(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11), MutableSortedSet.class);
    }

    @Test
    public void mutableSortedMaps()
    {
        this.assertEqualsAndInstanceOf(TreeSortedMap.newMap(), SortedMaps.mutable.empty(), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedMap(Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(1, 1), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedMap(Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(1, 1, 2, 2), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedMap(Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(1, 1, 2, 2, 3, 3), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedMap(Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(1, 1, 2, 2, 3, 3, 4, 4), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMapsWithComparator()
    {
        this.assertEqualsAndInstanceOf(TreeSortedMap.newMap(Comparators.reverseNaturalOrder()), SortedMaps.mutable.with(Comparators.reverseNaturalOrder()), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(TreeSortedMap.newMap(Comparators.reverseNaturalOrder()), SortedMaps.mutable.with(null), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(Comparators.reverseNaturalOrder(), 1, 1), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3, 4, 4), MutableSortedMap.class);
    }

    @Test
    public void mutableSortedMapsWithFunction()
    {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedMapBy(key -> -key, Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(Comparators.reverseNaturalOrder(), 1, 1), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedMapBy(key -> -key, Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedMapBy(key -> key, Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3), MutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedMapBy(key -> -key, Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.mutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3, 4, 4), MutableSortedMap.class);
    }

    @Test
    public void immutableSortedMaps()
    {
        this.assertEqualsAndInstanceOf(TreeSortedMap.newMap(), SortedMaps.immutable.empty(), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedMap(Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(1, 1), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedMap(Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(1, 1, 2, 2), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedMap(Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(1, 1, 2, 2, 3, 3), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedMap(Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(1, 1, 2, 2, 3, 3, 4, 4), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMapsWithComparator()
    {
        this.assertEqualsAndInstanceOf(TreeSortedMap.newMap(Comparators.reverseNaturalOrder()), SortedMaps.immutable.with(Comparators.reverseNaturalOrder()), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(Comparators.reverseNaturalOrder(), 1, 1), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedMap(Comparators.reverseNaturalOrder(), Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3, 4, 4), ImmutableSortedMap.class);
    }

    @Test
    public void immutableSortedMapsWithFunction()
    {
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toSortedMapBy(key -> -key, Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(Comparators.reverseNaturalOrder(), 1, 1), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toSortedMapBy(key -> -key, Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toSortedMapBy(key -> -key, Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3), ImmutableSortedMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toSortedMapBy(key -> -key, Functions.getPassThru(), Functions.getPassThru()),
                SortedMaps.immutable.with(Comparators.reverseNaturalOrder(), 1, 1, 2, 2, 3, 3, 4, 4), ImmutableSortedMap.class);
    }

    @Test
    public void mutableMaps()
    {
        this.assertEqualsAndInstanceOf(UnifiedMap.newMap(), Maps.mutable.empty(), MutableMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toMap(Functions.getPassThru(), Functions.getPassThru()),
                Maps.mutable.with(1, 1), MutableMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toMap(Functions.getPassThru(), Functions.getPassThru()),
                Maps.mutable.with(1, 1, 2, 2), MutableMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toMap(Functions.getPassThru(), Functions.getPassThru()),
                Maps.mutable.with(1, 1, 2, 2, 3, 3), MutableMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toMap(Functions.getPassThru(), Functions.getPassThru()),
                Maps.mutable.with(1, 1, 2, 2, 3, 3, 4, 4), MutableMap.class);
    }

    @Test
    public void immutableMaps()
    {
        this.assertEqualsAndInstanceOf(UnifiedMap.newMap(), Maps.immutable.empty(), ImmutableMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(1).toMap(Functions.getPassThru(), Functions.getPassThru()),
                Maps.immutable.with(1, 1), ImmutableMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(2).toMap(Functions.getPassThru(), Functions.getPassThru()),
                Maps.immutable.with(1, 1, 2, 2), ImmutableMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(3).toMap(Functions.getPassThru(), Functions.getPassThru()),
                Maps.immutable.with(1, 1, 2, 2, 3, 3), ImmutableMap.class);
        this.assertEqualsAndInstanceOf(Interval.oneTo(4).toMap(Functions.getPassThru(), Functions.getPassThru()),
                Maps.immutable.with(1, 1, 2, 2, 3, 3, 4, 4), ImmutableMap.class);
    }

    public void assertEqualsAndInstanceOf(Object expected, Object actual, Class<?> clazz)
    {
        Verify.assertEqualsAndHashCode(expected, actual);
        Verify.assertInstanceOf(clazz, actual);
    }

    @Test
    public void classIsNonInstantiable()
    {
        Verify.assertClassNonInstantiable(Iterables.class);
    }
}
