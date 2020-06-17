package com.game.tictac.gametypeactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.game.tictac.EnumPack.EnumHardLevel;
import com.game.tictac.EnumPack.EnumLevel;
import com.game.tictac.EnumPack.EnumType;
import com.game.tictac.LevelSettings.LevelSettings;
import com.game.tictac.R;
import com.game.tictac.Retrofit.NetworkService;
import com.game.tictac.mainactivity.MainActivity;
import com.game.tictac.settingsActivity.SettingsActivity;
import com.game.tictac.startactivity.StartActivity;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ButtonInitType {
    private GameTypeActivity activity;
    private String nickName;
    private EditText editText;
    private CheckBox checkBox;
    private InputMethodManager imm;
    //private String gameID;
    private int gamePosition;
    private MediaPlayer mediaPlayer;
    private boolean isSound;

    ButtonInitType(GameTypeActivity activity) {
        this.activity = activity;
        LevelSettings.setLevelSize(EnumLevel.FIRST);
        LevelSettings.setLevelHard(EnumHardLevel.EASY);

        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        isSound = myPreferences.getBoolean("isSound", true);

        mediaPlayer = MediaPlayer.create(activity, R.raw.click_sound);
        mediaPlayer.setVolume(0.15f, 0.15f);
        editText = activity.findViewById(R.id.editNickName);
        checkBox = activity.findViewById(R.id.checkBox);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if(myPreferences.getBoolean("isCheckBox", false)) {
            checkBox.setChecked(true);
            editText.setText(myPreferences.getString("userName", null));

            nickName = editText.getText().toString();

            SharedPreferences.Editor myEditor = myPreferences.edit();
            myEditor.putString("userName", nickName);
            myEditor.apply();
        }

    }

    void Listeners(){

        activity.findViewById(R.id.linearOffline).setOnClickListener(v-> {
            if (isSound)
                mediaPlayer.start();
            gamePosition = 1;
            LevelSettings.setLevelWhoPlayer(EnumType.OFFLINE);

            setVisibilityForTypes();

            ((ImageView)activity.findViewById(R.id.imageBuffer)).setImageResource(R.drawable.ic_users);
//            ((ImageView)activity.findViewById(R.id.imageBuffer)).setColorFilter(Color.parseColor("#124C78"));
            ((ImageView)activity.findViewById(R.id.imageBuffer)).setColorFilter(Color.WHITE);
            ((TextView)activity.findViewById(R.id.textBuffer)).setText(R.string.offline);
        });

        activity.findViewById(R.id.linearBot).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();
            gamePosition = 1;
            LevelSettings.setLevelWhoPlayer(EnumType.BOT);

            setVisibilityForTypes();
            activity.findViewById(R.id.linearHardType).setVisibility(View.VISIBLE);

            ((ImageView)activity.findViewById(R.id.imageBuffer)).setImageResource(R.drawable.ic_android);
//            ((ImageView)activity.findViewById(R.id.imageBuffer)).setColorFilter(Color.parseColor("#124C78"));
            ((ImageView)activity.findViewById(R.id.imageBuffer)).setColorFilter(Color.WHITE);
            ((TextView)activity.findViewById(R.id.textBuffer)).setText(R.string.bot);
        });

        activity.findViewById(R.id.linearOnline).setOnClickListener(v -> {
//            setVisibilityForTypes();
//            LevelSettings.setLevelWhoPlayer(EnumType.ONLINE);
//            ((TextView)activity.findViewById(R.id.textPlayerType)).setText("Online");
//            activity.findViewById(R.id.linearSizeType).setEnabled(false);
//            editText.setVisibility(View.VISIBLE);
//            checkBox.setVisibility(View.VISIBLE);
//            ((Button) activity.findViewById(R.id.btnStartRound)).setText("Find player");
//            activity.findViewById(R.id.linearSizeType).setVisibility(View.GONE);
//            ((TextView) activity.findViewById(R.id.textSizeType)).setText("3x3");
//            LevelSettings.setLevelSize(EnumLevel.FIRST);
//            editText.requestFocus();
//
//            //imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        });


        activity.findViewById(R.id.imageSizeRight).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();

            if (LevelSettings.getLevelSize() == EnumLevel.FIRST) {
                LevelSettings.setLevelSize(EnumLevel.SECOND);
                activity.findViewById(R.id.imageSizeLeft).setVisibility(View.VISIBLE);
                ((TextView) activity.findViewById(R.id.textSizeType)).setText(R.string.size5);
            } else if (LevelSettings.getLevelSize() == EnumLevel.SECOND) {
                LevelSettings.setLevelSize(EnumLevel.THIRD);
                activity.findViewById(R.id.imageSizeRight).setVisibility(View.GONE);
                ((TextView) activity.findViewById(R.id.textSizeType)).setText(R.string.size7);
            }
        });

        activity.findViewById(R.id.imageSizeLeft).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();

            if (LevelSettings.getLevelSize() == EnumLevel.SECOND) {
                LevelSettings.setLevelSize(EnumLevel.FIRST);
                activity.findViewById(R.id.imageSizeLeft).setVisibility(View.GONE);
                ((TextView) activity.findViewById(R.id.textSizeType)).setText(R.string.size3);
            } else if (LevelSettings.getLevelSize() == EnumLevel.THIRD) {
                LevelSettings.setLevelSize(EnumLevel.SECOND);
                activity.findViewById(R.id.imageSizeRight).setVisibility(View.VISIBLE);
                ((TextView) activity.findViewById(R.id.textSizeType)).setText(R.string.size5);
            }
        });

        activity.findViewById(R.id.imageHardRight).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();
                if (LevelSettings.getLevelHard() == EnumHardLevel.EASY) {
                    LevelSettings.setLevelHard(EnumHardLevel.MEDIUM);
                    activity.findViewById(R.id.imageHardLeft).setVisibility(View.VISIBLE);
                    ((TextView) activity.findViewById(R.id.textHardType)).setText(R.string.medium);
                } else if (LevelSettings.getLevelHard() == EnumHardLevel.MEDIUM) {
                    LevelSettings.setLevelHard(EnumHardLevel.HARD);
                    activity.findViewById(R.id.imageHardRight).setVisibility(View.GONE);
                    ((TextView) activity.findViewById(R.id.textHardType)).setText(R.string.hard);
                }
        });

        activity.findViewById(R.id.imageHardLeft).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();
                if (LevelSettings.getLevelHard() == EnumHardLevel.MEDIUM) {
                    LevelSettings.setLevelHard(EnumHardLevel.EASY);
                    activity.findViewById(R.id.imageHardLeft).setVisibility(View.GONE);
                    ((TextView) activity.findViewById(R.id.textHardType)).setText(R.string.easy);
                } else if (LevelSettings.getLevelHard() == EnumHardLevel.HARD) {
                    LevelSettings.setLevelHard(EnumHardLevel.MEDIUM);
                    activity.findViewById(R.id.imageHardRight).setVisibility(View.VISIBLE);
                    ((TextView) activity.findViewById(R.id.textHardType)).setText(R.string.medium);
                }
        });

        activity.findViewById(R.id.buttonBack).setOnClickListener(v -> {
            if (isSound)
                mediaPlayer.start();
            if(gamePosition == 0)
            {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            }
            else if(gamePosition == 1){
                gamePosition = 0;
                activity.findViewById(R.id.btnStartRound).setVisibility(View.GONE);
                activity.findViewById(R.id.linearBuffer).setVisibility(View.GONE);
                activity.findViewById(R.id.linearOffline).setVisibility(View.VISIBLE);
                //activity.findViewById(R.id.linearOnline).setVisibility(View.GONE);
                activity.findViewById(R.id.linearBot).setVisibility(View.VISIBLE);
                activity.findViewById(R.id.linearSizeType).setVisibility(View.GONE);
                activity.findViewById(R.id.linearHardType).setVisibility(View.GONE);

                editText.setVisibility(View.GONE);
                checkBox.setVisibility(View.GONE);
            }
        });

        activity.findViewById(R.id.btnStartRound).setOnClickListener(v-> {
            if (isSound)
                mediaPlayer.start();

            nickName = editText.getText().toString();
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);

            if(nickName == null && LevelSettings.getLevelWhoPlayer() == EnumType.ONLINE) {
                Toast.makeText(activity,"Write your nickname!", Toast.LENGTH_SHORT).show();
            }
            else{
                saveNickName(editText.getText().toString());
                try{
                    registration();
                    intentView();
                }catch (Exception e){
                    ConnectivityManager cm = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);

                    if(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED || cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)
                        Toast.makeText(activity, "Error, pls try later!", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(activity, "Problems with internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveNickName(editText.getText().toString());
                nickName = editText.getText().toString();
            }
        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
            SharedPreferences.Editor myEditor = myPreferences.edit();
            if (checkBox.isChecked()) {
                myEditor.putBoolean("isCheckBox", true);
                saveNickName(editText.getText().toString());
            }
            else{
                myEditor.putBoolean("isCheckBox", false);
            }
            myEditor.apply();
        });
    }

    private void setVisibilityForTypes(){
        activity.findViewById(R.id.btnStartRound).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.linearOffline).setVisibility(View.GONE);
        activity.findViewById(R.id.linearBot).setVisibility(View.GONE);
        activity.findViewById(R.id.linearOnline).setVisibility(View.GONE);
        activity.findViewById(R.id.linearSizeType).setVisibility(View.VISIBLE);
        activity.findViewById(R.id.linearBuffer).setVisibility(View.VISIBLE);
    }

    public void setPosition(int index){
        gamePosition = index;
    }

    public int getPosition(){
        return gamePosition;
    }

    private void intentView() {
        Intent intent = new Intent(activity, StartActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    private void saveNickName(String nickName){
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor myEditor = myPreferences.edit();

        myEditor.putString("userName", nickName);

        myEditor.apply();
    }

    private void registration(){
        JsonObject value = new JsonObject();
        value.addProperty("userName", nickName);

        NetworkService.getInstance()
                .getJSONApi()
                .postDataRegister(value)
                .enqueue(new Callback<NetworkService.Post>() {
                    @Override
                    public void onResponse(@NonNull Call<NetworkService.Post> call, @NonNull Response<NetworkService.Post> response) {
                        NetworkService.Post post = response.body();
                        if(post != null)
                            System.out.println("Message" + post.getMessage());
                    }
                    @Override
                    public void onFailure(@NonNull Call<NetworkService.Post> call, @NonNull Throwable t) {
                        System.out.println("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }
}
