package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.User;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    private MainController cont;
    private Stage stage;
    AnchorPane loginLayout;


    public void setLoginController(MainController cont,Stage stage,AnchorPane loginLayout) {
        this.cont = cont;
        this.stage=stage;
        this.loginAnchor=loginLayout;
    }



    @FXML
    private TextField textUsername;
    @FXML
    private PasswordField textPassword;
    @FXML
    private AnchorPane loginAnchor;

    @FXML
    private SplitPane splitLogin;
    private AnchorPane copy_loginAnchor;

    public void handleLoginAction(ActionEvent ev){
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Feature not yet implemented","The menu for admin is not yet available!");
        try {
            String Username = textUsername.getText();
            String Password = textPassword.getText();
            System.out.println(Username);
            System.out.println(Password);
            if (Username.equals("admin"))
                MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Feature not yet implemented", "The menu for admin is not yet available!");
            if(Username.equals("") || Username==null ||Username.isEmpty())
                MessageAlert.showErrorMessage(null, "The text field must contain an username!");
            else {
                //User user = cont.getUserByUsername(Username);
                User user=cont.tryLogin(Username,Password);
                showUserLayout();
               //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION, "Feature not yet implemented", "The menu for user is not yet available!");
            }
        }
        catch(Exception e){
            MessageAlert.showErrorMessage(null, e.getDescription());
        }
        catch(NullPointerException e){
            MessageAlert.showErrorMessage(null, "The text field must contain an username!");
        } catch (NoSuchAlgorithmException e) {
            MessageAlert.showErrorMessage(null, e.getMessage());
        }
        textUsername.setText(null);
        textPassword.setText(null);
    }

    private void showUserLayout()  {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("FinalFirstPage.fxml")).toURI().toURL());
            AnchorPane loginLayout1 = fxmlLoader.load();
            String Username =textUsername.getText();
            MainWindowController mainCont=fxmlLoader.getController();
                //FirstPageController mainCont=fxmlLoader.getController();
            //UserGuiController requestController = fxmlLoader.getController();
            //requestController.setUserGuiController(cont,cont.getUserByUsername(Username));
            //ControllerDeProba profileController=fxmlLoader.getController();
            //profileController.setProfileController(cont);
            Stage registerStage = new Stage();
            System.out.println(cont.toString());
            mainCont.setMainWindowController(cont,registerStage,loginLayout,cont.getUserByUsername(Username));
            //mainCont.setCont(cont);
            Scene scene = new Scene(loginLayout1);
            registerStage.initModality(Modality.WINDOW_MODAL);
            registerStage.setTitle(Username);
            registerStage.setScene(scene);
            stage.close();
            registerStage.show();

        }
        catch (IOException | InterruptedException | Exception e){
            e.printStackTrace();
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }

    }

    public void handleRegisterAction(ActionEvent ev){
        //MessageAlert.showMessage(null, Alert.AlertType.INFORMATION,"Feature not yet implemented","The register is not yet available!");
        textUsername.setText(null);
        textPassword.setText(null);
        showRegistrationLayout();
    }


    public void showRegistrationLayout(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(new File(Algoritm.getFullPath("register_view.fxml")).toURI().toURL());
            AnchorPane regLayout = fxmlLoader.load();
            RegisterController registerController = fxmlLoader.getController();
            registerController.setRegisterController(cont,stage,splitLogin, (AnchorPane) splitLogin.getItems().get(1));
            splitLogin.getItems().set(1,regLayout);

            //copy_loginAnchor=loginAnchor;
            //loginAnchor=regLayout;
            //Scene scene = new Scene(loginLayout);
            //stage.setTitle("Registration");
            //stage.setScene(scene);
            stage.show();
            //splitLogin.getItems().set(1,loginLayout);
        }catch(IOException | InterruptedException e){
            MessageAlert.showErrorMessage(null, e.getMessage()+"\n"+e.getCause());
        }
    }

}
