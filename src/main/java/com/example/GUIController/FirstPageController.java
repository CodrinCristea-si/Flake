package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FirstPageController {

    private MainController cont;
    @FXML
    private Button HomeButton,MessagesButton,EventsButton,StatisticsButton,PeopleButton,ExitButton;


    public void setCont(MainController cont) {
        this.cont = cont;
        /*SetButtonsImage(HomeButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\HomeIcon.png");
        SetButtonsImage(MessagesButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\MessagesIcon2.png");
        SetButtonsImage(PeopleButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\People2.png");
        SetButtonsImage(EventsButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\EventIcon.png");
        SetButtonsImage(StatisticsButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\StatisticsIcon.png");
        SetButtonsImage(ExitButton,"C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\ExitIcon.png");
   */
    }

    private void SetButtonsImage(Button NameButton,String url){
        Image img = new Image(url);
        javafx.scene.image.ImageView view = new ImageView(img);
        view.setFitHeight(23);
        view.setPreserveRatio(true);
        NameButton.setGraphic(view);
    }


    public void handleHome(ActionEvent actionEvent) {
    }

    public void handleMessage(ActionEvent actionEvent) {
    }

    public void handleFriends(ActionEvent actionEvent) {
    }

    public void handleEvents(ActionEvent actionEvent) {
    }

    public void handleStatistics(ActionEvent actionEvent) {
    }

    public void handleLogout(ActionEvent actionEvent) {
    }
}
