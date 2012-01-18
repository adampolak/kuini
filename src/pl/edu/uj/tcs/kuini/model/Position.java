package pl.edu.uj.tcs.kuini.model;

public class Position {
    private final float x, y;

    /**
     * Class representing position on board.
     * (x,y) is normalized to 0<=x<=1, 0<=y<=1. 
     * @param x
     * @param y
     */
    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
