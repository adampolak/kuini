package pl.edu.uj.tcs.kuini;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import android.util.Log;

import pl.edu.uj.tcs.kuini.controller.ControllersServer;
import pl.edu.uj.tcs.kuini.controller.DefaultController;
import pl.edu.uj.tcs.kuini.model.IModel;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import pl.edu.uj.tcs.kuini.view.IGamePlayView;

public class SimpleGame {

    private final DefaultController controller;
    private final ControllersServer server;
    
    public SimpleGame(IGamePlayView view, int playerId, long waitingTime) throws Exception {
        
        Log.i("SG", "Creating SimpleGame...");
        
        server = new ControllersServer(waitingTime);
        
        Log.i("SG", "CServer created.");
        
        PipedInputStream serverToControllerIn = new PipedInputStream();
        PipedOutputStream serverToControllerOut = new PipedOutputStream();
        PipedInputStream controllerToServerIn = new PipedInputStream();
        PipedOutputStream controllerToServerOut = new PipedOutputStream();
        
        Log.i("SG", "Pipes created.");
        
        serverToControllerIn.connect(serverToControllerOut);
        controllerToServerIn.connect(controllerToServerOut);
        
        
        ObjectOutputStream serverToControllerObjOut = new ObjectOutputStream(serverToControllerOut);
        ObjectOutputStream controllerToServerObjOut = new ObjectOutputStream(controllerToServerOut);
        ObjectInputStream serverToControllerObjIn = new ObjectInputStream(serverToControllerIn);
        ObjectInputStream controllerToServerObjIn = new ObjectInputStream(controllerToServerIn);
        
        Log.i("SG", "Pipes connected.");
        
        server.addPlayer(
                controllerToServerObjIn,
                serverToControllerObjOut,
                playerId);
        
        Log.i("SG", "Client added to server.");
        
        IModel model = new ModelFactory().getModel();
        
        Log.i("SG", "Model created.");
        
        controller = new DefaultController(
                serverToControllerObjIn,
                controllerToServerObjOut,
                model);
        
        Log.i("SG", "Controller created.");
        
        view.setController(controller);
        controller.setView(view);
        
        Log.i("SG", "Controller and view connected to each other.");
        Log.i("SG", "Game created :)");
        
    }
    
    public void start() {
        
        server.start();
        controller.start();
        
        Log.i("SG", "Game started :)");
        
    }
    
    public void stop() {
        controller.interrupt();
        server.interrupt();
        
    }
    
    
}
