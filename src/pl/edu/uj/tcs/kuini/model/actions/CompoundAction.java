package pl.edu.uj.tcs.kuini.model.actions;

import java.util.ArrayList;
import java.util.List;

import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class CompoundAction implements IAction {
	private final List<IAction> actions;
	public CompoundAction(List<IAction> actions){
		this.actions = new ArrayList<IAction>(actions);
	}
	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		for(IAction action : actions){
			action.performAction(actor, elapsedTime, state);
		}
	}

}
