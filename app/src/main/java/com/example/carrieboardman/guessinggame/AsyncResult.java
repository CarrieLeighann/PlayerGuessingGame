package com.example.carrieboardman.guessinggame;

/*simple interface for connecting the AsyncGetPlayers class and the GameMain activity
which receives its result */
public interface AsyncResult {

    void processJson(String s);

    void onCancel();
}
