package pl.edu.uj.tcs.kuini.model;

import java.io.Serializable;

import pl.edu.uj.tcs.kuini.model.geometry.Position;

public class Command implements Serializable{
	private static final long serialVersionUID = -4073525047169440066L;
	private final float radius;
    private final Path path;
    private final int playerId;

    public Command(float radius, Path path, int playerId) {
        super();
        this.radius = radius;
        this.path = path;
        this.playerId = playerId;
    }

    public float getRadius() {
        return radius;
    }

    public Path getPath() {
        return path;
    }

    public int getPlayerId() {
        return playerId;
    }
    
    public Position getStart(){
    	return path.getStart();
    }
    
    @Override
    public String toString(){
    	return "Command(player:"+playerId+", r:"+radius+", path:"+path+")";
    }

}
