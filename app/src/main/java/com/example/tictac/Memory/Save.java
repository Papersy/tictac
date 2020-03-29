package com.example.tictac.Memory;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.tictac.Statistic.Statistic;
import com.example.tictac.startactivity.StartActivity;

public class Save {
    private StartActivity activity;
    private Statistic statistic;

    public Save(StartActivity activity) {
        this.activity = activity;
        this.statistic = statistic;
    }

    public void saveStatistic(){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        myEditor.putInt("FirstPlayerWon", statistic.getFirstPlayerWon());
        myEditor.putInt("SecondPlayerWon", statistic.getSecondPlayerWon());

        myEditor.apply();
    }
}
