package org.example;

public enum TypeEventBonus {
    SUSTENANCE_BONUS {
        @Override
        public int getDiscount(Player p, TypeCard character){
            return p.countCharactersByType(character);
        }
    },
    SHAMANIC_PROTECTION_BONUS {
        @Override
        public boolean preventsShamanicLoss(){
            return true;
        }
    },
    SHAMANIC_STAR_BONUS{
        @Override
        public int getExtraStars(){
            return 3;
        }
    },
    HUNTING_EVENT_BONUS {
        @Override
        public void applyBonus(Player p){
            int hunters = p.countCharactersByType(TypeCard.HUNTER);
            p.addFood(hunters);
            p.modPoints(hunters);
        }
    },
    CAVE_PAINTING_BONUS {
        @Override
        public void applyFoodBonus(Player p){
            int artists = p.countCharactersByType(TypeCard.ARTIST);
            p.addFood(artists);
        }
    },
    DOUBLE_REWARD_SHAMANIC_EVENT_BONUS{
        @Override
        public boolean shamanicDoubler(){return true;}
    },
    NONE;
    public int getDiscount(Player p, TypeCard character){
        return 0;
    }
    public int getExtraStars(){
        return 0;
    }
    public void applyBonus(Player p){}
    public void applyFoodBonus(Player p){}
    public boolean preventsShamanicLoss(){
        return false;
    }
    public boolean shamanicDoubler(){return false;}
}
