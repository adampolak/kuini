package pl.edu.uj.tcs.kuini.model.factories;

import java.util.Arrays;
import java.util.Random;

import pl.edu.uj.tcs.kuini.model.Actor;
import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.IModel;
import pl.edu.uj.tcs.kuini.model.LiveState;
import pl.edu.uj.tcs.kuini.model.Model;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.Player;
import pl.edu.uj.tcs.kuini.model.PlayerColor;
import pl.edu.uj.tcs.kuini.model.RandomOrderer;
import pl.edu.uj.tcs.kuini.model.actions.CompoundAction;
import pl.edu.uj.tcs.kuini.model.actions.HealYourselfAction;
import pl.edu.uj.tcs.kuini.model.actions.IAction;
import pl.edu.uj.tcs.kuini.model.actions.SpawnAntAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class ModelFactory implements IModelFactory {
	@Override
	public IModel getModel() {
		ILiveState state = new LiveState(10, 20, new RandomOrderer(new Random(0)));
		
		ILivePlayer player1 = new Player(state.nextPlayerId(), "RED", PlayerColor.RED, 0, 1000);
		ILivePlayer player2 = new Player(state.nextPlayerId(), "BLUE", PlayerColor.BLUE, 0, 1000);
		state.addPlayer(player1);
		state.addPlayer(player2);
		
		IAntFactory antFactory = new AntFactory();
		IAction anthillAction = new CompoundAction(Arrays.asList(new IAction[]{
				new HealYourselfAction(),
				new SpawnAntAction(antFactory)
				}));
		state.addActor(new Actor(ActorType.ANTHILL, state.nextActorId(), player1.getId(), anthillAction,
				new Position(2,2), 0.5f, 0, 1000, Path.EMPTY_PATH));
		state.addActor(new Actor(ActorType.ANTHILL, state.nextActorId(), player2.getId(), anthillAction,
				new Position(8,18), 0.5f, (float)Math.PI, 1000, Path.EMPTY_PATH));
		state.nextTurn(0);
		return new Model(state);
	}

}
