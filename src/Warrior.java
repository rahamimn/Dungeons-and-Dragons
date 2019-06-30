import java.util.ArrayList;

public class Warrior extends Player{

	private int cooldown;
	private int remaining;
	
	public Warrior(String name, int health, int attackPoints, int defensePoints, Position position, RandomGenerator srandomGenerator,  int cooldown) {
		super(name, health, attackPoints, defensePoints, position, srandomGenerator);
		this.cooldown = cooldown;
		this.remaining = 0;
	}

	// heal
	public ArrayList<Enemy> castSpecialAbility(ArrayList<Enemy> enemies){
		if (this.remaining > 0){
			ui.cantCastSpecial();
		}
		else{
			this.remaining = cooldown;
			this.currentHealth = Math.min(this.currentHealth + 2 * this.defensePoints, this.healthPool);
		}
		return new ArrayList<Enemy>();
		
	}
	
	
	
	public void levelUp(){
		super.levelUp();
		this.remaining = 0;
		this.healthPool += (5 * this.getLevel());
		this.incDefense(1);
	}

	@Override
	public void gameTickUpdate() {
		this.remaining--;
	}
	
	public String unitStr(){
		String base = super.unitStr();
		return base + "\tAbility cooldown: " + this.cooldown + "\tRemaining: " + this.remaining;
	}
}
