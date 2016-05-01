/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.set.sorted.mutable;

import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.ListAdapter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.eclipse.collections.impl.test.Verify.assertEquals;
import static org.eclipse.collections.impl.test.Verify.assertFalse;
import static org.eclipse.collections.impl.test.Verify.assertListsEqual;
import static org.eclipse.collections.impl.test.Verify.assertThrows;
import static org.eclipse.collections.impl.test.Verify.assertTrue;

public class ScapegoatTreeSetTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ScapegoatTreeSetTest.class);

    @Test
    public void smokeTest()
    {
        // Add
        ScapegoatTreeSet<Integer> scapegoatTreeSet = new ScapegoatTreeSet<>(0.55f, 0.28f, 0.35f);
        for (int i = 0; i < 200; i++)
        {
            assertEquals(String.valueOf(i), i, scapegoatTreeSet.size());
            assertTrue(String.valueOf(i), scapegoatTreeSet.height() <= i);
            assertTrue(String.valueOf(i), scapegoatTreeSet.add(i));
            assertFalse(String.valueOf(i), scapegoatTreeSet.add(i));
            assertTrue(String.valueOf(i), scapegoatTreeSet.contains(i));
            assertListsEqual(String.valueOf(i), Interval.zeroTo(i), scapegoatTreeSet.toList());

            assertTrue(String.valueOf(i), scapegoatTreeSet.remove(i));
            assertFalse(String.valueOf(i), scapegoatTreeSet.contains(i));
            assertTrue(String.valueOf(i), scapegoatTreeSet.add(i));
            assertTrue(String.valueOf(i), scapegoatTreeSet.contains(i));
            assertListsEqual(String.valueOf(i), Interval.zeroTo(i), scapegoatTreeSet.toList());
        }

        assertFalse(scapegoatTreeSet.contains(201));
        assertFalse(scapegoatTreeSet.remove(201));

        // Remove all
        for (int i = 0; i < 200; i++)
        {
            assertEquals(Interval.fromTo(i, 199), scapegoatTreeSet.toList());
            assertEquals(String.valueOf(200 - i), 200 - i, scapegoatTreeSet.size());
            assertTrue(String.valueOf(i), scapegoatTreeSet.height() <= 200 - i);
            assertTrue(String.valueOf(i), scapegoatTreeSet.contains(i));
            assertTrue(String.valueOf(i), scapegoatTreeSet.remove(i));
            assertFalse(String.valueOf(i), scapegoatTreeSet.remove(i));
            assertFalse(String.valueOf(i), scapegoatTreeSet.contains(i));

            for (int j = 0; j <= i; j++)
            {
                assertFalse(j + " " + i, scapegoatTreeSet.contains(j));
            }
            for (int j = i + 1; j < 200; j++)
            {
                assertTrue(j + " " + i, scapegoatTreeSet.contains(j));
            }
        }

        assertEquals(Lists.immutable.of(), scapegoatTreeSet.toList());

        // Add back
        MutableList<Integer> integers = Interval.zeroTo(200).toList();
        Collections.shuffle(integers);
        integers.forEach(Procedures.cast(scapegoatTreeSet::add));
        Collections.shuffle(integers);

        integers.forEach(Procedures.cast(i ->
        {
            assertTrue(String.valueOf(i), scapegoatTreeSet.contains(i));
            assertTrue(String.valueOf(i), scapegoatTreeSet.remove(i));
            assertFalse(String.valueOf(i), scapegoatTreeSet.contains(i));
        }));

        assertEquals(Lists.immutable.of(), scapegoatTreeSet.toList());
    }

    @Test
    public void minSizeForDepth()
    {
        assertEquals(1, ScapegoatTreeSet.minSizeForDepth(1, 0.50f));
        assertEquals(2, ScapegoatTreeSet.minSizeForDepth(2, 0.50f));
        assertEquals(4, ScapegoatTreeSet.minSizeForDepth(3, 0.50f));
        assertEquals(8, ScapegoatTreeSet.minSizeForDepth(4, 0.50f));
        assertEquals(16, ScapegoatTreeSet.minSizeForDepth(5, 0.50f));

        assertEquals(1, ScapegoatTreeSet.minSizeForDepth(1, 0.66f));
        assertEquals(2, ScapegoatTreeSet.minSizeForDepth(2, 0.66f));
        assertEquals(3, ScapegoatTreeSet.minSizeForDepth(3, 0.66f));
        assertEquals(4, ScapegoatTreeSet.minSizeForDepth(4, 0.66f));
        assertEquals(6, ScapegoatTreeSet.minSizeForDepth(5, 0.66f));
        assertEquals(43, ScapegoatTreeSet.minSizeForDepth(10, 0.66f));
        assertEquals(2684, ScapegoatTreeSet.minSizeForDepth(20, 0.66f));
        assertEquals(171091, ScapegoatTreeSet.minSizeForDepth(30, 0.66f));
        assertThrows(IllegalArgumentException.class, () -> ScapegoatTreeSet.minSizeForDepth(0, 0.66f));
        // TODO consider overflow
        // Assert.assertEquals(9223372036854775807L, ScapegoatTreeSet.minSizeForDepth(1000, 0.66f));
        assertEquals(512, ScapegoatTreeSet.minSizeForDepth(10, 0.50f));
        assertEquals(1, ScapegoatTreeSet.minSizeForDepth(10, 1.0f));
    }

    @Test
    public void calculateMidPoint()
    {
        assertEquals(1, ScapegoatTreeSet.calculateMidPoint(0, 3));
        assertEquals(1, ScapegoatTreeSet.calculateMidPoint(0, 4));
        assertEquals(2, ScapegoatTreeSet.calculateMidPoint(0, 5));
        assertEquals(3, ScapegoatTreeSet.calculateMidPoint(0, 6));
        assertEquals(3, ScapegoatTreeSet.calculateMidPoint(0, 7));
        assertEquals(3, ScapegoatTreeSet.calculateMidPoint(0, 8));
        assertEquals(4, ScapegoatTreeSet.calculateMidPoint(0, 9));
        assertEquals(5, ScapegoatTreeSet.calculateMidPoint(0, 10));
        assertEquals(5, ScapegoatTreeSet.calculateMidPoint(0, 11));

        assertEquals(2, ScapegoatTreeSet.calculateMidPoint(1, 3));
        assertEquals(3, ScapegoatTreeSet.calculateMidPoint(1, 4));
        assertEquals(3, ScapegoatTreeSet.calculateMidPoint(1, 5));
        assertEquals(3, ScapegoatTreeSet.calculateMidPoint(1, 6));
        assertEquals(4, ScapegoatTreeSet.calculateMidPoint(1, 7));
        assertEquals(5, ScapegoatTreeSet.calculateMidPoint(1, 8));
        assertEquals(5, ScapegoatTreeSet.calculateMidPoint(1, 9));
        assertEquals(5, ScapegoatTreeSet.calculateMidPoint(1, 10));
        assertEquals(6, ScapegoatTreeSet.calculateMidPoint(1, 11));

        assertEquals(3, ScapegoatTreeSet.calculateMidPoint(2, 3));
        assertEquals(3, ScapegoatTreeSet.calculateMidPoint(2, 4));
        assertEquals(4, ScapegoatTreeSet.calculateMidPoint(2, 5));
        assertEquals(5, ScapegoatTreeSet.calculateMidPoint(2, 6));
        assertEquals(5, ScapegoatTreeSet.calculateMidPoint(2, 7));
        assertEquals(5, ScapegoatTreeSet.calculateMidPoint(2, 8));
        assertEquals(6, ScapegoatTreeSet.calculateMidPoint(2, 9));
        assertEquals(7, ScapegoatTreeSet.calculateMidPoint(2, 10));
        assertEquals(7, ScapegoatTreeSet.calculateMidPoint(2, 11));
    }

    @Test
    public void stress_test_with_TreeSet()
    {
        long currentTimeMillis = System.currentTimeMillis();
        LOGGER.info("currentTimeMillis = {}", currentTimeMillis);
        Random random = new Random(currentTimeMillis);

        Set<Integer> treeSet = new TreeSet<>();

        ScapegoatTreeSet<Integer> scapegoatTreeSet = new ScapegoatTreeSet<>(0.55f, 0.28f, 0.35f);

        for (int i = 0; i < 100_000; i++)
        {
            int each = random.nextInt(50_000);
            boolean contains = treeSet.contains(each);
            assertEquals(contains, scapegoatTreeSet.contains(each));
            if (contains)
            {
                assertTrue(scapegoatTreeSet.remove(each));
                assertTrue(treeSet.remove(each));
                assertFalse(scapegoatTreeSet.remove(each));
                assertFalse(treeSet.remove(each));
            }
            else
            {
                assertTrue(scapegoatTreeSet.add(each));
                assertTrue(treeSet.add(each));
                assertFalse(scapegoatTreeSet.add(each));
                assertFalse(treeSet.add(each));
            }

            assertEquals(treeSet.size(), scapegoatTreeSet.size());

            for (int j = 0; j < 5; j++)
            {
                int eachRemove = random.nextInt(50_000);
                boolean containsRemove = treeSet.contains(eachRemove);
                assertEquals(containsRemove, scapegoatTreeSet.contains(eachRemove));
                if (containsRemove)
                {
                    assertTrue(String.valueOf(eachRemove), scapegoatTreeSet.remove(eachRemove));
                    assertTrue(String.valueOf(eachRemove), treeSet.remove(eachRemove));
                    assertFalse(String.valueOf(eachRemove), scapegoatTreeSet.remove(eachRemove));
                    assertFalse(String.valueOf(eachRemove), treeSet.remove(eachRemove));
                }
            }

            assertEquals(treeSet.size(), scapegoatTreeSet.size());
        }
    }

    @Test
    public void stress_test_growth()
    {
        ScapegoatTreeSet<Integer> scapegoatTreeSet = new ScapegoatTreeSet<>(0.55f, 0.28f, 0.35f);

        for (int i = 1; i < 1_000_000; i++)
        {
            assertTrue(scapegoatTreeSet.add(i));
            assertEquals(i, scapegoatTreeSet.size());
        }
        assertListsEqual(Interval.oneTo(999_999), scapegoatTreeSet.toList());

        for (int i = 999_999; i > 0; i--)
        {
            assertEquals(i, scapegoatTreeSet.size());
            scapegoatTreeSet.remove(i);
        }
        assertListsEqual(Lists.mutable.of(), scapegoatTreeSet.toList());
    }

    @Test
    public void stress_test_random()
    {
        long currentTimeMillis = System.currentTimeMillis();
        LOGGER.info("currentTimeMillis = {}", currentTimeMillis);
        Random random = new Random(currentTimeMillis);

        ScapegoatTreeSet<Long> scapegoatTreeSet = new ScapegoatTreeSet<>(0.55f, 0.28f, 0.35f);

        MutableList<Long> longs = ListAdapter.adapt(random.longs(1_000_000).boxed().collect(Collectors.toList()));
        for (Long aLong : longs)
        {
            scapegoatTreeSet.add(aLong);
        }
        assertEquals(longs.toSortedList().distinct(), scapegoatTreeSet.toList());

        for (Long aLong : longs)
        {
            scapegoatTreeSet.remove(aLong);
        }
        assertEquals(0, scapegoatTreeSet.size());
    }

    @Test
    public void build_from_sorted_list()
    {
        ScapegoatTreeSet<Integer> small = ScapegoatTreeSet.buildFromSortedList(Interval.oneTo(15).toList(), 0.60f, 0.60f, 0.60f);
        assertEquals(small.prettyPrint(), Interval.oneTo(15), small.toList());

        ScapegoatTreeSet<Integer> medium = ScapegoatTreeSet.buildFromSortedList(Interval.oneTo(31).toList(), 0.60f, 0.60f, 0.60f);
        assertEquals(medium.prettyPrint(), Interval.oneTo(31), medium.toList());

        ScapegoatTreeSet<Integer> large = ScapegoatTreeSet.buildFromSortedList(Interval.oneTo(63).toList(), 0.60f, 0.60f, 0.60f);
        assertEquals(large.prettyPrint(), Interval.oneTo(63), large.toList());

        ScapegoatTreeSet<Integer> twoLess = ScapegoatTreeSet.buildFromSortedList(Interval.oneTo(29).toList(), 0.60f, 0.60f, 0.60f);
        assertEquals(twoLess.prettyPrint(), Interval.oneTo(29), twoLess.toList());

        ScapegoatTreeSet<Integer> oneLess = ScapegoatTreeSet.buildFromSortedList(Interval.oneTo(30).toList(), 0.60f, 0.60f, 0.60f);
        assertEquals(oneLess.prettyPrint(), Interval.oneTo(30), oneLess.toList());

        ScapegoatTreeSet<Integer> oneMore = ScapegoatTreeSet.buildFromSortedList(Interval.oneTo(32).toList(), 0.60f, 0.60f, 0.60f);
        assertEquals(oneMore.prettyPrint(), Interval.oneTo(32), oneMore.toList());

        ScapegoatTreeSet<Integer> twoMore = ScapegoatTreeSet.buildFromSortedList(Interval.oneTo(33).toList(), 0.60f, 0.60f, 0.60f);
        assertEquals(twoMore.prettyPrint(), Interval.oneTo(33), twoMore.toList());
    }
}
