package pl.edu.uj.tcs.kuini.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.uj.tcs.kuini.model.Actor;
import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.IPlayer;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.model.Player;
import pl.edu.uj.tcs.kuini.model.PlayerColor;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.state.State;

public class EmptyController implements IController{
	
    Player player0;
    Map<Integer, IPlayer> map;
    List<IActor> list;
    
    public EmptyController() {
        player0 = new Player(0, "zero", PlayerColor.BLUE, 0, 0);
        map = new HashMap<Integer, IPlayer>();
        map.put(0, player0);
        list = new ArrayList<IActor>();
        list.add(new Actor(ActorType.ANT, 1, 0, null, new Position(10, 10), 200, 0, 5, 10, null));
        
    }
	//@Override
    public IState getCurrentState() {
        return new State(list, map, 440, 800);    
    }

    @Override
    public void proxyCommand(Command command) {
    }

}
