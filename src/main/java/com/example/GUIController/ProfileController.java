package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Utils.Observer.Observer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ProfileController implements Observer {
    private MainController cont;
    private User user;
    private User other;

    @FXML
    private TextField textF;
    @FXML
    private TextField textL;
    @FXML
    private TextField textU;
    @FXML
    private TextField textNoF;
    @FXML
    private TextField textNoE;
    @FXML
    private TextField textData;

    @FXML
    private Label labelFr;

    @FXML
    private Button butR;
    @FXML
    private Button butL;

    public void setProfileController(MainController cont, User user,User user2){
        this.cont=cont;
        this.user=user;
        this.other=user2;
        cont.addObserver(this);
        setDetails();
    }

    public void setDetails(){
        textF.setText(other.getPers().getFirstName());
        textL.setText(other.getPers().getLastName());
        textU.setText(other.getUsername());
        textNoF.setText(String.valueOf(cont.getFriendsByUsername(other.getUsername()).size()));
        textNoE.setText(String.valueOf(cont.getUserEventsById(other.getId()).size()));
        setButtonText();
    }

    public void setButtonText(){
        Relationship rel=null,rel1=null,rel2=null;
        try {
            rel = cont.getRelationshipByOther(user.getUsername(), other.getUsername());
        }catch(Exception e){
            int i=0;
        }finally {
            if (rel != null) {
                butR.setVisible(true);
                butR.setText("Unfriend");
                butL.setVisible(false);
                labelFr.setVisible(true);
                textData.setVisible(true);
                textData.setEditable(false);
                textData.setText(rel.getDtf().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString());
                return;
            }
            //rel1 = cont.getRequestByUsername(user.getUsername(), other.getUsername());
            //if(rel1!=null)System.out.println("rel1 "+rel1.toString());
//            if (rel1 != null && rel1.getStatus().equals("pending")) {
//                butR.setVisible(true);
//                butR.setText("Accept");
//                butL.setVisible(true);
//                butL.setText("Reject");
//                labelFr.setVisible(false);
//                textData.setVisible(false);
//                textData.setEditable(false);
//                return;
//            }
            rel1 = cont.getRequestByUsername(other.getUsername(), user.getUsername());
            rel2 = cont.getRequestByUsername(user.getUsername(), other.getUsername());
            if(rel2!=null)System.out.println("rel2 "+rel2.toString());
            if (rel2 != null && rel2.getStatus().equals("pending")) {
                butR.setVisible(true);
                butR.setText("Cancel Request");
                butL.setVisible(false);
                labelFr.setVisible(false);
                textData.setVisible(false);
                textData.setEditable(false);
                return;
            }
            if(rel2==null && rel1==null)
            {
                butR.setVisible(true);
                butR.setText("Add friend");
                butL.setVisible(false);
                labelFr.setVisible(false);
                textData.setVisible(false);
                textData.setEditable(false);
                return;
            }
        }
    }

    public void handleButR(ActionEvent ev){
        if(butR.getText().equals("Add friend")){
            Relationship rel= new Relationship(user.getUsername(),other.getUsername(), LocalDate.now(),"pending");
            cont.AddRequest(rel);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Succes","The request has been sent!");
        }
        else if(butR.getText().equals("Cancel Request")){
            cont.UpdateStatusRequest("cancel",user.getUsername(),other.getUsername());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Succes","The request has been cancel!");
        }
//        else if(butR.getText().equals("Accept")){
//            cont.UpdateStatusRequest("accept",user.getUsername(),other.getUsername());
//            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Succes","The friend has been added!");
//        }
        else if(butR.getText().equals("Unfriend")){
            cont.removeRelationshipByUsernames(other.getUsername(),user.getUsername());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Succes","The friend has been deleted!");
        }
    }

    public void handleButL(ActionEvent ev){
        if(butR.getText().equals("Reject")){
            cont.UpdateStatusRequest("reject",other.getUsername(),user.getUsername());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Succes","The request has been rejected!");
        }
    }

    @Override
    public void update() {
        setDetails();
    }
}