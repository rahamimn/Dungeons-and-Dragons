import java.util.ArrayList;
import java.util.List;

public class Rogue extends Player {

	private int cost;
	private int currentEnergy;
	private final int maxEnergy = 100;

	public Rogue(String name, int health, int attackPoints, int defensePoints, Position position, RandomGenerator srandomGenerator, int cost) {
		super(name, health, attackPoints, defensePoints, position, srandomGenerator);
		this.cost = cost;
		this.currentEnergy = maxEnergy;
	}

	public void levelUp(){
		super.levelUp();
		this.currentEnergy = maxEnergy;
		this.incAttack(3);
	}

	private ArrayList<Enemy> getEnemiesInRange(ArrayList<Enemy> enemies) {
		ArrayList<Enemy> toret = new ArrayList<Enemy>();
		for(Enemy enemy : enemies){
			if(this.range(enemy.getPosition()) < 2)
				toret.add(enemy);
		}
		return toret;
	}

	
	@Override
	public ArrayList<Enemy> castSpecialAbility(ArrayList<Enemy> enemies) {
		ArrayList<Enemy> deadEnemies = new ArrayList<Enemy>();
		if (this.currentEnergy < this.cost){
			ui.cantCastSpecial();
			return deadEnemies;
		}
		this.currentEnergy -= this.cost;
		ArrayList<Enemy> enemiesInRange = this.getEnemiesInRange(enemies);
		for (Enemy enemy : enemiesInRange){
			boolean enemyDead = this.attemptDamage(enemy);
			deadEnemies.add(enemy);
		}
		return deadEnemies;
	}


	private boolean attemptDamage(Enemy enemy){
		int enemyDefensePts = enemy.rollDefenseForCombat();
		int diff = this.attackPoints - enemyDefensePts;
		if (diff > 0) {
			enemy.decHealth(diff);
			//defender is dead!
			if (enemy.getHealth() <= 0) {
				this.incExperience(enemy.getExperience());
				ui.printSpecial(this,  enemy, this.attackPoints, enemyDefensePts, diff);
				return true;
			}
			return false;
		} 
		ui.printSpecial(this, enemy, this.attackPoints, enemyDefensePts, 0);
		return false;
	}

	@Override
	public void gameTickUpdate() {
		this.currentEnergy = Math.min(100, this.currentEnergy + 10);

	}

	public String unitStr(){
		String base = super.unitStr();
		return base + "\tEnergy: " + this.currentEnergy + "/" + this.maxEnergy;
	}
}
