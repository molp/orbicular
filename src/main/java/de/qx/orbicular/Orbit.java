package de.qx.orbicular;

public class Orbit {
    private double semiMajorAxis;   // semimajor axis of orbit [m]
    private double eccentricity;    // eccentricity of orbit
    private double inclination;     // inclination of orbit [rad]
    private double rightAscension;  // right ascension of orbit [rad]
    private double periapsis;       // argument of periapsis of orbit [rad]
    private double meanAnomalyAt0;  // mean anomaly at epoch

    public Orbit(double semiMajorAxis, double eccentricity, double inclination, double rightAscension, double periapsis, double meanAnomalyAt0) {
        this.semiMajorAxis = semiMajorAxis;
        this.eccentricity = eccentricity;
        this.inclination = inclination;
        this.rightAscension = rightAscension;
        this.periapsis = periapsis;
        this.meanAnomalyAt0 = meanAnomalyAt0;
    }

    public double getEccentricity() {
        return eccentricity;
    }

    public double getInclination() {
        return inclination;
    }

    public double getMeanAnomalyAt0() {
        return meanAnomalyAt0;
    }

    public double getSemiMajorAxis() {
        return semiMajorAxis;
    }

    public double getRightAscension() {
        return rightAscension;
    }

    public double getPeriapsis() {
        return periapsis;
    }
}
