import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parser {
    static Scanner sc;

    public ArrayList<Word>parse(String path, int id,int block) throws FileNotFoundException {
        File file = new File(path);
        ArrayList<Word> words = new ArrayList<>();
        int numOfInstructions = 0;
        int start = 20*block;
        words.add(new Word("PID", String.valueOf(id)));
        words.add(new Word("State", "Ready"));
        words.add(new Word("PC", "0"));
        words.add(new Word("Start", start+""));
        words.add(new Word("End", ""));
        words.add(new Word("Variable", ""));
        words.add(new Word("Variable", ""));
        words.add(new Word("Variable", ""));
        sc = new Scanner(file);
        
        while(sc.hasNextLine()){
            numOfInstructions++;
              String line = sc.nextLine();
            words.add(new Word ("Instruction", line));

        }
        int end = numOfInstructions + (block*20)+5+3-1;
        words.get(4).setValue(end+"");

        return words;
        
    }
}
