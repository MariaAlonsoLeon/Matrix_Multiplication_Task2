package org.example;

import java.util.ArrayList;
import java.util.List;

public class CSRMultiplication implements MatrixMultiplier {

    private class CSRMatrix {
        List<Double> values;
        List<Integer> colIndices;
        List<Integer> rowPointers;

        public CSRMatrix(int rows) {
            values = new ArrayList<>();
            colIndices = new ArrayList<>();
            rowPointers = new ArrayList<>();
            for (int i = 0; i <= rows; i++) {
                rowPointers.add(0);
            }
        }

        public void add(int row, int col, double value) {
            values.add(value);
            colIndices.add(col);
            rowPointers.set(row + 1, rowPointers.get(row + 1) + 1);
        }

        public void finalizeStructure() {
            for (int i = 1; i < rowPointers.size(); i++) {
                rowPointers.set(i, rowPointers.get(i) + rowPointers.get(i - 1));
            }
        }
    }

    private CSRMatrix toCSR(double[][] matrix) {
        int rows = matrix.length;
        CSRMatrix csrMatrix = new CSRMatrix(rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] != 0) {
                    csrMatrix.add(i, j, matrix[i][j]);
                }
            }
        }
        csrMatrix.finalizeStructure();
        return csrMatrix;
    }

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsB = matrixB[0].length;

        CSRMatrix csrA = toCSR(matrixA);
        CSRMatrix csrB = toCSR(matrixB);

        double[][] result = new double[rowsA][colsB];

        for (int rowA = 0; rowA < rowsA; rowA++) {
            int startA = csrA.rowPointers.get(rowA);
            int endA = csrA.rowPointers.get(rowA + 1);

            for (int i = startA; i < endA; i++) {
                int colA = csrA.colIndices.get(i);
                double valA = csrA.values.get(i);

                int startB = csrB.rowPointers.get(colA);
                int endB = csrB.rowPointers.get(colA + 1);

                for (int j = startB; j < endB; j++) {
                    int colB = csrB.colIndices.get(j);
                    double valB = csrB.values.get(j);

                    result[rowA][colB] += valA * valB;
                }
            }
        }
        return result;
    }
}