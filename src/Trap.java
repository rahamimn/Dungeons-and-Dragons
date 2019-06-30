import java.util.ArrayList;
import java.util.Random;

public class Trap extends Enemy {

	private int relocationRange;
	private int relocationTime;
	private int visibilityTime;
	private int ticksCount;
	private boolean visible=true;

	public Trap(String name, int health, int attackPoints, int defensePoints, Position position,
			RandomGenerator randomGenerator, int experience, char tile, int relocationRange, int relocationTime,
			int visibilityTime) {
		super(name, health, attackPoints, defensePoints, position, randomGenerator, experience, tile);
		this.relocationRange = relocationRange;
		this.relocationTime = relocationTime;
		this.visibilityTime = visibilityTime;
		this.ticksCount = 0;
	}

	@Override
	public Object[] turn(Player player, char[][] board) {
		
		Boolean bol = new Boolean(false);
		Position newPosition = this.getPosition();
		if (this.ticksCount == this.relocationTime) {
			this.ticksCount = 0;
			ArrayList<Position> freePositions = getAllFreePositionsInRange(board);
			if(!freePositions.isEmpty()){
				int index = this.randomGenerator.nextInt(freePositions.size() - 1);
				newPosition = freePositions.get(index);
			}
			
		} 
		else {
			this.ticksCount++;
			if (range(player.getPosition()) < 2) {
				boolean userDead = this.meleeCombatEnemy(player);
				bol = new Boolean(userDead);
			}
		}

		if (this.ticksCount < this.visibilityTime) {
			this.visible = true;
		} else {
			this.visible = false;
		}
		Object[] toret = {bol, newPosition};
		return toret;
	}

	
	// ------------------- helpers
	public ArrayList<Position> getAllFreePositionsInRange(char[][] board) {
		ArrayList<Position> list = new ArrayList<Position>();
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				if (this.range(new Position(x, y)) <= this.relocationRange && board[x][y] == '.') 
						list.add(new Position(x, y));
				}
			}
		return list;
		}
	

//	public char[][] relocateRandomly(ArrayList<Position> list, char[][] board) {
//
//		
//		
//		return board;
//	}

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
