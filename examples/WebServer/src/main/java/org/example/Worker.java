package org.example;

import java.io.BufferedReader;
import java.net.Socket;

public class Worker extends Thread {
    private int id;
    private Socket socket;
    public WorkerQueue<Processor> queue;

    public Worker(int id, WorkerQueue<Processor> queue) {
        this.id = id;
        this.socket = socket;
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            // Get request
            while (true) {
                //BufferedReader input = queue.pop_element();
                //if(input == null) return;
                //HttpRequest request = HttpRequest.parse(input);
                // Process request
                Processor one = queue.pop_element();
                if (one == null) {
                    return;
                }
                try {
                    one.process(0);
                    System.out.println("---------------------------------------");
                    System.out.println("Thread " + id + " worked: ");
                    System.out.println("---------------------------------------");
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
}
