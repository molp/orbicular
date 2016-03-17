# orbicular
A java port of [orb](https://github.com/benelsen/orb): a library for simple orbital mechanics

So far only some part of the orb library have been ported to java. Feel free to port other parts!

## Usage

Calculate the position and velocity of a celesitial body:
  
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
Position.KeplerResult result = Position.keplerian(a, e, i, Ω, ω, t, T0, 0, massEarth, massSun);
```
