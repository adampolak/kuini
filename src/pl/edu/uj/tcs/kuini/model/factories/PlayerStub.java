package pl.edu.uj.tcs.kuini.model.factories;

import pl.edu.uj.tcs.kuini.model.PlayerColor;

public class PlayerStub implements IPlayerStub {
	private final String name;
	private final PlayerColor color;
	
	public PlayerStub(String name, PlayerColor color){
		this.name = name;
		this.color = color;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public PlayerColor getColor() {
		return color;
	}

}
