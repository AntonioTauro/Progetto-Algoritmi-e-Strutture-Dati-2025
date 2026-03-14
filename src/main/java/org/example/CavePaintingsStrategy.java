package org.example;

import java.util.HashMap;
import java.util.Map;

public class CavePaintingsStrategy implements EventStrategy {
    int minArtistForBonus;
    int bonus;
    int malus;
    public CavePaintingsStrategy(int minArtistForBonus, int bonus, int malus) {
        this.minArtistForBonus = minArtistForBonus;
        this.bonus = bonus;
        this.malus = malus;
    }
    //LA LOGICA SEMBRA CORRETTA, DA RIVEDERE SOLO COME OTTENERE minArtistForBonus e bonus e malus
    @Override
    public void resolve(GameModel model) {
        for(Player p : model.getPlayers()){
            int artist = p.countCharactersByType(TypeCard.ARTIST);
            if(artist>=minArtistForBonus){
                p.modPoints(bonus*artist);
            }else{
                p.modPoints(malus);                                     //NOTA: malus deve essere un numero negativo, altrimenti aggiunge punti
            }
            for(BuildingCard bc: p.getBuildings()){
                bc.bonusCavePaintingEvent(p);
            }
        }
    }
}
