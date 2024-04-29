package com.lab1.demo.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SocketClient {

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;


    public static void startConnection(String ip, int port) throws IOException {
        if(clientSocket==null) {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
        }

    }

    public static ArrayList<String> sendCommand(String msg) throws IOException{
        out.println(msg);
        String line;
        ArrayList<String> resp = new ArrayList<String>();
        //Thread.sleep(5);
        while((line=in.readLine()) != null) {
            System.out.println("Response:"+line);
            resp.add(line);
        }
        return resp;

    }

    public static void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }




}
