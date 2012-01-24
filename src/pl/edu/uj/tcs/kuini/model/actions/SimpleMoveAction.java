package pl.edu.uj.tcs.kuini.model.actions;

import java.util.Random;

import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.geometry.Vector;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class SimpleMoveAction implements IAction {
	
	private final Random random;
	public SimpleMoveAction(Random random){
		this.random = random;
	}
	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		Path path = actor.getPath();
		Position target;
		if(!path.isEmpty())target = path.getEnd();
		else target = new Vector(actor.getPosition(), 
				actor.getAngle()+((float) ((random.nextFloat()-0.5)*0.5*elapsedTime)), 
				100.0f).getTarget();
		
		Vector direction = new Vector(actor.getPosition(), target);
		actor.setAngle(direction.getAngle());
		float length = Math.min(direction.magnitude(), actor.getSpeed()*elapsedTime);
		actor.setPosition(new Vector(direction, length).getTarget());
		
		if(actor.getPosition().distanceTo(target) < actor.getRadius())
			actor.setPath(Path.EMPTY_PATH);
	}

}
