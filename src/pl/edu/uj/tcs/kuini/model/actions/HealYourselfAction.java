package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class HealYourselfAction implements IAction {

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		actor.changeHP(elapsedTime*2);
	}

}
