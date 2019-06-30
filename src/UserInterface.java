import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
	
	public UserInterface(){ }
	
	public Player selectPlayer(ArrayList<Player> players, ActionReader chooseAction){
		System.out.println("Select Player:\n");

		for (int i = 0; i < players.size(); i++){
			System.out.println(i+1 + ". " + players.get(i).unitStr());
		}
		
		int choice = Integer.parseInt(chooseAction.nextAction());
		Player chosen = players.get(choice-1);
		System.out.println("You have selected:\n" + chosen.unitStr());
		System.out.println("Use w/s/a/d to move.\nUse e for special ability or q to pass.");
		return players.get(choice-1);
	}

	public void printBoard(char[][] board, Player player){
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++)
				System.out.print(board[i][j]);
			System.out.print('\n');
		}
		System.out.print("\n \n");
		System.out.println(player.unitStr());
	}

	public void printCombat(GameUnit attacker, GameUnit defender, int attackPoints, int defensePoints, int damage){
		System.out.println(attacker.getName() + " engaged in battle with " + defender.getName());
		System.out.println(attacker.unitStr());
		System.out.println(defender.unitStr());
		System.out.println(attacker.getName() + " rolled "+ attackPoints + " attack points.");
		System.out.println(defender.getName() + " rolled "+ defensePoints + " defense points.");
		System.out.println(attacker.getName() + " hit "+ defender.getName() + " for " + damage + "damage.");
		
	}

	public Position getUserPosition(char[][] board){
		for (int x = 0; x < board.length; x++){
			for (int y = 0; y < board[x].length; y++){
				if (board[x][y] == '@'){
					return new Position(x,y);
				}
			}
		}
		throw new NullPointerException("No user player on board");
	}

	public void printSpecial(Player player, Enemy enemy, int spellPower, int enemyDefensePts, int diff) {
		System.out.println("cast special ability");
		
	}

	public void cantCastSpecial() {
		System.out.println("sorry you cant case special ability!");
		
	}
	
}
