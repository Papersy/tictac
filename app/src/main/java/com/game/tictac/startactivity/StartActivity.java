package com.game.tictac.startactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.game.tictac.R;
import com.game.tictac.gametypeactivity.GameTypeActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class StartActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    private AdView mAdView;
    private ConnectivityManager cm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ((ImageView)findViewById(R.id.imageBack)).setColorFilter(Color.WHITE);

        new ButtonInitStart(this).Listeners();

        //registration();

        initAds();
    }

    void initAds(){
        cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE); //check internet connection
        if(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
        {
            try{
                runOnUiThread(this::showAds);
            }
            catch (Exception ignored){}
        }
    }

    void showAds(){
        MobileAds.initialize(StartActivity.this, initializationStatus -> {});
        mAdView = findViewById(R.id.adView);
        mAdView.setAdListener(new AdScene());
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
            adRequest = new AdRequest.Builder().addTestDevice("ca-app-pub-3010679320408675/1376472689").build();
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