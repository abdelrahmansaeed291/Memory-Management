import java.io.FileNotFoundException;

public class Scheduler {
	int timeSlice;
	static int clock = 0;
	OS os;
	int runningProcess;

	public Scheduler(int x, OS os) {
		timeSlice = x;
         this.os = os;
	}



	

	public void runProcess() throws FileNotFoundException {
		os.checkArrival();
	
		while (!ReadyQueue.isEmpty()) {

			int p = ReadyQueue.removeFromReadyQueue();
			//not in memory
			if (os.lookUpTable[p-1]==-1){
				int blockNumber = os.blockNumber;
				os.swaptoDesk(blockNumber);
				os.swapFromDesk(blockNumber, p);
				os.blockNumber = blockNumber == 0 ? 1 : 0;
			}
			int endValue = os.memory.memory[os.lookUpTable[p-1]*20+4].getValueAsInt();
            int stateIndex = os.lookUpTable[p-1]*20+1; 
            os.memory.memory[stateIndex].setValue("Running");
			int counter = timeSlice;
			while (!os.memory.memory[stateIndex].getValue().equals("Finished")  && counter > 0) {
				System.out.println("Clock Cycle: " + clock);
				if (clock!=0)
					os.checkArrival();
				System.out.println();
				os.memory.printMemory();

				int pc = os.memory.memory[os.lookUpTable[p-1]*20+2].getValueAsInt();
				int instructionAddress = os.lookUpTable[p-1]*20+8+pc;
				System.out.println(os.memory.memory[instructionAddress].toString()+" is currently executing");
				pc++;
				os.memory.memory[os.lookUpTable[p-1]*20+2].setValue(pc+"");
				if(instructionAddress==endValue){
					os.memory.memory[stateIndex].setValue("Finished");
				}
				System.out.println("/////////////////////////////");
				clock++;
				//os.checkArrival();
				counter--;
			}
			if (os.memory.memory[stateIndex].getValue().equals("Running")) {
				os.memory.memory[stateIndex].setValue("Ready");
				ReadyQueue.addToReadyQueue(p);
			}

		}
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
		System.out.println("All processes are finished");

	}

}
