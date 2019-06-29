import java.util.Random;

public class Monster extends Enemy {

    private int visionRange;

    public Monster(String name, int health, int attackPoints, int defensePoints, Position position, int experience,
                   char tile, int visionRange) {
        super(name, health, attackPoints, defensePoints, position, experience, tile);
        this.visionRange = visionRange;
    }


    // stay - 0, left - 1, right - 2, up - 3, down - 4
    @Override
    public int turn(Position playerPos, Game game, RandomGenerator randomGenerator, int i) {

        if (range(playerPos) < this.visionRange) {

            int dx = this.getPosition().getX() - playerPos.getX();
            int dy = this.getPosition().getY() - playerPos.getY();

            if (Math.abs(dx) > Math.abs(dy)) {
                if (dx > 0) {
             //       System.out.println("Enemy move = 1");
                    return 1;
                } else {
              //      System.out.println("Enemy move = 2");
                    return 2;
                }

            } else {
                if (dy > 0) {
             //       System.out.println("Enemy move = 3");
                    return 3;
                } else {
              //      System.out.println("Enemy move = 4");
                    return 4;
                }
            }
        } else {

            return randomGenerator.nextInt(4); // NIR: TODO; add 5th movement = stay

        }

    }


}

