package pl.edu.uj.tcs.kuini.model;

import pl.edu.uj.tcs.kuini.model.live.ILiveState;
import pl.edu.uj.tcs.kuini.model.state.IFrozenState;
import pl.edu.uj.tcs.kuini.model.state.State;

public class Model implements IModel{
	private ILiveState state;
	private final float gameSpeed;
	
	public Model(ILiveState startingState, float gameSpeed){
		this.state = startingState;
		this.gameSpeed = gameSpeed;
	}

	@Override
	public IFrozenState getState() {
		return new State(state);
	}

	@Override
	public void doCommand(Command command) {;
	    state.doCommand(command);
	}

	@Override
	public void nextTurn(float elapsedTime) {
		state.nextTurn(elapsedTime*gameSpeed);
	}

}
