package com.example.carrieboardman.guessinggame;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


//This class keeps logic for iterating and processing the player array out of the Game class
public class PlayerArrayHandler {


    public PlayerArrayHandler() {

    }

    //TODO: Implement logic to handle two current players having the same score
    public Boolean hasDifferentScores (Player p1, Player p2){
        return !p1.getFppg().equals(p2.getFppg());

    }

    public int higherScoreCalc (Player[] curr) {
        if (curr[0].getFppg() > curr[1].getFppg()) {
            return 0;
        } else {
            return 1;
        }

    }

    //get the next two players on the list
    public Player[] getNextPlayers (Game game){
        Player[] players = new Player[2];
        for (int i = 0; i < 2; i++){
            players[i] = game.getPlayerArray().get(game.getPlayerIndex());
            game.incrementPlayerNo();
        }

        return players;

    }
}
