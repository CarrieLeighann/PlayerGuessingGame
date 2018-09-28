package com.example.carrieboardman.guessinggame;

import java.net.URL;

public class Player {

    private String playerName;
    private Double fppg;
    private String image;

    public Player() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Double getFppg() {
        return fppg;
    }

    public void setFppg(Double fppg) {
        this.fppg = fppg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
