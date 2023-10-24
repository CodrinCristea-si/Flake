package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Message;
import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Utils.Exceptions.Exception;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class SendAllGUIController {
    MainController cont;
    Stage stage;
    User user;
    boolean sendAll;

    public void setSendAllGUIController(MainController cont, Stage stage, User user,boolean send) {
        this.cont = cont;
        this.stage = stage;
        this.user = user;
        this.sendAll=send;
    }
    @FXML
    private TextArea textContent;

    @FXML
    private TextArea textTo;

    public void handleSend(){
        try{
            if(textContent==null || textTo==null||textContent.getText().isEmpty()||textTo.getText().isEmpty())
                throw new Exception("The content and the To box should not be empty!");
            else {
                List<User> users= Arrays.stream(textTo.getText().split(",")).map(
                        x->new User(x,new Persone("",""))
                ).toList();
                String mess=textContent.getText();
                Message message= new Message(user,mess,users,LocalDateTime.now());
                cont.sendMessageToAll(message);
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Succes","Message sent with succes!");
            }
        }
        catch(Exception e){
            MessageAlert.showErrorMessage(null,e.getDescription());
        }
        finally{
            handleExit(null);
        }
    }

    public void handleExit(ActionEvent ev){
        sendAll=false;
        stage.close();
    }

}
