package com.example.carrieboardman.guessinggame;

import org.json.JSONArray;

import java.util.ArrayList;

public class Game {


    private int playerIndex;
    private int score;

    private Player[] currentPlayers;
    private Player correctAnswer;
    private ArrayList<Player> playerArray;

    private PlayerArrayHandler handler;

    public ArrayList<Player> getPlayerArray() {
        return playerArray;
    }

    public void setPlayerArray(ArrayList<Player> playerArray) {
        this.playerArray = playerArray;
    }

    public Game( ) {
        this.playerIndex = 0;

        this.score = 0;

        handler = new PlayerArrayHandler();

    }

    public void incrementScore(){
        this.score++;
    }

    public void incrementPlayerNo(){
        this.playerIndex++;
    }

    public int getScore(){
        return this.score;
    }


    public int getPlayerNo(){
        return this.playerIndex;
    }

    public Player[] getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers() {
        this.currentPlayers = this.handler.getNextPlayers(this);
        this.setCorrectAnswer();
    }

    public Player getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer() {

        int index = this.handler.higherScoreCalc(this);
        this.correctAnswer = this.playerArray.get(index);

    }
}
