package pl.edu.uj.tcs.kuini.model;

import java.util.ArrayList;
import java.util.List;

public class Command {

	final float radius;
	final List<Position> path;
	final Player player;
	
	public Command(float radius, List<Position> path, Player player) {
		super();
		this.radius = radius;
		this.path = path;
		this.player = player;
	}

	public float getRadius() {
		return radius;
	}

	public List<Position> getPath() {
		return new ArrayList<Position>(path);
	}

	public Player getPlayer() {
		return player;
	}
	
	
}
