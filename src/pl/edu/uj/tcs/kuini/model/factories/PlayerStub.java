package pl.edu.uj.tcs.kuini.model.factories;

import java.io.Serializable;

public class PlayerStub implements Serializable {

    private static final long serialVersionUID = -3053809265967437353L;

    private final String name;
	private final int id;
	
	public PlayerStub(String name, int id){
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

}
