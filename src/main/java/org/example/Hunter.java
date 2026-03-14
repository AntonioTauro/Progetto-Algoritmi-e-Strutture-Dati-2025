package org.example;

public class Hunter extends TribeCard{
    private boolean hasEffect;
    public Hunter(boolean hasEffect,String cardID, int era, int minPlayer, TypeCard type){
        super(cardID,era,minPlayer,type);
        this.hasEffect = hasEffect;
    }
    public void applyImmediateEffect(GameModel gameModel, Player player){
        if(hasEffect) {
            int count = player.countCharactersByType(TypeCard.HUNTER);
            player.addFood(count);
        }else{
            return;
        }
    }
}
