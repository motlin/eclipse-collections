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

import java.util.Iterator;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.primitive.DoubleObjectToDoubleFunction;
import org.eclipse.collections.api.block.function.primitive.FloatObjectToFloatFunction;
import org.eclipse.collections.api.block.function.primitive.IntObjectToIntFunction;
import org.eclipse.collections.api.block.function.primitive.LongObjectToLongFunction;
import org.eclipse.collections.api.block.function.primitive.ShortFunction;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.impl.Counter;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.ByteHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.CharHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.DoubleHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.FloatHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.IntHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.LongHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.ShortHashBag;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.multimap.bag.HashBagMultimap;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("UnnecessaryCodeBlock")
public interface RichIterableTestCase
        extends RichIterableIteratingTestCase,
        RichIterableCountingTestCase,
        RichIterableTestingTestCase,
        RichIterableFindingTestCase,
        RichIterableFilteringTestCase,
        RichIterableTransformingTestCase,
        RichIterableGroupingTestCase,
        RichIterableAggregatingTestCase,
        RichIterableConvertingTestCase
{
    @Test
    default void newMutable_sanity()
    {
        assertIterablesEqual(this.getExpectedFiltered(3, 2, 1), this.newMutableForFilter(3, 2, 1));
    }

    @Test
    default void RichIterable_iterator_iterationOrder()
    {
        MutableCollection<Integer> iterationOrder = this.newMutableForFilter();
        Iterator<Integer> iterator = this.getInstanceUnderTest().iterator();
        while (iterator.hasNext())
        {
            iterationOrder.add(iterator.next());
        }
        assertIterablesEqual(this.expectedIterationOrder(), iterationOrder);

        MutableCollection<Integer> expectedIterationOrder = this.expectedIterationOrder();
        MutableCollection<Integer> forEachWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().forEachWith((each, param) -> forEachWithIterationOrder.add(each), null);
        assertIterablesEqual(expectedIterationOrder, forEachWithIterationOrder);

        MutableCollection<Integer> forEachWithIndexIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().forEachWithIndex((each, index) -> forEachWithIndexIterationOrder.add(each));
        assertIterablesEqual(expectedIterationOrder, forEachWithIndexIterationOrder);
    }

    @Test
    default void RichIterable_iterationOrder()
    {
        MutableCollection<Integer> expectedIterationOrder = this.expectedIterationOrder();

        Procedure<Object> noop = Procedures.noop();

        MutableCollection<Integer> selectIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().select(selectIterationOrder::add).forEach(noop);
        assertIterablesEqual(expectedIterationOrder, selectIterationOrder);

        MutableCollection<Integer> selectTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().select(selectTargetIterationOrder::add, new HashBag<>());
        assertIterablesEqual(expectedIterationOrder, selectTargetIterationOrder);

        MutableCollection<Integer> selectWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().selectWith((each, param) -> selectWithIterationOrder.add(each), null).forEach(noop);
        assertIterablesEqual(expectedIterationOrder, selectWithIterationOrder);

        MutableCollection<Integer> selectWithTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().selectWith((each, param) -> selectWithTargetIterationOrder.add(each), null, new HashBag<>());
        assertIterablesEqual(expectedIterationOrder, selectWithTargetIterationOrder);

        MutableCollection<Integer> rejectIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().reject(rejectIterationOrder::add).forEach(noop);
        assertIterablesEqual(expectedIterationOrder, rejectIterationOrder);

        MutableCollection<Integer> rejectTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().reject(rejectTargetIterationOrder::add, new HashBag<>());
        assertIterablesEqual(expectedIterationOrder, rejectTargetIterationOrder);

        MutableCollection<Integer> rejectWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().rejectWith((each, param) -> rejectWithIterationOrder.add(each), null).forEach(noop);
        assertIterablesEqual(expectedIterationOrder, rejectWithIterationOrder);

        MutableCollection<Integer> rejectWithTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().rejectWith((each, param) -> rejectWithTargetIterationOrder.add(each), null, new HashBag<>());
        assertIterablesEqual(expectedIterationOrder, rejectWithTargetIterationOrder);

        MutableCollection<Integer> partitionIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().partition(partitionIterationOrder::add);
        assertIterablesEqual(expectedIterationOrder, partitionIterationOrder);

        MutableCollection<Integer> partitionWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().partitionWith((each, param) -> partitionWithIterationOrder.add(each), null);
        assertIterablesEqual(expectedIterationOrder, partitionWithIterationOrder);

        MutableCollection<Integer> collectIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collect(collectIterationOrder::add).forEach(noop);
        assertIterablesEqual(expectedIterationOrder, collectIterationOrder);

        MutableCollection<Integer> collectTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collect(collectTargetIterationOrder::add, new HashBag<>());
        assertIterablesEqual(expectedIterationOrder, collectTargetIterationOrder);

        MutableCollection<Integer> collectWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectWith((each, param) -> collectWithIterationOrder.add(each), null).forEach(noop);
        assertIterablesEqual(expectedIterationOrder, collectWithIterationOrder);

        MutableCollection<Integer> collectWithTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectWith((each, param) -> collectWithTargetIterationOrder.add(each), null, new HashBag<>());
        assertIterablesEqual(expectedIterationOrder, collectWithTargetIterationOrder);

        MutableCollection<Integer> collectIfPredicateIterationOrder = this.newMutableForFilter();
        MutableCollection<Integer> collectIfFunctionIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectIf(collectIfPredicateIterationOrder::add, collectIfFunctionIterationOrder::add).forEach(noop);
        assertIterablesEqual(expectedIterationOrder, collectIfPredicateIterationOrder);
        assertIterablesEqual(expectedIterationOrder, collectIfFunctionIterationOrder);

        MutableCollection<Integer> collectIfPredicateTargetIterationOrder = this.newMutableForFilter();
        MutableCollection<Integer> collectIfFunctionTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectIf(collectIfPredicateTargetIterationOrder::add, collectIfFunctionTargetIterationOrder::add, new HashBag<>());
        assertIterablesEqual(expectedIterationOrder, collectIfPredicateTargetIterationOrder);
        assertIterablesEqual(expectedIterationOrder, collectIfFunctionTargetIterationOrder);

        MutableCollection<Integer> collectBooleanIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectBoolean(collectBooleanIterationOrder::add).forEach(each -> {
        });
        assertIterablesEqual(expectedIterationOrder, collectBooleanIterationOrder);

        MutableCollection<Integer> collectBooleanTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectBoolean(collectBooleanTargetIterationOrder::add, new BooleanHashBag());
        assertIterablesEqual(expectedIterationOrder, collectBooleanTargetIterationOrder);

        MutableCollection<Integer> collectByteIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectByte((Integer each) ->
        {
            collectByteIterationOrder.add(each);
            return (byte) 0;
        }).forEach(each -> {
        });
        assertIterablesEqual(expectedIterationOrder, collectByteIterationOrder);

        MutableCollection<Integer> collectByteTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectByte((Integer each) ->
        {
            collectByteTargetIterationOrder.add(each);
            return (byte) 0;
        }, new ByteHashBag());
        assertIterablesEqual(expectedIterationOrder, collectByteTargetIterationOrder);

        MutableCollection<Integer> collectCharIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectChar((Integer each) ->
        {
            collectCharIterationOrder.add(each);
            return ' ';
        }).forEach(each -> {
        });
        assertIterablesEqual(expectedIterationOrder, collectCharIterationOrder);

        MutableCollection<Integer> collectCharTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectChar((Integer each) ->
        {
            collectCharTargetIterationOrder.add(each);
            return ' ';
        }, new CharHashBag());
        assertIterablesEqual(expectedIterationOrder, collectCharTargetIterationOrder);

        MutableCollection<Integer> collectDoubleIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectDouble((Integer each) ->
        {
            collectDoubleIterationOrder.add(each);
            return 0.0;
        }).forEach(each -> {
        });
        assertIterablesEqual(expectedIterationOrder, collectDoubleIterationOrder);

        MutableCollection<Integer> collectDoubleTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectDouble((Integer each) ->
        {
            collectDoubleTargetIterationOrder.add(each);
            return 0.0;
        }, new DoubleHashBag());
        assertIterablesEqual(expectedIterationOrder, collectDoubleTargetIterationOrder);

        MutableCollection<Integer> collectFloatIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectFloat((Integer each) ->
        {
            collectFloatIterationOrder.add(each);
            return 0.0f;
        }).forEach(each -> {
        });
        assertIterablesEqual(expectedIterationOrder, collectFloatIterationOrder);

        MutableCollection<Integer> collectFloatTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectFloat((Integer each) ->
        {
            collectFloatTargetIterationOrder.add(each);
            return 0.0f;
        }, new FloatHashBag());
        assertIterablesEqual(expectedIterationOrder, collectFloatTargetIterationOrder);

        MutableCollection<Integer> collectIntIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectInt((Integer each) ->
        {
            collectIntIterationOrder.add(each);
            return 0;
        }).forEach(each -> {
        });
        assertIterablesEqual(expectedIterationOrder, collectIntIterationOrder);

        MutableCollection<Integer> collectIntTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectInt((Integer each) ->
        {
            collectIntTargetIterationOrder.add(each);
            return 0;
        }, new IntHashBag());
        assertIterablesEqual(expectedIterationOrder, collectIntTargetIterationOrder);

        MutableCollection<Integer> collectLongIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectLong((Integer each) ->
        {
            collectLongIterationOrder.add(each);
            return 0L;
        }).forEach(each -> {
        });
        assertIterablesEqual(expectedIterationOrder, collectLongIterationOrder);

        MutableCollection<Integer> collectLongTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectLong((Integer each) ->
        {
            collectLongTargetIterationOrder.add(each);
            return 0L;
        }, new LongHashBag());
        assertIterablesEqual(expectedIterationOrder, collectLongTargetIterationOrder);

        MutableCollection<Integer> collectShortIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectShort((ShortFunction<Integer>) each -> {
            collectShortIterationOrder.add(each);
            return (short) 0;
        }).forEach(each -> {
        });
        assertIterablesEqual(expectedIterationOrder, collectShortIterationOrder);

        MutableCollection<Integer> collectShortTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().collectShort((ShortFunction<Integer>) each -> {
            collectShortTargetIterationOrder.add(each);
            return (short) 0;
        }, new ShortHashBag());
        assertIterablesEqual(expectedIterationOrder, collectShortTargetIterationOrder);

        MutableCollection<Integer> flatCollectIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().flatCollect(each -> Lists.immutable.with(flatCollectIterationOrder.add(each))).forEach(noop);
        assertIterablesEqual(expectedIterationOrder, flatCollectIterationOrder);

        MutableCollection<Integer> flatCollectTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().flatCollect(each -> Lists.immutable.with(flatCollectTargetIterationOrder.add(each)), new HashBag<>());
        assertIterablesEqual(expectedIterationOrder, flatCollectTargetIterationOrder);

        MutableCollection<Integer> countIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().count(countIterationOrder::add);
        assertIterablesEqual(expectedIterationOrder, countIterationOrder);

        MutableCollection<Integer> countWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().countWith((each, param) -> countWithIterationOrder.add(each), null);
        assertIterablesEqual(expectedIterationOrder, countWithIterationOrder);

        MutableCollection<Integer> anySatisfyIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().anySatisfy(each -> {
            anySatisfyIterationOrder.add(each);
            return false;
        });
        assertIterablesEqual(expectedIterationOrder, anySatisfyIterationOrder);

        MutableCollection<Integer> anySatisfyWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().anySatisfyWith((each, param) -> {
            anySatisfyWithIterationOrder.add(each);
            return false;
        }, null);
        assertIterablesEqual(expectedIterationOrder, anySatisfyWithIterationOrder);

        MutableCollection<Integer> allSatisfyIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().allSatisfy(each -> {
            allSatisfyIterationOrder.add(each);
            return true;
        });
        assertIterablesEqual(expectedIterationOrder, allSatisfyIterationOrder);

        MutableCollection<Integer> allSatisfyWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().allSatisfyWith((each, param) -> {
            allSatisfyWithIterationOrder.add(each);
            return true;
        }, null);
        assertIterablesEqual(expectedIterationOrder, allSatisfyWithIterationOrder);

        MutableCollection<Integer> noneSatisfyIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().noneSatisfy(each -> {
            noneSatisfyIterationOrder.add(each);
            return false;
        });
        assertIterablesEqual(expectedIterationOrder, noneSatisfyIterationOrder);

        MutableCollection<Integer> noneSatisfyWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().noneSatisfyWith((each, param) -> {
            noneSatisfyWithIterationOrder.add(each);
            return false;
        }, null);
        assertIterablesEqual(expectedIterationOrder, noneSatisfyWithIterationOrder);

        MutableCollection<Integer> detectIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().detect(each -> {
            detectIterationOrder.add(each);
            return false;
        });
        assertIterablesEqual(expectedIterationOrder, detectIterationOrder);

        MutableCollection<Integer> detectWithIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().detectWith((each, param) -> {
            detectWithIterationOrder.add(each);
            return false;
        }, null);
        assertIterablesEqual(expectedIterationOrder, detectWithIterationOrder);

        MutableCollection<Integer> detectIfNoneIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().detectIfNone(each -> {
            detectIfNoneIterationOrder.add(each);
            return false;
        }, () -> 0);
        assertIterablesEqual(expectedIterationOrder, detectIfNoneIterationOrder);

        MutableCollection<Integer> detectWithIfNoneIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().detectWithIfNone((each, param) -> {
            detectWithIfNoneIterationOrder.add(each);
            return false;
        }, null, () -> 0);
        assertIterablesEqual(expectedIterationOrder, detectWithIfNoneIterationOrder);

        MutableCollection<Integer> minComparatorIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().min((o1, o2) -> {
            if (minComparatorIterationOrder.isEmpty())
            {
                minComparatorIterationOrder.add(o2);
            }
            minComparatorIterationOrder.add(o1);
            return 0;
        });
        assertIterablesEqual(expectedIterationOrder, minComparatorIterationOrder);

        MutableCollection<Integer> maxComparatorIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().max((o1, o2) -> {
            if (maxComparatorIterationOrder.isEmpty())
            {
                maxComparatorIterationOrder.add(o2);
            }
            maxComparatorIterationOrder.add(o1);
            return 0;
        });
        assertIterablesEqual(expectedIterationOrder, maxComparatorIterationOrder);

        MutableCollection<Integer> minByIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().minBy(minByIterationOrder::add);
        assertIterablesEqual(expectedIterationOrder, minByIterationOrder);

        MutableCollection<Integer> maxByIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().maxBy(maxByIterationOrder::add);
        assertIterablesEqual(expectedIterationOrder, maxByIterationOrder);

        MutableCollection<Integer> groupByIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().groupBy(groupByIterationOrder::add);
        assertIterablesEqual(expectedIterationOrder, groupByIterationOrder);

        MutableCollection<Integer> groupByTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().groupBy(groupByTargetIterationOrder::add, new HashBagMultimap<>());
        assertIterablesEqual(expectedIterationOrder, groupByTargetIterationOrder);

        MutableCollection<Integer> groupByEachIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().groupByEach(each -> {
            groupByEachIterationOrder.add(each);
            return Lists.immutable.with(each);
        });
        assertIterablesEqual(expectedIterationOrder, groupByEachIterationOrder);

        MutableCollection<Integer> groupByEachTargetIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().groupByEach(each -> {
            groupByEachTargetIterationOrder.add(each);
            return Lists.immutable.with(each);
        }, new HashBagMultimap<>());
        assertIterablesEqual(expectedIterationOrder, groupByEachTargetIterationOrder);

        MutableCollection<Integer> sumOfFloatIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().sumOfFloat(each -> {
            sumOfFloatIterationOrder.add(each);
            return each.floatValue();
        });
        assertIterablesEqual(expectedIterationOrder, sumOfFloatIterationOrder);

        MutableCollection<Integer> sumOfDoubleIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().sumOfDouble(each -> {
            sumOfDoubleIterationOrder.add(each);
            return each.doubleValue();
        });
        assertIterablesEqual(expectedIterationOrder, sumOfDoubleIterationOrder);

        MutableCollection<Integer> sumOfIntIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().sumOfInt(each -> {
            sumOfIntIterationOrder.add(each);
            return each.intValue();
        });
        assertIterablesEqual(expectedIterationOrder, sumOfIntIterationOrder);

        MutableCollection<Integer> sumOfLongIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().sumOfLong(each -> {
            sumOfLongIterationOrder.add(each);
            return each.longValue();
        });
        assertIterablesEqual(expectedIterationOrder, sumOfLongIterationOrder);

        MutableCollection<Integer> sumByIntIterationOrder1 = this.newMutableForFilter();
        MutableCollection<Integer> sumByIntIterationOrder2 = this.newMutableForFilter();
        this.getInstanceUnderTest().sumByInt(
                each -> {
                    sumByIntIterationOrder1.add(each);
                    return each;
                },
                each -> {
                    sumByIntIterationOrder2.add(each);
                    return 0;
                });
        assertIterablesEqual(expectedIterationOrder, sumByIntIterationOrder1);
        assertIterablesEqual(expectedIterationOrder, sumByIntIterationOrder2);

        MutableCollection<Integer> sumByLongIterationOrder1 = this.newMutableForFilter();
        MutableCollection<Integer> sumByLongIterationOrder2 = this.newMutableForFilter();
        this.getInstanceUnderTest().sumByLong(
                each -> {
                    sumByLongIterationOrder1.add(each);
                    return each;
                },
                each -> {
                    sumByLongIterationOrder2.add(each);
                    return 0L;
                });
        assertIterablesEqual(expectedIterationOrder, sumByLongIterationOrder1);
        assertIterablesEqual(expectedIterationOrder, sumByLongIterationOrder2);

        MutableCollection<Integer> expectedInjectIntoIterationOrder = this.allowsDuplicates()
                ? this.newMutableForFilter(4, 4, 4, 4, 3, 3, 3, 2, 2, 1)
                : expectedIterationOrder;

        MutableCollection<Integer> injectIntoIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().injectInto(0, (Function2<Integer, Integer, Integer>) (argument1, argument2) -> {
            injectIntoIterationOrder.add(argument2);
            return argument1 + argument2;
        });
        assertIterablesEqual(expectedInjectIntoIterationOrder, injectIntoIterationOrder);

        MutableCollection<Integer> injectIntoIntIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().injectInto(0, (IntObjectToIntFunction<Integer>) (intParameter, objectParameter) -> {
            injectIntoIntIterationOrder.add(objectParameter);
            return intParameter + objectParameter;
        });
        assertIterablesEqual(expectedInjectIntoIterationOrder, injectIntoIntIterationOrder);

        MutableCollection<Integer> injectIntoLongIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().injectInto(0L, (LongObjectToLongFunction<Integer>) (longParameter, objectParameter) -> {
            injectIntoLongIterationOrder.add(objectParameter);
            return longParameter + objectParameter;
        });
        assertIterablesEqual(expectedInjectIntoIterationOrder, injectIntoLongIterationOrder);

        MutableCollection<Integer> injectIntoDoubleIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().injectInto(0L, (DoubleObjectToDoubleFunction<Integer>) (doubleParameter, objectParameter) -> {
            injectIntoDoubleIterationOrder.add(objectParameter);
            return doubleParameter + objectParameter;
        });
        assertIterablesEqual(expectedInjectIntoIterationOrder, injectIntoDoubleIterationOrder);

        MutableCollection<Integer> injectIntoFloatIterationOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().injectInto(0.0f, (FloatObjectToFloatFunction<Integer>) (floatParameter, objectParameter) -> {
            injectIntoFloatIterationOrder.add(objectParameter);
            return floatParameter + objectParameter;
        });
        assertIterablesEqual(expectedInjectIntoIterationOrder, injectIntoFloatIterationOrder);

        Counter toSortedListCount = new Counter();
        this.getInstanceUnderTest().toSortedList((unused1, unused2) -> {
            toSortedListCount.increment();
            return 0;
        });
        assertEquals(expectedIterationOrder.size() - 1, toSortedListCount.getCount());

        Counter toSortedSetCount = new Counter();
        this.getInstanceUnderTest().toSortedSet((unused1, unused2) -> {
            toSortedSetCount.increment();
            return 0;
        });
        assertEquals(expectedIterationOrder.size(), toSortedSetCount.getCount());

        Counter toSortedBagCount = new Counter();
        this.getInstanceUnderTest().toSortedBag((unused1, unused2) -> {
            toSortedBagCount.increment();
            return 0;
        });
        assertEquals(expectedIterationOrder.size(), toSortedBagCount.getCount());

        MutableCollection<Integer> summarizeIntOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().summarizeInt(each -> {
            summarizeIntOrder.add(each);
            return 0;
        });
        assertIterablesEqual(expectedIterationOrder, summarizeIntOrder);

        MutableCollection<Integer> summarizeFloatOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().summarizeFloat(each -> {
            summarizeFloatOrder.add(each);
            return 0;
        });
        assertIterablesEqual(expectedIterationOrder, summarizeFloatOrder);

        MutableCollection<Integer> summarizeLongOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().summarizeLong(each -> {
            summarizeLongOrder.add(each);
            return 0;
        });
        assertIterablesEqual(expectedIterationOrder, summarizeLongOrder);

        MutableCollection<Integer> summarizeDoubleOrder = this.newMutableForFilter();
        this.getInstanceUnderTest().summarizeDouble(each -> {
            summarizeDoubleOrder.add(each);
            return 0;
        });
        assertIterablesEqual(expectedIterationOrder, summarizeDoubleOrder);
    }

    default MutableCollection<Integer> expectedIterationOrder()
    {
        MutableCollection<Integer> forEach = this.newMutableForFilter();
        this.getInstanceUnderTest().each(forEach::add);
        return forEach;
    }

    default RichIterable<Integer> getInstanceUnderTest()
    {
        return this.allowsDuplicates()
                ? this.newWith(4, 4, 4, 4, 3, 3, 3, 2, 2, 1)
                : this.newWith(4, 3, 2, 1);
    }
}
