package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.geometry.Vector;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class SimpleMoveAction implements IAction {

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		Path path = actor.getPath();
		if(path.isEmpty())return;
		actor.setPosition(
				new Vector(
						new Vector(actor.getPosition(), path.getEnd()), 
						actor.getSpeed()*elapsedTime)
				.getTarget()
				);

	}

}
