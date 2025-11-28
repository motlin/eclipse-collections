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

/**
 * Represents the ordering behavior of an iterable for test case purposes.
 *
 * @since 12.0
 */
public enum OrderingType
{
    UNORDERED,
    INSERTION_ORDER,
    SORTED_NATURAL,
    SORTED_REVERSE_NATURAL,
    SORTED_CUSTOM
}
