package com.game.tictac.settingsActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Settings {
    private Context activity;
    private boolean isMusic = true;
    private boolean isSound = true;
    private float musicTemp = 0.0f;
    private int musicPosition = 0;

    public Settings(Context activity){
        this.activity = activity;

        loadSaves();
    }

    public boolean isMusic() {
        return isMusic;
    }

    void setMusic(boolean music) {
        isMusic = music;
        saveSaves();
    }

    public boolean isSound() {
        return isSound;
    }

    void setSound(boolean sound) {
        isSound = sound;
        saveSaves();
    }

    public float getMusicTemp() {
        return musicTemp;
    }

    void setMusicTemp(float musicTemp) {
        this.musicTemp = musicTemp;
        saveSaves();
    }

    public int getMusicPosition() {
        return musicPosition;
    }

    public void setMusicPosition(int musicPosition) {
        this.musicPosition = musicPosition;
        saveSaves();
    }

    private void loadSaves(){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

        isMusic = myPreferences.getBoolean("isMusic", true);
        isSound = myPreferences.getBoolean("isSound", true);
        musicTemp = myPreferences.getFloat("musicTemp", 0.5f);
        musicPosition = myPreferences.getInt("musicPosition", 0);
    }

    void saveSaves(){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        myEditor.putBoolean("isMusic", isMusic);
        myEditor.putBoolean("isSound", isSound);
        myEditor.putFloat("musicTemp", musicTemp);
        myEditor.putInt("musicPosition", musicPosition);

        myEditor.apply();
    }
}
