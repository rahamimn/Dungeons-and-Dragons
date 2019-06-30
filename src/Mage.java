import java.util.ArrayList;
import java.util.Random;

public class Mage extends Player {

	private int spellPower;  // ability scale factor
	private int manaPool;    // max of resources
	private int currentMana;
	private int cost;
	private int hitTimes;
	private int range;
	
	public Mage(String name, int health, int attackPoints, int defensePoints, Position position, RandomGenerator srandomGenerator,
			int spellPower, int manaPool, int cost, int hitTimes, int range) {
		super(name, health, attackPoints, defensePoints, position, srandomGenerator);
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
	public ArrayList<Enemy>  castSpecialAbility(ArrayList<Enemy> enemies) {
		ArrayList<Enemy> deadEnemies = new ArrayList<Enemy>();
		if (this.currentMana < this.cost){
			ui.cantCastSpecial();
			return deadEnemies;
		}
		this.currentMana -= this.cost;
		int hits = 0;
		while(hits < this.hitTimes && this.hasEnemyInRange(enemies)){
			Enemy randEnemy = selectRandEnemyInRange(enemies);
			boolean isDead = AttemptDamage(randEnemy);
			if(isDead){
				enemies.remove(randEnemy);
				deadEnemies.add(randEnemy);
			}
			hits++;
		}
		return deadEnemies;
	}

	private boolean AttemptDamage(Enemy enemy) {
		int enemyDefensePts = enemy.rollDefenseForCombat();
		int diff = this.spellPower - enemyDefensePts;
		if (diff > 0) {
			enemy.decHealth(diff);
			//defender is dead!
			if (enemy.getHealth() <= 0) {
				this.incExperience(enemy.getExperience());
				ui.printSpecial(this,  enemy, this.spellPower, enemyDefensePts, diff);
				return true;
			}
			return false;
		} 
		ui.printSpecial(this, enemy, this.spellPower, enemyDefensePts, 0);
		return false;
	}


	private Enemy selectRandEnemyInRange(ArrayList<Enemy> enemies) {

		ArrayList<Enemy> enemiesInRange = new ArrayList<Enemy>();
		for (Enemy enemy : enemies) {
			if (range(enemy.getPosition()) < range) {
				enemiesInRange.add(enemy);
			}
		}

		int index = this.randomGenerator.nextInt(enemiesInRange.size()-1);
		return enemiesInRange.get(index);

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

//	//---------------- nir: to delete
//	private static int getRandomNumberInRange(int min, int max) {
//
//		if (min >= max) {
//			throw new IllegalArgumentException("max must be greater than min");
//		}
//
//		Random r = new Random();
//		return r.nextInt((max - min) + 1) + min;
//	}


}
