import java.util.List;

public class Rogue extends Player{

	private int cost;
	private int currentEnergy;
	private final int maxEnergy = 100;
	
	public Rogue(String name, int health, int attackPoints, int defensePoints, Position position, int cost) {
		super(name, health, attackPoints, defensePoints, position);
		this.cost = cost;
		this.currentEnergy = maxEnergy;
	}

	
	public void levelUp(){
		super.levelUp();
		this.currentEnergy = maxEnergy;
		this.incAttack(3);
	}
	
	@Override
	public boolean castSpecialAbility() {
		if (this.currentEnergy < this.cost)
			return false;
		this.currentEnergy -= this.cost;
		List<Enemy> enemiesInRange = this.getEnemiesInRange(2);
		for (Enemy enemy : enemiesInRange){
			AttemptDamage(enemy, this.attackPoints);
		}
		return true;
	}

	private void AttemptDamage(Enemy enemy, int attackPoints) {
		// TODO Auto-generated method stub
		
	}


	private List<Enemy> getEnemiesInRange(int i) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void gameTickUpdate() {
		this.currentEnergy = Math.min(100, this.currentEnergy + 10);
		
	}

}
