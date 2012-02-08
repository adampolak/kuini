package pl.edu.uj.tcs.kuini.model.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.IPlayer;
import pl.edu.uj.tcs.kuini.model.IState;

public class State implements Serializable, IFrozenState{
	private static final long serialVersionUID = 4420923801793215908L;
	private final List<IActor> actorStates;
    private final Map<Integer, IPlayer> playerStateById;
    private final float width;
    private final float height;
    public State(IState state){
    	this(state.getActorStates(), state.getPlayerStatesById(), state.getWidth(), state.getHeight());
    }
    	
    public State(List<IActor> actors, Map<Integer, IPlayer> playerStateById, float width, float height) {
        this.actorStates = new ArrayList<IActor>(actors.size());
        for(IActor actor : actors){
        	this.actorStates.add(new ActorState(actor));
        }
        this.playerStateById = new HashMap<Integer, IPlayer>(playerStateById.size());
        for(Map.Entry<Integer, IPlayer> e : playerStateById.entrySet()){
        	this.playerStateById.put(e.getKey(), new PlayerState(e.getValue()));
        }
        this.width = width;
        this.height = height;
    }
    public List<IActor> getActorStates() {
        return Collections.unmodifiableList(actorStates);
    }
    public Map<Integer, IPlayer> getPlayerStatesById() {
        return Collections.unmodifiableMap(playerStateById);
    }

	@Override
	public float getWidth() {
		return width;
	}

	@Override
	public float getHeight() {
		return height;
	}
	@Override
	public int hashCode(){
		return 5+ (int)width*7 +(int)(height)*11 + 13*actorStates.hashCode() + 17*playerStateById.hashCode();
	}
}
