package com.lab1.demo.Controller;

import com.lab1.demo.Model.SceneSwitch;
import com.lab1.demo.Model.SocketClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NumberOfController implements Initializable {

    @FXML
    public Label NumberOfLabel;

    @FXML
    public AnchorPane NumberOfAnchorPane;

    @FXML
    void BackBtnClicked(ActionEvent event) throws IOException {
        new SceneSwitch(NumberOfAnchorPane,"View/hello-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



    }
}
