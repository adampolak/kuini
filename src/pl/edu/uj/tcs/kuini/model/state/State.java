package pl.edu.uj.tcs.kuini.model.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.uj.tcs.kuini.model.IActor;

public class State implements Serializable {
	private static final long serialVersionUID = 4420923801793215908L;
	private final List<ActorState> actorStates;
    private final Map<Integer, PlayerState> playerStateById;
    public State(List<IActor> actors, Map<Integer, PlayerState> playerStateById) {
        super();
        this.actorStates = new ArrayList<ActorState>(actors.size());
        for(IActor actor : actors){
        	this.actorStates.add(new ActorState(actor));
        }
        this.playerStateById = new HashMap<Integer, PlayerState>(playerStateById);
    }
    public List<ActorState> getActorStates() {
        return Collections.unmodifiableList(actorStates);
    }
    public Map<Integer, PlayerState> getPlayerStatesById() {
        return Collections.unmodifiableMap(playerStateById);
    }
}
