package pl.edu.uj.tcs.kuini.model.state;

import java.io.Serializable;

import pl.edu.uj.tcs.kuini.model.IPlayer;
import pl.edu.uj.tcs.kuini.model.PlayerColor;

public class PlayerState implements Serializable, IPlayer{
	private static final long serialVersionUID = 6628465411701306103L;
	private final int id;
    private final String name;
    private final PlayerColor color;
    private final long score;
    private final long food;
	private final boolean human;

    public PlayerState(int id, String name, PlayerColor color, long score, long food, boolean human) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.score = score;
        this.food = food;
        this.human = human;
    }
    public PlayerState(IPlayer player){
    	this(player.getId(), 
    			player.getName(),
    			player.getColor(),
    			player.getScore(),
    			player.getFood(),
    			player.isHuman());
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
	@Override
	public boolean isHuman() {
		return human;
	}
	
	@Override
	public int hashCode(){
		return (int)(5 + 7*id + 11*name.hashCode() + 13*color.hashCode() + 17*score + 19*food);
	}
	
	@Override
	public String toString(){
		return "PlayerState[#"+hashCode()+"] id: "+id+" name: "+name+" color: "+color
			+" score: "+score+" food: "+food;
	}

}
