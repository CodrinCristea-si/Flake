package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Utils.Observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RequestsTableController implements Observer {
    User user;
    private MainController cont;
    Stage requestStage;
    public void setRequestsController(MainController cont, Stage stage,User user) {
        this.cont = cont;
        this.requestStage=stage;
        this.user=user;
        initModel();
        cont.addObserver(this);
    }
    ObservableList<PrintedRelationship> model = FXCollections.observableArrayList();

    @FXML
    private TableView<PrintedRelationship> tableView;
    @FXML
    private TableColumn<PrintedRelationship,String> tableColumnFrom;
    @FXML
    private TableColumn<PrintedRelationship, LocalDate> tableColumnDate;
    @FXML
    private TextField username;

    @FXML
    public void initialize() {
        tableColumnFrom.setCellValueFactory(new PropertyValueFactory<PrintedRelationship, String>("From"));
        tableColumnDate.setCellValueFactory(new PropertyValueFactory<PrintedRelationship, LocalDate>("Date"));
        tableView.setItems(model);

        username.textProperty().addListener(o -> handleFindAction());
    }

    public void initModel(){
       // System.out.println(cont.RequestsForAUser(user.getUsername()));
        List<Relationship> requests=cont.RequestsForAUser(user.getUsername());
        model.setAll(requests.stream()
                .map(x-> new PrintedRelationship(x.getFirstUserName(),x.getDtf()))
                .collect(Collectors.toList()));

    }

    public void handleAcceptAction(ActionEvent ev){
        PrintedRelationship rel= tableView.getSelectionModel().getSelectedItem();
        if(rel!= null){
            cont.UpdateStatusRequest("accept",rel.getFrom(),user.getUsername());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Accept","The User has been added to the friends list!");
        }
        else{
            MessageAlert.showErrorMessage(null,"You haven't selected a request!");
        }
    }

    public void handleRejectAction(ActionEvent ev){
        PrintedRelationship rel= tableView.getSelectionModel().getSelectedItem();
        if(rel!= null){
            cont.UpdateStatusRequest("reject",rel.getFrom(),user.getUsername());
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Reject","The request has been rejected!");
        }
        else{
            MessageAlert.showErrorMessage(null,"You haven't selected a request!");
        }
    }

    public void handleFindAction(){
            Predicate<PrintedRelationship> p1= u-> u.getFrom().startsWith(username.getText());
            model.setAll(cont.RequestsForAUser(user.getUsername()).stream()
                    .map(x-> new PrintedRelationship(x.getFirstUserName(),x.getDtf()))
                    .filter(p1)
                    .collect(Collectors.toList()));
    }

    public void handleCancelAction(ActionEvent ev){
        cont.removeObserver(this);
        requestStage.close();
    }

    @Override
    public void update() {
        initModel();
    }
}
