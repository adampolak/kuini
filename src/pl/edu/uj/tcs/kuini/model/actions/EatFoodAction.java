package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
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
		for(ILiveActor food : state.getNeighbours(actor.getPosition(), eatingRadius)){
			if(food.getActorType() != ActorType.FOOD)continue;
			float foodEaten = Math.max(0, Math.min(food.getHP(), elapsedTime*eatingSpeed));
			food.changeHP(-foodEaten);
			state.getLivePlayersById().get(actor.getPlayerId()).changeFood(foodEaten);
			return;
		}

	}

}
