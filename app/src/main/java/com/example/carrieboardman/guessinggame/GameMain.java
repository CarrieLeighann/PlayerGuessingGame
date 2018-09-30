package com.example.carrieboardman.guessinggame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class GameMain extends AppCompatActivity implements AsyncResult{


    static int NO_INTERNET = 0;
    static int JSON_EXCEPTION = 1;

    static String URL_STRING = "https://gist.githubusercontent.com/liamjdouglas/bb40ee8721f1a9313c22c6ea0851a105/r\n" +
            "aw/6b6fc89d55ebe4d9b05c1469349af33651d7e7f1/Player.json";

    private PlayerArrayHandler handler;

    private Game currentGame;


    TextView playerOneText; TextView playerTwoText;
    TextView playerOneFPPG; TextView playerTwoFPPG;
    TextView result;


    CardView playerOne; CardView playerTwo;

    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameMain.this, Home.class));
                finish();
            }
        });

        playerOneText = (TextView) findViewById(R.id.player_one);
        playerTwoText = (TextView) findViewById(R.id.player_two);
        playerOneFPPG = (TextView) findViewById(R.id.fppg_p1);
        playerTwoFPPG = (TextView) findViewById(R.id.fppg_p2);
        result = (TextView) findViewById(R.id.result);

        playerOne = findViewById(R.id.player_one_option);
        playerTwo = findViewById(R.id.player_two_option);

        next = findViewById(R.id.nextPlayer);

        next.setClickable(false);

        try {
            URL url = new URL(URL_STRING);

            AsyncGetPlayers get = new AsyncGetPlayers(this);

            get.callback = this;
            get.execute(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processJson(String s) {

        try {
            JSONObject res = new JSONObject(s);

            JsonHandler handler = new JsonHandler();

            currentGame = new Game();
            currentGame.setPlayerArray(handler.processPlayers(res));

            newTurn();
        } catch (JSONException e) {
            e.printStackTrace();
            createErrorDialog(1);
        }
    }

    @Override
    public void onCancel() {
     createErrorDialog(0);
    }


    public void createErrorDialog(int errorType){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (errorType == NO_INTERNET){
            builder.setMessage(R.string.no_internet)
                    .setTitle(R.string.no_internet_title);
        } else if (errorType == JSON_EXCEPTION){
            builder.setMessage(R.string.problem)
                    .setTitle(R.string.error);
        }


        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                startActivity(new Intent(GameMain.this, Home.class));
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void newTurn() {

        if (!this.currentGame.isLastTurn()) {

            this.currentGame.getNextPlayers();

            Picasso.get().load(this.currentGame.getCurrentPlayers()[0].getImage()).into((ImageView) findViewById(R.id.p1_image));
            Picasso.get().load(this.currentGame.getCurrentPlayers()[1].getImage()).into((ImageView) findViewById(R.id.p2_image));
            this.playerOneText.setText((this.currentGame.getCurrentPlayers()[0]).getPlayerName());
            this.playerTwoText.setText((this.currentGame.getCurrentPlayers()[1]).getPlayerName());
            this.playerOneFPPG.setText(String.valueOf((this.currentGame.getCurrentPlayers()[0]).getFppg()));
            this.playerTwoFPPG.setText(String.valueOf((this.currentGame.getCurrentPlayers()[1]).getFppg()));

        } else {
          /*  this.result.setVisibility(View.VISIBLE);
            this.result.setText("End of game");*/

          Intent backHome = new Intent(this, Home.class);
          backHome.putExtra("SCORE", this.currentGame.getScore());

          startActivity(backHome);

        }
    }

    private void resetUI() {
        this.playerOneFPPG.setVisibility(View.GONE);
        this.playerTwoFPPG.setVisibility(View.GONE);
        this.result.setVisibility(View.GONE);

        this.playerOne.setClickable(true);
        this.playerTwo.setClickable(true);

        findViewById(R.id.nextPlayer).setClickable(false);
        this.next.setTextColor(ResourcesCompat.getColor(getResources(), R.color.disabled, null));
    }

    public void playerChosen(View view) {

       this.playerOneFPPG.setVisibility(View.VISIBLE);
       this.playerTwoFPPG.setVisibility(View.VISIBLE);

        Player correctPlayer = this.currentGame.getCorrectAnswer();

        String chosenPlayer = "";

        switch (view.getId()){
            case (R.id.player_one_option):
                chosenPlayer = ((TextView) view.findViewById(R.id.player_one)).getText().toString();
                break;
            case (R.id.player_two_option):
                chosenPlayer = ((TextView) view.findViewById(R.id.player_two)).getText().toString();
                break;
        }


        this.result.setVisibility(View.VISIBLE);

        if (chosenPlayer.equals(correctPlayer.getPlayerName())){

            result.setText(getResources().getString(R.string.correct));
            result.setTextColor(ResourcesCompat.getColor(getResources(), R.color.correctGreen, null));
            this.currentGame.incrementScore();
            ((TextView) findViewById(R.id.score)).setText(String.valueOf(this.currentGame.getScore()));
        }else {
            result.setText(getResources().getString(R.string.wrong));
            result.setTextColor(ResourcesCompat.getColor(getResources(), R.color.incorrectRed, null));
        }

        this.currentGame.incrementTurns();
        this.playerOne.setClickable(false);
        this.playerTwo.setClickable(false);

       this.next.setClickable(true);
       this.next.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, null));
    }

    public void nextClicked(View view) {
        resetUI();
        newTurn();
    }
}
