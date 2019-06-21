
public abstract class Enemy extends GameUnit {

    private int experience;
    private char tile;

    public Enemy(String name, int health, int attackPoints, int defensePoints, Position position, int experience, char tile) {
        super(name, health, attackPoints, defensePoints, position);
        this.experience = experience;
        this.tile = tile;
    }

    // stay - 0, left - 1, right - 2, up - 3, down - 4
    public abstract int turn(Position playerPos);

}
