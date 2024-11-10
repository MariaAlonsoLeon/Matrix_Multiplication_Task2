package org.example;

import java.util.Random;

public class MatrixGenerator {

    public static double[][] generateMatrix(int rows, int cols) {
        double[][] matrix = new double[rows][cols];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextDouble() * 10;
            }
        }
        return matrix;
    }

    public static double[][] generateSparseMatrix(int size, double sparsityLevel) {
        double[][] matrix = new double[size][size];
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextDouble() >= sparsityLevel) {
                    matrix[i][j] = random.nextDouble() * 10;
                } else {
                    matrix[i][j] = 0;
                }
            }
        }
        return matrix;
    }
}