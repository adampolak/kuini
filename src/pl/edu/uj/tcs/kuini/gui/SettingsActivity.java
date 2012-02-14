package pl.edu.uj.tcs.kuini.gui;

import pl.edu.uj.tcs.kuini.R;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceGroup;

public class SettingsActivity extends PreferenceActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        PreferenceGroup group = getPreferenceScreen();
        group.removePreference(group.findPreference("game"));
    }
}
