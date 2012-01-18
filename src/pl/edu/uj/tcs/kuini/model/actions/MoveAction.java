package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.geometry.Vector;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class MoveAction implements IAction {

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		actor.setPosition(
				new Vector(actor.getPosition(), actor.getAngle(), actor.getSpeed()*elapsedTime)
				.getTarget()
				);

	}

}
