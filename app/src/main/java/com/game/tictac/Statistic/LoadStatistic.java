package com.game.tictac.Statistic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class LoadStatistic {
    private Context activity;
    private Statistic statistic;

    public LoadStatistic(Context activity) {
        this.activity = activity;

        statistic = new Statistic();
    }

    public Statistic loadSaves(){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

        statistic.setFirstPlayerWon(myPreferences.getInt("FirstPlayerWon", 0));
        statistic.setSecondPlayerWon(myPreferences.getInt("SecondPlayerWon", 0));
        statistic.addEasyBotWon(myPreferences.getInt("easyBot", 0));
        statistic.addMediumBotWon(myPreferences.getInt("mediumBot", 0));
        statistic.addHardBotWon(myPreferences.getInt("hardBot", 0));
        statistic.setTie(myPreferences.getInt("tieCount", 0));
        statistic.setBotGames(myPreferences.getInt("botGames", 0));
        statistic.setOfflineGames(myPreferences.getInt("offlineGames", 0));
        statistic.setOnlineGames(myPreferences.getInt("onlineGames", 0));

        return statistic;
    }
}
