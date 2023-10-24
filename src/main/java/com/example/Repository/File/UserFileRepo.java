package com.example.Repository.File;

import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Repository.UserRepo;
import com.example.Utils.Exceptions.EntityRepoException;
import com.example.Utils.Generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class UserFileRepo extends FileRepo<Long,User> implements UserRepo {
    private String delimiter=new String(",");
    public UserFileRepo() {
        initiateRepo();
    }
    /**
     * Loads data from files
     * @param other a list of strings with the name of the files
     */
    @Override
    public void loadData(String... other){
        loadUsers(other[0]);
        loadFriends(other[1]);
    }
    /**
     * Loads all the users from a file
     * @param filename the name of the file
     */
    public void loadUsers(String filename){
        try{
            File file=new File(filename);
            Scanner scan=new Scanner(file);
            while(scan.hasNextLine()){
                String data=scan.nextLine();
                List<String> values= List.of(data.split(delimiter));
                User user= new User(Long.parseLong(values.get(0)),values.get(1), new Persone(values.get(2),values.get(3)));
                save(user);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw(new EntityRepoException("The data cannot be loaded"));
        }
    }

    /**
     * Loads all the friends from a file
     * @param filename the name of the file
     */
    public void loadFriends(String filename)  {
        File file=new File(filename);
        Scanner scan= null;
        try {
            scan = new Scanner(file);
            while(scan.hasNextLine()){
                String data=scan.nextLine();
                List<String> values= List.of(data.split(delimiter));
                Long id=Long.parseLong(values.get(0));
                User user=get(id);
                int times=Integer.parseInt(values.get(1));
                for (int i=0;i<times;i++){
                    user.addFriend(values.get(i+2));
                }
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw(new EntityRepoException("The data cannot be loaded"));
        }
    }

    /**
     * Saves data to files
     * @param other a list of strings with the name of the files
     */
    @Override
    public void saveData(String... other){
        saveUsers(other[0]);
        saveFriends(other[1]);
    }

    /**
     * Saves all the users to a file
     * @param filename the name of the file
     */
    public void saveUsers(String filename){
        try {
            FileWriter file=new FileWriter(filename);
            for(User el: getAll()){
                file.write(el.getId().toString()+delimiter+el.getUsername()+
                            delimiter+el.getPers().getFirstName()+delimiter+el.getPers().getLastName()+"\n");
            }
            file.close();
        } catch (IOException e) {
            throw(new EntityRepoException("The data cannot be saved"));
        }
    }

    /**
     * Saves all the friends to a file
     * @param filename the name of the file
     */
    public void saveFriends(String filename){
        try {
            FileWriter file=new FileWriter(filename);
            for(User el: getAll()){
                file.write(el.getId().toString()+delimiter+el.getFriendsList().size());
                for(String elem:el.getFriendsList()){
                    file.write(delimiter+elem);
                }
                file.write("\n");
            }
            file.close();
        } catch (IOException e) {
            throw(new EntityRepoException("The data cannot be saved"));
        }
    }

    /**
     * Checks if there is any user that has the corespondent username
     * @param username the username of the user to be found
     * @return the corespondent object or null otherwise
     */
    public User getByUserName(String username){
        User user=null;
        for(User use:getAll()){
            if(use.getUsername().equals(username))
                user=use;
        }
        return user;
    }

    @Override
    public User getUserLogin(String username, String hash_pass) {
        return null;
    }

    /**
     * Saves an user to repository
     * @param entity the object to be saved
     * @return true if it has been saved with succes, flse otherwise
     */
    @Override
    public boolean save(User entity) {
        if(getByUserName(entity.getUsername())!=null)return false;
        return saveToRepo(entity);
    }
    /**
     *Generates an id for an entity
     * @return and id that there isnt in repository
     */
    @Override
    public Long generateId() {
        return Generator.generateId(getAllIds());
    }

    /**
     * Checks if there is any user that has the corespondent username
     * @param other the username of the user to be found
     * @return the corespondent object or null otherwise
     */
    @Override
    public User getByOther(String... other) {
        return getByUserName(other[0]);
    }
}
