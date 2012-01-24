package pl.edu.uj.tcs.kuini.model;

import pl.edu.uj.tcs.kuini.model.state.IFrozenState;

public interface IModel {
	IFrozenState getState();
	void doCommand(Command command);
	void nextTurn(float elapsedTime);
}
