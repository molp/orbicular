package de.qx.orbicular;

public class Position {
    private final static double epsilon = 1e-18;
    private final static double maxIterations = 100;

    /**
     * Calculates a planet's state (e.g. position/velocity vectors) at the given time with the given orbital parameters
     *
     * @param orbit orbital parameters
     * @param time  time [s]
     * @param t0    epoch of given elements
     * @param mass1 mass of body 1 (e.g. sun) [kg]
     * @param mass2 mass of body 2 [kg]
     * @return a {@link State} containing the position and velocity of the orbiting object
     */
    public static State orbitToState(Orbit orbit, double time, double t0, double mass1, double mass2) {
        double GM = Constants.G * (mass1 + mass2);

        double p = orbit.getSemiMajorAxis() * (1 - Math.pow(orbit.getEccentricity(), 2));

        // Mean motion
        double n = Math.sqrt(GM / Math.pow(orbit.getSemiMajorAxis(), 3));

        // Mean anomaly at t
        double M = orbit.getMeanAnomalyAt0() + n * (time - t0);

        // Eccentric anomaly
        double E = keplerEquation(orbit.getEccentricity(), M);

        // True anomaly
        double ν = 2 * Math.atan(Math.sqrt((1 + orbit.getEccentricity()) / (1 - orbit.getEccentricity())) * Math.tan(E / 2));
        // double ν = Math.atan2( Math.sqrt(1-e*e) * Math.sin(E), Math.cos(E) - e );

        // radius
        double r = p / (1 + orbit.getEccentricity() * Math.cos(ν));

        // position in orbital plane
        Matrix xOrbitalPlane = new Matrix(new double[]{
                r * Math.cos(ν),
                r * Math.sin(ν),
                0
        });

        Matrix xDotOrbitalPlane = new Matrix(new double[]{
                -Math.sqrt(GM / p) * Math.sin(ν),
                Math.sqrt(GM / p) * (orbit.getEccentricity() + Math.cos(ν)),
                0
        });

        return new State(
                Transformation.orbitalPlaneToInertial(xOrbitalPlane, orbit.getRightAscension(), orbit.getPeriapsis(), orbit.getInclination()),
                Transformation.orbitalPlaneToInertial(xDotOrbitalPlane, orbit.getRightAscension(), orbit.getPeriapsis(), orbit.getInclination()));
    }

    /**
     * Calculates the planet's {@link Orbit} from a {@link State} (e.g. position/velocity vectors) at a given point in time
     *
     * @param state the planet's state
     * @param time  time [s]
     * @param mass1 mass of body 1 (e.g. sun) [kg]
     * @param mass2 mass of body 2 [kg]
     * @return the {@link Orbit} for the given {@link State}
     */
    public static Orbit stateToOrbit(State state, double time, double mass1, double mass2) {
        double GM = Constants.G * (mass1 + mass2);

        double[] cross = new double[]{
                state.position[1] * state.velocity[2] - state.position[2] * state.velocity[1],
                state.position[2] * state.velocity[0] - state.position[0] * state.velocity[2],
                state.position[0] * state.velocity[1] - state.position[1] * state.velocity[0]
        };

        double rightAscension = Math.atan2(cross[0], -cross[1]);

        double inclination = Math.atan2(Math.hypot(cross[0], cross[1]), cross[2]);

        double p = dotProduct(cross, cross) / GM;

        double rLength = Math.sqrt(state.position[0] * state.position[0] + state.position[1] * state.position[1] + state.position[2] * state.position[2]);

        double eccentricity = Math.sqrt(
                p / GM * Math.pow(dotProduct(state.position, state.velocity) / rLength, 2)
                        + Math.pow(p / rLength - 1, 2));

        double ν = Math.atan2(Math.sqrt(p / GM) * dotProduct(state.position, state.velocity), p - rLength);

        Matrix rb =
                Matrix.getRotationMatrix(inclination, 1).multiplyWith(
                        Matrix.getRotationMatrix(rightAscension, 3).multiplyWith(new Matrix(state.position)));

        double periapsis = Math.atan2(rb.get(1), rb.get(0)) - ν;

        if (eccentricity < 1) {
            double semiMajorAxis = p / (1 - Math.pow(eccentricity, 2));
            double E = 2 * Math.atan(Math.sqrt((1 - eccentricity) / (1 + eccentricity)) * Math.tan(ν / 2));
            double t0 = time - Math.sqrt(Math.pow(semiMajorAxis, 3) / GM) * (E - eccentricity * Math.sin(E));
            return new Orbit(semiMajorAxis, eccentricity, inclination, rightAscension, periapsis, t0);
        } else if (eccentricity > 1) {
            double semiMajorAxis = p / (Math.pow(eccentricity, 2) - 1);
            double H = 2 * Math.tanh(Math.sqrt((1 - eccentricity) / (1 + eccentricity)) * Math.tan(ν / 2));
            double t0 = time - Math.sqrt(Math.pow(semiMajorAxis, 3) / GM) * (H - eccentricity * Math.sin(H));
            return new Orbit(semiMajorAxis, eccentricity, inclination, rightAscension, periapsis, t0);
        } else {
            double t0 = time - 0.5 * Math.sqrt(Math.pow(p, 3) / GM) * (Math.tan(ν / 2) + 1 / 3 * Math.pow(Math.tan(ν / 2), 3));
            return new Orbit(p, eccentricity, inclination, rightAscension, periapsis, t0);
        }
    }

    protected static double keplerEquation(double eccentricity, double meanAnomaly) {
        double eccentricAnomaly;

        if (eccentricity < 0.8) {
            eccentricAnomaly = meanAnomaly;
        } else {
            eccentricAnomaly = Math.PI;
        }

        double dE = 1.0;
        int i = 0;
        while (Math.abs(dE) > epsilon && i < maxIterations) {
            dE = (meanAnomaly + eccentricity * Math.sin(eccentricAnomaly) - eccentricAnomaly) /
                    (1 - eccentricity * Math.cos(eccentricAnomaly));
            eccentricAnomaly = eccentricAnomaly + dE;
            i++;
        }

        return eccentricAnomaly;
    }

    private static double dotProduct(double[] a, double[] b) {
        return a[0] * b[0] + a[1] * b[1] + a[2] * b[2];
    }
}
