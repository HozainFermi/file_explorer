package com.lab1.demo.Controller;

import com.lab1.demo.Model.SceneSwitch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserNameController implements Initializable {

    @FXML
    public Label UserNameLabel;

    @FXML
    public AnchorPane UserNameAnchorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ArrayList<String> list = ShellExec.ExecCommand("whoami");
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    UserNameLabel.setText(list.get(0));
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
