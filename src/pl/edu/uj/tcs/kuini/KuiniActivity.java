package pl.edu.uj.tcs.kuini;

import pl.edu.uj.tcs.kuini.controller.EmptyController;
import pl.edu.uj.tcs.kuini.controller.IController;
import pl.edu.uj.tcs.kuini.view.GamePlayView;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;

public class KuiniActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        Display display = getWindowManager().getDefaultDisplay(); 
        int width = display.getWidth();
        int height = display.getHeight();
        IController emptyController = new EmptyController();
        final GamePlayView gamePlayView = new GamePlayView(this, emptyController, width, height, 0);
        gamePlayView.stateChanged(emptyController.getCurrentState());
        setContentView(gamePlayView);
    }
}