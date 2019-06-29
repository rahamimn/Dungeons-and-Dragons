import java.util.ArrayList;

public abstract class Player extends GameUnit{

	// Player additional fields
	private int experience;  // increased by killing enemies
	private int level;       // increased by gaining experience
	
	
	public Player(String name, int health, int attackPoints, int defensePoints, Position position) {
		super(name, health, attackPoints, defensePoints, position);
		this.experience = 0;
		this.level = 1;
	}
	
	public abstract boolean castSpecialAbility(ArrayList<Enemy> enemies);
	
	public abstract void gameTickUpdate();
	
	public void levelUp(){
		decExperience(50);
		this.level++;
		this.healthPool += 10 * this.getLevel();
		this.currentHealth = this.healthPool;
		incAttack(5);
		incDefense(2);
		
	}
	
	protected int getLevel(){
		return this.level;
	}
	
	protected void decExperience(int mul){
		this.experience -= mul * this.level;
	}
	
	protected void incAttack(int mul){
		this.attackPoints += mul * this.level;
	}
	
	protected void incDefense(int mul){
		this.attackPoints += mul * this.level;
	}
	
	public int getExperience(){
		return this.experience;
	}
	
	public String unitStr(){
		String base = super.unitStr();
		return base + "\tLevel: "+ this.getLevel() + "\tExperience: " + this.getExperience() + "/50";
	}

	
	public String levelUpStr(){
		return "Level up: +" + 10 * this.getLevel() + " Health, +" + 5 * this.getLevel() + " Attack, +" + 2 * this.getLevel()+ " Defense";
	}
	
}
