/*
 * Copyright (c) 2018 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.bag.mutable;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.predicate.primitive.IntPredicate;
import org.eclipse.collections.api.map.primitive.MutableObjectIntMap;
import org.eclipse.collections.impl.map.mutable.primitive.ObjectIntHashMap;
import org.eclipse.collections.impl.utility.ArrayIterate;
import org.eclipse.collections.impl.utility.Iterate;

/**
 * A HashBag is a MutableBag which uses a Map as its underlying data store. Each key in the Map represents some item,
 * and the value in the map represents the current number of occurrences of that item.
 *
 * @since 1.0
 */
public class HashBag<T>
        extends AbstractHashBag<T>
        implements Externalizable
{
    private static final long serialVersionUID = 1L;

    public HashBag()
    {
        this.items = ObjectIntHashMap.newMap();
    }

    public HashBag(int size)
    {
        this.items = new ObjectIntHashMap<>(size);
    }

    private HashBag(MutableObjectIntMap<T> map)
    {
        this.items = map;
        this.size = (int) map.sum();
    }

    public static <E> MutableBag<E> newBag()
    {
        return new HashBag<>();
    }

    public static <E> MutableBag<E> newBag(int size)
    {
        return new HashBag<>(size);
    }

    public static <E> MutableBag<E> newBag(Bag<? extends E> source)
    {
        HashBag<E> result = new HashBag<>(source.sizeDistinct());
        result.addAllBag(source);
        return result;
    }

    public static <E> MutableBag<E> newBag(Iterable<? extends E> source)
    {
        if (source instanceof Bag)
        {
            return HashBag.newBag((Bag<E>) source);
        }
        return HashBag.newBagWith((E[]) Iterate.toArray(source));
    }

    public static <E> MutableBag<E> newBagWith(E... elements)
    {
        HashBag<E> result = new HashBag<>();
        ArrayIterate.addAllTo(elements, result);
        return result;
    }

    @Override
    protected int computeHashCode(T item)
    {
        return item.hashCode();
    }

    @Override
    public MutableBag<T> selectByOccurrences(IntPredicate predicate)
    {
        MutableObjectIntMap<T> map = this.items.select((each, occurrences) -> {
            return predicate.accept(occurrences);
        });
        return new HashBag<>(map);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        ((Externalizable) this.items).writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        this.items = new ObjectIntHashMap<>();
        ((Externalizable) this.items).readExternal(in);
        this.size = (int) this.items.sum();
    }

    @Override
    public MutableBag<T> without(T element)
    {
        this.remove(element);
        return this;
    }

    @Override
    public MutableBag<T> newEmpty()
    {
        return HashBag.newBag();
    }

    @Override
    public MutableBag<T> with(T element)
    {
        this.add(element);
        return this;
    }

    @Override
    public MutableBag<T> withAll(Iterable<? extends T> iterable)
    {
        this.addAllIterable(iterable);
        return this;
    }

    @Override
    public MutableBag<T> withoutAll(Iterable<? extends T> iterable)
    {
        this.removeAllIterable(iterable);
        return this;
    }

    public MutableBag<T> with(T... elements)
    {
        this.addAll(Arrays.asList(elements));
        return this;
    }

    public MutableBag<T> with(T element1, T element2)
    {
        return this.with(element1).with(element2);
    }

    public MutableBag<T> with(T element1, T element2, T element3)
    {
        return this.with(element1).with(element2).with(element3);
    }
}
