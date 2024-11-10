package org.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelMultiplication implements MatrixMultiplier {

    private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        double[][] result = new double[rowsA][colsB];

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        try {
            Future<?>[] futures = new Future<?>[rowsA];
            for (int i = 0; i < rowsA; i++) {
                final int row = i;
                futures[i] = executor.submit(() -> multiplyRow(matrixA, matrixB, result, row));
            }

            for (Future<?> future : futures) {
                future.get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        return result;
    }

    private void multiplyRow(double[][] matrixA, double[][] matrixB, double[][] result, int row) {
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        for (int j = 0; j < colsB; j++) {
            result[row][j] = 0;
            for (int k = 0; k < colsA; k++) {
                result[row][j] += matrixA[row][k] * matrixB[k][j];
            }
        }
    }
}
