package com.example.ador_testele;


import com.example.Controller.NewController.*;
import com.example.GUIController.LoginController;
import com.example.Repository.Db.*;
import com.example.Repository.RelationshipRepo;
import com.example.Repository.UserRepo;
import com.example.Utils.Algoritms.Algoritm;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override

    public void start(Stage stage) throws IOException, InterruptedException {
        UserRepo repoU= new UserDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password,3);
        RequestsDbRepo repoRQ=new RequestsDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password,3);
        MessageDbRepo repoM=new MessageDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);
        EventDbRepo repoE=new EventDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password,3);
        UserEventDbRepo repoUE=new UserEventDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);
        EventController contE=new EventController(repoE);
        UserEventController contUE=new UserEventController(repoUE);

        MainController cont= new MainController(contU,contR,contP,contM,contRQ,contE,contUE);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File(Algoritm.getFullPath("login_view.fxml")).toURI().toURL());
        AnchorPane loginLayout=fxmlLoader.load();
        LoginController loginController= fxmlLoader.getController();

        Scene scene = new Scene(loginLayout);
        loginController.setLoginController(cont,stage,loginLayout);
        stage.setTitle("Login");

        stage.setScene(scene);
        stage.show();
        cont.closeConnections();
    }

    public static void main(String[] args) {
        launch();
    }
}