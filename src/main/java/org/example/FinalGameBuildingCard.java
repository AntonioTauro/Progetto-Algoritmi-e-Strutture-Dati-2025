package org.example;

public class FinalGameBuildingCard extends BuildingCard {
    private TypeFinalGameBonus typeBonus;
    private TypeCard character;
    private int amount;
    public FinalGameBuildingCard(TypeFinalGameBonus typeBonus,String cardID, int cost,int era,int minPlayer, int points, TypeCard character, int amount) {
        super(cardID,cost,era,minPlayer,points);
        this.typeBonus = typeBonus;
        this.character = character;                     //andrà impostato a null per tutti tranne che per il metodo bonusPointsPerType
        this.amount = amount;                           //andrà impostato a 0 per tutti tranne che per il metodo bonusPointsPerType
    }
    public TypeFinalGameBonus getTypeBonus() {
        return typeBonus;
    }

    //METODI UTILI DA INVOCARE QUANDO VIENE CALCOLATO IL PUNTEGGIO FINALE
    @Override
    public boolean doublePointsForEachBuilder(){
        return typeBonus.builderDoubler();                  //RESTITUISCE VERO SOLO SE L'EDIFICIO CON QUESTO BONUS È POSSEDUTO
    }
    @Override
    public boolean bonus25Points(){
        return typeBonus.points25bonus();
    }
    @Override
    public int bonusPointsPerType(Player p){
        return typeBonus.pointsPerType(this.amount,this.character,p);
    }
    @Override
    public void bonusPointsPerSet(Player p){
        p.modPoints(typeBonus.bonusPointsPerSet(p));
    }
    @Override
    public void run(){

    }
}
