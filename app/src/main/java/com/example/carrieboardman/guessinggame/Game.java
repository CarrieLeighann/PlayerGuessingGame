package com.example.carrieboardman.guessinggame;

import java.util.ArrayList;

public class Game {


    static final int GAME_ITERATIONS = 10;
    static final int PLAYERS_SHOWN = 2;

    private int playerIndex;
    private int score;
    private int turns;

    private Player[] currentPlayers;
    private Player correctAnswer;
    private ArrayList<Player> playerArray;

    private PlayerArrayHandler handler;

    public Game( ) {
        this.playerIndex = 0;

        this.score = 0;

        handler = new PlayerArrayHandler();

       this.turns = 0;
    }


    public ArrayList<Player> getPlayerArray() {
        return playerArray;
    }

    public void setPlayerArray(ArrayList<Player> playerArray) {
        this.playerArray = playerArray;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void incrementTurns(){
        this.turns++;
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


    //call the handler to find the next players in the list and set the correct answer
    public void getNextPlayers(){
        setCurrentPlayers(this.handler.getNextPlayers(this));
        setCorrectAnswer(this.findCorrectAnswer());
    }

    public void setCurrentPlayers(Player [] currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    //call the handler to find out which player has the higher score
    // and is the correct answer in this turn
    protected Player findCorrectAnswer() {
        int index = this.handler.higherScoreCalc(this.currentPlayers);
        return this.getCurrentPlayers()[index];
    }

    public Player[] getCurrentPlayers() {
        return currentPlayers;
    }

    public Player getCorrectAnswer() {
        return correctAnswer;
    }

    private void setCorrectAnswer(Player correctAnswer){
        this.correctAnswer = correctAnswer;
    }

    public Boolean isLastTurn(){
        //THIS CODE WOULD BE IF THERE WAS NO SET LIMIT TO THE NUMBER OF TURNS
        //THE PLAYER COULD HAVE AND IT WAS INSTEAD BASED ON DATA SIZE
     /*   int arraySize = this.getPlayerArray().size();
        if (arraySize % PLAYERS_SHOWN == 0){
            return this.getPlayerNo() == arraySize;
        } else {
            return this.getPlayerNo() + 1 == arraySize;
        }*/

        return turns == GAME_ITERATIONS;
    }
}
