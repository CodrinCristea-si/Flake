package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import javafx.event.ActionEvent;
import com.example.Domain.Event;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class CreateEventController {
    public TextField NameEvent;
    public TextField LocationEvent;
    public TextField Date;
    public TextField Description;
    private MainController cont;


    public void set(MainController cont) {
        this.cont=cont;
    }



    public void handleCreateEvent(ActionEvent actionEvent) {
        String name,location,description;
        LocalDate dtf;
        name=NameEvent.getText();
        location=LocationEvent.getText();
        dtf= LocalDate.parse(Date.getText());
        description=Description.getText();
        Event ev;
        Long id=Long.valueOf(cont.getSize());
        ev=new Event(id+1,name,description,dtf);
        cont.addEvent(ev);
    }
}
