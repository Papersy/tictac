package com.example.tictac.settingsActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
    private Context activity;
    private boolean isMusic = true;
    private boolean isSound = true;
    private float musicTemp = 0.0f;

    Settings(Context activity){
        this.activity = activity;

        loadSaves();
    }

    boolean isMusic() {
        return isMusic;
    }

    public void setMusic(boolean music) {
        isMusic = music;
    }

    boolean isSound() {
        return isSound;
    }

    public void setSound(boolean sound) {
        isSound = sound;
    }

    public float getMusicTemp() {
        return musicTemp;
    }

    public void setMusicTemp(float musicTemp) {
        this.musicTemp = musicTemp;
    }

    void loadSaves(){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

        isMusic = myPreferences.getBoolean("isMusic", true);
        isSound = myPreferences.getBoolean("isSound", true);
        musicTemp = myPreferences.getFloat("musicTemp", 0.5f);
    }

    void saveSaves(){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        myEditor.putBoolean("isMusic", isMusic);
        myEditor.putBoolean("isSound", isSound);
        myEditor.putFloat("musicTemp", musicTemp);

        myEditor.apply();
    }
}
