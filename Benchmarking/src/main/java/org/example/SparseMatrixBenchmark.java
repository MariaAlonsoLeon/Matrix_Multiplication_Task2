package org.example;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import static org.example.MatrixGenerator.generateSparseMatrix;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SparseMatrixBenchmark {

    @Param({"64", "128", "256", "512"})
    private int matrixSize;

    @Param({"0.7", "0.8", "0.9"})
    private double sparsityLevel;

    private double[][] matrixA;
    private double[][] matrixB;

    private BasicMultiplication basicMultiplier;
    private StrassenMultiplication strassenMultiplier;
    private OptimizedCacheMultiplication optimizedMultiplier;
    private WinogradMultiplication winogradMultiplication;
    private RowColumnMajorMultiplication rowColumnMajorMultiplication;

    private COOMultiplication cooMultiplier;
    private CSCMultiplication cscMultiplier;
    private CSRMultiplication csrMultiplier;

    @Setup(Level.Trial)
    public void setUp() {
        matrixA = generateSparseMatrix(matrixSize, sparsityLevel);
        matrixB = generateSparseMatrix(matrixSize, sparsityLevel);

        basicMultiplier = new BasicMultiplication();
        strassenMultiplier = new StrassenMultiplication();
        optimizedMultiplier = new OptimizedCacheMultiplication();
        winogradMultiplication = new WinogradMultiplication();
        rowColumnMajorMultiplication = new RowColumnMajorMultiplication();

        cooMultiplier = new COOMultiplication();
        cscMultiplier = new CSCMultiplication();
        csrMultiplier = new CSRMultiplication();
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
    public void cooMultiplicationBenchmark() {
        cooMultiplier.multiply(matrixA, matrixB);
    }

    @Benchmark
    public void cscMultiplicationBenchmark() {
        cscMultiplier.multiply(matrixA, matrixB);
    }

    @Benchmark
    public void csrMultiplicationBenchmark() {
        csrMultiplier.multiply(matrixA, matrixB);
    }
}
