package com.acidcarpet.balance.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.acidcarpet.balance.R;
import com.acidcarpet.balance.main.MainActivity;
import com.acidcarpet.balance.statistics.StatisticActivity;
import com.acidcarpet.balance.tutorial.TutorialActivity;

public class SettingsActivity extends AppCompatActivity {

//    SwitchPreference consent;
//    ListPreference night_theme;

    private ImageButton cancel_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }

        cancel_button = (ImageButton) findViewById(R.id.settings_cancel_button);
        cancel_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cancel_click();
            }
        });
        //consent = (SwitchPreference) findViewById(R.id.consent_switch);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.settings_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.close_button:
//                startActivity(new Intent(this, MainActivity.class));
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }



    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
    public void cancel_click(){
        startActivity(new Intent(this, MainActivity.class));
    }
}