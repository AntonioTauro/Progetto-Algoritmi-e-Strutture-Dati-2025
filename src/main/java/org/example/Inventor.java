package org.example;



public class Inventor extends TribeCard{
    private TypeInvention invention;
    public Inventor(TypeInvention invention,String cardID, int era, int minPlayer, TypeCard type){
        super(cardID,era,minPlayer,type);
        this.invention = invention;
    }
    public TypeInvention getInvention(){
        return invention;
    }
}
