package org.example;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards;                          //non deve essere formato da

    public Deck() {
        this.cards = new Stack<>();
    }
    public void add(Card card){                         //metodo per aggiungere una carta specifica, utile per il setup del game che aggiungerà carte in base ai giocatori
        this.cards.push(card);
    }
    public void addAll(List<Card> cards){               //metodo per aggiungere un mazzo di carte, utile per mettere i mazzi della seconda era e terza sotto quello della prima
        cards.addAll(cards);
    }
    public void shuffle(){
        Collections.shuffle(cards);
    }
    public Card draw(){
        if(cards.isEmpty()){
            return null;                                //il model dovrà capire che se restituisce null, allora la partita è quasi finita. Questo null va gestito
        }
        return cards.pop();
    }


}
