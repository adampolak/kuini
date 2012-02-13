package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class SimpleCollision implements ICollisionResolver {

	@Override
	public Position computePosition(ILiveActor actor, Position target,
			ILiveState state) {
		/*for(ILiveActor other : state.getNeighbours(actor, actor.getRadius())){
			if(other.getId() != actor.getId()
					&& other.getActorType() != ActorType.FOOD) return target;
		}*/
		for(ILiveActor other : state.getNeighbours(actor)){
			if(other.getId() != actor.getId()
					&& other.getActorType() != ActorType.FOOD
					&& other.getPosition().distanceTo(target) <= actor.getRadius())
				return actor.getPosition();
		}
		return target;
	}

}
