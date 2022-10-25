package org.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Processor of HTTP request.
 */
public class Processor extends Thread {
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    public void process(int x) throws IOException {
        // Print request that we received.

        System.out.println("Got request:");
        System.out.println(request.toString());
        System.out.flush();

        // To send response back to the client.
        PrintWriter output = new PrintWriter(socket.getOutputStream());

        // We are returning a simple web page now.
        if(x==4) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Hello</title></head>");
            output.println("<body><p>Hello, world!</p></body>");
            output.print("<body><p>Number of Thread: </p></body>");
            output.println("</html>");
            output.flush();
        }
        if(x==1) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Create file</title></head>");
            output.println("<body><p>Created file!</p></body>");
            output.println("</html>");
            output.flush();
        }

        if(x==2) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Deleted file</title></head>");
            output.println("<body><p>Deleted file!</p></body>");
            output.println("</html>");
            output.flush();
        }
        if(x==3) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Compute</title></head>");
            output.println("<body><p>Computed!</p></body>");
            output.println("</html>");
            output.flush();
        }
        socket.close();
    }

}
