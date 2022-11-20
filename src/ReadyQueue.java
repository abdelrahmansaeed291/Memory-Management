import java.util.LinkedList;
import java.util.Queue;
public class ReadyQueue {
	
	
	static Queue<Integer> ReadyQueue = new LinkedList<>();
	
	
	public ReadyQueue() {
		
	}
	public static Queue<Integer> getReadyQueue() {
		return ReadyQueue;
	}
	public static void setReadyQueue(Queue<Integer> readyQueue) {
		ReadyQueue = readyQueue;
	}
	public static void addToReadyQueue(Integer p) {
		ReadyQueue.add(p);
	}
	public static Integer removeFromReadyQueue() {
		return ReadyQueue.poll();
	}
	public static boolean isEmpty() {
		return ReadyQueue.isEmpty();
	}
	public static  void display() {
        String s = "Ready Queue: * ";
        if(!ReadyQueue.isEmpty()) {
            for (int i = 0; i < ReadyQueue.size(); i++) {
                s = s +"[" + ReadyQueue.peek().toString() + "], ";
                ReadyQueue.add(ReadyQueue.remove());
            }
        }else {
            s = s + "The Queue is Empty";
        }
		System.out.println(s);

    } 


}
