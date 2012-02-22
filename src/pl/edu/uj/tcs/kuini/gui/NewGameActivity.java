package pl.edu.uj.tcs.kuini.gui;

import pl.edu.uj.tcs.kuini.R;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;
import android.widget.Toast;

public class NewGameActivity extends PreferenceActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.preferences);
        
        PreferenceGroup group = getPreferenceScreen();
        group.removePreference(group.findPreference("general"));
        
        findPreference("start").setOnPreferenceClickListener(new OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(NewGameActivity.this, KuiniActivity.class);
                intent.putExtra("game", KuiniActivity.HOST_GAME);
                startActivity(intent);
                return true;
            }
        });
        
        ensureBtEnabled();
        
    }

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
    
}
