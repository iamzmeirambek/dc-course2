package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * Simple web server.
 */
public class WebServer {
    public static void main(String[] args) {
        // Port number for http request
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 8080;
        // The maximum queue length for incoming connection
        int queueLength = args.length > 2 ? Integer.parseInt(args[2]) : 50;;
        int numOfThreads = args.length > 1 ? Integer.parseInt(args[1]) : 4;

        try (ServerSocket serverSocket = new ServerSocket(port, queueLength)) {
            System.out.println("Web Server is starting up, listening at port " + port + ".");
            System.out.println("You can access http://localhost:" + port + " now.");

            WorkerQueue<Processor> queue = new WorkerQueue<>();

            for (int i = 0; i < numOfThreads; i++) {
                Worker thread = new Worker(i, queue);
                thread.start();
            }


            while (true) {
                // Make the server socket wait for the next client request
                Socket socket = serverSocket.accept();
                System.out.println("Got connection!");
                // To read input from the client
                BufferedReader input = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

                try {
                    // Get request
                    HttpRequest request = HttpRequest.parse(input);
                    queue.add_element(new Processor(socket, request));
                    String data = request.getRequestLine();
                    String data1 = data.substring(5, 11);
                    System.out.println("lenght: " + data.length());
                        if (data1.equals("create")) {
                            String data2 = data.substring(12, (data.length() - 9));
                            System.out.println(data2);
                            createfile(data2);
                            Processor create = new Processor(socket, request);
                            create.process(1);
                        }

                        if (data1.equals("delete")) {
                            String data2 = data.substring(12, (data.length() - 9));
                            System.out.println(data2);
                            deletefile(data2);
                            Processor delete = new Processor(socket, request);
                            delete.process(2);
                        }
                        if (data1.equals("comput")) {
                            String data2 = data.substring(12, 20);
                            String data3 = data.substring(21, (data.length() - 9));
                            computefile(data2, data3);
                            Processor computed = new Processor(socket, request);
                            computed.process(3);
                        }
                    else{
                        Processor processor = new Processor(socket, request);
                        processor.process(4);
                    }
                }catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            System.out.println("Server has been shutdown!");
        }
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
