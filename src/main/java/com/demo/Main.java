package com.demo;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;

public class Main {
    public static boolean test_transpose(RealMatrix matrix) {
        int rows = matrix.getRowDimension();
        int cols = matrix.getColumnDimension();
        RealMatrix transposed = new Array2DRowRealMatrix(cols, rows);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed.setEntry(j, i, matrix.getEntry(i, j));
            }
        }
        return transposed.equals(matrix.transpose());
    }

    public static boolean test_multiply(RealMatrix matrix1, RealMatrix matrix2) {
        int rows1 = matrix1.getRowDimension();
        int cols1 = matrix1.getColumnDimension();
        int rows2 = matrix2.getRowDimension();
        int cols2 = matrix2.getColumnDimension();
        
        if (cols1 != rows2) {
            throw new IllegalArgumentException("Incompatible matrix dimensions for multiplication.");
        }

        RealMatrix result = new Array2DRowRealMatrix(rows1, cols2);
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                double sum = 0;
                for (int k = 0; k < cols1; k++) {
                    sum += matrix1.getEntry(i, k) * matrix2.getEntry(k, j);
                }
                result.setEntry(i, j, sum);
            }
        }
        return result.equals(matrix1.multiply(matrix2));
    }

    public static boolean test_add(RealMatrix matrix1, RealMatrix matrix2) {
        int rows1 = matrix1.getRowDimension();
        int cols1 = matrix1.getColumnDimension();
        int rows2 = matrix2.getRowDimension();
        int cols2 = matrix2.getColumnDimension();

        if (rows1 != rows2 || cols1 != cols2) {
            throw new IllegalArgumentException("Incompatible matrix dimensions for addition.");
        }

        RealMatrix result = new Array2DRowRealMatrix(rows1, cols1);
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols1; j++) {
                result.setEntry(i, j, matrix1.getEntry(i, j) + matrix2.getEntry(i, j));
            }
        }
        return result.equals(matrix1.add(matrix2));
    }

    public static boolean test_subtract(RealMatrix matrix1, RealMatrix matrix2) {
        int rows1 = matrix1.getRowDimension();
        int cols1 = matrix1.getColumnDimension();
        int rows2 = matrix2.getRowDimension();
        int cols2 = matrix2.getColumnDimension();
        
        if (rows1 != rows2 || cols1 != cols2) {
            throw new IllegalArgumentException("Incompatible matrix dimensions for subtraction.");
        }

        RealMatrix result = new Array2DRowRealMatrix(rows1, cols1);
        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols1; j++) {
                result.setEntry(i, j, matrix1.getEntry(i, j) - matrix2.getEntry(i, j));
            }
        }
        return result.equals(matrix1.subtract(matrix2));
    }

    public static boolean test_scalarMultiply(RealMatrix matrix, double scalar) {
        int rows = matrix.getRowDimension();
        int cols = matrix.getColumnDimension();

        RealMatrix result = new Array2DRowRealMatrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.setEntry(i, j, matrix.getEntry(i, j) * scalar);
            }
        }
        return result.equals(matrix.scalarMultiply(scalar));
    }

    public static void printMatrix(RealMatrix matrix) {
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            for (int j = 0; j < matrix.getColumnDimension(); j++) {
                System.out.print(matrix.getEntry(i, j) + " ");
            }
            System.out.println();
        }
        System.out.println("-----------------");
    }

    public static void main(String[] args) {
        double[][] data = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 8, 9 }
        };
        RealMatrix A = new Array2DRowRealMatrix(data);
        System.out.println("Matrix A:");
        printMatrix(A);

        RealMatrix AT = A.transpose();
        System.out.println("Transposed Matrix AT:");
        printMatrix(AT);

        // 控制输出颜色
        String Failed = "\033[31;4m" + "Failed" + "\033[0m";
        String Passed = "\033[32;4m" + "Passed" + "\033[0m";
        System.out.println("Testing transpose: " + (test_transpose(A) ? Passed : Failed));
        System.out.println("Testing multiply: " + (test_multiply(A, AT) ? Passed : Failed));
        System.out.println("Testing add: " + (test_add(A, AT) ? Passed : Failed));
        System.out.println("Testing subtract: " + (test_subtract(A, AT) ? Passed : Failed));
        System.out.println("Testing scalar multiply: " + (test_scalarMultiply(A, 2) ? Passed : Failed));
    }
}