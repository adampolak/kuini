package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.UUID;

import pl.edu.uj.tcs.kuini.controller.Controller;
import pl.edu.uj.tcs.kuini.controller.ControllersServer;
import pl.edu.uj.tcs.kuini.controller.SerialControllersServer;
import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.model.Model;
import pl.edu.uj.tcs.kuini.view.ICommandProxy;
import android.content.Context;
import android.preference.PreferenceManager;

public abstract class AbstractGame extends Thread implements ICommandProxy {

    protected static final UUID KUINI_UUID = 
            UUID.fromString("c3246310-523d-11e1-b86c-0800200c9a66");
    
    protected static final float RATIO = 800.0f/480.0f;
    
    protected final IView view;
    
    protected Model model = null;
    protected Controller controller = null;
    
    protected String playerName = "";
    
    public AbstractGame(IView view) {
        this.view = view;
    }
    
    public void importPlayerNameFromContext(Context context) {
        playerName = PreferenceManager.getDefaultSharedPreferences(context).getString("playerName", "");
    }
    
    @Override
    public void proxyCommand(Command command) {
        if (controller != null)
            controller.proxyCommand(command);
    }
    
    protected final Controller.StateChangeListener stateChangeListener =
            new Controller.StateChangeListener() {
                @Override
                public void stateChanged(IState state) {
                    view.stateChanged(state);
                }
            };
    
    protected void createLocalController(SerialControllersServer server, int playerId) throws IOException {
        
        PipedInputStream serverToControllerIn = new PipedInputStream();
        PipedOutputStream serverToControllerOut = new PipedOutputStream();
        PipedInputStream controllerToServerIn = new PipedInputStream();
        PipedOutputStream controllerToServerOut = new PipedOutputStream();
    
        serverToControllerIn.connect(serverToControllerOut);
        controllerToServerIn.connect(controllerToServerOut);
    
        ObjectOutputStream serverToControllerObjOut = new ObjectOutputStream(serverToControllerOut);
        ObjectOutputStream controllerToServerObjOut = new ObjectOutputStream(controllerToServerOut);
        ObjectInputStream serverToControllerObjIn = new ObjectInputStream(serverToControllerIn);
        ObjectInputStream controllerToServerObjIn = new ObjectInputStream(controllerToServerIn);
       
        server.addPlayer(
                controllerToServerObjIn,
                serverToControllerObjOut,
                playerId
                );
        
        controller = new Controller(
                serverToControllerObjIn,
                controllerToServerObjOut,
                model,
                stateChangeListener);    
    }
    
    @Deprecated
    protected void createLocalController(ControllersServer server, int playerId) throws IOException {
        
        PipedInputStream serverToControllerIn = new PipedInputStream();
        PipedOutputStream serverToControllerOut = new PipedOutputStream();
        PipedInputStream controllerToServerIn = new PipedInputStream();
        PipedOutputStream controllerToServerOut = new PipedOutputStream();
    
        serverToControllerIn.connect(serverToControllerOut);
        controllerToServerIn.connect(controllerToServerOut);
    
        ObjectOutputStream serverToControllerObjOut = new ObjectOutputStream(serverToControllerOut);
        ObjectOutputStream controllerToServerObjOut = new ObjectOutputStream(controllerToServerOut);
        ObjectInputStream serverToControllerObjIn = new ObjectInputStream(serverToControllerIn);
        ObjectInputStream controllerToServerObjIn = new ObjectInputStream(controllerToServerIn);
       
        server.addPlayer(
                controllerToServerObjIn,
                serverToControllerObjOut,
                playerId
                );
        
        controller = new Controller(
                serverToControllerObjIn,
                controllerToServerObjOut,
                model,
                stateChangeListener);    
    }

}
