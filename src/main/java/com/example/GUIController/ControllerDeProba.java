package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;


public class ControllerDeProba {
    public TextField textField;

    private MainController cont;
    @FXML
    public TextField unu,doi;
    @FXML
    private AnchorPane[] fancyTable ;

    @FXML
    private VBox vb;

    public void setProfileController(MainController cont) {
        this.cont=cont;
        AnchorPane[] fancyTable = new AnchorPane[15];

        for(int i=1;i<=3;i++) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(new File(Algoritm.getFullPath("AnchorForTFancyTable.fxml")).toURI().toURL());
                fancyTable[i] = fxmlLoader.load();
                User user=new User("username",new Persone(2L,"ionescu","vlad"));
                //TableEvents profileController=fxmlLoader.getController();
                //profileController.set(cont,user);
                vb.getChildren().add(fancyTable[i]);
            } catch (IOException | InterruptedException | Exception e) {
                e.printStackTrace();
                MessageAlert.showErrorMessage(null, e.getMessage() + "\n" + e.getCause());
            }

        }


    }
}