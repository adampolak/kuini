package pl.edu.uj.tcs.kuini.model.live;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.IActorOrderer;
import pl.edu.uj.tcs.kuini.model.IActorWatcher;
import pl.edu.uj.tcs.kuini.model.IPlayer;
import pl.edu.uj.tcs.kuini.model.PlayerColor;
import pl.edu.uj.tcs.kuini.model.actions.IGlobalAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;

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
	private final IActorWatcher actorWatcher;
	private List<ILiveActor> neighbourhoodCache;
	private long cachedNeighbourhoodActorId = -1;
	
	public LiveState(float width, float height,
			IActorOrderer orderer, IGlobalAction globalAction, IActorWatcher actorWatcher) {
		this.actors = new LinkedList<ILiveActor>();
		this.playersById = new LinkedHashMap<Integer, ILivePlayer>();
		this.actorsToAdd = new LinkedList<ILiveActor>();
		this.width = width;
		this.height = height;
		this.orderer = orderer;
		this.globalAction = globalAction;
		this.actorWatcher = actorWatcher;
		playersById.put(-1, new Player(-1, "FOOD", PlayerColor.GOLD, 0, 0));
	}

	@Override
	public List<IActor> getActorStates() {
		return new ArrayList<IActor>(actors);
	}

	@Override
	public Map<Integer, IPlayer> getPlayerStatesById() {
		return new LinkedHashMap<Integer, IPlayer>(playersById);
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
	public List<ILiveActor> getNeighbours(ILiveActor actor) {
		if(cachedNeighbourhoodActorId != actor.getId()){
			neighbourhoodCache = Collections.unmodifiableList(
					actorWatcher.getNeighbours(actor.getPosition(), actor.getNeigbourhoodRadius()));
			cachedNeighbourhoodActorId = actor.getId();
		}
		return neighbourhoodCache;
	}

	@Override
	public void nextTurn(float elapsedTime) {
		clearCache();
		/* long time = System.nanoTime(); */
		for(ILiveActor actor : orderer.orderActors(actors)){
			actor.performAction(elapsedTime, this);
			actorWatcher.updatePosition(actor);
		}
		globalAction.performAction(elapsedTime, this);
		for(ILiveActor actor : actorsToAdd)
			actorWatcher.addActor(actor);
		actors.addAll(actorsToAdd);
		actorsToAdd.clear();
		Set<ILiveActor> actorsToRemove = new HashSet<ILiveActor>();
		for(ILiveActor actor : actors){
			if(actor.isDead()){
				actorsToRemove.add(actor);
				actorWatcher.removeActor(actor);
			}
		}
		actors.removeAll(actorsToRemove);
		/*
		Log.d("TIME", "Next turn computed in "+(((double)(System.nanoTime()-time))/1000000000)+
			"s (actors:"+actors.size()+")");
		*/
	}

	private void clearCache() {
		cachedNeighbourhoodActorId = -1;
		neighbourhoodCache = null;
	}

	@Override
	public void doCommand(Command command) {
		/* int selectedActors = 0; */
		Position start = command.getStart();
		for(ILiveActor actor : getActorsInRange(start, command.getRadius())){
			if(actor.getPlayerId() == command.getPlayerId()){
				actor.setPath(command.getPath());
				/* selectedActors++; */
			}
		}
	}

	private List<ILiveActor> getActorsInRange(Position origin, float radius) {
		return actorWatcher.getNeighbours(origin, radius);
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

	@Override
	public int getWinnerId() {
		Set<Integer> livePlayers = new HashSet<Integer>();
		for(ILiveActor actor : getLiveActors()){
			if(actor.getActorType() == ActorType.ANT)
			    if (playersById.get(actor.getPlayerId()).isHuman())
			        livePlayers.add(actor.getPlayerId());
		}
		if (livePlayers.size() == 1)
			return livePlayers.iterator().next();
		else
		    return -1;
	}

	@Override
	public String getWinnerName() {
	    int i = getWinnerId();
	    if (i < 0) return null;
	    return playersById.get(i).getName();
	}
	
	@Override
	public boolean isGameEnded() {
		return getWinnerId() != -1;
	}

	@Override
	public List<ILiveActor> getNeighbours(ILiveActor actor, float radius) {
		List<ILiveActor> result = new LinkedList<ILiveActor>();
		for(ILiveActor neighbour : getNeighbours(actor)){
			if(neighbour.getPosition().distanceTo(actor.getPosition()) <= radius)
				result.add(neighbour);
		}
		return result;
	}
}
