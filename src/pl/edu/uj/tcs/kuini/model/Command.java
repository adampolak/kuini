package pl.edu.uj.tcs.kuini.model;

import java.io.Serializable;

import pl.edu.uj.tcs.kuini.model.state.PlayerState;

public class Command implements Serializable{
	private static final long serialVersionUID = -4073525047169440066L;
	final float radius;
    final Path path;
    final PlayerState player;

    public Command(float radius, Path path, PlayerState player) {
        super();
        this.radius = radius;
        this.path = path;
        this.player = player;
    }

    public float getRadius() {
        return radius;
    }

    public Path getPath() {
        return path;
    }

    public PlayerState getPlayer() {
        return player;
    }


}
