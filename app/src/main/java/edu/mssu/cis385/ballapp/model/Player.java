package edu.mssu.cis385.ballapp.model;

public class Player {
    private String player;
    private long updateTime;

    public Player(String player) {
        this.player = player;
    }

    //public void setPlayer (String player) {
    //    this.player = player;
    //}

    public String getText() {
        return player;
    }
}
