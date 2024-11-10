package org.example;

public class BlockMultiplication implements MatrixMultiplier {

    private static final int BLOCK_SIZE = 64;

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < rowsA; i += BLOCK_SIZE) {
            for (int j = 0; j < colsB; j += BLOCK_SIZE) {
                for (int k = 0; k < colsA; k += BLOCK_SIZE) {
                    multiplyBlock(matrixA, matrixB, result, i, j, k);
                }
            }
        }

        return result;
    }

    private void multiplyBlock(double[][] matrixA, double[][] matrixB, double[][] result, int row, int col, int kBlock) {
        int maxI = Math.min(row + BLOCK_SIZE, matrixA.length);
        int maxJ = Math.min(col + BLOCK_SIZE, result[0].length);
        int maxK = Math.min(kBlock + BLOCK_SIZE, matrixA[0].length);

        for (int i = row; i < maxI; i++) {
            for (int j = col; j < maxJ; j++) {
                double sum = 0;
                for (int k = kBlock; k < maxK; k++) {
                    sum += matrixA[i][k] * matrixB[k][j];
                }
                result[i][j] += sum;
            }
        }
    }
}
