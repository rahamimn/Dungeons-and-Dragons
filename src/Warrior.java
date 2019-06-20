
public class Warrior extends Player{

	private int cooldown;
	private int remaining;
	
	public Warrior(String name, int health, int attackPoints, int defensePoints, Position position, int cooldown) {
		super(name, health, attackPoints, defensePoints, position);
		this.cooldown = cooldown;
		this.remaining = cooldown;
	}

	// heal
	public boolean castSpecialAbility(){
		if (this.remaining > 0){
			return false;
		}
		int inc = Math.min(this.currentHealth + 2 * this.defensePoints, this.healthPool);
		this.currentHealth += inc;
		return true;
	}
	
	
	
	public void levelUp(){
		super.levelUp();
		this.remaining = 0;
		this.healthPool += (5 * this.getLevel());
		this.incDefense(1);
	}

	@Override
	public void gameTickUpdate() {
		if(this.remaining == 0)
			this.remaining = cooldown;
		else
			this.remaining--;
	}
	
	public String playerStr(){
		String base = super.unitStr();
		return base + "\tAbility cooldown: " + this.cooldown + "\tRemaining: " + this.remaining;
	}
}
