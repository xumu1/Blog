import lombok.SneakyThrows;
import org.junit.Test;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description: date: 2020-10-28
 *
 * @author xumu
 */
public class testExecutors {
    @Test
    public void test1() throws Exception {
        // ThreadPoolExecutor threadPoolExecutor =
        // new ThreadPoolExecutor(10,
        // 20,
        // 1,
        // TimeUnit.MILLISECONDS,
        // new ArrayBlockingQueue<Runnable>(2));
        // for (int i = 0; i < 20; i++) {
        // threadPoolExecutor.submit(new Runnable() {
        // @Override
        // public void run() {
        // try {
        // Thread.sleep(500);
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // System.out.println("activeCount"+Thread.activeCount());
        // System.out.println("thread name"+Thread.currentThread().getName());
        // }
        // });
        // }
        // Thread.sleep(200);
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        System.out.println(allStackTraces.keySet().size());
        Set<Thread> set = allStackTraces.keySet();
        for (Thread item : set) {
            System.out.println(item.getName());
        }
        System.out.println(Thread.activeCount() + "==");
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();

        System.out.println(threadGroup.getParent().getName());
        System.out.println(threadGroup.getName());
        System.out.println(threadGroup.activeCount());
        threadGroup.list();
    }
}
