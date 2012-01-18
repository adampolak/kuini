package pl.edu.uj.tcs.kuini.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.edu.uj.tcs.kuini.model.actions.IGlobalAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class LiveState implements ILiveState {
	private List<ILiveActor> actors;
	private Map<Integer, ILivePlayer> playersById;
	private List<ILiveActor> actorsToAdd;
	private final float width;
	private final float height;
	private long lastActorId = -1;
	private int lastPlayerId = 0;
	private IActorOrderer orderer;
	private IGlobalAction globalAction;
	
	public LiveState(float width, float height,
			IActorOrderer orderer, IGlobalAction globalAction) {
		this.actors = new LinkedList<ILiveActor>();
		this.playersById = new HashMap<Integer, ILivePlayer>();
		this.actorsToAdd = new LinkedList<ILiveActor>();
		this.width = width;
		this.height = height;
		this.orderer = orderer;
		this.globalAction = globalAction;
		playersById.put(-1, new Player(-1, "FOOD", PlayerColor.RED, 0, 0));
	}

	@Override
	public List<IActor> getActorStates() {
		return new ArrayList<IActor>(actors);
	}

	@Override
	public Map<Integer, IPlayer> getPlayerStatesById() {
		return new HashMap<Integer, IPlayer>(playersById);
	}

	@Override
	public List<ILiveActor> getLiveActors() {
		return actors;
	}

	@Override
	public Map<Integer, ILivePlayer> getLivePlayersById() {
		return playersById;
	}

	@Override
	public void addActor(ILiveActor actor) {
		actorsToAdd.add(actor);
	}

	@Override
	public void addPlayer(ILivePlayer player) {
		playersById.put(player.getId(), player);
	}


	@Override
	public List<ILiveActor> getNeigbours(Position position, float radius) {
		List<ILiveActor> result = new LinkedList<ILiveActor>();
		for(ILiveActor actor : actors){
			if(position.distanceTo(actor.getPosition()) <= radius + actor.getRadius())
				result.add(actor);
		}
		return result;
	}

	@Override
	public void nextTurn(float elapsedTime) {
		for(ILiveActor actor : orderer.orderActors(actors)){
			actor.performAction(elapsedTime, this);
		}
		globalAction.performAction(elapsedTime, this);
		actors.addAll(actorsToAdd);
		actorsToAdd.clear();
		Set<ILiveActor> actorsToRemove = new HashSet<ILiveActor>();
		for(ILiveActor actor : actors){
			if(actor.isDead())actorsToRemove.add(actor);
		}
		actors.removeAll(actorsToRemove);
	}

	@Override
	public void doCommand(Command command) {
		if(!command.getPath().isEmpty()){
			Position start = command.getPath().getPositions().get(0);
			for(ILiveActor actor : getNeigbours(start, command.getRadius())){
				if(actor.getPlayerId() == command.getPlayerId()){
					actor.setPath(command.getPath());
				}
			}
		}
	}

	@Override
	public long nextActorId() {
		lastActorId++;
		return lastActorId;
	}

	@Override
	public int nextPlayerId() {
		lastPlayerId++;
		return lastPlayerId;
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
	public int getFoodPlayerId() {
		return -1;
	}
}
