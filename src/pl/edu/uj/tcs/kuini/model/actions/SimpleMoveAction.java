package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.geometry.Vector;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class SimpleMoveAction implements IAction {

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		Path path = actor.getPath();
		if(path.isEmpty())return;
		Position target = path.getPositions().get(path.getPositions().size()-1);
		actor.setPosition(
				new Vector(
						new Vector(actor.getPosition(), target), 
						actor.getSpeed()*elapsedTime)
				.getTarget()
				);

	}

}
