/*
 * Copyright (c) 2022 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test;


    @Override
    @Test
    default void Iterable_toString()
    {
        assertThat(this.newWith(2, 2, 1).toString(), isOneOf("[2, 2, 1]", "[1, 2, 2]"));
        assertThat(this.newWith(2, 2, 1).asLazy().toString(), isOneOf("[2, 2, 1]", "[1, 2, 2]"));
    }

    @Override
    @Test
    default void RichIterable_getFirst()
    {
        RichIterable<Integer> integers = this.newWith(3, 2, 1);
        Integer first = integers.getFirst();
        assertThat(first, isOneOf(3, 2, 1));
        assertEquals(integers.iterator().next(), first);

        assertNotEquals(integers.getLast(), first);
    }

    @Override
    @Test
    default void RichIterable_getLast()
    {
        RichIterable<Integer> integers = this.newWith(3, 2, 1);
        Integer last = integers.getLast();
        assertThat(last, isOneOf(3, 2, 1));

        Iterator<Integer> iterator = integers.iterator();
        Integer iteratorLast = null;
        while (iterator.hasNext())
        {
            iteratorLast = iterator.next();
        }
        assertEquals(iteratorLast, last);

        assertNotEquals(integers.getFirst(), last);
    }
}
