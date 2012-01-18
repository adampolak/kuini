package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.factories.IAntFactory;
import pl.edu.uj.tcs.kuini.model.geometry.Vector;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class SpawnAntAction implements IAction {
	
	private IAntFactory antFactory;
	
	public SpawnAntAction(IAntFactory antFactory){
		this.antFactory = antFactory;
	}

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		ILivePlayer player = state.getLivePlayersById().get(actor.getPlayerId());
		if(player.getFood() > 100){
			ILiveActor ant = antFactory.getAnt(state, actor.getPlayerId());
			ant.setPosition(new Vector(actor.getPosition(), 
					ant.getRadius()+actor.getRadius(), 
					actor.getAngle()).getTarget());
			state.addActor(ant);
			player.changeFood(-100);
		}
	}

}
