package com.example.tictac.settingsActivity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.tictac.R;

import org.w3c.dom.Text;

public class ButtonInitSettings {
    private SettingsActivity activity;
    private boolean isMusic, isSound;
    private SeekBar settingsSeekBar;
    private float musicTemp;
    private Settings settings;

    public ButtonInitSettings(SettingsActivity activity) {
        this.activity = activity;

        settingsSeekBar = activity.findViewById(R.id.settingsSeekBar);

        settings = new Settings(activity);
        isMusic = settings.isMusic();
        isSound = settings.isSound();
        musicTemp = settings.getMusicTemp();

        settingsSeekBar.setProgress((int)(musicTemp*10));

        musicImage();
        soundImage();
    }

    void Listeners(){
        activity.findViewById(R.id.imgMusic).setOnClickListener(v->{
            if(isMusic){
                musicTemp = 0.0f;
                settingsSeekBar.setProgress(0);
            }
            else{
                musicTemp = 0.5f;
                settingsSeekBar.setProgress(5);
            }

            isMusic = !isMusic;
            musicImage();

            settings.setMusic(isMusic);
            settings.setMusicTemp(musicTemp);
            settings.saveSaves();
        });

        activity.findViewById(R.id.imgSound).setOnClickListener(v->{
            isSound = !isSound;
            soundImage();

            settings.setSound(isSound);
            settings.saveSaves();
        });

        settingsSeekBar.setOnSeekBarChangeListener(settingsSeekListener);
    }

    private SeekBar.OnSeekBarChangeListener settingsSeekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            musicImage();

            musicTemp = (float)seekBar.getProgress()/10f;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if(musicTemp == 0.0f) {
                activity.findViewById(R.id.imgMusic).setBackgroundResource(R.drawable.ic_volume_off);
                isMusic = false;
            }
            else{
                activity.findViewById(R.id.imgMusic).setBackgroundResource(R.drawable.ic_volume_on);
                isMusic = true;
            }
            settings.setSound(isSound);
            settings.setMusic(isMusic);
            settings.setMusicTemp(musicTemp);
            settings.saveSaves();
        }
    };

    void musicImage(){
        if(!isMusic || musicTemp == 0.0f)
            activity.findViewById(R.id.imgMusic).setBackgroundResource(R.drawable.ic_volume_off);
        else
            activity.findViewById(R.id.imgMusic).setBackgroundResource(R.drawable.ic_volume_on);
    }
    void soundImage(){
        if(!isSound)
            activity.findViewById(R.id.imgSound).setBackgroundResource(R.drawable.ic_volume_off);
        else
            activity.findViewById(R.id.imgSound).setBackgroundResource(R.drawable.ic_volume_on);
    }


}
