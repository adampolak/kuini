package pl.edu.uj.tcs.kuini.model.actions;

import pl.edu.uj.tcs.kuini.model.factories.IFoodFactory;
import pl.edu.uj.tcs.kuini.model.live.ILiveState;

public class SpawnFoodAction implements IGlobalAction {
	private float accumulatedTime;
	private float spawnTime = 10.0f;
	private int spawnCount = 1;
	private final IFoodFactory factory;
	public SpawnFoodAction(IFoodFactory factory){
		this.factory = factory;
	}
	@Override
	public void performAction(float elapsedTime, ILiveState state) {
		accumulatedTime += elapsedTime;
		if(accumulatedTime > spawnTime){
			for(int i=0;i<spawnCount;i++){
				state.addActor(factory.getFood(state));
			}
			accumulatedTime -= spawnTime;
		}
	}

}
