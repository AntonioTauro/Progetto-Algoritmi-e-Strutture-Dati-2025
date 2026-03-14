package org.example;

public class Shaman extends TribeCard{
    private int stars;
    public Shaman(int stars,String cardID, int era, int minPlayer, TypeCard type){
        super(cardID,era,minPlayer,type);
        this.stars = stars;
    }
    @Override
    public int getStars(){
        return stars;
    }
}
