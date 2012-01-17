package pl.edu.uj.tcs.kuini.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class State {
    private final List<IActor> actors;
    private final Map<Integer, Player> playerById;
    public State(List<IActor> actors, Map<Integer, Player> playerById) {
        super();
        this.actors = actors;
        this.playerById = playerById;
    }
    public List<IActor> getActors() {
        return new ArrayList<IActor>(actors);
    }
    public Map<Integer, Player> getPlayerIds() {
        return new HashMap<Integer, Player>(playerById);
    }
}
