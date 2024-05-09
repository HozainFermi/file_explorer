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
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.text.TextAlignment;
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

//static String renametx;



    ArrayList<Node> fil = new ArrayList<Node>();

    @FXML
     void OnUserNameClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/UserNameScene.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("User Name");
        stage.setScene(new Scene(root1, 200, 155));
        stage.show();

    }

    @FXML
     void OnProcessPriorClicked(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/ProcessPriorityScene.fxml"));
       Parent root1 = (Parent) fxmlLoader.load();
       Stage stage = new Stage();
       stage.setTitle("Process Priority");
       stage.setScene(new Scene(root1, 200, 155));
       stage.show();


    }

    @FXML
     void OnNumberOfClicked(ActionEvent event) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/NumberOfScene.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Number of proceses");
        stage.setScene(new Scene(root1, 200 , 155));
        stage.show();
    }

    @FXML
    void OnReloadClicked(ActionEvent event){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/hello-view.fxml"));
                Parent root1 = null;
                try {
                    root1 = (Parent) fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                Stage current = (Stage) FilesPane.getScene().getWindow();
                current.setScene(new Scene(root1));
                //current.show();


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

           ArrayList<String> listfolders = ShellExec.ExecCommand("ls -d */");
           ArrayList<String> listfiles = ShellExec.ExecCommand("ls -p | grep -v /");

            for(String resp : listfolders) {
                resp=resp.replace("/","");
                if(resp.contains("main")) {
                    VBox v = new VBox();
                    ImageView iv = new ImageView();
                    Label filename = new Label();
                    Insets insets = new Insets(10, 5, 0, 0);
                    v.setPadding(insets);

                    File fl = new File("/home/me/IdeaProjects/demo/src/main/resources/folder-documents-icon(1).png");
                    Image img = new Image(fl.toURI().toString());
                    v.setPrefHeight(60);
                    v.setPrefWidth(60);
                    iv.setFitHeight(41.0);
                    iv.setFitWidth(41.0);
                    iv.setPickOnBounds(true);
                    iv.setPreserveRatio(true);
                    iv.setImage(img);
                    v.getChildren().add(iv);
                    filename.setText("System");
                    v.getChildren().add(filename);
                    v.setId(resp);
                    fil.add(v);
                }
                else if(resp.contains("TrashCan")) {
                    VBox v = new VBox();
                    ImageView iv = new ImageView();
                    Label filename = new Label();
                    Insets insets = new Insets(10, 5, 0, 0);
                    v.setPadding(insets);

                    File fl = new File("/home/me/IdeaProjects/demo/src/main/resources/delete(1).png");
                    Image img = new Image(fl.toURI().toString());
                    v.setPrefHeight(Region.USE_COMPUTED_SIZE);
                    v.setPrefWidth(60);
                    iv.setFitHeight(41.0);
                    iv.setFitWidth(41.0);
                    iv.setPickOnBounds(true);
                    iv.setPreserveRatio(true);
                    iv.setImage(img);
                    v.getChildren().add(iv);
                    filename.setText("Trash");
                    filename.setTextAlignment(TextAlignment.LEFT);
                    filename.setWrapText(true);
                    v.getChildren().add(filename);
                    v.setId(resp);

                    v.setOnMouseDragExited(new EventHandler<MouseDragEvent>() {
                        @Override
                        public void handle(MouseDragEvent mouseDragEvent) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    v.setStyle("-fx-background-color: transparent");
                                }
                            });
                        }
                    });


                    v.setOnMouseDragEntered(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    v.setStyle("-fx-background-color: rgba(13, 137, 209, 0.63)");
                                }
                            });
                        }
                    });

                    fil.add(v);
                }
                else{
                    fil.add(CreateNewFolder(resp));
                }
            }

            for(String str : listfiles){
                fil.add(CreateNew(str));
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    FilesPane.getChildren().setAll(fil);
                }
            });
        } catch(
                IOException e)
        {
            throw new RuntimeException(e);
        }
    }


    public void OnCreateNewFile(ActionEvent actionEvent) throws IOException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FilesPane.getChildren().add(CreateNew("New"));
            }
        });

        Runnable thr = ()-> {
            ArrayList<String> resp = null;
            try {
                resp = SocketClient.sendCommand("touch New");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (String r : resp) {
                System.out.println(r);
            }
        };
        Thread thread = new Thread(thr);
        thread.start();


    }

    public void OnCreateNewFolder(ActionEvent actionEvent) {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                FilesPane.getChildren().add(CreateNewFolder("NewFolder"));
            }
        });

        Runnable thr = ()-> {
            ArrayList<String> resp = null;
            try {
                resp = SocketClient.sendCommand("mkdir NewFolder");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            for (String r : resp) {
                System.out.println(r);
            }
        };
        Thread thread = new Thread(thr);
        thread.start();


    }

    public void OnShowDevises(ActionEvent actionEvent) throws IOException {
        ArrayList<String> list = ShellExec.ExecCommand("ls /media/me/");
        for(String resp : list){
            FolderVIewController.Getfn("/media/me/"+resp+"/");
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/FolderView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(resp);
            stage.setScene(new Scene(root1));
            stage.show();
        }



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
        v.setPrefHeight(Region.USE_COMPUTED_SIZE);
        v.setPrefWidth(60);
        iv.setFitHeight(41.0);
        iv.setFitWidth(41.0);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        iv.setImage(img);
        v.getChildren().add(iv);
        filename.setText(fn);
        filename.setWrapText(true);
        v.setId(fn+"<file>");


        ContextMenu cm = new ContextMenu();
        javafx.scene.control.MenuItem open = new javafx.scene.control.MenuItem("Open");
        javafx.scene.control.MenuItem rename = new javafx.scene.control.MenuItem("Rename");
        javafx.scene.control.MenuItem delete = new javafx.scene.control.MenuItem("Delete");
        javafx.scene.control.MenuItem copy = new MenuItem("Copy");

        cm.getItems().addAll(open,rename, copy, delete);
        filename.setContextMenu(cm);
        v.getChildren().add(filename);


        v.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                v.setStyle("-fx-border-color: black");

            }
        });



        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FilesPane.getChildren().remove(v);
                        v.getChildren().removeAll();
                    }
                });

                Thread rn = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<String> resp =  SocketClient.sendCommand("rm "+filename.getText());
                            for(String ln : resp){
                                System.out.println(ln);
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                rn.start();

            }
        });

        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Node copy;
                copy=CreateNew(fn+"COPY");
                copy.setId(v.getId()+"COPY");

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FilesPane.getChildren().add(copy);
                    }
                });

                Thread thr = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<String> resp =  SocketClient.sendCommand("cp "+filename.getText()+" "+filename.getText()+"COPY");
                            for(String ln : resp){
                                System.out.println(ln);
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                thr.start();

            }
        });

        open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                    Thread tr = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                SocketClient.sendCommand("gedit "+filename.getText());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    tr.start();
            }
        });

        rename.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AnchorPane root = new AnchorPane();
                Scene scene = new Scene(root);
                root.setPrefHeight(30);
                root.setPrefWidth(210);

                TextField tx = new TextField();
                tx.setPrefHeight(14);
                tx.setPrefWidth(157);
                tx.setLayoutY(4);

                Button btn = new Button();
                btn.setLayoutY(4);
                btn.setLayoutX(162);
                btn.setPrefHeight(24);
                btn.setPrefWidth(37);
                btn.setText("Ok");
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Thread tr = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    SocketClient.sendCommand("mv "+fn+" "+tx.getText());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                        tr.start();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //FilesPane.getScene().getWindow().setWidth(FilesPane.getScene().getWidth()+0.001);
                                filename.setText(tx.getText());
                            }
                        });
                        Stage st = (Stage) btn.getScene().getWindow();
                        st.hide();
                    }
                });

                root.getChildren().addAll(tx,btn);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
        });



        return v;
    }


    public  Node CreateNewFolder(String fn) {
        VBox v = new VBox();
        ImageView iv = new ImageView();
        Label filename = new Label();
        Insets insets = new Insets(10, 5, 0, 0);
        v.setPadding(insets);

        File fl = new File("/home/me/IdeaProjects/demo/src/main/resources/folder-documents-icon(1).png");
        Image img = new Image(fl.toURI().toString());
        v.setPrefHeight(Region.USE_COMPUTED_SIZE);
        v.setPrefWidth(60);
        iv.setFitHeight(41.0);
        iv.setFitWidth(41.0);
        iv.setPickOnBounds(true);
        iv.setPreserveRatio(true);
        iv.setImage(img);
        v.getChildren().add(iv);
        filename.setText(fn);
        filename.setWrapText(true);
        v.setId(fn+"<folder>");

        ContextMenu cm = new ContextMenu();

        javafx.scene.control.MenuItem open = new javafx.scene.control.MenuItem("Open");
        javafx.scene.control.MenuItem rename = new MenuItem("Rename");
        javafx.scene.control.MenuItem delete = new javafx.scene.control.MenuItem("Delete");
        javafx.scene.control.MenuItem copy = new MenuItem("Copy");

        cm.getItems().addAll(open,rename, copy, delete);
        filename.setContextMenu(cm);
        v.getChildren().add(filename);
        v.setId(fn);

        v.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                v.setStyle("-fx-background-color: rgba(13, 137, 209, 0.63)");
            }
        });

        v.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                //!!!
            }
        });

        v.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                v.setStyle("-fx-background-color: transparent");
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FilesPane.getChildren().remove(v);
                        v.getChildren().removeAll();
                    }
                });

                Thread rn = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<String> resp =  SocketClient.sendCommand("rm -r "+filename.getText());
                            for(String ln : resp){
                                System.out.println(ln);
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                rn.start();
            }
        });

        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Node copy;
                copy=CreateNewFolder(fn+"COPY");
                copy.setId(v.getId()+"COPY");

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        FilesPane.getChildren().add(copy);
                    }
                });

                Thread thr = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ArrayList<String> resp =  SocketClient.sendCommand("cp -r "+filename.getText()+" "+filename.getText()+"COPY");
                            for(String ln : resp){
                                System.out.println(ln);
                            }

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                thr.start();

            }
        });

        open.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent actionEvent) {
                try {

                    FolderVIewController.Getfn(fn);
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/FolderView.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle(filename.getText());
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        rename.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                AnchorPane root = new AnchorPane();
                Scene scene = new Scene(root);
                root.setPrefHeight(30);
                root.setPrefWidth(210);

                TextField tx = new TextField();
                tx.setPrefHeight(14);
                tx.setPrefWidth(157);
                tx.setLayoutY(4);

                Button btn = new Button();
                btn.setLayoutY(4);
                btn.setLayoutX(162);
                btn.setPrefHeight(24);
                btn.setPrefWidth(37);
                btn.setText("Ok");
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Thread tr = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    SocketClient.sendCommand("mv "+fn+" "+tx.getText());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                        tr.start();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //FilesPane.getScene().getWindow().setWidth(FilesPane.getScene().getWidth()+0.001);
                                filename.setText(tx.getText());
                            }
                        });
                        Stage st = (Stage) btn.getScene().getWindow();
                        st.hide();
                    }
                });

                root.getChildren().addAll(tx,btn);

                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
        });

        return v;
    }


}