package com.example.carrieboardman.guessinggame;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonHandler {


    public JsonHandler() {

    }

    public ArrayList<Player> processPlayers(JSONObject result) throws JSONException {

        JSONArray jsonArray = result.getJSONArray("players");


        ArrayList<Player> playerArray = new ArrayList<Player>();

        for (int i=0; i < (Game.GAME_ITERATIONS*Game.PLAYERS_SHOWN); i++){

            JSONObject playerObject = jsonArray.getJSONObject(i);

            Double score = playerObject.optDouble("fppg");

            if (!(score.isNaN())) {
                Player newPlayer = new Player();

                String fullName = playerObject.getString("first_name") + " " + playerObject.getString("last_name");

                newPlayer.setPlayerName(fullName); newPlayer.setFppg(score);
                newPlayer.setImage(playerObject.getJSONObject("images").getJSONObject("default").getString("url"));

                playerArray.add(newPlayer);
            }
        }


        return playerArray;
    }
}
