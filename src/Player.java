abstract class Player extends GameUnit {


    int experience;
    int level;
    Attack attack;
    Defense defense;

    public Player() {
        super();
        this.experience = 0;
        this.level = 1;
        this.attack = new Attack();
        this.defense = new Defense();
    }

    abstract void useSpecialAbility();

    void levelUp(){

    }

}
