package pl.edu.uj.tcs.kuini.model;

import java.util.List;

import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;

public interface IActorWatcher {

	List<ILiveActor> getNeighbours(Position position, float radius);
	void updatePosition(ILiveActor actor);
	void addActor(ILiveActor actor);
	void removeActor(ILiveActor actor);

}
