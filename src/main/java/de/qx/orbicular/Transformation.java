package de.qx.orbicular;

public class Transformation {

    public static Matrix orbitalPlaneToInertial(Matrix x, double Ω, double ω, double i) {
        Matrix r1 = Matrix.getRotationMatrix(-ω, 3).multiplyWith(x);

        Matrix r2 = Matrix.getRotationMatrix(-i, 1).multiplyWith(r1);

        Matrix r3 = Matrix.getRotationMatrix(-Ω, 3).multiplyWith(r2);

        return r3;
    }
}
