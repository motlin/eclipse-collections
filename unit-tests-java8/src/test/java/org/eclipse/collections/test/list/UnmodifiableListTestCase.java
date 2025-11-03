/*
 * Copyright (c) 2022 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.list;

import java.util.List;

import org.eclipse.collections.api.factory.Lists;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface UnmodifiableListTestCase extends FixedSizeListTestCase
{
    @Override
    @Test
    default void List_set()
    {
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(1).set(0, 4));
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(1, 2).set(0, 4));
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(1, 2, 3).set(0, 4));

        assertThrows(UnsupportedOperationException.class, () -> this.newWith().set(-1, 4));
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(1, 2, 3).set(4, 4));
    }

    @Override
    @Test
    default void List_subList()
    {
        List<Integer> list = this.newWith(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> sublist = list.subList(2, 8);
        assertIterablesEqual(Lists.immutable.with(2, 3, 4, 5, 6, 7), sublist);

        assertThrows(UnsupportedOperationException.class, () -> sublist.set(0, 99));
        assertIterablesEqual(Lists.immutable.with(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
        assertIterablesEqual(Lists.immutable.with(2, 3, 4, 5, 6, 7), sublist);

        assertThrows(UnsupportedOperationException.class, () -> sublist.add(100));
        assertIterablesEqual(Lists.immutable.with(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
        assertIterablesEqual(Lists.immutable.with(2, 3, 4, 5, 6, 7), sublist);

        assertThrows(UnsupportedOperationException.class, () -> sublist.remove(Integer.valueOf(5)));
        assertIterablesEqual(Lists.immutable.with(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
        assertIterablesEqual(Lists.immutable.with(2, 3, 4, 5, 6, 7), sublist);

        assertThrows(UnsupportedOperationException.class, () -> sublist.add(1, 99));
        assertIterablesEqual(Lists.immutable.with(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
        assertIterablesEqual(Lists.immutable.with(2, 3, 4, 5, 6, 7), sublist);

        assertThrows(UnsupportedOperationException.class, sublist::clear);
        assertIterablesEqual(Lists.immutable.with(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), list);
        assertIterablesEqual(Lists.immutable.with(2, 3, 4, 5, 6, 7), sublist);
    }
}
