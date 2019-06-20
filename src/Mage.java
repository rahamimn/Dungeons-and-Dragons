
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
	public boolean castSpecialAbility() {
		if (this.currentMana < this.cost)
			return false;
		this.currentMana -= this.cost;
		int hits = 0;
		while(hits < this.hitTimes || this.hasEnemyInRange()){
			Enemy randEnemy = selectRandEnemyInRange();
			AttemptDamage(randEnemy, this.spellPower);
			hits++;
		}
		return true;
	}

	private void AttemptDamage(Enemy randEnemy, int spellPower2) {
		// TODO Auto-generated method stub
		
	}


	private Enemy selectRandEnemyInRange() {
		// TODO Auto-generated method stub
		return null;
	}


	private boolean hasEnemyInRange() {
		// TODO Auto-generated method stub
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

}
