package pl.edu.uj.tcs.kuini.model.factories;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.Model;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.PlayerColor;
import pl.edu.uj.tcs.kuini.model.RandomOrderer;
import pl.edu.uj.tcs.kuini.model.SimpleActorWatcher;
import pl.edu.uj.tcs.kuini.model.actions.CompoundAction;
import pl.edu.uj.tcs.kuini.model.actions.HealYourselfAction;
import pl.edu.uj.tcs.kuini.model.actions.IAction;
import pl.edu.uj.tcs.kuini.model.actions.RotateAction;
import pl.edu.uj.tcs.kuini.model.actions.SimpleCollision;
import pl.edu.uj.tcs.kuini.model.actions.SpawnAntAction;
import pl.edu.uj.tcs.kuini.model.actions.SpawnFoodAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.Actor;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;
import pl.edu.uj.tcs.kuini.model.live.LiveState;
import pl.edu.uj.tcs.kuini.model.live.Player;

public class ModelFactory {
	
    private static final int DEFAULT_MAX_ACTORS = 50;
    
    private ModelFactory() {}
    
	private static Vector<PlayerColor> getColors(Random random){
		Vector<PlayerColor> colors = new Vector<PlayerColor>(Arrays.asList(
				new PlayerColor[]{PlayerColor.BLUE, PlayerColor.GREEN, PlayerColor.RED, PlayerColor.BLACK}));
		Collections.shuffle(colors, random);
		return colors;
	}
	
	private static Position[] getAnthillsPositions(Random random, float width, float height, float radius, int anthills){
		radius *= 2;
		Position[] result = new Position[anthills];
		Position[] tmp;
		if(anthills != 3){
			 tmp = new Position[]{
					new Position(radius, radius),
					new Position(width-radius, height-radius),
					new Position(radius, height-radius),
					new Position(width-radius, radius)
			};
		}else{
			 tmp = new Position[]{
				/*
			    new Position(radius*0.7f+(width-radius)*0.3f, radius),
				new Position(width-radius, (height-radius)*0.7f+radius*0.3f),
				new Position(radius, height-radius)
				*/
			    new Position(radius*0.8f+(width-radius)*0.2f, radius),
			    new Position(radius*0.05f + (width-radius)*0.95f, (height-radius)*0.65f+radius*0.35f),
			    new Position(radius*0.75f+(width-radius)*0.25f, (height-radius)*0.85f+radius*0.15f)
			};
		}
		for(int i=0;i<anthills;i++)
			result[i] = tmp[i];
		Collections.shuffle(Arrays.asList(tmp), random);
		return result;
	}

	public static Model getModel(PlayerStub[] players, float screenRatio, long seed, float gameSpeed, boolean healAnts) {
		Random random = new Random(seed);
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
				new SpawnFoodAction(new FoodFactory(random), DEFAULT_MAX_ACTORS),
				//new GridActorWatcher((int)width/2,(int)height/2, height, width));
				new SimpleActorWatcher());
		
		IAntFactory antFactory = new AntFactory(random, healAnts);
		IAction anthillAction = new CompoundAction(Arrays.asList(new IAction[]{
				new RotateAction(0.5f),
				new HealYourselfAction(10),
				new SpawnAntAction(antFactory, new SimpleCollision(), 10.0f)
				}));
		
		Vector<PlayerColor> colors = getColors(random);
		float anthillRadius = 0.5f;
		Position[] anthills = getAnthillsPositions(random, width, height, anthillRadius, players.length);
		int idx = 0;
		for(PlayerStub playerStub : players){
			ILivePlayer player = new Player(playerStub.getId(), playerStub.getName(), colors.get(idx), 0, 500);
			state.addPlayer(player);
			state.addActor(new Actor(ActorType.ANTHILL, state.nextActorId(), player.getId(), anthillAction,
				anthills[idx],
				anthillRadius, //radius
				random.nextFloat()*(float)(2*Math.PI), //angle
				1000, //HP
				1000, //maxHP
				Path.EMPTY_PATH));
			idx++;
		}
		state.nextTurn(0);
		return new Model(state, gameSpeed);
	}
	
	public static class Arguments implements Serializable {
	    
	    private static final long serialVersionUID = 8236513382628197819L;
        
	    private final PlayerStub[] players;
	    private final float screenRatio;
	    private final long seed; 
	    private final float gameSpeed; 
	    private final boolean healAnts;
	    
	    public Arguments(PlayerStub[] players, float screenRatio, long seed, float gameSpeed, boolean healAnts) {
	        this.players = players;
	        this.screenRatio = screenRatio;
	        this.seed = seed;
	        this.gameSpeed = gameSpeed;
	        this.healAnts = healAnts;
	    }
	    
	}
	
	public static Model getModel(Arguments args) {
	    return getModel(args.players,
	            args.screenRatio,
	            args.seed,
	            args.gameSpeed,
	            args.healAnts); 
	}

}
