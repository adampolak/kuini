package pl.edu.uj.tcs.kuini.gui;

import pl.edu.uj.tcs.kuini.R;
import pl.edu.uj.tcs.kuini.controller.game.AbstractGame;
import pl.edu.uj.tcs.kuini.controller.game.DemoGame;
import pl.edu.uj.tcs.kuini.controller.game.HostGame;
import pl.edu.uj.tcs.kuini.controller.game.IView;
import pl.edu.uj.tcs.kuini.controller.game.JoinGame;
import pl.edu.uj.tcs.kuini.model.IState;
import pl.edu.uj.tcs.kuini.view.KuiniView;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class KuiniActivity extends Activity implements IView {

    public static final int HOST_GAME = 0;
    public static final int JOIN_GAME = 1;
    public static final int DEMO_GAME = 2;
   
    /*
    private View splash;
    private ProgressBar splashProgressBar;
    private TextView splashText;
    */
    
    private AbstractGame game = null;
    private KuiniView view;
    
    private boolean hasStarted = false;
    private volatile String winner = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.splash);
        
        /*
        splash = findViewById(R.id.splash);
        splashProgressBar = (ProgressBar)findViewById(R.id.splashProgressBar);
        splashText = (TextView)findViewById(R.id.splashText);
        
        splashProgressBar.setVisibility(View.VISIBLE);
        splashText.setText(R.string.splash_before);
        */
        
        /*
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels; 
        */
        
        Intent intent = getIntent();
        
        switch (intent.getIntExtra("game", HOST_GAME)) {
        case HOST_GAME:
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE));
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            int playersN = 1 + Integer.decode(preferences.getString("oppNumber", "1"));
            float gameSpeed = preferences.getBoolean("fasterGame", false) ? 2.5f : 1.0f;
            boolean healAnts = preferences.getBoolean("healAnts", true);
            game = new HostGame(this, adapter, playersN, gameSpeed, healAnts);
            break;
        case JOIN_GAME:
            BluetoothDevice device = intent.getParcelableExtra("device");
            game = new JoinGame(this, device);
            break;
        case DEMO_GAME:
            game = new DemoGame(this);
            break;
        }

        view = new KuiniView(this);
        view.setCommandProxy(game);
        game.importPlayerNameFromContext(this);
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
                hasStarted = true;
                view.setPlayerId(playerId);
                setContentView(view);
            }
        });
    }

    @Override
    public void gameFinished() {
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                if (KuiniActivity.this.isFinishing()) return;
                
                AlertDialog.Builder builder = new AlertDialog.Builder(KuiniActivity.this);

                if (!hasStarted)
                    builder.setMessage(R.string.dialog_not_started);
                else if (winner == null)
                    builder.setMessage(R.string.dialog_fail);
                else
                    builder.setMessage(winner + " " + getString(R.string.dialog_winner_2));
                
                builder.setPositiveButton(R.string.dialog_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        KuiniActivity.this.finish();
                    }
                });
                
                builder.show();
            }
        });
    }

    @Override
    public void stateChanged(final IState state) {
        if (state.isGameEnded())
            winner = state.getWinnerName();
        view.stateChanged(state);
    }
   
}