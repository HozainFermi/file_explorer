package com.lab1.demo.Controller;

import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.lab1.demo.HelloApplication;
import com.lab1.demo.Model.SceneSwitch;
import com.lab1.demo.Model.SocketClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    public AnchorPane scene1AnchorPane;

    @FXML
    public FlowPane FilesPane;





    ArrayList<Node> fil = new ArrayList<Node>();

    @FXML
     void OnUserNameClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/UserNameScene.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("User Name");
        stage.setScene(new Scene(root1, 100, 50));
        stage.show();

    }

    @FXML
     void OnProcessPriorClicked(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/ProcessPriorityScene.fxml"));
       Parent root1 = (Parent) fxmlLoader.load();
       Stage stage = new Stage();
       stage.setTitle("Process Priority");
       stage.setScene(new Scene(root1, 100, 50));
       stage.show();


    }

    @FXML
     void OnNumberOfClicked(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/NumberOfScene.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Number of proceses");
        stage.setScene(new Scene(root1, 100 , 50));
        stage.show();
    }

    @FXML
    void OnReloadClicked(ActionEvent event){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               FilesPane.getChildren().add(CreateNew("Test"));
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            SocketClient.startConnection("127.0.0.1", 8080);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try

        {
           ArrayList<String> list = ShellExec.ExecCommand("ls");

            for(String resp : list) {
                if (resp.contains("\n")) {
                    resp = resp.replace("\n", "");
                }
                VBox v = new VBox();
                ImageView iv = new ImageView();
                Label filename = new Label();
                Insets insets = new Insets(10, 5, 0, 0);
                v.setPadding(insets);

                File fl = new File("/home/me/IdeaProjects/demo/src/main/resources/copy_paste_document_file_1557.png");
                Image img = new Image(fl.toURI().toString());
                v.setPrefHeight(60);
                v.setPrefWidth(60);
                iv.setFitHeight(41.0);
                iv.setFitWidth(41.0);
                iv.setPickOnBounds(true);
                iv.setPreserveRatio(true);
                iv.setImage(img);
                v.getChildren().add(iv);
                filename.setText(resp);
                v.getChildren().add(filename);
                v.setId(resp);


                fil.add(v);
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    FilesPane.getChildren().setAll(fil);

                }
            });

           //FilesPane.getChildren().setAll(fil);
        } catch(
                IOException e)

        {
            throw new RuntimeException(e);
        }
    }


    public void OnCreateNewFile(ActionEvent actionEvent) {
    }

    public void OnCreateNewFolder(ActionEvent actionEvent) {
    }

    public void OnConsoleClicked(ActionEvent actionEvent) {
    }

    public  Node CreateNew(String fn) {
        VBox v = new VBox();
        ImageView iv = new ImageView();
        Label filename = new Label();
        Insets insets = new Insets(10, 5, 0, 0);
        v.setPadding(insets);

        File fl = new File("/home/me/IdeaProjects/demo/src/main/resources/copy_paste_document_file_1557.png");
        Image img = new Image(fl.toURI().toString());
        v.setPrefHeight(60);
        v.setPrefWidth(60);
        iv.setFitHeight(41.0);
        iv.setFitWidth(41.0);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        iv.setImage(img);
        v.getChildren().add(iv);
        filename.setText(fn);

        ContextMenu cm = new ContextMenu();
        javafx.scene.control.MenuItem delete = new javafx.scene.control.MenuItem("Delete");
        javafx.scene.control.MenuItem copy = new MenuItem("Copy");

        cm.getItems().addAll(delete, copy);
        filename.setContextMenu(cm);
        v.getChildren().add(filename);

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                v.getChildren().removeAll();
                try {
                    ArrayList<String> resp =  SocketClient.sendCommand("rm "+fn);
                    for(String ln : resp){
                        System.out.println(ln);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FilesPane.getChildren().add(v);
                    }
                });
                try {
                    ArrayList<String> resp =  SocketClient.sendCommand("cp "+fn+" "+fn+"COPY");
                    for(String ln : resp){
                        System.out.println(ln);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });




        return v;
    }

}