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

public class Holder<T extends Comparable<? super T>> implements Comparable<Holder<T>>
{
    private final T field;

    public Holder(T field)
    {
        this.field = field;
    }

    @Override
    public int compareTo(Holder<T> other)
    {
        return this.field.compareTo(other.field);
    }
}
