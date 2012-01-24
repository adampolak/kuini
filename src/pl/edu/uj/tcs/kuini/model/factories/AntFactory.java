package pl.edu.uj.tcs.kuini.model.factories;

import java.util.Arrays;
import java.util.Random;

import pl.edu.uj.tcs.kuini.model.Actor;
import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.actions.AttackAction;
import pl.edu.uj.tcs.kuini.model.actions.BounceAction;
import pl.edu.uj.tcs.kuini.model.actions.CompoundAction;
import pl.edu.uj.tcs.kuini.model.actions.EatFoodAction;
import pl.edu.uj.tcs.kuini.model.actions.HealYourselfAction;
import pl.edu.uj.tcs.kuini.model.actions.IAction;
import pl.edu.uj.tcs.kuini.model.actions.SimpleMoveAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class AntFactory implements IAntFactory {
	private final IAction antAction; 
	public AntFactory(Random random){
		this.antAction = new CompoundAction(
			Arrays.asList(new IAction[]{
				new EatFoodAction(2, 1.5f), // eatingSpeed, eatingRadius
				new SimpleMoveAction(random),
				new BounceAction(),
				new HealYourselfAction(1), // healingSpeed
				new AttackAction()
		}));
	}
	@Override
	public ILiveActor getAnt(ILiveState state, int playerId) {
		return new Actor(ActorType.ANT, state.nextActorId(), playerId, antAction,
				new Position(0,0), 0.3f, 0, 100, 100, Path.EMPTY_PATH); 
	}

}
