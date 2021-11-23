package Controller.NewController;

import Domain.Message;
import Domain.Persone;
import Domain.Relationship;
import Domain.User;
import Utils.Exceptions.Exception;
import Utils.Exceptions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MainController {
    UserController contU;
    RelationshipController contR;
    PersoneController contP;
    MessageController contM;
    RequestsController contRQ;

    public MainController(UserController contU, RelationshipController contR, PersoneController contP) {
        this.contU = contU;
        this.contR = contR;
        this.contP = contP;
    }

    /**
     * Basic constructor for this main controller that operates all the secondary controllers
     * @param contU controller for users
     * @param contR controller for relationships
     * @param contP controller for persons
     */



    public MainController(UserController contU, RelationshipController contR,PersoneController contP,MessageController contM) {
        this.contU = contU;
        this.contR = contR;
        this.contP = contP;
        this.contM = contM;

    }

    public MainController(UserController contU, RelationshipController contR, PersoneController contP, MessageController contM, RequestsController contRQ) {
        this.contU = contU;
        this.contR = contR;
        this.contP = contP;
        this.contM = contM;
        this.contRQ = contRQ;
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
     * search in friendships list for the friends with the given usernames
     * @param username the username for the a friend
     * @param username1 the username for the other friend
     * @return the date when the friendship of the first and second user started
     */
    public LocalDate getDate(String username,String username1){
        for(Relationship r: contR.getAll()) {
            if (r.getFirstUserName().equals(username) && r.getSecondUserName().equals(username1))
                return r.getDtf();
            if (r.getFirstUserName().equals(username1) && r.getSecondUserName().equals(username))
                return r.getDtf();
        }
            return null;
    }

    /**
     *  search in list of users for the user with given username
     * @param username of a users
     * @return the name and last name for a user
     */
    public Persone getPersonByUsername(String username){

        for(User user:getAllUsers())
            if(user.getUsername().equals(username))
               return user.getPers();
            return null;
    }

    /**
     * check if it exists a friendships  between username and username1 that started in the month
     * @param month
     * @param username
     * @param username1
     * @return true if there exist and false other
     */
    public boolean Month(int month,String username,String username1){
        for(Relationship r: contR.getAll()) {
            if (r.getFirstUserName().equals(username) && r.getSecondUserName().equals(username1)
                    && r.getDtf().getMonth().getValue()==month)
                return true;

            if (r.getFirstUserName().equals(username1) && r.getSecondUserName().equals(username)
                    && r.getDtf().getMonth().getValue()==month)
                return true;
        }
            return false;

    }


    /**
     * search in users list for the friends of the user with given username
     * @param username
     * @return return a list of usernames of the friends of the user
     */
    public List<String> getUserFriendsUsername(String username){
        for (User user : getAllUsers()) {
            if(user.getUsername().equals(username))
            return user.getFriendsList();}
        return null;
    }



    /**
     * for each username add in a list the specific user
     * @param usernameList a list of usernames
     * @return a list of user with the given usernames
     */
    public List<User> ListUserByUsername(List<String> usernameList){
        List<User> userList=new ArrayList<>();
        for(String i : usernameList)
            userList.add(getUserByOther(i));
        return userList;

    }

    /**
     * for each username add in a list the specific user
     * @param usernameList a list of usernames
     * @return a list of user with the given usernames
     */public List<User> ListUserByUsernameAndMonth(List<String> usernameList,int month,String username){
        List<User> userList=new ArrayList<>();
        for(String i : usernameList)
            if(Month(month,i,username)==true)
                  userList.add(getUserByOther(i));
        return userList;
    }

    public  Map<Persone,LocalDate> getFriendsByUsername(String username){
        List<String> friendsUsername=getUserFriendsUsername(username);
        List<User> list=ListUserByUsername(friendsUsername);

        Map<Persone,String> map=list
                .stream()
                 .collect(Collectors.toMap(
                         y->getPersonByUsername(y.getUsername()),
                         User::getUsername
                 ));
        Map<Persone,LocalDate > map1=
                map.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                        x->getDate(x.getValue(),username)
                        ));
        return map1;
    }



    public  Map<Persone,LocalDate> getFriendsByUsernameAndMonth(String username,int month){
        List<String> friendsUsername=getUserFriendsUsername(username);
        List<User> list=ListUserByUsernameAndMonth(friendsUsername,month,username);

        Map<Persone,String> map=list
                .stream()
                .collect(Collectors.toMap(
                        y->getPersonByUsername(y.getUsername()),
                        User::getUsername
                ));
        Map<Persone,LocalDate > map1=
                map.entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                x->getDate(x.getValue(),username)
                        ));
        return map1;
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

    public User getUserByUsername(String username){
        User user = contU.getByOther(username);
        if(user==null) throw new UserRepoException("there is no user with that username");
        Persone pers=contP.getById(user.getPers().getId());
        user.setPers(pers);
        return user;
    }

    public List<Message> loadConversation(String username1,String username2){
        if(getUserByUsername(username1)==null || getUserByUsername(username2)==null )
            throw new UserException("There are no users with that usernames");
        List<Message>listMess=contM.loadConversation(username1,username2);
        for(var mess: listMess){
            mess.setFrom(getUserByUsername(mess.getFrom().getUsername()));
            mess.setReceivers(Arrays.asList(getUserByUsername(mess.getReceivers().get(0).getUsername())));
        }
        Comparator<Message> messageComparator= (m1,m2)->{
            return Math.toIntExact(Long.valueOf(m1.getDate().compareTo(m2.getDate())));
        };
        Collections.sort(listMess,messageComparator);
        return listMess;
    }

    public boolean sendMessage(Message message){
        Relationship rel=contR.getByOther(message.getFrom().getUsername(),message.getReceivers().get(0).getUsername());
        if(rel==null) throw new RelationshipRepoException("There is no relationship between these 2 users");
        User sender=getUserByUsername(message.getFrom().getUsername());
        User receiver=getUserByUsername(message.getReceivers().get(0).getUsername());
        contM.add(new Message(message.getId(),sender, message.getMessage(), Arrays.asList(receiver), LocalDateTime.now(),message.getReply()));
        return true;
    }

    public boolean sendMessageToAll(Message message){
        for (int i=0;i<message.getReceivers().size();i++) {
            Relationship rel = contR.getByOther(message.getFrom().getUsername(), message.getReceivers().get(i).getUsername());
            if (rel == null) throw new RelationshipRepoException("There is no relationship between these 2 users");
        }
        User sender=getUserByUsername(message.getFrom().getUsername());
        List<User> receivers=new ArrayList<>();
        for (int i=0;i<message.getReceivers().size();i++) {
            User receiver = getUserByUsername(message.getReceivers().get(i).getUsername());
            if(receiver==null)
                throw new UserException("There are no users with that usernames");
            receivers.add(receiver);
        }
        if(!contM.add(new Message(message.getId(),sender, message.getMessage(), receivers, LocalDateTime.now(),message.getReply())))
            throw new MessageException("The message cannot be sent");
        return true;
    }

    public List<Relationship> getAllRequests(){
        return contRQ.getAll();
    }

    //lista de usernami care au trimis cereri de prietenie
    public List<String> getFriendshipsRequests(String username){

        List<String> list=new ArrayList<>();
        for(Relationship r: contRQ.getAll())
            if(r.getFirstUserName().equals(username) && r.getStatus().equals("pending"))
                list.add(r.getSecondUserName());

        return list;
    }

    public void UpdateStatusRequest(String status,String username){
        Relationship rel=getRequestByUsername(username);
        if(status.equals("accepted")){
            Relationship rel1=new Relationship(rel.getId(),rel.getFirstUserName(),rel.getSecondUserName(),rel.getDtf());
            addRelationship(rel1);
        }
        rel.setStatus(status);
        contRQ.UpdateStatus(rel.getId(),rel);
        //apeleaza pentru update

    }

    public Relationship getRequestByUsername(String username){
        for(Relationship r: contRQ.getAll())
            if(r.getSecondUserName().equals(username))
                return r;
            return null;
    }
}
