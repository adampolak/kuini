package pl.edu.uj.tcs.kuini.model.factories;

import pl.edu.uj.tcs.kuini.model.actions.IAction;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class RotateAction implements IAction {
	private final float rotateSpeed;
	public RotateAction(float rotateSpeed){
		this.rotateSpeed = rotateSpeed;
	}

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		actor.setAngle(actor.getAngle()+rotateSpeed*elapsedTime);
	}

}
