# Matrix Multiplication Benchmarking Project

This project focuses on benchmarking the performance of various matrix multiplication algorithms. The objective is to evaluate the execution time, memory usage of algorithms across different matrix sizes and sparsity levels. The performance is tested using both dense and sparse matrices, simulating real-world computational challenges in Big Data applications.

## Cover Page:

- **Subject:** Big Data (BD)
- **Academic Year:** 2024-2025
- **Degree:** Data Science and Engineering (GCID)
- **School:** School of Computer Engineering (EII)
- **University:** University of Las Palmas de Gran Canaria (ULPGC)

## Development Environment:

- **IDEs/Editors Used:** IntelliJ.
- **Version Control:** Git & GitHub for source code management and collaboration.

## Folder Structure

The repository is organized into several key modules, each of which plays a specific role in the matrix multiplication benchmarking process:

### 1. **Benchmarking Module**
   - This module contains three benchmark tests:
     - A test for dense matrices.
     - A test for sparse matrices (with sparsity levels 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8 and 0.9).
     - A comprehensive test that measures both execution time and memory usage.
   - Results are stored in JSON format for further analysis.
For a better understanding, please refer to the paper.

### 2. **MatrixMultiplicationFile Module**
   - Allows for matrix multiplication using matrices loaded from files, particularly with the COO format.
   - Tracks both execution time and memory usage for each matrix multiplication operation.
   
### 3. **MatrixGenerator Module**
   - Provides a class to generate dense and sparse matrices of specified sizes and sparsity levels (ranging from 0.1 to 0.9).
   - Useful for creating various test matrices for benchmarking.

### 4. **MatrixMultiplication Module**
   - Contains multiple matrix multiplication algorithms, all implementing the `MatrixMultiplier` interface.
   - Algorithms include basic matrix multiplication, optimized cache multiplication, and advanced methods such as Strassen’s algorithm.

## Dependencies

- `JMH` (Java Microbenchmarking Harness) for benchmarking.
- `ManagementFactory` for system resource tracking.

## Results

The results of the benchmarking experiments are available in the form of JSON files. These files contain detailed performance metrics such as execution time, memory usage, and error rates for each tested algorithm. The performance data are visualized using line graphs created with Python’s `matplotlib` library. The following are some of the main findings from the experiments:

- **Dense Matrices**:
  - Strassen’s algorithm exhibited high execution times for larger matrices, underperforming compared to optimized algorithms like Block and RowColumnMajor.
  - Algorithms like Block and RowColumnMajor performed significantly better as matrix size increased.
  
- **Sparse Matrices (Sparsity 0.7, 0.8, and 0.9)**:
  - The CSR, and CSC algorithms showed improved performance with increasing sparsity levels, particularly in terms of execution time.
  - Strassen and Winograd showed higher errors and execution times in sparse matrices, especially at higher sparsity levels.
  
- **Error Metrics**:
  - Error rates were tracked for each matrix multiplication algorithm. Algorithms optimized for sparse matrices generally had lower errors compared to the basic and Strassen algorithms.

- **Memory Usage**:
  - Memory consumption was measured and tracked for each algorithm. The Winograd algorithm, for example, showed the best memory efficiency for dense matrices, while CSR and CSC excelled in sparse matrix multiplication.

## Usage

To use the project, follow these steps:

1. Clone the repository to your local machine:
   ```bash
   git clone https://github.com/your-repository-url.git
   ```
2. Open the project in IntelliJ IDEA.
3. Once the project is opened in IntelliJ, navigate to the `Benchmarking/` module or the module corresponding to the programming language you want to test.
4. In IntelliJ, locate the test classes under the src/test/java directory, and right-click on the test class and select Run to execute the tests.
5. The tests will execute and generate benchmarking results, which will be stored in JSON format for further analysis.
