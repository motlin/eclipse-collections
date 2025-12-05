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

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.ByteIterable;
import org.eclipse.collections.api.CharIterable;
import org.eclipse.collections.api.DoubleIterable;
import org.eclipse.collections.api.FloatIterable;
import org.eclipse.collections.api.IntIterable;
import org.eclipse.collections.api.LongIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.ShortIterable;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.collection.primitive.MutableByteCollection;
import org.eclipse.collections.api.collection.primitive.MutableCharCollection;
import org.eclipse.collections.api.collection.primitive.MutableDoubleCollection;
import org.eclipse.collections.api.collection.primitive.MutableFloatCollection;
import org.eclipse.collections.api.collection.primitive.MutableIntCollection;
import org.eclipse.collections.api.collection.primitive.MutableLongCollection;
import org.eclipse.collections.api.collection.primitive.MutableShortCollection;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.fail;

public interface InternalIterableTestCase extends IterableTestCase
{
    @Override
    <T> RichIterable<T> newWith(T... elements);

    <T> RichIterable<T> getExpectedFiltered(T... elements);

    <T> RichIterable<T> getExpectedTransformed(T... elements);

    <T> MutableCollection<T> newMutableForFilter(T... elements);

    <T> MutableCollection<T> newMutableForTransform(T... elements);

    MutableBooleanCollection newBooleanForTransform(boolean... elements);

    MutableByteCollection newByteForTransform(byte... elements);

    MutableCharCollection newCharForTransform(char... elements);

    MutableDoubleCollection newDoubleForTransform(double... elements);

    MutableFloatCollection newFloatForTransform(float... elements);

    MutableIntCollection newIntForTransform(int... elements);

    MutableLongCollection newLongForTransform(long... elements);

    MutableShortCollection newShortForTransform(short... elements);

    default BooleanIterable getExpectedBoolean(boolean... elements)
    {
        return this.newBooleanForTransform(elements);
    }

    default ByteIterable getExpectedByte(byte... elements)
    {
        return this.newByteForTransform(elements);
    }

    default CharIterable getExpectedChar(char... elements)
    {
        return this.newCharForTransform(elements);
    }

    default DoubleIterable getExpectedDouble(double... elements)
    {
        return this.newDoubleForTransform(elements);
    }

    default FloatIterable getExpectedFloat(float... elements)
    {
        return this.newFloatForTransform(elements);
    }

    default IntIterable getExpectedInt(int... elements)
    {
        return this.newIntForTransform(elements);
    }

    default LongIterable getExpectedLong(long... elements)
    {
        return this.newLongForTransform(elements);
    }

    default ShortIterable getExpectedShort(short... elements)
    {
        return this.newShortForTransform(elements);
    }

    @Test
    default void InternalIterable_forEach()
    {
        {
            RichIterable<Integer> iterable = this.newWith(3, 2, 1);
            MutableCollection<Integer> result = this.newMutableForFilter();
            iterable.forEach(Procedures.cast(i -> result.add(i + 10)));
            assertIterablesEqual(this.newMutableForFilter(13, 12, 11), result);
        }

        {
            RichIterable<Integer> iterable = this.newWith(2, 1);
            MutableCollection<Integer> result = this.newMutableForFilter();
            iterable.forEach(Procedures.cast(i -> result.add(i + 10)));
            assertIterablesEqual(this.newMutableForFilter(12, 11), result);
        }

        RichIterable<Integer> iterable = this.newWith(1);
        MutableCollection<Integer> result = this.newMutableForFilter();
        iterable.forEach(Procedures.cast(i -> result.add(i + 10)));
        assertIterablesEqual(this.newMutableForFilter(11), result);

        this.newWith().forEach(Procedures.cast(each -> fail()));

        if (!this.allowsDuplicates())
        {
            return;
        }

        {
            RichIterable<Integer> iterable2 = this.newWith(3, 3, 3, 2, 2, 1);
            MutableCollection<Integer> result2 = this.newMutableForFilter();
            iterable2.forEach(Procedures.cast(i -> result2.add(i + 10)));
            assertIterablesEqual(this.newMutableForFilter(13, 13, 13, 12, 12, 11), result2);
        }

        {
            RichIterable<Integer> iterable3 = this.newWith(2, 2, 1);
            MutableCollection<Integer> result3 = this.newMutableForFilter();
            iterable3.forEach(Procedures.cast(i -> result3.add(i + 10)));
            assertIterablesEqual(this.newMutableForFilter(12, 12, 11), result3);
        }
    }

    @Test
    default void InternalIterable_forEachWith()
    {
        RichIterable<Integer> iterable = this.newWith(3, 2, 1);
        MutableCollection<Integer> result = this.newMutableForFilter();
        iterable.forEachWith((argument1, argument2) -> result.add(argument1 + argument2), 10);
        assertIterablesEqual(this.newMutableForFilter(13, 12, 11), result);

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterable2 = this.newWith(3, 3, 3, 2, 2, 1);
        MutableCollection<Integer> result2 = this.newMutableForFilter();
        iterable2.forEachWith((argument1, argument2) -> result2.add(argument1 + argument2), 10);
        assertIterablesEqual(this.newMutableForFilter(13, 13, 13, 12, 12, 11), result2);
    }
}
