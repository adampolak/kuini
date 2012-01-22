package pl.edu.uj.tcs.kuini.model.actions;

import java.util.Random;

import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.geometry.Vector;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class SimpleMoveAction implements IAction {
	
	Random random;
	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		if(random == null)random = new Random(actor.getId());
		actor.changeAngle((float) ((random.nextFloat()-0.5)*1.0*elapsedTime));
		Path path = actor.getPath();
		Position target = new Vector(actor.getPosition(), actor.getAngle(), 1.0f).getTarget();
		if(!path.isEmpty())target = path.getEnd();
		actor.setPosition(
				new Vector(
						new Vector(actor.getPosition(), target), 
						actor.getSpeed()*elapsedTime)
				.getTarget()
				);

	}

}
