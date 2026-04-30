/*
 * Copyright (c) 2026 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.test.map;

import org.junit.jupiter.api.Test;

public interface NoIteratorBiMapValuesCollectionTestCase extends BiMapValuesCollectionTestCase
{
    @Override
    default boolean allowsSerialization()
    {
        return false;
    }

    @Override
    @Test
    default void Iterable_hasNext()
    {
        // Not applicable
    }

    @Override
    @Test
    default void Iterable_next()
    {
        // Not applicable
    }

    @Override
    @Test
    default void Iterable_remove()
    {
        // Not applicable
    }

    @Override
    @Test
    default void Collection_retainAll()
    {
        // Not applicable
    }
}
