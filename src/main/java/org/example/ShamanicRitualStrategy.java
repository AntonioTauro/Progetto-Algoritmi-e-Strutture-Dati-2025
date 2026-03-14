package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShamanicRitualStrategy implements EventStrategy {
    private int winPoints;
    private int losePoints;

    public ShamanicRitualStrategy(int winPoints, int losePoints) {
        this.winPoints = winPoints;
        this.losePoints = losePoints;
    }
    //LA LOGICA SEMBRA CORRETTA, DA RIVEDERE SOLO COME OTTENERE winPoints e losePoints
    @Override
    public void resolve(GameModel model){
        List<Player> players = model.getPlayers();
        Map<Player,Integer> playerStars = new HashMap<>();
        for(Player p : players){
            int stars = p.getStars();
            for(BuildingCard bc : p.getBuildings()){
                stars += bc.getExtraStars();
            }
            playerStars.put(p,stars);
        }
        int max = playerStars.values().stream().mapToInt(Integer::intValue).max().getAsInt();
        int min = playerStars.values().stream().mapToInt(Integer::intValue).min().getAsInt();
        int winnersCount = (int) playerStars.values().stream().filter(s -> s==max).count();
        for(Player p : model.getPlayers()){
            int stars = playerStars.get(p);
            boolean doublePoints= p.getBuildings().stream().anyMatch(b-> b.bonusDoublePointsShamanicEvent());
            boolean protection = p.getBuildings().stream().anyMatch(b-> b.hasShamanicProtection());

            if(stars == max){
                if(doublePoints && winnersCount == 1){
                    p.modPoints(2*winPoints);
                }else{
                    p.modPoints(winPoints);
                }
            }
            if(stars == min){
                if(!protection){
                    p.modPoints(losePoints);
                }
            }
        }
    }
}
