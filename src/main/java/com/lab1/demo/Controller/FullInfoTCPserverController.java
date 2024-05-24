package com.lab1.demo.Controller;

import com.lab1.demo.Model.TCPServer;
import javafx.application.Platform;

import javafx.fxml.Initializable;

import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;




import java.io.BufferedReader;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import java.util.ResourceBundle;

public class FullInfoTCPserverController implements Initializable {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    public TextFlow ServerTextFLow;

    public AnchorPane AnchorPaneServer;

    public String string;
    public String full;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                TCPServer.startServer();
                TCPServer.runinfo();


                while (true){

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    synchronized (TCPServer.flag){
                        string=TCPServer.mainstr;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                ServerTextFLow.getChildren().clear();
                                ServerTextFLow.getChildren().setAll(new Text(string));
                            }
                        });
                    }

                }
            }
        });
        thread.start();


    }


}
