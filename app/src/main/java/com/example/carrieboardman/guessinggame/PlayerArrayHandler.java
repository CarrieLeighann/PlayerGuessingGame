package com.example.carrieboardman.guessinggame;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerArrayHandler {

    private ArrayList<Player> playerArray;

    public PlayerArrayHandler(JSONArray playerArray) {
        this.playerArray = new ArrayList<>(playerArray.length());

        try {
            processPlayers(playerArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void processPlayers(JSONArray playerArray) throws JSONException {

        for (int i=0; i < playerArray.length()-1; i++){

            JSONObject playerObject = playerArray.getJSONObject(i);

            Double score = playerObject.optDouble("fppg");

            if (!(score.isNaN())) {
                Player newPlayer = new Player();

                String fullName = playerObject.getString("first_name") + " " + playerObject.getString("last_name");

                newPlayer.setPlayerName(fullName); newPlayer.setFppg(score);

                this.playerArray.add(newPlayer);
            }
        }
        Log.d("MAP", this.playerArray.toString());
    }


    public Boolean differentScores (Player p1, Player p2){
        return !p1.getFppg().equals(p2.getFppg());

    }

    public Player higherScoreCalc (Player p1, Player p2){
        if (p1.getFppg() > p2.getFppg()){
            return p1;
        } else {
            return p2;
        }
    }
}
