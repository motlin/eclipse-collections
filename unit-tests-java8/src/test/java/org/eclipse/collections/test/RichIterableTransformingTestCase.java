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
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.collection.primitive.MutableByteCollection;
import org.eclipse.collections.api.collection.primitive.MutableCharCollection;
import org.eclipse.collections.api.collection.primitive.MutableDoubleCollection;
import org.eclipse.collections.api.collection.primitive.MutableFloatCollection;
import org.eclipse.collections.api.collection.primitive.MutableIntCollection;
import org.eclipse.collections.api.collection.primitive.MutableLongCollection;
import org.eclipse.collections.api.collection.primitive.MutableShortCollection;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.eclipse.collections.impl.factory.primitive.ShortLists;
import org.eclipse.collections.impl.list.Interval;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.fail;

public interface RichIterableTransformingTestCase extends InternalIterableTestCase
{
    @Test
    default void RichIterable_collect()
    {
        RichIterable<Integer> iterable = this.newWith(13, 12, 11, 3, 2, 1);

        Integer[] expected = switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL -> new Integer[]{3, 2, 1, 3, 2, 1};
            case SORTED_NATURAL -> new Integer[]{1, 2, 3, 1, 2, 3};
        };

        assertIterablesEqual(this.getExpectedTransformed(expected), iterable.collect(i -> i % 10));

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            MutableCollection<Integer> target = this.newMutableForTransform();
            MutableCollection<Integer> result = iterable.collect(i -> i % 10, target);
            assertIterablesEqual(this.newMutableForTransform(expected), result);
            assertSame(target, result);
        }

        assertIterablesEqual(this.getExpectedTransformed(expected), iterable.collectWith((i, mod) -> i % mod, 10));

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            MutableCollection<Integer> target2 = this.newMutableForTransform();
            MutableCollection<Integer> result2 = iterable.collectWith((i, mod) -> i % mod, 10, target2);
            assertIterablesEqual(this.newMutableForTransform(expected), result2);
            assertSame(target2, result2);
        }

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableWithDuplicates = this.newWith(13, 13, 12, 12, 11, 11, 3, 3, 2, 2, 1, 1);

        Integer[] expectedWithDuplicates = switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL -> new Integer[]{3, 3, 2, 2, 1, 1, 3, 3, 2, 2, 1, 1};
            case SORTED_NATURAL -> new Integer[]{1, 1, 2, 2, 3, 3, 1, 1, 2, 2, 3, 3};
        };

        assertIterablesEqual(this.getExpectedTransformed(expectedWithDuplicates), iterableWithDuplicates.collect(i -> i % 10));

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            MutableCollection<Integer> targetWithDuplicates = this.newMutableForTransform();
            MutableCollection<Integer> resultWithDuplicates = iterableWithDuplicates.collect(i -> i % 10, targetWithDuplicates);
            assertIterablesEqual(this.newMutableForTransform(expectedWithDuplicates), resultWithDuplicates);
            assertSame(targetWithDuplicates, resultWithDuplicates);
        }

        assertIterablesEqual(this.getExpectedTransformed(expectedWithDuplicates), iterableWithDuplicates.collectWith((i, mod) -> i % mod, 10));

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            MutableCollection<Integer> targetWithDuplicates2 = this.newMutableForTransform();
            MutableCollection<Integer> resultWithDuplicates2 = iterableWithDuplicates.collectWith((i, mod) -> i % mod, 10, targetWithDuplicates2);
            assertIterablesEqual(this.newMutableForTransform(expectedWithDuplicates), resultWithDuplicates2);
            assertSame(targetWithDuplicates2, resultWithDuplicates2);
        }
    }

    @Test
    default void RichIterable_collectIf()
    {
        RichIterable<Integer> iterable = this.newWith(13, 12, 11, 3, 2, 1);

        Integer[] expected = switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL -> new Integer[]{3, 1, 3, 1};
            case SORTED_NATURAL -> new Integer[]{1, 3, 1, 3};
        };

        assertIterablesEqual(this.getExpectedTransformed(expected), iterable.collectIf(i -> i % 2 != 0, i -> i % 10));

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            MutableCollection<Integer> target = this.newMutableForTransform();
            MutableCollection<Integer> result = iterable.collectIf(i -> i % 2 != 0, i -> i % 10, target);
            assertIterablesEqual(this.newMutableForTransform(expected), result);
            assertSame(target, result);
        }

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterableWithDuplicates = this.newWith(13, 13, 12, 12, 11, 11, 3, 3, 2, 2, 1, 1);

        Integer[] expectedWithDuplicates = switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL -> new Integer[]{3, 3, 1, 1, 3, 3, 1, 1};
            case SORTED_NATURAL -> new Integer[]{1, 1, 3, 3, 1, 1, 3, 3};
        };

        assertIterablesEqual(this.getExpectedTransformed(expectedWithDuplicates), iterableWithDuplicates.collectIf(i -> i % 2 != 0, i -> i % 10));

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            MutableCollection<Integer> targetWithDuplicates = this.newMutableForTransform();
            MutableCollection<Integer> resultWithDuplicates = iterableWithDuplicates.collectIf(i -> i % 2 != 0, i -> i % 10, targetWithDuplicates);
            assertIterablesEqual(this.newMutableForTransform(expectedWithDuplicates), resultWithDuplicates);
            assertSame(targetWithDuplicates, resultWithDuplicates);
        }
    }

    @Test
    default void RichIterable_collectPrimitive()
    {
        assertIterablesEqual(
                this.getExpectedBoolean(false, true, false),
                this.newWith(3, 2, 1).collectBoolean(each -> each % 2 == 0));

        RichIterable<Integer> iterable = this.newWith(13, 12, 11, 3, 2, 1);

        switch (this.getOrderingType())
        {
            case UNORDERED ->
            {
                assertIterablesEqual(
                        this.getExpectedByte((byte) 3, (byte) 2, (byte) 1, (byte) 3, (byte) 2, (byte) 1),
                        iterable.collectByte(each -> (byte) (each % 10)));
                assertIterablesEqual(
                        this.getExpectedChar((char) 3, (char) 2, (char) 1, (char) 3, (char) 2, (char) 1),
                        iterable.collectChar(each -> (char) (each % 10)));
                assertIterablesEqual(
                        this.getExpectedDouble(3.0, 2.0, 1.0, 3.0, 2.0, 1.0),
                        iterable.collectDouble(each -> (double) (each % 10)));
                assertIterablesEqual(
                        this.getExpectedFloat(3.0f, 2.0f, 1.0f, 3.0f, 2.0f, 1.0f),
                        iterable.collectFloat(each -> (float) (each % 10)));
                assertIterablesEqual(
                        this.getExpectedInt(3, 2, 1, 3, 2, 1),
                        iterable.collectInt(each -> each % 10));
                assertIterablesEqual(
                        this.getExpectedLong(3, 2, 1, 3, 2, 1),
                        iterable.collectLong(each -> each % 10));
                assertIterablesEqual(
                        this.getExpectedShort((short) 3, (short) 2, (short) 1, (short) 3, (short) 2, (short) 1),
                        iterable.collectShort(each -> (short) (each % 10)));
            }
            case INSERTION_ORDER, SORTED_REVERSE_NATURAL ->
            {
                {
                    MutableBooleanCollection target = this.newBooleanForTransform();
                    MutableBooleanCollection result = this.newWith(3, 2, 1).collectBoolean(each -> each % 2 == 0, target);
                    assertIterablesEqual(this.newBooleanForTransform(false, true, false), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedByte((byte) 3, (byte) 2, (byte) 1, (byte) 3, (byte) 2, (byte) 1),
                        iterable.collectByte(each -> (byte) (each % 10)));

                {
                    MutableByteCollection target = this.newByteForTransform();
                    MutableByteCollection result = iterable.collectByte(each -> (byte) (each % 10), target);
                    assertIterablesEqual(this.newByteForTransform((byte) 3, (byte) 2, (byte) 1, (byte) 3, (byte) 2, (byte) 1), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedChar((char) 3, (char) 2, (char) 1, (char) 3, (char) 2, (char) 1),
                        iterable.collectChar(each -> (char) (each % 10)));

                {
                    MutableCharCollection target = this.newCharForTransform();
                    MutableCharCollection result = iterable.collectChar(each -> (char) (each % 10), target);
                    assertIterablesEqual(this.newCharForTransform((char) 3, (char) 2, (char) 1, (char) 3, (char) 2, (char) 1), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedDouble(3.0, 2.0, 1.0, 3.0, 2.0, 1.0),
                        iterable.collectDouble(each -> (double) (each % 10)));

                {
                    MutableDoubleCollection target = this.newDoubleForTransform();
                    MutableDoubleCollection result = iterable.collectDouble(each -> (double) (each % 10), target);
                    assertIterablesEqual(this.newDoubleForTransform(3.0, 2.0, 1.0, 3.0, 2.0, 1.0), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedFloat(3.0f, 2.0f, 1.0f, 3.0f, 2.0f, 1.0f),
                        iterable.collectFloat(each -> (float) (each % 10)));

                {
                    MutableFloatCollection target = this.newFloatForTransform();
                    MutableFloatCollection result = iterable.collectFloat(each -> (float) (each % 10), target);
                    assertIterablesEqual(this.newFloatForTransform(3.0f, 2.0f, 1.0f, 3.0f, 2.0f, 1.0f), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedInt(3, 2, 1, 3, 2, 1),
                        iterable.collectInt(each -> each % 10));

                {
                    MutableIntCollection target = this.newIntForTransform();
                    MutableIntCollection result = iterable.collectInt(each -> each % 10, target);
                    assertIterablesEqual(this.newIntForTransform(3, 2, 1, 3, 2, 1), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedLong(3, 2, 1, 3, 2, 1),
                        iterable.collectLong(each -> each % 10));

                {
                    MutableLongCollection target = this.newLongForTransform();
                    MutableLongCollection result = iterable.collectLong(each -> each % 10, target);
                    assertIterablesEqual(this.newLongForTransform(3, 2, 1, 3, 2, 1), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedShort((short) 3, (short) 2, (short) 1, (short) 3, (short) 2, (short) 1),
                        iterable.collectShort(each -> (short) (each % 10)));

                MutableShortCollection target = this.newShortForTransform();
                MutableShortCollection result = iterable.collectShort(each -> (short) (each % 10), target);
                assertIterablesEqual(this.newShortForTransform((short) 3, (short) 2, (short) 1, (short) 3, (short) 2, (short) 1), result);
                assertSame(target, result);
            }
            case SORTED_NATURAL ->
            {
                {
                    MutableBooleanCollection target = this.newBooleanForTransform();
                    MutableBooleanCollection result = this.newWith(3, 2, 1).collectBoolean(each -> each % 2 == 0, target);
                    assertIterablesEqual(this.newBooleanForTransform(false, true, false), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedByte((byte) 1, (byte) 2, (byte) 3, (byte) 1, (byte) 2, (byte) 3),
                        iterable.collectByte(each -> (byte) (each % 10)));

                {
                    MutableByteCollection target = this.newByteForTransform();
                    MutableByteCollection result = iterable.collectByte(each -> (byte) (each % 10), target);
                    assertIterablesEqual(this.newByteForTransform((byte) 1, (byte) 2, (byte) 3, (byte) 1, (byte) 2, (byte) 3), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedChar((char) 1, (char) 2, (char) 3, (char) 1, (char) 2, (char) 3),
                        iterable.collectChar(each -> (char) (each % 10)));

                {
                    MutableCharCollection target = this.newCharForTransform();
                    MutableCharCollection result = iterable.collectChar(each -> (char) (each % 10), target);
                    assertIterablesEqual(this.newCharForTransform((char) 1, (char) 2, (char) 3, (char) 1, (char) 2, (char) 3), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedDouble(1.0, 2.0, 3.0, 1.0, 2.0, 3.0),
                        iterable.collectDouble(each -> (double) (each % 10)));

                {
                    MutableDoubleCollection target = this.newDoubleForTransform();
                    MutableDoubleCollection result = iterable.collectDouble(each -> (double) (each % 10), target);
                    assertIterablesEqual(this.newDoubleForTransform(1.0, 2.0, 3.0, 1.0, 2.0, 3.0), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedFloat(1.0f, 2.0f, 3.0f, 1.0f, 2.0f, 3.0f),
                        iterable.collectFloat(each -> (float) (each % 10)));

                {
                    MutableFloatCollection target = this.newFloatForTransform();
                    MutableFloatCollection result = iterable.collectFloat(each -> (float) (each % 10), target);
                    assertIterablesEqual(this.newFloatForTransform(1.0f, 2.0f, 3.0f, 1.0f, 2.0f, 3.0f), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedInt(1, 2, 3, 1, 2, 3),
                        iterable.collectInt(each -> each % 10));

                {
                    MutableIntCollection target = this.newIntForTransform();
                    MutableIntCollection result = iterable.collectInt(each -> each % 10, target);
                    assertIterablesEqual(this.newIntForTransform(1, 2, 3, 1, 2, 3), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedLong(1, 2, 3, 1, 2, 3),
                        iterable.collectLong(each -> each % 10));

                {
                    MutableLongCollection target = this.newLongForTransform();
                    MutableLongCollection result = iterable.collectLong(each -> each % 10, target);
                    assertIterablesEqual(this.newLongForTransform(1, 2, 3, 1, 2, 3), result);
                    assertSame(target, result);
                }

                assertIterablesEqual(
                        this.getExpectedShort((short) 1, (short) 2, (short) 3, (short) 1, (short) 2, (short) 3),
                        iterable.collectShort(each -> (short) (each % 10)));

                MutableShortCollection target = this.newShortForTransform();
                MutableShortCollection result = iterable.collectShort(each -> (short) (each % 10), target);
                assertIterablesEqual(this.newShortForTransform((short) 1, (short) 2, (short) 3, (short) 1, (short) 2, (short) 3), result);
                assertSame(target, result);
            }
            default -> fail("Unexpected value: " + this.getOrderingType());
        }

        if (!this.allowsDuplicates())
        {
            return;
        }

        assertIterablesEqual(
                this.getExpectedBoolean(false, false, true, true, false, false),
                this.newWith(3, 3, 2, 2, 1, 1).collectBoolean(each -> each % 2 == 0));

        {
            MutableBooleanCollection targetDup = this.newBooleanForTransform();
            MutableBooleanCollection resultDup = this.newWith(3, 3, 2, 2, 1, 1).collectBoolean(each -> each % 2 == 0, targetDup);
            assertIterablesEqual(this.newBooleanForTransform(false, false, true, true, false, false), resultDup);
            assertSame(targetDup, resultDup);
        }

        RichIterable<Integer> iterableDup = this.newWith(13, 13, 12, 12, 11, 11, 3, 3, 2, 2, 1, 1);

        switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL ->
            {
                assertIterablesEqual(
                        this.getExpectedByte((byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 1, (byte) 1),
                        iterableDup.collectByte(each -> (byte) (each % 10)));

                {
                    MutableByteCollection targetDup = this.newByteForTransform();
                    MutableByteCollection resultDup = iterableDup.collectByte(each -> (byte) (each % 10), targetDup);
                    assertIterablesEqual(this.newByteForTransform((byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 1, (byte) 1), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedChar((char) 3, (char) 3, (char) 2, (char) 2, (char) 1, (char) 1, (char) 3, (char) 3, (char) 2, (char) 2, (char) 1, (char) 1),
                        iterableDup.collectChar(each -> (char) (each % 10)));

                {
                    MutableCharCollection targetDup = this.newCharForTransform();
                    MutableCharCollection resultDup = iterableDup.collectChar(each -> (char) (each % 10), targetDup);
                    assertIterablesEqual(this.newCharForTransform((char) 3, (char) 3, (char) 2, (char) 2, (char) 1, (char) 1, (char) 3, (char) 3, (char) 2, (char) 2, (char) 1, (char) 1), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedDouble(3.0, 3.0, 2.0, 2.0, 1.0, 1.0, 3.0, 3.0, 2.0, 2.0, 1.0, 1.0),
                        iterableDup.collectDouble(each -> (double) (each % 10)));

                {
                    MutableDoubleCollection targetDup = this.newDoubleForTransform();
                    MutableDoubleCollection resultDup = iterableDup.collectDouble(each -> (double) (each % 10), targetDup);
                    assertIterablesEqual(this.newDoubleForTransform(3.0, 3.0, 2.0, 2.0, 1.0, 1.0, 3.0, 3.0, 2.0, 2.0, 1.0, 1.0), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedFloat(3.0f, 3.0f, 2.0f, 2.0f, 1.0f, 1.0f, 3.0f, 3.0f, 2.0f, 2.0f, 1.0f, 1.0f),
                        iterableDup.collectFloat(each -> (float) (each % 10)));

                {
                    MutableFloatCollection targetDup = this.newFloatForTransform();
                    MutableFloatCollection resultDup = iterableDup.collectFloat(each -> (float) (each % 10), targetDup);
                    assertIterablesEqual(this.newFloatForTransform(3.0f, 3.0f, 2.0f, 2.0f, 1.0f, 1.0f, 3.0f, 3.0f, 2.0f, 2.0f, 1.0f, 1.0f), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedInt(3, 3, 2, 2, 1, 1, 3, 3, 2, 2, 1, 1),
                        iterableDup.collectInt(each -> each % 10));

                {
                    MutableIntCollection targetDup = this.newIntForTransform();
                    MutableIntCollection resultDup = iterableDup.collectInt(each -> each % 10, targetDup);
                    assertIterablesEqual(this.newIntForTransform(3, 3, 2, 2, 1, 1, 3, 3, 2, 2, 1, 1), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedLong(3, 3, 2, 2, 1, 1, 3, 3, 2, 2, 1, 1),
                        iterableDup.collectLong(each -> each % 10));

                {
                    MutableLongCollection targetDup = this.newLongForTransform();
                    MutableLongCollection resultDup = iterableDup.collectLong(each -> each % 10, targetDup);
                    assertIterablesEqual(this.newLongForTransform(3, 3, 2, 2, 1, 1, 3, 3, 2, 2, 1, 1), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedShort((short) 3, (short) 3, (short) 2, (short) 2, (short) 1, (short) 1, (short) 3, (short) 3, (short) 2, (short) 2, (short) 1, (short) 1),
                        iterableDup.collectShort(each -> (short) (each % 10)));

                MutableShortCollection targetDup = this.newShortForTransform();
                MutableShortCollection resultDup = iterableDup.collectShort(each -> (short) (each % 10), targetDup);
                assertIterablesEqual(this.newShortForTransform((short) 3, (short) 3, (short) 2, (short) 2, (short) 1, (short) 1, (short) 3, (short) 3, (short) 2, (short) 2, (short) 1, (short) 1), resultDup);
                assertSame(targetDup, resultDup);
            }
            case SORTED_NATURAL ->
            {
                assertIterablesEqual(
                        this.getExpectedByte((byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 3, (byte) 3, (byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 3, (byte) 3),
                        iterableDup.collectByte(each -> (byte) (each % 10)));

                {
                    MutableByteCollection targetDup = this.newByteForTransform();
                    MutableByteCollection resultDup = iterableDup.collectByte(each -> (byte) (each % 10), targetDup);
                    assertIterablesEqual(this.newByteForTransform((byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 3, (byte) 3, (byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 3, (byte) 3), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedChar((char) 1, (char) 1, (char) 2, (char) 2, (char) 3, (char) 3, (char) 1, (char) 1, (char) 2, (char) 2, (char) 3, (char) 3),
                        iterableDup.collectChar(each -> (char) (each % 10)));

                {
                    MutableCharCollection targetDup = this.newCharForTransform();
                    MutableCharCollection resultDup = iterableDup.collectChar(each -> (char) (each % 10), targetDup);
                    assertIterablesEqual(this.newCharForTransform((char) 1, (char) 1, (char) 2, (char) 2, (char) 3, (char) 3, (char) 1, (char) 1, (char) 2, (char) 2, (char) 3, (char) 3), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedDouble(1.0, 1.0, 2.0, 2.0, 3.0, 3.0, 1.0, 1.0, 2.0, 2.0, 3.0, 3.0),
                        iterableDup.collectDouble(each -> (double) (each % 10)));

                {
                    MutableDoubleCollection targetDup = this.newDoubleForTransform();
                    MutableDoubleCollection resultDup = iterableDup.collectDouble(each -> (double) (each % 10), targetDup);
                    assertIterablesEqual(this.newDoubleForTransform(1.0, 1.0, 2.0, 2.0, 3.0, 3.0, 1.0, 1.0, 2.0, 2.0, 3.0, 3.0), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedFloat(1.0f, 1.0f, 2.0f, 2.0f, 3.0f, 3.0f, 1.0f, 1.0f, 2.0f, 2.0f, 3.0f, 3.0f),
                        iterableDup.collectFloat(each -> (float) (each % 10)));

                {
                    MutableFloatCollection targetDup = this.newFloatForTransform();
                    MutableFloatCollection resultDup = iterableDup.collectFloat(each -> (float) (each % 10), targetDup);
                    assertIterablesEqual(this.newFloatForTransform(1.0f, 1.0f, 2.0f, 2.0f, 3.0f, 3.0f, 1.0f, 1.0f, 2.0f, 2.0f, 3.0f, 3.0f), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedInt(1, 1, 2, 2, 3, 3, 1, 1, 2, 2, 3, 3),
                        iterableDup.collectInt(each -> each % 10));

                {
                    MutableIntCollection targetDup = this.newIntForTransform();
                    MutableIntCollection resultDup = iterableDup.collectInt(each -> each % 10, targetDup);
                    assertIterablesEqual(this.newIntForTransform(1, 1, 2, 2, 3, 3, 1, 1, 2, 2, 3, 3), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedLong(1, 1, 2, 2, 3, 3, 1, 1, 2, 2, 3, 3),
                        iterableDup.collectLong(each -> each % 10));

                {
                    MutableLongCollection targetDup = this.newLongForTransform();
                    MutableLongCollection resultDup = iterableDup.collectLong(each -> each % 10, targetDup);
                    assertIterablesEqual(this.newLongForTransform(1, 1, 2, 2, 3, 3, 1, 1, 2, 2, 3, 3), resultDup);
                    assertSame(targetDup, resultDup);
                }

                assertIterablesEqual(
                        this.getExpectedShort((short) 1, (short) 1, (short) 2, (short) 2, (short) 3, (short) 3, (short) 1, (short) 1, (short) 2, (short) 2, (short) 3, (short) 3),
                        iterableDup.collectShort(each -> (short) (each % 10)));

                MutableShortCollection targetDup = this.newShortForTransform();
                MutableShortCollection resultDup = iterableDup.collectShort(each -> (short) (each % 10), targetDup);
                assertIterablesEqual(this.newShortForTransform((short) 1, (short) 1, (short) 2, (short) 2, (short) 3, (short) 3, (short) 1, (short) 1, (short) 2, (short) 2, (short) 3, (short) 3), resultDup);
                assertSame(targetDup, resultDup);
            }
            default -> fail("Unexpected value: " + this.getOrderingType());
        }
    }

    @Test
    default void RichIterable_flatCollect()
    {
        Integer[] expectedFlatCollect = switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL -> new Integer[]{1, 2, 3, 1, 2, 1};
            case SORTED_NATURAL -> new Integer[]{1, 1, 2, 1, 2, 3};
        };

        Integer[] expectedFlatCollectWith = switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL -> new Integer[]{3, 2, 1, 2, 1, 1};
            case SORTED_NATURAL -> new Integer[]{1, 2, 1, 3, 2, 1};
        };

        assertIterablesEqual(this.getExpectedTransformed(expectedFlatCollect), this.newWith(3, 2, 1).flatCollect(Interval::oneTo));

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            assertIterablesEqual(this.newMutableForTransform(expectedFlatCollect), this.newWith(3, 2, 1).flatCollect(Interval::oneTo, this.newMutableForTransform()));
        }

        assertIterablesEqual(this.getExpectedTransformed(expectedFlatCollectWith), this.newWith(3, 2, 1).flatCollectWith(Interval::fromTo, 1));

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            assertIterablesEqual(this.newMutableForTransform(expectedFlatCollectWith), this.newWith(3, 2, 1).flatCollectWith(Interval::fromTo, 1, this.newMutableForTransform()));
        }

        if (!this.allowsDuplicates())
        {
            return;
        }

        Integer[] expectedFlatCollectDup = switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL -> new Integer[]{1, 2, 3, 1, 2, 1, 2, 1};
            case SORTED_NATURAL -> new Integer[]{1, 1, 2, 1, 2, 1, 2, 3};
        };

        assertIterablesEqual(this.getExpectedTransformed(expectedFlatCollectDup), this.newWith(3, 2, 2, 1).flatCollect(Interval::oneTo));

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            assertIterablesEqual(this.newMutableForTransform(expectedFlatCollectDup), this.newWith(3, 2, 2, 1).flatCollect(Interval::oneTo, this.newMutableForTransform()));
        }

        Integer[] expectedFlatCollectWithDup = switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL -> new Integer[]{3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 1, 2, 3, 4, 5};
            case SORTED_NATURAL -> new Integer[]{1, 2, 3, 4, 5, 2, 3, 4, 5, 2, 3, 4, 5, 3, 4, 5};
        };

        assertIterablesEqual(this.getExpectedTransformed(expectedFlatCollectWithDup), this.newWith(3, 2, 2, 1).flatCollectWith(Interval::fromTo, 5));

        Integer[] expectedFlatCollectWithDup2 = switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL -> new Integer[]{3, 2, 1, 2, 1, 2, 1, 1};
            case SORTED_NATURAL -> new Integer[]{1, 2, 1, 2, 1, 3, 2, 1};
        };

        if (this.getOrderingType() != OrderingType.UNORDERED)
        {
            assertIterablesEqual(this.newMutableForTransform(expectedFlatCollectWithDup2), this.newWith(3, 2, 2, 1).flatCollectWith(Interval::fromTo, 1, this.newMutableForTransform()));
        }
    }

    @Test
    default void RichIterable_flatCollect_primitive()
    {
        {
            MutableBooleanCollection target = this.newBooleanForTransform();
            MutableBooleanCollection result = this
                    .newWith(3, 2, 1)
                    .flatCollectBoolean(each -> BooleanLists.immutable.with(each % 2 == 0, each % 2 == 0), target);
            assertIterablesEqual(this.newBooleanForTransform(false, false, true, true, false, false), result);
            assertSame(target, result);
        }

        RichIterable<Integer> iterable = this.newWith(13, 12, 11, 3, 2, 1);

        switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL ->
            {
                {
                    MutableByteCollection target = this.newByteForTransform();
                    MutableByteCollection result = iterable.flatCollectByte(
                            each -> ByteLists.immutable.with((byte) (each % 10), (byte) (each % 10)),
                            target);
                    assertIterablesEqual(
                            this.newByteForTransform((byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 1, (byte) 1),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableCharCollection target = this.newCharForTransform();
                    MutableCharCollection result = iterable.flatCollectChar(
                            each -> CharLists.immutable.with((char) (each % 10), (char) (each % 10)),
                            target);
                    assertIterablesEqual(
                            this.newCharForTransform((char) 3, (char) 3, (char) 2, (char) 2, (char) 1, (char) 1, (char) 3, (char) 3, (char) 2, (char) 2, (char) 1, (char) 1),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableDoubleCollection target = this.newDoubleForTransform();
                    MutableDoubleCollection result = iterable.flatCollectDouble(each -> DoubleLists.immutable.with(
                            (double) (each % 10),
                            (double) (each % 10)), target);
                    assertIterablesEqual(
                            this.newDoubleForTransform(3.0, 3.0, 2.0, 2.0, 1.0, 1.0, 3.0, 3.0, 2.0, 2.0, 1.0, 1.0),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableFloatCollection target = this.newFloatForTransform();
                    MutableFloatCollection result = iterable.flatCollectFloat(each -> FloatLists.immutable.with(
                            (float) (each % 10),
                            (float) (each % 10)), target);
                    assertIterablesEqual(
                            this.newFloatForTransform(3.0f, 3.0f, 2.0f, 2.0f, 1.0f, 1.0f, 3.0f, 3.0f, 2.0f, 2.0f, 1.0f, 1.0f),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableIntCollection target = this.newIntForTransform();
                    MutableIntCollection result =
                            iterable.flatCollectInt(each -> IntLists.immutable.with(each % 10, each % 10), target);
                    assertIterablesEqual(
                            this.newIntForTransform(3, 3, 2, 2, 1, 1, 3, 3, 2, 2, 1, 1),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableLongCollection target = this.newLongForTransform();
                    MutableLongCollection result =
                            iterable.flatCollectLong(each -> LongLists.immutable.with(each % 10, each % 10), target);
                    assertIterablesEqual(
                            this.newLongForTransform(3, 3, 2, 2, 1, 1, 3, 3, 2, 2, 1, 1),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableShortCollection target = this.newShortForTransform();
                    MutableShortCollection result = iterable.flatCollectShort(each -> ShortLists.immutable.with(
                            (short) (each % 10),
                            (short) (each % 10)), target);
                    assertIterablesEqual(
                            this.newShortForTransform((short) 3, (short) 3, (short) 2, (short) 2, (short) 1, (short) 1, (short) 3, (short) 3, (short) 2, (short) 2, (short) 1, (short) 1),
                            result);
                    assertSame(target, result);
                }
            }
            case SORTED_NATURAL ->
            {
                {
                    MutableByteCollection target = this.newByteForTransform();
                    MutableByteCollection result = iterable.flatCollectByte(
                            each -> ByteLists.immutable.with((byte) (each % 10), (byte) (each % 10)),
                            target);
                    assertIterablesEqual(
                            this.newByteForTransform((byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 3, (byte) 3, (byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 3, (byte) 3),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableCharCollection target = this.newCharForTransform();
                    MutableCharCollection result = iterable.flatCollectChar(
                            each -> CharLists.immutable.with((char) (each % 10), (char) (each % 10)),
                            target);
                    assertIterablesEqual(
                            this.newCharForTransform((char) 1, (char) 1, (char) 2, (char) 2, (char) 3, (char) 3, (char) 1, (char) 1, (char) 2, (char) 2, (char) 3, (char) 3),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableDoubleCollection target = this.newDoubleForTransform();
                    MutableDoubleCollection result = iterable.flatCollectDouble(each -> DoubleLists.immutable.with(
                            (double) (each % 10),
                            (double) (each % 10)), target);
                    assertIterablesEqual(
                            this.newDoubleForTransform(1.0, 1.0, 2.0, 2.0, 3.0, 3.0, 1.0, 1.0, 2.0, 2.0, 3.0, 3.0),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableFloatCollection target = this.newFloatForTransform();
                    MutableFloatCollection result = iterable.flatCollectFloat(each -> FloatLists.immutable.with(
                            (float) (each % 10),
                            (float) (each % 10)), target);
                    assertIterablesEqual(
                            this.newFloatForTransform(1.0f, 1.0f, 2.0f, 2.0f, 3.0f, 3.0f, 1.0f, 1.0f, 2.0f, 2.0f, 3.0f, 3.0f),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableIntCollection target = this.newIntForTransform();
                    MutableIntCollection result =
                            iterable.flatCollectInt(each -> IntLists.immutable.with(each % 10, each % 10), target);
                    assertIterablesEqual(
                            this.newIntForTransform(1, 1, 2, 2, 3, 3, 1, 1, 2, 2, 3, 3),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableLongCollection target = this.newLongForTransform();
                    MutableLongCollection result =
                            iterable.flatCollectLong(each -> LongLists.immutable.with(each % 10, each % 10), target);
                    assertIterablesEqual(
                            this.newLongForTransform(1, 1, 2, 2, 3, 3, 1, 1, 2, 2, 3, 3),
                            result);
                    assertSame(target, result);
                }

                {
                    MutableShortCollection target = this.newShortForTransform();
                    MutableShortCollection result = iterable.flatCollectShort(each -> ShortLists.immutable.with(
                            (short) (each % 10),
                            (short) (each % 10)), target);
                    assertIterablesEqual(
                            this.newShortForTransform((short) 1, (short) 1, (short) 2, (short) 2, (short) 3, (short) 3, (short) 1, (short) 1, (short) 2, (short) 2, (short) 3, (short) 3),
                            result);
                    assertSame(target, result);
                }
            }
            default -> fail("Unexpected value: " + this.getOrderingType());
        }

        if (!this.allowsDuplicates())
        {
            return;
        }

        {
            MutableBooleanCollection targetDup = this.newBooleanForTransform();
            MutableBooleanCollection resultDup = this.newWith(3, 3, 2, 2, 1, 1).flatCollectBoolean(
                    each -> BooleanLists.immutable.with(each % 2 == 0, each % 2 == 0),
                    targetDup);
            assertIterablesEqual(this.newBooleanForTransform(false, false, false, false, true, true, true, true, false, false, false, false), resultDup);
            assertSame(targetDup, resultDup);
        }

        RichIterable<Integer> iterableDup = this.newWith(13, 13, 12, 12, 11, 11, 3, 3, 2, 2, 1, 1);

        switch (this.getOrderingType())
        {
            case UNORDERED, INSERTION_ORDER, SORTED_REVERSE_NATURAL ->
            {
                {
                    MutableByteCollection targetDup = this.newByteForTransform();
                    MutableByteCollection resultDup = iterableDup.flatCollectByte(
                            each -> ByteLists.immutable.with((byte) (each % 10), (byte) (each % 10)),
                            targetDup);
                    assertIterablesEqual(
                            this.newByteForTransform((byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 2, (byte) 2, (byte) 2, (byte) 2, (byte) 1, (byte) 1, (byte) 1, (byte) 1),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableCharCollection targetDup = this.newCharForTransform();
                    MutableCharCollection resultDup = iterableDup.flatCollectChar(
                            each -> CharLists.immutable.with((char) (each % 10), (char) (each % 10)),
                            targetDup);
                    assertIterablesEqual(
                            this.newCharForTransform((char) 3, (char) 3, (char) 3, (char) 3, (char) 2, (char) 2, (char) 2, (char) 2, (char) 1, (char) 1, (char) 1, (char) 1, (char) 3, (char) 3, (char) 3, (char) 3, (char) 2, (char) 2, (char) 2, (char) 2, (char) 1, (char) 1, (char) 1, (char) 1),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableDoubleCollection targetDup = this.newDoubleForTransform();
                    MutableDoubleCollection resultDup = iterableDup.flatCollectDouble(
                            each -> DoubleLists.immutable.with((double) (each % 10), (double) (each % 10)),
                            targetDup);
                    assertIterablesEqual(
                            this.newDoubleForTransform(3.0, 3.0, 3.0, 3.0, 2.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0, 3.0, 3.0, 3.0, 3.0, 2.0, 2.0, 2.0, 2.0, 1.0, 1.0, 1.0, 1.0),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableFloatCollection targetDup = this.newFloatForTransform();
                    MutableFloatCollection resultDup = iterableDup.flatCollectFloat(
                            each -> FloatLists.immutable.with((float) (each % 10), (float) (each % 10)),
                            targetDup);
                    assertIterablesEqual(
                            this.newFloatForTransform(3.0f, 3.0f, 3.0f, 3.0f, 2.0f, 2.0f, 2.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f, 3.0f, 3.0f, 3.0f, 3.0f, 2.0f, 2.0f, 2.0f, 2.0f, 1.0f, 1.0f, 1.0f, 1.0f),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableIntCollection targetDup = this.newIntForTransform();
                    MutableIntCollection resultDup = iterableDup.flatCollectInt(
                            each -> IntLists.immutable.with(each % 10, each % 10),
                            targetDup);
                    assertIterablesEqual(
                            this.newIntForTransform(3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1, 3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableLongCollection targetDup = this.newLongForTransform();
                    MutableLongCollection resultDup = iterableDup.flatCollectLong(
                            each -> LongLists.immutable.with(each % 10, each % 10),
                            targetDup);
                    assertIterablesEqual(
                            this.newLongForTransform(3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1, 3, 3, 3, 3, 2, 2, 2, 2, 1, 1, 1, 1),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableShortCollection targetDup = this.newShortForTransform();
                    MutableShortCollection resultDup = iterableDup.flatCollectShort(
                            each -> ShortLists.immutable.with((short) (each % 10), (short) (each % 10)),
                            targetDup);
                    assertIterablesEqual(
                            this.newShortForTransform((short) 3, (short) 3, (short) 3, (short) 3, (short) 2, (short) 2, (short) 2, (short) 2, (short) 1, (short) 1, (short) 1, (short) 1, (short) 3, (short) 3, (short) 3, (short) 3, (short) 2, (short) 2, (short) 2, (short) 2, (short) 1, (short) 1, (short) 1, (short) 1),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }
            }
            case SORTED_NATURAL ->
            {
                {
                    MutableByteCollection targetDup = this.newByteForTransform();
                    MutableByteCollection resultDup = iterableDup.flatCollectByte(
                            each -> ByteLists.immutable.with((byte) (each % 10), (byte) (each % 10)),
                            targetDup);
                    assertIterablesEqual(
                            this.newByteForTransform((byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 2, (byte) 2, (byte) 3, (byte) 3, (byte) 3, (byte) 3, (byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 2, (byte) 2, (byte) 2, (byte) 2, (byte) 3, (byte) 3, (byte) 3, (byte) 3),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableCharCollection targetDup = this.newCharForTransform();
                    MutableCharCollection resultDup = iterableDup.flatCollectChar(
                            each -> CharLists.immutable.with((char) (each % 10), (char) (each % 10)),
                            targetDup);
                    assertIterablesEqual(
                            this.newCharForTransform((char) 1, (char) 1, (char) 1, (char) 1, (char) 2, (char) 2, (char) 2, (char) 2, (char) 3, (char) 3, (char) 3, (char) 3, (char) 1, (char) 1, (char) 1, (char) 1, (char) 2, (char) 2, (char) 2, (char) 2, (char) 3, (char) 3, (char) 3, (char) 3),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableDoubleCollection targetDup = this.newDoubleForTransform();
                    MutableDoubleCollection resultDup = iterableDup.flatCollectDouble(
                            each -> DoubleLists.immutable.with((double) (each % 10), (double) (each % 10)),
                            targetDup);
                    assertIterablesEqual(
                            this.newDoubleForTransform(1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 3.0, 3.0, 1.0, 1.0, 1.0, 1.0, 2.0, 2.0, 2.0, 2.0, 3.0, 3.0, 3.0, 3.0),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableFloatCollection targetDup = this.newFloatForTransform();
                    MutableFloatCollection resultDup = iterableDup.flatCollectFloat(
                            each -> FloatLists.immutable.with((float) (each % 10), (float) (each % 10)),
                            targetDup);
                    assertIterablesEqual(
                            this.newFloatForTransform(1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f, 1.0f, 1.0f, 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 3.0f, 3.0f, 3.0f),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableIntCollection targetDup = this.newIntForTransform();
                    MutableIntCollection resultDup = iterableDup.flatCollectInt(
                            each -> IntLists.immutable.with(each % 10, each % 10),
                            targetDup);
                    assertIterablesEqual(
                            this.newIntForTransform(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableLongCollection targetDup = this.newLongForTransform();
                    MutableLongCollection resultDup = iterableDup.flatCollectLong(
                            each -> LongLists.immutable.with(each % 10, each % 10),
                            targetDup);
                    assertIterablesEqual(
                            this.newLongForTransform(1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }

                {
                    MutableShortCollection targetDup = this.newShortForTransform();
                    MutableShortCollection resultDup = iterableDup.flatCollectShort(
                            each -> ShortLists.immutable.with((short) (each % 10), (short) (each % 10)),
                            targetDup);
                    assertIterablesEqual(
                            this.newShortForTransform((short) 1, (short) 1, (short) 1, (short) 1, (short) 2, (short) 2, (short) 2, (short) 2, (short) 3, (short) 3, (short) 3, (short) 3, (short) 1, (short) 1, (short) 1, (short) 1, (short) 2, (short) 2, (short) 2, (short) 2, (short) 3, (short) 3, (short) 3, (short) 3),
                            resultDup);
                    assertSame(targetDup, resultDup);
                }
            }
            default -> fail("Unexpected value: " + this.getOrderingType());
        }
    }
}
