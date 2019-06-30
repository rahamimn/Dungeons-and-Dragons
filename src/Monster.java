import java.util.Random;

public class Monster extends Enemy {

	private int visionRange;

	public Monster(String name, int health, int attackPoints, int defensePoints, Position position,
			RandomGenerator randomGenerator, int experience, char tile, int visionRange) {
		super(name, health, attackPoints, defensePoints, position, randomGenerator, experience, tile);
		this.visionRange = visionRange;
	}

	// stay - 0, left - 1, right - 2, up - 3, down - 4
	@Override
	public Object[] turn(Player player, char[][] board) {

		int move;
		Position newPos = null;
		Boolean bol = new Boolean(false);
		Position playerPos = player.getPosition();
		if (range(playerPos) < this.visionRange) {

			int dx = this.getPosition().getX() - playerPos.getX();
			int dy = this.getPosition().getY() - playerPos.getY();

			if (Math.abs(dx) > Math.abs(dy)) {
				if (dx > 0)
					move = 1;
				else
					move = 2;
			} 
			else {
				if (dy > 0)
					move = 3;
				else
					move = 4;
			}
		} 
		else {
			move = randomGenerator.nextInt(5);
		}

		switch (move) {
		case 0:
			newPos = this.getPosition();
			break;
		case 1:
			newPos = this.getPosition().getLeft();
			break;
		case 2:
			newPos = this.getPosition().getRight();
			break;
		case 3:
			newPos = this.getPosition().getUp();
			break;
		case 4:
			newPos = this.getPosition().getDown();
			break;
		}
		switch(board[newPos.getX()][newPos.getY()]){
		// monster can move
		case '.':
			break;
		// melee combat
		case '@':
			boolean userDead = this.meleeCombatEnemy(player);
			bol = new Boolean(userDead);
			break;
		// monster can't move
		default:
			newPos = this.getPosition();
			break;
		}

		Object[] toret = {bol, newPos};
		return toret;

	}

}
