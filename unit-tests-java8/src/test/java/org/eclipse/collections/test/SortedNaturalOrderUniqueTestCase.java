/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.collection.primitive.MutableByteCollection;
import org.eclipse.collections.api.collection.primitive.MutableCharCollection;
import org.eclipse.collections.api.collection.primitive.MutableDoubleCollection;
import org.eclipse.collections.api.collection.primitive.MutableFloatCollection;
import org.eclipse.collections.api.collection.primitive.MutableIntCollection;
import org.eclipse.collections.api.collection.primitive.MutableLongCollection;
import org.eclipse.collections.api.collection.primitive.MutableShortCollection;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.ordered.SortedIterable;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.set.sorted.SortedSetIterable;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.test.domain.A;
import org.eclipse.collections.test.domain.B;
import org.eclipse.collections.test.domain.C;
import org.eclipse.collections.test.set.sorted.SortedSetIterableTestCase;
import org.junit.Assert;
import org.junit.Test;

import static org.eclipse.collections.impl.test.Verify.assertThrows;
import static org.eclipse.collections.test.IterableTestCase.assertEquals;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public interface SortedNaturalOrderUniqueTestCase extends SortedNaturalOrderTestCase, SortedSetIterableTestCase
{
    @Override
    default <T> SortedSetIterable<T> getExpectedFiltered(T... elements)
    {
        return SortedSets.immutable.with(elements);
    }

    @Override
    default <T> MutableSortedSet<T> newMutableForFilter(T... elements)
    {
        return SortedSets.mutable.with(elements);
    }

    @Override
    @Test
    default void Iterable_next()
    {
        SortedSetIterable<Integer> iterable = this.newWith(1, 2, 3);

        Iterator<Integer> iterator = iterable.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(1), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(2), iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(Integer.valueOf(3), iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Override
    @Test
    default void RichIterable_selectInstancesOf()
    {
        // Must test with two classes that are mutually Comparable

        SortedSetIterable<A> numbers = this.newWith(new B(1), new C(2.0), new B(3), new C(4.0));
        assertEquals(this.getExpectedFiltered(new B(1), new B(3)), numbers.selectInstancesOf(B.class));
        assertEquals(this.getExpectedFiltered(new B(1), new C(2.0), new B(3), new C(4.0)), numbers.selectInstancesOf(A.class));
    }

    @Override
    @Test
    default void RichIterable_collect()
    {
        RichIterable<Integer> iterable = this.newWith(1, 2, 3, 11, 12, 13);

        assertEquals(
                this.getExpectedTransformed(1, 2, 3, 1, 2, 3),
                iterable.collect(i -> i % 10));

        {
            MutableCollection<Integer> target = this.newMutableForTransform();
            MutableCollection<Integer> result = iterable.collect(i -> i % 10, target);
            assertEquals(this.newMutableForTransform(1, 2, 3, 1, 2, 3), result);
            assertSame(target, result);
        }

        assertEquals(
                this.getExpectedTransformed(1, 2, 3, 1, 2, 3),
                iterable.collectWith((i, mod) -> i % mod, 10));

        MutableCollection<Integer> target = this.newMutableForTransform();
        MutableCollection<Integer> result = iterable.collectWith((i, mod) -> i % mod, 10, target);
        assertEquals(this.newMutableForTransform(1, 2, 3, 1, 2, 3), result);
        assertSame(target, result);
    }

    @Override
    @Test
    default void RichIterable_collectIf()
    {
        RichIterable<Integer> iterable = this.newWith(1, 2, 3, 11, 12, 13);

        assertEquals(
                this.getExpectedTransformed(1, 3, 1, 3),
                iterable.collectIf(i -> i % 2 != 0, i -> i % 10));

        MutableCollection<Integer> target = this.newMutableForTransform();
        MutableCollection<Integer> result = iterable.collectIf(i -> i % 2 != 0, i -> i % 10, target);
        assertEquals(this.newMutableForTransform(1, 3, 1, 3), result);
        assertSame(target, result);
    }

    @Override
    @Test
    default void RichIterable_collectPrimitive()
    {
        assertEquals(
                this.getExpectedBoolean(false, true, false),
                this.newWith(1, 2, 3).collectBoolean(each -> each % 2 == 0));

        {
            MutableBooleanCollection target = this.newBooleanForTransform();
            MutableBooleanCollection result = this.newWith(1, 2, 3).collectBoolean(each -> each % 2 == 0, target);
            assertEquals(this.newBooleanForTransform(false, true, false), result);
            assertSame(target, result);
        }

        RichIterable<Integer> iterable = this.newWith(1, 2, 3, 11, 12, 13);

        assertEquals(
                this.getExpectedByte((byte) 1, (byte) 2, (byte) 3, (byte) 1, (byte) 2, (byte) 3),
                iterable.collectByte(each -> (byte) (each % 10)));

        {
            MutableByteCollection target = this.newByteForTransform();
            MutableByteCollection result = iterable.collectByte(each -> (byte) (each % 10), target);
            assertEquals(this.newByteForTransform((byte) 1, (byte) 2, (byte) 3, (byte) 1, (byte) 2, (byte) 3), result);
            assertSame(target, result);
        }

        assertEquals(
                this.getExpectedChar((char) 1, (char) 2, (char) 3, (char) 1, (char) 2, (char) 3),
                iterable.collectChar(each -> (char) (each % 10)));

        {
            MutableCharCollection target = this.newCharForTransform();
            MutableCharCollection result = iterable.collectChar(each -> (char) (each % 10), target);
            assertEquals(this.newCharForTransform((char) 1, (char) 2, (char) 3, (char) 1, (char) 2, (char) 3), result);
            assertSame(target, result);
        }

        assertEquals(
                this.getExpectedDouble(1.0, 2.0, 3.0, 1.0, 2.0, 3.0),
                iterable.collectDouble(each -> (double) (each % 10)));

        {
            MutableDoubleCollection target = this.newDoubleForTransform();
            MutableDoubleCollection result = iterable.collectDouble(each -> (double) (each % 10), target);
            assertEquals(this.newDoubleForTransform(1.0, 2.0, 3.0, 1.0, 2.0, 3.0), result);
            assertSame(target, result);
        }

        assertEquals(
                this.getExpectedFloat(1.0f, 2.0f, 3.0f, 1.0f, 2.0f, 3.0f),
                iterable.collectFloat(each -> (float) (each % 10)));

        {
            MutableFloatCollection target = this.newFloatForTransform();
            MutableFloatCollection result = iterable.collectFloat(each -> (float) (each % 10), target);
            assertEquals(this.newFloatForTransform(1.0f, 2.0f, 3.0f, 1.0f, 2.0f, 3.0f), result);
            assertSame(target, result);
        }

        assertEquals(
                this.getExpectedInt(1, 2, 3, 1, 2, 3),
                iterable.collectInt(each -> each % 10));

        {
            MutableIntCollection target = this.newIntForTransform();
            MutableIntCollection result = iterable.collectInt(each -> each % 10, target);
            assertEquals(this.newIntForTransform(1, 2, 3, 1, 2, 3), result);
            assertSame(target, result);
        }

        assertEquals(
                this.getExpectedLong(1, 2, 3, 1, 2, 3),
                iterable.collectLong(each -> each % 10));

        {
            MutableLongCollection target = this.newLongForTransform();
            MutableLongCollection result = iterable.collectLong(each -> each % 10, target);
            assertEquals(this.newLongForTransform(1, 2, 3, 1, 2, 3), result);
            assertSame(target, result);
        }

        assertEquals(
                this.getExpectedShort((short) 1, (short) 2, (short) 3, (short) 1, (short) 2, (short) 3),
                iterable.collectShort(each -> (short) (each % 10)));

        MutableShortCollection target = this.newShortForTransform();
        MutableShortCollection result = iterable.collectShort(each -> (short) (each % 10), target);
        assertEquals(this.newShortForTransform((short) 1, (short) 2, (short) 3, (short) 1, (short) 2, (short) 3), result);
        assertSame(target, result);
    }

    @Override
    @Test
    default void RichIterable_flatCollect()
    {
        assertEquals(
                this.getExpectedTransformed(1, 1, 2, 1, 2, 3),
                this.newWith(1, 2, 3).flatCollect(Interval::oneTo));

        assertEquals(
                this.newMutableForTransform(1, 1, 2, 1, 2, 3),
                this.newWith(1, 2, 3).flatCollect(Interval::oneTo, this.newMutableForTransform()));
    }

    @Override
    @Test
    default void RichIterable_detect()
    {
        RichIterable<Integer> iterable = this.newWith(1, 2, 3);

        assertThat(iterable.detect(Predicates.greaterThan(0)), is(1));
        assertThat(iterable.detect(Predicates.greaterThan(1)), is(2));
        assertThat(iterable.detect(Predicates.greaterThan(2)), is(3));
        assertThat(iterable.detect(Predicates.greaterThan(3)), nullValue());

        assertThat(iterable.detect(Predicates.lessThan(1)), nullValue());
        assertThat(iterable.detect(Predicates.lessThan(2)), is(1));
        assertThat(iterable.detect(Predicates.lessThan(3)), is(1));
        assertThat(iterable.detect(Predicates.lessThan(4)), is(1));

        assertThat(iterable.detectWith(Predicates2.greaterThan(), 0), is(1));
        assertThat(iterable.detectWith(Predicates2.greaterThan(), 1), is(2));
        assertThat(iterable.detectWith(Predicates2.greaterThan(), 2), is(3));
        assertThat(iterable.detectWith(Predicates2.greaterThan(), 3), nullValue());

        assertThat(iterable.detectWith(Predicates2.lessThan(), 1), nullValue());
        assertThat(iterable.detectWith(Predicates2.lessThan(), 2), is(1));
        assertThat(iterable.detectWith(Predicates2.lessThan(), 3), is(1));
        assertThat(iterable.detectWith(Predicates2.lessThan(), 4), is(1));

        assertThat(iterable.detectIfNone(Predicates.greaterThan(0), () -> 4), is(1));
        assertThat(iterable.detectIfNone(Predicates.greaterThan(1), () -> 4), is(2));
        assertThat(iterable.detectIfNone(Predicates.greaterThan(2), () -> 4), is(3));
        assertThat(iterable.detectIfNone(Predicates.greaterThan(3), () -> 4), is(4));

        assertThat(iterable.detectIfNone(Predicates.lessThan(1), () -> 4), is(4));
        assertThat(iterable.detectIfNone(Predicates.lessThan(2), () -> 4), is(1));
        assertThat(iterable.detectIfNone(Predicates.lessThan(3), () -> 4), is(1));
        assertThat(iterable.detectIfNone(Predicates.lessThan(4), () -> 4), is(1));

        assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 0, () -> 4), is(1));
        assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 1, () -> 4), is(2));
        assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 2, () -> 4), is(3));
        assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 3, () -> 4), is(4));

        assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 1, () -> 4), is(4));
        assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 2, () -> 4), is(1));
        assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 3, () -> 4), is(1));
        assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 4, () -> 4), is(1));
    }

    @Override
    @Test
    default void RichIterable_minBy_maxBy()
    {
        assertEquals("ca", this.newWith("ab", "bc", "ca", "da", "ed").minBy(string -> string.charAt(string.length() - 1)));
        assertThrows(NoSuchElementException.class, () -> this.<String>newWith().minBy(string -> string.charAt(string.length() - 1)));

        assertEquals("cz", this.newWith("ew", "dz", "cz", "bx", "ay").maxBy(string -> string.charAt(string.length() - 1)));
        assertThrows(NoSuchElementException.class, () -> this.<String>newWith().maxBy(string -> string.charAt(string.length() - 1)));
    }

    @Override
    @Test
    default void RichIterable_makeString_appendString()
    {
        RichIterable<Integer> iterable = this.newWith(1, 2, 3, 4);
        assertEquals(
                "1, 2, 3, 4",
                iterable.makeString());

        assertEquals(
                "1/2/3/4",
                iterable.makeString("/"));

        assertEquals(
                "[1/2/3/4]",
                iterable.makeString("[", "/", "]"));

        StringBuilder builder1 = new StringBuilder();
        iterable.appendString(builder1);
        assertEquals(
                "1, 2, 3, 4",
                builder1.toString());

        StringBuilder builder2 = new StringBuilder();
        iterable.appendString(builder2, "/");
        assertEquals(
                "1/2/3/4",
                builder2.toString());

        StringBuilder builder3 = new StringBuilder();
        iterable.appendString(builder3, "[", "/", "]");
        assertEquals(
                "[1/2/3/4]",
                builder3.toString());
    }

    @Override
    @Test
    default void RichIterable_toString()
    {
        RichIterable<Integer> iterable = this.newWith(1, 2, 3, 4);
        assertEquals(
                "[1, 2, 3, 4]",
                iterable.toString());
    }

    @Override
    @Test
    default void RichIterable_toList()
    {
        SortedSetIterable<Integer> iterable = this.newWith(1, 2, 3, 4);
        assertEquals(
                Lists.immutable.with(1, 2, 3, 4),
                iterable.toList());

        MutableList<Integer> target = Lists.mutable.empty();
        iterable.each(target::add);
        assertEquals(
                target,
                iterable.toList());
    }

    @Override
    @Test
    default void RichIterable_into()
    {
        assertEquals(
                Lists.immutable.with(1, 2, 3, 4),
                this.newWith(1, 2, 3, 4).into(Lists.mutable.empty()));
    }

    @Override
    @Test
    default void RichIterable_toArray()
    {
        Object[] array = this.newWith(1, 2, 3).toArray();
        assertArrayEquals(new Object[]{1, 2, 3}, array);
    }

    @Override
    @Test
    default void OrderedIterable_getFirst()
    {
        assertEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getFirst());
    }

    @Override
    @Test
    default void OrderedIterable_getLast()
    {
        assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    default void OrderedIterable_next()
    {
        Iterator<Integer> iterator = this.newWith(1, 2, 3).iterator();
        assertEquals(Integer.valueOf(1), iterator.next());
        assertEquals(Integer.valueOf(2), iterator.next());
        assertEquals(Integer.valueOf(3), iterator.next());
    }

    @Override
    @Test
    default void OrderedIterable_zipWithIndex()
    {
        RichIterable<Integer> iterable = this.newWith(1, 2, 3, 4);
        Assert.assertEquals(
                Lists.immutable.with(
                        Tuples.pair(1, 0),
                        Tuples.pair(2, 1),
                        Tuples.pair(3, 2),
                        Tuples.pair(4, 3)),
                iterable.zipWithIndex().toList());
    }

    @Override
    @Test
    default void OrderedIterable_zipWithIndex_target()
    {
        RichIterable<Integer> iterable = this.newWith(1, 2, 3, 4);
        MutableList<Pair<Integer, Integer>> target = Lists.mutable.empty();
        MutableList<Pair<Integer, Integer>> result = iterable.zipWithIndex(target);
        Assert.assertEquals(
                Lists.immutable.with(
                        Tuples.pair(1, 0),
                        Tuples.pair(2, 1),
                        Tuples.pair(3, 2),
                        Tuples.pair(4, 3)),
                result);
        assertSame(target, result);
    }

    @Override
    @Test
    default void SortedIterable_comparator()
    {
        SortedIterable<Object> iterable = this.newWith();
        assertNull(iterable.comparator());
    }

    @Override
    @Test
    default void SortedSetIterable_union()
    {
        SortedSetIterable<Integer> union = this.newWith(1, 2, 3).union(this.newWith(3, 4, 5));
        assertEquals(SortedSets.immutable.with(1, 2, 3, 4, 5), union);
    }
}
