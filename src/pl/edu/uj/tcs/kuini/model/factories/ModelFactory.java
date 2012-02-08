package pl.edu.uj.tcs.kuini.model.factories;

import java.util.Arrays;
import java.util.Random;

import pl.edu.uj.tcs.kuini.model.Actor;
import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.GridActorWatcher;
import pl.edu.uj.tcs.kuini.model.IModel;
import pl.edu.uj.tcs.kuini.model.LiveState;
import pl.edu.uj.tcs.kuini.model.Model;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.Player;
import pl.edu.uj.tcs.kuini.model.PlayerColor;
import pl.edu.uj.tcs.kuini.model.RandomOrderer;
import pl.edu.uj.tcs.kuini.model.SimpleActorWatcher;
import pl.edu.uj.tcs.kuini.model.actions.CompoundAction;
import pl.edu.uj.tcs.kuini.model.actions.HealYourselfAction;
import pl.edu.uj.tcs.kuini.model.actions.IAction;
import pl.edu.uj.tcs.kuini.model.actions.NoCollision;
import pl.edu.uj.tcs.kuini.model.actions.SimpleCollision;
import pl.edu.uj.tcs.kuini.model.actions.SpawnAntAction;
import pl.edu.uj.tcs.kuini.model.actions.SpawnFoodAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class ModelFactory implements IModelFactory {
	public IModel getModel() {
		Random random = new Random(0);
		ILiveState state = new LiveState(12, 18, 
				new RandomOrderer(random), 
				new SpawnFoodAction(new FoodFactory(random)),
				new SimpleActorWatcher());
				//new GridActorWatcher(16, 24, 12.0f, 18.0f));
		
		ILivePlayer player1 = new Player(state.nextPlayerId(), "RED", PlayerColor.RED, 0, 1000);
		ILivePlayer player2 = new Player(state.nextPlayerId(), "BLUE", PlayerColor.BLUE, 0, 1000);
		state.addPlayer(player1);
		state.addPlayer(player2);
		
		IAntFactory antFactory = new AntFactory(random);
		IAction anthillAction = new CompoundAction(Arrays.asList(new IAction[]{
				new HealYourselfAction(10),
				new SpawnAntAction(antFactory, new NoCollision(), 1.5f)
				}));
		state.addActor(new Actor(ActorType.ANTHILL, state.nextActorId(), player1.getId(), anthillAction,
				new Position(2,2), 0.5f, 0, 1000, 1000, Path.EMPTY_PATH));
		state.addActor(new Actor(ActorType.ANTHILL, state.nextActorId(), player2.getId(), anthillAction,
				new Position(10,16), 0.5f, (float)Math.PI, 1000, 1000, Path.EMPTY_PATH));
        
		state.nextTurn(0);
		return new Model(state);
	}
	
	public IModel getTestingModel(IPlayerStub[] players, float screenRatio, String seed, int antsPerPlayer){
		Random random = new Random(seed.hashCode());
		float width, height;
		if(screenRatio < 1){ // horizontal
			width = 18;
			height = width*screenRatio;
		}else{
			height = 18;
			width = height/screenRatio;
		}
		ILiveState state = new LiveState(width, height, 
				new RandomOrderer(random), 
				new SpawnFoodAction(new FoodFactory(random)),
				new GridActorWatcher((int)width/2,(int)height/2, height, width));
		
		IAntFactory antFactory = new AntFactory(random, false, false);
		
		for(IPlayerStub playerStub : players){
			ILivePlayer player = new Player(state.nextPlayerId(), playerStub.getName(), playerStub.getColor(), 0, 1000);
			state.addPlayer(player);
			for(int i=0;i<antsPerPlayer;i++){
				ILiveActor ant = antFactory.getAnt(state, player.getId());
				ant.setPosition(new Position(random.nextFloat()*width, random.nextFloat()*height));
				state.addActor(ant);
			}
		}
		state.nextTurn(0);
		return new Model(state);
	}

	@Override
	public IModel getModel(IPlayerStub[] players, float screenRatio, String seed) {
		Random random = new Random(seed.hashCode());
		float width, height;
		if(screenRatio < 1){ // horizontal
			width = 18;
			height = width*screenRatio;
		}else{
			height = 18;
			width = height/screenRatio;
		}
		ILiveState state = new LiveState(width, height, 
				new RandomOrderer(random), 
				new SpawnFoodAction(new FoodFactory(random)),
//				new SimpleActorWatcher());
				new GridActorWatcher((int)width/2,(int)height/2, height, width));
		
		IAntFactory antFactory = new AntFactory(random);
		IAction anthillAction = new CompoundAction(Arrays.asList(new IAction[]{
				new RotateAction(0.5f),
				new HealYourselfAction(10),
				new SpawnAntAction(antFactory, new SimpleCollision(), 1.5f)
				}));
		
		for(IPlayerStub playerStub : players){
			ILivePlayer player = new Player(state.nextPlayerId(), playerStub.getName(), playerStub.getColor(), 0, 1000);
			state.addPlayer(player);
			state.addActor(new Actor(ActorType.ANTHILL, state.nextActorId(), player.getId(), anthillAction,
				new Position(random.nextFloat()*width, random.nextFloat()*height), 
				0.5f, //radius
				random.nextFloat()*(float)(2*Math.PI), //angle
				1000, //HP
				1000, //maxHP
				Path.EMPTY_PATH));
		}
		state.nextTurn(0);
		return new Model(state);
	}

}
