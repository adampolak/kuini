package pl.edu.uj.tcs.kuini.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pl.edu.uj.tcs.kuini.model.live.ILiveActor;

public class RandomOrderer implements IActorOrderer {

	private final Random random;
	public RandomOrderer(Random random) {
		this.random = random;
	}
	@Override
	public List<ILiveActor> orderActors(List<ILiveActor> actors) {
		List<ILiveActor> result = new ArrayList<ILiveActor>(actors);
		Collections.shuffle(result, random);
		return result;
	}

}
