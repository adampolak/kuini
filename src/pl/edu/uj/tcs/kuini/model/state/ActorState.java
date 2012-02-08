package pl.edu.uj.tcs.kuini.model.state;

import java.io.Serializable;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.geometry.Position;

public class ActorState implements IActor, Serializable {
	private static final long serialVersionUID = -1927504484577547043L;
	private final Position position;
	private float radius;
	private final ActorType actorType;
	private final long id;
	private final float angle;
	private final int playerId;
	private final float hp;
	private final float maxHp;
	
	public ActorState(IActor actor){
		position = actor.getPosition();
		radius = actor.getRadius();
		actorType = actor.getActorType();
		id = actor.getId();
		angle = actor.getAngle();
		playerId = actor.getPlayerId();
		hp = actor.getHP();
		maxHp = actor.getMaxHP();
	}
	
	@Override
	public int hashCode(){
		return (int)(5 + 7*position.hashCode() + 11*radius + 13*actorType.hashCode())
			+ (int)(17*id + 19*angle + 23*playerId + 29*hp + 31*maxHp);
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public float getRadius() {
		return radius;
	}

	@Override
	public ActorType getActorType() {
		return actorType;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public float getAngle() {
		return angle;
	}

	@Override
	public int getPlayerId() {
		return playerId;
	}

	@Override
	public float getHP() {
		return hp;
	}
	
	@Override
	public float getMaxHP(){
		return maxHp;
	}
	
	@Override
	public String toString(){
		return actorType+"_State("+position+", hp:"+hp+"/"+maxHp+")";
	}

}
