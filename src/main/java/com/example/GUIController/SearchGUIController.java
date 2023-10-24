package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SearchGUIController {
    MainController cont;
    User user;
    SplitPane split;
    private TextField searchText;
    public void setSearchGUIController(MainController cont,User user,SplitPane split,TextField text) {
        this.cont = cont;
        this.user=user;
        this.split=split;
        this.searchText=text;
        initialize1();
        initmodel();
    }

    ObservableList<PrintedUsers> model = FXCollections.observableArrayList();

    @FXML
    private TableView<PrintedUsers> tableU;
    @FXML
    private TableColumn<PrintedUsers,String> tableColumnUsername;
    @FXML
    private TableColumn<PrintedUsers, String> tableColumnFName;
    @FXML
    private TableColumn<PrintedUsers, String> tableColumnLName;

    public void initialize1() {
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<PrintedUsers, String>("Username"));
        tableColumnFName.setCellValueFactory(new PropertyValueFactory<PrintedUsers, String>("FirstName"));
        tableColumnLName.setCellValueFactory(new PropertyValueFactory<PrintedUsers, String>("LastName"));
        tableU.setItems(model);
        searchText.textProperty().addListener(o -> handleFindAction());
    }

    public void initmodel(){
        List<User> listU=cont.getAllUsers();
        model.setAll(listU.stream()
                .map(x->new PrintedUsers(x.getUsername(),x.getPers().getFirstName(),x.getPers().getLastName()))
                .filter(x->{return !x.getUsername().equals(user.getUsername());})
                .collect(Collectors.toList()));
    }

    public void handleFindAction(){
        Predicate<PrintedUsers> p1= u-> u.getUsername().startsWith(searchText.getText());
        Predicate<PrintedUsers> p2= u-> !u.getUsername().equals(user.getUsername());
        model.setAll(cont.getAllUsers().stream()
                .map(x->new PrintedUsers(x.getUsername(),x.getPers().getFirstName(),x.getPers().getLastName()))
                .filter(p1.and(p2))
                .collect(Collectors.toList()));
    }

    public void handleProfile(MouseEvent ev) throws IOException, InterruptedException {
        PrintedUsers Usern=tableU.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new File(Algoritm.getFullPath("profile_other.fxml")).toURI().toURL());
        AnchorPane profLayout = fxmlLoader.load();
        split.getItems().set(1, profLayout);
        ProfileController profileController = fxmlLoader.getController();
        profileController.setProfileController(cont,user,cont.getUserByUsername(Usern.getUsername()));
    }
}
