package pl.edu.uj.tcs.kuini.model.actions;

import java.util.HashMap;
import java.util.Map;

import pl.edu.uj.tcs.kuini.model.factories.IAntFactory;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.geometry.Vector;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class SpawnAntAction implements IAction {
	
    public static final float ANT_PRICE = 100.0f;
    
    private final float cooldown;
	private final IAntFactory antFactory;
	private final Map<Long, Float> cooldownLeftByActorId = new HashMap<Long, Float>();
	private final ICollisionResolver collisionResolver;
	
	public SpawnAntAction(IAntFactory antFactory, ICollisionResolver collisionResolver, float cooldown){
		this.antFactory = antFactory;
		this.cooldown = cooldown;
		this.collisionResolver = collisionResolver;
	}

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		if(cooldownLeftByActorId.get(actor.getId()) == null)
			cooldownLeftByActorId.put(actor.getId(), cooldown);
		float cooldownLeft = cooldownLeftByActorId.get(actor.getId());
		cooldownLeft = Math.max(-0.01f, cooldownLeft-elapsedTime);
		
		ILivePlayer player = state.getLivePlayersById().get(actor.getPlayerId());
		// Log.d("SPAWN", "Player: "+player+" food: "+player.getFood()+" cooldown: "+cooldownLeft);
		if(player.getFood() >= ANT_PRICE && cooldownLeft <= 0.0f){
			ILiveActor ant = antFactory.getAnt(state, actor.getPlayerId());
			ant.setPosition(actor.getPosition());
			Position target = collisionResolver.computePosition(ant, new Vector(actor.getPosition(), 
					actor.getAngle(),
					(ant.getRadius()+actor.getRadius())*1.1f
					).getTarget() , state);
			if(!target.equals(ant.getPosition())){
				ant.setPosition(target);
				ant.setAngle(actor.getAngle());
				state.addActor(ant);
				player.changeFood(-100);
				cooldownLeft = cooldown;
			}
		}
		cooldownLeftByActorId.put(actor.getId(), cooldownLeft);
	}

}
