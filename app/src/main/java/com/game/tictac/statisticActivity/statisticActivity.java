package com.game.tictac.statisticActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.game.tictac.Statistic.LoadStatistic;
import com.game.tictac.R;
import com.game.tictac.Statistic.Statistic;
import com.game.tictac.mainactivity.MainActivity;

public class statisticActivity extends AppCompatActivity {
    private Statistic statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);


        statistic = new LoadStatistic(this).loadSaves();
        ((TextView)findViewById(R.id.txtFirstPlayerStat)).setText(String.format(getString(R.string.formatStrStr), getString(R.string.firstPlayerVictories), String.valueOf(statistic.getFirstPlayerWon())));
        ((TextView)findViewById(R.id.txtSecondPlayerStat)).setText(String.format(getString(R.string.formatStrStr), getString(R.string.secondPlayerVictories), String.valueOf(statistic.getSecondPlayerWon())));
        ((TextView)findViewById(R.id.txtEasyBotWon)).setText(String.format(getString(R.string.formatStrStr), getString(R.string.easyEliminated), String.valueOf(statistic.getEasyBotWon())));
        ((TextView)findViewById(R.id.txtMediumBotWon)).setText(String.format(getString(R.string.formatStrStr), getString(R.string.mediumEliminated), String.valueOf(statistic.getMediumBotWon())));
        ((TextView)findViewById(R.id.txtHardBotWon)).setText(String.format(getString(R.string.formatStrStr), getString(R.string.hardEliminated), String.valueOf(statistic.getHardBotWon())));
        ((TextView)findViewById(R.id.txtBotGames)).setText(String.format(getString(R.string.formatStrStr), getString(R.string.botGames), String.valueOf(statistic.getBotGames())));
        ((TextView)findViewById(R.id.txtTieCount)).setText(String.format(getString(R.string.formatStrStr), getString(R.string.tieCount), String.valueOf(statistic.getTie())));

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
