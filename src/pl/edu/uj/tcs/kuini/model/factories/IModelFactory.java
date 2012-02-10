package pl.edu.uj.tcs.kuini.model.factories;

import pl.edu.uj.tcs.kuini.model.IModel;

public interface IModelFactory {
	IModel getModel(IPlayerStub[] players, float screenRatio, String seed,
			float gameSpeed, boolean healAnts, int maxActors);
}
