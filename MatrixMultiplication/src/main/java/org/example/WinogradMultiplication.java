package org.example;

public class WinogradMultiplication implements MatrixMultiplier {

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        if (colsA != matrixB.length) {
            throw new IllegalArgumentException("The number of columns of A must be equal to the number of rows of B.");
        }

        double[][] result = new double[rowsA][colsB];

        double[] rowFactors = new double[rowsA];
        double[] colFactors = new double[colsB];

        for (int i = 0; i < rowsA; i++) {
            rowFactors[i] = 0;
            for (int k = 0; k < colsA / 2 * 2; k += 2) {
                rowFactors[i] += matrixA[i][k] * matrixA[i][k + 1];
            }
        }

        for (int j = 0; j < colsB; j++) {
            colFactors[j] = 0;
            for (int k = 0; k < matrixB.length / 2 * 2; k += 2) {
                colFactors[j] += matrixB[k][j] * matrixB[k + 1][j];
            }
        }

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                double sum = -rowFactors[i] - colFactors[j];
                for (int k = 0; k < colsA; k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                result[i][j] = sum;
            }
        }

        return result;
    }
}
