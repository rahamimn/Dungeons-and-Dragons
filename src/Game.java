import java.util.ArrayList;

public class Game {

	private ArrayList<char[][]> gameBoards;
	private char[][] board;
	private Player chosen;
	private ArrayList<Player> players;
	private ArrayList<Enemy> currEnemies;
	private UserInterface ui = new UserInterface();
	boolean gameNotOver;
	int enemiesCounter = 0;
	boolean isNextLevel = false;
	static RandomGenerator randomGenerator;
	static ActionReader actionGenerator;

	public void initGameUnits() {

		players = new ArrayList<Player>();
		currEnemies = new ArrayList<Enemy>();

		Warrior jon_snow = new Warrior("Jon Snow", 300, 30, 4, null, randomGenerator, 6);
		Warrior the_hound = new Warrior("The Hound", 400, 20, 6, null, randomGenerator, 4);
		Mage melisandre = new Mage("Melisandre", 160, 10, 1, null, randomGenerator, 40, 300, 30, 5, 6);
		Mage thoros_of_myr = new Mage("Thoros of Myr", 250, 25, 3, null, randomGenerator, 15, 150, 50, 3, 3);
		Rogue arya_stark = new Rogue("Arya Stark", 150, 40, 2, null, randomGenerator, 20);
		Rogue bronn = new Rogue("Bronn", 250, 35, 3, null, randomGenerator, 60);

		players.add(jon_snow);
		players.add(the_hound);
		players.add(melisandre);
		players.add(thoros_of_myr);
		players.add(arya_stark);
		players.add(bronn);
	}

	public Game(ArrayList<char[][]> gameBoards, RandomGenerator srandomGenerator, ActionReader sactionGenerator) {
		gameNotOver = true;
		randomGenerator = srandomGenerator;
		actionGenerator = sactionGenerator;
		this.gameBoards = gameBoards;
		initGameUnits();
	}

	private void userSelectPlayer() {
		chosen = ui.selectPlayer(players, actionGenerator);
	}

	private void printCurrBoard() {
		char[][] boardToPrint = handleTrapsVisibility();
		ui.printBoard(boardToPrint, chosen);
	}

	public char[][] removeUnitFromBoard(Enemy enemy, Position oldPos) {
		char[][] newBoard = this.board;
		chosen.setPosition(enemy.getPosition());
		int posX = enemy.getPosition().getX();
		int posY = enemy.getPosition().getY();
		newBoard[posX][posY] = '@';
		newBoard[oldPos.getX()][oldPos.getY()] = '.';
		currEnemies.removeIf(obj -> ((obj.getPosition().getX() == posX) && (obj.getPosition().getY() == posY)));
		enemiesCounter--;
		if (enemiesCounter <= 0) {
			isNextLevel = true;
		}
		return newBoard;
	}

	// stay - 0, left - 1, right - 2, up - 3, down - 4
	public void attemptMove(Position newPosition, Position currPosition) {
		if (newPosition.inBounds()) {
			switch (board[newPosition.getX()][newPosition.getY()]) {
				case '.': {
					chosen.setPosition(newPosition);
					board[newPosition.getX()][newPosition.getY()] = '@';
					board[currPosition.getX()][currPosition.getY()] = '.';
					break;
				}
				case '#':
					break;
				case '@':
					break;
				default: {
					Enemy enemy = getEnemyAtPosition(newPosition);
					boolean enemyDead = chosen.meleeCombatUser(enemy, newPosition, currPosition);
					if (enemyDead) {
						this.board = removeUnitFromBoard(enemy, currPosition);
					}
				}
			}
		}
	}

	public Enemy getEnemyAtPosition(Position pos) {

		for (Enemy enemy : currEnemies) {
			if (enemy.getPosition().isEqual(pos)) {
				return enemy;
			}

		}
		return null;
	}

	private void get_User_MoveAndApply() {
		String playerMove = actionGenerator.nextAction();
		System.out.println(playerMove);

		switch (playerMove) {
			// up
			case "w":
				attemptMove(chosen.getPosition().getUp(), chosen.getPosition());
				break;
			// down
			case "s": {
				attemptMove(chosen.getPosition().getDown(), chosen.getPosition());
				break;
			}
			// left
			case "a":
				attemptMove(chosen.getPosition().getLeft(), chosen.getPosition());
				break;
			// right
			case "d":
				attemptMove(chosen.getPosition().getRight(), chosen.getPosition());
				break;
			// special ability
			case "e":
				ArrayList<Enemy> deadEnemies = chosen.castSpecialAbility(currEnemies);
				for (Enemy dead : deadEnemies) {
					board[dead.getPosition().getX()][dead.getPosition().getX()] = '.';
					enemiesCounter--;
					currEnemies.remove(dead);
					if (enemiesCounter <= 0) {
						gameNotOver = false;
					}
				}
				break;
			// do nothing
			case "q":
				break;
		}
		chosen.gameTickUpdate();
	}

	public void initBoardUnits() {
		enemiesCounter = 0;

		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				switch (board[x][y]) {
					case 's':
						Monster lannister_solider = new Monster("Lannister Solider", 80, 8, 3, new Position(x, y), randomGenerator, 25, 's', 3);
						currEnemies.add(lannister_solider);
						enemiesCounter++;
						break;

					case 'k':
						Monster lannister_knight = new Monster("Lannister Knight", 200, 14, 8, new Position(x, y), randomGenerator, 50, 'k', 4);
						currEnemies.add(lannister_knight);
						enemiesCounter++;
						break;

					case 'q':
						Monster queens_guard = new Monster("Queens Guard", 400, 20, 15, new Position(x, y), randomGenerator, 100, 'q', 5);
						currEnemies.add(queens_guard);
						enemiesCounter++;
						break;

					case 'z':
						Monster wright = new Monster("Wright", 600, 30, 15, new Position(x, y), randomGenerator, 100, 'z', 3);
						currEnemies.add(wright);
						enemiesCounter++;
						break;

					case 'b':
						Monster bear_wright = new Monster("Bear-Wright", 1000, 75, 30, new Position(x, y), randomGenerator, 250, 'b', 4);
						currEnemies.add(bear_wright);
						enemiesCounter++;
						break;

					case 'g':
						Monster giant_wright = new Monster("Giant-Wright", 1500, 100, 40, new Position(x, y), randomGenerator, 500, 'g', 5);
						currEnemies.add(giant_wright);
						enemiesCounter++;
						break;

					case 'w':
						Monster white_walker = new Monster("White Walker", 2000, 150, 50, new Position(x, y), randomGenerator, 1000, 'w', 6);
						currEnemies.add(white_walker);
						enemiesCounter++;
						break;

					case 'M':
						Monster the_mountain = new Monster("The Mountain", 1000, 60, 25, new Position(x, y), randomGenerator, 500, 'M', 6);
						currEnemies.add(the_mountain);
						enemiesCounter++;
						break;
					case 'C':
						Monster queen_cersei = new Monster("Queen Cersei", 100, 10, 10, new Position(x, y), randomGenerator, 1000, 'C', 1);
						currEnemies.add(queen_cersei);
						enemiesCounter++;
						break;
					case 'K':
						Monster nights_king = new Monster("Night's King", 5000, 300, 150, new Position(x, y), randomGenerator, 5000, 'K', 8);
						currEnemies.add(nights_king);
						enemiesCounter++;
						break;

					case 'B':
						Trap bonus_trap = new Trap("Bonus \"Trap\"", 1, 1, 1, new Position(x, y), randomGenerator, 250, 'B', 5, 6, 2);
						currEnemies.add(bonus_trap);
						enemiesCounter++;
						break;

					case 'Q':
						Trap queens_trap = new Trap("Queen's Trap", 250, 50, 10, new Position(x, y), randomGenerator, 100, 'Q', 4, 10, 4);
						currEnemies.add(queens_trap);
						enemiesCounter++;
						break;

					case 'D':
						Trap death_trap = new Trap("Death Trap", 500, 100, 20, new Position(x, y), randomGenerator, 250, 'D', 6, 10, 3);
						currEnemies.add(death_trap);
						enemiesCounter++;
						break;
				}
			}
		}
	}

	public void start() throws Exception {

		userSelectPlayer();

		for (char[][] currBoard : gameBoards) {
			if (gameNotOver) {

				isNextLevel = false;
				initCurrLevelBoard(currBoard);
				initUserPlayer();
				initBoardUnits();

				while (gameNotOver) {
					printCurrBoard();
					get_User_MoveAndApply();
					if (isNextLevel) {
						chosen.levelUp();
						break;
					}
					get_Enemies_MoveAndApply();
				}
			}
		}
		System.out.println("Game Over!");
	}

	public void get_Enemies_MoveAndApply() throws Exception {
		for (Enemy enemy : currEnemies) {
			Object[] toret = enemy.turn(chosen, board);
			updateEnemyPosition(enemy, toret);
			if (!gameNotOver) {
				break;
			}
		}
	}

	private void updateEnemyPosition(Enemy enemy, Object[] results) {
		Position newPos = (Position) results[1];

		if (((Boolean) results[0]).booleanValue()) {
			//player is dead!
			gameNotOver = false;
			board[chosen.getPosition().getX()][chosen.getPosition().getY()] = 'X';
		} else {
			if (!enemy.getPosition().isEqual(newPos)) {
				board[enemy.getPosition().getX()][enemy.getPosition().getY()] = '.';
				board[newPos.getX()][newPos.getY()] = enemy.getTile();
				enemy.setPosition(newPos);
			}
		}
	}

	private void initUserPlayer() {
		chosen.setPosition(ui.getUserPosition(board));
	}

	private void initCurrLevelBoard(char[][] currBoard) {
		this.board = currBoard;
	}

	private char[][] handleTrapsVisibility() {
		char[][] boardToPrint = this.board;
		Trap trap = null;
		for (int x = 0; x < boardToPrint.length; x++) {
			for (int y = 0; y < boardToPrint[x].length; y++) {
				switch (boardToPrint[x][y]) {
					case 'B':
						trap = (Trap) getEnemyAtPosition(new Position(x, y));
						if (!trap.isVisible()) {
							boardToPrint[x][y] = '.';
						}
						break;

					case 'Q':
						trap = (Trap) getEnemyAtPosition(new Position(x, y));
						if (!trap.isVisible()) {
							boardToPrint[x][y] = '.';
						}
						break;

					case 'D':
						trap = (Trap) getEnemyAtPosition(new Position(x, y));
						if (!trap.isVisible()) {
							boardToPrint[x][y] = '.';
						}
						break;
				}
			}
		}
		return boardToPrint;
	}

}
