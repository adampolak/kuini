package pl.edu.uj.tcs.kuini;

import pl.edu.uj.tcs.kuini.view.GamePlayView;
import android.app.Activity;
import android.os.Bundle;
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
        setContentView(new GamePlayView(this, new EmptyController(), width, height));
    }
}