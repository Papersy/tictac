package com.game.tictac.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.game.tictac.GooglePlayServices;
import com.game.tictac.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.tasks.Task;

import java.util.Locale;


public class MainActivity extends AppCompatActivity{
    private long backPressedTime;
    private Toast backToast;
    private Boolean isMusic;
    //private static final int RC_SIGN_IN = 9001;
    //private GooglePlayServices googlePlayServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        language();
        setContentView(R.layout.activity_main);


        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        new ButtonInitMain(this).Listeners();
//        googlePlayServices = new GooglePlayServices(this);
//        googlePlayServices.start();
    }

    void language(){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String languageToLoad = myPreferences.getString("language", "null");

        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getApplicationContext().getResources().getDisplayMetrics());
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            Task<GoogleSignInAccount> task =
//                    GoogleSignIn.getSignedInAccountFromIntent(data);
//            assert result != null;
//            if (result.isSuccess()) {
//                // The signed in account is stored in the result.
//                GoogleSignInAccount signedInAccount = result.getSignInAccount();
//                GoogleSignInAccount acct = task.getResult();
//            } else {
//                String message = result.getStatus().getStatusMessage();
//                if (message == null || message.isEmpty()) {
//                    message = "Please, try later!";
//                }
//                new AlertDialog.Builder(this).setMessage(message)
//                        .setNeutralButton(android.R.string.ok, null).show();
//            }
//        }
//    }

    @Override
    public void onBackPressed(){
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();

            super.onBackPressed();

            return;
        }
        else{
            backToast = Toast.makeText(getBaseContext(), getString(R.string.press), Toast.LENGTH_SHORT);

            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onResume(){
        super.onResume();
//        try {
//            googlePlayServices.signInSilently();
//        }catch (Exception ignored){ }

    }
}

