package pl.edu.uj.tcs.kuini.model.factories;

import pl.edu.uj.tcs.kuini.model.Actor;
import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class AntFactory implements IAntFactory {
	@Override
	public ILiveActor getAnt(ILiveState state, int playerId) {
		return new Actor(ActorType.ANT, state.nextActorId(), playerId); 
	}

}
