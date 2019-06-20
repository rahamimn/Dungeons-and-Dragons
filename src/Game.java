import java.util.ArrayList;
import java.util.List;

public class Game {

    private ArrayList<char[][]> gameBoards;
    private char[][] board;
    private Player chosen;
    private ArrayList<Player> players;
    private ArrayList<Enemy> enemies;
    private UserInterface ui = new UserInterface();


    public void initGameUnits() {

        players = new ArrayList<Player>();
        enemies = new ArrayList<Enemy>();

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
    public void attemptMove(Position position, Position currPosition) {
        if (position.inBounds()) {
            switch (board[position.getX()][position.getY()]) {
                case '.':
                    chosen.getPosition().setX(position.getX());
                    chosen.getPosition().setY(position.getY());
                    board[position.getX()][position.getY()] = '@';
                    board[currPosition.getX()][currPosition.getY()] = '.';
                    break;
                case '#':
                    break;
                case '@':
                    break;
            }
        }
    }

    public void start() {
        chosen = ui.selectPlayer(players);
        boolean notOver = true;

        for (char[][] board : gameBoards) {
            this.board = board;
            while (notOver) {
                ui.printBoard(board, chosen);
                char playerMove = ui.getMoveFromUser();
                switch (playerMove) {
                    // up
                    case 'w':
                        attemptMove(chosen.getPosition().getUp(), chosen.getPosition());
                        break;
                    // down
                    case 's':
                        attemptMove(chosen.getPosition().getDown(), chosen.getPosition());
                        break;
                    // left
                    case 'a':
                        attemptMove(chosen.getPosition().getLeft(), chosen.getPosition());
                        break;
                    // right
                    case 'd':
                        attemptMove(chosen.getPosition().getRight(), chosen.getPosition());
                        break;
                    //special ability
                    case 'e':
                        break;
                    // do nothing
                    case 'q':
                        break;

                }
            }
        }
    }
}
