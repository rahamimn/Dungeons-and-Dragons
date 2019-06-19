import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class DeterministicActions implements ActionReader {

	private ArrayList<String> actions;
	private int index;
	
	public DeterministicActions(String path) throws IOException{
		
		actions = new ArrayList<String>(Files.readAllLines(Paths.get(path)));
		index = 0;
	}
	
	@Override
	public String nextAction() {
		String to_ret = actions.get(index);
		if(index == actions.size() - 1)
			index = 0;
		else
			index++;
		return to_ret;
	}

}
