import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.omg.CORBA.portable.InputStream;

public class DeterministicNums implements RandomGenerator {

	private ArrayList<String> nums;
	private int index;

	
	public DeterministicNums(String path) throws IOException{
		
		nums = new ArrayList<String>(Files.readAllLines(Paths.get(path)));
		index = 0;
	}
	
	@Override
	public int nextInt(int n) {
		int to_ret = Integer.parseInt(nums.get(index));
		if(index == nums.size() - 1)
			index = 0;
		else
			index++;

		return to_ret;
	}

}
