import java.util.PriorityQueue;

public class PriorityQueueDemo {
    public static int tmp = 0;

    public static void main(String[] args) {
//        PriorityQueue<Integer> queue = new PriorityQueue<>(2, (a, b) -> a - b);
//        queue.add(5);
//        queue.add(1);
//        queue.add(3);
//        if (!queue.isEmtpy()) {
//            System.out.println(queue.remove());
//        }
        for (int m = 0; m < 10; m++) {
            for (int i = 0; i < 500; i++) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 100000; j++) {
                            PriorityQueueDemo.tmp++;
                        }
                    }
                }).run();
            }
            System.out.println(tmp);
        }

    }
}
