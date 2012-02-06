package pl.edu.uj.tcs.kuini.gui;

import pl.edu.uj.tcs.kuini.controller.AbstractGame;
import pl.edu.uj.tcs.kuini.controller.DemoGame;
import pl.edu.uj.tcs.kuini.controller.IView;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.view.KuiniView;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class KuiniActivity extends Activity implements IView {

    public static final int HOST_GAME = 0;
    public static final int JOIN_GAME = 1;
    public static final int DEMO_GAME = 2;
   
    private AbstractGame game = null;
    private KuiniView gamePlayView = null; 
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Display display = getWindowManager().getDefaultDisplay(); 
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //int width = display.getWidth();
        //int height = display.getHeight();
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels; 
        
        gamePlayView = new KuiniView(this, width, height);
                
        game = new DemoGame(this);
        
        gamePlayView.setCommandProxy(game);
        
        game.start();
        this.setContentView(gamePlayView);
        
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        game.interrupt();
    }

    @Override
    public void gameStarted(int playerId) {
        gamePlayView.setPlayerId(playerId);
        // TODO Auto-generated method stub
        
    }

    @Override
    public void gameBroken() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void gameFinished() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stateChanged(IState state) {
        gamePlayView.stateChanged(state);
        
    }
    
}