package pl.edu.uj.tcs.kuini.gui;

import pl.edu.uj.tcs.kuini.R;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;

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
        
    }
}
