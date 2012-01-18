package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class NullAction implements IAction {

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
	}

}
