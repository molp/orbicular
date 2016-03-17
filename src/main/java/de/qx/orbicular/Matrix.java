package de.qx.orbicular;

class Matrix {

    protected final double[] data;

    Matrix(double[] data) {
        this.data = data;
    }

    int getSize() {
        return data.length;
    }

    double get(int i) {
        return data[i];
    }

    Matrix multiplyWith(Matrix matrix) {
        if (matrix.getSize() == 9) {
            double[] resultData = new double[]{
                    this.data[0] * matrix.data[0] + this.data[1] * matrix.data[3] + this.data[2] * matrix.data[6],
                    this.data[0] * matrix.data[1] + this.data[1] * matrix.data[4] + this.data[2] * matrix.data[7],
                    this.data[0] * matrix.data[2] + this.data[1] * matrix.data[5] + this.data[2] * matrix.data[8],
                    this.data[3] * matrix.data[0] + this.data[4] * matrix.data[3] + this.data[5] * matrix.data[6],
                    this.data[3] * matrix.data[1] + this.data[4] * matrix.data[4] + this.data[5] * matrix.data[7],
                    this.data[3] * matrix.data[2] + this.data[4] * matrix.data[5] + this.data[5] * matrix.data[8],
                    this.data[6] * matrix.data[0] + this.data[7] * matrix.data[3] + this.data[8] * matrix.data[6],
                    this.data[6] * matrix.data[1] + this.data[7] * matrix.data[4] + this.data[8] * matrix.data[7],
                    this.data[6] * matrix.data[2] + this.data[7] * matrix.data[5] + this.data[8] * matrix.data[8]};
            Matrix result = new Matrix(resultData);
            return result;
        } else if (matrix.getSize() == 3) {
            double[] resultData = new double[]{
                    this.data[0] * matrix.data[0] + this.data[1] * matrix.data[1] + this.data[2] * matrix.data[2],
                    this.data[3] * matrix.data[0] + this.data[4] * matrix.data[1] + this.data[5] * matrix.data[2],
                    this.data[6] * matrix.data[0] + this.data[7] * matrix.data[1] + this.data[8] * matrix.data[2]};
            Matrix result = new Matrix(resultData);
            return result;
        } else {
            return null;
        }
    }

    static Matrix getMirrorMatrix(int i) {
        if (i < 1 || i > 3) {
            throw new IllegalArgumentException("mirror matrix argument needs to be 1, 2 or 3");
        }
        double[] data = new double[]{1, 0, 0, 0, 1, 0, 0, 0, 1};
        data[--i * 4] *= -1;
        return new Matrix(data);
    }

    /**
     * rotationMatrix() returns a matrix for a coordinate system rotation
     * of alpha radians around axis e relative to the origin.
     * These rotation matrices are for CRS transformations not geometric
     * point transformations!
     *
     * @param angle rotation angle in radians
     * @param axis  Numbers 1, 2 or 3, representing x, y and z-axis
     * @return a 3x3 Matrix
     */
    static Matrix getRotationMatrix(double angle, int axis) {
        double alpha = angle % (2 * Math.PI);

        double cosAlpha, sinAlpha;

        if (isEqual(alpha, 0)) {
            cosAlpha = 1;
            sinAlpha = 0;
        } else if (alpha == Math.PI / 2 || alpha == -3 / 2 * Math.PI) {
            cosAlpha = 0;
            sinAlpha = 1;
        } else if (isEqual(alpha, Math.PI) || isEqual(alpha, -Math.PI)) {
            cosAlpha = -1;
            sinAlpha = 0;
        } else if (isEqual(alpha, 3 / 2 * Math.PI) || isEqual(alpha, -Math.PI / 2)) {
            cosAlpha = 0;
            sinAlpha = -1;
        } else {
            cosAlpha = Math.cos(alpha);
            sinAlpha = Math.sin(alpha);
        }

        switch (axis) {
            case 1:
                return new Matrix(new double[]{1, 0, 0, 0, cosAlpha, sinAlpha, 0, -sinAlpha, cosAlpha});
            case 2:
                return new Matrix(new double[]{cosAlpha, 0, -sinAlpha, 0, 1, 0, sinAlpha, 0, cosAlpha});
            case 3:
                return new Matrix(new double[]{cosAlpha, sinAlpha, 0, -sinAlpha, cosAlpha, 0, 0, 0, 1});
            default:
                throw new IllegalArgumentException("Rotation axis has to be 1, 2 or 3");
        }
    }

    private static boolean isEqual(double a, double b) {
        return Math.abs(b - a) <= 0.0000001;
    }
}
