package pl.edu.uj.tcs.kuini.model.actions;

import android.util.Log;
import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILivePlayer;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class EatFoodAction implements IAction {
	
	private final float eatingSpeed;
	private final float eatingRadius;
	
	public EatFoodAction(float eatingSpeed, float eatingRadius){
		this.eatingSpeed = eatingSpeed;
		this.eatingRadius = eatingRadius;
	}

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		for(ILiveActor food : state.getNeighbours(actor, eatingRadius)){
			if(food.getActorType() != ActorType.FOOD)continue;
			float foodEaten = Math.max(0, Math.min(food.getHP(), elapsedTime*eatingSpeed));
			ILivePlayer player = state.getLivePlayersById().get(actor.getPlayerId());
			Log.d("EAT FOOD", "Player: "+actor.getPlayerId()+" Food eaten: "+foodEaten +" ("+player.getFood()+")");
			food.changeHP(-foodEaten);
			player.changeFood(foodEaten);
			return;
		}

	}

}
