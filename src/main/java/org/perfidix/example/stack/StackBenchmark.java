/**
 * Copyright (c) 2012, University of Konstanz, Distributed Systems Group All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: * Redistributions of source code must retain the above copyright notice, this list of
 * conditions and the following disclaimer. * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation and/or other materials provided with the
 * distribution. * Neither the name of the University of Konstanz nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.perfidix.example.stack;


import java.util.ArrayDeque;
import java.util.Random;
import java.util.Stack;

import org.perfidix.Benchmark;
import org.perfidix.annotation.BeforeBenchClass;
import org.perfidix.annotation.Bench;
import org.perfidix.example.Config;
import org.perfidix.ouput.TabularSummaryOutput;
import org.perfidix.result.BenchmarkResult;


/**
 * Benching {@link FastIntStack} against {@link Stack}. Just a simple example of Perfidix.
 * 
 * @author Sebastian Graf, University of Konstanz
 */
public final class StackBenchmark {

    /**
     * Number of runs.
     */
    private static final int RUNS = 100;

    /**
     * Size of array to be tested.
     */
    private static final int ARRAYSIZE = 100;

    /** Data to be written and read. */
    private transient int[] intData;

    /** FastStack instance. */
    private transient FastIntStack fastInt;

    /** Stack instance. */
    private transient Stack<Integer> normalInt;

    private transient ArrayDeque<Integer> arrayDeque;

    /**
     * Generating the data, just once per runtime.
     */
    @BeforeBenchClass
    public void generateData () {
        final Random ran = new Random();
        intData = new int[ARRAYSIZE];
        int counter = 0;
        while (counter < ARRAYSIZE) {
            intData[counter] = ran.nextInt();
            counter++;
        }
    }

    /**
     * Bench for pushing the data to the {@link FastIntStack}.
     */
    @Bench (runs = RUNS)
    public void benchFastIntPush () {
        fastInt = new FastIntStack();
        for (final int i : intData) {
            fastInt.push(i);
        }
    }

    /**
     * Bench for popping the data from the {@link FastIntStack}.
     */
    @Bench (runs = RUNS , beforeEachRun = "benchFastIntPush")
    public void benchFastIntStackPop () {

        while (fastInt.size() > 0) {
            fastInt.pop();

        }
    }

    /**
     * Bench for pushing the data to the {@link Stack}.
     */
    @Bench (runs = RUNS)
    public void benchNormalIntPush () {
        normalInt = new Stack<Integer>();
        for (final int i : intData) {
            normalInt.push(i);
        }
    }

    /**
     * Bench for popping the data from the {@link Stack}.
     */
    @Bench (runs = RUNS , beforeEachRun = "benchNormalIntPush")
    public void benchNormalIntPop () {
        while (normalInt.size() > 0) {
            normalInt.pop();
        }
    }

    @Bench (runs = RUNS)
    /**
     * Bench for pushing the data to the {@link ArrayDeque}.
     */
    public void benchArrayDequePush () {
        arrayDeque = new ArrayDeque<Integer>();
        for (final int i : intData) {
            arrayDeque.push(i);
        }
    }

    /**
     * Bench for popping the data from the {@link ArrayDeque}.
     */
    @Bench (runs = RUNS , beforeEachRun = "benchArrayDequePush")
    public void benchArrayDequeStackPop () {

        while (arrayDeque.size() > 0) {
            arrayDeque.pop();
        }
    }

    /**
     * Simple setUp of a benchmark. The {@link Benchmark} is initialized with two Meters (<code>TimeMeter</code> and
     * <code>MemMeter</code>). Afterwards the benchmark is running with a TabularOutput as a listener registered. The
     * result of the benchmark is displayed in a complete table at the end.
     * 
     * @param args not used here
     */
    public static void main (final String[] args) {

        final Benchmark bench = new Benchmark(new Config());
        bench.add(StackBenchmark.class);

        final BenchmarkResult res = bench.run();
        new TabularSummaryOutput().visitBenchmark(res);
    }

}
