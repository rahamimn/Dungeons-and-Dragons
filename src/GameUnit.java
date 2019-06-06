abstract class GameUnit {

    String name;
    Health health;
    int attackPoints;
    int defensePoints;
    Position position;

    public GameUnit(String name, Health health, Integer attackPoints, Integer defensePoints, Position position) {
        this.name = name;
        this.health = health;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.position = position;
    }
}
