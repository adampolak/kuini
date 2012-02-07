package pl.edu.uj.tcs.kuini.controller;

import java.io.IOException;

import pl.edu.uj.tcs.kuini.model.PlayerColor;
import pl.edu.uj.tcs.kuini.model.factories.IPlayerStub;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import pl.edu.uj.tcs.kuini.model.factories.PlayerStub;

public class DemoGame extends AbstractGame {

    private static final int DEMO_PLAYER_ID = 1;
    
    public DemoGame(IView view) {
        super(view);
    }
    
    public void run() {
        
        model = new ModelFactory().getModel(
                new IPlayerStub[]{
                        new PlayerStub("RED", PlayerColor.RED),
                        new PlayerStub("BLUE", PlayerColor.BLUE),
                        new PlayerStub("GREEN", PlayerColor.GREEN)
                }, 800.0f/480.0f, "ANTS!"
        );

        ControllersServer server = new ControllersServer();
        
        try {
            createLocalController(server, DEMO_PLAYER_ID);
        } catch (IOException e) {
            view.gameBroken();
            return;
        }
        
        try {
            sleep(3500);
        } catch (InterruptedException e) {
            return;
        }
        
        
        
        server.start();
        controller.start();
        
        view.gameStarted(DEMO_PLAYER_ID);
        
        try {
            controller.join();
        } catch (InterruptedException e) {
            controller.interrupt();
        }
        
        server.interrupt();
        
        // TODO: ask model if the anyone won the game
        view.gameBroken();
        
    }
    
    
    
}
