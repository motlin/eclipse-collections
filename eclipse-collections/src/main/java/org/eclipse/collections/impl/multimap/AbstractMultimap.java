/*
 * Copyright (c) 2021 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.multimap;

import java.util.Map;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.block.procedure.Procedure2;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.UnmodifiableRichIterable;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.tuple.Tuples;

public abstract class AbstractMultimap<K, V, C extends RichIterable<V>>
        implements Multimap<K, V>
{
    protected abstract MapIterable<K, C> getMap();

    /**
     * Creates the collection of values for a single key.
     * <p>
     * Collections with weak, soft, or phantom references are not supported.
     * Each call to {@code createCollection} should create a new instance.
     * <p>
     * The returned collection class determines whether duplicate key-value
     * pairs are allowed.
     *
     * @return an empty collection of values
     */
    protected abstract C createCollection();

    protected Function<AbstractMultimap<K, V, C>, C> createCollectionBlock()
    {
        return AbstractMultimap::createCollection;
    }

    // Query Operations

    @Override
    public boolean containsKey(Object key)
    {
        return this.getMap().containsKey(key);
    }

    @Override
    public boolean containsValue(Object value)
    {
        return this.getMap().anySatisfy(collection -> collection.contains(value));
    }

    @Override
    public boolean containsKeyAndValue(Object key, Object value)
    {
        C collection = this.getMap().get(key);
        return collection != null && collection.contains(value);
    }

    // Views

    @Override
    public RichIterable<K> keysView()
    {
        return this.getMap().keysView();
    }

    @Override
    public RichIterable<RichIterable<V>> multiValuesView()
    {
        return this.getMap().valuesView().collect(UnmodifiableRichIterable::of);
    }

    @Override
    public Bag<K> keyBag()
    {
        MutableBag<K> bag = Bags.mutable.empty();
        this.getMap().forEachKeyValue((key, value) -> bag.addOccurrences(key, value.size()));
        return bag;
    }

    @Override
    public RichIterable<V> valuesView()
    {
        return this.getMap().valuesView().flatCollect(Functions.<Iterable<V>>identity());
    }

    @Override
    public RichIterable<Pair<K, RichIterable<V>>> keyMultiValuePairsView()
    {
        return this.getMap().keyValuesView().collect(pair -> Tuples.pair(pair.getOne(), UnmodifiableRichIterable.of(pair.getTwo())));
    }

    @Override
    public RichIterable<Pair<K, V>> keyValuePairsView()
    {
        return this.keyMultiValuePairsView().flatCollect(pair -> pair.getTwo().collect(new KeyValuePairFunction<>(pair.getOne())));
    }

    // Comparison and hashing

    @Override
    public boolean equals(Object object)
    {
        if (object == this)
        {
            return true;
        }
        if (object instanceof Multimap)
        {
            Multimap<?, ?> that = (Multimap<?, ?>) object;
            return this.getMap().equals(that.toMap());
        }
        return false;
    }

    /**
     * Returns the hash code for this multimap.
     * <p>
     * The hash code of a multimap is defined as the hash code of the map view,
     * as returned by {@link Multimap#toMap()}.
     *
     * @see Map#hashCode()
     */
    @Override
    public int hashCode()
    {
        return this.getMap().hashCode();
    }

    /**
     * Returns a string representation of the multimap, generated by calling
     * {@code toString} on the map returned by {@link Multimap#toMap()}.
     *
     * @return a string representation of the multimap
     */
    @Override
    public String toString()
    {
        return this.getMap().toString();
    }

    @Override
    public boolean notEmpty()
    {
        return !this.isEmpty();
    }

    @Override
    public void forEachValue(Procedure<? super V> procedure)
    {
        this.getMap().forEachValue(collection -> collection.forEach(procedure));
    }

    @Override
    public void forEachKey(Procedure<? super K> procedure)
    {
        this.getMap().forEachKey(procedure);
    }

    @Override
    public void forEachKeyValue(Procedure2<? super K, ? super V> procedure)
    {
        Procedure2<V, K> innerProcedure = (value, key) -> procedure.value(key, value);

        this.getMap().forEachKeyValue((key, collection) -> collection.forEachWith(innerProcedure, key));
    }

    @Override
    public void forEachKeyMultiValues(Procedure2<? super K, ? super RichIterable<V>> procedure)
    {
        this.getMap().forEachKeyValue(procedure);
    }

    @Override
    public <R extends MutableMultimap<K, V>> R selectKeysValues(Predicate2<? super K, ? super V> predicate, R target)
    {
        this.getMap().forEachKeyValue((key, collection) -> {
            RichIterable<V> selectedValues = collection.select(value -> predicate.accept(key, value));
            target.putAll(key, selectedValues);
        });
        return target;
    }

    @Override
    public <R extends MutableMultimap<K, V>> R rejectKeysValues(Predicate2<? super K, ? super V> predicate, R target)
    {
        this.getMap().forEachKeyValue((key, collection) -> {
            RichIterable<V> selectedValues = collection.reject(value -> predicate.accept(key, value));
            target.putAll(key, selectedValues);
        });
        return target;
    }

    @Override
    public <R extends MutableMultimap<K, V>> R selectKeysMultiValues(Predicate2<? super K, ? super RichIterable<V>> predicate, R target)
    {
        this.forEachKeyMultiValues((key, collection) -> {
            if (predicate.accept(key, collection))
            {
                target.putAll(key, collection);
            }
        });
        return target;
    }

    @Override
    public <R extends MutableMultimap<K, V>> R rejectKeysMultiValues(Predicate2<? super K, ? super RichIterable<V>> predicate, R target)
    {
        this.forEachKeyMultiValues((key, collection) -> {
            if (!predicate.accept(key, collection))
            {
                target.putAll(key, collection);
            }
        });
        return target;
    }

    @Override
    public <K2, V2, R extends MutableMultimap<K2, V2>> R collectKeysValues(Function2<? super K, ? super V, Pair<K2, V2>> function, R target)
    {
        this.getMap().forEachKeyValue((key, collection) -> collection.each(value -> target.add(function.value(key, value))));
        return target;
    }

    @Override
    public <K2, V2, R extends MutableMultimap<K2, V2>> R collectKeyMultiValues(Function<? super K, ? extends K2> keyFunction, Function<? super V, ? extends V2> valueFunction, R target)
    {
        this.forEachKeyMultiValues((key, values) ->
                target.putAll(
                        keyFunction.valueOf(key),
                        values.collect(valueFunction)));
        return target;
    }

    @Override
    public <V2, R extends MutableMultimap<K, V2>> R collectValues(Function<? super V, ? extends V2> function, R target)
    {
        this.forEachKeyMultiValues((key, values) ->
                target.putAll(
                        key,
                        values.collect(function)));
        return target;
    }

    private static final class KeyValuePairFunction<V, K> implements Function<V, Pair<K, V>>
    {
        private static final long serialVersionUID = 1L;

        private final K key;

        private KeyValuePairFunction(K key)
        {
            this.key = key;
        }

        @Override
        public Pair<K, V> valueOf(V value)
        {
            return Tuples.pair(this.key, value);
        }
    }
}
