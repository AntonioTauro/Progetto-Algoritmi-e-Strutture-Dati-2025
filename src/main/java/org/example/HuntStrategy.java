package org.example;

public class HuntStrategy implements EventStrategy{
    private int pointsPerHunter;
    public HuntStrategy(int pointsPerHunter) {
        this.pointsPerHunter = pointsPerHunter;
    }
    //LA LOGICA SEMBRA CORRETTA, DA RIVEDERE SOLO COME OTTENERE pointsPerHunter
    @Override
    public void resolve(GameModel model) {
        for(Player p: model.getPlayers()){
            int hunters = p.countCharactersByType(TypeCard.HUNTER);
            p.addFood(hunters);
            p.modPoints(pointsPerHunter*hunters);
            for(BuildingCard bc: p.getBuildings()) {
                bc.bonusHuntingEvent(p);
            }
        }
    }
}
