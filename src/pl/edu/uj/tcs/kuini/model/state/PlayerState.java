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

    public PlayerState(int id, String name, PlayerColor color, long score) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.score = score;
    }
    public PlayerState(IPlayer player){
    	this.id = player.getId();
    	this.name = player.getName();
    	this.color = player.getColor();
    	this.score = player.getScore();
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
