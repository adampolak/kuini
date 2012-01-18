package pl.edu.uj.tcs.kuini.model;

public class Actor implements IActor {
	private Position position;
	private float radius;
	private final ActorType actorType;
	private final long id;
	private float angle;
	private final int playerId;
	public Actor(Position position, float radius, ActorType actorType, long id,
			float angle, int playerId) {
		this.position = position;
		this.radius = radius;
		this.actorType = actorType;
		this.id = id;
		this.angle = angle;
		this.playerId = playerId;
	}
	public Actor(ActorType actorType, long id, int playerId) {
		this.position = new Position(0,0);
		this.radius = 0.01f;
		this.angle = 0;
		this.actorType = actorType;
		this.id = id;
		this.playerId = playerId;
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
}
