package pl.edu.uj.tcs.kuini.model;

import java.util.ArrayList;
import java.util.List;

import pl.edu.uj.tcs.kuini.model.state.PlayerState;

public class Command {

    final float radius;
    final List<Position> path;
    final PlayerState player;

    public Command(float radius, List<Position> path, PlayerState player) {
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

    public PlayerState getPlayer() {
        return player;
    }


}
