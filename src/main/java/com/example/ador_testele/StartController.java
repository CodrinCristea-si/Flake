package com.example.ador_testele;

//import com.example.Controller.OldController.StringController;
//import //com.example.Domain.String;
import com.example.Controller.NewController.PersoneController;
import com.example.Domain.Persone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class StartController {

    PersoneController personeController;
    ObservableList<Persone> model = FXCollections.observableArrayList();

    @FXML
    private Label Text;
    @FXML
    private Button btn=new Button("merge");
    @FXML
    TableView<Persone> tableView;
    @FXML
    TableColumn<Persone,String> tableColumnFirstName;
    @FXML
    TableColumn<Persone,String> tableColumnLastName;




    @FXML
    protected void onFriendsButtonClick() {
        Text.setText("Welcome to JavaFX Application!");
    }


    public void onHIHi(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(
                new File("C:\\Users\\Alexandra\\Desktop\\anul 2\\LastTry\\src\\main\\resources\\com\\example\\ador_testele\\tableTry.fxml")
                        .toURI().toURL()
        );
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Stage f=new Stage();


        f.setTitle("Hello");
        f.setScene(scene);

         initialize();
        f.show();

    }

    @FXML
    public void initialize() {

        tableColumnFirstName.setCellValueFactory(new PropertyValueFactory<Persone, String>("FirstName"));
        tableColumnLastName.setCellValueFactory(new PropertyValueFactory<Persone, String>("LastName"));
        tableView.setItems(model);
    }

    private void initModel() {
        Iterable<Persone> persones = personeController.getAll();
        List<Persone> personeList = StreamSupport.stream(persones.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(personeList);

    }

}
