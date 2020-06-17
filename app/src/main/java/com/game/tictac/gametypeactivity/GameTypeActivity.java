package com.game.tictac.gametypeactivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.game.tictac.R;
import com.game.tictac.mainactivity.MainActivity;

public class GameTypeActivity extends AppCompatActivity {
    ButtonInitType buttonInitType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_type);
        ((ImageView)findViewById(R.id.imageBot)).setColorFilter(Color.WHITE);
        ((ImageView)findViewById(R.id.imageOffline)).setColorFilter(Color.WHITE);
        ((ImageView)findViewById(R.id.imageOnline)).setColorFilter(Color.WHITE);
        ((ImageView)findViewById(R.id.imageBack)).setColorFilter(Color.WHITE);
        ((ImageView)findViewById(R.id.imageSizeLeft)).setColorFilter(Color.WHITE);
        ((ImageView)findViewById(R.id.imageSizeRight)).setColorFilter(Color.WHITE);
        ((ImageView)findViewById(R.id.imageHardLeft)).setColorFilter(Color.WHITE);
        ((ImageView)findViewById(R.id.imageHardRight)).setColorFilter(Color.WHITE);

        buttonInitType = new ButtonInitType(this);
        buttonInitType.Listeners();
    }

    @Override
    public void onBackPressed(){
        if(buttonInitType.getPosition() == 0)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if(buttonInitType.getPosition() == 1){
            buttonInitType.setPosition(0);
            findViewById(R.id.btnStartRound).setVisibility(View.GONE);
            findViewById(R.id.linearBuffer).setVisibility(View.GONE);
            findViewById(R.id.linearOffline).setVisibility(View.VISIBLE);
            //findViewById(R.id.linearOnline).setVisibility(View.GONE);
            findViewById(R.id.linearBot).setVisibility(View.VISIBLE);
            findViewById(R.id.editNickName).setVisibility(View.GONE);
            findViewById(R.id.checkBox).setVisibility(View.GONE);
            findViewById(R.id.linearSizeType).setVisibility(View.GONE);
            findViewById(R.id.linearHardType).setVisibility(View.GONE);
        }
    }
}
