package pl.edu.uj.tcs.kuini.gui;

import android.R;
import android.app.ListActivity;
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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, 
                R.layout.simple_list_item_1, 
                MENU_ITEMS));
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
            intent = new Intent(this, KuiniActivity.class);
            intent.putExtra("game", KuiniActivity.HOST_GAME);
            startActivity(intent);
            break;
        case 2:
            intent = new Intent(this, SelectHostActivity.class);
            startActivity(intent);
            break;
        case 3:
            startActivity(new Intent(this, SettingsActivity.class));
            break;
        default:
            Toast.makeText(this, "Not implemented yet :-(", Toast.LENGTH_SHORT).show();
        }
    
    }
    
}
