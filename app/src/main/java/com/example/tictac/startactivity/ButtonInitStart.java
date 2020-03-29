package com.example.tictac.startactivity;

import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tictac.EnumPack.EnumHardLevel;
import com.example.tictac.EnumPack.EnumType;
import com.example.tictac.GameController;
import com.example.tictac.LevelSettings.LevelSettings;
import com.example.tictac.Retrofit.NetworkService;
import com.example.tictac.Statistic.LoadStatistic;
import com.example.tictac.R;
import com.example.tictac.Statistic.Statistic;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.logging.Level;

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
    }

    void Listeners(){
        textStep = activity.findViewById(R.id.textStep);

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

                linearBox.setBackgroundResource(R.drawable.play_btn_style);

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

        activity.findViewById(R.id.imgRestartGame).setOnClickListener(v-> restartGame());
    }

    void setEnabled(){
        for (LinearLayout linear: linearList) {
            linear.setEnabled(true);
        }
    }

    private void checkBox(ImageView imageView, int x, int y){
        if(LevelSettings.getLevelWhoPlayer() == EnumType.OFFLINE) {
            if (gameController.setArray(x, y, isGo) && isGame) {

                changeInfoSound(imageView, activity.getString(R.string.firstMove), activity.getString(R.string.secondMove));

                if (gameController.arrayCheck()) {
                    isGame = false;
                    if (isGo) {
                        textStep.setText(activity.getString(R.string.congratFirst));
                        statistic.addFirstPlayerWon(1);
                        saveStatistic();
                    } else {
                        textStep.setText(activity.getString(R.string.congratSecond));
                        statistic.addSecondPlayerWon(1);
                        saveStatistic();
                    }
                } else if (gameController.nooneWon() && !gameController.arrayCheck())
                    textStep.setText(activity.getString(R.string.nooneWon));

                isGo = !isGo;
            }
        }
        else if(LevelSettings.getLevelWhoPlayer() == EnumType.BOT){
            if (gameController.setArray(x, y, isGo) && isGame) {
                changeInfoSound(imageView, activity.getString(R.string.firstMove), activity.getString(R.string.botMove));

                victoryCheck(activity.getString(R.string.congratFirst), activity.getString(R.string.congratBot));

                isGo = !isGo;

                if(isGame) {
                    int[] array = gameController.botGoes(hardLevel);
                    changeInfoSound(imageList.get(array[0] * 3 + array[1]), activity.getString(R.string.firstMove), activity.getString(R.string.botMove));

                    victoryCheck(activity.getString(R.string.congratFirst), activity.getString(R.string.congratBot));

                    isGo = !isGo;
                }
            }
        }
        else if(LevelSettings.getLevelWhoPlayer() == EnumType.ONLINE){
            if(isMove.equals(nickName)){
                if (gameController.setArray(x, y, 1) && isGame) {
                    coordinates(x,y);
                    if (isSound)
                        mediaPlayer.start();

                    int icon = R.drawable.ic_cancel;
                    textStep.setText(activity.getString(R.string.moves) + " " + isMove);

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

    private void changeInfoSound(ImageView imageView, String moveFirst, String moveSecond){
        if (isSound)
            mediaPlayer.start();
        int icon;
        if (isGo) {
            icon = R.drawable.ic_cancel;
            textStep.setText(moveSecond);
        } else {
            icon = R.drawable.ic_circle;
            textStep.setText(moveFirst);
        }
        imageView.setImageResource(icon);
    }

    private void victoryCheck(String firstCongrat, String secondCongrat){
        if (gameController.arrayCheck()) {
            isGame = false;
            if (isGo) {
                textStep.setText(firstCongrat);

                if(LevelSettings.getLevelWhoPlayer() == EnumType.OFFLINE){
                    statistic.addFirstPlayerWon(1);
                }

                else if(LevelSettings.getLevelWhoPlayer() == EnumType.BOT){
                    System.out.println("ASDASD");
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
            }
        } else if (gameController.nooneWon() && !gameController.arrayCheck())
            textStep.setText(activity.getString(R.string.nooneWon));
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
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        myEditor.putInt("FirstPlayerWon", statistic.getFirstPlayerWon());
        myEditor.putInt("SecondPlayerWon", statistic.getSecondPlayerWon());
        myEditor.putInt("easyBot", statistic.getEasyBotWon());
        myEditor.putInt("mediumBot", statistic.getMediumBotWon());
        myEditor.putInt("hardBot", statistic.getHardBotWon());

        myEditor.apply();
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


