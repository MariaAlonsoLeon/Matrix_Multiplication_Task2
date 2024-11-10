package org.example;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class Main {
    public static void main(String[] args) {
        try {
            String filepath = "mc2depi.mtx";

            System.out.println("Loading sparse matrix...");
            SparseColumnMatrix matrix = SparseColumnMatrix.fromCOO(filepath);

            System.out.println("First 100 rows of the sparse matrix:");
            matrix.printFirstNLines(100);

            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage beforeMemoryUsage = memoryBean.getHeapMemoryUsage();

            long startTime = System.nanoTime();

            SparseColumnMatrix finalResultMatrix = SparseColumnMatrix.multiply(matrix, matrix);
            System.out.println("First 100 rows of the resulting sparse matrix:");
            finalResultMatrix.printFirstNLines(100);

            long endTime = System.nanoTime();

            MemoryUsage afterMemoryUsage = memoryBean.getHeapMemoryUsage();

            long executionTimeMs = (endTime - startTime) / 1_000_000;
            long memoryUsage = (afterMemoryUsage.getUsed() - beforeMemoryUsage.getUsed()) / (1024 * 1024);

            System.out.println("Execution time: " + executionTimeMs + " ms");
            System.out.println("Additional memory usage: " + memoryUsage + " MB");

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
