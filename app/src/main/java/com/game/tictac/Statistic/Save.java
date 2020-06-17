package com.game.tictac.Statistic;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.game.tictac.startactivity.StartActivity;

public class Save {
    private StartActivity activity;
    private Statistic statistic;


    public Save(StartActivity activity, Statistic statistic){
        this.activity = activity;
        this.statistic = statistic;
    }

    public void saveStatistic(){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        myEditor.putInt("FirstPlayerWon", statistic.getFirstPlayerWon());
        myEditor.putInt("SecondPlayerWon", statistic.getSecondPlayerWon());
        myEditor.putInt("easyBot", statistic.getEasyBotWon());
        myEditor.putInt("mediumBot", statistic.getMediumBotWon());
        myEditor.putInt("hardBot", statistic.getHardBotWon());

        myEditor.putInt("botGames", statistic.getBotGames());
        myEditor.putInt("offlineGames", statistic.getOfflineGames());
        myEditor.putInt("onlineGames", statistic.getOnlineGames());
        myEditor.putInt("tieCount", statistic.getTie());

        myEditor.apply();
    }
}
