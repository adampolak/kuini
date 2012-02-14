package pl.edu.uj.tcs.kuini.gui;

import pl.edu.uj.tcs.kuini.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuActivity extends ListActivity {

    private static final String[] MENU_ITEMS = {
        "Start demo game",
        "Host new game",
        "Join existing game",
        "Settings",
        "About Kuini"
    };
    
    private void ensureBtEnabled() {
        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            Toast.makeText(this, R.string.error_no_bt, Toast.LENGTH_LONG).show();
            finish();
        }
        if (!btAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, 0);
        }
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, R.string.error_bt_disabled, Toast.LENGTH_LONG).show();
            finish();
        }
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, 
                android.R.layout.simple_list_item_1, 
                MENU_ITEMS));
        ensureBtEnabled();
    }
     
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent;
        
        switch (position) {
        case 0:
            intent = new Intent(this, KuiniActivity.class);
            intent.putExtra("game", KuiniActivity.DEMO_GAME);
            startActivity(intent);
            break;
        case 1:
            intent = new Intent(this, NewGameActivity.class);
            startActivity(intent);
            break;
        case 2:
            intent = new Intent(this, SelectHostActivity.class);
            startActivity(intent);
            break;
        case 3:
            startActivity(new Intent(this, SettingsActivity.class));
            break;
        case 4:
            (new AlertDialog.Builder(this))
                .setTitle(R.string.about_title)
                .setMessage(R.string.about)
                .setPositiveButton(R.string.about_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        dialog.cancel();
                    }
                })
                .show();
            break;
        default:
            Toast.makeText(this, "Not implemented yet.", Toast.LENGTH_SHORT).show();
        }
    
    }
    
}
