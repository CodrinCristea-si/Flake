package com.example.GUIController;

import com.example.Controller.NewController.MainController;
import com.example.Domain.Event;
import com.example.Domain.User;


import com.example.Domain.UserEvent;
import com.example.Repository.PagingRepo.PageType;
import com.example.Utils.Algoritms.Algoritm;
import com.example.Utils.Exceptions.Exception;
import com.example.Utils.Observer.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.util.List;



public class TableEvents  implements Observer {

    public VBox vertical_box;
    private MainController cont;
    private User user;

    private boolean FirstButton=false;

    Stage registerStage;


    @FXML
    private Button Participate1,Participate2,Participate3;
    @FXML
    private TextField Date_event1,Date_event2,Date_event3;
    @FXML
    private Button Notify1,Notify2,Notify3;
    @FXML
    private ImageView ImageEvent1,ImageEvent2,ImageEvent3;
    @FXML
    public TextField NameEvent1,NameEvent2,NameEvent3;
    @FXML
    public TextArea DecriptionEvent1,DecriptionEvent2,DecriptionEvent3;

    @FXML
    public HBox hboxf2,hboxf1,hboxf3;

    private List<Event> events;
    private List<Event> events1;




    public void  set(MainController cont,User user) {
        this.cont = cont;
        this.user=user;

        cont.addObserver(this);
        updateListEvents2(PageType.CURRENT,true);
        SetEventsTable1(events1);


    }
    ObservableList<PrintedPersones> model = FXCollections.observableArrayList();

    public void handleEventPreviousPage(ActionEvent ev){
        if(FirstButton==true)
            updateListEvents(PageType.PREVIOUS,false);
        else
            updateListEvents2(PageType.PREVIOUS,false);
    }

    public void handleEventNextPage(ActionEvent ev){
        if(FirstButton==true) {
            if (events1.size() == 3) {
                updateListEvents(PageType.NEXT, false);
            }
        }else {
            if (events.size() == 3) {
                updateListEvents2(PageType.NEXT, false);
            }
        }
    }

    public void updateListEvents( PageType type,boolean f){
        events1=cont.getPageEventS(type,f,user);
        hboxf1.setVisible(true);
        hboxf2.setVisible(true);
        hboxf3.setVisible(true);
        SetEventsTable1(events1);
    }

    private void SetEventsTable1(List<Event> events) {
        Long id_ev;

        if(events!=null && !events.isEmpty()) {
            for (int i = 1; i <= 3; i++) {
                if (i == 1 && i <= events.size()) {
                    Event ev = events.get(i - 1);
                    NameEvent1.setText(ev.getName());
                    Date_event1.setText(ev.getDtf().toString());
                    DecriptionEvent1.setText(ev.getDescription());
                    id_ev = cont.getEventByName(NameEvent1.getText()).getId();
                    if (cont.FindIfUserParticipateToEvent(user.getId(), id_ev))
                        Participate1.setText("Cancel");
                    else Participate1.setText("Add event");
                    Notify1.setText("Notify me!");

                }
                if (i == 2 && i <= events.size()) {
                    Event ev = events.get(i - 1);
                    NameEvent2.setText(ev.getName());
                    Date_event2.setText(ev.getDtf().toString());
                    DecriptionEvent2.setText(ev.getDescription());
                    id_ev = cont.getEventByName(NameEvent2.getText()).getId();
                    if (cont.FindIfUserParticipateToEvent(user.getId(), id_ev))
                        Participate2.setText("Cancel");
                    else Participate2.setText("Add event");
                    Notify2.setText("Notify me!");

                }
                if (i == 3 && i <= events.size()) {
                    Event ev = events.get(i - 1);
                    NameEvent3.setText(ev.getName());
                    Date_event3.setText(ev.getDtf().toString());
                    DecriptionEvent3.setText(ev.getDescription());
                    id_ev = cont.getEventByName(NameEvent3.getText()).getId();
                    if (cont.FindIfUserParticipateToEvent(user.getId(), id_ev))
                        Participate3.setText("Cancel");
                    else Participate3.setText("Add event");
                    Notify3.setText("Notify me!");

                }

            }
            for (int i = events.size() + 1; i <= 3; i++) {

                if (i == 1) hboxf1.setVisible(false);
                if (i == 2) hboxf2.setVisible(false);
                if (i == 3) hboxf3.setVisible(false);
            }
        }
    }

    public void updateListEvents2( PageType type,boolean f){
        FirstButton=false;
        events=cont.getPageEvent(type,f);
        hboxf1.setVisible(true);
        hboxf2.setVisible(true);
        hboxf3.setVisible(true);
        SetEventsTable1(events);

    }




    @Override
    public void update() {
        updateListEvents(PageType.CURRENT,false);
    }



    public void handleAllEvents(ActionEvent actionEvent) {

        updateListEvents2(PageType.CURRENT,false);
    }

    public void handleEventsParticipation(ActionEvent actionEvent) {
        FirstButton=true;
        updateListEvents(PageType.CURRENT,false);
    }

    public void handleCreateEvent(ActionEvent actionEvent) throws IOException, InterruptedException {
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(new File(Algoritm.getFullPath("create_event.fxml")).toURI().toURL());
        AnchorPane create_event=fxmlLoader.load();
        CreateEventController createEventController=fxmlLoader.getController();
        Stage registerStage = new Stage();
        createEventController.set(cont);
        Scene scene = new Scene(create_event);
        registerStage.initModality(Modality.WINDOW_MODAL);
        registerStage.setTitle("Create Event");
        registerStage.setScene(scene);
        registerStage.show();

    }

    public void handleButtonEvent1(ActionEvent actionEvent) {
        Long id_ev = cont.getEventByName(NameEvent1.getText()).getId();
        Long id_ue=cont.getUserEventByIds(user.getId(),id_ev);
        if (Participate1.getText().equals("Cancel"))
            cont.removeUserEventIdUser(id_ue);
        else
        {
            UserEvent ue=new UserEvent(15L,user.getId(),id_ev);
            cont.addUE(ue);
        }

    }
    public void handleButtonEvent2(ActionEvent actionEvent) {
        Long id_ev = cont.getEventByName(NameEvent2.getText()).getId();
        Long id_ue=cont.getUserEventByIds(user.getId(),id_ev);
        if (Participate2.getText().equals("Cancel"))
            cont.removeUserEventIdUser(id_ue);
        else
        {
            UserEvent ue=new UserEvent(13L,user.getId(),id_ev);
            cont.addUE(ue);
        }
    }
    public void handleButtonEvent3(ActionEvent actionEvent) {
        Long id_ev = cont.getEventByName(NameEvent3.getText()).getId();
        Long id_ue=cont.getUserEventByIds(user.getId(),id_ev);
        if (Participate3.getText().equals("Cancel"))
            cont.removeUserEventIdUser(id_ue);
        else
        {
            Long id=Long.valueOf(generateId());
            UserEvent ue=new UserEvent(id,user.getId(),id_ev);
            cont.addUE(ue);
        }
    }

    private int generateId() {
        return cont.getSize()+1;
    }
}
