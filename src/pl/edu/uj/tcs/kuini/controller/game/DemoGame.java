package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;

import pl.edu.uj.tcs.kuini.controller.ControllersServer;
import pl.edu.uj.tcs.kuini.model.PlayerStub;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;

public class DemoGame extends AbstractGame {

    private static final int DEMO_PLAYER_ID = 1;
    
    public DemoGame(IView view) {
        super(view);
    }
    
    public void run() {
        
        model = new ModelFactory().getModel(
                new PlayerStub[]{
                        new PlayerStub("Player 1", 1),
                        new PlayerStub("Player 2", 2),
                        new PlayerStub("Player 3", 3)
                }, 800.0f/480.0f, "ANTS!", 1.5f, false
        );

        ControllersServer server = new ControllersServer();
        
        try {
            createLocalController(server, DEMO_PLAYER_ID);
        } catch (IOException e) {
            view.gameFinished();
            return;
        }
        
        try { 
            sleep(500);    /* Only for testing/debugging purpose */
        } catch (InterruptedException e) {
            view.gameFinished();
            return;
        }
        
        server.start();
        
        view.gameStarted(DEMO_PLAYER_ID);
        
        controller.run();
        
        server.interrupt();
        
        // TODO: ask model if the anyone won the game
        view.gameFinished();
        
    }
    
    
    
}
