package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public interface IAction {
	void performAction(ILiveActor actor, float elapsedTime, ILiveState state);
}
