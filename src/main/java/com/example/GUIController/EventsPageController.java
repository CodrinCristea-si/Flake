package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Event;
import com.example.Domain.User;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.security.cert.PolicyNode;

public class EventsPageController {
    public PolicyNode vertical_box;
    public TextField NameEvent;
    public ImageView ImageEvent;
    public TextField Date_event;
    public TextArea DecriptionEvent;
    public Button Participate;
    public Button Notify;
    private MainController cont;
    private Event event;
    private User user;
    
    public void setEventPageController(MainController cont, Event event, User user){
        this.cont=cont;
        this.event=event;
        this.user=user;
        setDateForEvent(event,user);
        
    }

    private void setDateForEvent(Event event,User user) {
        NameEvent.setText(event.getName());
        Date_event.setText(String.valueOf(event.getDtf()));
        DecriptionEvent.setText(event.getDescription());

      if(cont.FindIfUserParticipateToEvent(user.getId(), event.getId()))
          Participate.setText("Participate");
      else Participate.setText("Add event");

    }
}
