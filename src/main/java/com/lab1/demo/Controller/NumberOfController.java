package com.lab1.demo.Controller;

import com.lab1.demo.Model.SceneSwitch;
import com.lab1.demo.Model.SocketClientConsole;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class NumberOfController implements Initializable {

    public  Socket clientSocket;
    public  PrintWriter out;
    public  BufferedReader in;

    public void startConnection(){

            try {
                clientSocket = new Socket("127.0.0.1", 8092);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }


    @FXML
    public Label NumberOfLabel;

    @FXML
    public AnchorPane NumberOfAnchorPane;
    String fullstr="";
    String buf="";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // NumberOfLabel.setWrapText(true);


            Thread readthrear = new Thread(new Runnable() {
                @Override
                public void run() {
                    startConnection();
                    synchronized (HelloController.key){
                        try {
                           fullstr=in.readLine();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    NumberOfLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
                                    NumberOfLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);
                                    NumberOfLabel.setText(fullstr);
                                   try {
                                       stopConnection();
                                   } catch (IOException e) {
                                       throw new RuntimeException(e);
                                   }
                                }
                            });
                        }
                        catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            readthrear.start();

    }
}
