package com.lab1.demo.Controller;

import com.lab1.demo.Model.SceneSwitch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class UserNameController implements Initializable {

    private  Socket clientSocket;
    private  PrintWriter out;
    private  BufferedReader in;

    final static Boolean key=true;
    String buf="";

    @FXML
    public Label UserNameLabel;

    @FXML
    public AnchorPane UserNameAnchorPane;

    public  void startConnection(){

            try {
                clientSocket = new Socket("127.0.0.1", 8092);
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader((clientSocket.getInputStream())));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


    }

    public  void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
               startConnection();
                synchronized (HelloController.key) {
                    try {
                        buf = in.readLine();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                UserNameLabel.setText(buf);

                               try {
                                  stopConnection();
                                  // HelloController.shu();
                               } catch (IOException e) {
                                   throw new RuntimeException(e);
                               }
                            }
                        });

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();

      // try {
      //     ArrayList<String> list = ShellExec.ExecCommand("whoami");
      //     Platform.runLater(new Runnable() {
      //         @Override
      //         public void run() {

      //             UserNameLabel.setText(list.get(0));
      //         }
      //     });
      // } catch (IOException e) {
      //     throw new RuntimeException(e);
      // }

    }
}
