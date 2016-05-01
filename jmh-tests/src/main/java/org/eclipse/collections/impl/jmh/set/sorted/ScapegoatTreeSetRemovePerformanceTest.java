/*
 * Copyright (c) 2016 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl.jmh.set.sorted;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.set.sorted.mutable.ScapegoatTreeSet;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class ScapegoatTreeSetRemovePerformanceTest
{
    private Integer[] dataForRemove;

    @Param({"0.65", "0.7", "0.75", "0.8", "0.85", "0.9"})
    public float balanceRatio;
    @Param({"0.65", "0.75", "0.85"})
    public float fullRebalanceRatioAdd;
    @Param({"0.65", "0.75", "0.85"})
    public float fullRebalanceRatioRemove;

    @Param("1000000")
    public int size;

    @Param({"true", "false"})
    public boolean compact;

    private MutableList<Integer> data;

    private ScapegoatTreeSet<Integer> integers;

    @Setup(Level.Invocation)
    public void setUp()
    {
        this.data = Interval.oneTo(this.size).toList();
        Collections.shuffle(this.data);
        Integer[] dataForAdd = this.data.toArray(new Integer[this.data.size()]);
        Collections.shuffle(this.data);
        this.dataForRemove = this.data.toArray(new Integer[this.data.size()]);

        this.integers = new ScapegoatTreeSet<>(this.balanceRatio, this.fullRebalanceRatioAdd, this.fullRebalanceRatioRemove);

        for (Integer integer : dataForAdd)
        {
            this.integers.add(integer);
        }
        if (this.compact)
        {
            this.integers.compact();
        }
    }

    @Warmup(iterations = 20)
    @Measurement(iterations = 10)
    @Benchmark
    public ScapegoatTreeSet<Integer> remove()
    {
        for (Integer integer : this.dataForRemove)
        {
            this.integers.remove(integer);
        }
        return this.integers;
    }

    @Warmup(iterations = 20)
    @Measurement(iterations = 10)
    @Benchmark
    public void contains(Blackhole blackhole)
    {
        for (Integer integer : this.dataForRemove)
        {
            blackhole.consume(this.integers.contains(integer));
        }
    }
}
