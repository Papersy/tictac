package com.game.tictac.settingsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import com.game.tictac.R;
import com.game.tictac.mainactivity.MainActivity;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        language();
        setContentView(R.layout.activity_settings);

        ((ImageView)findViewById(R.id.btnSettingsBack)).setColorFilter(Color.WHITE);

        new ButtonInitSettings(this).Listeners();
    }

    void language(){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String languageToLoad = myPreferences.getString("language", "null");

        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
