package com.lab1.demo.Controller;

import com.lab1.demo.HelloApplication;
import com.lab1.demo.Model.TCPClient;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FullInfoTCPclientController implements Initializable {

    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    public TextFlow InfoFlow;

    public static String string;
    public AnchorPane AnchorPaneClient;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TCPClient.startConnection();
        TCPClient.runifno();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){

                    try {
                        Thread.sleep(900);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            InfoFlow.getChildren().clear();
                            InfoFlow.getChildren().setAll(new Text(string));
                        }
                    });

                }
            }
        });
        thread.start();

    }




}
