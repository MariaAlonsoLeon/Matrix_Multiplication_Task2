package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;

import static org.example.MatrixGenerator.generateSparseMatrix;
import static org.example.MatrixGenerator.generateMatrix;

public class MatrixSizeTest {

    private static final int BASE_TIME_THRESHOLD_MS = 2000;
    private static final int BASE_MEMORY_THRESHOLD_MB = 500;
    private static final String OUTPUT_FILE = "tests_size_results.json";
    private static final List<TestResult> testResults = new ArrayList<>();

    public static void main(String[] args) {
        int[] sizes = {64, 128, 256, 512, 1024, 2048};
        double[] sparsityLevels = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9};

        runTestsForDenseMatrices(sizes);
        runTestsForSparseMatrices(sizes, sparsityLevels);

        saveResultsToJson();
    }

    private static void runTestsForDenseMatrices(int[] sizes) {
        System.out.println("Dense Matrix Tests:");
        BasicMultiplication basicMultiplier = new BasicMultiplication();
        StrassenMultiplication strassenMultiplier = new StrassenMultiplication();
        OptimizedCacheMultiplication optimizedMultiplier = new OptimizedCacheMultiplication();
        WinogradMultiplication winogradMultiplier = new WinogradMultiplication();
        RowColumnMajorMultiplication rowColumnMajorMultiplier = new RowColumnMajorMultiplication();
        ParallelMultiplication parallelMultiplier = new ParallelMultiplication();
        BlockMultiplication blockMultiplier = new BlockMultiplication();

        for (int size : sizes) {
            double[][] matrixA = generateMatrix(size, size);
            double[][] matrixB = generateMatrix(size, size);

            System.out.println("\nMatrix size: " + size + "x" + size);

            testMatrixMultiplication("Basic", basicMultiplier, matrixA, matrixB, size, 0);
            testMatrixMultiplication("Strassen", strassenMultiplier, matrixA, matrixB, size, 0);
            testMatrixMultiplication("Optimized (Cache)", optimizedMultiplier, matrixA, matrixB, size, 0);
            testMatrixMultiplication("Winograd", winogradMultiplier, matrixA, matrixB, size, 0);
            testMatrixMultiplication("Row-Column Major", rowColumnMajorMultiplier, matrixA, matrixB, size, 0);
            testMatrixMultiplication("Parallel", parallelMultiplier, matrixA, matrixB, size, 0);
            testMatrixMultiplication("Block", blockMultiplier, matrixA, matrixB, size, 0);
        }
    }

    private static void runTestsForSparseMatrices(int[] sizes, double[] sparsityLevels) {
        System.out.println("\nSparse Matrix Tests:");
        BasicMultiplication basicMultiplier = new BasicMultiplication();
        StrassenMultiplication strassenMultiplier = new StrassenMultiplication();
        OptimizedCacheMultiplication optimizedMultiplier = new OptimizedCacheMultiplication();
        WinogradMultiplication winogradMultiplier = new WinogradMultiplication();
        RowColumnMajorMultiplication rowColumnMajorMultiplier = new RowColumnMajorMultiplication();
        ParallelMultiplication parallelMultiplier = new ParallelMultiplication();
        BlockMultiplication blockMultiplier = new BlockMultiplication();
        CSCMultiplication cscMultiplier = new CSCMultiplication();
        CSRMultiplication csrMultiplier = new CSRMultiplication();

        for (int size : sizes) {
            for (double sparsity : sparsityLevels) {
                double[][] sparseMatrixA = generateSparseMatrix(size, sparsity);
                double[][] sparseMatrixB = generateSparseMatrix(size, sparsity);

                System.out.println("\nSparse matrix size: " + size + "x" + size + " with sparsity " + (sparsity * 100) + "%");

                testMatrixMultiplication("Basic", basicMultiplier, sparseMatrixA, sparseMatrixB, size, sparsity);
                testMatrixMultiplication("Strassen", strassenMultiplier, sparseMatrixA, sparseMatrixB, size, sparsity);
                testMatrixMultiplication("Optimized (Cache)", optimizedMultiplier, sparseMatrixA, sparseMatrixB, size, sparsity);
                testMatrixMultiplication("Winograd", winogradMultiplier, sparseMatrixA, sparseMatrixB, size, sparsity);
                testMatrixMultiplication("Row-Column Major", rowColumnMajorMultiplier, sparseMatrixA, sparseMatrixB, size, sparsity);
                testMatrixMultiplication("Parallel", parallelMultiplier, sparseMatrixA, sparseMatrixB, size, sparsity);
                testMatrixMultiplication("Block", blockMultiplier, sparseMatrixA, sparseMatrixB, size, sparsity);
                testMatrixMultiplication("CSC", cscMultiplier, sparseMatrixA, sparseMatrixB, size, sparsity);
                testMatrixMultiplication("CSR", csrMultiplier, sparseMatrixA, sparseMatrixB, size, sparsity);
            }
        }
    }

    private static void testMatrixMultiplication(String methodName, MatrixMultiplier method, double[][] matrixA, double[][] matrixB, int matrixSize, double sparsity) {
        int timeThreshold = calculateTimeThreshold(matrixSize);
        int memoryThreshold = calculateMemoryThreshold(matrixSize);

        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage beforeMemoryUsage = memoryBean.getHeapMemoryUsage();
        long startTime = System.nanoTime();

        method.multiply(matrixA, matrixB);

        long endTime = System.nanoTime();
        MemoryUsage afterMemoryUsage = memoryBean.getHeapMemoryUsage();

        double executionTimeMs = (endTime - startTime) / 1_000_000.0;

        long memoryUsage = (afterMemoryUsage.getUsed() - beforeMemoryUsage.getUsed()) / (1024 * 1024);
        memoryUsage = Math.max(memoryUsage, 0);

        boolean isEfficientTime = executionTimeMs <= timeThreshold;
        boolean isEfficientMemory = memoryUsage <= memoryThreshold;

        System.out.printf("Method: %s | Execution time: %.3f ms | Memory used: %d MB%n", methodName, executionTimeMs, memoryUsage);

        if (!isEfficientTime) {
            System.out.printf("Warning: Matrix size has exceeded the allowed time limit (%.3f ms).%n", (double) timeThreshold);
        }
        if (!isEfficientMemory) {
            System.out.printf("Warning: Matrix size has exceeded the allowed memory limit (%d MB).%n", memoryThreshold);
        }

        testResults.add(new TestResult(methodName, matrixSize, sparsity, executionTimeMs, memoryUsage, isEfficientTime, isEfficientMemory));
    }

    private static int calculateTimeThreshold(int matrixSize) {
        return BASE_TIME_THRESHOLD_MS * (matrixSize / 64);
    }

    private static int calculateMemoryThreshold(int matrixSize) {
        return BASE_MEMORY_THRESHOLD_MB * (matrixSize / 64);
    }

    private static void saveResultsToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(OUTPUT_FILE)) {
            gson.toJson(testResults, writer);
            System.out.println("Results saved to " + OUTPUT_FILE);
        } catch (IOException e) {
            System.err.println("Error saving results to JSON: " + e.getMessage());
        }
    }
}
