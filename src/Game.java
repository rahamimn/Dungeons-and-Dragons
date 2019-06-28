import java.util.ArrayList;

public class Game {

    private ArrayList<char[][]> gameBoards;
    private char[][] board;
    private Player chosen;
    private ArrayList<Player> players;
    private ArrayList<Enemy> currEnemies;
    private UserInterface ui = new UserInterface();
    boolean gameNotOver;
    int enemiesCounter = 0; //NIR: TODO: change initialization location


    public void initGameUnits() {

        players = new ArrayList<Player>();
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
    }

    public Game(ArrayList<char[][]> gameBoards) {
        gameNotOver = true;
        this.gameBoards = gameBoards;
        initGameUnits();
    }

    // stay - 0, left - 1, right - 2, up - 3, down - 4
    public void attemptMove(Position newPosition, Position currPosition) {
        //System.out.println("inside attemptMove_1");
        if (newPosition.inBounds()) {
            //System.out.println("inside attemptMove_2");
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
                 //   System.out.println("in default");
                    Enemy enemy = getEnemyAtPosition(newPosition);
                    if (enemy == null){
                        System.out.println("Enemy is null");
                        return;
                    }
                            System.out.println("FIGHT!");
                            int userAttackPts = chosen.rollAttackForCombat();
                            int enemyDefensePts = enemy.rollDefenseForCombat();
                            int diff = userAttackPts - enemyDefensePts;
                            if (diff > 0){
                                System.out.println("enemy.getHealth() = " + enemy.getHealth());
                                enemy.decHealth(diff);
                                System.out.println("now health is  = " + enemy.getHealth());
                                if (enemy.getHealth() <= 0) {
                                    System.out.println("Health is over");
                                    chosen.decExperience(0-diff); // diff is negative
                                    this.board = removeUnitFromBoard(enemy);
                            }
                        }
                            else{
                                System.out.println("No damage happend");
                            }
                    }
                }
        } else {
            System.out.println("OUT OF BOUNDS "); // NIR
        }
    }

    public void start() throws Exception {

        userSelectPlayer();

        for (char[][] currBoard : gameBoards) { // Every currBoard is a new level
            initCurrLevelBoard(currBoard);
            initUserPlayer();
            initBoardUnits();

            while (gameNotOver) {
                printCurrBoard();
                get_User_MoveAndApply();
                get_Enemies_MoveAndApply();

                /**
                 * TODO:
                 *
                 * if (isGameOver()){ // Do stuff return; }
                 * 
                 * if (isNextLevel){ // Do stuff break;
                 */
            }
        }
    }

    private void fightIfCombat(Enemy enemy) {

        // TODO: finish this
        if (hasCombat()) {
            System.out.println("FIGHT!");
            int userAttackPts = chosen.rollAttackForCombat();
            int enemyDefensePts = enemy.rollDefenseForCombat();
            int diff = userAttackPts - enemyDefensePts;

            if (diff > 0) {
                enemy.decHealth(0 - diff); // diff is negative

                if (enemy.getHealth() <= 0) {
                    chosen.decExperience(diff); // diff is negative
                    removeUnitFromBoard(enemy);
                }
            }

        }

    }

    public char[][] removeUnitFromBoard(GameUnit unit) {
        int posX = unit.getPosition().getX();
        int posY = unit.getPosition().getY();

        char[][] newBoard = this.board;
        newBoard[posX][posY] = '.';

        System.out.println("before: currEnemies.size() = " + currEnemies.size());
        currEnemies.removeIf(obj->((obj.getPosition().getX()==posX) && (obj.getPosition().getY()==posY)));
        System.out.println("after: currEnemies.size() = " + currEnemies.size());

        enemiesCounter--;

        return newBoard;
    }

    private boolean hasCombat() {

        int userPosX = chosen.getPosition().getX();
        int userPosY = chosen.getPosition().getY();

        for (Enemy enemy : currEnemies) {
            int enemyPosX = enemy.getPosition().getX();
            int enemyPosY = enemy.getPosition().getY();


            if ((enemyPosX == userPosX) && (enemyPosY == userPosY)){
                return true;
            }
        }
        return false;
    }

    private void get_User_MoveAndApply() {
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



    }

    private void printCurrBoard() {
        ui.printBoard(board, chosen);
    }

    private void initUserPlayer() {
        chosen.setPosition(ui.getUserPosition(board));
    }

    private void initCurrLevelBoard(char[][] currBoard) {
        this.board = currBoard;
    }

    private void userSelectPlayer() {
        chosen = ui.selectPlayer(players);
    }

    public void initBoardUnits() {
        enemiesCounter = 0;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                switch (board[x][y]) {
                case 's':
                    Monster lannister_solider = new Monster("Lannister Solider", 80, 8, 3, new Position(x, y), 25, 's',
                            3);
                    currEnemies.add(lannister_solider);
                    enemiesCounter++;
                    break;

                case 'k':
                    Monster lannister_knight = new Monster("Lannister Knight", 200, 14, 8, new Position(x, y), 50, 'k',
                            4);
                    currEnemies.add(lannister_knight);
                    enemiesCounter++;
                    break;

                case 'q':
                    Monster queens_guard = new Monster("Queens Guard", 100, 20, 15, new Position(x, y), 100, 'q', 5);
                    //TODO: nir: change health to 400!!!!
                    currEnemies.add(queens_guard);
                    enemiesCounter++;
                    break;

                case 'z':
                    Monster wright = new Monster("Wright", 600, 30, 15, new Position(x, y), 100, 'z', 3);
                    currEnemies.add(wright);
                    enemiesCounter++;
                    break;

                case 'b':
                    Monster bear_wright = new Monster("Bear-Wright", 1000, 75, 30, new Position(x, y), 250, 'b', 4);
                    currEnemies.add(bear_wright);
                    enemiesCounter++;
                    break;

                case 'g':
                    Monster giant_wright = new Monster("Giant-Wright", 1500, 100, 40, new Position(x, y), 500, 'g', 5);
                    currEnemies.add(giant_wright);
                    enemiesCounter++;
                    break;

                case 'w':
                    Monster white_walker = new Monster("White Walker", 2000, 150, 50, new Position(x, y), 1000, 'w', 6);
                    currEnemies.add(white_walker);
                    enemiesCounter++;
                    break;

                case 'M':
                    Monster the_mountain = new Monster("The Mountain", 1000, 60, 25, new Position(x, y), 500, 'M', 6);
                    currEnemies.add(the_mountain);
                    Monster queen_cersei = new Monster("Queen Cersei", 100, 10, 10, new Position(x, y), 1000, 'C', 1);
                    currEnemies.add(queen_cersei);
                    enemiesCounter++;
                    break;

                case 'K':
                    Monster nights_king = new Monster("Night's King", 5000, 300, 150, new Position(x, y), 5000, 'K', 8);
                    currEnemies.add(nights_king);
                    enemiesCounter++;
                    break;

                case 'B':
                    Trap bonus_trap = new Trap("Bonus \"Trap\"", 1, 1, 1, new Position(x, y), 250, 'B', 5, 6, 2);
                    currEnemies.add(bonus_trap);
                    enemiesCounter++;
                    break;

                case 'Q':
                    Trap queens_trap = new Trap("Queen's Trap", 250, 50, 10, new Position(x, y), 100, 'Q', 4, 10, 4);
                    currEnemies.add(queens_trap);
                    enemiesCounter++;
                    break;

                case 'D':
                    Trap death_trap = new Trap("Death Trap", 500, 100, 20, new Position(x, y), 250, 'D', 6, 10, 3);
                    currEnemies.add(death_trap);
                    enemiesCounter++;
                    break;
                }
            }
        }
    }

    public void get_Enemies_MoveAndApply() throws Exception {
        for (Enemy enemy : currEnemies) {
            int moveNum = enemy.turn(chosen.getPosition(), this);
            updateEnemyPosition(enemy, moveNum);
            fightIfCombat(enemy); // NIR: Need to check this line
        }
    }

    public void updateEnemyPosition(GameUnit gameUnit, int moveNum) throws Exception {

        Position newPosition;
        Position currPosition = gameUnit.getPosition();

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
                char gameUnitTile = board[currPosition.getX()][currPosition.getY()];
                gameUnit.setPosition(newPosition);
                board[newPosition.getX()][newPosition.getY()] = gameUnitTile;
                board[currPosition.getX()][currPosition.getY()] = '.';
                break;
            }
            case '#':
                break;
            case '@':
                break;
            }
        } else {
            System.out.println("OUT OF BOUNDS "); // NIR
        }
    }

    public Enemy getEnemyAtPosition(Position pos){

        for (Enemy enemy : currEnemies) {

            int enemyPosX = enemy.getPosition().getX();
            int enemyPosY = enemy.getPosition().getY();

            if ((enemyPosX == pos.getX()) && (enemyPosY == pos.getY())){
                return enemy;
            }

        }
        return null;
    }



    // ----------------------------- DEBUG HELPERS ----------------------------

    public void printBoardDebug(char[][] board) {
        System.out.println("- - - - - - - - -  DEBUG BEGIN - - - - - - - - -\n");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++)
                System.out.print(board[i][j]);
            System.out.print('\n');
        }
        System.out.println("- - - - - - - - - DEBUG END - - - - - - - - -\n");
    }

    public void printCell(int x, int y, String message) {
        char content = board[x][y];
        System.out.println("{ " + message + " } | (" + x + "," + y + ") content: " + content);
    }

    public void printCell(Position pos, String message) {
        char content = board[pos.getX()][pos.getY()];
        System.out.println("{ " + message + " } | (" + pos.getX() + "," + pos.getY() + ") content: " + content);
    }

    public void setBoard(char[][] board){
        this.board = board;
    }

    public char[][] getBoard(){
        return this.board;
    }

    public Player getChosen(){
        return chosen;
    }

}
