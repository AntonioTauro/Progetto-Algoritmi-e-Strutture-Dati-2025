package org.example;

public abstract class Card {
    private final String cardID;
    private final int era;
    private final int minPlayer;
    public Card(String cardID, int era, int minPlayer) {
        this.cardID = cardID;
        this.era = era;
        this.minPlayer = minPlayer;
    }
    public String getId(){
        return cardID;
    }
    public int getEra(){
        return era;
    }
    public int getNumPlayer(){
        return minPlayer;
    }
}
