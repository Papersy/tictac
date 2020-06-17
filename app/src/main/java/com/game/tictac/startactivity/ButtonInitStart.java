package com.game.tictac.startactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.game.tictac.EnumPack.EnumHardLevel;
import com.game.tictac.EnumPack.EnumType;
import com.game.tictac.GameController;
import com.game.tictac.LevelSettings.LevelSettings;
import com.game.tictac.Retrofit.NetworkService;
import com.game.tictac.Statistic.LoadStatistic;
import com.game.tictac.R;
import com.game.tictac.Statistic.Save;
import com.game.tictac.Statistic.Statistic;
import com.game.tictac.gametypeactivity.GameTypeActivity;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ButtonInitStart {
    private MediaPlayer mediaPlayer;
    private Boolean isGo = true;
    private String isMove = null;
    private boolean isGame = true;
    private boolean isSound = true;
    private GameController gameController;
    private Statistic statistic;
    private StartActivity activity;
    private TextView textStep;
    private EnumHardLevel hardLevel;
    private int index = 0, arraySize = 3, parameter = 230;
    private ArrayList<DataCon> arrayList;
    private ArrayList<LinearLayout> linearList = new ArrayList<>();
    private ArrayList<ImageView> imageList = new ArrayList<>();
    private String nickName;

    private int enemyX = -1, enemyY = -1;
    private String gameID;

    ButtonInitStart(StartActivity activity) {
        this.activity = activity;

        gameController = new GameController();
        statistic = new LoadStatistic(activity).loadSaves();

        ((ImageView) activity.findViewById(R.id.imgRestartGame)).setColorFilter(Color.WHITE);
        textStep = activity.findViewById(R.id.textStep);


        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

        isSound = myPreferences.getBoolean("isSound", true);
        nickName = myPreferences.getString("userName", null);
        gameID = myPreferences.getString("gameId", null);

        mediaPlayer = MediaPlayer.create(activity, R.raw.click_sound);
        mediaPlayer.setVolume(0.15f, 0.15f);

        hardLevel = LevelSettings.getLevelHard();

        switch (LevelSettings.getLevelSize()){
            case SECOND:
                arraySize = 5;
                parameter = 120;
                break;
            case THIRD:
                arraySize = 7;
                parameter = 70;
                break;
        }

        if(LevelSettings.getLevelWhoPlayer() == EnumType.ONLINE) {
            textStep.setText(activity.getString(R.string.firstMove));
            matchmaking(nickName);

            Thread mainThread = new Thread(() -> {
                while (isGame) {
                    try {
                        Thread.sleep(1000);
                        getInfoByGameID();
                        activity.runOnUiThread(this::setEnemyArray);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            Thread getGameIdThread = new Thread(() -> {
                boolean isSearch = true;
                while (isSearch) {
                    try {
                        getInfoByName();
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (gameID != null) {
                        //setEnabled();
                        mainThread.start();
                        isSearch = false;
                    }
                }
            });

            getGameIdThread.start();
        }
        else if(LevelSettings.getLevelWhoPlayer() == EnumType.OFFLINE)
            textStep.setText(activity.getString(R.string.firstMove));
        else
            textStep.setText(activity.getString(R.string.playerMove));
    }

    void Listeners(){
        createMainBox();

        activity.findViewById(R.id.imgRestartGame).setOnClickListener(v-> {
            if (isSound)
                mediaPlayer.start();
            restartGame();
        });
        activity.findViewById(R.id.btnBackFromMainGame).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();
            Intent intent = new Intent(activity, GameTypeActivity.class);
            activity.startActivity(intent);
            activity.finish();
        });
    }

    private void createMainBox(){
        LinearLayout mainLinear = activity.findViewById(R.id.testLinear);
        mainLinear.setOrientation(LinearLayout.VERTICAL);
        arrayList = new ArrayList<>();

        for(int j = 0; j < arraySize; j++){
            LinearLayout linearLine = new LinearLayout(activity);
            linearLine.setOrientation(LinearLayout.HORIZONTAL);
            linearLine.setWeightSum(j + 1);

            for(int i = 0; i < arraySize; i++,index++){
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(10, 10, 10, 10);

                LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParam.setMargins(30, 30, 30, 30);
                layoutParam.height = parameter;
                layoutParam.width = parameter;

                LinearLayout linearBox = new LinearLayout(activity);
                ImageView imageView = new ImageView(activity);

                linearBox.setBackgroundResource(R.drawable.tie);

                linearBox.addView(imageView, layoutParam);
                linearBox.setId(index);
                linearLine.addView(linearBox, layoutParams);

                linearList.add(linearBox);
                imageList.add(imageView);
                //linearBox.setEnabled(false);

                arrayList.add(new DataCon(index, j, i));

            }
            mainLinear.addView(linearLine);
        }

        for (DataCon data: arrayList) {
            activity.findViewById(data.index).
                    setOnClickListener(v->{
                        System.out.println("X - " + data.x + "Y - " + data.y);
                        checkBox(imageList.get(data.index), data.x, data.y);
                    });
        }
    }

    void setEnabled(){
        for (LinearLayout linear: linearList) {
            linear.setEnabled(true);
        }
    }

    private void checkBox(ImageView imageView, int x, int y){
        if(LevelSettings.getLevelWhoPlayer() == EnumType.OFFLINE) {
            if (gameController.setArray(x, y, isGo) && isGame) {
                changeDeleteToCircle(imageView, activity.getString(R.string.firstMove), activity.getString(R.string.secondMove));

                victoryCheck(activity.getString(R.string.congratFirst), activity.getString(R.string.congratSecond));

                isGo = !isGo;
            }
        }
        else if(LevelSettings.getLevelWhoPlayer() == EnumType.BOT){
            if (isGo && gameController.setArray(x, y, isGo) && isGame) {
                changeDeleteToCircle(imageView, activity.getString(R.string.playerMove), activity.getString(R.string.botMove));

                victoryCheck(activity.getString(R.string.congratPlayer), activity.getString(R.string.congratBot));

                isGo = !isGo;

                botsStepTimer();
            }
        }
        else if(LevelSettings.getLevelWhoPlayer() == EnumType.ONLINE){
            if(isMove.equals(nickName)){
                if (gameController.setArray(x, y, 1) && isGame) {
                    coordinates(x,y);
                    if (isSound)
                        mediaPlayer.start();

                    int icon = R.drawable.ic_cancel;
                    textStep.setText(String.format(activity.getString(R.string.formatStrStr), activity.getString(R.string.moves), isMove));
                    imageView.setImageResource(icon);

                    if (gameController.arrayCheck()) {
                        isGame = false;
                        textStep.setText(activity.getString(R.string.uWon));
                    } else if (gameController.nooneWon() && !gameController.arrayCheck()) {
                        isGame = false;
                        textStep.setText(activity.getString(R.string.nooneWon));
                    }
                }
            }
        }
    }

    private void changeDeleteToCircle(ImageView imageView, String moveFirst, String moveSecond){
        if (isSound)
            mediaPlayer.start();

        int icon;
        if (isGo) {
            icon = R.drawable.ic_delete;
            textStep.setText(moveSecond);
            imageView.setImageResource(icon);
            imageView.setColorFilter(Color.BLACK);
        } else {
            icon = R.drawable.ic_circle;
            textStep.setText(moveFirst);
            imageView.setImageResource(icon);
            imageView.setColorFilter(Color.WHITE);
        }
    }

    private void victoryCheck(String firstCongrat, String secondCongrat){
        if (gameController.arrayCheck(isGo)) {
            isGame = false;

            if (isGo) {
                textStep.setText(firstCongrat);

                if(LevelSettings.getLevelWhoPlayer() == EnumType.OFFLINE){
                    statistic.addFirstPlayerWon(1);
                }
                else if(LevelSettings.getLevelWhoPlayer() == EnumType.BOT){
                    switch (LevelSettings.getLevelHard()){
                        case EASY:
                            statistic.addEasyBotWon(1);
                            break;
                        case MEDIUM:
                            statistic.addMediumBotWon(1);
                            break;
                        case HARD:
                            statistic.addHardBotWon(1);
                            break;
                    }
                }
                saveStatistic();
            } else {
                textStep.setText(secondCongrat);
                if((LevelSettings.getLevelWhoPlayer() == EnumType.OFFLINE))
                    statistic.addSecondPlayerWon(1);
            }
        } else if (gameController.nooneWon() && !gameController.arrayCheck()) {
            isGame = false;
            textStep.setText(activity.getString(R.string.nooneWon));
            statistic.haveTie();
        }
        saveStatistic();
    }

    private void botsStepTimer(){
        int temp = (int)(Math.random() * 400 + 100);
        new CountDownTimer(temp, 1000){
            @Override
            public void onTick(long millisUntilFinished){ }
            public void onFinish(){
                if(isGame) {
                    int[] array = gameController.botGoes(hardLevel);
                    changeDeleteToCircle(imageList.get(array[0] * arraySize + array[1]), activity.getString(R.string.playerMove), activity.getString(R.string.botMove));

                    victoryCheck(activity.getString(R.string.congratPlayer), activity.getString(R.string.congratBot));

                    isGo = !isGo;
                }
            }
        }.start();
    }


    private void setEnemyArray(){
            if (isGame && enemyX != -1 && gameController.setArray(enemyX, enemyY, 2)) {
//                if (isSound)
//                    mediaPlayer.start();

                textStep.setText(activity.getString(R.string.moves) + " " + isMove);
                int icon = R.drawable.ic_circle;
                switch (enemyX){
                    case 0:
                        imageList.get(enemyY).setImageResource(icon);
                        break;
                    case 1:
                        imageList.get(3 + enemyY).setImageResource(icon);
                        break;
                    case 2:
                        imageList.get(6 + enemyY).setImageResource(icon);
                        break;
                }

                if (gameController.arrayCheck()) {
                    isGame = false;
                    textStep.setText(activity.getString(R.string.uLost));
                } else if (gameController.nooneWon() && !gameController.arrayCheck())
                    textStep.setText(activity.getString(R.string.nooneWon));
            }
    }

    private void restartGame(){
        if(LevelSettings.getLevelWhoPlayer() == EnumType.OFFLINE || LevelSettings.getLevelWhoPlayer() == EnumType.BOT) {
            isMove = null;
            isGame = true;
            isGo = true;
            textStep.setText(activity.getString(R.string.firstMove));
            gameController = new GameController();

            for (ImageView image : imageList) {
                image.setImageResource(R.drawable.empty);
            }
        }
    }

    private void saveStatistic(){
        new Save(activity, statistic).saveStatistic();
    }

    private class DataCon{
        private int  index, x, y;

        DataCon(int index, int x, int y) {
            this.index = index;
            this.x = x;
            this.y = y;
        }
    }

    private void getInfoByGameID(){
        NetworkService.getInstance()
                .getJSONApi()
                .getPostWithGameID(gameID)
                .enqueue(new Callback<NetworkService.Post>() {
                    @Override
                    public void onResponse(@NonNull Call<NetworkService.Post> call, @NonNull Response<NetworkService.Post> response) {
                        NetworkService.Post post = response.body();

                        if (post != null) {
                            if(!post.getX().equals("None"))
                                enemyX = Integer.parseInt(post.getX());
                            if(!post.getY().equals("None"))
                                enemyY = Integer.parseInt(post.getY());
                            if(!post.getMove().equals("None"))
                                isMove = post.getMove();

                            System.out.println("BASE X " + post.getX());
                            System.out.println("BASE Y " + post.getY());
                            System.out.println("BASE MOVE: " + post.getMove());

                            System.out.println("GAME ENEMY X" + enemyX);
                            System.out.println("GAME ENEMY Y" + enemyY);
                            System.out.println("GAME ENEMY MOVE" + isMove);
                        }
                        else{
                            System.out.println("Error");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NetworkService.Post> call, @NonNull Throwable t) {
                        System.out.println("IDError occurred while getting request!");
                        System.out.println("IDCall: " + call.toString());
                        System.out.println("IDError: " + t.toString());
                    }
                });
    }

    private void getInfoByName(){
        NetworkService.getInstance()
                .getJSONApi()
                .getPostWithName(nickName)
                .enqueue(new Callback<NetworkService.Post>() {
                    @Override
                    public void onResponse(@NonNull Call<NetworkService.Post> call, @NonNull Response<NetworkService.Post> response) {
                        NetworkService.Post post = response.body();
                        System.out.println(call);

                        if (post != null) {
                            if(post.getGameId() != null) {
                                gameID = post.getGameId();
                                System.out.println("GameID: " + post.getGameId() + "\n");
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NetworkService.Post> call, @NonNull Throwable t) {
                        System.out.println("NAMEError occurred while getting request!");
                        System.out.println("NAME" + t.toString());
                    }
                });
    }

    private void matchmaking(String nickName){
        JsonObject value = new JsonObject();
        value.addProperty("userName", nickName);


        NetworkService.getInstance()
                .getJSONApi()
                .postMatchmaking(value)
                .enqueue(new Callback<NetworkService.Post>() {
                    @Override
                    public void onResponse(@NonNull Call<NetworkService.Post> call, @NonNull Response<NetworkService.Post> response) { }

                    @Override
                    public void onFailure(@NonNull Call<NetworkService.Post> call, @NonNull Throwable t) {
                        Toast.makeText(activity, "Error occurred while getting request!", Toast.LENGTH_LONG).show();
                        System.out.println(t.toString());
                        t.printStackTrace();
                    }
                });
    }

    private void coordinates(int x, int y){
        JsonObject value = new JsonObject();
        value.addProperty("gameId", gameID);
        value.addProperty("userName", nickName);
        value.addProperty("x", x);
        value.addProperty("y", y);

        NetworkService.getInstance()
                .getJSONApi()
                .postCoordinates(value)
                .enqueue(new Callback<NetworkService.Post>() {
                    @Override
                    public void onResponse(@NonNull Call<NetworkService.Post> call, @NonNull Response<NetworkService.Post> response) { }

                    @Override
                    public void onFailure(@NonNull Call<NetworkService.Post> call, @NonNull Throwable t) {
                        Toast.makeText(activity, "COORDINATESError occurred while getting request!", Toast.LENGTH_LONG).show();
                        System.out.println("COORDINATESError occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }
}


