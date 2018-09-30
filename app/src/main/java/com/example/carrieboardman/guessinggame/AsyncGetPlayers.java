package com.example.carrieboardman.guessinggame;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncGetPlayers extends AsyncTask<URL, String, String> {

    public AsyncGetPlayers(Context context) {

        this.context = new WeakReference<Context>(context);
    }

    public AsyncResult callback = null;

    private WeakReference<Context> context;


    private Boolean isNetworkConnected(){

        Context ctx = context.get();
        ConnectivityManager connManager =
                (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }


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
            } else {
                return result;
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

        callback.processJson(s);

    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        callback.onCancel();
    }
}
