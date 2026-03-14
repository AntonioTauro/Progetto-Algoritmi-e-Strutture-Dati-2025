package org.example;
import java.util.*;

public class SustenanceStrategy implements EventStrategy {
    private int penaltyUnfed;
    public SustenanceStrategy(int penaltyUnfed) {
        this.penaltyUnfed = penaltyUnfed;
    }
    //LA LOGICA SEMBRA CORRETTA, DA RIVEDERE SOLO COME OTTENERE penaltyUnfed
    @Override
    public void resolve(GameModel model) {
        for(Player p : model.getPlayers()){
            int tribeSize = p.getTribe().size();
            int discount = p.countCharactersByType(TypeCard.GATHERER);
            int buildingDiscount = p.getBuildings().stream().mapToInt(b->b.getSustenanceDiscount(p)).sum();
            int foodToPay = tribeSize - 3*discount - buildingDiscount;
            if(foodToPay < 0){
                foodToPay = 0;
            }
            int missingfood = p.consumeFood(foodToPay);
            if(missingfood > 0){
                p.modPoints(-(this.penaltyUnfed*missingfood));
            }
        }
    }
}
