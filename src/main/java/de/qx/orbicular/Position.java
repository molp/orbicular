package de.qx.orbicular;

public class Position {
    private final static double epsilon = 1e-18;
    private final static double maxIterations = 100;

    /**
     * Calculates a planet's position/velocity at the given time with the given orbital parameters
     *
     * @param semiMajorAxis  semimajor axis of orbit [m]
     * @param eccentricity   eccentricity of orbit
     * @param inclination    inclination of orbit [rad]
     * @param Ω              right ascension of orbit [rad]
     * @param ω              argument of periapsis of orbit [rad]
     * @param t              time [s]
     * @param t0             epoch of given elements
     * @param meanAnomalyAt0 mean anomaly at epoch
     * @param mass1          mass of body 1 (e.g. sun) [kg]
     * @param mass2          mass of body 2 [kg]
     * @return a KeplerResult containing the position and velocity of the orbiting object
     */
    public static KeplerResult keplerian(double semiMajorAxis, double eccentricity, double inclination, double Ω, double ω, double t, double t0, double meanAnomalyAt0, double mass1, double mass2) {
        double GM = Constants.G * (mass1 + mass2);

        double p = semiMajorAxis * (1 - Math.pow(eccentricity, 2));

        // Mean motion
        double n = Math.sqrt(GM / Math.pow(semiMajorAxis, 3));

        // Mean anomaly at t
        double M = meanAnomalyAt0 + n * (t - t0);

        // Eccentric anomaly
        double E = keplerEquation(eccentricity, M);

        // True anomaly
        double ν = 2 * Math.atan(Math.sqrt((1 + eccentricity) / (1 - eccentricity)) * Math.tan(E / 2));
        // double ν = Math.atan2( Math.sqrt(1-e*e) * Math.sin(E), Math.cos(E) - e );

        // radius
        double r = p / (1 + eccentricity * Math.cos(ν));

        // position in orbital plane
        Matrix xOrbitalPlane = new Matrix(new double[]{
                r * Math.cos(ν),
                r * Math.sin(ν),
                0
        });

        Matrix xDotOrbitalPlane = new Matrix(new double[]{
                -Math.sqrt(GM / p) * Math.sin(ν),
                Math.sqrt(GM / p) * (eccentricity + Math.cos(ν)),
                0
        });

        return new KeplerResult(
                Transformation.orbitalPlaneToInertial(xOrbitalPlane, Ω, ω, inclination),
                Transformation.orbitalPlaneToInertial(xDotOrbitalPlane, Ω, ω, inclination));
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

    public static class KeplerResult {
        public final double [] position;
        public final double [] velocity;

        public KeplerResult(Matrix position, Matrix velocity) {
            this.position = new double[] {position.get(0), position.get(1), position.get(2)};
            this.velocity = new double[] {velocity.get(0), velocity.get(1), velocity.get(2)};
        }
    }
}
