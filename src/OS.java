import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.*;

public class OS {
	int timeSlice;
	int[] timeToArrive;
	String[] programsPaths;
	Parser parser;
	Memory memory;
	Scheduler scheduler;
	int[] lookUpTable;
	int[] timeStamp;
	int blockNumber = 0;
	static String basePath = "/home/shimaa/GUC/Projects/Team_81/OS Project/";

	public OS(String[] programsPaths, int timeSlice, int[] timeToArrive) throws FileNotFoundException {
		this.parser = new Parser();
		this.memory = new Memory();
		this.scheduler = new Scheduler(timeSlice, this);
		this.programsPaths = programsPaths;
		this.timeToArrive = timeToArrive;

		lookUpTable = new int[programsPaths.length];
		timeStamp = new int[programsPaths.length];
		Arrays.fill(lookUpTable, -1);

	}

	public void checkArrival() throws FileNotFoundException {
		// System.out.println("Clock Cycle: " + Scheduler.clock);
		for (int i = 0; i < timeToArrive.length; i++) {
			if (timeToArrive[i] == Scheduler.clock) {
				int blockToSwap = blockNumber;
				if (BlockFilled(blockNumber)) {
					String state = memory.memory[blockNumber * 20 + 1].getValue();
					// if the process in the block we swap with is running swap with the other block
					if (state.equals("Running")) {
						blockToSwap = (blockNumber + 1) % 2;
					} else {
						blockNumber = blockNumber == 0 ? 1 : 0;
					}
					if (BlockFilled(blockToSwap)) {
						swaptoDesk(blockToSwap);
					}
				}
				ArrayList<Word> process = parser.parse(programsPaths[i], i + 1, blockToSwap);
				writeToMemory(process, blockToSwap, i + 1);

				ReadyQueue.addToReadyQueue(i + 1);
			}
		}
	}

	public void writeToMemory(ArrayList<Word> process, int blockNumber, int processID) {
		for (int i = 0; i < process.size(); i++) {
			memory.memory[blockNumber * 20 + i] = process.get(i);
		}
		System.out.println("processID: " + processID + "writed to memory");
		lookUpTable[processID - 1] = blockNumber;
	}

	public void swaptoDesk(int blockNumber) {
		int processId = memory.memory[blockNumber * 20].getValueAsInt();
		lookUpTable[processId - 1] = -1;
		String path = basePath + "desk" + processId + ".txt";

		int start = blockNumber * 20;
		int end = memory.memory[start + 4].getValueAsInt();

		try {
			FileWriter myWriter = new FileWriter(path);

			for (int i = start; i <= end; i++) {
				myWriter.write(memory.memory[i].toString() + "\n");
			}
			myWriter.close();
			System.out.println("Process" + processId + "swapped from memory to desk");
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

	}

	public void swapFromDesk(int blockNumber, int processId) throws FileNotFoundException {
		String path = basePath + "desk" + processId + ".txt";
		int start = blockNumber * 20;
		int numOfLines = 0;
		try {
			Scanner myReader = new Scanner(new File(path));
			while (myReader.hasNextLine()) {
				String line = myReader.nextLine();
				String[] splittedLine = line.split(":");
				String value;
				if (splittedLine[0].equals("Variable")) {
					value = "";
				} else {
					value = splittedLine[1];
				}
				memory.memory[start] = new Word(splittedLine[0], value);
				start++;
				numOfLines++;

			}
			myReader.close();
			System.out.println("Process" + processId + "swapped from desk to memory in block " + blockNumber);
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		lookUpTable[processId - 1] = blockNumber;
		int startMemory = blockNumber * 20;
		int endMemory = startMemory + numOfLines - 1;
		memory.memory[blockNumber * 20 + 3].setValue(startMemory + "");
		memory.memory[blockNumber * 20 + 4].setValue(endMemory + "");
		PrintWriter pw = new PrintWriter(path);
		pw.close();

	}

	public boolean BlockFilled(int value) {
		for (int i = 0; i < lookUpTable.length; i++) {
			if (lookUpTable[i] == value) {
				return true;
			}
		}
		return false;
	}

	public void start() throws FileNotFoundException {
		scheduler.runProcess();
	}

	public static void main(String[] args) throws FileNotFoundException {

		String[] programStrings = { "/home/shimaa/GUC/Projects/Team_81/OS Project/Program_1.txt",
				"/home/shimaa/GUC/Projects/Team_81/OS Project/Program_2.txt",
				"/home/shimaa/GUC/Projects/Team_81/OS Project/Program_3.txt" };
		OS os = new OS(programStrings, 2, new int[] { 0, 1, 4 });
		os.start();

	}
}
