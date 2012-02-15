package pl.edu.uj.tcs.kuini.model.live;

import pl.edu.uj.tcs.kuini.model.PlayerColor;

public class Player implements ILivePlayer {
	private final int id;
	private final String name;
	private final PlayerColor color;
	private float score;
	private float food;

	public Player(int id, String name, PlayerColor color, float score, float food) {
		this.id = id;
		this.name = name;
		this.color = color;
		this.score = score;
		this.food = food;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public PlayerColor getColor() {
		return color;
	}

	@Override
	public float getScore() {
		return score;
	}

	@Override
	public float getFood() {
		return food;
	}

	@Override
	public void changeScore(float change) {
		score += change;
	}

	@Override
	public boolean shouldAttack(ILivePlayer player) {
		int otherId = player.getId();
		return player.isHuman() && otherId != getId();
	}

	@Override
	public boolean isHuman() {
		return getId()>0;
	}

	@Override
	public void changeFood(float change) {
		food += change;
	}
	
	@Override
	public int hashCode(){
		return (int)(5 + 7*id + 11*name.hashCode() + 13*color.hashCode() + 17*score + 19*food);
	}
}
