import java.util.Random;
import java.util.Scanner;

public class ActionReaderImpl implements ActionReader {

	private Scanner scanner;
	
	public ActionReaderImpl(){
		scanner = new Scanner(System.in);
	}
	
	@Override
	public String nextAction(){
		
		String action = scanner.nextLine();
		return action;
	}

}
