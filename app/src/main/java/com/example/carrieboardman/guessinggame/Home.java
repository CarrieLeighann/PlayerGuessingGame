package com.example.carrieboardman.guessinggame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Bundle bundle = this.getIntent().getExtras();

       if (!(bundle == null)){
           int score = bundle.getInt("SCORE");

           String message = getResources().getString(R.string.your_score) + " " + String.valueOf(score) + " out of " + Game.GAME_ITERATIONS + "\n " + getResources().getString(R.string.new_game);
           ((TextView) findViewById(R.id.welcome_text)).setText(message);
       }




    }

    public void goToGame(View view) {

        startActivity(new Intent(this, GameMain.class));
    }
}
