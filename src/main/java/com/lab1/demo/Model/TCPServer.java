package com.lab1.demo.Model;

import com.lab1.demo.Controller.FullInfoTCPserverController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

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

    static String str="";
    static String buf="";
    static int counter=0;
    static boolean flag =false;

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
                            if ((buf = in.readLine()) != null) {
                                str += "\n" + buf;
                                FullInfoTCPserverController.string=str;
                               // counter++;

                            } else {
                               // str = "";
                               // counter=0;
                            }


                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                }
            }
        });
        thread.start();
    }

    public void shotdown() throws IOException {

        clientSocket.close();
        serverSocket.close();
        in.close();
        out.close();
    }
}
