package pl.edu.uj.tcs.kuini.model.live;

import java.util.List;
import java.util.Map;

import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IState;

/**
 * Interface describing state that can be changed.
 */
public interface ILiveState extends IState {
	List<ILiveActor> getLiveActors();
	Map<Integer, ILivePlayer> getLivePlayersById();
	void addActor(ILiveActor actor);
	void addPlayer(ILivePlayer player);
	List<ILiveActor> getNeighbours(ILiveActor actor);
	/**
	 * Returns neighbours in given radius. Radius should 
	 * be not greater than actor's neighbourhood radius.
	 * @param actor
	 * @param radius
	 * @return
	 */
	List<ILiveActor> getNeighbours(ILiveActor actor, float radius);
	void nextTurn(float elapsedTime);
	void doCommand(Command command);
	long nextActorId();
	int nextPlayerId();
}
