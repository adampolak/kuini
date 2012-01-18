package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class DecayAction implements IAction {
	public final float decayFactor;

	public DecayAction(float decayFactor) {
		this.decayFactor = decayFactor;
	}

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		actor.changeHP(-elapsedTime*decayFactor);
	}

}
