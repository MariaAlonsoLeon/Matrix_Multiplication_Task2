package org.example;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkMain {

    public static void main(String[] args) throws RunnerException {
        runDenseMatrixBenchmarks();
        runSparseMatrixBenchmarks();
    }

    private static void runDenseMatrixBenchmarks() throws RunnerException {
        System.out.println("Running benchmarks for dense matrices...");

        Options denseOptions = new OptionsBuilder()
                .include(DenseMatrixBenchmark.class.getSimpleName())
                .warmupIterations(2)
                .measurementIterations(5)
                .forks(1)
                .result("dense_matrix_benchmark_results.json")
                .resultFormat(org.openjdk.jmh.results.format.ResultFormatType.JSON)
                .build();

        new Runner(denseOptions).run();
    }

    private static void runSparseMatrixBenchmarks() throws RunnerException {
        System.out.println("Running benchmarks for sparse matrices...");

        Options sparseOptions = new OptionsBuilder()
                .include(SparseMatrixBenchmark.class.getSimpleName())
                .warmupIterations(2)
                .measurementIterations(3)
                .forks(1)
                .result("sparse_matrix_benchmark_results.json")
                .resultFormat(org.openjdk.jmh.results.format.ResultFormatType.JSON)
                .build();

        new Runner(sparseOptions).run();
    }
}
