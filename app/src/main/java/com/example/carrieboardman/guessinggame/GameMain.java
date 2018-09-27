package com.example.carrieboardman.guessinggame;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

    String urlString = "https://gist.githubusercontent.com/liamjdouglas/bb40ee8721f1a9313c22c6ea0851a105/r\n" +
            "aw/6b6fc89d55ebe4d9b05c1469349af33651d7e7f1/Player.json";


    private PlayerArrayHandler handler;

    private Game currentGame;


    TextView playerOneText; TextView playerTwoText;
    TextView playerOneFPPG; TextView playerTwoFPPG;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        playerOneText = (TextView) findViewById(R.id.player_one);
        playerTwoText = (TextView) findViewById(R.id.player_two);
        playerOneFPPG = (TextView) findViewById(R.id.fppg_p1);
        playerTwoFPPG = (TextView) findViewById(R.id.fppg_p2);

        try {
            URL url = new URL(urlString);

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

            displayPlayers();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void displayPlayers() {

        this.currentGame.setCurrentPlayers();
        this.playerOneText.setText((this.currentGame.getCurrentPlayers()[0]).getPlayerName());
        this.playerTwoText.setText((this.currentGame.getCurrentPlayers()[1]).getPlayerName());
        this.playerOneFPPG.setText(String.valueOf((this.currentGame.getCurrentPlayers()[0]).getFppg()));
        this.playerTwoFPPG.setText(String.valueOf((this.currentGame.getCurrentPlayers()[1]).getFppg()));
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

        TextView result = (TextView) findViewById(R.id.result);
        result.setVisibility(View.VISIBLE);

        if (chosenPlayer.equals(correctPlayer.getPlayerName())){

            result.setText(getResources().getString(R.string.correct));
            result.setTextColor(getResources().getColor(R.color.correctGreen));
            this.currentGame.incrementScore();
            ((TextView) findViewById(R.id.score)).setText(String.valueOf(this.currentGame.getScore()));
        }else {
            result.setText(getResources().getString(R.string.wrong));
            result.setTextColor(getResources().getColor(R.color.incorrectRed));
        }

        newTurn();
    }

    private void newTurn() {


    }
}
