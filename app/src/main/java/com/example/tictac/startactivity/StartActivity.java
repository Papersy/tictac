package com.example.tictac.startactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tictac.R;
import com.example.tictac.Retrofit.NetworkService;
import com.example.tictac.gametypeactivity.GameTypeActivity;
import com.example.tictac.mainactivity.MainActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new ButtonInitStart(this).Listeners();


        //registration();

//        MobileAds.initialize(this, initializationStatus -> {});
//        mAdView = findViewById(R.id.adView);
//        mAdView.setAdListener(new AdScene());

    }




    @Override
    public void onBackPressed(){
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();

            Intent intent = new Intent(this, GameTypeActivity.class);
            startActivity(intent);
            finish();

            return;
        }
        else{
            backToast = Toast.makeText(getBaseContext(), "This game will be destroyed", Toast.LENGTH_SHORT);

            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private class AdScene extends AdListener {
        AdRequest adRequest;
        AdScene() {
            adRequest = new AdRequest.Builder().addTestDevice("ca-app-pub-3940256099942544/6300978111").build();
            mAdView.loadAd(adRequest);
        }
        @Override
        public void onAdLoaded() { }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            mAdView.loadAd(adRequest);
        }

        @Override
        public void onAdOpened() { }

        @Override
        public void onAdClicked() { }

        @Override
        public void onAdLeftApplication() { }

        @Override
        public void onAdClosed() {
            mAdView.loadAd(adRequest);
        }

    }
}
