package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Persone;
import com.example.Domain.Relationship;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UserGuiController  {
    public ImageView idImage;
    public Image image;
    public Button ButonCuIco;
    private MainController cont;



    @FXML
    private TextField FirstNameUser;
    @FXML
    private TextField LastNameUser;

    @FXML
    private TextField textUsername;

    @FXML
    private TextField SearchField;
    @FXML
    private Label LabelUsername;

    public void setUserGuiController(MainController cont,User user) {
        this.cont = cont;
        this.user=user;
        //aici trebuie modificat
        //idImage.setImage(new Image("C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\images\\pozaProiect.jpg"));
        textUsername.setText(user.getUsername());
        FirstNameUser.setText(user.getPers().getFirstName());
        LastNameUser.setText(user.getPers().getLastName());

        Image img = new Image("C:\\Users\\Alexandra\\Desktop\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\Photos\\HomeIcon.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(22);
        view.setPreserveRatio(true);
        ButonCuIco.setGraphic(view);
        initModel();
    }


    ObservableList<PrintedAllPersones> model = FXCollections.observableArrayList();
    @FXML
    private TableView<PrintedAllPersones> tableView;
    @FXML
    private TableColumn<PrintedAllPersones,String> FirstName;
    @FXML
    private TableColumn<PrintedAllPersones, String> LastName;
    @FXML
    private TableColumn<PrintedAllPersones, String> Username;


    private User user;
    public void handleTest(ActionEvent ev){
        showTestTableLayout();
    }



    public void showTestTableLayout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("requests_table_view.fxml")).toURI().toURL());
            AnchorPane loginLayout = fxmlLoader.load();
            String Username = textUsername.getText();
            Stage registerStage = new Stage();
            RequestsTableController requestController = fxmlLoader.getController();
            requestController.setRequestsController(cont,registerStage,cont.getUserByOther(Username));
            Scene scene = new Scene(loginLayout);
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.setTitle("Requests");
            registerStage.setScene(scene);
            registerStage.show();
        }catch(IOException | InterruptedException | Exception e){
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }
    }




    public void handleMessage(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("message_view.fxml")).toURI().toURL());
            AnchorPane loginLayout = fxmlLoader.load();
            String Username = textUsername.getText();
            Stage messStage = new Stage();
            MessageGUIController messageController = fxmlLoader.getController();
            messageController.setMessageGUIController(cont,messStage,cont.getUserByOther(Username));
            Scene scene = new Scene(loginLayout);
            messStage.initModality(Modality.WINDOW_MODAL);
            messStage.setTitle("Conversation");
            messStage.setScene(scene);
            messStage.show();
        }catch(IOException | InterruptedException | Exception e){
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }
    }

    @FXML
    public void initialize() {
        FirstName.setCellValueFactory(new PropertyValueFactory<PrintedAllPersones, String>("First_Name"));
        LastName.setCellValueFactory(new PropertyValueFactory<PrintedAllPersones, String>("Last_Name"));
        Username.setCellValueFactory(new PropertyValueFactory<PrintedAllPersones,String>("username"));
       tableView.setItems(model);
    }

    public void initModel() {

        List<Persone> persones = new ArrayList<>();
        for(User u: cont.getAllUsers())
            persones.add(u.getPers());

        SearchField.textProperty().addListener(o -> handleFilter());

        model.setAll(cont.getAllUsers().stream()
              .map(x -> new PrintedAllPersones(x.getPers().getFirstName(),x.getPers().getLastName(), x.getUsername()))
            .collect(Collectors.toList()));

    }



    public void handleFilter() {
        Predicate<PrintedAllPersones>p1=
                n->n.getFirst_Name().startsWith(SearchField.getText());

        model.setAll(cont.getAllUsers().stream()
                .map(x -> new PrintedAllPersones(x.getPers().getFirstName(),x.getPers().getLastName(), x.getUsername()))
                        .filter(p1)
                .collect(Collectors.toList()));

    }


    public void handleAddFriend(ActionEvent actionEvent) {

        String username=tableView.getSelectionModel().getSelectedItem().getUsername();
        try {

             Relationship rel = new Relationship(user.getUsername(), username, LocalDate.now(), "pending");
            cont.AddRequest(rel);
            MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Add", "cerere trimisa cu succes");
        }
        catch (Exception e)
        {
            MessageAlert.showErrorMessage(null, e.getDescription());
        }
    }

    public void handleSearch(ActionEvent actionEvent) {
        String username=SearchField.getText();
        User r=cont.getUserByUsername(username);
        List<PrintedAllPersones> list=new ArrayList<>();
        list.add(new PrintedAllPersones(r.getPers().getFirstName(),r.getPers().getLastName(),username));


    }


    public void handleSeeProfile(ActionEvent actionEvent) {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("profile.fxml")).toURI().toURL());
            AnchorPane loginLayout = fxmlLoader.load();
            String Username=tableView.getSelectionModel().getSelectedItem().getUsername();
            String Username2 = textUsername.getText();
            ProfileController profileController=fxmlLoader.getController();
            profileController.setProfileController(cont,cont.getUserByUsername(Username2),cont.getUserByUsername(Username));
            Stage registerStage = new Stage();
            Scene scene = new Scene(loginLayout);
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.setTitle(Username);
            registerStage.setScene(scene);
            registerStage.show();
        }
        catch (IOException | InterruptedException | Exception e){
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }


    }

    public void handleButonDeProba(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("FinalFirstPage.fxml")).toURI().toURL());
            AnchorPane loginLayout = fxmlLoader.load();

            FirstPageController profileController=fxmlLoader.getController();
            profileController.setCont(cont);
            Stage registerStage = new Stage();
            Scene scene = new Scene(loginLayout);
            registerStage.initModality(Modality.WINDOW_MODAL);

            registerStage.setScene(scene);
            registerStage.show();
        }
        catch (IOException | InterruptedException | Exception e){
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }


    }

    public void HandleTestFancyTable(ActionEvent actionEvent) {
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("ceva_de_proba.fxml")).toURI().toURL());
            AnchorPane loginLayout = fxmlLoader.load();
            String Username2 = textUsername.getText();
            TableEvents profileController=fxmlLoader.getController();
            //aici probabil o sa fie o lista de eventinmente
            profileController.set(cont,cont.getUserByUsername(Username2));
            Stage registerStage = new Stage();
            Scene scene = new Scene(loginLayout);
            registerStage.initModality(Modality.WINDOW_MODAL);

            registerStage.setScene(scene);
            registerStage.show();
        }
        catch (IOException | InterruptedException | Exception e){
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }

    }
}
