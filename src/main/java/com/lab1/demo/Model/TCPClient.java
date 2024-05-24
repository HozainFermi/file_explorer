package com.lab1.demo.Model;

import com.lab1.demo.Controller.FullInfoTCPclientController;
import com.lab1.demo.Controller.ShellExec;



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


                            ArrayList<String> usr = ShellExec.ExecCommand("whoami");
                            out.println(usr.getFirst());
                            str += "\n" + usr.getFirst();

                            out.println("------Process priority------");
                            str += "\n------Process priority------";

                            ArrayList<String> prior = ShellExec.ExecCommand("ps -eo user,nice,comm | grep java");
                            for (int i = 0; i < prior.size(); i++) {
                                out.println(prior.get(i));
                                str += "\n" + prior.get(i);

                            }

                            out.println("------Number of processes------");
                            str += "\n------Number of processes------";

                            ArrayList<String> list = ShellExec.ExecCommand("ps -eo user,pid,pcpu,nice,comm | grep -v java");
                            out.println(list.size());
                            str += "\n" + list.size();

                            for (int i = 0; i < list.size(); i++) {
                                out.println(list.get(i));
                                str += "\n" + list.get(i);

                            }
                            out.println("end");
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

    public static void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }



}
