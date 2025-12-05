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
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public interface RichIterableTestingTestCase extends InternalIterableTestCase
{
    @Test
    default void RichIterable_isEmpty()
    {
        assertFalse(this.newWith(3, 2, 1).isEmpty());
        assertTrue(this.newWith().isEmpty());
    }

    @Test
    default void RichIterable_notEmpty()
    {
        assertTrue(this.newWith(3, 2, 1).notEmpty());
        assertFalse(this.newWith().notEmpty());
    }

    @Test
    default void RichIterable_contains()
    {
        RichIterable<Integer> iterable3 = this.newWith(3, 2, 1);
        assertTrue(iterable3.contains(3));
        assertTrue(iterable3.contains(2));
        assertTrue(iterable3.contains(1));
        assertFalse(iterable3.contains(0));

        RichIterable<Integer> iterable2 = this.newWith(2, 1);
        assertTrue(iterable2.contains(2));
        assertTrue(iterable2.contains(1));
        assertFalse(iterable2.contains(0));

        RichIterable<Integer> iterable1 = this.newWith(1);
        assertTrue(iterable1.contains(1));
        assertFalse(iterable1.contains(0));

        RichIterable<Integer> iterable0 = this.newWith();
        assertFalse(iterable0.contains(0));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(3, 3, 3, 2, 2, 1);
        assertTrue(iterableDup.contains(3));
        assertTrue(iterableDup.contains(2));
        assertTrue(iterableDup.contains(1));
        assertFalse(iterableDup.contains(0));
    }

    @Test
    default void RichIterable_containsAllIterable()
    {
        RichIterable<Integer> iterable3 = this.newWith(3, 2, 1);

        assertTrue(iterable3.containsAllIterable(Lists.immutable.of(3)));
        assertTrue(iterable3.containsAllIterable(Lists.immutable.of(3, 2, 1)));
        assertTrue(iterable3.containsAllIterable(Lists.immutable.of(3, 3, 3)));
        assertTrue(iterable3.containsAllIterable(Lists.immutable.of(3, 3, 3, 3, 2, 2, 2, 1, 1)));
        assertFalse(iterable3.containsAllIterable(Lists.immutable.of(4)));
        assertFalse(iterable3.containsAllIterable(Lists.immutable.of(4, 4, 5)));
        assertFalse(iterable3.containsAllIterable(Lists.immutable.of(3, 2, 1, 0)));
        assertTrue(iterable3.containsAllIterable(Lists.immutable.empty()));

        RichIterable<Integer> iterable2 = this.newWith(2, 1);

        assertTrue(iterable2.containsAllIterable(Lists.immutable.of(2)));
        assertTrue(iterable2.containsAllIterable(Lists.immutable.of(2, 1)));
        assertTrue(iterable2.containsAllIterable(Lists.immutable.of(2, 2, 2)));
        assertTrue(iterable2.containsAllIterable(Lists.immutable.of(2, 2, 2, 1, 1)));
        assertFalse(iterable2.containsAllIterable(Lists.immutable.of(4)));
        assertFalse(iterable2.containsAllIterable(Lists.immutable.of(4, 4, 5)));
        assertFalse(iterable2.containsAllIterable(Lists.immutable.of(2, 1, 0)));
        assertTrue(iterable2.containsAllIterable(Lists.immutable.empty()));

        RichIterable<Integer> iterable1 = this.newWith(1);

        assertTrue(iterable1.containsAllIterable(Lists.immutable.of(1)));
        assertTrue(iterable1.containsAllIterable(Lists.immutable.of(1, 1, 1)));
        assertFalse(iterable1.containsAllIterable(Lists.immutable.of(4)));
        assertFalse(iterable1.containsAllIterable(Lists.immutable.of(4, 4, 5)));
        assertFalse(iterable1.containsAllIterable(Lists.immutable.of(2, 1, 0)));
        assertTrue(iterable1.containsAllIterable(Lists.immutable.empty()));

        RichIterable<Integer> iterable0 = this.newWith();

        assertFalse(iterable0.containsAllIterable(Lists.immutable.of(1)));
        assertFalse(iterable0.containsAllIterable(Lists.immutable.of(1, 1, 1)));
        assertFalse(iterable0.containsAllIterable(Lists.immutable.of(4, 4, 5)));
        assertTrue(iterable0.containsAllIterable(Lists.immutable.empty()));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(3, 3, 3, 2, 2, 1);

        assertTrue(iterableDup.containsAllIterable(Lists.immutable.of(3)));
        assertTrue(iterableDup.containsAllIterable(Lists.immutable.of(3, 2, 1)));
        assertTrue(iterableDup.containsAllIterable(Lists.immutable.of(3, 3, 3)));
        assertTrue(iterableDup.containsAllIterable(Lists.immutable.of(3, 3, 3, 3, 2, 2, 2, 1, 1)));
        assertFalse(iterableDup.containsAllIterable(Lists.immutable.of(4)));
        assertFalse(iterableDup.containsAllIterable(Lists.immutable.of(4, 4, 5)));
        assertFalse(iterableDup.containsAllIterable(Lists.immutable.of(3, 2, 1, 0)));
        assertTrue(iterableDup.containsAllIterable(Lists.immutable.empty()));
    }

    @Test
    default void RichIterable_containsAll()
    {
        RichIterable<Integer> iterable3 = this.newWith(3, 2, 1);

        assertTrue(iterable3.containsAll(Lists.mutable.of(3)));
        assertTrue(iterable3.containsAll(Lists.mutable.of(3, 2, 1)));
        assertTrue(iterable3.containsAll(Lists.mutable.of(3, 3, 3)));
        assertTrue(iterable3.containsAll(Lists.mutable.of(3, 3, 3, 3, 2, 2, 2, 1, 1)));
        assertFalse(iterable3.containsAll(Lists.mutable.of(4)));
        assertFalse(iterable3.containsAll(Lists.mutable.of(4, 4, 5)));
        assertFalse(iterable3.containsAll(Lists.mutable.of(3, 2, 1, 0)));
        assertTrue(iterable3.containsAll(Lists.mutable.empty()));

        RichIterable<Integer> iterable2 = this.newWith(2, 1);

        assertTrue(iterable2.containsAll(Lists.mutable.of(2)));
        assertTrue(iterable2.containsAll(Lists.mutable.of(2, 1)));
        assertTrue(iterable2.containsAll(Lists.mutable.of(2, 2, 2)));
        assertTrue(iterable2.containsAll(Lists.mutable.of(2, 2, 2, 1, 1)));
        assertFalse(iterable2.containsAll(Lists.mutable.of(4)));
        assertFalse(iterable2.containsAll(Lists.mutable.of(4, 4, 5)));
        assertFalse(iterable2.containsAll(Lists.mutable.of(2, 1, 0)));
        assertTrue(iterable2.containsAll(Lists.mutable.empty()));

        RichIterable<Integer> iterable1 = this.newWith(1);

        assertTrue(iterable1.containsAll(Lists.mutable.of(1)));
        assertTrue(iterable1.containsAll(Lists.mutable.of(1, 1, 1)));
        assertFalse(iterable1.containsAll(Lists.mutable.of(4)));
        assertFalse(iterable1.containsAll(Lists.mutable.of(4, 4, 5)));
        assertFalse(iterable1.containsAll(Lists.mutable.of(2, 1, 0)));
        assertTrue(iterable1.containsAll(Lists.mutable.empty()));

        RichIterable<Integer> iterable0 = this.newWith();

        assertFalse(iterable0.containsAll(Lists.mutable.of(1)));
        assertFalse(iterable0.containsAll(Lists.mutable.of(1, 1, 1)));
        assertFalse(iterable0.containsAll(Lists.mutable.of(4, 4, 5)));
        assertTrue(iterable0.containsAll(Lists.mutable.empty()));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(3, 3, 3, 2, 2, 1);

        assertTrue(iterableDup.containsAll(Lists.mutable.of(3)));
        assertTrue(iterableDup.containsAll(Lists.mutable.of(3, 2, 1)));
        assertTrue(iterableDup.containsAll(Lists.mutable.of(3, 3, 3)));
        assertTrue(iterableDup.containsAll(Lists.mutable.of(3, 3, 3, 3, 2, 2, 2, 1, 1)));
        assertFalse(iterableDup.containsAll(Lists.mutable.of(4)));
        assertFalse(iterableDup.containsAll(Lists.mutable.of(4, 4, 5)));
        assertFalse(iterableDup.containsAll(Lists.mutable.of(3, 2, 1, 0)));
        assertTrue(iterableDup.containsAll(Lists.mutable.empty()));
    }

    @Test
    default void RichIterable_containsAllArguments()
    {
        RichIterable<Integer> iterable3 = this.newWith(3, 2, 1);

        assertTrue(iterable3.containsAllArguments(3));
        assertTrue(iterable3.containsAllArguments(3, 2, 1));
        assertTrue(iterable3.containsAllArguments(3, 3, 3));
        assertTrue(iterable3.containsAllArguments(3, 3, 3, 3, 2, 2, 2, 1, 1));
        assertFalse(iterable3.containsAllArguments(4));
        assertFalse(iterable3.containsAllArguments(4, 4, 5));
        assertFalse(iterable3.containsAllArguments(3, 2, 1, 0));
        assertTrue(iterable3.containsAllArguments());

        RichIterable<Integer> iterable2 = this.newWith(2, 1);

        assertTrue(iterable2.containsAllArguments(2));
        assertTrue(iterable2.containsAllArguments(2, 1));
        assertTrue(iterable2.containsAllArguments(2, 2, 2));
        assertTrue(iterable2.containsAllArguments(2, 2, 2, 1, 1));
        assertFalse(iterable2.containsAllArguments(4));
        assertFalse(iterable2.containsAllArguments(4, 4, 5));
        assertFalse(iterable2.containsAllArguments(2, 1, 0));
        assertTrue(iterable2.containsAllArguments());

        RichIterable<Integer> iterable1 = this.newWith(1);

        assertTrue(iterable1.containsAllArguments(1));
        assertTrue(iterable1.containsAllArguments(1, 1, 1));
        assertFalse(iterable1.containsAllArguments(4));
        assertFalse(iterable1.containsAllArguments(4, 4, 5));
        assertFalse(iterable1.containsAllArguments(2, 1, 0));
        assertTrue(iterable1.containsAllArguments());

        RichIterable<Integer> iterable0 = this.newWith();

        assertFalse(iterable0.containsAllArguments(1));
        assertFalse(iterable0.containsAllArguments(1, 1, 1));
        assertFalse(iterable0.containsAllArguments(4, 4, 5));
        assertTrue(iterable0.containsAllArguments());

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(3, 3, 3, 2, 2, 1);

        assertTrue(iterableDup.containsAllArguments(3));
        assertTrue(iterableDup.containsAllArguments(3, 2, 1));
        assertTrue(iterableDup.containsAllArguments(3, 3, 3));
        assertTrue(iterableDup.containsAllArguments(3, 3, 3, 3, 2, 2, 2, 1, 1));
        assertFalse(iterableDup.containsAllArguments(4));
        assertFalse(iterableDup.containsAllArguments(4, 4, 5));
        assertFalse(iterableDup.containsAllArguments(3, 2, 1, 0));
        assertTrue(iterableDup.containsAllArguments());
    }

    @Test
    default void RichIterable_containsAny()
    {
        RichIterable<Integer> iterable3 = this.newWith(3, 2, 1);

        assertTrue(iterable3.containsAny(Lists.mutable.of(3)));
        assertTrue(iterable3.containsAny(Lists.mutable.of(3, 2, 1)));
        assertTrue(iterable3.containsAny(Lists.mutable.of(3, 3, 3)));
        assertTrue(iterable3.containsAny(Lists.mutable.of(3, 3, 3, 3, 2, 2, 2, 1, 1)));
        assertFalse(iterable3.containsAny(Lists.mutable.of(4)));
        assertFalse(iterable3.containsAny(Lists.mutable.of(4, 4, 5)));
        assertTrue(iterable3.containsAny(Lists.mutable.of(3, 2, 1, 0)));
        assertFalse(iterable3.containsAny(Lists.mutable.empty()));

        RichIterable<Integer> iterable2 = this.newWith(2, 1);

        assertTrue(iterable2.containsAny(Lists.mutable.of(2)));
        assertTrue(iterable2.containsAny(Lists.mutable.of(2, 1)));
        assertTrue(iterable2.containsAny(Lists.mutable.of(2, 2, 2)));
        assertTrue(iterable2.containsAny(Lists.mutable.of(2, 2, 2, 1, 1)));
        assertFalse(iterable2.containsAny(Lists.mutable.of(4)));
        assertFalse(iterable2.containsAny(Lists.mutable.of(4, 4, 5)));
        assertTrue(iterable2.containsAny(Lists.mutable.of(2, 1, 0)));
        assertFalse(iterable2.containsAny(Lists.mutable.empty()));

        RichIterable<Integer> iterable1 = this.newWith(1);

        assertTrue(iterable1.containsAny(Lists.mutable.of(1)));
        assertTrue(iterable1.containsAny(Lists.mutable.of(1, 1, 1)));
        assertFalse(iterable1.containsAny(Lists.mutable.of(4)));
        assertFalse(iterable1.containsAny(Lists.mutable.of(4, 4, 5)));
        assertTrue(iterable1.containsAny(Lists.mutable.of(2, 1, 0)));
        assertFalse(iterable1.containsAny(Lists.mutable.empty()));

        RichIterable<Integer> iterable0 = this.newWith();

        assertFalse(iterable0.containsAll(Lists.mutable.of(1)));
        assertFalse(iterable0.containsAll(Lists.mutable.of(1, 1, 1)));
        assertFalse(iterable0.containsAll(Lists.mutable.of(4, 4, 5)));
        assertTrue(iterable0.containsAll(Lists.mutable.empty()));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(3, 3, 3, 2, 2, 1);

        assertTrue(iterableDup.containsAny(Lists.mutable.of(3)));
        assertTrue(iterableDup.containsAny(Lists.mutable.of(3, 2, 1)));
        assertTrue(iterableDup.containsAny(Lists.mutable.of(3, 3, 3)));
        assertTrue(iterableDup.containsAny(Lists.mutable.of(3, 3, 3, 3, 2, 2, 2, 1, 1)));
        assertFalse(iterableDup.containsAny(Lists.mutable.of(4)));
        assertFalse(iterableDup.containsAny(Lists.mutable.of(4, 4, 5)));
        assertTrue(iterableDup.containsAny(Lists.mutable.of(3, 2, 1, 0)));
        assertFalse(iterableDup.containsAny(Lists.mutable.empty()));
    }

    @Test
    default void RichIterable_containsNone()
    {
        RichIterable<Integer> iterable3 = this.newWith(3, 2, 1);

        assertFalse(iterable3.containsNone(Lists.mutable.of(3)));
        assertFalse(iterable3.containsNone(Lists.mutable.of(3, 2, 1)));
        assertFalse(iterable3.containsNone(Lists.mutable.of(3, 3, 3)));
        assertFalse(iterable3.containsNone(Lists.mutable.of(3, 3, 3, 3, 2, 2, 2, 1, 1)));
        assertTrue(iterable3.containsNone(Lists.mutable.of(4)));
        assertTrue(iterable3.containsNone(Lists.mutable.of(4, 4, 5)));
        assertFalse(iterable3.containsNone(Lists.mutable.of(3, 2, 1, 0)));
        assertTrue(iterable3.containsNone(Lists.mutable.empty()));

        RichIterable<Integer> iterable2 = this.newWith(2, 1);

        assertFalse(iterable2.containsNone(Lists.mutable.of(2)));
        assertFalse(iterable2.containsNone(Lists.mutable.of(2, 1)));
        assertFalse(iterable2.containsNone(Lists.mutable.of(2, 2, 2)));
        assertFalse(iterable2.containsNone(Lists.mutable.of(2, 2, 2, 1, 1)));
        assertTrue(iterable2.containsNone(Lists.mutable.of(4)));
        assertTrue(iterable2.containsNone(Lists.mutable.of(4, 4, 5)));
        assertFalse(iterable2.containsNone(Lists.mutable.of(2, 1, 0)));
        assertTrue(iterable2.containsNone(Lists.mutable.empty()));

        RichIterable<Integer> iterable1 = this.newWith(1);

        assertFalse(iterable1.containsNone(Lists.mutable.of(1)));
        assertFalse(iterable1.containsNone(Lists.mutable.of(1, 1, 1)));
        assertTrue(iterable1.containsNone(Lists.mutable.of(4)));
        assertTrue(iterable1.containsNone(Lists.mutable.of(4, 4, 5)));
        assertFalse(iterable1.containsNone(Lists.mutable.of(2, 1, 0)));
        assertTrue(iterable1.containsNone(Lists.mutable.empty()));

        RichIterable<Integer> iterable0 = this.newWith();

        assertTrue(iterable0.containsNone(Lists.mutable.of(1)));
        assertTrue(iterable0.containsNone(Lists.mutable.of(1, 1, 1)));
        assertTrue(iterable0.containsNone(Lists.mutable.of(4, 4, 5)));
        assertTrue(iterable0.containsNone(Lists.mutable.empty()));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableDup = this.newWith(3, 3, 3, 2, 2, 1);

        assertFalse(iterableDup.containsNone(Lists.mutable.of(3)));
        assertFalse(iterableDup.containsNone(Lists.mutable.of(3, 2, 1)));
        assertFalse(iterableDup.containsNone(Lists.mutable.of(3, 3, 3)));
        assertFalse(iterableDup.containsNone(Lists.mutable.of(3, 3, 3, 3, 2, 2, 2, 1, 1)));
        assertTrue(iterableDup.containsNone(Lists.mutable.of(4)));
        assertTrue(iterableDup.containsNone(Lists.mutable.of(4, 4, 5)));
        assertFalse(iterableDup.containsNone(Lists.mutable.of(3, 2, 1, 0)));
        assertTrue(iterableDup.containsNone(Lists.mutable.empty()));
    }

    @Test
    default void RichIterable_anySatisfy_allSatisfy_noneSatisfy()
    {
        {
            RichIterable<Integer> iterable = this.newWith(3, 2, 1);

            assertTrue(iterable.anySatisfy(Predicates.greaterThan(0)));
            assertTrue(iterable.anySatisfy(Predicates.greaterThan(1)));
            assertTrue(iterable.anySatisfy(Predicates.greaterThan(2)));
            assertFalse(iterable.anySatisfy(Predicates.greaterThan(3)));

            assertTrue(iterable.anySatisfyWith(Predicates2.greaterThan(), 0));
            assertTrue(iterable.anySatisfyWith(Predicates2.greaterThan(), 1));
            assertTrue(iterable.anySatisfyWith(Predicates2.greaterThan(), 2));
            assertFalse(iterable.anySatisfyWith(Predicates2.greaterThan(), 3));

            assertTrue(iterable.allSatisfy(Predicates.greaterThan(0)));
            assertFalse(iterable.allSatisfy(Predicates.greaterThan(1)));
            assertFalse(iterable.allSatisfy(Predicates.greaterThan(2)));
            assertFalse(iterable.allSatisfy(Predicates.greaterThan(3)));

            assertTrue(iterable.allSatisfyWith(Predicates2.greaterThan(), 0));
            assertFalse(iterable.allSatisfyWith(Predicates2.greaterThan(), 1));
            assertFalse(iterable.allSatisfyWith(Predicates2.greaterThan(), 2));
            assertFalse(iterable.allSatisfyWith(Predicates2.greaterThan(), 3));

            assertFalse(iterable.noneSatisfy(Predicates.greaterThan(0)));
            assertFalse(iterable.noneSatisfy(Predicates.greaterThan(1)));
            assertFalse(iterable.noneSatisfy(Predicates.greaterThan(2)));
            assertTrue(iterable.noneSatisfy(Predicates.greaterThan(3)));

            assertFalse(iterable.noneSatisfyWith(Predicates2.greaterThan(), 0));
            assertFalse(iterable.noneSatisfyWith(Predicates2.greaterThan(), 1));
            assertFalse(iterable.noneSatisfyWith(Predicates2.greaterThan(), 2));
            assertTrue(iterable.noneSatisfyWith(Predicates2.greaterThan(), 3));
        }

        {
            RichIterable<Integer> iterable = this.newWith(2, 1);

            assertTrue(iterable.anySatisfy(Predicates.greaterThan(0)));
            assertTrue(iterable.anySatisfy(Predicates.greaterThan(1)));
            assertFalse(iterable.anySatisfy(Predicates.greaterThan(2)));

            assertTrue(iterable.anySatisfyWith(Predicates2.greaterThan(), 0));
            assertTrue(iterable.anySatisfyWith(Predicates2.greaterThan(), 1));
            assertFalse(iterable.anySatisfyWith(Predicates2.greaterThan(), 2));

            assertTrue(iterable.allSatisfy(Predicates.greaterThan(0)));
            assertFalse(iterable.allSatisfy(Predicates.greaterThan(1)));
            assertFalse(iterable.allSatisfy(Predicates.greaterThan(2)));

            assertTrue(iterable.allSatisfyWith(Predicates2.greaterThan(), 0));
            assertFalse(iterable.allSatisfyWith(Predicates2.greaterThan(), 1));
            assertFalse(iterable.allSatisfyWith(Predicates2.greaterThan(), 2));

            assertFalse(iterable.noneSatisfy(Predicates.greaterThan(0)));
            assertFalse(iterable.noneSatisfy(Predicates.greaterThan(1)));
            assertTrue(iterable.noneSatisfy(Predicates.greaterThan(2)));

            assertFalse(iterable.noneSatisfyWith(Predicates2.greaterThan(), 0));
            assertFalse(iterable.noneSatisfyWith(Predicates2.greaterThan(), 1));
            assertTrue(iterable.noneSatisfyWith(Predicates2.greaterThan(), 2));
        }

        {
            RichIterable<Integer> iterable = this.newWith(1);

            assertTrue(iterable.anySatisfy(Predicates.greaterThan(0)));
            assertFalse(iterable.anySatisfy(Predicates.greaterThan(1)));

            assertTrue(iterable.anySatisfyWith(Predicates2.greaterThan(), 0));
            assertFalse(iterable.anySatisfyWith(Predicates2.greaterThan(), 1));

            assertTrue(iterable.allSatisfy(Predicates.greaterThan(0)));
            assertFalse(iterable.allSatisfy(Predicates.greaterThan(1)));

            assertTrue(iterable.allSatisfyWith(Predicates2.greaterThan(), 0));
            assertFalse(iterable.allSatisfyWith(Predicates2.greaterThan(), 1));

            assertFalse(iterable.noneSatisfy(Predicates.greaterThan(0)));
            assertTrue(iterable.noneSatisfy(Predicates.greaterThan(1)));

            assertFalse(iterable.noneSatisfyWith(Predicates2.greaterThan(), 0));
            assertTrue(iterable.noneSatisfyWith(Predicates2.greaterThan(), 1));
        }

        RichIterable<Integer> iterable = this.newWith();

        Predicate<Integer> throwPredicate = each -> {
            throw new AssertionError();
        };
        Predicate2<Integer, Object> throwPredicate2 = (each, parameter) -> {
            throw new AssertionError();
        };

        assertFalse(iterable.anySatisfy(throwPredicate));
        assertFalse(iterable.anySatisfyWith(throwPredicate2, null));

        assertTrue(iterable.allSatisfy(throwPredicate));
        assertTrue(iterable.allSatisfyWith(throwPredicate2, null));

        assertTrue(iterable.noneSatisfy(throwPredicate));
        assertTrue(iterable.noneSatisfyWith(throwPredicate2, null));
    }
}
