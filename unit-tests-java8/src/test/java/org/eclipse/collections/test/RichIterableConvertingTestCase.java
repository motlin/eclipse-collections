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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.factory.SortedSets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isOneOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public interface RichIterableConvertingTestCase extends InternalIterableTestCase
{
    @Test
    default void RichIterable_into()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertIterablesEqual(
                        Lists.immutable.with(0, 4, 3, 2, 1),
                        iterable.into(Lists.mutable.with(0)));
                case SORTED_NATURAL -> assertIterablesEqual(
                        Lists.immutable.with(0, 1, 2, 3, 4),
                        iterable.into(Lists.mutable.with(0)));
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        MutableList<Integer> target = Lists.mutable.with(0);
        iterable.each(target::add);
        assertIterablesEqual(
                target,
                iterable.into(Lists.mutable.with(0)));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> duplicateIterable = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertIterablesEqual(
                        Lists.immutable.with(0, 4, 4, 4, 4, 3, 3, 3, 2, 2, 1),
                        duplicateIterable.into(Lists.mutable.with(0)));
                case SORTED_NATURAL -> assertIterablesEqual(
                        Lists.immutable.with(0, 1, 2, 2, 3, 3, 3, 4, 4, 4, 4),
                        duplicateIterable.into(Lists.mutable.with(0)));
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        MutableList<Integer> duplicateTarget = Lists.mutable.with(0);
        duplicateIterable.each(duplicateTarget::add);
        assertIterablesEqual(
                duplicateTarget,
                duplicateIterable.into(Lists.mutable.with(0)));
    }

    @Test
    default void RichIterable_toList()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertIterablesEqual(
                        Lists.immutable.with(4, 3, 2, 1),
                        iterable.toList());
                case SORTED_NATURAL -> assertIterablesEqual(
                        Lists.immutable.with(1, 2, 3, 4),
                        iterable.toList());
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        MutableList<Integer> target = Lists.mutable.empty();
        iterable.each(target::add);
        assertIterablesEqual(
                target,
                iterable.toList());

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> duplicateIterable = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertIterablesEqual(
                        Lists.immutable.with(4, 4, 4, 4, 3, 3, 3, 2, 2, 1),
                        duplicateIterable.toList());
                case SORTED_NATURAL -> assertIterablesEqual(
                        Lists.immutable.with(1, 2, 2, 3, 3, 3, 4, 4, 4, 4),
                        duplicateIterable.toList());
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        MutableList<Integer> duplicateTarget = Lists.mutable.empty();
        duplicateIterable.each(duplicateTarget::add);
        assertIterablesEqual(
                duplicateTarget,
                duplicateIterable.toList());
    }

    @Test
    default void RichIterable_toSortedList()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        assertIterablesEqual(
                Lists.immutable.with(1, 2, 3, 4),
                iterable.toSortedList());

        assertIterablesEqual(
                Lists.immutable.with(4, 3, 2, 1),
                iterable.toSortedList(Comparators.reverseNaturalOrder()));

        assertIterablesEqual(
                Lists.immutable.with(1, 2, 3, 4),
                iterable.toSortedListBy(Functions.identity()));

        assertIterablesEqual(
                Lists.immutable.with(4, 3, 2, 1),
                iterable.toSortedListBy(each -> each * -1));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterable2 = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        assertIterablesEqual(
                Lists.immutable.with(1, 2, 2, 3, 3, 3, 4, 4, 4, 4),
                iterable2.toSortedList());

        assertIterablesEqual(
                Lists.immutable.with(4, 4, 4, 4, 3, 3, 3, 2, 2, 1),
                iterable2.toSortedList(Comparators.reverseNaturalOrder()));

        assertIterablesEqual(
                Lists.immutable.with(1, 2, 2, 3, 3, 3, 4, 4, 4, 4),
                iterable2.toSortedListBy(Functions.identity()));

        assertIterablesEqual(
                Lists.immutable.with(4, 4, 4, 4, 3, 3, 3, 2, 2, 1),
                iterable2.toSortedListBy(each -> each * -1));
    }

    @Test
    default void RichIterable_toSet()
    {
        assertIterablesEqual(
                Sets.immutable.with(4, 3, 2, 1),
                this.newWith(4, 3, 2, 1).toSet());

        if (!this.allowsDuplicates())
        {
            return;
        }

        assertIterablesEqual(
                Sets.immutable.with(4, 3, 2, 1),
                this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1).toSet());
    }

    @Test
    default void RichIterable_toSortedSet()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        assertIterablesEqual(
                SortedSets.immutable.with(1, 2, 3, 4),
                iterable.toSortedSet());

        assertIterablesEqual(
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 4, 3, 2, 1),
                iterable.toSortedSet(Comparators.reverseNaturalOrder()));

        assertIterablesEqual(
                SortedSets.immutable.with(Comparators.byFunction(Functions.identity()), 1, 2, 3, 4),
                iterable.toSortedSetBy(Functions.identity()));

        assertIterablesEqual(
                SortedSets.immutable.with(Comparators.byFunction((Integer each) -> each * -1), 4, 3, 2, 1),
                iterable.toSortedSetBy(each -> each * -1));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterable2 = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        assertIterablesEqual(
                SortedSets.immutable.with(1, 2, 3, 4),
                iterable2.toSortedSet());

        assertIterablesEqual(
                SortedSets.immutable.with(Comparators.reverseNaturalOrder(), 4, 3, 2, 1),
                iterable2.toSortedSet(Comparators.reverseNaturalOrder()));

        assertIterablesEqual(
                SortedSets.immutable.with(Comparators.byFunction(Functions.identity()), 1, 2, 3, 4),
                iterable2.toSortedSetBy(Functions.identity()));

        assertIterablesEqual(
                SortedSets.immutable.with(Comparators.byFunction((Integer each) -> each * -1), 4, 3, 2, 1),
                iterable2.toSortedSetBy(each -> each * -1));
    }

    @Test
    default void RichIterable_toBag()
    {
        assertIterablesEqual(
                Bags.immutable.with(4, 3, 2, 1),
                this.newWith(4, 3, 2, 1).toBag());

        if (!this.allowsDuplicates())
        {
            return;
        }

        assertIterablesEqual(
                Bags.immutable.with(4, 4, 4, 4, 3, 3, 3, 2, 2, 1),
                this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1).toBag());
    }

    @Test
    default void RichIterable_toSortedBag()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        assertIterablesEqual(
                TreeBag.newBagWith(1, 2, 3, 4),
                iterable.toSortedBag());

        assertIterablesEqual(
                TreeBag.newBagWith(Comparators.reverseNaturalOrder(), 4, 3, 2, 1),
                iterable.toSortedBag(Comparators.reverseNaturalOrder()));

        assertIterablesEqual(
                TreeBag.newBagWith(Comparators.byFunction(Functions.identity()), 1, 2, 3, 4),
                iterable.toSortedBagBy(Functions.identity()));

        assertIterablesEqual(
                TreeBag.newBagWith(Comparators.byFunction((Integer each) -> each * -1), 4, 3, 2, 1),
                iterable.toSortedBagBy(each -> each * -1));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterable2 = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        assertIterablesEqual(
                TreeBag.newBagWith(1, 2, 2, 3, 3, 3, 4, 4, 4, 4),
                iterable2.toSortedBag());

        assertIterablesEqual(
                TreeBag.newBagWith(Comparators.reverseNaturalOrder(), 4, 4, 4, 4, 3, 3, 3, 2, 2, 1),
                iterable2.toSortedBag(Comparators.reverseNaturalOrder()));

        assertIterablesEqual(
                TreeBag.newBagWith(Comparators.byFunction(Functions.identity()), 1, 2, 2, 3, 3, 3, 4, 4, 4, 4),
                iterable2.toSortedBagBy(Functions.identity()));

        assertIterablesEqual(
                TreeBag.newBagWith(Comparators.byFunction((Integer each) -> each * -1), 4, 4, 4, 4, 3, 3, 3, 2, 2, 1),
                iterable2.toSortedBagBy(each -> each * -1));
    }

    @Test
    default void RichIterable_toMap()
    {
        RichIterable<Integer> iterable = this.newWith(13, 12, 11, 3, 2, 1);

        assertIterablesEqual(
                UnifiedMap.newMapWith(
                        Tuples.pair("13", 3),
                        Tuples.pair("12", 2),
                        Tuples.pair("11", 1),
                        Tuples.pair("3", 3),
                        Tuples.pair("2", 2),
                        Tuples.pair("1", 1)),
                iterable.toMap(Object::toString, each -> each % 10));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterable2 = this.newWith(13, 13, 12, 12, 11, 11, 3, 3, 2, 2, 1, 1);

        assertIterablesEqual(
                UnifiedMap.newMapWith(
                        Tuples.pair("13", 3),
                        Tuples.pair("12", 2),
                        Tuples.pair("11", 1),
                        Tuples.pair("3", 3),
                        Tuples.pair("2", 2),
                        Tuples.pair("1", 1)),
                iterable2.toMap(Object::toString, each -> each % 10));
    }

    @Test
    default void RichIterable_toMapTarget()
    {
        RichIterable<Integer> iterable = this.newWith(13, 12, 11, 3, 2, 1);

        Map<String, Integer> jdkMap = new HashMap<>();
        jdkMap.put("13", 3);
        jdkMap.put("12", 2);
        jdkMap.put("11", 1);
        jdkMap.put("3", 3);
        jdkMap.put("2", 2);
        jdkMap.put("1", 1);

        assertIterablesEqual(
                jdkMap,
                iterable.toMap(Object::toString, each -> each % 10, new HashMap<>()));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(13, 13, 12, 12, 11, 11, 3, 3, 2, 2, 1, 1);

        assertIterablesEqual(
                jdkMap,
                iterableDup.toMap(Object::toString, each -> each % 10, new HashMap<>()));
    }

    @Test
    default void RichIterable_toSortedMap()
    {
        RichIterable<Integer> iterable = this.newWith(13, 12, 11, 3, 2, 1);

        Pair<String, Integer>[] pairs = new Pair[]
                {
                        Tuples.pair("13", 3),
                        Tuples.pair("12", 2),
                        Tuples.pair("11", 1),
                        Tuples.pair("3", 3),
                        Tuples.pair("2", 2),
                        Tuples.pair("1", 1),
                };
        assertIterablesEqual(
                TreeSortedMap.newMapWith(pairs),
                iterable.toSortedMap(Object::toString, each -> each % 10));

        assertIterablesEqual(
                TreeSortedMap.newMapWith(
                        Comparators.reverseNaturalOrder(),
                        pairs),
                iterable.toSortedMap(Comparators.reverseNaturalOrder(), Object::toString, each -> each % 10));

        assertIterablesEqual(
                TreeSortedMap.newMapWith(
                        Comparators.naturalOrder(),
                        pairs),
                iterable.toSortedMapBy(Functions.getStringPassThru(), Object::toString, each -> each % 10));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> duplicateIterable = this.newWith(13, 13, 12, 12, 11, 11, 3, 3, 2, 2, 1, 1);

        Pair<String, Integer>[] duplicatePairs = new Pair[]
                {
                        Tuples.pair("13", 3),
                        Tuples.pair("12", 2),
                        Tuples.pair("11", 1),
                        Tuples.pair("3", 3),
                        Tuples.pair("2", 2),
                        Tuples.pair("1", 1),
                };
        assertIterablesEqual(
                TreeSortedMap.newMapWith(duplicatePairs),
                duplicateIterable.toSortedMap(Object::toString, each -> each % 10));

        assertIterablesEqual(
                TreeSortedMap.newMapWith(
                        Comparators.reverseNaturalOrder(),
                        duplicatePairs),
                duplicateIterable.toSortedMap(Comparators.reverseNaturalOrder(), Object::toString, each -> each % 10));

        assertIterablesEqual(
                TreeSortedMap.newMapWith(
                        Comparators.naturalOrder(),
                        duplicatePairs),
                duplicateIterable.toSortedMapBy(Functions.getStringPassThru(), Object::toString, each -> each % 10));
    }

    @Test
    default void RichIterable_toArray()
    {
        Object[] array = this.newWith(3, 2, 1).toArray();
        assertIterablesEqual(Bags.immutable.with(3, 2, 1), HashBag.newBagWith(array));

        if (!this.allowsDuplicates())
        {
            return;
        }

        Object[] array2 = this.newWith(3, 3, 3, 2, 2, 1).toArray();
        assertIterablesEqual(Bags.immutable.with(3, 3, 3, 2, 2, 1), HashBag.newBagWith(array2));
    }

    @Test
    default void RichIterable_makeString_appendString()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL ->
                {
                    assertEquals("4, 3, 2, 1", iterable.makeString());
                    assertEquals("4/3/2/1", iterable.makeString("/"));
                    assertEquals("[4/3/2/1]", iterable.makeString("[", "/", "]"));
                }
                case SORTED_NATURAL ->
                {
                    assertEquals("1, 2, 3, 4", iterable.makeString());
                    assertEquals("1/2/3/4", iterable.makeString("/"));
                    assertEquals("[1/2/3/4]", iterable.makeString("[", "/", "]"));
                }
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        StringBuilder stringBuilder1 = new StringBuilder();
        iterable.appendString(stringBuilder1);
        assertEquals(iterable.makeString(), stringBuilder1.toString());

        StringBuilder stringBuilder2 = new StringBuilder();
        iterable.appendString(stringBuilder2, "/");
        assertEquals(iterable.makeString("/"), stringBuilder2.toString());

        StringBuilder stringBuilder3 = new StringBuilder();
        iterable.appendString(stringBuilder3, "[", "/", "]");
        assertEquals(iterable.makeString("[", "/", "]"), stringBuilder3.toString());

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterable2 = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL ->
                {
                    assertEquals("4, 4, 4, 4, 3, 3, 3, 2, 2, 1", iterable2.makeString());
                    assertEquals("4/4/4/4/3/3/3/2/2/1", iterable2.makeString("/"));
                    assertEquals("[4/4/4/4/3/3/3/2/2/1]", iterable2.makeString("[", "/", "]"));
                }
                case SORTED_NATURAL ->
                {
                    assertEquals("1, 2, 2, 3, 3, 3, 4, 4, 4, 4", iterable2.makeString());
                    assertEquals("1/2/2/3/3/3/4/4/4/4", iterable2.makeString("/"));
                    assertEquals("[1/2/2/3/3/3/4/4/4/4]", iterable2.makeString("[", "/", "]"));
                }
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        assertEquals(
                iterable2.makeString(),
                iterable2.reduceInPlace(Collectors2.makeString()));

        assertEquals(
                iterable2.makeString("/"),
                iterable2.reduceInPlace(Collectors2.makeString("/")));

        assertEquals(
                iterable2.makeString("[", "/", "]"),
                iterable2.reduceInPlace(Collectors2.makeString("[", "/", "]")));

        StringBuilder builder1 = new StringBuilder();
        iterable2.appendString(builder1);
        assertEquals(iterable2.makeString(), builder1.toString());

        StringBuilder builder2 = new StringBuilder();
        iterable2.appendString(builder2, "/");
        assertEquals(iterable2.makeString("/"), builder2.toString());

        StringBuilder builder3 = new StringBuilder();
        iterable2.appendString(builder3, "[", "/", "]");
        assertEquals(iterable2.makeString("[", "/", "]"), builder3.toString());
    }

    @Test
    default void RichIterable_fused_collectMakeString()
    {
        RichIterable<Integer> iterable = this.newWith(0, 1, 8);

        assertEquals(
                iterable.asLazy().collect(Integer::toUnsignedString).makeString("[", ",", "]"),
                iterable.makeString(Integer::toUnsignedString, "[", ",", "]"));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(0, 0, 1, 1, 8, 8);

        assertEquals(
                iterableDup.asLazy().collect(Integer::toUnsignedString).makeString("[", ",", "]"),
                iterableDup.makeString(Integer::toUnsignedString, "[", ",", "]"));
    }

    @Override
    @Test
    default void Iterable_toString()
    {
        RichIterable<Integer> iterable = this.newWith(3, 2, 1);
        assertThat(iterable.toString(), isOneOf(
                "[3, 2, 1]", "[3, 1, 2]", "[2, 3, 1]", "[2, 1, 3]", "[1, 3, 2]", "[1, 2, 3]"));
        assertThat(iterable.asLazy().toString(), isOneOf(
                "[3, 2, 1]", "[3, 1, 2]", "[2, 3, 1]", "[2, 1, 3]", "[1, 3, 2]", "[1, 2, 3]"));
        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL ->
                {
                    assertEquals("[3, 2, 1]", iterable.toString());
                    assertEquals("[3, 2, 1]", iterable.asLazy().toString());
                }
                case SORTED_NATURAL ->
                {
                    assertEquals("[1, 2, 3]", iterable.toString());
                    assertEquals("[1, 2, 3]", iterable.asLazy().toString());
                }
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterable2 = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);
        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL ->
                {
                    assertEquals("[4, 4, 4, 4, 3, 3, 3, 2, 2, 1]", iterable2.toString());
                    assertEquals("[4, 4, 4, 4, 3, 3, 3, 2, 2, 1]", iterable2.asLazy().toString());
                }
                case SORTED_NATURAL ->
                {
                    assertEquals("[1, 2, 2, 3, 3, 3, 4, 4, 4, 4]", iterable2.toString());
                    assertEquals("[1, 2, 2, 3, 3, 3, 4, 4, 4, 4]", iterable2.asLazy().toString());
                }
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }
    }
}
