package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SparseColumnMatrix {
    final int numRows;
    final int numCols;
    final HashMap<Integer, List<int[]>> columns;

    public SparseColumnMatrix(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.columns = new HashMap<>();
    }

    public void addElement(int row, int col, int value) {
        columns.putIfAbsent(col, new ArrayList<>());
        columns.get(col).add(new int[]{row, value});
    }

    public List<int[]> getColumn(int col) {
        return columns.getOrDefault(col, new ArrayList<>());
    }

    public static SparseColumnMatrix fromCOO(String filepath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("%") || line.startsWith("%%")) {
                    continue;
                }

                String[] dimensions = line.split(" ");
                int rows = Integer.parseInt(dimensions[0]);
                int cols = Integer.parseInt(dimensions[1]);

                SparseColumnMatrix matrix = new SparseColumnMatrix(rows, cols);

                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    if (line.isEmpty() || line.startsWith("%")) {
                        continue;
                    }

                    String[] parts = line.split(" ");
                    int row = Integer.parseInt(parts[0]) - 1;
                    int col = Integer.parseInt(parts[1]) - 1;
                    int value = Integer.parseInt(parts[2]);

                    matrix.addElement(row, col, value);
                }

                return matrix;
            }

            throw new IOException("Empty file or no matrix data");
        }
    }

    public void printFirstNLines(int n) {
        int count = 0;
        for (int col : columns.keySet()) {
            List<int[]> elements = columns.get(col);
            for (int[] element : elements) {
                if (count < n) {
                    System.out.println("Row: " + element[0] + ", Column: " + col + ", Value: " + element[1]);
                    count++;
                } else {
                    return;
                }
            }
        }
    }

    public static SparseColumnMatrix multiply(SparseColumnMatrix matrixA, SparseColumnMatrix matrixB) {
        SparseColumnMatrix result = new SparseColumnMatrix(matrixA.numRows, matrixB.numCols);

        for (int colB : matrixB.columns.keySet()) {
            List<int[]> elementsB = matrixB.getColumn(colB);

            for (int[] elemB : elementsB) {
                int rowB = elemB[0];
                int valueB = elemB[1];

                List<int[]> elementsA = matrixA.getColumn(rowB);
                if (elementsA != null) {
                    for (int[] elemA : elementsA) {
                        int rowA = elemA[0];
                        int valueA = elemA[1];

                        int product = valueA * valueB;
                        result.addElement(rowA, colB, product);
                    }
                }
            }
        }
        return result;
    }

    public void checkMultiplicationResult(int row, int col, int expectedValue) {
        List<int[]> resultCol = columns.get(col);
        for (int[] element : resultCol) {
            if (element[0] == row) {
                if (element[1] == expectedValue) {
                    System.out.println("Correct multiplication at [" + row + ", " + col + "] -> Value: " + element[1]);
                } else {
                    System.out.println("Multiplication error at [" + row + ", " + col + "] -> Expected: " + expectedValue + ", Got: " + element[1]);
                }
                return;
            }
        }
        System.out.println("Element not found at [" + row + ", " + col + "]");
    }

    public void printMatrix() {
        columns.forEach((col, elements) -> {
            System.out.println("Column " + col + ": " + elements);
        });
    }
}
