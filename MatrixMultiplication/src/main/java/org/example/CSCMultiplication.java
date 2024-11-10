package org.example;

import java.util.ArrayList;
import java.util.List;

public class CSCMultiplication implements MatrixMultiplier {

    private class CSCMatrix {
        List<Double> values;
        List<Integer> rowIndices;
        List<Integer> colPointers;

        public CSCMatrix(int cols) {
            values = new ArrayList<>();
            rowIndices = new ArrayList<>();
            colPointers = new ArrayList<>();
            for (int i = 0; i <= cols; i++) {
                colPointers.add(0);
            }
        }

        public void add(int row, int col, double value) {
            values.add(value);
            rowIndices.add(row);
            colPointers.set(col + 1, colPointers.get(col + 1) + 1);
        }

        public void finalizeStructure() {
            for (int i = 1; i < colPointers.size(); i++) {
                colPointers.set(i, colPointers.get(i) + colPointers.get(i - 1));
            }
        }
    }

    private CSCMatrix toCSC(double[][] matrix) {
        int cols = matrix[0].length;
        CSCMatrix cscMatrix = new CSCMatrix(cols);

        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < matrix.length; i++) {
                if (matrix[i][j] != 0) {
                    cscMatrix.add(i, j, matrix[i][j]);
                }
            }
        }
        cscMatrix.finalizeStructure();
        return cscMatrix;
    }

    @Override
    public double[][] multiply(double[][] matrixA, double[][] matrixB) {
        int rowsA = matrixA.length;
        int colsB = matrixB[0].length;

        CSCMatrix cscA = toCSC(matrixA);
        CSCMatrix cscB = toCSC(matrixB);

        double[][] result = new double[rowsA][colsB];

        for (int colB = 0; colB < colsB; colB++) {
            int start = cscB.colPointers.get(colB);
            int end = cscB.colPointers.get(colB + 1);

            for (int i = start; i < end; i++) {
                int rowB = cscB.rowIndices.get(i);
                double valB = cscB.values.get(i);

                int rowStart = cscA.colPointers.get(rowB);
                int rowEnd = cscA.colPointers.get(rowB + 1);

                for (int j = rowStart; j < rowEnd; j++) {
                    int rowA = cscA.rowIndices.get(j);
                    double valA = cscA.values.get(j);

                    result[rowA][colB] += valA * valB;
                }
            }
        }
        return result;
    }
}
