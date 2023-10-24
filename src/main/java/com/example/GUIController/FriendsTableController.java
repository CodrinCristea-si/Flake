package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.User;
import com.example.Repository.PagingRepo.PageType;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import com.example.Utils.Observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FriendsTableController implements Observer {
    User user;
    private MainController cont;
    Stage registerStage;
    SplitPane split;

    public void setFriendsController(MainController cont, Stage stage, User user,SplitPane split) {
        this.cont = cont;
        this.registerStage = stage;
        this.user = user;
        this.split=split;
        cont.addObserver(this);
        updateListFriend(PageType.CURRENT,true);
        updateListRequest(PageType.CURRENT,true);
        //initModel();
    }

    ObservableList<PrintedPersones> model = FXCollections.observableArrayList();

    @FXML
    private Label flname1;
    @FXML
    private Label flname2;
    @FXML
    private Label flname3;

    @FXML
    private Label flnamer1;
    @FXML
    private Label flnamer2;
    @FXML
    private Label flnamer3;


    @FXML
    private Label uname1;
    @FXML
    private Label uname2;
    @FXML
    private Label uname3;


    @FXML
    private Label unamer1;
    @FXML
    private Label unamer2;
    @FXML
    private Label unamer3;


    @FXML
    private HBox hboxf1;
    @FXML
    private HBox hboxf2;
    @FXML
    private HBox hboxf3;


    @FXML
    private HBox hboxr1;
    @FXML
    private HBox hboxr2;
    @FXML
    private HBox hboxr3;


    private List<User> listU;
    private List<User> listR;

    public void printFriends(){
        if(!listU.isEmpty())
        for(int i=1;i<=3;i++){
            if(i==1 && i<=listU.size() ){
                User use=listU.get(i-1);
                flname1.setText(use.getPers().getFirstName()+" "+use.getPers().getLastName());
                uname1.setText(use.getUsername());
            }
            if(i==2 && i<=listU.size() ){
                User use=listU.get(i-1);
                flname2.setText(use.getPers().getFirstName()+" "+use.getPers().getLastName());
                uname2.setText(use.getUsername());
            }
            if(i==3 && i<=listU.size() ){
                User use=listU.get(i-1);
                flname3.setText(use.getPers().getFirstName()+" "+use.getPers().getLastName());
                uname3.setText(use.getUsername());
            }
        }
        for(int i=listU.size()+1;i<=3;i++){
            if(i==1)hboxf1.setVisible(false);
            if(i==2)hboxf2.setVisible(false);
            if(i==3)hboxf3.setVisible(false);

        }
    }

    public void printRequests(){
        if(!listR.isEmpty())
        for(int i=1;i<=3;i++){
            if(i==1 && i<=listR.size() ){
                 User use=listR.get(i-1);
                flnamer1.setText(use.getPers().getFirstName()+" "+use.getPers().getLastName());
                unamer1.setText(use.getUsername());
            }
            if(i==2 && i<=listR.size() ){
                User use=listR.get(i-1);
                flnamer2.setText(use.getPers().getFirstName()+" "+use.getPers().getLastName());
                unamer2.setText(use.getUsername());
            }
            if(i==3 && i<=listR.size() ){
                User use=listR.get(i-1);
                flnamer3.setText(use.getPers().getFirstName()+" "+use.getPers().getLastName());
                unamer3.setText(use.getUsername());
            }

        }
        for(int i=listR.size()+1;i<=3;i++){
            if(i==1)hboxr1.setVisible(false);
            if(i==2)hboxr2.setVisible(false);
            if(i==3)hboxr3.setVisible(false);

        }
    }

    public void handleRemoveFriend1(ActionEvent actionEvent) {
        String username=uname1.getText();
        remove(username);
    }
    public void handleRemoveFriend2(ActionEvent actionEvent) {
        String username=uname2.getText();
        remove(username);
    }
    public void handleRemoveFriend3(ActionEvent actionEvent) {
        String username=uname3.getText();
        remove(username);
    }

    public void handleSeeProfileFriend1(ActionEvent actionEvent) throws IOException, InterruptedException {
        String username=uname1.getText();
        seeProfile(username);
    }
    public void handleSeeProfileFriend2(ActionEvent actionEvent) throws IOException, InterruptedException {
        String username=uname2.getText();
        seeProfile(username);
    }
    public void handleSeeProfileFriend3(ActionEvent actionEvent) throws IOException, InterruptedException {
        String username=uname3.getText();
        seeProfile(username);
    }

    public void handleAcceptFriend1(ActionEvent actionEvent) {
        String username=unamer1.getText();
        accept(username);
    }
    public void handleAcceptFriend2(ActionEvent actionEvent) {
        String username=unamer2.getText();
        accept(username);
    }
    public void handleAcceptFriend3(ActionEvent actionEvent) {
        String username=unamer3.getText();
        accept(username);
    }

    public void handleRejectFriend1(ActionEvent actionEvent) {
        String username=unamer1.getText();
        reject(username);
    }
    public void handleRejectFriend2(ActionEvent actionEvent) {
        String username=unamer2.getText();
        reject(username);
    }
    public void handleRejectFriend3(ActionEvent actionEvent) {
        String username=unamer3.getText();
        reject(username);
    }


    public void remove(String username){
        try {
            //Relationship rel = new Relationship(user.getUsername(), username, LocalDate.now(), "pending");
            cont.removeRelationshipByUsernames(username,user.getUsername());
        } catch (Exception e) {
            MessageAlert.showErrorMessage(registerStage,e.getDescription());
        }
    }

    public void accept(String username){
        try {
            cont.UpdateStatusRequest("accept",username,user.getUsername());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Succes","The friend has been added!");
        } catch (Exception e) {
            MessageAlert.showErrorMessage(registerStage,e.getDescription());
        }
    }

    public void reject(String username){
        try {
            cont.UpdateStatusRequest("reject",username,user.getUsername());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Succes","The raquest has been rejected!");
        } catch (Exception e) {
            MessageAlert.showErrorMessage(registerStage,e.getDescription());
        }
    }

    public void seeProfile(String username) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File(Algoritm.getFullPath("profile_other.fxml")).toURI().toURL());
        AnchorPane profLayout = fxmlLoader.load();
        split.getItems().set(1, profLayout);
        ProfileController profileController = fxmlLoader.getController();
        profileController.setProfileController(cont,user,cont.getUserByUsername(username));
    }

    public void handleFriendNextPage(ActionEvent ev){
        if(listU.size()==3){
            updateListFriend(PageType.NEXT,false);
        }
    }

    public void handleFriendPreviousPage(ActionEvent ev){
            updateListFriend(PageType.PREVIOUS,false);
    }

    public void handleRequestNextPage(ActionEvent ev){
        if(listR.size()==3){
            updateListRequest(PageType.NEXT,false);
        }
    }

    public void handleRequestPreviousPage(ActionEvent ev){
        updateListRequest(PageType.PREVIOUS,false);
    }

    public void updateListFriend( PageType type,boolean f){
        listU=cont.getPageFriends(user.getUsername(), type,f);
        hboxf1.setVisible(true);
        hboxf2.setVisible(true);
        hboxf3.setVisible(true);
        printFriends();

    }

    public void updateListRequest( PageType type,boolean f){
        listR=cont.getPageRequests(user.getUsername(), type,f);
        hboxr1.setVisible(true);
        hboxr2.setVisible(true);
        hboxr3.setVisible(true);
        printRequests();
    }

    @Override
    public void update() {
        updateListFriend(PageType.CURRENT,false);
        updateListRequest(PageType.CURRENT,false);
    }

    public void handleSearch(ActionEvent actionEvent) {
    }

    public void handleDeleteFriend(ActionEvent actionEvent) {
    }

    public void SearchRequests(ActionEvent actionEvent) {

    }


}