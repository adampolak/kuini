package pl.edu.uj.tcs.kuini.model;

import java.util.List;
import java.util.Map;

public interface IState {

    public List<IActor> getActorStates();
    public Map<Integer, IPlayer> getPlayerStatesById();
}
