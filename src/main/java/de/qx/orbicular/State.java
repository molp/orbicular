package de.qx.orbicular;

public class State {
    public final double[] position;
    public final double[] velocity;

    public State(Matrix position, Matrix velocity) {
        this.position = new double[]{position.get(0), position.get(1), position.get(2)};
        this.velocity = new double[]{velocity.get(0), velocity.get(1), velocity.get(2)};
    }

    public State(double[] position, double[] velocity) {
        this.position = position;
        this.velocity = velocity;
    }
}
