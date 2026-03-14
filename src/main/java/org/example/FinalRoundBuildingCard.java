package org.example;

public class FinalRoundBuildingCard extends BuildingCard{
    private TypeFinalRoundBonus typeBonus;
    public FinalRoundBuildingCard(TypeFinalRoundBonus typeBonus,String cardID, int cost,int era,int minPlayer, int points) {
        super(cardID,cost,era,minPlayer,points);
        this.typeBonus = typeBonus;
    }

    @Override
    public void run(){

    }
}
