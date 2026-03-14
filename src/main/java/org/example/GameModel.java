package org.example;
import java.util.*;


public class GameModel {
    private List<Player> players;
    private int numberOfPlayers;
    private int currRound;
    private int currEra;
    private Deck tribeDeck;
    private Map<Integer, Deck> buildingsDeck;                       //la chiave può essere 1,2,3; in base all'era ci sono degli edifici
    private List<TrackTiles> trackTiles;                            //sono le tessere al centro del tavolo, da implementare e capire come
    private GameState gameState;
    private List<Card> currUpCards;
    private List<Card> currDownCards;
    private Player currPlayer;
    private boolean newEraCardDrawn;
    private Player[] turnOrderTile;                                 //questa è la tessera all'estremo sinistro sul tavolo al centro, per l'ordine dei giocatori e la posizione dei relativi totem
    private List<Player> currOrderPlayers;                          //questa deve sempre avere l'ordine corrente dei giocatori e di quando è il loro turno, ho messo questa lista perchè il turno cambia rispetto alla loro posizione nella tessera dei totem.
                                                                    //cioè: per il posizionamento dei totem si può seguire turnOrderTile ma non è detto che chi posiziona il primo totem sia il primo a svolgere l'azione della tessera sulla quale ha posizionato il totem

    //METODI
    private void setupPlayers(List<String> nicknames) {
        for(int i=0; i<nicknames.size(); i++) {
            Player player = new Player(nicknames.get(i));
            if(i==0){
                player.addFood(2);
            }
            if(i==1 || i==2){
                player.addFood(3);
            }
            if(i==3 || i==4){
                player.addFood(4);
            }
            players.add(player);                                    //carta riepilogo da gestire: ogni giocatore ne prende una
            //GESTIRE LA SCELTA DEL COLORE DEI TOTEM
            //GESTIRE ANCHE IL POSIZIONAMENTO RANDOMICO DEI TOTEM

        }
        numberOfPlayers = players.size();
    }

    
    public GameModel(List<String> nicknames) {
        this.currRound = 0;
        this.currEra = 1;
        this.gameState = GameState.SETUP;
        this.players = new ArrayList<>();
        this.currUpCards = new ArrayList<>();
        this.currDownCards = new ArrayList<>();
        this.buildingsDeck = new HashMap<>();
        setupPlayers(nicknames);

    }
    public List<Player> getPlayers() {
        return players;
    }
}
