package pl.edu.uj.tcs.kuini.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import pl.edu.uj.tcs.kuini.model.Actor;
import pl.edu.uj.tcs.kuini.model.ActorType;
import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IActor;
import pl.edu.uj.tcs.kuini.model.IModel;
import pl.edu.uj.tcs.kuini.model.IPlayer;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.model.Player;
import pl.edu.uj.tcs.kuini.model.PlayerColor;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import pl.edu.uj.tcs.kuini.model.geometry.Position;
import pl.edu.uj.tcs.kuini.model.state.State;

public class EmptyController implements IController{
	
    State state;
    
    public EmptyController() {
/*        Player player0 = new Player(0, "zero", PlayerColor.BLUE, 0, 0);
        Map map = new HashMap<Integer, IPlayer>();
        map.put(0, player0);
        List<IActor> list = new ArrayList<IActor>();
        list.add(new Actor(ActorType.ANT, 1, 0, null, new Position(10, 10), 200, 0, 5, 10, null));
        state = new State(list, map, 440, 800);
  */  }
	//@Override
    public IState getCurrentState() {
        IModel model = new ModelFactory().getModel();
        Log.d("DEBUG", model.getState().getActorStates().toString());
        return model.getState();
        //return state;
    }

    @Override
    public void proxyCommand(Command command) {
    }

}
