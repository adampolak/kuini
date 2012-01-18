package pl.edu.uj.tcs.kuini.model.live;

import java.util.List;
import java.util.Map;

import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.model.geometry.Position;

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
	List<ILiveActor> getNeigbours(Position position, float radius);
	void nextTurn(float elapsedTime);
	void doCommand(Command command);
	long nextActorId();
	int nextPlayerId();
}
