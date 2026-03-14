package org.example;

public abstract class BuildingCard extends Card {
    private int cost;
    private int points;
    public BuildingCard(String cardID, int cost,int era,int minPlayer, int points){
        super(cardID,era,minPlayer);
        this.cost = cost;
        this.points = points;
    }
    public int getCost() {
        return cost;
    }
    public int getPoints() {
        return points;
    }

    //QUESTI METODI SERVONO DA GANCIO PER SFRUTTARE IL POLIMORFISMO NELLE SOTTOCLASSI

    public int getSustenanceDiscount(Player p){return 0;}
    public boolean hasShamanicProtection(){return false;}
    public int getExtraStars(){return 0;}
    public void bonusHuntingEvent(Player p){}
    public void bonusCavePaintingEvent(Player p){}
    public boolean bonusDoublePointsShamanicEvent(){return false;}
    public boolean doublePointsForEachBuilder(){return false;}
    public boolean bonus25Points(){return false;}
    public int bonusPointsPerType(Player p){return 0;}
    public void bonusPointsPerSet(Player p){}
    public int getNumSetPaid(){return 0;}
    public void setNumSetPaid(int amount){}

    public abstract void run();
}
