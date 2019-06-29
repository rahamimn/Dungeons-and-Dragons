import java.util.ArrayList;
import java.util.Random;

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
	public int turn(Position playerPos, Game game, RandomGenerator randomGenerator, int i) {
		char[][] board = game.getBoard();
		if (this.ticksCount == this.relocationTime){
			this.ticksCount = 0;
			System.out.println("inside turn()");
			ArrayList list = getAllFreePositionsInRange(this.getPosition(), this.relocationRange, board);
			game.setBoard(relocateRandomly(list, board));
		}
		else{
			this.ticksCount++;
			if(range(playerPos) < 2){
				System.out.println("Trap fight!");
				int userAttackPts = game.getChosen().rollAttackForCombat(randomGenerator, i);
				System.out.println("userAttackPts = " + userAttackPts);

				int enemyDefensePts = this.rollDefenseForCombat(randomGenerator, i);
				System.out.println("enemyDefensePts = " + enemyDefensePts);

				int diff = userAttackPts - enemyDefensePts;

				System.out.println("Health before = " + this.getHealth());
				if (diff > 0) {
					this.decHealth(diff); // diff is negative
					System.out.println("Health after = " + this.getHealth());
					if (this.getHealth() <= 0) {
						game.getChosen().decExperience(0-diff); // diff is negative
						char[][] newBoard = game.removeUnitFromBoard(this);
						game.setBoard(newBoard);
					}
				}

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



	//------------------- helpers
	public ArrayList<Position> getAllFreePositionsInRange(Position pos, int range, char[][] board) {
		ArrayList list = new ArrayList();
		double currRange;

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				currRange = Math.sqrt(Math.pow(pos.getX() - x, 2) + Math.pow(pos.getY() - y, 2));

				if (range - currRange >= 0) {
					Position currPosition = new Position(x, y);

					if (currPosition.inBounds() && board[x][y] == '.') {
						list.add(currPosition);
					}
				}
			}
		}
		return list;
	}

	public char[][] relocateRandomly(ArrayList<Position>list, char[][] board){

		System.out.println("Old Position = " + this.getPosition().getX() + "," + this.getPosition().getY());
		int index = getRandomNumberInRange(0, list.size()-1);
		Position currPosition = this.getPosition();
		Position newPosition = list.get(index);
		this.setPosition(newPosition);
		System.out.println("New Position = " + this.getPosition().getX() + "," + this.getPosition().getY());
		char tile = board[currPosition.getX()][currPosition.getY()];
		board[newPosition.getX()][newPosition.getY()] = tile;
		board[currPosition.getX()][currPosition.getY()] = '.';

		return board;
	}

	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	

}
