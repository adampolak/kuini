package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.live.ILiveActor;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class EatFoodAction implements IAction {

	@Override
	public void performAction(ILiveActor actor, float elapsedTime,
			ILiveState state) {
		for(ILiveActor food : state.getNeigbours(actor.getPosition(), actor.getRadius()*3)){
			if(food.getActorType() != ActorType.FOOD)continue;
			float foodEaten = Math.max(0, Math.min(food.getHP(), elapsedTime*10));
			food.changeHP(-foodEaten);
			state.getLivePlayersById().get(actor.getPlayerId()).changeFood(foodEaten);
			return;
		}

	}

}
