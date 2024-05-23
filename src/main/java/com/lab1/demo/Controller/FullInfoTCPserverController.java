package com.lab1.demo.Controller;

import com.lab1.demo.Model.TCPServer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FullInfoTCPserverController implements Initializable {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    public TextFlow ServerTextFLow;

    public AnchorPane AnchorPaneServer;

    public  static String string;
    ArrayList<Node> list = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                TCPServer.startServer();
                TCPServer.runinfo();


                while (true){

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if(string=="------User name------"){
                               ServerTextFLow.getChildren().clear();
                               ServerTextFLow.getChildren().add(new Text(string));  // !!!!
                            }
                            else {
                                ServerTextFLow.getChildren().add(new Text(string));
                            }
                        }
                    });

                }
            }
        });
        thread.start();


    }


}
