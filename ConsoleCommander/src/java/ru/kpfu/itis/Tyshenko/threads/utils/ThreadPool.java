package ru.kpfu.itis.Tyshenko.threads.utils;

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
        addTask(task);
        giveToThread();
    }

    private void addTask(Runnable task) {
        synchronized (tasks) {
            tasks.offer(task);
        }
    }

    private void giveToThread() {
        boolean dontGiveToThread = true;
        while (dontGiveToThread) {
            for (PoolWorker worker : threads) {
                if (worker.getState().equals(Thread.State.WAITING)) {
                    synchronized (worker) {
                        worker.notify();
                    }
                    dontGiveToThread = false;
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
