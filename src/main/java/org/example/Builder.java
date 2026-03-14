package org.example;

public class Builder extends TribeCard{
    private int discount;
    private int prestigePoints;
    public Builder(String cardID, int era, int minPlayer, TypeCard type, int discount, int prestigePoints){
        super(cardID,era,minPlayer,type);
        this.discount = discount;
        this.prestigePoints = prestigePoints;
    }
    public int getDiscount() {
        return discount;
    }
    public int getPrestigePoints() {
        return prestigePoints;
    }
}
