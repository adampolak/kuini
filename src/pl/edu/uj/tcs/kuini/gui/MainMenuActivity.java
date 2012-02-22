package pl.edu.uj.tcs.kuini.gui;

import pl.edu.uj.tcs.kuini.R;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainMenuActivity extends ListActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, 
                android.R.layout.simple_list_item_1, 
                getResources().getStringArray(R.array.menu_items)));
    }
     
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        Intent intent;
        
        switch (position) {
        case 4:
            intent = new Intent(this, KuiniActivity.class);
            intent.putExtra("game", KuiniActivity.DEMO_GAME);
            startActivity(intent);
            break;
        case 0:
            intent = new Intent(this, NewGameActivity.class);
            startActivity(intent);
            break;
        case 1:
            intent = new Intent(this, SelectHostActivity.class);
            startActivity(intent);
            break;
        case 2:
            startActivity(new Intent(this, SettingsActivity.class));
            break;
        case 3:
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
            /* This should never happen */
            Toast.makeText(this, "Not implemented yet.", Toast.LENGTH_SHORT).show();
        }
    
    }
    
}
