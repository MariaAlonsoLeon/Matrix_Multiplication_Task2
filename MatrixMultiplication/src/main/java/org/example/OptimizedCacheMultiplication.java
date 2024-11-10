package org.example;

public class OptimizedCacheMultiplication implements MatrixMultiplier {

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        double[][] transposedB = transpose(matrixB, colsA, colsB);

        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                int sum = 0;
                int k = 0;

                for (; k <= colsA - 2; k += 2) {
                    sum += matrixA[i][k] * transposedB[j][k]
                            + matrixA[i][k + 1] * transposedB[j][k + 1];
                }

                if (k < colsA) {
                    sum += matrixA[i][k] * transposedB[j][k];
                }

                result[i][j] = sum;
            }
        }

        return result;
    }

    private double[][] transpose(double[][] matrix, int rows, int cols) {
        double[][] transposed = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }
}
