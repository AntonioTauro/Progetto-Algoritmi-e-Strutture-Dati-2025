package org.example;

public class EventCard extends Card{
    private EventStrategy effect;
    private boolean endEvent;
    public EventCard(String cardID, int era, int minPlayer, EventStrategy effect, boolean e ){
        super(cardID, era, minPlayer);
        this.effect = effect;
        this.endEvent = e;
    }
    public void resolve(GameModel model){
        effect.resolve(model);
    }
}
