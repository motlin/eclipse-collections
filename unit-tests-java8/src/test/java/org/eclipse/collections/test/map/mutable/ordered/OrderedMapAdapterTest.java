/*
 * Copyright (c) 2021 Two Sigma.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.map.mutable.ordered;

import java.util.LinkedHashMap;

import org.eclipse.collections.api.map.MutableOrderedMap;
import org.eclipse.collections.impl.map.ordered.mutable.OrderedMapAdapter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class OrderedMapAdapterTest implements MutableOrderedMapTestCase
{
    @Override
    public <T> MutableOrderedMap<Object, T> newWith(T... elements)
    {
        int i = elements.length;
        MutableOrderedMap<Object, T> result = OrderedMapAdapter.adapt(new LinkedHashMap<>());
        for (T each : elements)
        {
            assertNull(result.put(i, each));
            i--;
        }
        return result;
    }

    @Override
    public <K, V> MutableOrderedMap<K, V> newWithKeysValues(Object... elements)
    {
        if (elements.length % 2 != 0)
        {
            fail(String.valueOf(elements.length));
        }

        MutableOrderedMap<K, V> result = OrderedMapAdapter.adapt(new LinkedHashMap<>());
        for (int i = 0; i < elements.length; i += 2)
        {
            assertNull(result.put((K) elements[i], (V) elements[i + 1]));
        }
        return result;
    }

    @Override
    @Test
    public void OrderedIterable_forEach_from_to()
    {
        // TODO Support indexed traversal for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).forEach(5, 7, each -> { }));

        // TODO Support reverse indexed traversal for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).forEach(7, 5, each -> { }));
    }

    @Override
    @Test
    public void OrderedIterable_forEachWithIndex_from_to()
    {
        // TODO Support indexed traversal for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).forEachWithIndex(5, 7, (each, index) -> { }));

        // TODO Support reverse indexed traversal for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(9, 8, 7, 6, 5, 4, 3, 2, 1, 0).forEachWithIndex(7, 5, (each, index) -> { }));
    }

    @Override
    @Test
    public void OrderedIterable_corresponds()
    {
        // TODO Support corresponds for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(3, 2, 1).corresponds(this.newWith(3, 2, 1), (one, two) -> one.equals(two)));
    }

    @Override
    @Test
    public void OrderedIterable_distinct()
    {
        // TODO Support distinct for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(3, 2, 1).distinct());
    }

    @Override
    @Test
    public void OrderedIterable_toStack()
    {
        // TODO Support toStack for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(3, 2, 1).toStack());
    }

    @Override
    @Test
    public void OrderedIterable_detectIndex()
    {
        // TODO Support detectIndex for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(3, 2, 1).detectIndex(each -> true));
    }

    @Override
    @Test
    public void ReversibleIterable_detectLastIndex()
    {
        // TODO Support detectLastIndex for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(3, 2, 1).detectLastIndex(each -> true));
    }

    @Override
    @Test
    public void ReversibleIterable_toReversed()
    {
        // TODO Support toReversed for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(3, 2, 1).toReversed());
    }

    @Override
    @Test
    public void ReversibleIterable_reverseForEach()
    {
        // TODO Support reverse traversal for ordered maps.
        assertThrows(UnsupportedOperationException.class, () -> this.newWith(3, 2, 1).reverseForEach(each -> { }));
    }
}
