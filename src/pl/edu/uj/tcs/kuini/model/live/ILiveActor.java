package pl.edu.uj.tcs.kuini.model.live;

import pl.edu.uj.tcs.kuini.model.IActor;

/**
 * Interface describing actor whose state can be changed.
 * @author michal
 *
 */
public interface ILiveActor extends IActor {
	void performAction(float elapsedTime, ILiveState state);
}
