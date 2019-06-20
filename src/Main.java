import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Main {
	public static void main(String[] args) throws IOException {
    	int len = args.length; 
    	boolean is_deterministic = false;
    	if (len > 1 && args[1].equals("-D")){
    		is_deterministic = true;
    	}
    	File folder = new File(args[0]);
    	File[] listOfFiles = folder.listFiles();
    	
		ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>(); 
    	for (File file : listOfFiles) {
    	    if (file.isFile() && file.getName().contains("level")) {
    	    	String level =  (file.getName()).substring(6);
    	    	level = level.substring(0, level.length()-4);
    	    	ArrayList<Object> map = new ArrayList<>();
    	    	map.add(Integer.parseInt(level));
    	    	map.add(file);
    	    	list.add(map);
    	    }
    	}
    	
    
    	
    	RandomGenerator num_generator = null;
    	ActionReader action_generator = null;
    	
    	if(is_deterministic){
    		num_generator = new DeterministicNums("user_input.txt");
    		action_generator = new DeterministicActions("random_numbers.txt");
    	}
    	else{
    		num_generator = new RandomGeneratorImpl();
    		action_generator = new ActionReaderImpl();
    	}
    	
    	// TODO
    	//createGameBoards(list);
    	Game game = new Game(list);
    	game.start();
    	
    	class MySort implements Comparator<ArrayList<Object>> {
    	    @Override
    	    public int compare(ArrayList<Object> a, ArrayList<Object> b) {
    	        return ((Integer) a.get(0)).compareTo((Integer)b.get(0));
    	    }
    	}
    	Collections.sort(list, new MySort());
    	
    	
    	
    }

	
}
