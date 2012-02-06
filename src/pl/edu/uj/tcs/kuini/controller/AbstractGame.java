package pl.edu.uj.tcs.kuini.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import pl.edu.uj.tcs.kuini.model.Command;
import pl.edu.uj.tcs.kuini.model.IModel;
import pl.edu.uj.tcs.kuini.model.IState;

public abstract class AbstractGame extends Thread implements ICommandProxy {

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

}
