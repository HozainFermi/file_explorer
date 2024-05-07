package com.lab1.demo.Controller;

import com.lab1.demo.Model.SceneSwitch;
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

public class ProcessPriorityController implements Initializable {

    @FXML
    public Label ProcessPriorityLabel;

    @FXML
    public AnchorPane ProcessPriorityAnchorPane;
    String fullstr="";

    @FXML
    void BackBtnClicked(ActionEvent event) throws IOException {
        new SceneSwitch(ProcessPriorityAnchorPane,"View/hello-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<String> list = ShellExec.ExecCommand("ps -eo user,nice,comm | grep java");
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
