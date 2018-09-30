package com.example.carrieboardman.guessinggame;

import android.util.Log;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class UnitTests {

    @Test
    public void fppgComparisonTest(){
        PlayerArrayHandler handler = new PlayerArrayHandler();

        Player[] players= currentPlayerGenerator(3.43432423423, 3.42654656532);

        assertThat(handler.higherScoreCalc(players), is(0));
    }

    @Test
    public void lastTurnTest(){

        Game newGame = new Game();

        for (int i = 0; i < 10; i++){
            assertThat(newGame.isLastTurn(), is(false));
            newGame.incrementTurns();
        }
        assertThat(newGame.isLastTurn(), is(true));
    }

    @Test
    public void correctWinningPlayer(){

        Game newGame = new Game();

        Player[] players= currentPlayerGenerator(4.3243242, 2.34235312);

        newGame.setCurrentPlayers(players);

        assertThat(newGame.findCorrectAnswer(),is(players[0]));
    }


    private Player[] currentPlayerGenerator (double score_higher, double score_lower){

        Player p1 = new Player();
        p1.setFppg(score_higher);
        Player p2 = new Player();
        p2.setFppg(score_lower);

        Player[] players= new Player[2]; players[0] = p1; players[1] = p2;

        return players;
    }

}