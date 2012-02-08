package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class SimpleCollision implements ICollisionResolver {

	@Override
	public Position computePosition(ILiveActor actor, Position target,
			ILiveState state) {
		for(ILiveActor other : state.getNeighbours(actor.getPosition(), actor.getRadius())){
			if(other.getId() != actor.getId()
					&& other.getActorType() != ActorType.FOOD) return target;
		}
		for(ILiveActor other : state.getNeighbours(target, actor.getRadius())){
			if(other.getId() != actor.getId()
					&& other.getActorType() != ActorType.FOOD) return actor.getPosition();
		}
		return target;
	}

}
