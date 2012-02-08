package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.UUID;

import pl.edu.uj.tcs.kuini.controller.Controller;
import pl.edu.uj.tcs.kuini.controller.ControllersServer;
import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IModel;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.view.ICommandProxy;

public abstract class AbstractGame extends Thread implements ICommandProxy {

    protected static final UUID KUINI_UUID = 
            UUID.fromString("c3246310-523d-11e1-b86c-0800200c9a66");
    
    protected final IView view;
    
    protected IModel model = null;
    protected Controller controller = null;
    
    public AbstractGame(IView view) {
        this.view = view;
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
    
    protected String getPlayerName() {
        return "";
    }

}
