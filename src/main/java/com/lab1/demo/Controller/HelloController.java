package com.lab1.demo.Controller;

import com.almasb.fxgl.dsl.components.view.ChildViewComponent;
import com.lab1.demo.HelloApplication;
import com.lab1.demo.Model.SceneSwitch;
import com.lab1.demo.Model.SocketClientConsole;
import com.lab1.demo.Model.TCPClient;
import com.lab1.demo.Model.TCPServer;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
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
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class HelloController implements Initializable {
    @FXML
    public AnchorPane scene1AnchorPane;

    @FXML
    public FlowPane FilesPane;
    public MenuItem ReloadBtn;
    public MenuItem CreateNewFolderBtn;
    public MenuItem CreateNewFileBtn;
    public MenuItem ConsoleBtn;
    public MenuItem SaveReportBtn;
    public MenuItem ShowInfoBtn;
    public TextField FindTextField;

    String mainpath = HelloController.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    ArrayList<Node> fil = new ArrayList<Node>();

    private static ServerSocket serverSocket;
    private static Socket clientSocket;
    private static PrintWriter out;
    private static BufferedReader in;
    final static AtomicBoolean key = new AtomicBoolean();


    public static void startServer() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //serverSocket = new ServerSocket(8092);
                    System.out.println("Server is ready, waiting for connections");
                    clientSocket = serverSocket.accept();
                    System.out.println("Connection accepted");
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        thread.start();
    }

    public static void shutdown() throws IOException {

        //clientSocket.close();
        serverSocket.close();
        in.close();
        out.close();
    }


    @FXML
     void OnUserNameClicked(ActionEvent event) throws IOException {
        Thread serverthread = new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        });
        serverthread.start();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/UserNameScene.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("User Name");
        stage.setScene(new Scene(root1, 200, 155));
        stage.show();


      stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent windowEvent) {
            //  try {
            //      UserNameController.stopConnection();
            //  } catch (IOException e) {
            //      throw new RuntimeException(e);
            //  }
          }
      });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<String> list = ShellExec.ExecCommand("whoami");
                    out.println(list.getFirst());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        thread.start();
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
        Thread serverthread = new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        });
        serverthread.start();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/NumberOfScene.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Number of proceses");
        stage.setScene(new Scene(root1, 200 , 155));
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
              //  try {
              //      NumberOfController.stopConnection();
              //  } catch (IOException e) {
              //      throw new RuntimeException(e);
              //  }
            }
        });

        Thread numberofthread = new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> list = null;
                try {
                    list = ShellExec.ExecCommand("ps -aux --no-headers | grep -v java | wc -l"); // vse proc krome programmi
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String buf="";
                buf+="--"+list.getFirst();
                out.println(buf);
            }
        });
        numberofthread.start();
    }

    @FXML
    void OnShowInfoCLicked(ActionEvent event) throws IOException{

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/FullInfoTCPserverView.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Info server");
        stage.setScene(new Scene(root1, 600 , 400));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                try {
                    TCPServer.shotdown();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                FXMLLoader fxmlLoader2 = new FXMLLoader(HelloApplication.class.getResource("View/FullInfoTCPclientView.fxml"));
                Parent root2 = null;
                try {
                    root2 = (Parent) fxmlLoader2.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage2 = new Stage();
                stage2.setTitle("Info client");
                stage2.setScene(new Scene(root2, 600, 400));
                stage2.show();
                stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        try {
                            TCPClient.stopConnection();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });


    }

    public void OnSaveReportClicked(ActionEvent event) throws IOException {

        ShellExec.ExecCommand("touch Report-information");
        String PATH = mainpath.replace("main/","")+"Report-information"; //!!!
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(PATH)));

        writer.println("------User name------");
        ArrayList<String> usr = ShellExec.ExecCommand("whoami");
        writer.println(usr.getFirst());

        writer.println("------Process priority------");
        ArrayList<String> prior = ShellExec.ExecCommand("ps -eo user,nice,comm | grep java");
        for(int i=0;i<prior.size();i++){
            writer.println(prior.get(i));
        }

        writer.println("------Number of processes------");
        ArrayList<String> list = ShellExec.ExecCommand("ps -eo command,lstart | grep -v java");
        writer.println(list.size()-5);
        for(int i=0;i<list.size()-5;i++){
            if(list.get(i).contains("SuperApp")){continue;}
            writer.println(list.get(i));
        }

        writer.close();
    }
    public void OnSaveLogClicked(ActionEvent event) throws IOException {
        ObservableList<Node> children = FilesPane.getChildren();
        Boolean noLogFile=true;
        String PATH = mainpath.replace("main/","")+"Log";
        ArrayList<String> username = ShellExec.ExecCommand("whoami");
        Calendar now = Calendar.getInstance();

        for(Node chield : children){
            VBox ch = (VBox)chield;
            Label name = (Label) ch.getChildren();
            if(name.getText()=="Log"){
                noLogFile=false;
                FileWriter writer = new FileWriter(PATH, true);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                bufferedWriter.write(now.get(Calendar.DAY_OF_MONTH)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.YEAR)+" "+ now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+
                        " "+username.getFirst()+"\n");
                bufferedWriter.close();
            }
        }
        if(noLogFile){
            ShellExec.ExecCommand("touch Log");
            FileWriter writer = new FileWriter(PATH, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(now.get(Calendar.DAY_OF_MONTH)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.YEAR)+" "+ now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+
                    " "+username.getFirst()+"\n");
            bufferedWriter.close();
        }
    }


    @FXML
    void OnReloadClicked(ActionEvent event){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    serverSocket.close();
                    if(clientSocket!=null){
                        clientSocket.close();
                    }
                    if(in!=null){
                        in.close();
                    }
                    if(out!=null){
                        out.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/hello-view.fxml"));
                Parent root1 = null;
                try {
                    root1 = (Parent) fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage current = (Stage) FilesPane.getScene().getWindow();
                current.setScene(new Scene(root1));
            }
        });
    }

    public void OnDisksClicked(ActionEvent event) {
        Thread diskthread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ShellExec.ExecCommand("gnome-disks");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        diskthread.start();
    }

    public void OnDiskAnalyzer(ActionEvent event) {
        Thread diskanalyzerthread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ShellExec.ExecCommand("baobab");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        diskanalyzerthread.start();
    }
    public void OnTerminalClicked(ActionEvent event) {
        Thread terminalthread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ShellExec.ExecCommand("gnome-terminal");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        terminalthread.start();
    }

    public void OnFindBtnClicked(ActionEvent event) throws IOException {
        String needfind=FindTextField.getText();
        ArrayList<String> list = ShellExec.ExecCommand("find . -name "+needfind);
        for(String pathtoneed : list ){
            pathtoneed=pathtoneed.replace("./","");
            pathtoneed=pathtoneed.replace("/"+needfind,"");
            try {
                FolderVIewController.Getfn(pathtoneed);
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/FolderView.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle(pathtoneed);
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ReloadBtn.setAccelerator(new KeyCodeCombination(KeyCode.R,KeyCombination.CONTROL_DOWN));
        CreateNewFolderBtn.setAccelerator(new KeyCodeCombination(KeyCode.E,KeyCombination.CONTROL_DOWN));
        CreateNewFileBtn.setAccelerator(new KeyCodeCombination(KeyCode.X,KeyCombination.CONTROL_DOWN));
        ConsoleBtn.setAccelerator(new KeyCodeCombination(KeyCode.T,KeyCombination.CONTROL_DOWN));
        SaveReportBtn.setAccelerator(new KeyCodeCombination(KeyCode.L,KeyCombination.CONTROL_DOWN));

        Thread savelog = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String PATH = mainpath.replace("main/demo-1.0-SNAPSHOT-shaded.jar","")+"Log";
                    ArrayList<String> username = ShellExec.ExecCommand("whoami");
                    Calendar now = Calendar.getInstance();

                    FileWriter writer = new FileWriter(PATH, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write(now.get(Calendar.DAY_OF_MONTH)+"-"+now.get(Calendar.MONTH)+"-"+now.get(Calendar.YEAR)+" "+ now.get(Calendar.HOUR_OF_DAY)+":"+now.get(Calendar.MINUTE)+
                            " "+username.getFirst()+"\n");
                    bufferedWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Calendar now = Calendar.getInstance();
            }
        });
        savelog.start();


        try {

            serverSocket = new ServerSocket(8092);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread serverthread = new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        });
        serverthread.start();


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                  // ShellExec.ExecCommand("cd /home/me/IdeaProjects/ && java -jar CommandLine-1.0-SNAPSHOT-shaded.jar");
                  // /home/me/IdeaProjects/demo/target/demo-1.0-SNAPSHOT-shaded.jar

                  String pathtocmd =mainpath.replace("demo-1.0-SNAPSHOT-shaded.jar","CommandLine-1.0-SNAPSHOT-shaded.jar");
                 // System.out.println(mainpath);
                  mainpath=mainpath.replace("demo-1.0-SNAPSHOT-shaded.jar","");

                   // CommandLine-1.0-SNAPSHOT-shaded.jar
                  ShellExec.ExecCommand("java -jar"+" "+pathtocmd); //exec cmd

              } catch (IOException e) {
                   throw new RuntimeException(e);
              }
            }
        });
        thread.start();

        FilesPane.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent mouseDragEvent) {
                VBox vb = (VBox)mouseDragEvent.getGestureSource();
                vb.setStyle("-fx-border-color: transparent");
            }
        });

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

                    File fl = new File(mainpath+"folder-documents-icon(1).png");
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

                    File fl = new File(mainpath+"delete(1).png");
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
                    v.setId(resp);

                    ContextMenu cm = new ContextMenu();
                    javafx.scene.control.MenuItem open = new javafx.scene.control.MenuItem("Open");
                    javafx.scene.control.MenuItem empty = new javafx.scene.control.MenuItem("Empty");

                    cm.getItems().addAll(open, empty);
                    filename.setContextMenu(cm);
                    v.getChildren().add(filename);

                    open.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            FolderVIewController.Getfn("TrashCan");
                            FXMLLoader Loader = new FXMLLoader(HelloApplication.class.getResource("View/FolderView.fxml"));
                            Parent root1 = null;
                            try {
                                root1 = (Parent) Loader.load();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage stage = new Stage();
                            stage.setTitle(filename.getText());
                            stage.setScene(new Scene(root1));
                            stage.show();

                        }
                    });

                    empty.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                ShellExec.ExecCommand("rm -f TrashCan/*");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });

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
                    v.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
                        @Override
                        public void handle(MouseDragEvent mouseDragEvent) {
                            VBox vBox = (VBox)mouseDragEvent.getGestureSource();
                            String name = (String)vBox.getId();
                            name=name.replace("<file>","");
                            name=name.replace("<folder>","");

                            //v.setStyle("-fx-border-color: transparent");
                            FilesPane.getChildren().remove(vBox);
                            try {
                                ShellExec.ExecCommand("mv "+name+" TrashCan");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
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
                resp = ShellExec.ExecCommand("touch New");
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
                resp = ShellExec.ExecCommand("mkdir NewFolder");
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
        ArrayList<String> username = ShellExec.ExecCommand("whoami");
        ArrayList<String> list = ShellExec.ExecCommand("ls /media/"+username.getFirst()+"/");  // !!!!!!
        for(String resp : list){
            FolderVIewController.Getfn("/media/"+username.getFirst()+"/"+resp);
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/FolderView.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle(resp);
            stage.setScene(new Scene(root1));
            stage.show();
        }
    }

    public void OnConsoleClicked(ActionEvent actionEvent) throws IOException, InterruptedException {


        try {
           // if (thread.getState()!=Thread.State.TERMINATED) {
                SocketClientConsole.startConnection("127.0.0.1", 8090);
           // }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void OnAboutClicked(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("View/AboutScene.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("About");
        stage.setScene(new Scene(root1));
        stage.show();
    }



    public  Node CreateNew(String fn) {

        VBox v = new VBox();
        ImageView iv = new ImageView();
        Label filename = new Label();
        Insets insets = new Insets(10, 5, 0, 0);
        v.setPadding(insets);

        File fl = new File(mainpath+"copy_paste_document_file_1557.png");
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
                v.startFullDrag();

            }
        });

        v.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent mouseDragEvent) {
                v.setStyle("-fx-border-color: transparent");
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
                            ArrayList<String> resp =  ShellExec.ExecCommand ("rm "+filename.getText());
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
                            ArrayList<String> resp =  ShellExec.ExecCommand ("cp "+filename.getText()+" "+filename.getText()+"COPY");
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
                                ShellExec.ExecCommand ("xdg-open "+filename.getText());
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
                                    ShellExec.ExecCommand ("mv "+fn+" "+tx.getText());
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

        File fl = new File(mainpath+"folder-documents-icon(1).png");
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

        v.setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent mouseDragEvent) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        v.setStyle("-fx-background-color: rgba(13, 137, 209, 0.63)");
                    }
                });
            }
        });

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

        v.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent mouseDragEvent) {
                VBox vBox = (VBox)mouseDragEvent.getGestureSource();
                String name = (String)vBox.getId();
                if(name.contains("<file>")) {
                    name = name.replace("<file>", "");
                }
                if(name.contains("<folder>")) {
                    name = name.replace("<folder>", "");
                }
                //v.setStyle("-fx-border-color: transparent");
                FilesPane.getChildren().remove(vBox);
                try {
                    ShellExec.ExecCommand("mv "+name+" "+filename.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
                            ArrayList<String> resp =  ShellExec.ExecCommand ("rm -r "+filename.getText());
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
                            ArrayList<String> resp =  ShellExec.ExecCommand ("cp -r "+filename.getText()+" "+filename.getText()+"COPY");
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
                                    ShellExec.ExecCommand ("mv "+fn+" "+tx.getText());
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