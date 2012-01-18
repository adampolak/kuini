package pl.edu.uj.tcs.kuini.model.state;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.uj.tcs.kuini.model.IActor;

public class State {
    private final List<ActorState> actorStates;
    private final Map<Integer, PlayerState> playerStateById;
    public State(List<IActor> actors, Map<Integer, PlayerState> playerStateById) {
        super();
        this.actorStates = new ArrayList<ActorState>(actors.size());
        for(IActor actor : actors){
        	this.actorStates.add(new ActorState(actor));
        }
        this.playerStateById = playerStateById;
    }
    public List<ActorState> getActorStates() {
        return new ArrayList<ActorState>(actorStates);
    }
    public Map<Integer, PlayerState> getPlayerStatesById() {
        return new HashMap<Integer, PlayerState>(playerStateById);
    }
}
