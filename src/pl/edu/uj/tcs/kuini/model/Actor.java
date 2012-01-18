package pl.edu.uj.tcs.kuini.model;

import pl.edu.uj.tcs.kuini.model.actions.IAction;
import pl.edu.uj.tcs.kuini.model.actions.NullAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class Actor implements ILiveActor {
	private Position position;
	private IAction action;
	private float radius;
	private final ActorType actorType;
	private final long id;
	private float angle;
	private final int playerId;
	private float hp;
	public Actor(ActorType actorType, long id, int playerId, IAction action, 
			Position position, float radius, float angle, float hp) {
		this.action = action;
		this.position = position;
		this.radius = radius;
		this.actorType = actorType;
		this.id = id;
		this.angle = angle;
		this.playerId = playerId;
		this.hp = hp;
	}
	public Actor(ActorType actorType, long id, int playerId) {
		this(actorType, id, playerId, new NullAction(), new Position(0,0), 0.01f, 0, 100);
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
	public void performAction(float elapsedTime, ILiveState state) {
		if(!isDead())
			action.performAction(this, elapsedTime, state);
	}
	@Override
	public float getSpeed() {
		return 0.01f;
	}
	@Override
	public void setPosition(Position position) {
		this.position = position;
	}
	@Override
	public float getHP() {
		return hp;
	}
	@Override
	public void changeHP(float change) {
		hp += change;
	}
	@Override
	public boolean isDead() {
		return hp > 0;
	}
}
