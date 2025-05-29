package com.lab1.demo;

import com.lab1.demo.Controller.HelloController;
import com.lab1.demo.Controller.ShellExec;
import com.lab1.demo.Model.SocketClientConsole;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

       // new LoadFiles().run();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 316);
        stage.setTitle("SuperApp");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            HelloController.shutdown();
                            SocketClientConsole.stopConnection();
                            ArrayList<String> resp = ShellExec.ExecCommand("kill -9 $(lsof -t -i:8090)");

                           System.out.println(resp.getFirst());

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                thread.start();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}


