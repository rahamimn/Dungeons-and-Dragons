public abstract class GameUnit {

    private String name;
    protected int healthPool;
    protected int currentHealth;
    protected int attackPoints;
    protected int defensePoints;
    private Position position;


    public GameUnit(String name, int health, int attackPoints, int defensePoints, Position position) {
        this.name = name;
        this.healthPool = health;
        this.currentHealth = health;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
        this.position = position;
    }

    public double range(Position other) {
        return Math.sqrt(Math.pow(this.position.getX() - other.getX(), 2) + Math.pow(this.position.getY() - other.getY(), 2));
    }

    public Position getPosition() {
        return this.position;
    }

    public void printPosition(String message) {
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        System.out.println("> " + message + " < : (" + x + "," + y + ")");
    }


    public void setPosition(Position newPos) {
        this.position = new Position(0, 0);
        this.position.setX(newPos.getX());
        this.position.setY(newPos.getY());
    }

    public int rollAttackForCombat() {
        //need to figure out hoe to send the random genertator
        return 0;
    }

    public int rollDefenseForCombat() {
        //need to figure out hoe to send the random genertator
        return 0;
    }

    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.currentHealth;
    }

    public int getAttack() {
        return this.attackPoints;
    }

    public int getDefense() {
        return this.defensePoints;
    }

    public String unitStr() {
        return this.getName() + "\tHealth: " + this.getHealth() + "\tAttack damage: " + this.getAttack()
                + "\tDefense: " + this.getDefense();
    }


}
