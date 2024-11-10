package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.example.MatrixGenerator.generateMatrix;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class DenseMatrixBenchmark {

    @Param({"64", "128", "256", "512", "1024", "2048"})
    private int matrixSize;

    private double[][] matrixA;
    private double[][] matrixB;

    private BasicMultiplication basicMultiplier;
    private StrassenMultiplication strassenMultiplier;
    private OptimizedCacheMultiplication optimizedMultiplier;
    private WinogradMultiplication winogradMultiplication;
    private RowColumnMajorMultiplication rowColumnMajorMultiplication;
    private ParallelMultiplication parallelMultiplication;
    private BlockMultiplication blockMultiplication;

    @Setup(Level.Trial)
    public void setUp() {
        matrixA = generateMatrix(matrixSize, matrixSize);
        matrixB = generateMatrix(matrixSize, matrixSize);

        basicMultiplier = new BasicMultiplication();
        strassenMultiplier = new StrassenMultiplication();
        optimizedMultiplier = new OptimizedCacheMultiplication();
        winogradMultiplication = new WinogradMultiplication();
        rowColumnMajorMultiplication = new RowColumnMajorMultiplication();
        parallelMultiplication = new ParallelMultiplication();
        blockMultiplication = new BlockMultiplication();

    }

    @Benchmark
    public void basicMultiplicationBenchmark() {
        basicMultiplier.multiply(matrixA, matrixB);
    }

    @Benchmark
    public void strassenMultiplicationBenchmark() {
        strassenMultiplier.multiply(matrixA, matrixB);
    }

    @Benchmark
    public void optimizedCacheMultiplicationBenchmark() {
        optimizedMultiplier.multiply(matrixA, matrixB);
    }

    @Benchmark
    public void winogradMultiplicationBenchmark() {
        winogradMultiplication.multiply(matrixA, matrixB);
    }

    @Benchmark
    public void rowColumnMajorMultiplicationBenchmark() {
        rowColumnMajorMultiplication.multiply(matrixA, matrixB);
    }

    @Benchmark
    public void parallelMultiplicationBenchmark() { parallelMultiplication.multiply(matrixA, matrixB); }

    @Benchmark
    public void blockMultiplicationBenchmark() { blockMultiplication.multiply(matrixA, matrixB); }

}
