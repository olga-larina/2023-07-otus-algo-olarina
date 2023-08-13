package ru.otus.matrix;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * Middle. 14. Матрицы. Умножение. Возведение в степень
 */
public class Matrix2D {

    public static final Matrix2D IDENTITY = new Matrix2D(new BigInteger[][] {
        new BigInteger[] { BigInteger.ONE, BigInteger.ZERO },
        new BigInteger[] { BigInteger.ONE, BigInteger.ZERO }
    });

    public static final Matrix2D BASE = new Matrix2D(new BigInteger[][] {
        new BigInteger[] { BigInteger.ONE, BigInteger.ONE },
        new BigInteger[] { BigInteger.ONE, BigInteger.ZERO }
    });

    private final BigInteger[][] matrix;

    public Matrix2D(BigInteger[][] matrix) {
        this(matrix, true);
    }

    private Matrix2D(BigInteger[][] matrix, boolean copy) {
        if (copy) {
            this.matrix = Arrays.copyOf(matrix, matrix.length);
        } else {
            this.matrix = matrix;
        }
    }

    public BigInteger elementAt(int row, int col) {
        return matrix[row][col];
    }

    public int rows() {
        return matrix.length;
    }

    public int cols() {
        return matrix[0].length;
    }

    /**
     * Умножение матриц
     * @param other другая матрица
     * @return матрица l x m умножить на матрицу m x n => матрица l x n
     */
    public Matrix2D multiply(Matrix2D other) {
        if (cols() != other.rows()) {
            throw new IllegalArgumentException();
        }
        BigInteger[][] res = new BigInteger[rows()][other.cols()];
        for (int l = 0; l < matrix.length; l++) {
            for (int n = 0; n < other.matrix[0].length; n++) {
                BigInteger sum = BigInteger.ZERO;
                for (int m = 0; m < matrix[l].length; m++) {
                    sum = sum.add(matrix[l][m].multiply(other.matrix[m][n]));
                }
                res[l][n] = sum;
            }
        }
        return new Matrix2D(res, false);
    }

    /**
     * Возведение в степень
     * @param pow степень
     * @return матрица
     */
    public static Matrix2D power(int pow) {
        Matrix2D res = IDENTITY;
        Matrix2D base = BASE;
        while (pow > 1) {
            if ((pow & 1) == 1) {
                res = res.multiply(base);
            }
            base = base.multiply(base);
            pow >>= 1;
        }
        return res.multiply(base);
    }
}
