package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Message;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import com.example.Utils.Observer.Observer;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MessageGUIController implements Observer {
    private MainController cont;
    private Stage stage;
    private User user;

    private User otherPerson;

    public void setMessageGUIController (MainController cont, Stage stage, User user){
        this.cont=cont;
        this.stage=stage;
        this.user=user;
        initModel();
        cont.addObserver(this);
    }

    private Message reply;

    @FXML
    private TextArea textMess;

    @FXML
    private VBox mainSceneTExt;

    @FXML
    private TextField textFilterUser;

    @FXML
    private Label labelReply;

    @FXML
    private TableView<String> tableUsername;
    @FXML
    private TableColumn<String,String> columnUsername;

    private ObservableList<String> model = FXCollections.observableArrayList();

    private void initModel(){
        model.setAll(cont.getUserFriendsUsername(user.getUsername()));
    }

    public void loadTheConversation(){
        mainSceneTExt.getChildren().clear();
        if(otherPerson==null)return ;
        List<Message> conv=cont.loadConversation(user.getUsername(),otherPerson.getUsername());
        for (var mess:conv){
            TextArea convo_mess=new TextArea();
            String date=mess.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")).toString()+": ";
            String Username1=mess.getFrom().getUsername();
            Message messReply=null;
            if(mess.getReply()!=null)
                messReply= cont.getMessageById(mess.getReply().getId());
            if(messReply!=null)
            {
                String rep="reply to: "+messReply.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm")).toString()+": "+messReply.getFrom().getUsername()+": "+messReply.getMessage();
                if(rep.length()>=50){
                    rep=rep.substring(0,50);
                    rep+="...";
                }
                convo_mess.setText(rep+"\n"+date+Username1+": "+mess.getMessage());
            }
            else convo_mess.setText(date+Username1+": "+mess.getMessage());
            if (mess.getFrom().getUsername().equals(user.getUsername()))
                convo_mess.setStyle("-fx-background-color: red;-fx-text-fill: red");
            else if(mess.getFrom().getUsername().equals(otherPerson.getUsername()))
                convo_mess.setStyle("-fx-background-color: green;-fx-border-colorder-color: green;-text-area-backorund: green; -fx-text-fill: green");
            convo_mess.setWrapText(true);
            convo_mess.setEditable(false);
            convo_mess.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if(event.getButton().equals(MouseButton.SECONDARY)){
                        labelReply.setVisible(true);
                        reply=mess;
                    }
                }
            });
            mainSceneTExt.getChildren().add(convo_mess);
        }
    }

    @FXML
    private void initialize(){
        columnUsername.setCellValueFactory((p)->{ return new ReadOnlyStringWrapper(p.getValue());});
        tableUsername.setItems(model);

        textFilterUser.textProperty().addListener(o->handleFilterAction());
    }


    public void handleFilterAction(){
        Predicate<String> pred= u-> u.startsWith(textFilterUser.getText());
        model.setAll(cont.getUserFriendsUsername(user.getUsername())
                .stream()
                .filter(pred)
                .collect(Collectors.toList()));
    }

    public void handleSend(ActionEvent ev){
        String message=textMess.getText();
        if(otherPerson==null)
            MessageAlert.showErrorMessage(null,"There is no conversation to reply");
        else if (message.equals("") || message==null || message.isBlank())
            MessageAlert.showErrorMessage(null,"The text field is null");
        else{
            Message mess;
            if(labelReply.isVisible()){
                Message messReply=cont.getMessageById(reply.getId());
                mess= new Message(user,message, Arrays.asList(otherPerson), LocalDateTime.now(),messReply);
            }
            else mess= new Message(user,message, Arrays.asList(otherPerson), LocalDateTime.now());
            cont.sendMessage(mess);
            reply=null;
            labelReply.setVisible(false);
            textMess.clear();
            loadTheConversation();
        }
    }

    public void handleChoose(MouseEvent ev){
        String Usern=tableUsername.getSelectionModel().getSelectedItem();
        if(Usern==null)
            MessageAlert.showErrorMessage(null,"You haven't selected an user to chat with!");
        else
        {
            otherPerson=cont.getUserByUsername(Usern);
            loadTheConversation();
        }
    }

    private boolean sendAll=false;
    public void handleSendAll(ActionEvent ev){
        try {
            if(!sendAll) {
                sendAll=true;
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(new File(Algoritm.getFullPath("send-all-view.fxml")).toURI().toURL());
                AnchorPane loginLayout = fxmlLoader.load();
                Stage messStage = new Stage();
                SendAllGUIController messageController = fxmlLoader.getController();
                messageController.setSendAllGUIController(cont, messStage, user,sendAll);
                Scene scene = new Scene(loginLayout);
                messStage.initModality(Modality.WINDOW_MODAL);
                messStage.setTitle("Send All");
                messStage.setScene(scene);
                messStage.show();

            }
        }catch(IOException | InterruptedException | Exception e){
            sendAll=false;
            //e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }
    }

    public void handleExit(ActionEvent ev){
        stage.close();
    }


    @Override
    public void update() {
        initModel();
        loadTheConversation();
    }
}
