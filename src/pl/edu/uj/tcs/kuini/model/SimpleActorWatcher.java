package pl.edu.uj.tcs.kuini.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;

public class SimpleActorWatcher implements IActorWatcher {

	Set<ILiveActor> actors = new LinkedHashSet<ILiveActor>();
	@Override
	public List<ILiveActor> getNeighbours(Position position, float radius) {
	List<ILiveActor> result = new ArrayList<ILiveActor>();
		for(ILiveActor actor : actors){
			if(position.distanceTo(actor.getPosition()) <= radius + actor.getRadius())
				result.add(actor);
		}
		return result;
	}

	@Override
	public void updatePosition(ILiveActor actor) {
	}

	@Override
	public void addActor(ILiveActor actor) {
		actors.add(actor);
	}

	@Override
	public void removeActor(ILiveActor actor) {
		actors.remove(actor);
	}

}
