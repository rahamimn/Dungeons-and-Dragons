import java.io.File;
import java.nio.file.Path;
import java.util.Random;

public class RandomGeneratorImpl implements RandomGenerator  {
	
	private Random rand;
	
	private RandomGeneratorImpl(){
		rand = new Random();
	}
	
	@Override
	public int nextInt(int n) {
		return rand.nextInt(n);
	}

		
}
