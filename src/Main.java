import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        /** Check if deterministic */
        int len = args.length;
        boolean is_deterministic = false;
        if (len > 1 && args[1].equals("-D"))
            is_deterministic = true;


        /** Get all level files */
		File folder = new File(args[0]);
        File[] listOfFiles = folder.listFiles();
        ArrayList<ArrayList<Object>> list = new ArrayList<ArrayList<Object>>();

        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().contains("level")) {
            	String level = (file.getName().substring(6, file.getName().length()-4));
                ArrayList<Object> map = new ArrayList<>();
                map.add(Integer.parseInt(level));
                map.add(file);
                list.add(map);
            }
        }
        
        /** Sort level files by number */
        class MySort implements Comparator<ArrayList<Object>> {
            public int compare(ArrayList<Object> a, ArrayList<Object> b) {
                return ((Integer) a.get(0)).compareTo((Integer) b.get(0));
            }
        }
        Collections.sort(list, new MySort());

        RandomGenerator num_generator = null;
        ActionReader action_generator = null;
        /** Get game actions */
        if (is_deterministic) { 
            num_generator = new DeterministicNums("random_numbers.txt");
            action_generator = new DeterministicActions("user_actions.txt");

        } else {
            num_generator = new RandomGeneratorImpl();
            action_generator = new ActionReaderImpl();
        }

        ArrayList<char[][]> gameBoards = createGameBoards(list);
        Game game = new Game(gameBoards, num_generator, action_generator);
        game.start();
    }


    public static ArrayList<char[][]> createGameBoards(ArrayList<ArrayList<Object>> boardsFileList) throws IOException {

        ArrayList<char[][]> boardsList = new ArrayList<char[][]>();
        for (int levelNum = 0; levelNum < boardsFileList.size(); levelNum++) {
            File file = (File) (boardsFileList.get(levelNum)).get(1);
            Scanner sc = new Scanner(file);
            int width = sc.nextLine().length();
            int height = 0;
            String path = "level " + (levelNum + 1) + ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(path));

            while (reader.readLine() != null) height++;
            reader.close();

            char[][] gameBoard = new char[height][width];
            Scanner scanner = new Scanner(new File(path));

            for (int i = 0; scanner.hasNextLine(); i++)
                gameBoard[i] = scanner.nextLine().toCharArray();

            sc.close();
            boardsList.add(gameBoard);
        }
        return boardsList;
    }
}
