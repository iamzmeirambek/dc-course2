package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class WorkerQueue<T> {
    private final Queue<T> queue;

    public WorkerQueue() {
        this.queue = new LinkedList<>();
    }

    public synchronized void add_element(T element) {
        queue.add(element);
        notify();
    }

    public synchronized T pop_element() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        return this.queue.poll();
    }

    public synchronized int size() {
        return queue.size();
    }
}
