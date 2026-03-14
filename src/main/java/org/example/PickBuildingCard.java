package org.example;

public class PickBuildingCard extends BuildingCard{
    private TypePickBonus typeBonus;
    private int numberOfSetPaid;             //in questa variabile viene memorizzato il numero di set già pagati + quelli già presenti che non hanno diritto al bonus
    public PickBuildingCard(TypePickBonus typeBonus, String cardID, int cost, int era, int minPlayer, int points) {
        super(cardID,cost,era,minPlayer,points);
        this.typeBonus = typeBonus;
        this.numberOfSetPaid = 0;
    }
    @Override
    public int getNumSetPaid(){
        return numberOfSetPaid;
    }
    @Override
    public void setNumSetPaid(int amount){
        numberOfSetPaid=amount;
    }
    //SCRIVERE IL METODO CHE PAGA OGNI VOLTA CHE VIENE COMPLETATO UN SET NUOVO. NOTA: IL NUMERO DI SET DI CARTE È UGUALE AL MINIMO TRA LE QUANTITA' DI OGNI CHARACTER
    @Override
    public void run(){

    }
}
