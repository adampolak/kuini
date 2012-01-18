package pl.edu.uj.tcs.kuini.model.factories;

import java.util.Arrays;

import pl.edu.uj.tcs.kuini.model.Actor;
import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.actions.AttackAction;
import pl.edu.uj.tcs.kuini.model.actions.CompoundAction;
import pl.edu.uj.tcs.kuini.model.actions.EatFoodAction;
import pl.edu.uj.tcs.kuini.model.actions.HealYourselfAction;
import pl.edu.uj.tcs.kuini.model.actions.IAction;
import pl.edu.uj.tcs.kuini.model.actions.SimpleMoveAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class AntFactory implements IAntFactory {
	private final IAction antAction = new CompoundAction(
			Arrays.asList(new IAction[]{
				new EatFoodAction(),
				new SimpleMoveAction(),
				new HealYourselfAction(),
				new AttackAction()
		}));
	@Override
	public ILiveActor getAnt(ILiveState state, int playerId) {
		return new Actor(ActorType.ANT, state.nextActorId(), playerId, antAction,
				new Position(0,0), 0.01f, 0, 100, Path.EMPTY_PATH); 
	}

}
