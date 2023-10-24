package com.example.Domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User extends Entity<Long>{
    String username;
    String password;

    public User(String username, String password, Persone pers) {
        this.username = username;
        this.password = password;
        this.pers = pers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    Persone pers;
    List<String>friendsList;

    public User(String username, Persone pers) {
        initialize(username,pers);
    }

    public User(Long id,String username, Persone pers) {
        initialize(username,pers);
        super.setId(id);
    }

    public User(Long id,String pass,String username, Persone pers) {
        this.password=pass;
        initialize(username,pers);
        super.setId(id);
    }
    /**
     * Initializez the fields of the class
     * @param username
     * @param pers
     */
    private void initialize(String username, Persone pers){
        this.username = username;
        this.pers = pers;
        friendsList=new ArrayList<String>();
    }

    /**
     * Gives the username of the user
     * @return a string that represents the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Updates teh username of an user
     * @param username the string to be replased with
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gives the persone of an user
     * @return an object of type Persone
     */
    public Persone getPers() {
        return pers;
    }

    /**
     * Modifies the Persone of a user
     * @param pers the persone to  be replaced with
     */
    public void setPers(Persone pers) {
        this.pers = pers;
    }

    /**
     * Adds a friend to the friend list
     * @param userName the username of the user to be added
     */
    public void addFriend(String userName){
        friendsList.add(userName);
    }

    /**
     * Removes a friend from the friends list
     * @param userName the username of the user to be removed
     */
    public void removeFriend(String userName){
        friendsList.remove(userName);
    }

    /**
     * Gives the friends list
     * @return a list of strings with the usernames of yhe friends
     */
    public List<String> getFriendsList(){
        return friendsList;
    }

    /**
     * Gives the number of friends
     * @return the size of the friends list
     */
    public int getNumberOfFriends(){
        return friendsList.size();
    }

    /**
     * Return the user in an elegant way
     * @return a string that contains the data of that user
     */
    @Override
    public String toString() {
        if(getId()!=null)
            return "User{" +
                "id" + super.getId().toString()+
                "username='" + username + '\'' +
                ", pers=" + pers +
                '}';
        return "User{" +
                "username='" + username + '\'' +
                ", pers=" + pers +
                '}';
    }

    /**
     * Checks The Equality of 2 objects
     * @param o the object to be compared with
     * @return true, the the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return (getId()!=null && getId()==((User) o).getId()) || Objects.equals(username, user.username);
    }

    /**
     * Gives the hash code of that object
     * @return hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(username,getId());
    }
}
