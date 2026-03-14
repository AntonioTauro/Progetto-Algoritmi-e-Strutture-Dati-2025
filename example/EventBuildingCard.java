package org.example;

public class EventBuildingCard extends BuildingCard {
    private TypeEventBonus typeBonus;
    private TypeCard character;
    public EventBuildingCard(TypeEventBonus typeBonus,String cardID, int cost,int era,int minPlayer, int points, TypeCard character) {
        super(cardID,cost,era,minPlayer,points);
        this.typeBonus = typeBonus;
        this.character = character;
    }
    public TypeEventBonus getTypeBonus() {
        return typeBonus;
    }
    public TypeCard getCharacter() {
        return character;
    }
    @Override
    public int getSustenanceDiscount(Player p) {
        return typeBonus.getDiscount(p, this.character);
    }
    @Override
    public boolean hasShamanicProtection(){
        return typeBonus.preventsShamanicLoss();
    }
    @Override
    public int getExtraStars() {
        return typeBonus.getExtraStars();
    }
    @Override
    public void bonusHuntingEvent(Player p){
        typeBonus.applyBonus(p);
    }
    @Override
    public void bonusCavePaintingEvent(Player p){
        typeBonus.applyFoodBonus(p);
    }
    @Override
    public boolean bonusDoublePointsShamanicEvent(){
        return typeBonus.shamanicDoubler();
    }
    @Override
    public void run() {
    }
}
