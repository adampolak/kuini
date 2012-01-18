package pl.edu.uj.tcs.kuini.model;

import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;

public class Player implements ILivePlayer {
	private final int id;
	private final String name;
	private final PlayerColor color;
	private long score;
	private long food;

	public Player(int id, String name, PlayerColor color, long score, long food) {
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
	public long getScore() {
		return score;
	}

	@Override
	public long getFood() {
		return food;
	}

	@Override
	public void changeScore(long change) {
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
}
