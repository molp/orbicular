# orbicular
A java port of [orb](https://github.com/benelsen/orb): a library for simple orbital mechanics

So far the position parts of the orb library to convert from a state to an orbit and back have been ported to java. Feel free to port other parts!

## Usage

Calculate the state (e.g. position and velocity) from the orbit a celesitial body:
  
```java
double a = 149598261150.0;            // semimajor axis of orbit [m]
double e = 0.01671123;                // eccentricity of orbit
double i = 7.155 * Math.PI / 180;     // inclination of orbit [rad]
double Ω = 348.73936 * Math.PI / 180; // right ascension of orbit [rad]
double ω = 114.20783 * Math.PI / 180; // argument of periapsis of orbit [rad]
double T0 = 0;                        // epoch of given elements
double t = 1 * 365.256363004 * 86400; // time [s]

double massEarth = 1.988546944e30;
double massSun = 5.9725801308e24 * 1.0123000371;

Orbit orbit = new Orbit(a, e, i, Ω, ω, 0.0);
State result = Position.orbitToState(orbit, T0, 0, massEarth, massSun);
```

Calculate the orbit of a celestial body from its state (e.g. position and velocity) at the time t
  
```java
final double[] position = {3194418.35653, -5715730.19269, 1116844.10036};
final double[] velocity = {-199.07213470, -1607.83626052, -7579.15862735};
final Orbit orbit = Position.stateToOrbit(new State(position, velocity), t, massEarth, 0.0);
```
