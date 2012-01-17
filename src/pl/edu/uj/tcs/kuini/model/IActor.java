package pl.edu.uj.tcs.kuini.model;

public interface IActor {
	
	Position getPosition();
	float getRadius();
	ActorType getActorType();
	long getId();
	float getAngle(); // radians
	Player getPlayer();
	
}
