package pl.edu.uj.tcs.kuini;

import java.util.Timer;
import java.util.TimerTask;

import pl.edu.uj.tcs.kuini.controller.EmptyController;
import pl.edu.uj.tcs.kuini.controller.IController;
import pl.edu.uj.tcs.kuini.view.GamePlayView;
import pl.edu.uj.tcs.kuini.view.IGamePlayView;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Display;
import android.widget.SlidingDrawer;

public class KuiniActivity extends Activity {

    private final int DEMO_ID = 1;
    private final long WAITING_TIME = 50; /* [ms] */
    
    private SimpleGame game = null;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Activity", "onCreate");
        //setContentView(R.layout.main);
        /*
        Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();
        int height = display.getHeight();
        final EmptyController emptyController = new EmptyController();
        final GamePlayView gamePlayView = new GamePlayView(this, width, height, DEMO_ID);
        */
        /*
        gamePlayView.setController(emptyController);
        gamePlayView.stateChanged(emptyController.getCurrentState());
        setContentView(gamePlayView);
        
        TimerTask timerTask = new TimerTask() {
            
            @Override
            public void run() {                       
                emptyController.pushTime();
                gamePlayView.stateChanged(emptyController.getCurrentState());
                Log.d("TIME PUSH", "hurray");                                                          
            }
        };
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 100);
        //*/
        /*
        try {
            SimpleGame game = new SimpleGame(gamePlayView, DEMO_ID, WAITING_TIME);
            game.start();
        } catch (Exception e) {}
        setContentView(gamePlayView);
        //*/
        
    }
    
    @Override
    public void onStart() {
        super.onStart();
        Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();
        int height = display.getHeight();
        GamePlayView gamePlayView = new GamePlayView(this, width, height, DEMO_ID);
        try {
            game = new SimpleGame(gamePlayView, DEMO_ID, WAITING_TIME);
        } catch (Exception e) {}
        game.start();
        setContentView(gamePlayView);
    }
    
    @Override
    public void onStop() {
        super.onStop();
        game.stop();
    }
    
}