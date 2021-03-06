package pl.edu.uj.tcs.kuini.model.state;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
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
	private final boolean gameEnded;
	private final int winnerId;
	private final int foodPlayerId;
    public State(IState state){
    	this(state.getActorStates(), state.getPlayerStatesById(), 
    			state.getWidth(), state.getHeight(), state.getWinnerId(), state.isGameEnded(),
    			state.getFoodPlayerId());
    }
    	
    public State(List<IActor> actors, Map<Integer, IPlayer> playerStateById, float width, float height, 
    		int winnerId, boolean gameEnded, int foodPlayerId) {
        this.actorStates = new ArrayList<IActor>(actors.size());
        for(IActor actor : actors){
        	this.actorStates.add(new ActorState(actor));
        }
        this.playerStateById = new LinkedHashMap<Integer, IPlayer>(playerStateById.size());
        for(Map.Entry<Integer, IPlayer> e : playerStateById.entrySet()){
        	this.playerStateById.put(e.getKey(), new PlayerState(e.getValue()));
        }
        this.width = width;
        this.height = height;
        this.gameEnded = gameEnded;
        this.winnerId = winnerId;
        this.foodPlayerId = foodPlayerId;
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
	
	@Override
	public String toString(){
		return "State[#"+hashCode()+"]: actorStates: "+actorStates+" playerStateById: "+playerStateById
		+" width: "+width+" height: "+height;	
	}

	@Override
	public int getWinnerId() {
		return winnerId;
	}

	@Override
	public String getWinnerName() {
	    if (winnerId < 0) return null;
	    return playerStateById.get(winnerId).getName();
	}
	
	@Override
	public boolean isGameEnded() {
		return gameEnded;
	}

	@Override
	public int getFoodPlayerId() {
		return foodPlayerId;
	}
}
