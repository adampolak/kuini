package pl.edu.uj.tcs.kuini.model.live;

import pl.edu.uj.tcs.kuini.model.IPlayer;

public interface ILivePlayer extends IPlayer {
	void changeScore(long change);
	boolean shouldAttack(ILivePlayer player);
	void changeFood(float change);
}
