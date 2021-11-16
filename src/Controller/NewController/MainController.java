package Controller.NewController;


import Domain.Persone;
import Domain.Relationship;
import Domain.User;
import Utils.Exceptions.Exception;
import Utils.Exceptions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainController {
    UserController contU;
    RelationshipController contR;
    PersoneController contP;

    /**
     * Basic constructor for this main controller that operates all the secondary controllers
     * @param contU controller for users
     * @param contR controller for relationships
     * @param contP controller for persons
     */
    public MainController(UserController contU, RelationshipController contR,PersoneController contP) {
        this.contU = contU;
        this.contR = contR;
        this.contP = contP;
    }

    /**
     * Adds a user to the repository
     * @param user the user to be added
     * @return true if the user has been saved with success, false otherwise
     * @throws EntityRepoException if there are errors during the saving process
     */
    public boolean addUser(User user) {
        try {
            if(contU.getByOther(user.getUsername())==null) {
                Persone pers = contP.getByOther(user.getPers().getFirstName(), user.getPers().getLastName());
                if (pers == null) contP.add(user.getPers());
                else user.getPers().setId(pers.getId());
            }
        }
        catch(Exception e){
        }
        finally{
            contU.add(user);
        }
        return true;
    }

    /**
     * Adds a relationship to the repository
     * @param rel the rel to be added
     * @return true if the relationship has been saved with success, false otherwise
     * @throws EntityRepoException if there are errors during the saving process
     */
    public boolean addRelationship(Relationship rel) {
        if(contU.getByOther(rel.getFirstUserName())==null || contU.getByOther(rel.getSecondUserName())==null )
        throw new EntityRepoException("A relationship is only applied between tow existing users\n");
        contR.add(rel);
        return true;
    }

    /**
     * Removes a user to the repository by his id
     * @param id id of the user to be deleted
     * @return true if the user has been deleted with success, false otherwise
     * @throws EntityRepoException if there are errors during the removing process
     */
    public boolean removeByUserId(Long id) {
        User user= contU.getById(id);
        if(user==null) throw new UserRepoException("There isnt an user with that if");
        contR.deleteAllRelationsByUsername(user.getUsername());
        contU.removeById(id);
        removeSinglePersoneFromUsers(user.getPers());
        return true;
    }

    /**
     * Removes a relationship to the repository by his id
     * @param id id of the relationship to be deleted
     * @return true if the relationship has been deleted with success, false otherwise
     * @throws EntityRepoException if there are errors during the removing process
     */
    public boolean removeByRelationshipId(Long id) {
        contR.removeById(id);
        return true;
    }

    /**
     * Removes a user to the repository by his username
     * @param username username of the user to be deleted
     * @return true if the user has been deleted with success, false otherwise
     * @throws EntityRepoException if there are errors during the removing process
     */
    public boolean removeUserByUsername(String username) {
        User user= contU.getByOther(username);
        if(user==null) throw new UserRepoException("There isnt an user with that username");
        contR.deleteAllRelationsByUsername(user.getUsername());
        contU.removeByOthers(username);
        removeSinglePersoneFromUsers(user.getPers());
        return true;
    }

    /**
     * Removes a persone to the repository
     * This function is called when all the users that contains the persone are deleted
     * @param persone the person to be deleted
     * @return true if the person has been deleted with success, false otherwise
     * @throws EntityRepoException if there are errors during the removing process
     */
    private boolean removeSinglePersoneFromUsers(Persone persone){
        boolean found=false;
        for(User user: contU.getAll()){
            if(user.getPers().equals(persone)){
                found=true;
                break;
            }
        }
        if(!found) contP.removeById(persone.getId());
        return found;
    }

    /**
     * Removes a relationship to the repository by his usernames
     * @param username1 first username of the relationship to be deleted
     * @param username2 second username of the relationship to be deleted
     * @return true if the relationship has been deleted with success, false otherwise
     * @throws EntityRepoException if there are errors during the removing process
     */
    public boolean removeRelationshipByUsernames(String username1,String username2) {
        contR.removeByOthers(username1,username2);
        return true;
    }

    /**
     * Retrieves a user from the repository by his id
     * @param id id of the user to be searched
     * @return the user with the corespondent id or
     * null if there is not a user with that id
     * @throws EntityRepoException if there are errors during the retrieving process
     * @throws UserRepoException if there is not a user with that id
     */
    public User getUserById(Long id) {
        User user= contU.getById(id);
        if(user==null) throw new UserRepoException("There isnt an user with that id");
        Persone pers=contP.getById(user.getPers().getId());
        user.setPers(pers);
        return user;
    }

    /**
     * Retrieves a relationship from the repository by his id
     * @param id id of the relationship to be searched
     * @return the relationshipp with the corespondent id or
     * null if there is not a relationship with that id
     * @throws EntityRepoException if there are errors during the retrieving process
     * @throws RelationshipRepoException if there is not a relationship with that id
     */
    public Relationship getRelationshipById(Long id) {
        Relationship rel = contR.getById(id);
        if(rel==null) throw new RelationshipRepoException("There isnt a relationship with that id");
        return rel;
    }

    /**
     * Retrieves a user from the repository by his username
     * @param username username of the user to be searched
     * @return the user with the corespondent username or
     * null if there is not a user with that username
     * @throws EntityRepoException if there are errors during the retrieving process
     * @throws UserRepoException if there is not a user with that username
     */
    public User getUserByOther(String username) {
        User user= contU.getByOther(username);
        if(user==null) throw new UserRepoException("There isnt an user with that username");
        Persone pers=contP.getById(user.getPers().getId());
        user.setPers(pers);
        return user;
    }

    /**
     * Retrieves a relationship from the repository by his usernames
     * @param username1 first username of the relationship to be searched
     * @param username2 second username of the relationship to be searched
     * @return the relationship with the corespondent usernames or
     * null if there is not a relationship with that usernames
     * @throws EntityRepoException if there are errors during the retrieving process
     * @throws RelationshipRepoException if there is not a relationship with that usernames
     */
    public Relationship getRelationshipByOther(String username1,String username2) {
        Relationship rel = contR.getByOther(username1,username2);
        if(rel==null) throw new RelationshipRepoException("There isnt a relationship with that usernames");
        return rel;
    }
    /**
     * Retrieves a list with all the users from the repository
     * @return a list with all the users from the repository
     * @throws EntityException if there are errors during the retrieving process
     */
    public List<User> getAllUsers() {
        Map<String,User> listU= new HashMap<>();
        ///loading users
        for(User el:contU.getAll()){
            listU.put(el.getUsername(),el);
        }
        ///loading persones
        for (Map.Entry<String,User> el: listU.entrySet()){
            Persone pers=contP.getById(el.getValue().getPers().getId());
            el.getValue().setPers(pers);
        }
        ///loading friends
        for(Relationship rel: contR.getAll()){
            //for the first user
            User user1=listU.get(rel.getFirstUserName());
            user1.addFriend(rel.getSecondUserName());
            listU.put(user1.getUsername(),user1);

            //for the second user
            User user2=listU.get(rel.getSecondUserName());
            user2.addFriend(rel.getFirstUserName());
            listU.put(user2.getUsername(),user2);
        }
        ///creating list of users
        List<User> list= new ArrayList<>();
        for (Map.Entry<String,User> el: listU.entrySet()){
            list.add(el.getValue());
        }

        return list;
    }

    /**
     * Retrieves a list with all the relationships from the repository
     * @return a list with all the relationships from the repository
     * @throws EntityException if there are errors during the retrieving process
     */
    public List<Relationship> getAllRelationships() {
        return contR.getAll();
    }

    /**
     * Retrieves the number of all the users from the repository
     * @return the number of all the users from the repository
     * @throws EntityException if there are errors during this process
     */
    public int getUserSize() {
        return contU.getSize();
    }

    /**
     * Retrieves the number of all the relationships from the repository
     * @return the number of all the relationships from the repository
     * @throws EntityException if there are errors during the removing process
     */
    public int getRelationshipSize() {
        return contR.getSize();
    }

    /**
     * This function gives the current number of isolated networks
     * @return an integer which represents the current number of distinct networks
     * @throws EntityException if there are errors during this process
     */
    public int getNumberOfCommunities(){
        return contR.getNumberOfCommunities(contU.getSize());
    }

    /**
     * This function gives the most connected network
     * @return a list of string which contains the usernames of the users
     * that take part of the community
     * @throws EntityException if there are errors during this process
     */
    public List<String> getTheMostSociableCommunity(){
        return contR.getTheMostSociableCommunity(contU.getSize());
    }
}
