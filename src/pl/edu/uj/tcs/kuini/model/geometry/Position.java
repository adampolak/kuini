package pl.edu.uj.tcs.kuini.model.geometry;

import java.io.Serializable;

public class Position implements Serializable {
	private static final long serialVersionUID = 4791245481333071454L;
	private final float x, y;

    /**
     * Class representing position on board.
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
    
    public float distanceTo(Position target){
    	float x = target.getX() - getX();
    	float y = target.getY() - getY();
    	return (float) Math.sqrt(x*x+y*y);
    }
    @Override
    public String toString(){
    	return "("+x+","+y+")";
    }
    
    @Override
    public int hashCode(){
    	return (int)(17*x + 19*y + 7);
    }
}
