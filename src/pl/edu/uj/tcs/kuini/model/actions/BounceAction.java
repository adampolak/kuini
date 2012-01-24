package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.geometry.Vector;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class BounceAction implements IAction {

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		float x = actor.getPosition().getX();
		float y = actor.getPosition().getY();
		float w = state.getWidth();
		float h = state.getHeight();
		float r = actor.getRadius();
		if(x > w-r) x = w-r;
		if(x < r) x = r;
		if(y > h-r) y = h-r;
		if(y < r) y = r;
		if(x == actor.getPosition().getX() && y == actor.getPosition().getY())return;
		Position newPosition = new Position(x,y);
		actor.setAngle(new Vector(actor.getPosition(), newPosition).getAngle());
		actor.setPosition(newPosition);
	}

}
