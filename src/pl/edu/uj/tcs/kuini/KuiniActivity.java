package pl.edu.uj.tcs.kuini;

import pl.edu.uj.tcs.kuini.controller.EmptyController;
import pl.edu.uj.tcs.kuini.controller.IController;
import pl.edu.uj.tcs.kuini.view.GamePlayView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.SlidingDrawer;

public class KuiniActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();
        int height = display.getHeight();
        final EmptyController emptyController = new EmptyController();
        final GamePlayView gamePlayView = new GamePlayView(this, emptyController, width, height, 0);
        gamePlayView.stateChanged(emptyController.getCurrentState());
        setContentView(gamePlayView);
        new Thread() {
            
            @Override
            public void run() {
                while(true) {
                    try {
                        sleep(100);
                        emptyController.pushTime();
                        gamePlayView.stateChanged(emptyController.getCurrentState());
                        Log.d("TIME PUSH", "hurray");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                }
            }
        }.run();
    }
}