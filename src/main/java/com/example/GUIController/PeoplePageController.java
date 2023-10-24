package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.User;


public class PeoplePageController {
    private MainController cont;
    private User user;
    
    
    public void setPeoplePageController(MainController cont,User user){
        this.cont=cont;
        this.user=user;
        SetFriendsTable(user);
        SetRequestsTable(user);
    }

    private void SetRequestsTable(User user) {
    }

    private void SetFriendsTable(User user) {
    }
}
