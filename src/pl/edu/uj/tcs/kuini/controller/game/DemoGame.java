package pl.edu.uj.tcs.kuini.controller.game;

import java.io.IOException;

import pl.edu.uj.tcs.kuini.controller.SerialControllersServer;
import pl.edu.uj.tcs.kuini.model.factories.ModelFactory;
import pl.edu.uj.tcs.kuini.model.factories.PlayerStub;

public class DemoGame extends AbstractGame {

    public DemoGame(IView view) {
        super(view);
    }
    
    public void run() {
        
        model = ModelFactory.getModel(
                new PlayerStub[]{
                        new PlayerStub(playerName, 1),
                        new PlayerStub("Opponent 1", 2),
                        new PlayerStub("Opponent 2", 3),
                        new PlayerStub("Opponent 3", 4)
                }, RATIO, System.currentTimeMillis(), 1.5f, true
        );

        SerialControllersServer server = new SerialControllersServer();
        
        try {
            createLocalController(server, 1);
        } catch (IOException e) {
            view.gameFinished();
            return;
        }
        
        try { 
            sleep(1000);    /* Only for testing/debugging purpose */
        } catch (InterruptedException e) {
            view.gameFinished();
            return;
        }
        
        server.start();
        
        view.gameStarted(1);
        
        controller.run();
        
        server.interrupt();
        
        view.gameFinished();
        
    }
    
    
    
}
