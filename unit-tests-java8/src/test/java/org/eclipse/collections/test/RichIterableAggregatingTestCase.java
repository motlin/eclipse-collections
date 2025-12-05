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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.factory.primitive.ObjectDoubleMaps;
import org.eclipse.collections.impl.factory.primitive.ObjectLongMaps;
import org.eclipse.collections.impl.list.Interval;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public interface RichIterableAggregatingTestCase extends InternalIterableTestCase
{
    @Test
    default void RichIterable_aggregateBy_aggregateInPlaceBy_reduceBy()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        MapIterable<String, Integer> aggregateBy = iterable.aggregateBy(
                Object::toString,
                () -> 0,
                (integer1, integer2) -> integer1 + integer2);

        assertEquals(4, aggregateBy.get("4").intValue());
        assertEquals(3, aggregateBy.get("3").intValue());
        assertEquals(2, aggregateBy.get("2").intValue());
        assertEquals(1, aggregateBy.get("1").intValue());

        MapIterable<String, AtomicInteger> aggregateInPlaceBy = iterable.aggregateInPlaceBy(
                String::valueOf,
                AtomicInteger::new,
                AtomicInteger::addAndGet);
        assertEquals(4, aggregateInPlaceBy.get("4").intValue());
        assertEquals(3, aggregateInPlaceBy.get("3").intValue());
        assertEquals(2, aggregateInPlaceBy.get("2").intValue());
        assertEquals(1, aggregateInPlaceBy.get("1").intValue());

        MapIterable<String, Integer> reduceBy = iterable.reduceBy(
                Object::toString,
                (integer1, integer2) -> integer1 + integer2);

        assertEquals(4, reduceBy.get("4").intValue());
        assertEquals(3, reduceBy.get("3").intValue());
        assertEquals(2, reduceBy.get("2").intValue());
        assertEquals(1, reduceBy.get("1").intValue());

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> duplicateIterable = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        MapIterable<String, Integer> duplicateAggregateBy = duplicateIterable.aggregateBy(
                Object::toString,
                () -> 0,
                (integer1, integer2) -> integer1 + integer2);

        assertEquals(16, duplicateAggregateBy.get("4").intValue());
        assertEquals(9, duplicateAggregateBy.get("3").intValue());
        assertEquals(4, duplicateAggregateBy.get("2").intValue());
        assertEquals(1, duplicateAggregateBy.get("1").intValue());

        MapIterable<String, AtomicInteger> duplicateAggregateInPlaceBy = duplicateIterable.aggregateInPlaceBy(
                String::valueOf,
                AtomicInteger::new,
                AtomicInteger::addAndGet);
        assertEquals(16, duplicateAggregateInPlaceBy.get("4").intValue());
        assertEquals(9, duplicateAggregateInPlaceBy.get("3").intValue());
        assertEquals(4, duplicateAggregateInPlaceBy.get("2").intValue());
        assertEquals(1, duplicateAggregateInPlaceBy.get("1").intValue());

        MapIterable<String, Integer> duplicateReduceBy = duplicateIterable.reduceBy(
                Object::toString,
                (integer1, integer2) -> integer1 + integer2);

        assertEquals(16, duplicateReduceBy.get("4").intValue());
        assertEquals(9, duplicateReduceBy.get("3").intValue());
        assertEquals(4, duplicateReduceBy.get("2").intValue());
        assertEquals(1, duplicateReduceBy.get("1").intValue());
    }

    @Test
    default void RichIterable_sumOfPrimitive()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        assertEquals(10.0f, iterable.sumOfFloat(Integer::floatValue), 0.001);
        assertEquals(10.0, iterable.sumOfDouble(Integer::doubleValue), 0.001);
        assertEquals(10, iterable.sumOfInt(integer -> integer));
        assertEquals(10L, iterable.sumOfLong(Integer::longValue));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterable2 = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        assertEquals(30.0f, iterable2.sumOfFloat(Integer::floatValue), 0.001);
        assertEquals(30.0, iterable2.sumOfDouble(Integer::doubleValue), 0.001);
        assertEquals(30, iterable2.sumOfInt(Integer::intValue));
        assertEquals(30L, iterable2.sumOfLong(Integer::longValue));
    }

    @Test
    default void RichIterable_sumByPrimitive()
    {
        RichIterable<String> iterable = this.newWith("4", "3", "2", "1");

        assertIterablesEqual(
                ObjectLongMaps.immutable.with(0, 6L).newWithKeyValue(1, 4L),
                iterable.sumByInt(s -> Integer.parseInt(s) % 2, Integer::parseInt));

        assertIterablesEqual(
                ObjectLongMaps.immutable.with(0, 6L).newWithKeyValue(1, 4L),
                iterable.sumByLong(s -> Integer.parseInt(s) % 2, Long::parseLong));

        assertIterablesEqual(
                ObjectDoubleMaps.immutable.with(0, 6.0d).newWithKeyValue(1, 4.0d),
                iterable.sumByDouble(s -> Integer.parseInt(s) % 2, Double::parseDouble));

        assertIterablesEqual(
                ObjectDoubleMaps.immutable.with(0, 6.0d).newWithKeyValue(1, 4.0d),
                iterable.sumByFloat(s -> Integer.parseInt(s) % 2, Float::parseFloat));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<String> iterable2 = this.newWith("4", "4", "4", "4", "3", "3", "3", "2", "2", "1");

        assertIterablesEqual(
                ObjectLongMaps.immutable.with(0, 20L).newWithKeyValue(1, 10L),
                iterable2.sumByInt(s -> Integer.parseInt(s) % 2, Integer::parseInt));

        assertIterablesEqual(
                ObjectLongMaps.immutable.with(0, 20L).newWithKeyValue(1, 10L),
                iterable2.sumByLong(s -> Integer.parseInt(s) % 2, Long::parseLong));

        assertIterablesEqual(
                ObjectDoubleMaps.immutable.with(0, 20.0d).newWithKeyValue(1, 10.0d),
                iterable2.sumByDouble(s -> Integer.parseInt(s) % 2, Double::parseDouble));

        assertIterablesEqual(
                ObjectDoubleMaps.immutable.with(0, 20.0d).newWithKeyValue(1, 10.0d),
                iterable2.sumByFloat(s -> Integer.parseInt(s) % 2, Float::parseFloat));
    }

    @Test
    default void RichIterable_summarizePrimitive()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        assertEquals(10.0f, iterable.summarizeFloat(Integer::floatValue).getSum(), 0.001);
        assertEquals(10.0, iterable.summarizeDouble(Integer::doubleValue).getSum(), 0.001);
        assertEquals(10, iterable.summarizeInt(Integer::intValue).getSum());
        assertEquals(10L, iterable.summarizeLong(Integer::longValue).getSum());

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> bigIterable = this.newWith(5, 5, 5, 5, 5, 4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        assertEquals(55.0f, bigIterable.summarizeFloat(Integer::floatValue).getSum(), 0.001);
        assertEquals(55.0, bigIterable.summarizeDouble(Integer::doubleValue).getSum(), 0.001);
        assertEquals(55, bigIterable.summarizeInt(Integer::intValue).getSum());
        assertEquals(55L, bigIterable.summarizeLong(Integer::longValue).getSum());

        RichIterable<Integer> littleIterable = this.newWith(5, 4, 3, 2, 1);

        assertEquals(15.0f, littleIterable.summarizeFloat(Integer::floatValue).getSum(), 0.001);
        assertEquals(15.0, littleIterable.summarizeDouble(Integer::doubleValue).getSum(), 0.001);
        assertEquals(15, littleIterable.summarizeInt(Integer::intValue).getSum());
        assertEquals(15L, littleIterable.summarizeLong(Integer::longValue).getSum());
    }

    @Test
    default void RichIterable_reduceInPlaceCollector()
    {
        RichIterable<Integer> iterable = this.newWith(1, 2, 3);
        MutableBag<Integer> result = iterable.reduceInPlace(Collectors.toCollection(Bags.mutable::empty));
        assertEquals(Bags.immutable.with(1, 2, 3), result);

        String joining = result.collect(Object::toString).reduceInPlace(Collectors.joining(","));
        assertEquals(result.collect(Object::toString).makeString(","), joining);

        String joining2 = result.toImmutable().collect(Object::toString).reduceInPlace(Collectors.joining(","));
        assertEquals(result.toImmutable().collect(Object::toString).makeString(","), joining2);

        String joining3 = result.asLazy().collect(Object::toString).reduceInPlace(Collectors.joining(","));
        assertEquals(result.asLazy().collect(Object::toString).makeString(","), joining3);

        Map<Boolean, List<Integer>> expected =
                iterable.toList().stream().collect(Collectors.partitioningBy(each -> each % 2 == 0));
        Map<Boolean, List<Integer>> actual =
                iterable.reduceInPlace(Collectors.partitioningBy(each -> each % 2 == 0));
        assertEquals(expected, actual);

        Map<String, List<Integer>> groupByJDK =
                iterable.toList().stream().collect(Collectors.groupingBy(Object::toString));
        Map<String, List<Integer>> groupByEC =
                result.reduceInPlace(Collectors.groupingBy(Object::toString));
        assertEquals(groupByJDK, groupByEC);

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> littleIterable = this.newWith(1, 2, 3, 1, 2, 3);
        MutableBag<Integer> duplicateResult =
                littleIterable.reduceInPlace(Collectors.toCollection(Bags.mutable::empty));
        assertEquals(Bags.immutable.with(1, 1, 2, 2, 3, 3), duplicateResult);

        RichIterable<Integer> bigIterable = this.newWith(Interval.oneTo(20).toArray());
        MutableBag<Integer> bigResult =
                bigIterable.reduceInPlace(Collectors.toCollection(Bags.mutable::empty));
        assertEquals(Interval.oneTo(20).toBag(), bigResult);

        String duplicateJoining =
                duplicateResult.collect(Object::toString).reduceInPlace(Collectors.joining(","));
        assertEquals(duplicateResult.collect(Object::toString).makeString(","), duplicateJoining);

        ImmutableBag<Integer> immutableBag = duplicateResult.toImmutable();
        String duplicateJoining2 =
                immutableBag.collect(Object::toString).reduceInPlace(Collectors.joining(","));
        assertEquals(immutableBag.collect(Object::toString).makeString(","), duplicateJoining2);

        String duplicateJoining3 =
                duplicateResult.asLazy().collect(Object::toString).reduceInPlace(Collectors.joining(","));
        assertEquals(duplicateResult.asLazy().collect(Object::toString).makeString(","), duplicateJoining3);

        Map<Boolean, List<Integer>> duplicateExpected =
                littleIterable.toList().stream().collect(Collectors.partitioningBy(each -> each % 2 == 0));
        Map<Boolean, List<Integer>> duplicateActual =
                littleIterable.reduceInPlace(Collectors.partitioningBy(each -> each % 2 == 0));
        assertEquals(duplicateExpected, duplicateActual);

        Map<String, List<Integer>> duplicateGroupByJDK =
                littleIterable.toList().stream().collect(Collectors.groupingBy(Object::toString));
        Map<String, List<Integer>> duplicateGroupByEC =
                duplicateResult.reduceInPlace(Collectors.groupingBy(Object::toString));
        assertEquals(duplicateGroupByJDK, duplicateGroupByEC);
    }

    @Test
    default void RichIterable_reduceInPlace()
    {
        RichIterable<Integer> iterable = this.newWith(1, 2, 3);
        MutableBag<Integer> result =
                iterable.reduceInPlace(Bags.mutable::empty, MutableBag::add);
        assertEquals(Bags.immutable.with(1, 2, 3), result);

        String joining =
                result.collect(Object::toString).reduceInPlace(StringBuilder::new, StringBuilder::append).toString();
        assertEquals(result.collect(Object::toString).makeString(""), joining);

        ImmutableBag<Integer> immutableBag = result.toImmutable();
        String joining2 =
                immutableBag.collect(Object::toString).reduceInPlace(StringBuilder::new, StringBuilder::append).toString();
        assertEquals(immutableBag.collect(Object::toString).makeString(""), joining2);

        String joining3 =
                result.asLazy().collect(Object::toString).reduceInPlace(StringBuilder::new, StringBuilder::append).toString();
        assertEquals(result.asLazy().collect(Object::toString).makeString(""), joining3);

        int atomicAdd = iterable.reduceInPlace(AtomicInteger::new, AtomicInteger::addAndGet).get();
        assertEquals(6, atomicAdd);

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> littleIterable = this.newWith(1, 2, 3, 1, 2, 3);
        MutableBag<Integer> resultDup =
                littleIterable.reduceInPlace(Bags.mutable::empty, MutableBag::add);
        assertEquals(Bags.immutable.with(1, 1, 2, 2, 3, 3), resultDup);

        RichIterable<Integer> bigIterable = this.newWith(Interval.oneTo(20).toArray());
        MutableBag<Integer> bigResult =
                bigIterable.reduceInPlace(Bags.mutable::empty, MutableBag::add);
        assertEquals(Interval.oneTo(20).toBag(), bigResult);

        String joiningDup =
                resultDup.collect(Object::toString).reduceInPlace(StringBuilder::new, StringBuilder::append).toString();
        assertEquals(resultDup.collect(Object::toString).makeString(""), joiningDup);

        ImmutableBag<Integer> immutableBagDup = resultDup.toImmutable();
        String joining2Dup =
                immutableBagDup.collect(Object::toString).reduceInPlace(StringBuilder::new, StringBuilder::append).toString();
        assertEquals(immutableBagDup.collect(Object::toString).makeString(""), joining2Dup);

        String joining3Dup =
                resultDup.asLazy().collect(Object::toString).reduceInPlace(StringBuilder::new, StringBuilder::append).toString();
        assertEquals(resultDup.asLazy().collect(Object::toString).makeString(""), joining3Dup);

        int atomicAddDup = littleIterable.reduceInPlace(AtomicInteger::new, AtomicInteger::addAndGet).get();
        assertEquals(12, atomicAddDup);
    }

    @Test
    default void RichIterable_reduceOptional()
    {
        RichIterable<Integer> iterable = this.newWith(1, 2, 3);
        Optional<Integer> result =
                iterable.reduce(Integer::sum);
        assertEquals(6, result.get().intValue());

        Optional<Integer> max =
                iterable.reduce(Integer::max);
        assertEquals(3, max.get().intValue());

        Optional<Integer> min =
                iterable.reduce(Integer::min);
        assertEquals(1, min.get().intValue());

        RichIterable<Integer> iterableEmpty = this.newWith();
        Optional<Integer> resultEmpty =
                iterableEmpty.reduce(Integer::sum);
        assertFalse(resultEmpty.isPresent());

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> littleIterable = this.newWith(1, 2, 3, 1, 2, 3);
        Optional<Integer> resultDup =
                littleIterable.reduce(Integer::sum);
        assertEquals(12, resultDup.get().intValue());

        RichIterable<Integer> bigIterable = this.newWith(Interval.oneTo(20).toArray());
        Optional<Integer> bigResult =
                bigIterable.reduce(Integer::max);
        assertEquals(20, bigResult.get().intValue());

        Optional<Integer> maxDup =
                littleIterable.reduce(Integer::max);
        assertEquals(3, maxDup.get().intValue());

        Optional<Integer> minDup =
                littleIterable.reduce(Integer::min);
        assertEquals(1, minDup.get().intValue());
    }

    @Test
    default void RichIterable_injectInto()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        assertEquals(Integer.valueOf(11), iterable.injectInto(1, new Function2<Integer, Integer, Integer>()
        {
            private static final long serialVersionUID = 1L;

            public Integer value(Integer argument1, Integer argument2)
            {
                return argument1 + argument2;
            }
        }));
        assertEquals(Integer.valueOf(10), iterable.injectInto(0, new Function2<Integer, Integer, Integer>()
        {
            private static final long serialVersionUID = 1L;

            public Integer value(Integer argument1, Integer argument2)
            {
                return argument1 + argument2;
            }
        }));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        assertEquals(Integer.valueOf(31), iterableDup.injectInto(1, AddFunction.INTEGER));
        assertEquals(Integer.valueOf(30), iterableDup.injectInto(0, AddFunction.INTEGER));
    }

    @Test
    default void RichIterable_injectInto_primitive()
    {
        RichIterable<Integer> iterable = this.newWith(4, 3, 2, 1);

        assertEquals(11, iterable.injectInto(1, AddFunction.INTEGER_TO_INT));
        assertEquals(10, iterable.injectInto(0, AddFunction.INTEGER_TO_INT));

        assertEquals(11L, iterable.injectInto(1, AddFunction.INTEGER_TO_LONG));
        assertEquals(10L, iterable.injectInto(0, AddFunction.INTEGER_TO_LONG));

        assertEquals(11.0d, iterable.injectInto(1, AddFunction.INTEGER_TO_DOUBLE), 0.001);
        assertEquals(10.0d, iterable.injectInto(0, AddFunction.INTEGER_TO_DOUBLE), 0.001);

        assertEquals(11.0f, iterable.injectInto(1, AddFunction.INTEGER_TO_FLOAT), 0.001f);
        assertEquals(10.0f, iterable.injectInto(0, AddFunction.INTEGER_TO_FLOAT), 0.001f);

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1);

        assertEquals(31, iterableDup.injectIntoInt(1, AddFunction.INTEGER_TO_INT));
        assertEquals(30, iterableDup.injectIntoInt(0, AddFunction.INTEGER_TO_INT));

        assertEquals(31L, iterableDup.injectIntoLong(1, AddFunction.INTEGER_TO_LONG));
        assertEquals(30L, iterableDup.injectIntoLong(0, AddFunction.INTEGER_TO_LONG));

        assertEquals(31.0d, iterableDup.injectIntoDouble(1, AddFunction.INTEGER_TO_DOUBLE), 0.001);
        assertEquals(30.0d, iterableDup.injectIntoDouble(0, AddFunction.INTEGER_TO_DOUBLE), 0.001);

        assertEquals(31.0f, iterableDup.injectIntoFloat(1, AddFunction.INTEGER_TO_FLOAT), 0.001f);
        assertEquals(30.0f, iterableDup.injectIntoFloat(0, AddFunction.INTEGER_TO_FLOAT), 0.001f);
    }
}
