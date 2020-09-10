import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadPool {

    private Queue<Runnable> tasks;
    private PoolWorker[] threads;

    public ThreadPool(int threadsCount) {
        this.tasks = new ConcurrentLinkedQueue<>();
        this.threads = new PoolWorker[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void submit(Runnable task) {
        synchronized (tasks) {
            tasks.offer(task);
        }
    }

    private class PoolWorker extends Thread {
        @Override
        public void run() {
            while (true) {
                Runnable task;
                synchronized (tasks) {
                    task = tasks.poll();
                }
                if (task != null) task.run();
            }
        }
    }
}

