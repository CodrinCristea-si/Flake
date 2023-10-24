package com.example.ador_testele;

import com.example.Controller.NewController.*;
import com.example.Repository.Db.*;
import com.example.Repository.RelationshipRepo;
import com.example.Repository.UserRepo;
import com.example.Ui.NewUi.Login;



import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

	// write your code here


        UserRepo repoU= new UserDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);
        PersoneDbRepo repoP= new PersoneDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);
        RelationshipRepo repoR= new RelationshipDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);
        RequestsDbRepo repoRQ=new RequestsDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);
        MessageDbRepo repoM=new MessageDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);
        EventDbRepo repoE=new EventDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);
        UserEventDbRepo repoUE=new UserEventDbRepo(ConnectionsMain.URL,ConnectionsMain.Username,ConnectionsMain.Password);

        UserController contU=new UserController(repoU);
        RelationshipController contR=new RelationshipController((RelationshipDbRepo) repoR);
        PersoneController contP=new PersoneController(repoP);
        RequestsController contRQ=new RequestsController(repoRQ);
        MessageController contM=new MessageController(repoM);
        EventController contE=new EventController(repoE);
        UserEventController contUE=new UserEventController(repoUE);
        MainController cont= new MainController(contU,contR,contP,contM,contRQ,contE,contUE);

        Login ui= new Login(cont);
        ui.mainMenu();


    }
}
