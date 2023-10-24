package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.User;
import javafx.scene.control.TextField;

public class UserProfileController {
    public TextField Username;
    public TextField prenume;
    public TextField Nume;
    private MainController cont;
    User user;

    public void set(MainController cont, User user) {
        this.cont = cont;
        this.user = user;
        Username.setText(user.getUsername());
        prenume.setText(user.getPers().getLastName());
        Nume.setText(user.getPers().getFirstName());
    }

    
}
