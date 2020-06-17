package com.game.tictac.mainactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;

import com.game.tictac.R;
import com.game.tictac.aboutActivity.AboutActivity;
import com.game.tictac.gametypeactivity.GameTypeActivity;
import com.game.tictac.settingsActivity.SettingsActivity;
import com.game.tictac.statisticActivity.statisticActivity;

class ButtonInitMain {
    private boolean isSound = true;
    private MainActivity activity;
    private MediaPlayer mediaPlayer;

    ButtonInitMain(MainActivity activity) {
        this.activity = activity;

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        isSound = myPreferences.getBoolean("isSound", true);

        mediaPlayer = MediaPlayer.create(activity, R.raw.click_sound);
        mediaPlayer.setVolume(0.15f, 0.15f);
    }

    void Listeners(){
        activity.findViewById(R.id.btnStart).setOnClickListener(v-> {
            if (isSound)
                mediaPlayer.start();

            btnHelp(GameTypeActivity.class);
//            Intent intent = new Intent(activity, GameTypeActivity.class);
//            activity.startActivity(intent);
//            activity.finish();
        });

        activity.findViewById(R.id.btnAbout).setOnClickListener(v->{
            if (isSound)
                mediaPlayer.start();

            btnHelp(AboutActivity.class);
//            Intent intent = new Intent(activity, AboutActivity.class);
//            activity.startActivity(intent);
//            activity.finish();
        });

        activity.findViewById(R.id.btnStatistic).setOnClickListener(v-> {
            if (isSound)
                mediaPlayer.start();

            btnHelp(statisticActivity.class);
//            Intent intent = new Intent(activity, statisticActivity.class);
//            activity.startActivity(intent);
//            activity.finish();
        });

        activity.findViewById(R.id.btnSettings).setOnClickListener(v->{
            if (isSound)
                mediaPlayer.start();


            btnHelp(SettingsActivity.class);
//            Intent intent = new Intent(activity, SettingsActivity.class);
//            activity.startActivity(intent);
//            activity.finish();
        });

        activity.findViewById(R.id.btnExit).setOnClickListener(v-> {
            if (isSound)
                mediaPlayer.start();

            activity.finishAffinity();
        });
    }

    private void btnHelp(Class Class){
        if (isSound)
            mediaPlayer.start();

        Intent intent = new Intent(activity, Class);
        activity.startActivity(intent);
        activity.finish();
    }
}
