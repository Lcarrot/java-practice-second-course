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
        boolean dontDo = true;
        while (dontDo) {
            for (PoolWorker worker : threads) {
                if (worker.getState().equals(Thread.State.WAITING)) {
                    synchronized (worker) {
                        worker.notify();
                    }
                    dontDo = false;
                    break;
                }
            }
        }
    }

    private class PoolWorker extends Thread {

        @Override
        public void run() {
            while (true) {
                waitThread();
                takeAndDoTask();
            }
        }

        private void waitThread() {
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new IllegalArgumentException();
                }
            }
        }

        private void takeAndDoTask() {
            Runnable task;
            synchronized (tasks) {
                task = tasks.poll();
            }
            if (task != null) task.run();
        }

    }
}

