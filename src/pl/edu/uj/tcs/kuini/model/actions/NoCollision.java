package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class NoCollision implements ICollisionResolver {

	@Override
	public Position computePosition(ILiveActor actor, Position target,
			ILiveState state) {
		return target;
	}

}
