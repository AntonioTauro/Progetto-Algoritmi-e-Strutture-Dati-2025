package org.example;


public abstract class TribeCard extends Card{
    private final TypeCard type;
    public TribeCard(String cardID, int era, int minPlayer, TypeCard type){
        super(cardID, era, minPlayer);
        this.type = type;
    }
    public TypeCard getType(){
        return type;
    }
    public int getStars(){
        return 0;
    }
}
