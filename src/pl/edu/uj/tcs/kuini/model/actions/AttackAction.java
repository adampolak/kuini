package pl.edu.uj.tcs.kuini.model.actions;

import java.util.Map;

import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class AttackAction implements IAction {

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		Map<Integer, ILivePlayer> players = state.getLivePlayersById();
		ILivePlayer player = players.get(actor.getPlayerId());
		for(ILiveActor neighbour:state.getNeigbours(actor.getPosition(), 3*actor.getRadius())){
			if(player.shouldAttack(players.get(neighbour.getPlayerId()))){
				neighbour.changeHP(-10*elapsedTime);
			}
		}
	}

}
