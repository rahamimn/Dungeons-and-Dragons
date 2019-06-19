public abstract class GameUnit {
	
	private String name;
	protected int healthPool;
	protected int currentHealth;
	protected int attackPoints;
	protected int defensePoints;
	private Position position;
	
	 public GameUnit(String name, int health, int attackPoints, int defensePoints, Position position) {
	        this.name = name;
	        this.healthPool = health;
	        this.currentHealth = health;
	        this.attackPoints = attackPoints;
	        this.defensePoints = defensePoints;
	        this.position = position;
	    }
	 
	 
	 public double range(Position other){
		 return Math.sqrt(Math.pow(this.position.getX() - other.getX(), 2) + Math.pow(this.position.getY() - other.getY(), 2));
	 }
	 
	 public Position getPosition(){
		 return this.position;
	 }
	 
	 public int rollAttackForCombat(){
		 //need to figure out hoe to send the random genertator
		 return 0;
	 }
	 
	 public int rollDefenseForCombat(){
		 //need to figure out hoe to send the random genertator
		 return 0;
	 }
	 

}
