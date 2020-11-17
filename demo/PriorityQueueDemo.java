import java.util.PriorityQueue;

public class PriorityQueueDemo {
    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(2, (a, b) -> a - b);
        queue.add(5);
        queue.add(1);
        queue.add(3);
        if (!queue.isEmtpy()) {
            System.out.println(queue.remove());
        }
    }
}
