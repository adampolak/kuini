package pl.edu.uj.tcs.kuini.model.actions;

import java.util.HashMap;
import java.util.Map;

import pl.edu.uj.tcs.kuini.model.factories.IAntFactory;
import pl.edu.uj.tcs.kuini.model.geometry.Vector;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class SpawnAntAction implements IAction {
	private final float cooldown;
	private final IAntFactory antFactory;
	private final Map<Long, Float> cooldownLeftByActorId = new HashMap<Long, Float>();
	
	public SpawnAntAction(IAntFactory antFactory, float cooldown){
		this.antFactory = antFactory;
		this.cooldown = cooldown;
	}

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		if(cooldownLeftByActorId.get(actor.getId()) == null)
			cooldownLeftByActorId.put(actor.getId(), cooldown);
		float cooldownLeft = cooldownLeftByActorId.get(actor.getId());
		cooldownLeft = Math.max(0.0f, cooldownLeft-elapsedTime);
		
		ILivePlayer player = state.getLivePlayersById().get(actor.getPlayerId());
		if(player.getFood() >= 100 && cooldownLeft == 0.0f){
			ILiveActor ant = antFactory.getAnt(state, actor.getPlayerId());
			ant.setPosition(new Vector(actor.getPosition(), 
					actor.getAngle(),
					ant.getRadius()+actor.getRadius()
					).getTarget());
			ant.setAngle(actor.getAngle());
			state.addActor(ant);
			player.changeFood(-100);
			cooldownLeft = cooldown;
		}
		cooldownLeftByActorId.put(actor.getId(), cooldownLeft);
	}

}
