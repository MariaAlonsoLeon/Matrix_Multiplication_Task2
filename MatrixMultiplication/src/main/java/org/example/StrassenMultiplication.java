package org.example;

public class StrassenMultiplication implements MatrixMultiplier {

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int n = matrixA.length;
        return strassen(matrixA, matrixB, n);
    }

    private double[][] strassen(double[][] A, double[][] B, int size) {
        if (size == 1) {
            double[][] result = {{A[0][0] * B[0][0]}};
            return result;
        }

        int newSize = size / 2;
        double[][] a11 = new double[newSize][newSize];
        double[][] a12 = new double[newSize][newSize];
        double[][] a21 = new double[newSize][newSize];
        double[][] a22 = new double[newSize][newSize];
        double[][] b11 = new double[newSize][newSize];
        double[][] b12 = new double[newSize][newSize];
        double[][] b21 = new double[newSize][newSize];
        double[][] b22 = new double[newSize][newSize];

        split(A, a11, 0, 0);
        split(A, a12, 0, newSize);
        split(A, a21, newSize, 0);
        split(A, a22, newSize, newSize);
        split(B, b11, 0, 0);
        split(B, b12, 0, newSize);
        split(B, b21, newSize, 0);
        split(B, b22, newSize, newSize);

        double[][] m1 = strassen(add(a11, a22), add(b11, b22), newSize);
        double[][] m2 = strassen(add(a21, a22), b11, newSize);
        double[][] m3 = strassen(a11, subtract(b12, b22), newSize);
        double[][] m4 = strassen(a22, subtract(b21, b11), newSize);
        double[][] m5 = strassen(add(a11, a12), b22, newSize);
        double[][] m6 = strassen(subtract(a21, a11), add(b11, b12), newSize);
        double[][] m7 = strassen(subtract(a12, a22), add(b21, b22), newSize);

        double[][] c11 = add(subtract(add(m1, m4), m5), m7);
        double[][] c12 = add(m3, m5);
        double[][] c21 = add(m2, m4);
        double[][] c22 = add(subtract(add(m1, m3), m2), m6);

        double[][] result = new double[size][size];
        join(c11, result, 0, 0);
        join(c12, result, 0, newSize);
        join(c21, result, newSize, 0);
        join(c22, result, newSize, newSize);

        return result;
    }

    private double[][] add(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] + B[i][j];
            }
        }
        return result;
    }

    private double[][] subtract(double[][] A, double[][] B) {
        int n = A.length;
        double[][] result = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = A[i][j] - B[i][j];
            }
        }
        return result;
    }

    private void split(double[][] P, double[][] C, int iB, int jB) {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++) {
            for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++) {
                C[i1][j1] = P[i2][j2];
            }
        }
    }

    private void join(double[][] C, double[][] P, int iB, int jB) {
        for (int i1 = 0, i2 = iB; i1 < C.length; i1++, i2++) {
            for (int j1 = 0, j2 = jB; j1 < C.length; j1++, j2++) {
                P[i2][j2] = C[i1][j1];
            }
        }
    }
}
