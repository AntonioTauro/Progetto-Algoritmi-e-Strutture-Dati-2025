package org.example;


import java.util.*;

public enum TypeFinalGameBonus {
    DOUBLE_BUILDER_POINTS_BONUS{
        @Override
        public boolean builderDoubler(){
            return true;
        }
    },
    TWENTYFIVE_POINTS_BONUS{
        @Override
        public boolean points25bonus(){
            return true;
        }
    },
    POINTS_PER_INDICATED_TYPE_BONUS{
        @Override
        public int pointsPerType(int amount, TypeCard character, Player p){
            int number = p.countCharactersByType(character);
            return amount*number;
        }
    },
    POINTS_FOR_EACH_SET_BONUS{
        //LA MIA IDEA È CONTARE IL NUMERO DI CARTE PER OGNI PERSONAGGIO, TROVARE IL MINIMO E QUELLO SARA' IL NUMERO DI SET
        //ES: 4 ARTISTI, 3 SCIAMANI, 4 HUNTER, 5 CACCIATORI, 3 INVENTORI, 4 COSTRUTTORI; SIGNIFICA CHE POSSO COMPORRE 3 SET, CIOÈ IL MINIMO TRA QUESTI NUMERI
        //L'IDEA SI BASA SULL'ASSUNZIONE CHE NON POSSO RIUTILIZZARE LE STESSE CARTE PER FARE PIU' SET, OGNI CARTA PUO' APPARTENERE AD UN SOLO SET
        //IL METODO È int E RITORNA IL NUMERO DI PUNTI BONUS 6 * NUMERO DI SET
        @Override
        public int bonusPointsPerSet(Player p){
            Map<TypeCard, Integer> characterPresence = new HashMap<>();
            for(TypeCard type : TypeCard.values()){
                characterPresence.put(type, p.countCharactersByType(type));
            }
            //numero minimo di set
            int min = characterPresence.values().stream().mapToInt(Integer::intValue).min().getAsInt();
            return 6*min;
        }
    },
    NONE;
    public int bonusPointsPerSet(Player p){return 0;}
    public int pointsPerType(int amount, TypeCard character, Player p){return 0;}
    public boolean builderDoubler(){return false;}
    public boolean points25bonus(){return false;}
}
