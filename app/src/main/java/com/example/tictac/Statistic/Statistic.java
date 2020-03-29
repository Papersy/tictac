package com.example.tictac.Statistic;

import java.sql.Time;

public class Statistic {
    private int firstPlayerWon = 0;
    private int secondPlayerWon = 0;
    private int easyBotWon = 0;
    private int mediumBotWon = 0;
    private int hardBotWon = 0;

    private int games = 0;
    private long timeInGame = 0;

    public int getFirstPlayerWon() {
        return firstPlayerWon;
    }

    public int getSecondPlayerWon() {
        return secondPlayerWon;
    }

    public void setFirstPlayerWon(int firstPlayerWon) {
        this.firstPlayerWon = firstPlayerWon;
    }

    public void setSecondPlayerWon(int secondPlayerWon) {
        this.secondPlayerWon = secondPlayerWon;
    }

    public void addFirstPlayerWon(int firstPlayerWon) {
        this.firstPlayerWon += firstPlayerWon;
    }

    public void addSecondPlayerWon(int secondPlayerWon) {
        this.secondPlayerWon += secondPlayerWon;
    }


    public int getEasyBotWon() {
        return easyBotWon;
    }

    public void addEasyBotWon(int easyBotWon) {
        this.easyBotWon += easyBotWon;
    }

    public int getMediumBotWon() {
        return mediumBotWon;
    }

    public void addMediumBotWon(int mediumBotWon) {
        this.mediumBotWon += mediumBotWon;
    }

    public int getHardBotWon() {
        return hardBotWon;
    }

    public void addHardBotWon(int hardBotWon) {
        this.hardBotWon += hardBotWon;
    }
}
