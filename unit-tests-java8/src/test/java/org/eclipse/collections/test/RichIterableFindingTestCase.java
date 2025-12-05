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
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isOneOf;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public interface RichIterableFindingTestCase extends InternalIterableTestCase
{
    @Test
    default void RichIterable_getFirst_empty_null()
    {
        assertThat(this.newWith().getFirst(), nullValue());
    }

    @Test
    default void RichIterable_getLast_empty_null()
    {
        assertThat(this.newWith().getLast(), nullValue());
    }

    @Test
    default void RichIterable_getFirst()
    {
        RichIterable<Integer> iterable = this.newWith(3, 2, 1);
        Integer first = iterable.getFirst();
        assertThat(first, isOneOf(3, 2, 1));
        assertEquals(iterable.iterator().next(), first);

        switch (this.getOrderingType())
        {
            case UNORDERED -> assertThat(first, isOneOf(3, 2, 1));
            case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertEquals(Integer.valueOf(3), first);
            case SORTED_NATURAL -> assertEquals(Integer.valueOf(1), first);
            default -> fail("Unexpected value: " + this.getOrderingType());
        }

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableWithDuplicates = this.newWith(3, 3, 3, 2, 2, 1);
        Integer firstWithDuplicates = iterableWithDuplicates.getFirst();
        assertThat(firstWithDuplicates, isOneOf(3, 2, 1));
        assertEquals(iterableWithDuplicates.iterator().next(), firstWithDuplicates);

        switch (this.getOrderingType())
        {
            case UNORDERED -> assertThat(firstWithDuplicates, isOneOf(3, 2, 1));
            case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertEquals(Integer.valueOf(3), firstWithDuplicates);
            case SORTED_NATURAL -> assertEquals(Integer.valueOf(1), firstWithDuplicates);
            default -> fail("Unexpected value: " + this.getOrderingType());
        }
    }

    @Test
    default void RichIterable_getLast()
    {
        RichIterable<Integer> iterable = this.newWith(3, 2, 1);
        Integer last = iterable.getLast();
        assertThat(last, isOneOf(3, 2, 1));
        Iterator<Integer> iterator = iterable.iterator();
        Integer iteratorLast = null;
        while (iterator.hasNext())
        {
            iteratorLast = iterator.next();
        }
        assertEquals(iteratorLast, last);

        switch (this.getOrderingType())
        {
            case UNORDERED -> assertThat(last, isOneOf(3, 2, 1));
            case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertThat(last, is(1));
            case SORTED_NATURAL -> assertThat(last, is(3));
            default -> fail("Unexpected value: " + this.getOrderingType());
        }

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableWithDuplicates = this.newWith(3, 3, 3, 2, 2, 1);
        Integer lastWithDuplicates = iterableWithDuplicates.getLast();
        assertThat(lastWithDuplicates, isOneOf(3, 2, 1));
        Iterator<Integer> iteratorWithDuplicates = iterableWithDuplicates.iterator();
        Integer iteratorLastWithDuplicates = null;
        while (iteratorWithDuplicates.hasNext())
        {
            iteratorLastWithDuplicates = iteratorWithDuplicates.next();
        }
        assertEquals(iteratorLastWithDuplicates, lastWithDuplicates);

        switch (this.getOrderingType())
        {
            case UNORDERED -> assertThat(lastWithDuplicates, isOneOf(3, 2, 1));
            case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertEquals(Integer.valueOf(1), lastWithDuplicates);
            case SORTED_NATURAL -> assertEquals(Integer.valueOf(3), lastWithDuplicates);
            default -> fail("Unexpected value: " + this.getOrderingType());
        }
    }

    @Test
    default void RichIterable_getOnly()
    {
        RichIterable<Integer> iterable = this.newWith(3);
        Integer only = iterable.getOnly();
        assertThat(only, is(3));

        Iterator<Integer> iterator = iterable.iterator();
        assertThat(iterator.next(), is(only));
        assertThat(iterator.hasNext(), is(false));

        assertThrows(IllegalStateException.class, () -> this.newWith().getOnly());
        assertThrows(IllegalStateException.class, () -> this.newWith(1, 2).getOnly());

        if (this.allowsDuplicates())
        {
            assertThrows(IllegalStateException.class, () -> this.newWith(1, 1).getOnly());
        }
    }

    @Test
    default void RichIterable_getFirst_and_getLast()
    {
        RichIterable<Integer> iterable = this.newWith(3, 2, 1);
        assertNotEquals(iterable.getFirst(), iterable.getLast());
    }

    @Test
    default void RichIterable_detect()
    {
        RichIterable<Integer> iterable = this.newWith(3, 2, 1);

        assertThat(iterable.detect(Predicates.greaterThan(0)), isOneOf(3, 2, 1));
        assertThat(iterable.detect(Predicates.greaterThan(1)), isOneOf(3, 2));
        assertThat(iterable.detect(Predicates.greaterThan(2)), is(3));
        assertThat(iterable.detect(Predicates.greaterThan(3)), nullValue());

        assertThat(iterable.detect(Predicates.lessThan(1)), nullValue());
        assertThat(iterable.detect(Predicates.lessThan(2)), is(1));
        assertThat(iterable.detect(Predicates.lessThan(3)), isOneOf(2, 1));
        assertThat(iterable.detect(Predicates.lessThan(4)), isOneOf(3, 2, 1));

        assertThat(iterable.detectWith(Predicates2.greaterThan(), 0), isOneOf(3, 2, 1));
        assertThat(iterable.detectWith(Predicates2.greaterThan(), 1), isOneOf(3, 2));
        assertThat(iterable.detectWith(Predicates2.greaterThan(), 2), is(3));
        assertThat(iterable.detectWith(Predicates2.greaterThan(), 3), nullValue());

        assertThat(iterable.detectWith(Predicates2.lessThan(), 1), nullValue());
        assertThat(iterable.detectWith(Predicates2.lessThan(), 2), is(1));
        assertThat(iterable.detectWith(Predicates2.lessThan(), 3), isOneOf(2, 1));
        assertThat(iterable.detectWith(Predicates2.lessThan(), 4), isOneOf(3, 2, 1));

        assertThat(iterable.detectIfNone(Predicates.greaterThan(0), () -> 4), isOneOf(3, 2, 1));
        assertThat(iterable.detectIfNone(Predicates.greaterThan(1), () -> 4), isOneOf(3, 2));
        assertThat(iterable.detectIfNone(Predicates.greaterThan(2), () -> 4), is(3));
        assertThat(iterable.detectIfNone(Predicates.greaterThan(3), () -> 4), is(4));

        assertThat(iterable.detectIfNone(Predicates.lessThan(1), () -> 4), is(4));
        assertThat(iterable.detectIfNone(Predicates.lessThan(2), () -> 4), is(1));
        assertThat(iterable.detectIfNone(Predicates.lessThan(3), () -> 4), isOneOf(2, 1));
        assertThat(iterable.detectIfNone(Predicates.lessThan(4), () -> 4), isOneOf(3, 2, 1));

        assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 0, () -> 4), isOneOf(3, 2, 1));
        assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 1, () -> 4), isOneOf(3, 2));
        assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 2, () -> 4), is(3));
        assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 3, () -> 4), is(4));

        assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 1, () -> 4), is(4));
        assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 2, () -> 4), is(1));
        assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 3, () -> 4), isOneOf(2, 1));
        assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 4, () -> 4), isOneOf(3, 2, 1));

        assertThat(iterable.detectOptional(Predicates.greaterThan(0)), isOneOf(Optional.of(3), Optional.of(2), Optional.of(1)));
        assertThat(iterable.detectOptional(Predicates.greaterThan(1)), isOneOf(Optional.of(3), Optional.of(2)));
        assertThat(iterable.detectOptional(Predicates.greaterThan(2)), is(Optional.of(3)));
        assertThat(iterable.detectOptional(Predicates.greaterThan(3)), is(Optional.empty()));

        assertThat(iterable.detectOptional(Predicates.lessThan(1)), is(Optional.empty()));
        assertThat(iterable.detectOptional(Predicates.lessThan(2)), is(Optional.of(1)));
        assertThat(iterable.detectOptional(Predicates.lessThan(3)), isOneOf(Optional.of(2), Optional.of(1)));
        assertThat(iterable.detectOptional(Predicates.lessThan(4)), isOneOf(Optional.of(3), Optional.of(2), Optional.of(1)));

        assertThat(iterable.detectWithOptional(Predicates2.greaterThan(), 0), isOneOf(Optional.of(3), Optional.of(2), Optional.of(1)));
        assertThat(iterable.detectWithOptional(Predicates2.greaterThan(), 1), isOneOf(Optional.of(3), Optional.of(2)));
        assertThat(iterable.detectWithOptional(Predicates2.greaterThan(), 2), is(Optional.of(3)));
        assertThat(iterable.detectWithOptional(Predicates2.greaterThan(), 3), is(Optional.empty()));

        assertThat(iterable.detectWithOptional(Predicates2.lessThan(), 1), is(Optional.empty()));
        assertThat(iterable.detectWithOptional(Predicates2.lessThan(), 2), is(Optional.of(1)));
        assertThat(iterable.detectWithOptional(Predicates2.lessThan(), 3), isOneOf(Optional.of(2), Optional.of(1)));
        assertThat(iterable.detectWithOptional(Predicates2.lessThan(), 4), isOneOf(Optional.of(3), Optional.of(2), Optional.of(1)));

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL ->
                {
                    assertThat(iterable.detect(Predicates.greaterThan(0)), is(3));
                    assertThat(iterable.detect(Predicates.greaterThan(1)), is(3));
                    assertThat(iterable.detect(Predicates.lessThan(3)), is(2));
                    assertThat(iterable.detect(Predicates.lessThan(4)), is(3));

                    assertThat(iterable.detectWith(Predicates2.greaterThan(), 0), is(3));
                    assertThat(iterable.detectWith(Predicates2.greaterThan(), 1), is(3));
                    assertThat(iterable.detectWith(Predicates2.lessThan(), 3), is(2));
                    assertThat(iterable.detectWith(Predicates2.lessThan(), 4), is(3));

                    assertThat(iterable.detectIfNone(Predicates.greaterThan(0), () -> 4), is(3));
                    assertThat(iterable.detectIfNone(Predicates.greaterThan(1), () -> 4), is(3));
                    assertThat(iterable.detectIfNone(Predicates.lessThan(3), () -> 4), is(2));
                    assertThat(iterable.detectIfNone(Predicates.lessThan(4), () -> 4), is(3));

                    assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 0, () -> 4), is(3));
                    assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 1, () -> 4), is(3));
                    assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 3, () -> 4), is(2));
                    assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 4, () -> 4), is(3));

                    assertThat(iterable.detectOptional(Predicates.greaterThan(0)), is(Optional.of(3)));
                    assertThat(iterable.detectOptional(Predicates.greaterThan(1)), is(Optional.of(3)));
                    assertThat(iterable.detectOptional(Predicates.lessThan(3)), is(Optional.of(2)));
                    assertThat(iterable.detectOptional(Predicates.lessThan(4)), is(Optional.of(3)));

                    assertThat(iterable.detectWithOptional(Predicates2.greaterThan(), 0), is(Optional.of(3)));
                    assertThat(iterable.detectWithOptional(Predicates2.greaterThan(), 1), is(Optional.of(3)));
                    assertThat(iterable.detectWithOptional(Predicates2.lessThan(), 3), is(Optional.of(2)));
                    assertThat(iterable.detectWithOptional(Predicates2.lessThan(), 4), is(Optional.of(3)));
                }
                case SORTED_NATURAL ->
                {
                    assertThat(iterable.detect(Predicates.greaterThan(0)), is(1));
                    assertThat(iterable.detect(Predicates.greaterThan(1)), is(2));
                    assertThat(iterable.detect(Predicates.lessThan(3)), is(1));
                    assertThat(iterable.detect(Predicates.lessThan(4)), is(1));

                    assertThat(iterable.detectWith(Predicates2.greaterThan(), 0), is(1));
                    assertThat(iterable.detectWith(Predicates2.greaterThan(), 1), is(2));
                    assertThat(iterable.detectWith(Predicates2.lessThan(), 3), is(1));
                    assertThat(iterable.detectWith(Predicates2.lessThan(), 4), is(1));

                    assertThat(iterable.detectIfNone(Predicates.greaterThan(0), () -> 4), is(1));
                    assertThat(iterable.detectIfNone(Predicates.greaterThan(1), () -> 4), is(2));
                    assertThat(iterable.detectIfNone(Predicates.lessThan(3), () -> 4), is(1));
                    assertThat(iterable.detectIfNone(Predicates.lessThan(4), () -> 4), is(1));

                    assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 0, () -> 4), is(1));
                    assertThat(iterable.detectWithIfNone(Predicates2.greaterThan(), 1, () -> 4), is(2));
                    assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 3, () -> 4), is(1));
                    assertThat(iterable.detectWithIfNone(Predicates2.lessThan(), 4, () -> 4), is(1));

                    assertThat(iterable.detectOptional(Predicates.greaterThan(0)), is(Optional.of(1)));
                    assertThat(iterable.detectOptional(Predicates.greaterThan(1)), is(Optional.of(2)));
                    assertThat(iterable.detectOptional(Predicates.lessThan(3)), is(Optional.of(1)));
                    assertThat(iterable.detectOptional(Predicates.lessThan(4)), is(Optional.of(1)));

                    assertThat(iterable.detectWithOptional(Predicates2.greaterThan(), 0), is(Optional.of(1)));
                    assertThat(iterable.detectWithOptional(Predicates2.greaterThan(), 1), is(Optional.of(2)));
                    assertThat(iterable.detectWithOptional(Predicates2.lessThan(), 3), is(Optional.of(1)));
                    assertThat(iterable.detectWithOptional(Predicates2.lessThan(), 4), is(Optional.of(1)));
                }
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }
    }

    @Test
    default void RichIterable_detectOptionalNull()
    {
        RichIterable<Integer> iterable = this.newWith(1, null, 3);

        assertThrows(NullPointerException.class, () -> iterable.detectOptional(Objects::isNull));
        assertThrows(NullPointerException.class, () -> iterable.detectWithOptional((i, object) -> i == object, null));
    }

    @Test
    default void RichIterable_min_max()
    {
        assertEquals(Integer.valueOf(-1), this.newWith(-1, 0, 1).min());
        assertEquals(Integer.valueOf(-1), this.newWith(1, 0, -1).min());
        assertThrows(NoSuchElementException.class, () -> this.newWith().min());

        assertEquals(Integer.valueOf(1), this.newWith(-1, 0, 1).max());
        assertEquals(Integer.valueOf(1), this.newWith(1, 0, -1).max());
        assertThrows(NoSuchElementException.class, () -> this.newWith().max());

        assertEquals(Integer.valueOf(1), this.newWith(-1, 0, 1).min(Comparators.reverseNaturalOrder()));
        assertEquals(Integer.valueOf(1), this.newWith(1, 0, -1).min(Comparators.reverseNaturalOrder()));
        assertThrows(NoSuchElementException.class, () -> this.newWith().min(Comparators.reverseNaturalOrder()));

        assertEquals(Integer.valueOf(-1), this.newWith(-1, 0, 1).max(Comparators.reverseNaturalOrder()));
        assertEquals(Integer.valueOf(-1), this.newWith(1, 0, -1).max(Comparators.reverseNaturalOrder()));
        assertThrows(NoSuchElementException.class, () -> this.newWith().max(Comparators.reverseNaturalOrder()));

        if (!this.allowsDuplicates())
        {
            return;
        }

        assertEquals(Integer.valueOf(-1), this.newWith(-1, -1, 0, 0, 1, 1).min());
        assertEquals(Integer.valueOf(-1), this.newWith(1, 1, 0, 0, -1, -1).min());

        assertEquals(Integer.valueOf(1), this.newWith(-1, -1, 0, 0, 1, 1).max());
        assertEquals(Integer.valueOf(1), this.newWith(1, 1, 0, 0, -1, -1).max());

        assertEquals(Integer.valueOf(1), this.newWith(-1, -1, 0, 0, 1, 1).min(Comparators.reverseNaturalOrder()));
        assertEquals(Integer.valueOf(1), this.newWith(1, 1, 0, 0, -1, -1).min(Comparators.reverseNaturalOrder()));

        assertEquals(Integer.valueOf(-1), this.newWith(-1, -1, 0, 0, 1, 1).max(Comparators.reverseNaturalOrder()));
        assertEquals(Integer.valueOf(-1), this.newWith(1, 1, 0, 0, -1, -1).max(Comparators.reverseNaturalOrder()));

        if (this.getOrderingType() == OrderingType.UNORDERED)
        {
            return;
        }

        Holder<Integer> firstMin = new Holder<>(-1);
        Holder<Integer> secondMin = new Holder<>(-1);
        assertSame(firstMin, this.newWith(new Holder<>(2), firstMin, new Holder<>(0), secondMin).min());

        Holder<Integer> firstMax = new Holder<>(1);
        Holder<Integer> secondMax = new Holder<>(1);
        assertSame(firstMax, this.newWith(new Holder<>(-2), firstMax, new Holder<>(0), secondMax).max());

        Holder<Integer> firstMinReverse = new Holder<>(1);
        Holder<Integer> secondMinReverse = new Holder<>(1);
        assertSame(firstMinReverse, this.newWith(new Holder<>(-2), firstMinReverse, new Holder<>(0), secondMinReverse).min(Comparators.reverseNaturalOrder()));

        Holder<Integer> firstMaxReverse = new Holder<>(-1);
        Holder<Integer> secondMaxReverse = new Holder<>(-1);
        assertSame(firstMaxReverse, this.newWith(new Holder<>(2), firstMaxReverse, new Holder<>(0), secondMaxReverse).max(Comparators.reverseNaturalOrder()));
    }

    @Test
    default void RichIterable_min_max_non_comparable()
    {
        Object sentinel = new Object();

        assertSame(sentinel, this.newWith(sentinel).min());
        assertThrows(ClassCastException.class, () -> this.newWith(new Object(), new Object()).min());

        assertSame(sentinel, this.newWith(sentinel).max());
        assertThrows(ClassCastException.class, () -> this.newWith(new Object(), new Object()).max());

        assertSame(sentinel, this.newWith(sentinel).min(Comparators.reverseNaturalOrder()));
        assertThrows(ClassCastException.class, () -> this.newWith(new Object(), new Object()).min(Comparators.reverseNaturalOrder()));

        assertSame(sentinel, this.newWith(sentinel).max(Comparators.reverseNaturalOrder()));
        assertThrows(ClassCastException.class, () -> this.newWith(new Object(), new Object()).max(Comparators.reverseNaturalOrder()));
    }

    @Test
    default void RichIterable_minOptional_maxOptional()
    {
        assertEquals(Optional.of(-1), this.newWith(-1, 0, 1).minOptional());
        assertEquals(Optional.of(-1), this.newWith(1, 0, -1).minOptional());
        assertSame(Optional.empty(), this.newWith().minOptional());
        assertThrows(NullPointerException.class, () -> this.newWith(new Object[]{null}).minOptional());

        assertEquals(Optional.of(1), this.newWith(-1, 0, 1).maxOptional());
        assertEquals(Optional.of(1), this.newWith(1, 0, -1).maxOptional());
        assertSame(Optional.empty(), this.newWith().maxOptional());
        assertThrows(NullPointerException.class, () -> this.newWith(new Object[]{null}).maxOptional());

        assertEquals(Optional.of(1), this.newWith(-1, 0, 1).minOptional(Comparators.reverseNaturalOrder()));
        assertEquals(Optional.of(1), this.newWith(1, 0, -1).minOptional(Comparators.reverseNaturalOrder()));
        assertSame(Optional.empty(), this.newWith().minOptional(Comparators.reverseNaturalOrder()));
        assertThrows(NullPointerException.class, () -> this.newWith(new Object[]{null}).minOptional(Comparators.reverseNaturalOrder()));

        assertEquals(Optional.of(-1), this.newWith(-1, 0, 1).maxOptional(Comparators.reverseNaturalOrder()));
        assertEquals(Optional.of(-1), this.newWith(1, 0, -1).maxOptional(Comparators.reverseNaturalOrder()));
        assertSame(Optional.empty(), this.newWith().maxOptional(Comparators.reverseNaturalOrder()));
        assertThrows(NullPointerException.class, () -> this.newWith(new Object[]{null}).maxOptional(Comparators.reverseNaturalOrder()));

        if (!this.allowsDuplicates())
        {
            return;
        }

        assertEquals(Optional.of(-1), this.newWith(-1, -1, 0, 0, 1, 1).minOptional());
        assertEquals(Optional.of(-1), this.newWith(1, 1, 0, 0, -1, -1).minOptional());

        assertEquals(Optional.of(1), this.newWith(-1, -1, 0, 0, 1, 1).maxOptional());
        assertEquals(Optional.of(1), this.newWith(1, 1, 0, 0, -1, -1).maxOptional());

        assertEquals(Optional.of(1), this.newWith(-1, -1, 0, 0, 1, 1).minOptional(Comparators.reverseNaturalOrder()));
        assertEquals(Optional.of(1), this.newWith(1, 1, 0, 0, -1, -1).minOptional(Comparators.reverseNaturalOrder()));

        assertEquals(Optional.of(-1), this.newWith(-1, -1, 0, 0, 1, 1).maxOptional(Comparators.reverseNaturalOrder()));
        assertEquals(Optional.of(-1), this.newWith(1, 1, 0, 0, -1, -1).maxOptional(Comparators.reverseNaturalOrder()));
    }

    @Test
    default void RichIterable_minOptional_maxOptional_non_comparable()
    {
        Object sentinel = new Object();

        assertEquals(Optional.of(sentinel), this.newWith(sentinel).minOptional());
        assertThrows(ClassCastException.class, () -> this.newWith(new Object(), new Object()).minOptional());

        assertEquals(Optional.of(sentinel), this.newWith(sentinel).maxOptional());
        assertThrows(ClassCastException.class, () -> this.newWith(new Object(), new Object()).maxOptional());

        assertEquals(Optional.of(sentinel), this.newWith(sentinel).minOptional(Comparators.reverseNaturalOrder()));
        assertThrows(ClassCastException.class, () -> this.newWith(new Object(), new Object()).minOptional(Comparators.reverseNaturalOrder()));

        assertEquals(Optional.of(sentinel), this.newWith(sentinel).maxOptional(Comparators.reverseNaturalOrder()));
        assertThrows(ClassCastException.class, () -> this.newWith(new Object(), new Object()).maxOptional(Comparators.reverseNaturalOrder()));
    }

    @Test
    default void RichIterable_minBy_maxBy()
    {
        RichIterable<String> minIterable = this.newWith("ed", "da", "ca", "bc", "ab");
        String actualMin = minIterable.minBy(string -> string.charAt(string.length() - 1));
        assertThat(actualMin, isOneOf("ca", "da"));
        assertEquals(minIterable.detect(each -> each.equals("ca") || each.equals("da")), actualMin);
        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertThat(actualMin, is("da"));
                case SORTED_NATURAL -> assertThat(actualMin, is("ca"));
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        assertThrows(NoSuchElementException.class, () -> this.<String>newWith().minBy(string -> string.charAt(string.length() - 1)));

        RichIterable<String> maxIterable = this.newWith("ew", "dz", "cz", "bx", "ay");
        String actualMax = maxIterable.maxBy(string -> string.charAt(string.length() - 1));
        assertThat(actualMax, isOneOf("cz", "dz"));
        assertEquals(maxIterable.detect(each -> each.equals("cz") || each.equals("dz")), actualMax);
        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertThat(actualMax, is("dz"));
                case SORTED_NATURAL -> assertThat(actualMax, is("cz"));
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        assertThrows(NoSuchElementException.class, () -> this.<String>newWith().maxBy(string -> string.charAt(string.length() - 1)));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<String> minIterableDups = this.newWith("ed", "ed", "da", "da", "ca", "ca", "bc", "bc", "ab", "ab");
        String actualMinDups = minIterableDups.minBy(string -> string.charAt(string.length() - 1));
        assertThat(actualMinDups, isOneOf("ca", "da"));
        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertThat(actualMinDups, is("da"));
                case SORTED_NATURAL -> assertThat(actualMinDups, is("ca"));
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        RichIterable<String> maxIterableDups = this.newWith("ew", "ew", "dz", "dz", "cz", "cz", "bx", "bx", "ay", "ay");
        String actualMaxDups = maxIterableDups.maxBy(string -> string.charAt(string.length() - 1));
        assertThat(actualMaxDups, isOneOf("cz", "dz"));
        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertThat(actualMaxDups, is("dz"));
                case SORTED_NATURAL -> assertThat(actualMaxDups, is("cz"));
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }
    }

    @Test
    default void RichIterable_minByOptional_maxByOptional()
    {
        RichIterable<String> minIterable = this.newWith("ed", "da", "ca", "bc", "ab");
        Optional<String> actualMinOptional = minIterable.minByOptional(string -> string.charAt(string.length() - 1));
        assertThat(actualMinOptional, isOneOf(Optional.of("ca"), Optional.of("da")));
        assertEquals(minIterable.detect(each -> each.equals("ca") || each.equals("da")), actualMinOptional.get());
        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertThat(actualMinOptional, is(Optional.of("da")));
                case SORTED_NATURAL -> assertThat(actualMinOptional, is(Optional.of("ca")));
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        assertSame(Optional.empty(), this.<String>newWith().minByOptional(string -> string.charAt(string.length() - 1)));
        assertThrows(NullPointerException.class, () -> this.newWith(new Object[]{null}).minByOptional(Objects::isNull));

        RichIterable<String> maxIterable = this.newWith("ew", "dz", "cz", "bx", "ay");
        Optional<String> actualMaxOptional = maxIterable.maxByOptional(string -> string.charAt(string.length() - 1));
        assertThat(actualMaxOptional, isOneOf(Optional.of("cz"), Optional.of("dz")));
        assertEquals(maxIterable.detect(each -> each.equals("cz") || each.equals("dz")), actualMaxOptional.get());
        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertThat(actualMaxOptional, is(Optional.of("dz")));
                case SORTED_NATURAL -> assertThat(actualMaxOptional, is(Optional.of("cz")));
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        assertSame(Optional.empty(), this.<String>newWith().maxByOptional(string -> string.charAt(string.length() - 1)));
        assertThrows(NullPointerException.class, () -> this.newWith(new Object[]{null}).maxByOptional(Objects::isNull));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<String> minIterableDups = this.newWith("ed", "ed", "da", "da", "ca", "ca", "bc", "bc", "ab", "ab");
        Optional<String> actualMinDupsOptional = minIterableDups.minByOptional(string -> string.charAt(string.length() - 1));
        assertThat(actualMinDupsOptional, isOneOf(Optional.of("ca"), Optional.of("da")));
        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertThat(actualMinDupsOptional, is(Optional.of("da")));
                case SORTED_NATURAL -> assertThat(actualMinDupsOptional, is(Optional.of("ca")));
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }

        RichIterable<String> maxIterableDups = this.newWith("ew", "ew", "dz", "dz", "cz", "cz", "bx", "bx", "ay", "ay");
        Optional<String> actualMaxDupsOptional = maxIterableDups.maxByOptional(string -> string.charAt(string.length() - 1));
        assertThat(actualMaxDupsOptional, isOneOf(Optional.of("cz"), Optional.of("dz")));
        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            switch (this.getOrderingType())
            {
                case INSERTION_ORDER, SORTED_REVERSE_NATURAL -> assertThat(actualMaxDupsOptional, is(Optional.of("dz")));
                case SORTED_NATURAL -> assertThat(actualMaxDupsOptional, is(Optional.of("cz")));
                default -> fail("Unexpected value: " + this.getOrderingType());
            }
        }
    }
}
