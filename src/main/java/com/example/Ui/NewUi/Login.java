package com.example.Ui.NewUi;

import com.example.Controller.NewController.MainController;
import com.example.Controller.Validator.ContextValidator;
import com.example.Controller.Validator.Strategy;
import com.example.Controller.Validator.Validator;
import com.example.Domain.Persone;
import com.example.Domain.User;

import com.example.Utils.Exceptions.*;

import com.example.Utils.Exceptions.Exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Login {
    MainController cont;

    public Login(MainController cont) {
        this.cont = cont;
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
        options.put(1,"- to register");
        options.put(2,"- to login");
        options.put(3,"- to exit");
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
                        login();
                        break;
                    case 3:
                        done=true;
                        break;
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
        String username;
        String firstName;
        String lastName;
        Scanner scan = new Scanner(System.in);
        System.out.println("Provide all the necesary information: ");
        System.out.println("First Name :");
        firstName=scan.nextLine();
        System.out.println("Last Name :");
        lastName=scan.nextLine();
        System.out.print("Username: ");
        username=scan.nextLine();
        try{
            User user;
            user=new User(username,new Persone(firstName,lastName));
            Validator vali= ContextValidator.createValidator(Strategy.USER);
            vali.validate(user);

            //System.out.println(user);

            cont.addUser(user);
            System.out.println("User created succesfully");
        }
        catch(Exception e){
            System.out.println(e.getDescription());
        }

    }

    public void login(){
        String username;
        Scanner scan = new Scanner(System.in);
        System.out.println("Login as : ");
        username=scan.nextLine();
        if(username.equals("admin")){
            MainMenu uiA=new MainMenu(cont);
            uiA.mainMenu();
        }
        else {
            try {
                UserUi uiU = new UserUi(cont, cont.getUserByUsername(username));
                uiU.mainMenu();
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
