package com.example.tictac.mainactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.tictac.R;
import com.example.tictac.aboutActivity.AboutActivity;
import com.example.tictac.gametypeactivity.GameTypeActivity;
import com.example.tictac.settingsActivity.SettingsActivity;
import com.example.tictac.statisticActivity.statisticActivity;

class ButtonInitMain {
    private MainActivity activity;

    ButtonInitMain(MainActivity activity) {
        this.activity = activity;
    }

    void Listeners(){
        activity.findViewById(R.id.btnStart).setOnClickListener(v-> {
            Intent intent = new Intent(activity, GameTypeActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });

        activity.findViewById(R.id.btnAbout).setOnClickListener(v->{
            Intent intent = new Intent(activity, AboutActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });

        activity.findViewById(R.id.btnStatistic).setOnClickListener(v-> {
            Intent intent = new Intent(activity, statisticActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });

        activity.findViewById(R.id.btnSettings).setOnClickListener(v->{
            Intent intent = new Intent(activity, SettingsActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });




        activity.findViewById(R.id.btnExit).setOnClickListener(v-> activity.finishAffinity());
    }
}
