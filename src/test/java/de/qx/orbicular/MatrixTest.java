package de.qx.orbicular;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class MatrixTest {

    @Test
    public void testMatrixMultiplication() {
        Matrix m1 = new Matrix(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        Matrix m2 = new Matrix(new double[]{0, 1, 2, 3, 4, 5, 6, 7, 8});
        Matrix result = m1.multiplyWith(m2);

        assertArrayEquals(new double[]{24, 30, 36, 51, 66, 81, 78, 102, 126}, result.data, 0.0001);
    }

    @Test
    public void testMatrixMultiplicationWithSmallMatrix() {
        Matrix m1 = new Matrix(new double[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        Matrix m2 = new Matrix(new double[]{0, 1, 2});
        Matrix result = m1.multiplyWith(m2);

        assertArrayEquals(new double[]{8, 17, 26}, result.data, 0.0001);
    }

    @Test
    public void testMirrorMatrixCreation() {
        Matrix m1 = Matrix.getMirrorMatrix(1);
        Matrix m2 = Matrix.getMirrorMatrix(2);
        Matrix m3 = Matrix.getMirrorMatrix(3);

        assertArrayEquals(new double[]{-1, 0, 0, 0, 1, 0, 0, 0, 1}, m1.data, 0.0001);
        assertArrayEquals(new double[]{1, 0, 0, 0, -1, 0, 0, 0, 1}, m2.data, 0.0001);
        assertArrayEquals(new double[]{1, 0, 0, 0, 1, 0, 0, 0, -1}, m3.data, 0.0001);
    }

    @Test
    public void testRotationMatrixCreation() {
        Matrix m1 = Matrix.getRotationMatrix(0, 1);
        Matrix m2 = Matrix.getRotationMatrix(2 * Math.PI, 2);
        Matrix m3 = Matrix.getRotationMatrix(-4 * Math.PI, 3);
        Matrix m4 = Matrix.getRotationMatrix(Math.PI / 4, 1);
        Matrix m5 = Matrix.getRotationMatrix(-Math.PI / 3, 2);
        Matrix m6 = Matrix.getRotationMatrix(7 * Math.PI / 6, 3);

        double sqrt1_2 = Math.sqrt(1.0 / 2.0);
        double sqrt3_2 = Math.sqrt(3.0) / 2.0;

        assertArrayEquals(new double[]{1, 0, 0, 0, 1, 0, 0, 0, 1}, m1.data, 0.0001);
        assertArrayEquals(new double[]{1, 0, 0, 0, 1, 0, 0, 0, 1}, m2.data, 0.0001);
        assertArrayEquals(new double[]{1, 0, 0, 0, 1, 0, 0, 0, 1}, m3.data, 0.0001);
        assertArrayEquals(new double[]{1, 0, 0, 0, sqrt1_2, sqrt1_2, 0, -sqrt1_2, sqrt1_2}, m4.data, 0.0001);
        assertArrayEquals(new double[]{0.5, 0, sqrt3_2, 0, 1, 0, -sqrt3_2, 0, 0.5}, m5.data, 0.0001);
        assertArrayEquals(new double[]{-sqrt3_2, -0.5, 0, 0.5, -sqrt3_2, 0, 0, 0, 1}, m6.data, 0.0001);
    }
}
