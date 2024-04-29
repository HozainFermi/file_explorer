package com.lab1.demo.Controller;

import com.lab1.demo.Model.SceneSwitch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProcessPriorityController implements Initializable {

    @FXML
    public Label ProcessPriorityLabel;

    @FXML
    public AnchorPane ProcessPriorityAnchorPane;

    @FXML
    void BackBtnClicked(ActionEvent event) throws IOException {
        new SceneSwitch(ProcessPriorityAnchorPane,"View/hello-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
