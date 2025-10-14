/*
 * Copyright (c) 2025 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.list.mutable;

import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.test.IterableTestCase;
import org.junit.jupiter.api.Test;

public class FastListSubListTest implements MutableListTestCase
{
    @SafeVarargs
    @Override
    public final <T> MutableList<T> newWith(T... elements)
    {
        MutableList<T> list = Lists.mutable.empty();
        list.add(null);
        IterableTestCase.addAllTo(elements, list);
        list.add(null);
        return (MutableList<T>) list.subList(1, elements.length + 1);
    }

    @Override
    @Test
    public void Object_PostSerializedEqualsAndHashCode()
    {
        // ArrayList.subList() is not serializable, but FastList.subList() is serializable via writeReplace()
        Verify.assertPostSerializedEqualsAndHashCode(this.newWith(1, 2, 3));
    }
}
