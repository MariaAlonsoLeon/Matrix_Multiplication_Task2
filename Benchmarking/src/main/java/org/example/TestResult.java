package org.example;

public class TestResult {
    private String methodName;
    private int matrixSize;
    private double sparsity;
    private double executionTime;
    private long memoryUsage;
    private boolean isEfficientTime;
    private boolean isEfficientMemory;

    public TestResult(String methodName, int matrixSize, double sparsity, double executionTime, long memoryUsage, boolean isEfficientTime, boolean isEfficientMemory) {
        this.methodName = methodName;
        this.matrixSize = matrixSize;
        this.sparsity = sparsity;
        this.executionTime = executionTime;
        this.memoryUsage = memoryUsage;
        this.isEfficientTime = isEfficientTime;
        this.isEfficientMemory = isEfficientMemory;
    }
}