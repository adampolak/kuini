package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;

import pl.edu.uj.tcs.kuini.controller.SerialControllersServer;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import pl.edu.uj.tcs.kuini.model.factories.PlayerStub;

public class DemoGame extends AbstractGame {

    private static final int DEMO_PLAYER_ID = 1;
    
    public DemoGame(IView view) {
        super(view);
    }
    
    public void run() {
        
        model = ModelFactory.getModel(
                new PlayerStub[]{
                        new PlayerStub("Player 1", 1),
                        new PlayerStub("Player 2", 2),
                        new PlayerStub("Player 3", 3)
                }, RATIO, 4475/*System.currentTimeMillis()*/, 1.5f, false
        );

        //ControllersServer server = new ControllersServer();
        SerialControllersServer server = new SerialControllersServer();
        
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
        
        view.gameFinished();
        
    }
    
    
    
}
