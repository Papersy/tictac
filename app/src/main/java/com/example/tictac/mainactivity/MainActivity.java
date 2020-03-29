package com.example.tictac.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.tictac.R;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private int soundLength;
    private long backPressedTime;
    private Toast backToast;
    private Boolean isMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.out.println("Start new Act");
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        isMusic = myPreferences.getBoolean("isMusic", true);
        musicOn();

        new ButtonInitMain(this).Listeners();
    }

    void musicOn(){
        if(isMusic) {
            SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            float temp = myPreferences.getFloat("musicTemp", 0.5f);
            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
            mediaPlayer.setVolume(temp, temp);
            mediaPlayer.start();
        }
    }

    @Override
    public void onBackPressed(){
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();

            super.onBackPressed();

            return;
        }
        else{
            backToast = Toast.makeText(getBaseContext(), "Press one more time to exit", Toast.LENGTH_SHORT);

            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isMusic)
            mediaPlayer.stop();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(isMusic) {
            mediaPlayer.pause();
            soundLength = mediaPlayer.getCurrentPosition();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(isMusic) {
            mediaPlayer.seekTo(soundLength);
            mediaPlayer.start();
        }
    }
}

