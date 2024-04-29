package com.lab1.demo.Controller;

import com.lab1.demo.HelloApplication;
import com.lab1.demo.Model.SocketClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.synedra.validatorfx.Check;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class CreateNewFile {

        public static Node CreateNewNode(String fn) {
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
                           // FilesPane.getChildren().add(v);
                        }
                    });
                    try {
                        ArrayList<String> resp =  SocketClient.sendCommand("cp "+fn);
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

