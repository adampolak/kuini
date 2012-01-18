package pl.edu.uj.tcs.kuini.model.state;

import java.io.Serializable;

import pl.edu.uj.tcs.kuini.model.IPlayer;
import pl.edu.uj.tcs.kuini.model.PlayerColor;

public class PlayerState implements Serializable, IPlayer{
	private static final long serialVersionUID = 6628465411701306103L;
	final int id;
    final String name;
    final PlayerColor color;
    final long score;
    final long food;

    public PlayerState(int id, String name, PlayerColor color, long score, long food) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.score = score;
        this.food = food;
    }
    public PlayerState(IPlayer player){
    	this(player.getId(), 
    			player.getName(),
    			player.getColor(),
    			player.getScore(),
    			player.getFood());
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
	@Override
	public long getFood() {
		return food;
	}

}
