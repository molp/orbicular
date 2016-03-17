package de.qx.orbicular;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TransformationTest {

    @Test
    public void testTransformVectorFromOrbitalPlaneToECI() {
        double Ω = 118;
        double ω = -250;
        double i = 97;

        Matrix x_0 = new Matrix(new double[]{100.0, 500.0, 0.0});
        Matrix result = Transformation.orbitalPlaneToInertial(x_0, Ω, ω, i);

        assertEquals(result.get(0), -284.8506, 1e-4);
        assertEquals(result.get(1), 414.7774, 1e-4);
        assertEquals(result.get(2), 82.5825, 1e-4);
    }
}
