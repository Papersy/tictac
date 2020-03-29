package com.example.tictac.statisticActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tictac.Statistic.LoadStatistic;
import com.example.tictac.R;
import com.example.tictac.Statistic.Statistic;
import com.example.tictac.mainactivity.MainActivity;

public class statisticActivity extends AppCompatActivity {
    private Statistic statistic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);


        statistic = new LoadStatistic(this).loadSaves();
        ((TextView)findViewById(R.id.txtFirstPlayerStat)).setText("First\n" + String.valueOf(statistic.getFirstPlayerWon()) + " victories");
        ((TextView)findViewById(R.id.txtSecondPlayerStat)).setText("Second\n" + String.valueOf(statistic.getSecondPlayerWon()) + " victories");
        ((TextView)findViewById(R.id.txtEasyBotWon)).setText("Easy\n" + String.valueOf(statistic.getEasyBotWon()) + " victories");
        ((TextView)findViewById(R.id.txtMediumBotWon)).setText("Medium\n" + String.valueOf(statistic.getMediumBotWon()) + " victories");
        ((TextView)findViewById(R.id.txtHardBotWon)).setText("Hard\n" + String.valueOf(statistic.getHardBotWon()) + " victories");
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
