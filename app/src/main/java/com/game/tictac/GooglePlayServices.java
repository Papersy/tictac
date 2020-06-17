package com.game.tictac;

import android.content.Intent;
import android.view.Gravity;

import com.game.tictac.Statistic.LoadStatistic;
import com.game.tictac.Statistic.Statistic;
import com.game.tictac.mainactivity.MainActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesClient;

import java.util.Objects;

public class GooglePlayServices {

    private MainActivity activity;
    private Statistic statistic;
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    private static final int RC_LEADERBOARD_UI = 9004;
    private GamesClient gamesClient;

    public GooglePlayServices(MainActivity activity){
        this.activity = activity;

        statistic = new LoadStatistic(activity).loadSaves();

//        activity.findViewById(R.id.signIn).setOnClickListener(v->{
//            startSignInIntent();
//            gamesClient = Games.getGamesClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity)));
//            gamesClient.setViewForPopups(activity.findViewById(android.R.id.content));
//        });
//
//        activity.findViewById(R.id.achievements).setOnClickListener(v->{
//            try{
//                showAchievements();
//            } catch (Exception e){ }
//        });
//
//        activity.findViewById(R.id.leaderBoard).setOnClickListener(v->{
//            try{
//                showLeaderboard();
//            } catch (Exception e){ }
//        });
    }

    public void start(){
        try{
            signInSilently();
            gamesClient = Games.getGamesClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity)));
            gamesClient.setViewForPopups(activity.findViewById(android.R.id.content));
        }
        catch (Exception e){ }
    }

    public void addToLeadreBoard(){
        int temp = statistic.getBotGames();
        Games.getLeaderboardsClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity)))
                .submitScore(activity.getString(R.string.leaderboard_top), temp);
    }

    public void unlockAchievement(){


        if(statistic.getBotGames() >= 500)
            Games.getAchievementsClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity)))
                            .unlock(activity.getString(R.string.achievement_win_500_bots));
        else if(statistic.getBotGames() >= 250)
            Games.getAchievementsClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity)))
                    .unlock(activity.getString(R.string.achievement_win_250_bots));
        else if(statistic.getBotGames() >= 100)
            Games.getAchievementsClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity)))
                    .unlock(activity.getString(R.string.achievement_win_100_bots));
        else if(statistic.getBotGames() >= 50)
            Games.getAchievementsClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity)))
                    .unlock(activity.getString(R.string.achievement_win_50_bots));
        else if(statistic.getBotGames() >= 10)
            Games.getAchievementsClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity)))
                    .unlock(activity.getString(R.string.achievement_win_10_bots));


//            Games.getAchievementsClient(this, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(this)));
//                    .unlock(getString(R.string.achievement_first_regular));
            gamesClient = Games.getGamesClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity.getBaseContext()))); // i changed here to base context
            gamesClient.setViewForPopups(activity.findViewById(android.R.id.content));
            gamesClient.setGravityForPopups(Gravity.TOP | Gravity.CENTER_HORIZONTAL);

    }




    private void showLeaderboard() {
        Games.getLeaderboardsClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity)))
                .getLeaderboardIntent(activity.getString(R.string.leaderboard_top))
                .addOnSuccessListener(intent -> activity.startActivityForResult(intent, RC_LEADERBOARD_UI));
    }

    private void showAchievements() {
        Games.getAchievementsClient(activity, Objects.requireNonNull(GoogleSignIn.getLastSignedInAccount(activity)))
                .getAchievementsIntent()
                .addOnSuccessListener(intent -> activity.startActivityForResult(intent, RC_ACHIEVEMENT_UI));
    }

    private void startSignInIntent() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(activity,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        activity.startActivityForResult(intent, RC_SIGN_IN);
    }

    public void signInSilently() {
        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(activity);
        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            GoogleSignInAccount signedInAccount = account;
        } else {
            GoogleSignInClient signInClient = GoogleSignIn.getClient(activity, signInOptions);
            signInClient
                    .silentSignIn()
                    .addOnCompleteListener(
                            activity,
                            task -> {
                                if (task.isSuccessful()) {
                                    GoogleSignInAccount signedInAccount = task.getResult();
                                } else {
                                }
                            });
        }
    }
}
