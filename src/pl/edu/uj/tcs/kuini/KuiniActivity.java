package pl.edu.uj.tcs.kuini;

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
        final GamePlayView gamePlayView = new GamePlayView(this, new EmptyController(), width, height);
        setContentView(gamePlayView);
    }
}