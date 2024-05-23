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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NumberOfController implements Initializable {

    @FXML
    public Label NumberOfLabel;

    @FXML
    public AnchorPane NumberOfAnchorPane;
    String fullstr="";

    @FXML
    void BackBtnClicked(ActionEvent event) throws IOException {
        new SceneSwitch(NumberOfAnchorPane,"View/hello-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // NumberOfLabel.setWrapText(true);

        try {
            ArrayList<String> list = ShellExec.ExecCommand("ps -eo user,pid,pcpu,nice,comm | grep -v java");
             //fullstr="";
            fullstr+=list.size();
            for(int i=0;i<list.size();i++){
                fullstr+="\n";
                fullstr+=list.get(i);
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    NumberOfLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
                    NumberOfLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    NumberOfLabel.setText(fullstr);

                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
