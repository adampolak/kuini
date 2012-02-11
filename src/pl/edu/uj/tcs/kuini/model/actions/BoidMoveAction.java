package pl.edu.uj.tcs.kuini.model.actions;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.LivePath;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.geometry.Vector;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class BoidMoveAction implements IAction {
	private final float followPathFactor = 0.1f;
	private final float velocityFactor = 0.005f;
	private final Random random;
	private final ICollisionResolver collisionResolver;
	
	public BoidMoveAction(Random random, ICollisionResolver collisionResolver){
		this.random = random;
		this.collisionResolver = collisionResolver;
	}

	private ILivePlayer getPlayer(ILiveActor actor, ILiveState state){
		return state.getLivePlayersById().get(actor.getPlayerId());
	}
	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		/*List<ILiveActor> friends = new LinkedList<ILiveActor>();
		List<ILiveActor> enemies = new LinkedList<ILiveActor>();
		List<ILiveActor> food = new LinkedList<ILiveActor>();
		ILivePlayer player = getPlayer(actor, state);
		
		for(ILiveActor neighbour : state.getNeighbours(actor.getPosition(), actor.getRadius()*5)){
			if(neighbour.getActorType() == ActorType.FOOD){
				food.add(neighbour);
			}else{
				if(player.shouldAttack(getPlayer(neighbour, state))){
					enemies.add(neighbour);
				}else{
					friends.add(neighbour);
				}
			}
		}*/
		Position velocityTarget = getVelocityTarget(actor, elapsedTime);
		Position pathTarget = getPathTarget(actor);
		
		Position target = average(new Position[]{velocityTarget, pathTarget}, 
				new float[]{velocityFactor, followPathFactor});
		
		Vector direction = new Vector(actor.getPosition(), target);
		actor.setAngle(direction.getAngle());
		float length = Math.min(direction.magnitude(), actor.getSpeed()*elapsedTime);
		actor.setPosition(collisionResolver.computePosition(actor, new Vector(direction, length).getTarget(), state));
	}
	
	private Position average(Position[] positions, float[] factors){
		float x=0, y=0;
		float sum = 0;
		for(int i=0;i<positions.length;i++){
			x += factors[i]*positions[i].getX();
			y += factors[i]*positions[i].getY();
			sum += factors[i];
		}
		x /= sum;
		y /= sum;
		return new Position(x, y);
	}
	
	private Position getVelocityTarget(ILiveActor actor, float elapsedTime){
		return new Vector(actor.getPosition(), 
		        actor.getAngle()+((float) ((random.nextFloat()-0.5)*0.5*elapsedTime)), 
				100.0f).getTarget();
	}
	
	private Position getPathTarget(ILiveActor actor) {
		LivePath path = actor.getLivePath();
		while(true){
			if(!path.hasNextPosition()){
				actor.setPath(Path.EMPTY_PATH);
				return actor.getPosition();
			}
			if(actor.getPosition().distanceTo(path.currentPosition()) < 2*actor.getRadius())
				path.nextPosition();
			else 
				break;
		}
		return path.currentPosition();
	}

}
