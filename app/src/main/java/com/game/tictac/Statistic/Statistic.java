package com.game.tictac.Statistic;

public class Statistic {
    private int firstPlayerWon = 0;
    private int secondPlayerWon = 0;
    private int easyBotWon = 0;
    private int mediumBotWon = 0;
    private int hardBotWon = 0;

    private int botGames = 0;
    private int offlineGames = 0;
    private int onlineGames = 0;
    private int tieCount = 0;

    //private long timeInGame = 0;

    public int getFirstPlayerWon() {
        return firstPlayerWon;
    }

    public int getSecondPlayerWon() {
        return secondPlayerWon;
    }

    void setFirstPlayerWon(int firstPlayerWon) {
        this.firstPlayerWon = firstPlayerWon;
    }

    void setSecondPlayerWon(int secondPlayerWon) {
        this.secondPlayerWon = secondPlayerWon;
    }

    public void addFirstPlayerWon(int firstPlayerWon) {
        this.firstPlayerWon += firstPlayerWon;
        offlineGames++;
    }

    public void addSecondPlayerWon(int secondPlayerWon) {
        this.secondPlayerWon += secondPlayerWon;
        offlineGames++;
    }


    public int getEasyBotWon() {
        return easyBotWon;
    }

    public void addEasyBotWon(int easyBotWon) {
        this.easyBotWon += easyBotWon;
        botGames++;
    }

    public int getMediumBotWon() {
        return mediumBotWon;
    }

    public void addMediumBotWon(int mediumBotWon) {
        this.mediumBotWon += mediumBotWon;
        botGames++;
    }

    public int getHardBotWon() {
        return hardBotWon;
    }

    public void addHardBotWon(int hardBotWon) {
        this.hardBotWon += hardBotWon;
        botGames++;
    }

    public void haveTie(){
        tieCount++;
    }

    void setTie(int index){
        tieCount = index;
    }

    public int getTie(){
        return tieCount;
    }

    public int getBotGames() {
        return botGames;
    }

    void setBotGames(int botGames) {
        this.botGames = botGames;
    }

    int getOfflineGames() {
        return offlineGames;
    }

    void setOfflineGames(int offlineGames) {
        this.offlineGames = offlineGames;
    }

    int getOnlineGames() {
        return onlineGames;
    }

    void setOnlineGames(int onlineGames) {
        this.onlineGames = onlineGames;
    }
}
