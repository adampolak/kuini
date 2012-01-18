package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public interface IGlobalAction {
	void performAction(float elapsedTime, ILiveState state);
}
