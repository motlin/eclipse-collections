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

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class ScapegoatTreeSetPerformanceTest
{
    private Integer[] dataForAdd;
    private Integer[] dataForRemove;

    // 0.575, 0.7, and 0.725 perform poorly. 0.6, 0.625, 0.65, 0.675 perform well
    // 0.6 through 0.65 performs better than 0.65 through 0.7
    // @Param({"0.575", "0.6", "0.625", "0.65", "0.675", "0.7", "0.725"})
    // @Param({"0.55", "0.56", "0.57", "0.58", "0.59", "0.6", "0.61", "0.62", "0.65"})
    @Param({"0.82", "8.25", "0.83"})
    public float balanceRatio;

    // 0.25 performs poorly, 0.275 performs poorly, 0.3 seems good, 0.375 seems good, 0.5 seems good, up to 2 seems ok. 16 seems not great.
    // @Param({/*"0.25", "0.275", "0.3", */"0.28", "0.30", "0.31", "0.325", "0.33", "0.35", "0.375", "0.4", "0.45", "0.5", /*"0.675", "0.7", "0.8", "0.9", "1.0", "1.25", "1.5", "2" */ /*"4", "256"*/})
    @Param("0.4")
    public float fullRebalanceRatioAdd;

    // 0.8 and 0.9 perform poorly, below 0.5 seems to work best. 0.375 seems great still, but drop-off at 0.5 and 0.675 isn't so bad
    // @Param({/*"0.01", "0.1", "0.25", "0.35",*/ "0.36", "0.375", "0.39" /* "0.4" "0.5", "0.60", "0.675", "0.7", "0.8", "0.9"*/})
    @Param("0.375")
    public float fullRebalanceRatioRemove;

    @Param("10000000")
    public int size;

    @Setup(Level.Iteration)
    public void setUp()
    {
        MutableList<Integer> data = Interval.oneTo(this.size).toList();
        Collections.shuffle(data);
        this.dataForAdd = data.toArray(new Integer[data.size()]);
        Collections.shuffle(data);
        this.dataForRemove = data.toArray(new Integer[data.size()]);
    }

    @Warmup(iterations = 20)
    @Measurement(iterations = 10)
    @Benchmark
    public ScapegoatTreeSet<Integer> add()
    {
        ScapegoatTreeSet<Integer> integers = new ScapegoatTreeSet<Integer>(
                this.balanceRatio,
                this.fullRebalanceRatioAdd,
                this.fullRebalanceRatioRemove);
        for (Integer integer : this.dataForAdd)
        {
            integers.add(integer);
        }
        return integers;
    }

    @Warmup(iterations = 20)
    @Measurement(iterations = 10)
    @Benchmark
    public ScapegoatTreeSet<Integer> add_and_compact()
    {
        ScapegoatTreeSet<Integer> integers = new ScapegoatTreeSet<Integer>(
                this.balanceRatio,
                this.fullRebalanceRatioAdd,
                this.fullRebalanceRatioRemove);
        for (Integer integer : this.dataForAdd)
        {
            integers.add(integer);
        }
        integers.compact();
        return integers;
    }

    @Warmup(iterations = 20)
    @Measurement(iterations = 10)
    @Benchmark
    public ScapegoatTreeSet<Integer> add_and_remove()
    {
        ScapegoatTreeSet<Integer> integers = new ScapegoatTreeSet<Integer>(
                this.balanceRatio,
                this.fullRebalanceRatioAdd,
                this.fullRebalanceRatioRemove);
        for (Integer integer : this.dataForAdd)
        {
            integers.add(integer);
        }
        for (Integer integer : this.dataForRemove)
        {
            integers.remove(integer);
        }
        return integers;
    }
}
