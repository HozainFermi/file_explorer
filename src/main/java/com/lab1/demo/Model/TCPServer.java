package com.lab1.demo.Model;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static String str="";
    public static String mainstr;
    static String buf="";
    static int counter=0;
    public final static Object flag =false;

    public static void startServer(){
        try {
            serverSocket = new ServerSocket(8091);
            System.out.println("Server is ready, waiting for connections");
            clientSocket = serverSocket.accept();

            System.out.println("Connection accepted");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runinfo(){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){


                        try {
                            while ((buf = in.readLine()) != null) {
                                if(buf.equals("end")){

                                    //flag.wait(200);
                                    break;
                                }
                                str += "\n" + buf;

                            }
                            mainstr=str;
                            str="";
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                }
            }
        });
        thread.start();
    }

    public static void shotdown() throws IOException {

        clientSocket.close();
        serverSocket.close();
        in.close();
        out.close();
    }
}
