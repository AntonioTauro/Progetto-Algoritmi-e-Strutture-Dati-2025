package org.example;


import java.util.List;
import java.util.ArrayList;

public class Player {
    private final String nickname;
    private int foodQuantity;
    private int points;
    private List<TribeCard> tribe;
    private List<BuildingCard> buildings;
    private TotemColor color;
    private int stars;
    public Player(String nick){
        this.nickname = nick;
        tribe = new ArrayList<>();
        buildings = new ArrayList<>();
        points = 0;
        foodQuantity = 0;
        stars = 0;
    }
    public boolean payFoodforBuildings(int amount){
        if(foodQuantity >= amount){
            foodQuantity -= amount;
            return true;
        }
        return false;                   //l'idea è che il GameModel dovrà capire quando il cibo è abbastanza per pagare, l'ho inserito per acquistare gli edifici.
                                        //non basta infatti il consumeFood perchè toglierebbe punti nel caso in cui si vuole costruire un edificio e il player
                                        //non riesce a pagare con il cibo
    }
    public int consumeFood(int amount){ //il GameModel potrà vedere il valore di ritorno e deciderà quanti punti sottrarre sulla base di esso
        if(foodQuantity >= amount){
            foodQuantity-=amount;
            return 0;
        }else{
            int missingFood = amount - this.foodQuantity;
            this.foodQuantity = 0;
            return missingFood;
        }
    }
    public void addFood(int amount){
        foodQuantity += amount;
    }
    public void modPoints(int amount){                      //se devo togliere punti, amount sarà negativo
        points += amount;
    }
    public int getPoints(){
        return points;
    }
    public int getFoodQuantity(){
        return foodQuantity;
    }
    public String getNickname(){
        return nickname;
    }
    public List<TribeCard> getTribe(){
        return tribe;
    }
    public int getStars(){
        return stars;
    }
    public List<BuildingCard> getBuildings() {
        return buildings;
    }
    public void modStars(int amount){                       // da usare ogni volta che verrà pescato uno sciamano e serve per mantenere un conteggio costante delle stelle
        stars += amount;
    }
    public TotemColor getColor(){
        return color;
    }
    public void pickCard(TribeCard card){
        if(card.getType() == TypeCard.SHAMAN){
            stars+= card.getStars();
        }
        tribe.add(card);
    }
    public void pickBuilding(BuildingCard card){
        buildings.add(card);
    }
    public int countCharactersByType(TypeCard type){
        int count = 0;
        for (TribeCard card : this.tribe) {
            if (card.getType().equals(type)) {
                count++;
            }
        }
        return count;
    }
}
