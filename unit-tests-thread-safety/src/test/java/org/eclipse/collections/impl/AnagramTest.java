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

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnagramTest
{
    private static final Logger LOGGER = LoggerFactory.getLogger(AnagramTest.class);

    private static final int SIZE_THRESHOLD = 10;
    private final String[] words =
            {
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
            "tesla",
            };

    private void log(String string)
    {
        LOGGER.info(string);
    }

    @Test
    public void anagrams()
    {
        Stream.of(this.words)
                .collect(Collectors.groupingBy(this::sortString))
                .values()
                .stream()
                .filter(list -> list.size() > SIZE_THRESHOLD)
                .sorted((list1, list2) -> Integer.compare(list2.size(), list1.size()))
                .map(list -> list.size() + ": " + list)
                .forEach(this::log);
    }

    @Test
    public void anagramsLonghand()
    {
        Stream.of(this.words)
                .collect(Collectors.groupingBy(this::sortString))
                .values()
                .stream()
                .filter(list -> list.size() > SIZE_THRESHOLD)
                .sorted((list1, list2) -> Integer.compare(list2.size(), list1.size()))
                .map(list -> list.size() + ": " + list)
                .forEach(this::log);
    }

    private String sortString(String word)
    {
        return word
                .chars()
                .sorted()
                .collect(
                        StringBuilder::new,
                        StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }
}
