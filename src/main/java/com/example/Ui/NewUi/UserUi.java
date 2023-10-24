package com.example.Ui.NewUi;

import com.example.Controller.NewController.MainController;
import com.example.Controller.Validator.ContextValidator;
import com.example.Controller.Validator.Strategy;
import com.example.Controller.Validator.Validator;
import com.example.Domain.Message;
import com.example.Domain.Persone;
import com.example.Domain.Relationship;
import com.example.Domain.User;

import com.example.Utils.Exceptions.Exception;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.example.Utils.Exceptions.*;
import com.example.Utils.Exceptions.Exception;


public class UserUi {
    MainController cont;
    User user;

    public UserUi(MainController cont, User user) {
        this.cont = cont;
        this.user = user;
    }

    /**
     * Generates a map with the option and the corespondent number of that option on the maniu
     * @return a map with Integer as Keys and String as Values
     */
    private Map<Integer,String> generateMainOptions(){
        Map<Integer,String> options= new HashMap();
        /*
        am scris un comentariu
        */
        options.put(1,"- to manage friends list");
        options.put(2,"- to conversation section");
        options.put(3,"- to logout");
        return options;
    }

    private Map<Integer,String> generateFriendsListOptions(){
        Map<Integer,String> options= new HashMap();
        /*
        am scris un comentariu
        */
        options.put(1,"- to see friends list");
        options.put(2,"- to send a friend request");
        options.put(3,"- to see all friend requests");
        options.put(4,"- to accept a friend request");
        options.put(5,"- to reject a friend request");
        options.put(6,"- show all requests");
        options.put(7,"- to exit");
        return options;
    }


    private Map<Integer,String> generateConversationListOptions(){
        Map<Integer,String> options= new HashMap();
        /*
        am scris un comentariu ador!!!
        */
        options.put(1,"- to see the history of a conversation");
        options.put(2,"- to sent a message");
        options.put(3,"- to sent a message to multiple friends");
        options.put(4,"- to reply to a message");
        options.put(5,"- to exit");
        return options;
    }
    /**
     * prints the menu of the screen
     */
    private void printMainMenu(){
        Map<Integer,String> options= generateMainOptions();
        System.out.println("Main Manu");
        for(int i=1; i<=options.size();i++)
            System.out.println(Integer.toString(i)+options.get(i));
        System.out.print("Option: ");
    }
    private void printFriendMenu(){
        Map<Integer,String> options= generateFriendsListOptions();
        System.out.println("Main Manu");
        for(int i=1; i<=options.size();i++)
            System.out.println(Integer.toString(i)+options.get(i));
        System.out.print("Option: ");
    }
    private void printConvoMenu(){
        Map<Integer,String> options= generateConversationListOptions();
        System.out.println("Main Manu");
        for(int i=1; i<=options.size();i++)
            System.out.println(Integer.toString(i)+options.get(i));
        System.out.print("Option: ");
    }


    /**
     * The main menu of the application
     */
    public void mainMenu(){
        boolean done=false;
        try {
            while (!done) {
                printMainMenu();
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();
                switch (option) {
                    case 1:
                        mainFriendsMenu();
                        break;
                    case 2:
                        mainConvoSectionMenu();
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
     * The main menu of the application
     */
    public void mainFriendsMenu(){
        boolean done=false;
        try {
            while (!done) {
                printFriendMenu();
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();
                switch (option) {
                    case 1:
                        seeFriendsList();
                        break;
                    case 2:
                        SendFriendRequest();
                        break;
                    case 3:
                        GetRequestsForUser();
                        break;
                    case 4:
                        try {
                            AcceptFriendsRequest();
                        }
                        catch (Exception e) {
                            System.out.println(e.getDescription());
                        }

                        break;
                    case 5:
                        try {
                        CancelFriendsRequest();
                        }
                        catch (Exception e) {
                            System.out.println(e.getDescription());
                        }
                        break;

                    case 6:
                        System.out.println(cont.RequestsForAUser(user.getUsername()));
                    case 7:
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
     * The main menu of the application
     */
    public void mainConvoSectionMenu(){
        boolean done=false;
        try {
            while (!done) {
                printConvoMenu();
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();
                switch (option) {
                    case 1:
                        loadConversation();
                        break;
                    case 2:
                        sendSimpleMessage();
                        break;
                    case 3:
                        sendMultipleMessage();
                        break;
                    case 4:
                        sendReplyMessage();
                        break;
                    case 5:
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


    public void seeFriendsList(){
        System.out.println(cont.getFriendsByUsername(user.getUsername()));
    }

    public void sendSimpleMessage(){
        Scanner scan= new Scanner(System.in);
        String username,message;
        System.out.println("What do you want to send?");
        message= scan.nextLine();
        System.out.println("Who do you want to sent?");
        username=scan.nextLine();
        Validator vali= ContextValidator.createValidator(Strategy.MESSAGE);
        Message mess= new Message(user,message, Arrays.asList(new User(username, new Persone("",""))), LocalDateTime.now(),null);
        try{
            vali.validate(mess);
            cont.sendMessage(mess);
            System.out.println("Message sent successfully!");
        }catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    public void loadConversation(){
        Scanner scan= new Scanner(System.in);
        String username;
        System.out.println("Who do you want to see?");
        username=scan.nextLine();
        List<Message> list=cont.loadConversation(user.getUsername(),username);
        if(list.isEmpty()) System.out.println("There are no history messages with this user");
        for( Message mess: list){
            System.out.println("Id:"+ mess.getId());
            if(mess.getReply()!=null) System.out.println("Reply to :" +mess.getReply().getId());
            System.out.println(mess.getFrom().getUsername()+" -> "+ mess.getReceivers().get(0).getUsername()+"("+ mess.getDate()+")");
            System.out.println(mess.getMessage());
            System.out.println("\n");
        }
    }

    public void sendMultipleMessage(){
        Scanner scan= new Scanner(System.in);
        String username,message;
        System.out.println("What do you want to send?");
        message= scan.nextLine();
        List<User> listUsernames= new ArrayList<>();
        System.out.println("Who do you want to send?(Enter to stop)");
        username = scan.nextLine();
        while(!username.isEmpty()) {
            listUsernames.add(new User(username,new Persone("","")));
            System.out.println("Who do you want to send?(Enter to stop)");
            username = scan.nextLine();
        }
        Validator vali= ContextValidator.createValidator(Strategy.MESSAGE);
        Message mess= new Message(user,message,listUsernames, LocalDateTime.now(),null);
        try{
            vali.validate(mess);
            cont.sendMessageToAll(mess);
            System.out.println("Message sent successfully to the users!");
        }catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    public void sendReplyMessage(){
        Scanner scan= new Scanner(System.in);
        String username,message;
        Long id;
        System.out.println("What do you want to send?");
        message= scan.nextLine();
        System.out.println("Who do you want to sent?");
        username=scan.nextLine();
        System.out.println("The id of the message that you want to reply");
        id=scan.nextLong();
        Validator vali= ContextValidator.createValidator(Strategy.MESSAGE);
        Message mess= new Message(user,message, Arrays.asList(new User(username, new Persone("",""))), LocalDateTime.now(),new Message(id,user,message,Arrays.asList(new User(username, new Persone("","")))));
        try{
            vali.validate(mess);
            cont.sendMessage(mess);
            System.out.println("Reply sent successfully!");
        }catch(Exception e){
            System.out.println(e.getDescription());
        }
    }

    public void GetRequestsForUser(){

        List<String> list=cont.getFriendshipsRequests(user.getUsername());
        if(list.isEmpty())
            System.out.println("There are no friend requests for this user :( .");
        else {
            for (String s : list) {
                System.out.println(s);
            }
        }
    }

    public void AcceptFriendsRequest(){

        Scanner scan=new Scanner(System.in);
        System.out.println("Add username to accept: ");
        String username= scan.nextLine();
        int ok=0;
        List<String> list=cont.getFriendshipsRequests(user.getUsername());
        for(String s: list)
            if(s.equals(username))
                ok=1;
        if(ok==0)
            throw new Exception("this user does not exist in the request list :(");

        cont.UpdateStatusRequest("accept",username,user.getUsername());

    }

    public void CancelFriendsRequest(){

        Scanner scan=new Scanner(System.in);
        System.out.println("Add username to cancel: ");
        String username= scan.nextLine();
        int ok=0;
        List<String> list=cont.getFriendshipsRequests(user.getUsername());
        for(String s: list)
            if(s.equals(username))
                ok=1;
        if(ok==0)
            throw new Exception("this user does not exist in the request list :(");

        cont.UpdateStatusRequest("cancel",username,user.getUsername());


    }


    public void SendFriendRequest(){
        Scanner scan=new Scanner(System.in);
        System.out.println("Add username to add friend: ");
        String username= scan.nextLine();
        Relationship rel=new Relationship(user.getUsername(),username, LocalDate.now(),"pending");
        try {
            cont.AddRequest(rel);

            System.out.println("Request sent successfully ! <3");
        }
        catch (Exception e){
            System.out.println(e.getDescription());
        }
    }

}
