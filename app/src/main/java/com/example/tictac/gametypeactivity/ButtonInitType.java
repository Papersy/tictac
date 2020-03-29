package com.example.tictac.gametypeactivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tictac.EnumPack.EnumHardLevel;
import com.example.tictac.EnumPack.EnumLevel;
import com.example.tictac.EnumPack.EnumType;
import com.example.tictac.LevelSettings.LevelSettings;
import com.example.tictac.R;
import com.example.tictac.Retrofit.NetworkService;
import com.example.tictac.startactivity.StartActivity;
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
    private String gameID;

    ButtonInitType(GameTypeActivity activity) {
    this.activity = activity;
    LevelSettings.setLevelSize(EnumLevel.FIRST);
    LevelSettings.setLevelHard(EnumHardLevel.EASY);

    editText = activity.findViewById(R.id.editNickName);
    checkBox = activity.findViewById(R.id.checkBox);
    editText.setInputType(InputType.TYPE_CLASS_TEXT);
    imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

    SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(activity);

        if(myPreferences.getBoolean("isCheckBox", false)) {
            checkBox.setChecked(true);
            editText.setText(myPreferences.getString("userName", null));

            nickName = editText.getText().toString();

            ((TextView)activity.findViewById(R.id.textPlayerType)).setText(LevelSettings.getLevelWhoPlayer().toString());
            switch (LevelSettings.getLevelWhoPlayer()){
                case BOT:
                    LevelSettings.setLevelHard(EnumHardLevel.EASY);
                    ((TextView) activity.findViewById(R.id.textSizeType)).setText(LevelSettings.getLevelHard().toString());
                    break;
                case OFFLINE:
                    ((TextView) activity.findViewById(R.id.textSizeType)).setText("3x3");
                    break;
                case ONLINE:
                    activity.findViewById(R.id.linearSizeType).setEnabled(false);
                    editText.setVisibility(View.VISIBLE);
                    checkBox.setVisibility(View.VISIBLE);
                    activity.findViewById(R.id.linearSizeType).setVisibility(View.GONE);
                    break;
            }


            SharedPreferences.Editor myEditor = myPreferences.edit();
            myEditor.putString("userName", nickName);
            myEditor.apply();
        }

    }

    void Listeners(){

        activity.findViewById(R.id.linearPlayerType).setOnClickListener(v-> {
            if(LevelSettings.getLevelWhoPlayer() == EnumType.OFFLINE){
                LevelSettings.setLevelWhoPlayer(EnumType.BOT);
                ((TextView)activity.findViewById(R.id.textPlayerType)).setText("Bot");
                activity.findViewById(R.id.linearSizeType).setEnabled(true);
                editText.setVisibility(View.GONE);
                checkBox.setVisibility(View.GONE);
                ((Button) activity.findViewById(R.id.btnStartRound)).setText("Start");
                ((TextView) activity.findViewById(R.id.textSizeType)).setText("Easy");
                activity.findViewById(R.id.linearSizeType).setVisibility(View.VISIBLE);
            }
            else if(LevelSettings.getLevelWhoPlayer() == EnumType.BOT){
                LevelSettings.setLevelWhoPlayer(EnumType.ONLINE);
                ((TextView)activity.findViewById(R.id.textPlayerType)).setText("Online");
                activity.findViewById(R.id.linearSizeType).setEnabled(false);
                editText.setVisibility(View.VISIBLE);
                checkBox.setVisibility(View.VISIBLE);
                ((Button) activity.findViewById(R.id.btnStartRound)).setText("Find player");
                activity.findViewById(R.id.linearSizeType).setVisibility(View.GONE);
                ((TextView) activity.findViewById(R.id.textSizeType)).setText("3x3");
                LevelSettings.setLevelSize(EnumLevel.FIRST);
                editText.requestFocus();

                //imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
            else if(LevelSettings.getLevelWhoPlayer() == EnumType.ONLINE){
                //editText.clearFocus();

                LevelSettings.setLevelWhoPlayer(EnumType.OFFLINE);
                ((TextView)activity.findViewById(R.id.textPlayerType)).setText("Offline");
                activity.findViewById(R.id.linearSizeType).setEnabled(true);
                editText.setVisibility(View.GONE);
                checkBox.setVisibility(View.GONE);
                ((Button) activity.findViewById(R.id.btnStartRound)).setText("Start");
                activity.findViewById(R.id.linearSizeType).setVisibility(View.VISIBLE);

                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        });

        activity.findViewById(R.id.linearSizeType).setOnClickListener(v->{
            if(LevelSettings.getLevelWhoPlayer() == EnumType.OFFLINE) {
                if (LevelSettings.getLevelSize() == EnumLevel.FIRST) {
                    LevelSettings.setLevelSize(EnumLevel.SECOND);
                    ((TextView) activity.findViewById(R.id.textSizeType)).setText(String.valueOf("5x5"));
                } else if (LevelSettings.getLevelSize() == EnumLevel.SECOND) {
                    LevelSettings.setLevelSize(EnumLevel.THIRD);
                    ((TextView) activity.findViewById(R.id.textSizeType)).setText(String.valueOf("7x7"));
                } else if (LevelSettings.getLevelSize() == EnumLevel.THIRD) {
                    LevelSettings.setLevelSize(EnumLevel.FIRST);
                    ((TextView) activity.findViewById(R.id.textSizeType)).setText(String.valueOf("3x3"));
                }
            }
            else if(LevelSettings.getLevelWhoPlayer() == EnumType.BOT){
                if (LevelSettings.getLevelHard() == EnumHardLevel.EASY) {
                    LevelSettings.setLevelHard(EnumHardLevel.MEDIUM);
                    ((TextView) activity.findViewById(R.id.textSizeType)).setText(String.valueOf("Medium"));
                } else if (LevelSettings.getLevelHard() == EnumHardLevel.MEDIUM) {
                    LevelSettings.setLevelHard(EnumHardLevel.HARD);
                    ((TextView) activity.findViewById(R.id.textSizeType)).setText(String.valueOf("Hard"));
                } else if (LevelSettings.getLevelHard() == EnumHardLevel.HARD) {
                    LevelSettings.setLevelHard(EnumHardLevel.EASY);
                    ((TextView) activity.findViewById(R.id.textSizeType)).setText(String.valueOf("Easy"));
                }
            }
        });

        activity.findViewById(R.id.btnStartRound).setOnClickListener(v->
        {
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
                        Toast.makeText(activity, "Error with internet connection!", Toast.LENGTH_SHORT).show();
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
