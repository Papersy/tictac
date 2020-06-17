package com.game.tictac.settingsActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.SeekBar;

import com.game.tictac.R;
import com.game.tictac.mainactivity.MainActivity;

import java.util.Locale;
import java.util.Objects;

class ButtonInitSettings {
    private MediaPlayer mediaPlayer;
    private SettingsActivity activity;
    private boolean isSound;
    //private SeekBar settingsSeekBar;
    //private float musicTemp;
    private Settings settings;

    ButtonInitSettings(SettingsActivity activity) {
        this.activity = activity;

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        isSound = myPreferences.getBoolean("isSound", true);

        mediaPlayer = MediaPlayer.create(activity, R.raw.click_sound);
        mediaPlayer.setVolume(0.15f, 0.15f);

        //settingsSeekBar = activity.findViewById(R.id.settingsSeekBar);

        settings = new Settings(activity);
        isSound = settings.isSound();
        //musicTemp = settings.getMusicTemp();

        //settingsSeekBar.setProgress((int)(musicTemp*10));

        soundImage();
    }

    void Listeners(){
        //activity.findViewById(R.id.imgMusic).setOnClickListener(v-> settings.saveSaves());

        activity.findViewById(R.id.imgSound).setOnClickListener(v->{
            if (isSound)
                mediaPlayer.start();
            isSound = !isSound;
            soundImage();

            settings.setSound(isSound);
            settings.saveSaves();
        });

        activity.findViewById(R.id.langRU).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();
            language("ru");
        });

        activity.findViewById(R.id.langEN).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();
            language("en");
        });

        activity.findViewById(R.id.btnSettingsBack).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();
            Intent intent = new Intent(activity, MainActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });

        //settingsSeekBar.setOnSeekBarChangeListener(settingsSeekListener);
    }

    private void language(String lang){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        myEditor.putString("language", lang);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(!Objects.equals(myPreferences.getString("language", "en"), lang)){
                Locale locale = new Locale(lang);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                activity.getBaseContext().getResources().updateConfiguration(config,
                        activity.getBaseContext().getResources().getDisplayMetrics());
                activity.finish();
                Intent refresh = new Intent(activity, SettingsActivity.class);
                activity.startActivity(refresh);
                activity.recreate();
            }
        }
        else{
            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            activity.getBaseContext().getResources().updateConfiguration(config,
                    activity.getBaseContext().getResources().getDisplayMetrics());
            activity.finish();
            Intent refresh = new Intent(activity, SettingsActivity.class);
            activity.startActivity(refresh);
            activity.recreate();
        }

        myEditor.apply();
    }

//    private SeekBar.OnSeekBarChangeListener settingsSeekListener = new SeekBar.OnSeekBarChangeListener() {
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            musicImage();
//
//            musicTemp = (float)seekBar.getProgress()/10f;
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) { }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            if(musicTemp == 0.0f) {
//                activity.findViewById(R.id.imgMusic).setBackgroundResource(R.drawable.ic_music_note_off);
//                isMusic = false;
//            }
//            else{
//                activity.findViewById(R.id.imgMusic).setBackgroundResource(R.drawable.ic_music_note_on);
//                isMusic = true;
//            }
//            settings.setSound(isSound);
//            settings.setMusic(isMusic);
//            settings.setMusicTemp(musicTemp);
//            settings.saveSaves();
//        }
//    };

//    private void musicImage(){
//        if(!isMusic || musicTemp == 0.0f)
//            activity.findViewById(R.id.imgMusic).setBackgroundResource(R.drawable.ic_music_note_off);
//        else
//            activity.findViewById(R.id.imgMusic).setBackgroundResource(R.drawable.ic_music_note_on);
//    }

    private void soundImage(){
        if(!isSound)
            activity.findViewById(R.id.imgSound).setBackgroundResource(R.drawable.ic_volume_off);
        else
            activity.findViewById(R.id.imgSound).setBackgroundResource(R.drawable.ic_volume_on);
    }
}
