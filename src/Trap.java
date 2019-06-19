
public class Trap extends Enemy {

	
	private int relocationRange;
	private int relocationTime;
	private int visibilityTime;
	private int ticksCount;
	
	public Trap(String name, int health, int attackPoints, int defensePoints, Position position, int experience,
			char tile, int relocationRange, int relocationTime, int visibilityTime) {
		super(name, health, attackPoints, defensePoints, position, experience, tile);
		this.relocationRange = relocationRange;
		this.relocationTime = relocationTime;
		this.visibilityTime = visibilityTime;
		this.ticksCount = 0;
	}

	@Override
	public int turn(Position playerPos) {
		if (this.ticksCount == this.relocationTime){
			this.ticksCount = 0;
			//find all free position within relocation range
			//select random set trap
		}
		else{
			this.ticksCount++;
			if(range(playerPos) < 2){
				//engage in melee combat with player
			}
		}
		
		if(this.ticksCount < this.visibilityTime){
			//trap visible
		}
		else{
			//trap invisible
		}
		//delete after
		return -1;
	}
	

}
