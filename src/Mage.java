import java.util.ArrayList;
import java.util.Random;

public class Mage extends Player {

	private int spellPower;  // ability scale factor
	private int manaPool;    // max of resources
	private int currentMana;
	private int cost;
	private int hitTimes;
	private int range;
	
	public Mage(String name, int health, int attackPoints, int defensePoints, Position position, 
			int spellPower, int manaPool, int cost, int hitTimes, int range) {
		super(name, health, attackPoints, defensePoints, position);
		this.spellPower = spellPower;
		this.manaPool = manaPool;
		this.currentMana = manaPool / 4;
		this.cost = cost;
		this.hitTimes = hitTimes;
		this.range = range;
	}

	
	public void levelUp(){
		super.levelUp();
		this.manaPool += 25 * this.getLevel();
		this.currentMana = Math.min(currentMana + (manaPool / 4), manaPool);
		this.spellPower += 10 * this.getLevel();
	}
	
	
	@Override
	// blizzard
	public boolean castSpecialAbility(ArrayList<Enemy> enemies) {
		if (this.currentMana < this.cost)
			return false;

		this.currentMana -= this.cost;
		int hits = 0;
		while(hits < this.hitTimes && this.hasEnemyInRange(enemies)){
			Enemy randEnemy = selectRandEnemyInRange(enemies);
			AttemptDamage(randEnemy);
			hits++;
		}
		return true;
	}

	private void AttemptDamage(Enemy enemy) {

//		System.out.println("FIGHT in Special Ability!");
//		int userAttackPts = this.rollAttackForCombat(randomGenerator, i);
//		int enemyDefensePts = enemy.rollDefenseForCombat(randomGenerator, i);
//		int diff = userAttackPts - enemyDefensePts;
//		if (diff > 0){
//			System.out.println("enemy.getHealth() = " + enemy.getHealth());
//			enemy.decHealth(diff);
//			System.out.println("now health is  = " + enemy.getHealth());
//			if (enemy.getHealth() <= 0) {
//				System.out.println("Health is over");
//				this.decExperience(0-diff); // diff is negative
//				this.board = removeUnitFromBoard(enemy);
//
//				if (enemiesCounter <=0 ){
//					isNextLevel = true;
//				}
//
//			}
//		}
//		else{
//			System.out.println("No damage happend");
//		}


	}


	private Enemy selectRandEnemyInRange(ArrayList<Enemy> enemies) {

		ArrayList<Enemy> enemiesInRange = new ArrayList<Enemy>();
		for (Enemy enemy : enemies) {
			if (range(enemy.getPosition()) < range) {
				enemiesInRange.add(enemy);
			}
		}

		int index = getRandomNumberInRange(0, enemiesInRange.size()-1);
		Enemy enemyToReturn = enemiesInRange.get(index);

		return enemyToReturn;
	}

	private boolean hasEnemyInRange(ArrayList<Enemy> enemies) {
		for (Enemy enemy : enemies) {
			if (range(enemy.getPosition()) < range){
				return true;
			}
		}
		return false;
	}


	@Override
	public void gameTickUpdate() {
		this.currentMana = Math.min(currentMana + 1, manaPool);
		
	}
	
	public String unitStr(){
		String base = super.unitStr();
		return base + "\tSpellPower: " + this.spellPower + "\tMana: " + this.currentMana + "/"+ this.manaPool;
	}
	
	
	public String levelUpStr(){
		String base = super.levelUpStr();
		return base + "\n+" + 25 * this.getLevel() + " maximum mana, +" + 10 * this.getLevel() + " spell power";
	}

	//---------------- nir: to delete
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}


}
