package pl.edu.uj.tcs.kuini.model;

public class Player {
	final int id;
	final String name;
	final PlayerColor color;
	final long score;

	public Player(int id, String name, PlayerColor color, long score) {
		super();
		this.id = id;
		this.name = name;
		this.color = color;
		this.score = score;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public PlayerColor getColor() {
		return color;
	}

	public long getScore() {
		return score;
	}
	
}
