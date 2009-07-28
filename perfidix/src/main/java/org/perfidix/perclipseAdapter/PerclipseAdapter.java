/*
 * Copyright 2009 Distributed Systems Group, University of Konstanz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * $LastChangedRevision$
 * $LastChangedBy$
 * $LastChangedDate$
 *
 */
package org.perfidix.perclipseAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.perfidix.Benchmark;
import org.perfidix.element.BenchmarkMethod;
import org.perfidix.ouput.TabularSummaryOutput;
import org.perfidix.result.BenchmarkResult;

/**
 * @author Lukas Lewandowski, University of Konstanz
 * @author Sebastian Graf, University of Konstanz
 */
public final class PerclipseAdapter {

    /** Instance for this run of the adapter */
    private final Benchmark benchmark;

    /** View instance for communicating with the perclipse plugin */
    private final PerclipseViewProgressUpdater view;

    /**
     * private constructor.
     */
    private PerclipseAdapter(final int port) {
        benchmark = new Benchmark();
        view = new PerclipseViewProgressUpdater(null, port);
    }

    /**
     * Registering all classes and getting a mapping with the Methods and the
     * corresponding overall runs
     * 
     * @param classNames
     *            the names of the classes to be benched
     */
    private final void registerClasses(final List<String> classNames) {
        for (final String className : classNames) {
            try {
                benchmark.add(Class.forName(className));
            } catch (final ClassNotFoundException e) {
                // TODO Exception handling auf das Perclipse plugin
            }
        }
        final Map<BenchmarkMethod, Integer> vals =
                benchmark.getNumberOfMethodsAndRuns();
        view.initProgressView(vals);
    }

    private final void runBenchmark() {
        final BenchmarkResult res =
                benchmark.run(new PerclipseListener(view));
        new TabularSummaryOutput().visitBenchmark(res);
        view.finished();
    }

    /**
     * Main method for invoking benchs with classes as strings.
     * 
     * @param args
     *            the classes
     * @throws Exception
     *             of any kind.
     */
    public final static void main(final String[] args) throws Exception {
        // init of the connection to the plugin
        int viewPort = 0;
        final List<String> classList = new ArrayList<String>();
        for (int i = 0; i < args.length; i++) {
            if (!args[i].equals("-Port")) {
                classList.add(args[i]);
            } else {
                if (args[i + 1] != null) {
                    viewPort = Integer.parseInt(args[i + 1]);
                }
                break;
            }
        }

        final PerclipseAdapter adapter = new PerclipseAdapter(viewPort);
        adapter.registerClasses(classList);
        adapter.runBenchmark();
        //

        /****/

    }
}
