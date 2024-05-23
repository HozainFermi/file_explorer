package com.lab1.demo.Model;

import com.lab1.demo.Controller.FullInfoTCPclientController;
import com.lab1.demo.Controller.ShellExec;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class TCPClient {
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    public static String str;
    public AnchorPane AnchorPaneClient;


    public static void runifno(){

        Thread infothread = new Thread(new Runnable() {
            @Override
            public void run() {

                while(true){
                   // if(flag==true) {
                        try {
                            Thread.sleep(500);
                            startConnection();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }


                        try {
                            out.println("------User name------");
                            str += "------User name------";
                            TCPServer.counter++;

                            ArrayList<String> usr = ShellExec.ExecCommand("whoami");
                            out.println(usr.getFirst());
                            str += "\n" + usr.getFirst();
                            TCPServer.counter++;
                            out.println("------Process priority------");
                            str += "\n------Process priority------";
                            TCPServer.counter++;
                            ArrayList<String> prior = ShellExec.ExecCommand("ps -eo user,nice,comm | grep java");
                            for (int i = 0; i < prior.size(); i++) {
                                out.println(prior.get(i));
                                str += "\n" + prior.get(i);
                                TCPServer.counter++;
                            }

                            out.println("------Number of processes------");
                            str += "\n------Number of processes------";
                            TCPServer.counter++;
                            ArrayList<String> list = ShellExec.ExecCommand("ps -eo user,pid,pcpu,nice,comm | grep -v java");
                            out.println(list.size());
                            str += "\n" + list.size();
                            TCPServer.counter++;
                            for (int i = 0; i < list.size(); i++) {
                                out.println(list.get(i));
                                str += "\n" + list.get(i);
                                TCPServer.counter++;
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        FullInfoTCPclientController.string=str;

                        str = "";


                    //}
                }
            }
        });
        infothread.start();
    }


    public static void startConnection(){
        if(clientSocket==null) {
            try {
                clientSocket = new Socket("127.0.0.1", 8091);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }



}
