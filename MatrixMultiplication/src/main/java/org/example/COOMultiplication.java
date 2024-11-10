package org.example;

import java.util.ArrayList;
import java.util.List;

public class COOMultiplication implements MatrixMultiplier {

    private class COOMatrix {
        List<Integer> rows;
        List<Integer> cols;
        List<Double> values;

        public COOMatrix() {
            rows = new ArrayList<>();
            cols = new ArrayList<>();
            values = new ArrayList<>();
        }

        public void add(int row, int col, double value) {
            rows.add(row);
            cols.add(col);
            values.add(value);
        }
    }

    private COOMatrix toCOO(double[][] matrix) {
        COOMatrix cooMatrix = new COOMatrix();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    cooMatrix.add(i, j, matrix[i][j]);
                }
            }
        }
        return cooMatrix;
    }

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsB = matrixB[0].length;

        COOMatrix cooA = toCOO(matrixA);
        COOMatrix cooB = toCOO(matrixB);

        double[][] result = new double[rowsA][colsB];

        for (int i = 0; i < cooA.values.size(); i++) {
            int rowA = cooA.rows.get(i);
            int colA = cooA.cols.get(i);
            double valA = cooA.values.get(i);

            for (int j = 0; j < cooB.values.size(); j++) {
                int rowB = cooB.rows.get(j);
                int colB = cooB.cols.get(j);
                double valB = cooB.values.get(j);

                if (colA == rowB) {
                    result[rowA][colB] += valA * valB;
                }
            }
        }
        return result;
    }
}