
public abstract class Enemy extends GameUnit {

	private int experience;
	private char tile;

	public Enemy(String name, int health, int attackPoints, int defensePoints, Position position,
			RandomGenerator randomGenerator, int experience, char tile) {
		super(name, health, attackPoints, defensePoints, position, randomGenerator);
		this.experience = experience;
		this.tile = tile;
	}

	// stay - 0, left - 1, right - 2, up - 3, down - 4
	public abstract Object[] turn(Player player, char[][] board);

	public boolean meleeCombatEnemy(GameUnit defender) {
		int enemyAttackPts = this.rollAttackForCombat();
		int playerDefensePts = defender.rollDefenseForCombat();
		int diff = enemyAttackPts - playerDefensePts;
		if (diff > 0) {
			defender.decHealth(diff);
			ui.printCombat(this, defender, enemyAttackPts, playerDefensePts, diff);
			if (defender.getHealth() <= 0) {
				return true;
			}
			return false;
		}
		ui.printCombat(this, defender, enemyAttackPts, playerDefensePts, 0);
		return false;
	}
	
	public char getTile(){
		return this.tile;
	}
	
	public int getExperience(){
		return this.experience;
	}

}
