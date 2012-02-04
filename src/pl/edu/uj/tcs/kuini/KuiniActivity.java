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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.SlidingDrawer;

public class KuiniActivity extends Activity {

    private final int DEMO_ID = 1;
   
    private SimpleGame game = null;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Activity", "onCreate");
    }
    
    @Override
    public void onStart() {
        super.onStart();
        //Display display = getWindowManager().getDefaultDisplay(); 
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //int width = display.getWidth();
        //int height = display.getHeight();
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels; 
        
        GamePlayView gamePlayView = new GamePlayView(this, width, height, DEMO_ID);
        try {
            game = new SimpleGame(gamePlayView, DEMO_ID);
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