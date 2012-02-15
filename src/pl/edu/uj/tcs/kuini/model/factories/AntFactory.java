package pl.edu.uj.tcs.kuini.model.factories;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.actions.AttackAction;
import pl.edu.uj.tcs.kuini.model.actions.BoidMoveAction;
import pl.edu.uj.tcs.kuini.model.actions.BounceAction;
import pl.edu.uj.tcs.kuini.model.actions.CompoundAction;
import pl.edu.uj.tcs.kuini.model.actions.EatFoodAction;
import pl.edu.uj.tcs.kuini.model.actions.HealYourselfAction;
import pl.edu.uj.tcs.kuini.model.actions.IAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.Actor;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class AntFactory implements IAntFactory {
	private final IAction antAction; 
	public AntFactory(Random random, boolean healAnts){
		this(random, true, true, healAnts);
	}
	public AntFactory(Random random, boolean attack, boolean collision, boolean healAnts){
		List<IAction> actions = new LinkedList<IAction>();
		actions.add(new EatFoodAction(2, 1.5f)); // eatingSpeed, eatingRadius
		
		actions.add(new BoidMoveAction(random, collision));
		actions.add(new BounceAction());
		if(healAnts)
			actions.add(new HealYourselfAction(1)); // healingSpeed
		if(attack)
			actions.add(new AttackAction(5, 1.5f)); // attackForce, attackRadius
		antAction = new CompoundAction(actions);
	}
	@Override
	public ILiveActor getAnt(ILiveState state, int playerId) {
		return new Actor(ActorType.ANT, state.nextActorId(), playerId, antAction,
				new Position(0,0), 0.3f, 0, 100, 100, Path.EMPTY_PATH); 
	}

}
