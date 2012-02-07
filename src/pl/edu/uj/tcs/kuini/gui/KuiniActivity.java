package pl.edu.uj.tcs.kuini.gui;

import pl.edu.uj.tcs.kuini.R;
import pl.edu.uj.tcs.kuini.controller.AbstractGame;
import pl.edu.uj.tcs.kuini.controller.DemoGame;
import pl.edu.uj.tcs.kuini.controller.IView;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.view.KuiniView;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class KuiniActivity extends Activity implements IView {

    public static final int HOST_GAME = 0;
    public static final int JOIN_GAME = 1;
    public static final int DEMO_GAME = 2;
   
    private View splash;
    private ProgressBar splashProgressBar;
    private TextView splashText;
    
    private AbstractGame game = null;
    private KuiniView view;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.splash);
        
        splash = findViewById(R.id.splash);
        splashProgressBar = (ProgressBar)findViewById(R.id.splashProgressBar);
        splashText = (TextView)findViewById(R.id.splashText);
        
        splashProgressBar.setVisibility(View.VISIBLE);
        splashText.setText(R.string.splash_before);
        
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels; 
        
        view = new KuiniView(this, width, height);
                
        game = new DemoGame(this);
        
        view.setCommandProxy(game);
        
        game.start();
        
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (game != null) game.interrupt();
    }

    @Override
    public void gameStarted(final int playerId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                view.setPlayerId(playerId);
                setContentView(view);
            }
        });
    }

    @Override
    public void gameBroken() {
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                splashProgressBar.setVisibility(View.GONE);
                splashText.setText(R.string.splash_broken);
                setContentView(splash);
            }
        });
    }

    @Override
    public void stateChanged(final IState state) {
        view.stateChanged(state);
    }
    
}