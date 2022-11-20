import java.util.ArrayList;

public class Memory {

    public static final int MEMORY_SIZE = 40;

    public static Word[] memory;

    public Memory() {
        memory = new Word[MEMORY_SIZE];
    }

    public void printMemory() {
        System.out.println("........................");

        System.out.println("Memory:");
        for (int i = 0; i < memory.length; i++) {
            System.out.println(memory[i]!= null ? memory[i].toString(): "Empty");
        }

        System.out.println("........................");
    }

}
