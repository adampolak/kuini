package pl.edu.uj.tcs.kuini.model.live;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.Path;
import pl.edu.uj.tcs.kuini.model.actions.IAction;
import pl.edu.uj.tcs.kuini.model.actions.NullAction;
import pl.edu.uj.tcs.kuini.model.geometry.Position;

public class Actor implements ILiveActor {
	private Position position;
	private IAction action;
	private float radius;
	private final ActorType actorType;
	private final long id;
	private float angle;
	private final int playerId;
	private float hp;
	private float maxHp;
	private Path path;
	private LivePath livePath;
	public Actor(ActorType actorType, long id, int playerId, IAction action, 
			Position position, float radius, float angle, float hp, float maxHp, Path path) {
		this.action = action;
		this.position = position;
		this.radius = radius;
		this.actorType = actorType;
		this.id = id;
		this.angle = angle;
		this.playerId = playerId;
		this.hp = hp;
		this.maxHp = maxHp;
		setPath(path);
	}
	public Actor(ActorType actorType, long id, int playerId) {
		this(actorType, id, playerId, new NullAction());
	}
	public Actor(ActorType actorType, long id, int playerId,
			IAction action) {
		this(actorType, id, playerId, action, new Position(0,0), 0.01f, 0, 100, 100, Path.EMPTY_PATH);
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
		return 0.1f;
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
		hp = Math.min(hp, maxHp);
	}
	@Override
	public boolean isDead() {
		return hp < 0;
	}
	@Override
	public void setPath(Path path) {
		this.path = path;
		this.livePath = new LivePath(path);
	}
	@Override
	public Path getPath() {
		return path;
	}
	@Override
	public float getMaxHP(){
		return maxHp;
	}
	
	@Override
	public void setAngle(float angle) {
		this.angle = (float)(angle%(2*Math.PI));
	}
	@Override
	public LivePath getLivePath() {
		return livePath;
	}
	@Override
	public float getNeigbourhoodRadius() {
		return 5*getRadius();
	}
}
