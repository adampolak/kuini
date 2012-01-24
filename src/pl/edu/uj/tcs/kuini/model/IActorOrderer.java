package pl.edu.uj.tcs.kuini.model;

import java.util.List;

import pl.edu.uj.tcs.kuini.model.live.ILiveActor;

public interface IActorOrderer {
	List<ILiveActor> orderActors(List<ILiveActor> actors);
}
