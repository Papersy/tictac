package com.game.tictac.aboutActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.game.tictac.R;
import com.game.tictac.mainactivity.MainActivity;

public class AboutActivity extends AppCompatActivity {
    private boolean isSound = true;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isSound = myPreferences.getBoolean("isSound", true);

        mediaPlayer = MediaPlayer.create(this, R.raw.click_sound);
        mediaPlayer.setVolume(0.15f, 0.15f);

        ((ImageView)findViewById(R.id.btnAboutBack)).setColorFilter(Color.WHITE);

        findViewById(R.id.btnAboutBack).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed(){
        if (isSound)
            mediaPlayer.start();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
