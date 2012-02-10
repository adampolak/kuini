package pl.edu.uj.tcs.kuini.model.factories;

public class PlayerStub implements IPlayerStub {
	private final String name;
	private final int id;
	
	public PlayerStub(String name, int id){
		this.name = name;
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getId() {
		return id;
	}

}
