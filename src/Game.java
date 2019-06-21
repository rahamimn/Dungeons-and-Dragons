import java.util.ArrayList;
import java.util.List;

public class Game {

    private ArrayList<char[][]> gameBoards;
    private char[][] board;
    private Player chosen;
    private ArrayList<Player> players;
    private ArrayList<Enemy> enemies;
    private ArrayList<Enemy> currEnemies;
    private UserInterface ui = new UserInterface();

    public void initGameUnits() {

        players = new ArrayList<Player>();
        enemies = new ArrayList<Enemy>();
        currEnemies = new ArrayList<Enemy>();


        Warrior jon_snow = new Warrior("Jon Snow", 300, 30, 4, null, 6);
        Warrior the_hound = new Warrior("The Hound", 400, 20, 6, null, 4);
        Mage melisandre = new Mage("Melisandre", 160, 10, 1, null, 40, 300, 30, 5, 6);
        Mage thoros_of_myr = new Mage("Thoros of Myr", 250, 25, 3, null, 15, 150, 50, 3, 3);
        Rogue arya_stark = new Rogue("Arya Stark", 150, 40, 2, null, 20);
        Rogue bronn = new Rogue("Bronn", 250, 35, 3, null, 60);

        players.add(jon_snow);
        players.add(the_hound);
        players.add(melisandre);
        players.add(thoros_of_myr);
        players.add(arya_stark);
        players.add(bronn);

        Monster lannister_solider = new Monster("Lannister Solider", 80, 8, 3, null, 25, 's', 3);
        Monster lannister_knight = new Monster("Lannister Knight", 200, 14, 8, null, 50, 'k', 4);
        Monster queens_guard = new Monster("Queens Guard", 400, 20, 15, null, 100, 'q', 5);
        Monster wright = new Monster("Wright", 600, 30, 15, null, 100, 'z', 3);
        Monster bear_wright = new Monster("Bear-Wright", 1000, 75, 30, null, 250, 'b', 4);
        Monster giant_wright = new Monster("Giant-Wright", 1500, 100, 40, null, 500, 'g', 5);
        Monster white_walker = new Monster("White Walker", 2000, 150, 50, null, 1000, 'w', 6);
        Monster the_mountain = new Monster("The Mountain", 1000, 60, 25, null, 500, 'M', 6);
        Monster queen_cersei = new Monster("Queen Cersei", 100, 10, 10, null, 1000, 'C', 1);
        Monster nights_king = new Monster("Night's King", 5000, 300, 150, null, 5000, 'K', 8);

        Trap bonus_trap = new Trap("Bonus \"Trap\"", 1, 1, 1, null, 250, 'B', 5, 6, 2);
        Trap queens_trap = new Trap("Queen's Trap", 250, 50, 10, null, 100, 'Q', 4, 10, 4);
        Trap death_trap = new Trap("Death Trap", 500, 100, 20, null, 250, 'D', 6, 10, 3);

        enemies.add(lannister_solider);
        enemies.add(lannister_knight);
        enemies.add(queens_guard);
        enemies.add(wright);
        enemies.add(bear_wright);
        enemies.add(giant_wright);
        enemies.add(white_walker);
        enemies.add(the_mountain);
        enemies.add(queen_cersei);
        enemies.add(nights_king);
        enemies.add(bonus_trap);
        enemies.add(queens_trap);
        enemies.add(death_trap);
    }

    public Game(ArrayList<char[][]> gameBoards) {
        this.gameBoards = gameBoards;
        initGameUnits();
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
            }
        } else {
            System.out.println("OUT OF BOUNDS "); //NIR
        }
    }

    public void start() throws Exception {
        chosen = ui.selectPlayer(players);

        boolean notOver = true;

        for (char[][] currBoard : gameBoards) {

            this.board = currBoard;

            initBoardUnits();
            chosen.setPosition(ui.getUserPosition(board));

            while (notOver) {
                ui.printBoard(board, chosen);
                char playerMove = ui.getMoveFromUser();

                switch (playerMove) {
                    // up
                    case 'w':
                        attemptMove(chosen.getPosition().getUp(), chosen.getPosition());
                        break;
                    // down
                    case 's': {
                        attemptMove(chosen.getPosition().getDown(), chosen.getPosition());
                        break;
                    }
                    // left
                    case 'a':
                        attemptMove(chosen.getPosition().getLeft(), chosen.getPosition());
                        break;
                    // right
                    case 'd':
                        attemptMove(chosen.getPosition().getRight(), chosen.getPosition());
                        break;
                    // special ability
                    case 'e':
                        break;
                    // do nothing
                    case 'q':
                        break;
                }

                applyEnemiesMove();


            }
        }
    }

    public void printBoardDebug(char[][] board) {
        System.out.println("%%%%%%%%%%%% - - - DEBUG BEGIN - - - %%%%%%%%%%%\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++)
                System.out.print(board[i][j]);
            System.out.print('\n');
        }
        System.out.println("%%%%%%%%%%%%  - - - DEBUG END - - - %%%%%%%%%%%\n");
    }


    private char[][] rotateClockWise(char[][] array) {
        int size = array.length;
        char[][] ret = new char[size][size];

        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j)
                ret[i][j] = array[size - j - 1][i]; //***

        return ret;
    }


    public void printCell(int x, int y, String message) {
        char content = board[x][y];
        System.out.println("{ " + message + " } | (" + x + "," + y + ") content: " + content);
    }

    public void printCell(Position pos, String message) {
        char content = board[pos.getX()][pos.getY()];
        System.out.println("{ " + message + " } | (" + pos.getX() + "," + pos.getY() + ") content: " + content);
    }

    public void initBoardUnits() {

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                switch (board[x][y]) {
                    case 's':
                        Monster lannister_solider = new Monster("Lannister Solider", 80, 8, 3, new Position(x, y), 25, 's', 3);
                        currEnemies.add(lannister_solider);
                        break;

                    case 'k':
                        Monster lannister_knight = new Monster("Lannister Knight", 200, 14, 8, new Position(x, y), 50, 'k', 4);
                        currEnemies.add(lannister_knight);
                        break;

                    case 'q':
                        Monster queens_guard = new Monster("Queens Guard", 400, 20, 15, new Position(x, y), 100, 'q', 5);
                        currEnemies.add(queens_guard);
                        break;

                    case 'z':
                        Monster wright = new Monster("Wright", 600, 30, 15, new Position(x, y), 100, 'z', 3);
                        currEnemies.add(wright);
                        break;

                    case 'b':
                        Monster bear_wright = new Monster("Bear-Wright", 1000, 75, 30, new Position(x, y), 250, 'b', 4);
                        currEnemies.add(bear_wright);
                        break;

                    case 'g':
                        Monster giant_wright = new Monster("Giant-Wright", 1500, 100, 40, new Position(x, y), 500, 'g', 5);
                        currEnemies.add(giant_wright);
                        break;

                    case 'w':
                        Monster white_walker = new Monster("White Walker", 2000, 150, 50, new Position(x, y), 1000, 'w', 6);
                        currEnemies.add(white_walker);
                        break;

                    case 'M':
                        Monster the_mountain = new Monster("The Mountain", 1000, 60, 25, new Position(x, y), 500, 'M', 6);
                        currEnemies.add(the_mountain);
                        break;

                    case 'C':
                        Monster queen_cersei = new Monster("Queen Cersei", 100, 10, 10, new Position(x, y), 1000, 'C', 1);
                        currEnemies.add(queen_cersei);
                        break;

                    case 'K':
                        Monster nights_king = new Monster("Night's King", 5000, 300, 150, new Position(x, y), 5000, 'K', 8);
                        currEnemies.add(nights_king);
                        break;

                    case 'B':
                        Trap bonus_trap = new Trap("Bonus \"Trap\"", 1, 1, 1, new Position(x, y), 250, 'B', 5, 6, 2);
                        currEnemies.add(bonus_trap);
                        break;

                    case 'Q':
                        Trap queens_trap = new Trap("Queen's Trap", 250, 50, 10, new Position(x, y), 100, 'Q', 4, 10, 4);
                        currEnemies.add(queens_trap);
                        break;

                    case 'D':
                        Trap death_trap = new Trap("Death Trap", 500, 100, 20, new Position(x, y), 250, 'D', 6, 10, 3);
                        currEnemies.add(death_trap);
                        break;
                }
            }
        }

        for (Enemy enemy : currEnemies) {
            //System.out.println(enemy.unitStr()); //NIR
        }
    }

    public void applyEnemiesMove() throws Exception {
        for (Enemy enemy : currEnemies) {
            int moveNum = enemy.turn(chosen.getPosition());
            updateEnemyPosition(enemy, moveNum);
        }
//        for (Enemy enemy : currEnemies) {
//            enemy.printPosition(enemy.getName());
//        }
    }

    public void updateEnemyPosition(GameUnit gameUnit, int moveNum) throws Exception {


        Position newPosition = new Position(0, 0);
        Position currPosition = gameUnit.getPosition();

        //System.out.println("MoveNum = " + moveNum);

        switch (moveNum) {
            case 1:
                newPosition = currPosition.getLeft();
                break;
            case 2:
                newPosition = currPosition.getRight();
                break;
            case 3:
                newPosition = currPosition.getUp();
                break;
            case 4:
                newPosition = currPosition.getDown();
                break;
            default:
                return;
        }

        if (newPosition.inBounds() && currPosition.inBounds()) {
            switch (board[newPosition.getX()][newPosition.getY()]) {
                case '.': {
//                    printCell(currPosition, "currPosition after");
//                    printCell(newPosition, "newPosition after");
                    char gameUnitTile = board[currPosition.getX()][currPosition.getY()];
                    gameUnit.setPosition(newPosition);
                    board[newPosition.getX()][newPosition.getY()] = gameUnitTile;
                    board[currPosition.getX()][currPosition.getY()] = '.';
//                    printCell(currPosition, "currPosition after");
//                    printCell(newPosition, "newPosition after");

                    break;
                }
                case '#':
                    break;
                case '@':
                    break;
            }
        } else {
            System.out.println("OUT OF BOUNDS "); //NIR
        }
    }


}

