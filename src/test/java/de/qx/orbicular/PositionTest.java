package de.qx.orbicular;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {

    @Test
    public void testEccentricAnomalyForLowEccentricity() {
        double eccentricAnomaly = Position.keplerEquation(0.002, 1.048037758440223);
        assertEquals(eccentricAnomaly, 1.049772378330563, 1e-12);
    }

    @Test
    public void testEccentricAnomalyForHighEccentricity() {
        double eccentricAnomaly = Position.keplerEquation(0.9, 1.048037758440223);
        assertEquals(eccentricAnomaly, 1.899773668961998, 1e-12);
    }

    @Test
    public void testKeplerianPositionOfSatelliteAfter900Seconds() {
        double a = 6649e3;
        double e = 0.002;
        double i = 97 * Math.PI / 180;
        double Ω = 118 * Math.PI / 180;
        double ω = -250 * Math.PI / 180;
        double T0 = 0;
        double t = 15 * 60;

        Position.KeplerResult keplerResult = Position.keplerian(a, e, i, Ω, ω, t, T0, 0, Constants.MASS_EARTH, 0);

        assertEquals(3194418.35653, keplerResult.position[0], 1e-5);
        assertEquals(-5715730.19269, keplerResult.position[1], 1e-5);
        assertEquals(1116844.10036, keplerResult.position[2], 1e-5);

        assertEquals(-199.07213470, keplerResult.velocity[0], 1e-5);
        assertEquals(-1607.83626052, keplerResult.velocity[1], 1e-5);
        assertEquals(-7579.15862735, keplerResult.velocity[2], 1e-5);
    }

    @Test
    public void testKeplerianPositionOfEarthAfter1Year() {
        double a = 149598261150.0;
        double e = 0.01671123;
        double i = 7.155 * Math.PI / 180;
        double Ω = 348.73936 * Math.PI / 180;
        double ω = 114.20783 * Math.PI / 180;
        double T0 = 0;
        double t = 1 * 365.256363004 * 86400;

        double massEarth = 1.988546944e30;
        double massSun = 5.9725801308e24 * 1.0123000371;

        Position.KeplerResult keplerResult = Position.keplerian(a, e, i, Ω, ω, t, T0, 0, massEarth, massSun);

        assertEquals(-33158216645.818844, keplerResult.position[0], 1e-3);
        assertEquals(142334785036.72403, keplerResult.position[1], 1e-3);
        assertEquals(16710733601.488567, keplerResult.position[2], 1e-3);

        assertEquals(-29497.972100017116, keplerResult.velocity[0], 1e-6);
        assertEquals(-6690.245698136594, keplerResult.velocity[1], 1e-6);
        assertEquals(-1546.7484094314725, keplerResult.velocity[2], 1e-6);
    }
}
