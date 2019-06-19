import java.util.ArrayList;

public class Game {
	
	
	GameUnit[][] board = new GameUnit[][]{null};
	
	// positions missing
	 Warrior jon_snow = new Warrior("Jon Snow", 300, 30, 4, ,6);
     Warrior the_hound = new Warrior("The Hound", 400, 20, 6, , 4);
     Mage melisandre = new Mage("Melisandre", 160, 10, 1, , 40, 300, 30, 5, 6);
     Mage thoros_of_myr = new Mage("Thoros of Myr", 250, 25, 3, , 15, 150, 50, 3, 3);
     Rogue arya_stark = new Rogue("Arya Stark", 150, 40, 2, , 20);
     Rogue bronn = new Rogue("Bronn", 250, 35, 3, , 60);

     
     Monster lannister_solider = new Monster("Lannister Solider", 80, 8, 3, , 25, 's', 3);
     Monster lannister_knight = new Monster("Lannister Knight", 200, 14, 8, , 50, 'k', 4);
     Monster queens_guard = new Monster("Queens Guard", 400, 20, 15, , 100, 'q', 5);
     Monster wright = new Monster("Wright", 600, 30, 15, , 100, 'z', 3);
     Monster bear_wright = new Monster("Bear-Wright", 1000, 75, 30, , 250, 'b', 4);
     Monster giant_wright = new Monster("Giant-Wright", 1500, 100, 40, , 500, 'g', 5);
     Monster white_walker = new Monster("White Walker", 2000, 150, 50, , 1000, 'w', 6);
     Monster the_mountain = new Monster("The Mountain", 1000, 60, 25, , 500, 'M', 6);
     Monster queen_cersei = new Monster("Queen Cersei", 100, 10, 10, , 1000, 'C', 1);
     Monster nights_king = new Monster("Night's King", 5000, 300, 150, , 5000, 'K', 8);

     String name, int health, int attackPoints, int defensePoints, Position position, int experience,
		char tile, int relocationRange, int relocationTime, int visibilityTime
		
     Trap bonus_trap = new Trap("Bonus Trap", 1, 1, 1, , 250, 'B', 5, 6, 2);
     Trap queens_trap= new Trap("Queen's Trap", 250, 50, 10, , 100, 'Q', 4, 10, 4);
     Trap death_trap = new Trap("Death Trap", 500, 100, 20, , 250, 'D', 6, 10, 3);

     

	public Game(ArrayList<ArrayList<Object>> list) {
		// TODO Auto-generated constructor stub
	}

	public void start() {
		for level in gameLevels:
			while(playerNotDead || levelNotFinished)
				player.move()
				each enemy move()
		
	}

}
