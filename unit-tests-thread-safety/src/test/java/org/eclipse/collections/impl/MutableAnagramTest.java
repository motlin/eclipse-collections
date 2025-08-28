/*
 * Copyright (c) 2021 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl;

import java.util.Collections;
import java.util.Iterator;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.utility.internal.IteratorIterate;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MutableAnagramTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(MutableAnagramTest.class);

    private static final int SIZE_THRESHOLD = 10;
    private static final MutableList<String> WORDS_LIST = Lists.mutable.with(
            "alerts",
            "alters",
            "artels",
            "estral",
            "laster",
            "ratels",
            "salter",
            "slater",
            "staler",
            "stelar",
            "talers",
            "least",
            "setal",
            "slate",
            "stale",
            "steal",
            "stela",
            "taels",
            "tales",
            "teals",
            "tesla");
    private final Iterator<String> words = WORDS_LIST.iterator();

    @Test
    public void anagrams()
    {
        IteratorIterate.groupBy(this.words, this::sortStringDescending)
                .multiValuesView()
                .select(iterable -> iterable.size() > SIZE_THRESHOLD)
                .toSortedList(Collections.reverseOrder(Comparators.byFunction(RichIterable::size)))
                .asLazy()
                .collect(iterable -> iterable.size() + ": " + iterable)
                .forEach(LOGGER::info);
    }

    private String sortStringDescending(String string)
    {
        return string
                .chars()
                .mapToObj(c -> (char) c)
                .sorted(Collections.reverseOrder())
                .collect(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append)
                .toString();
    }
}
