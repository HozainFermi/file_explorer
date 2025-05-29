package com.lab1.demo.Controller;

import com.lab1.demo.Model.SceneSwitch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProcessPriorityController implements Initializable {

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;

    @FXML
    public Label ProcessPriorityLabel;

    @FXML
    public AnchorPane ProcessPriorityAnchorPane;
    String fullstr="";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ArrayList<String> list = ShellExec.ExecCommand("ps -eo user,nice,command | grep java");
            for(int i=0;i<list.size();i++){
                fullstr+="\n";
                fullstr+=list.get(i);
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ProcessPriorityLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    ProcessPriorityLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    ProcessPriorityLabel.setText(fullstr);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
