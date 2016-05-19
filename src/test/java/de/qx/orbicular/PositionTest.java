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
    public void testOrbitToStateOfSatelliteAfter900Seconds() {
        double a = 6649e3;
        double e = 0.002;
        double i = 97.0 * Math.PI / 180.0;
        double Ω = 118.0 * Math.PI / 180.0;
        double ω = -250.0 * Math.PI / 180.0;
        double T0 = 0.0;
        double t = 15 * 60;

        double massEarth = 5.972580e24;

        Orbit orbit = new Orbit(a, e, i, Ω, ω, 0.0);
        State state = Position.orbitToState(orbit, t, T0, massEarth, 0);

        assertEquals(3194418.35653, state.position[0], 1e1);
        assertEquals(-5715730.19269, state.position[1], 1e-1);
        assertEquals(1116844.10036, state.position[2], 1e-1);

        assertEquals(-199.07213470, state.velocity[0], 1e-1);
        assertEquals(-1607.83626052, state.velocity[1], 1e-1);
        assertEquals(-7579.15862735, state.velocity[2], 1e-1);
    }

    @Test
    public void testOrbitToStateOfEarthAfter1Year() {
        double a = 149598261150.0;
        double e = 0.01671123;
        double i = 7.155 * Math.PI / 180.0;
        double Ω = 348.73936 * Math.PI / 180.0;
        double ω = 114.20783 * Math.PI / 180.0;
        double T0 = 0.0;
        double t = 1.0 * 365.256363004 * 86400.0;

        double massEarth = 1.988546944e30;
        double massSun = 5.9725801308e24 * 1.0123000371;

        Orbit orbit = new Orbit(a, e, i, Ω, ω, 0.0);
        State state = Position.orbitToState(orbit, t, T0, massEarth, massSun);

        assertEquals(-33158216645.818844, state.position[0], 1e-3);
        assertEquals(142334785036.72403, state.position[1], 1e-3);
        assertEquals(16710733601.488567, state.position[2], 1e-3);

        assertEquals(-29497.972100017116, state.velocity[0], 1e-6);
        assertEquals(-6690.245698136594, state.velocity[1], 1e-6);
        assertEquals(-1546.7484094314725, state.velocity[2], 1e-6);
    }

    @Test
    public void testStateToOrbitOfSatelliteAfter900Seconds() {
        double a = 6649e3;
        double e = 0.002;
        double i = 97.0 * Math.PI / 180.0;
        double Ω = 118.0 * Math.PI / 180.0;
        double ω = (-250.0 + 360) * Math.PI / 180.0;
        double T0 = 0.0;
        double t = 15.0 * 60.0;

        double massEarth = 5.972580e24;

        final double[] position = {3194418.35653, -5715730.19269, 1116844.10036};
        final double[] velocity = {-199.07213470, -1607.83626052, -7579.15862735};
        final Orbit orbit = Position.stateToOrbit(new State(position, velocity), t, massEarth, 0.0);

        assertEquals(a, orbit.getSemiMajorAxis(), 1);
        assertEquals(e, orbit.getEccentricity(), 1e-5);
        assertEquals(i, orbit.getInclination(), 1e-5);
        assertEquals(Ω, orbit.getRightAscension(), 1e-5);
        assertEquals(ω, orbit.getPeriapsis(), 1e-5);
        assertEquals(T0, orbit.getMeanAnomalyAt0(), 1e-2);
    }

    @Test
    public void testStateToOrbitOfEarthAfter1Year() {
        double a = 149598261150.0;
        double e = 0.01671123;
        double i = 7.155 * Math.PI / 180.0;
        double Ω = 348.73936 * Math.PI / 180.0;
        double ω = 114.20783 * Math.PI / 180.0;
        double T0 = 0;
        double t = 365.256363004 * 86400;

        double massEarth = 1.988546944e30;
        double massSun = 5.9725801308e24 * 1.0123000371;

        final double[] position = {-33158216645.818844, 142334785036.72403, 16710733601.488567};
        final double[] velocity = {-29497.972100017116, -6690.245698136594, -1546.7484094314725};
        final Orbit orbit = Position.stateToOrbit(new State(position, velocity), t, massEarth, massSun);

        assertEquals(a, orbit.getSemiMajorAxis(), 1e-3);
        assertEquals(e, orbit.getEccentricity(), 1e-5);
        assertEquals(i, orbit.getInclination(), 1e-5);
        assertEquals(Ω - 2 * Math.PI, orbit.getRightAscension(), 1e-5);
        assertEquals(ω, orbit.getPeriapsis(), 1e-5);
        assertEquals(T0, orbit.getMeanAnomalyAt0() - t, 1e+3);
    }
}
