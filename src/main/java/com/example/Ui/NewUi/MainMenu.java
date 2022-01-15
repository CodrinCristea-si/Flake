package com.example.Ui.NewUi;

import com.example.Controller.NewController.MainController;
import com.example.Controller.Validator.AbstractValidator;
import com.example.Controller.Validator.ContextValidator;
import com.example.Controller.Validator.Strategy;
import com.example.Controller.Validator.Validator;
import com.example.Domain.*;

import com.example.Utils.Exceptions.Exception;


import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.example.Utils.Exceptions.*;
import com.example.Utils.Exceptions.Exception;


public class MainMenu {
    String currentMode=new String();
    MainController cont;

    public MainMenu(MainController cont) {
        this.cont=cont;
    }

    /**
     * Generates a map with the option and the corespondent number of that option on the maniu
     * @return a map with Integer as Keys and String as Values
     */
    private Map<Integer,String> generateOptions(){
        Map<Integer,String> options= new HashMap();
        /* 
        am scris un comentariu
        */
        options.put(1,"- to add a user");
        options.put(2,"- to add a a relationship between two users");
        options.put(3,"- to remove a user");
        options.put(4,"- to remove a relationship");
        options.put(5,"- print all users");
        options.put(6,"- print all relationships");
        options.put(7,"- number of cummunities");
        options.put(8,"- the most sociable community");
        options.put(9,"-get friends by username");
        options.put(10,"-get friends by username and month");
        options.put(11,"- to exit");
        return options;
    }

    /**
     * prints the menu of the screen
     */
    private void printMenu(){
        Map<Integer,String> options= generateOptions();
        System.out.println("Main Manu");
        for(int i=1; i<=options.size();i++)
            System.out.println(Integer.toString(i)+options.get(i));
        System.out.print("Option: ");
    }

    /**
     * The main manu of the application
     */
    public void mainMenu(){
        boolean done=false;
        try {
            while (!done) {
                printMenu();
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();
                switch (option) {
                    case 1:
                        addUser();
                        break;
                    case 2:
                        addRelationship();
                        break;
                    case 3:
                        removeUser();
                        break;
                    case 4:
                        removeRelationship();
                        break;
                    case 5:
                        printAllUsers();
                        break;
                    case 6:
                        printAllRelationships();
                        break;
                    case 7:
                        printNumberOfCommunities();
                        break;
                    case 8:
                        printTheMostSociableCommunity();
                        break;
                    case 9:
                        getFriendsByUsername();
                        break;
                    case 10:
                        getFriendsByUsernameAndMonth();
                        break;
                    case 11:
                        done = true;
                        break;
                    case 12:
                       testareEvent();
                        break;
                    case 13:

                        testareUserEvent();
                        break;
                    case 404:
                        setCurrentMode();
                    default:
                        System.out.println("Invalid Option");
                }
            }
        }
         catch (Exception e) {
             System.out.println(e.getDescription());
        }
    }

    /**
     * adds an user
     */
    private void addUser(){
        long id=0;
        String username;
        String firstName;
        String lastName;
        Scanner scan = new Scanner(System.in);
        System.out.println("Provide all the necesary information: ");
        if(currentMode.equals("admin")){
            System.out.print("Id: ");
            id= scan.nextLong();
            scan.nextLine();
        }
        System.out.println("First Name :");
        firstName=scan.nextLine();
        System.out.println("Last Name :");
        lastName=scan.nextLine();
        System.out.print("Username: ");
        username=scan.nextLine();

        try{
            User user;
            if(id!=0)user=new User(id,username,new Persone(firstName,lastName));
            else user=new User(username,new Persone(firstName,lastName));
            Validator vali= ContextValidator.createValidator(Strategy.USER);
            vali.validate(user);
            System.out.println(user);
            cont.addUser(user);
            System.out.println("User created succesfully");
        }
        catch(Exception e){
            System.out.println(e.getDescription());
        }

    }

    /**
     * adds a relationship
     */



    private void addRelationship(){
        long id=0;
        String username1,username2;
        int year,month,day;
        Scanner scan = new Scanner(System.in);
        System.out.println("Provide all the necesary information: ");
        if(currentMode.equals("admin")){
            System.out.print("Id: ");
            id= scan.nextLong();
            scan.nextLine();
        }
        System.out.println("First Username :");
        username1=scan.nextLine();
        System.out.println("Second Username :");
        username2=scan.nextLine();

        System.out.println("Add the date : ");
        System.out.println("year: ");
        year=scan.nextInt();
        System.out.println("month: ");
        month=scan.nextInt();
        System.out.println("day: ");
        day=scan.nextInt();
        try{
            Relationship rel;
            if(id!=0)
                rel=new Relationship(id,username1,username2,
                   LocalDate.of(year,month,day));
            else rel=new Relationship(username1,username2,
                    LocalDate.of(year,month,day));

            Validator vali= ContextValidator.createValidator(Strategy.RELATIONSHIP);
            vali.validate(rel);
            cont.addRelationship(rel);
            System.out.println("Relationship created succesfully");

        }
        catch (DateTimeException e){
            System.out.println(e.getMessage());

        }

        catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * removes an user
     */
    private void removeUser(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Delete by : 1-Id or 2-Username\nYour option: ");
        int option=scan.nextInt();
        switch(option){
            case 1:
                removeUserById();
                break;
            case 2:
                removeUserByUsername();
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    /**
     * removes a relationship
     */
    private void removeRelationship(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Delete by : 1-Id or 2-By two usernames\nYour option: ");
        int option=scan.nextInt();
        switch(option){
            case 1:
                removeRelationshipById();
                break;
            case 2:
                removeRelationshipByUsernames();
                break;
            default:
                System.out.println("Invalid option!");
        }
    }

    /**
     * removes a relationship by id
     */
    private void removeRelationshipById(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Id:");
        long id=scan.nextLong();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkId(id);
            cont.removeRelationshipById(id);
            System.out.println("Relationship removed with succes");
        }
        catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * removes a relationship by the corespondent usernames
     */
    private void removeRelationshipByUsernames(){
        Scanner scan=new Scanner(System.in);
        System.out.println("First Username :");
        String username1=scan.nextLine();
        System.out.println("Second Username");
        String username2=scan.nextLine();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkUserName(username1);
            vali.checkUserName(username2);
            cont.removeRelationshipByUsernames(username1,username2);
            System.out.println("Relationship removed with succes");
        }
        catch(Exception e){
            System.out.println(e.getDescription());
        }

    }

    /**
     * removes an user by id
     */
    private void removeUserById(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Id:");
        long id=scan.nextLong();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkId(id);
            cont.removeUserById(id);
            System.out.println("User removed with succes");
        }
        catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * removes an user by username
     */
    private void removeUserByUsername(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Username:");
        String username=scan.nextLine();
        try{
            AbstractValidator vali=ContextValidator.createValidator(Strategy.USER);
            vali.checkUserName(username);
            cont.removeUserByUsername(username);
            System.out.println("User removed with succes");
        }
        catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * set the current state of the class
     */
    private void setCurrentMode(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Set the current mode : ");
        currentMode= scan.nextLine();
        System.out.println("Entering to " + currentMode +" mode");
    }

    /**
     * prints all the users to the console
     */
    private void printAllUsers(){
        try {
            List<User> list = cont.getAllUsers();
            for (User el : list) {
                System.out.println(el);
                System.out.println(el.getFriendsList());
            }
        }catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * prints all the relationship to the console
     */
    private void printAllRelationships(){
        try{
            List<Relationship> list=cont.getAllRelationships();
            for (Relationship el: list){

                System.out.println(el.toString());

            }
        }catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    /**
     * prints the current number of networks to the console
     */
    private void printNumberOfCommunities(){
        System.out.println("Number of active communities" + cont.getNumberOfCommunities());
    }

    /**
     * prints the most sociable network to the console
     */
    private void printTheMostSociableCommunity(){
        List<String>list= cont.getTheMostSociableCommunity();
        System.out.println("The most sociable communty is formed with : ");
        for(String el:list)
            System.out.print(el);
        System.out.println("It has "+ list.size()+ " members");
    }


    /**
     * prints the first, last name, and date
     * for all the friends of a user by his username
     */
    private void getFriendsByUsername(){
        String username;
        Scanner scan = new Scanner(System.in);
        System.out.println("Add the username: ");
        username=scan.nextLine();
        //cauta in repo
        System.out.println(cont.getFriendsByUsername(username));

        }

    private void getFriendsByUsernameAndMonth(){
        String username;
        int month;
        Scanner scan = new Scanner(System.in);
        System.out.println("Add the username: ");
        username=scan.nextLine();
        System.out.println("Add month: ");
        month=scan.nextInt();
        //cauta in repo
        if(month<=0 || month>=13) throw new Exception("Invalid month!!!");
        System.out.println(cont.getFriendsByUsernameAndMonth(username,month));

    }
    public void testareEvent(){
        System.out.println(cont.getAllEvents());
        Event ev,ev1,ev2;
        ev=new Event(4L,"untold","musical",LocalDate.now());
        ev1=new Event(2L,"aaa","musical",LocalDate.now());
        //ev5=new Event("aaa","musical",LocalDate.now());
        ev2=new Event(3L,"bbb","musical",LocalDate.now());
        System.out.println("ajunge aici");
        try {
            cont.addEvent(ev1);
           //
            cont.addEvent(ev2);
            //cont.addEvent(ev);
        }
        catch (EntityRepoException as){
            System.out.println(as.getDescription());
        }
        System.out.println(cont.getAllEvents());
        /*cont.removeEventId(2L);

        System.out.println(cont.getAllEvents());
        cont.removeEventId(10L);*/

    }

    public void testareUserEvent(){
        List<UserEvent> list=cont.getAllUserEvent();
        for(UserEvent u: list)
        {
          //  System.out.println(cont.getUserById(u.getId_user()).getPers().getLastName());
            //System.out.println(u.getId_event());
        }

      //  UserEvent userEvent=new UserEvent(5908993355151158916L,1L);
       // cont.addUE(userEvent);
       /* List<UserEvent> list1=cont.getAllUserEvent();
        for(UserEvent u: list1)
        {
            System.out.println(cont.getUserById(u.getId_user()));
            System.out.println(u.getId_event());
        }

        try {
            UserEvent userEvent1=new UserEvent(590L,1L);
            cont.addUE(userEvent1);
        }
        catch (EntityRepoException as){
            System.out.println(as.getDescription());
        }
*/
       // cont.removeUserEventIdUser(3L);
        List<UserEvent> list1=cont.getAllUserEvent();
        //System.out.println(list1);
        for(UserEvent u: list1)
        {
           // System.out.println(cont.getUserById(u.getId_user()).getPers().getLastName());
            System.out.println(u.getId_ue());
        }



    }
    }




