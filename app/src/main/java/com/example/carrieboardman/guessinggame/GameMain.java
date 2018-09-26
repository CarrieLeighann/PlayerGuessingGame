package com.example.carrieboardman.guessinggame;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

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
import java.util.HashMap;

public class GameMain extends AppCompatActivity {

    String urlString = "https://gist.githubusercontent.com/liamjdouglas/bb40ee8721f1a9313c22c6ea0851a105/r\n" +
            "aw/6b6fc89d55ebe4d9b05c1469349af33651d7e7f1/Player.json";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        try {
            URL url = new URL(urlString);

            GetPlayers get = new GetPlayers();

            get.execute(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    private Boolean isNetworkConnected(){
        ConnectivityManager connManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }



    private class GetPlayers extends AsyncTask<URL, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!isNetworkConnected()){
                cancel(true);
            }
        }

        @Override
        protected String doInBackground(URL... params) {

            URL url = params[0];
            String result = "";

            try{
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setInstanceFollowRedirects(true);

                urlConnection.connect();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK){
                    InputStream in = urlConnection.getInputStream();

                    if (in != null){

                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        StringBuilder sb = new StringBuilder();
                        String line = "";

                        while ((line = br.readLine()) != null){
                            sb.append(line);
                        }

                        result = sb.toString();
                        in.close();
                    }
                }
                urlConnection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
                return result;
            }

            return result;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject res = new JSONObject(s);
                JSONArray players = res.getJSONArray("players");

                new PlayerArrayHandler(players);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
