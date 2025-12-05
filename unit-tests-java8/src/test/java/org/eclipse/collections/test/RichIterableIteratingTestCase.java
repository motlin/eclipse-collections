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
import org.eclipse.collections.impl.block.factory.Procedures;
import org.junit.jupiter.api.Test;

import static org.eclipse.collections.test.IterableTestCase.assertIterablesEqual;
import static org.junit.jupiter.api.Assertions.fail;

public interface RichIterableIteratingTestCase extends InternalIterableTestCase
{
    @Test
    default void RichIterable_tap()
    {
        RichIterable<Integer> iterable = this.newWith(3, 2, 1);
        MutableCollection<Integer> result = this.newMutableForFilter();
        iterable.tap(result::add).forEach(Procedures.noop());
        assertIterablesEqual(this.newMutableForFilter(3, 2, 1), result);
        this.newWith().tap(Procedures.cast(each -> fail()));

        if (!this.allowsDuplicates())
        {
            return;
        }

        RichIterable<Integer> iterable2 = this.newWith(3, 3, 3, 2, 2, 1);
        MutableCollection<Integer> result2 = this.newMutableForFilter();
        iterable2.tap(result2::add).forEach(Procedures.noop());
        assertIterablesEqual(this.newMutableForFilter(3, 3, 3, 2, 2, 1), result2);
    }
}
