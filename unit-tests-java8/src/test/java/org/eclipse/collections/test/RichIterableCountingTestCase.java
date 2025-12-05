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
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface RichIterableCountingTestCase extends InternalIterableTestCase
{
    @Test
    default void RichIterable_size_empty()
    {
        assertEquals(0, this.newWith().size());
    }

    @Test
    default void RichIterable_size()
    {
        assertEquals(3, this.newWith(3, 2, 1).size());

        if (!this.allowsDuplicates())
        {
            return;
        }

        assertEquals(6, this.newWith(3, 3, 3, 2, 2, 1).size());
    }

    @Test
    default void RichIterable_count()
    {
        RichIterable<Integer> iterable = this.newWith(3, 2, 1);

        assertEquals(1, iterable.count(Integer.valueOf(3)::equals));
        assertEquals(1, iterable.count(Integer.valueOf(2)::equals));
        assertEquals(1, iterable.count(Integer.valueOf(1)::equals));
        assertEquals(0, iterable.count(Integer.valueOf(0)::equals));
        assertEquals(2, iterable.count(i -> i % 2 != 0));
        assertEquals(3, iterable.count(i -> i > 0));

        assertEquals(1, iterable.countWith(Object::equals, 3));
        assertEquals(1, iterable.countWith(Object::equals, 2));
        assertEquals(1, iterable.countWith(Object::equals, 1));
        assertEquals(0, iterable.countWith(Object::equals, 0));
        assertEquals(3, iterable.countWith(Predicates2.greaterThan(), 0));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterable2 = this.newWith(3, 3, 3, 2, 2, 1);

        assertEquals(3, iterable2.count(Integer.valueOf(3)::equals));
        assertEquals(2, iterable2.count(Integer.valueOf(2)::equals));
        assertEquals(1, iterable2.count(Integer.valueOf(1)::equals));
        assertEquals(0, iterable2.count(Integer.valueOf(0)::equals));
        assertEquals(4, iterable2.count(i -> i % 2 != 0));
        assertEquals(6, iterable2.count(i -> i > 0));

        assertEquals(3, iterable2.countWith(Object::equals, 3));
        assertEquals(2, iterable2.countWith(Object::equals, 2));
        assertEquals(1, iterable2.countWith(Object::equals, 1));
        assertEquals(0, iterable2.countWith(Object::equals, 0));
        assertEquals(6, iterable2.countWith(Predicates2.greaterThan(), 0));
    }

    @Test
    default void RichIterable_countBy()
    {
        RichIterable<Integer> integers = this.newWith(4, 3, 2, 1);
        Bag<Integer> evensAndOdds = integers.countBy(each -> Integer.valueOf(each % 2));
        assertEquals(2, evensAndOdds.occurrencesOf(1));
        assertEquals(2, evensAndOdds.occurrencesOf(0));
        Bag<Integer> evensAndOdds2 = integers.countBy(each -> Integer.valueOf(each % 2), Bags.mutable.empty());
        assertEquals(2, evensAndOdds2.occurrencesOf(1));
        assertEquals(2, evensAndOdds2.occurrencesOf(0));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> integersDup = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);
        Bag<Integer> evensAndOddsDup = integersDup.countBy(each -> Integer.valueOf(each % 2));
        assertEquals(4, evensAndOddsDup.occurrencesOf(1));
        assertEquals(6, evensAndOddsDup.occurrencesOf(0));
        Bag<Integer> evensAndOdds2Dup = integersDup.countBy(each -> Integer.valueOf(each % 2), Bags.mutable.empty());
        assertEquals(4, evensAndOdds2Dup.occurrencesOf(1));
        assertEquals(6, evensAndOdds2Dup.occurrencesOf(0));
    }

    /**
     * @since 9.0
     */
    @Test
    default void RichIterable_countByWith()
    {
        RichIterable<Integer> integers = this.newWith(4, 3, 2, 1);
        Bag<Integer> evensAndOdds = integers.countByWith((each, parm) -> Integer.valueOf(each % parm), 2);
        assertEquals(2, evensAndOdds.occurrencesOf(1));
        assertEquals(2, evensAndOdds.occurrencesOf(0));
        Bag<Integer> evensAndOdds2 = integers.countByWith((each, parm) -> Integer.valueOf(each % parm), 2, Bags.mutable.empty());
        assertEquals(2, evensAndOdds2.occurrencesOf(1));
        assertEquals(2, evensAndOdds2.occurrencesOf(0));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> integersDup = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);
        Bag<Integer> evensAndOddsDup = integersDup.countByWith((each, parm) -> Integer.valueOf(each % parm), 2);
        assertEquals(4, evensAndOddsDup.occurrencesOf(1));
        assertEquals(6, evensAndOddsDup.occurrencesOf(0));
        Bag<Integer> evensAndOdds2Dup = integersDup.countByWith((each, parm) -> Integer.valueOf(each % parm), 2, Bags.mutable.empty());
        assertEquals(4, evensAndOdds2Dup.occurrencesOf(1));
        assertEquals(6, evensAndOdds2Dup.occurrencesOf(0));
    }

    /**
     * @since 10.0.0
     */
    @Test
    default void RichIterable_countByEach()
    {
        RichIterable<Integer> integers = this.newWith(4, 3, 2, 1);
        Bag<Integer> integerBag1 = integers.countByEach(each -> IntInterval.oneTo(5).collect(i -> each * i));
        assertEquals(1, integerBag1.occurrencesOf(1));
        assertEquals(2, integerBag1.occurrencesOf(2));
        assertEquals(2, integerBag1.occurrencesOf(3));
        assertEquals(3, integerBag1.occurrencesOf(4));
        assertEquals(1, integerBag1.occurrencesOf(5));
        assertEquals(2, integerBag1.occurrencesOf(6));
        assertEquals(2, integerBag1.occurrencesOf(8));
        assertEquals(1, integerBag1.occurrencesOf(9));
        assertEquals(1, integerBag1.occurrencesOf(10));
        assertEquals(2, integerBag1.occurrencesOf(12));
        assertEquals(1, integerBag1.occurrencesOf(15));
        assertEquals(1, integerBag1.occurrencesOf(16));
        assertEquals(1, integerBag1.occurrencesOf(20));
        Bag<Integer> integerBag2 = integers.countByEach(each -> IntInterval.oneTo(5).collect(i -> each * i), Bags.mutable.empty());
        assertEquals(1, integerBag2.occurrencesOf(1));
        assertEquals(2, integerBag2.occurrencesOf(2));
        assertEquals(2, integerBag2.occurrencesOf(3));
        assertEquals(3, integerBag2.occurrencesOf(4));
        assertEquals(1, integerBag2.occurrencesOf(5));
        assertEquals(2, integerBag2.occurrencesOf(6));
        assertEquals(2, integerBag2.occurrencesOf(8));
        assertEquals(1, integerBag2.occurrencesOf(9));
        assertEquals(1, integerBag2.occurrencesOf(10));
        assertEquals(2, integerBag2.occurrencesOf(12));
        assertEquals(1, integerBag2.occurrencesOf(15));
        assertEquals(1, integerBag2.occurrencesOf(16));
        assertEquals(1, integerBag2.occurrencesOf(20));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> integersDup = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);
        Bag<Integer> integerBag1Dup = integersDup.countByEach(each -> IntInterval.oneTo(5).collect(i -> each * i));
        assertEquals(1, integerBag1Dup.occurrencesOf(1));
        assertEquals(3, integerBag1Dup.occurrencesOf(2));
        assertEquals(4, integerBag1Dup.occurrencesOf(3));
        assertEquals(7, integerBag1Dup.occurrencesOf(4));
        assertEquals(1, integerBag1Dup.occurrencesOf(5));
        assertEquals(5, integerBag1Dup.occurrencesOf(6));
        assertEquals(6, integerBag1Dup.occurrencesOf(8));
        assertEquals(3, integerBag1Dup.occurrencesOf(9));
        assertEquals(2, integerBag1Dup.occurrencesOf(10));
        assertEquals(7, integerBag1Dup.occurrencesOf(12));
        assertEquals(3, integerBag1Dup.occurrencesOf(15));
        assertEquals(4, integerBag1Dup.occurrencesOf(16));
        assertEquals(4, integerBag1Dup.occurrencesOf(20));
        Bag<Integer> integerBag2Dup = integersDup.countByEach(each -> IntInterval.oneTo(5).collect(i -> each * i), Bags.mutable.empty());
        assertEquals(1, integerBag2Dup.occurrencesOf(1));
        assertEquals(3, integerBag2Dup.occurrencesOf(2));
        assertEquals(4, integerBag2Dup.occurrencesOf(3));
        assertEquals(7, integerBag2Dup.occurrencesOf(4));
        assertEquals(1, integerBag2Dup.occurrencesOf(5));
        assertEquals(5, integerBag2Dup.occurrencesOf(6));
        assertEquals(6, integerBag2Dup.occurrencesOf(8));
        assertEquals(3, integerBag2Dup.occurrencesOf(9));
        assertEquals(2, integerBag2Dup.occurrencesOf(10));
        assertEquals(7, integerBag2Dup.occurrencesOf(12));
        assertEquals(3, integerBag2Dup.occurrencesOf(15));
        assertEquals(4, integerBag2Dup.occurrencesOf(16));
        assertEquals(4, integerBag2Dup.occurrencesOf(20));
    }
}
