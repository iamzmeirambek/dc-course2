package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        String data = request.getRequestLine();
        String data1 = data.substring(5, 11);
        System.out.println("lenght: " + data.length());
        // We are returning a simple web page now.
        //
         if(request.getRequestLine().equals("GET /create/itemid HTTP/1.1")) {
            String data2 = data.substring(12, (data.length() - 9));
            System.out.println(data2);
            createfile(data2);
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Create file</title></head>");
            output.println("<body><p>Created file!</p></body>");
            output.println("</html>");
            output.flush();
        }

        else if(request.getRequestLine().equals("GET /delete/itemid HTTP/1.1")) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Deleted file</title></head>");
            output.println("<body><p>Deleted file!</p></body>");
            output.println("</html>");
            output.flush();
        }
        else if(request.getRequestLine().equals("GET /comput/dat1.txt/alsjdflkajsdklfjaksldjfk HTTP/1.1")) {
            output.println("HTTP/1.1 200 OK");
            output.println("Content-Type: text/html; charset=utf-8");
            output.println();
            output.println("<html>");
            output.println("<head><title>Compute</title></head>");
            output.println("<body><p>Computed!</p></body>");
            output.println("</html>");
            output.flush();
        }
         else{
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
        socket.close();
    }
    private static void computefile(String data2, String data3) {
        try(FileWriter writer = new FileWriter(data2, false))
        {
            // запись всей строки
            writer.write(data3);
            // запись по символам
            writer.append('\n');
            writer.flush();
            Hamming.main(data2,data3);
            Thread.sleep(100);
        }
        catch(IOException ex){
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private static void deletefile(String data2) throws IOException {
        Files.delete(Paths.get(data2));
    }


    private static void createfile(String data2) {
        try {
            File myObj = new File(data2);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
