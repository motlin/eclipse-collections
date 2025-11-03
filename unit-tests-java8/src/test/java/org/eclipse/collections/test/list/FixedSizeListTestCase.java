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
import java.util.ListIterator;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.test.FixedSizeCollectionTestCase;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.assertThrows;

public interface FixedSizeListTestCase extends FixedSizeCollectionTestCase, ListTestCase
{
    @Override
    @Test
    default void Iterable_remove()
    {
        FixedSizeCollectionTestCase.super.Iterable_remove();
    }

    @Override
    @Test
    default void List_subList()
    {
        List<Integer> list = this.newWith(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        List<Integer> sublist = list.subList(2, 8);
        assertIterablesEqual(Lists.immutable.with(2, 3, 4, 5, 6, 7), sublist);

        sublist.set(0, 99);
        assertIterablesEqual(Lists.immutable.with(0, 1, 99, 3, 4, 5, 6, 7, 8, 9), list);
        assertIterablesEqual(Lists.immutable.with(99, 3, 4, 5, 6, 7), sublist);

        assertThrows(UnsupportedOperationException.class, () -> sublist.add(100));
        assertIterablesEqual(Lists.immutable.with(0, 1, 99, 3, 4, 5, 6, 7, 8, 9), list);
        assertIterablesEqual(Lists.immutable.with(99, 3, 4, 5, 6, 7), sublist);

        assertThrows(UnsupportedOperationException.class, () -> sublist.remove(Integer.valueOf(5)));
        assertIterablesEqual(Lists.immutable.with(0, 1, 99, 3, 4, 5, 6, 7, 8, 9), list);
        assertIterablesEqual(Lists.immutable.with(99, 3, 4, 5, 6, 7), sublist);

        assertThrows(UnsupportedOperationException.class, () -> sublist.add(1, 200));
        assertIterablesEqual(Lists.immutable.with(0, 1, 99, 3, 4, 5, 6, 7, 8, 9), list);
        assertIterablesEqual(Lists.immutable.with(99, 3, 4, 5, 6, 7), sublist);

        assertThrows(UnsupportedOperationException.class, sublist::clear);
        assertIterablesEqual(Lists.immutable.with(0, 1, 99, 3, 4, 5, 6, 7, 8, 9), list);
        assertIterablesEqual(Lists.immutable.with(99, 3, 4, 5, 6, 7), sublist);
    }

    @Override
    @Test
    default void List_subList_subList_remove()
    {
        List<String> list = this.newWith("A", "B", "C", "D");
        List<String> sublist = list.subList(0, 3);
        List<String> sublist2 = sublist.subList(0, 2);
        assertIterablesEqual(Lists.immutable.with("A", "B", "C"), sublist);
        assertIterablesEqual(Lists.immutable.with("A", "B"), sublist2);

        assertThrows(UnsupportedOperationException.class, () -> sublist2.remove(1));
        assertIterablesEqual(Lists.immutable.with("A", "B", "C"), sublist);
        assertIterablesEqual(Lists.immutable.with("A", "B"), sublist2);

        assertIterablesEqual(Lists.immutable.with("A", "B", "C", "D"), list);
    }

    @Override
    @Test
    default void List_subList_subList_iterator_add_remove()
    {
        List<String> list = this.newWith("A", "B", "C", "D");
        List<String> sublist = list.subList(0, 3);
        List<String> sublist2 = sublist.subList(0, 2);
        assertIterablesEqual(Lists.immutable.with("A", "B", "C"), sublist);
        assertIterablesEqual(Lists.immutable.with("A", "B"), sublist2);

        ListIterator<String> iterator = sublist2.listIterator();
        assertThrows(UnsupportedOperationException.class, () -> iterator.add("X"));
        assertIterablesEqual(Lists.immutable.with("A", "B", "C"), sublist);
        assertIterablesEqual(Lists.immutable.with("A", "B"), sublist2);

        ListIterator<String> iterator2 = sublist2.listIterator();
        iterator2.next();
        assertThrows(UnsupportedOperationException.class, iterator2::remove);
        assertIterablesEqual(Lists.immutable.with("A", "B", "C"), sublist);
        assertIterablesEqual(Lists.immutable.with("A", "B"), sublist2);

        assertIterablesEqual(Lists.immutable.with("A", "B", "C", "D"), list);
    }

    @Override
    @Test
    default void List_subList_subList_addAll()
    {
        List<String> list = this.newWith("A", "B", "C", "D");
        List<String> sublist = list.subList(0, 3);
        List<String> sublist2 = sublist.subList(0, 2);
        assertIterablesEqual(Lists.immutable.with("A", "B", "C"), sublist);
        assertIterablesEqual(Lists.immutable.with("A", "B"), sublist2);

        assertThrows(UnsupportedOperationException.class, () -> sublist2.addAll(Lists.mutable.of("D", "E")));
        assertIterablesEqual(Lists.immutable.with("A", "B", "C"), sublist);
        assertIterablesEqual(Lists.immutable.with("A", "B"), sublist2);

        assertThrows(UnsupportedOperationException.class, sublist2::clear);
        assertIterablesEqual(Lists.immutable.with("A", "B", "C"), sublist);
        assertIterablesEqual(Lists.immutable.with("A", "B"), sublist2);

        assertIterablesEqual(Lists.immutable.with("A", "B", "C", "D"), list);
    }

    @Override
    @Test
    default void List_subList_subList_clear()
    {
        List<String> list = this.newWith("A", "B", "C", "D", "E", "F");
        List<String> sublist = list.subList(3, 6);
        List<String> sublist2 = sublist.subList(0, 2);
        assertIterablesEqual(Lists.immutable.with("D", "E", "F"), sublist);
        assertIterablesEqual(Lists.immutable.with("D", "E"), sublist2);

        assertThrows(UnsupportedOperationException.class, sublist2::clear);
        assertIterablesEqual(Lists.immutable.with("A", "B", "C", "D", "E", "F"), list);
        assertIterablesEqual(Lists.immutable.with("D", "E", "F"), sublist);
        assertIterablesEqual(Lists.immutable.with("D", "E"), sublist2);
    }
}
