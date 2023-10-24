package com.example.Controller.OldController;

import com.example.Domain.User;
import com.example.Repository.Memory.UserMemoryRepo;

import java.util.List;

public class UserController extends Controller<Long, User>{

    public UserController(UserMemoryRepo rep) {
        super.repo=rep;
    }


    /**
     * Adds a friend to an user
     * @param user the user that has a new friend
     * @param username the username to be added
     */
    public void addFriend(User user,String username){
        user.addFriend(username);
    }
    /**
     * Removess a friend from an user
     * @param user the user that has no longer that friend
     * @param username the username that has to be removed
     */
    public void removeFriend(User user,String username){
        user.removeFriend(username);
    }

    /**
     * Removes the user forom all of his friends
     * @param username the user that needs to be removed
     */
    public void removeUserFromAllFriends(String username){
        for (User el: (List<User>)repo.getAll()){
            el.removeFriend(username);
        }
    }


    /**
     * Loads Data from a file
     * @param fileUser the location where the users are stored
     * @param fileFriend the location where the friends of the users are stored
     */
    //public void loadData(String fileUser,String fileFriend){repo.loadData(fileUser,fileFriend);}

    /**
     * Saves data to a file
     * @param fileUser the location where the users need to be stored
     * @param fileFriend the location where the friends of the users need to be stored
     */
    //public void saveData(String fileUser,String fileFriend){repo.saveData(fileUser,fileFriend);}

}
