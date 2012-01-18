package pl.edu.uj.tcs.kuini.model.live;

import java.util.List;
import java.util.Map;

import pl.edu.uj.tcs.kuini.model.IState;

/**
 * Interface describing state that can be changed.
 */
public interface ILiveState extends IState {
	List<ILiveActor> getLiveActors();
	Map<Integer, ILivePlayer> getLivePlayersById();
	void addActor(ILiveActor actor);
	void removeActor(ILiveActor actor);
	void addPlayer(ILivePlayer player);
	void removePlayer(ILivePlayer player);
}
