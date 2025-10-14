/*
 * Copyright (c) 2022 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.list.mutable;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.collections.test.list.ListTestCase;
import org.junit.jupiter.api.Test;

public class LinkedListSubListTest
        implements ListTestCase
{
    @SafeVarargs
    @Override
    public final <T> List<T> newWith(T... elements)
    {
        List<T> result = new LinkedList<>();
        result.add((T) new Object());
        result.addAll(Arrays.asList(elements));
        result.add((T) new Object());
        return result.subList(1, result.size() - 1);
    }

    @Override
    @Test
    public void Object_PostSerializedEqualsAndHashCode()
    {
        // TODO 2025-10-12: Assert that the object is not serializable
    }
}
