package pl.edu.uj.tcs.kuini.model.geometry;

import java.io.Serializable;


public class Vector implements Serializable{
	private static final long serialVersionUID = -4317051249315419643L;
	private final Position source;
	private final Position target;
	
	public Vector(Vector v, float magnitude){
		this(v.getSource(), v.getAngle(), magnitude);
	}
	
	public Vector(Position source, Position target) {
		this.source = source;
		this.target = target;
	}
	
	public Vector(Position source, float angle, float magnitude){
		this(source, new Position((float)(source.getX()+Math.cos(angle)*magnitude),
				(float)(source.getY()+Math.sin(angle)*magnitude)));
	}

	public Position getSource() {
		return source;
	}

	public Position getTarget() {
		return target;
	}
	
	/**
	 * Returns angle from OX to this vector, in radians;
	 * @return
	 */
	public float getAngle(){
		float acos = (float) Math.acos(getX()/magnitude());
		if(getY() >= 0) return acos;
		else return (float)(2*Math.PI-acos);
	}
	
	public float magnitude(){
		return source.distanceTo(target);
	}
	
	public float getX(){
		return target.getX() - source.getX();
	}
	
	public float getY(){
		return target.getY() - source.getY();
	}
		
}
