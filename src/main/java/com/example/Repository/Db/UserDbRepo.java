package com.example.Repository.Db;

import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.Pageble;
import com.example.Repository.UserRepo;
import com.example.Utils.Exceptions.EntityRepoException;
import com.example.Utils.Exceptions.UserRepoException;
import com.example.Utils.Generator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDbRepo extends DbRepoId<Long, User> implements UserRepo {

    public UserDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    /**
     * Saves a user to repository
     * @param entity the user to be saved
     * @return true if it has been saved with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     * @throws UserRepoException if there is already a user with the same id or username
     */
    @Override
    public boolean save(User entity) {
        if(entity.getId()==null) entity.setId(generateId());
            User user,user1;
            user=getByOther(entity.getUsername());
            user1=get(entity.getId());
            if(user!=null || user1!=null)throw new UserRepoException("There is an user with the same username");
            else{
                super.sql= "insert into public.\"Users\" values (?, ?, ?, ?)";
                return super.save(entity);
            }
    }

    /**
     * Retrieves the corespondent user with that id
     * @param id the id of the user to be found
     * @return the user that has that id or null if there is no user with that id
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public User get(Long id) {
        super.sql= "select * from public.\"Users\" where id_user="+id.toString();
        return super.get(id);
    }

    @Override
    public User getUserLogin(String username, String hash_pass) {
        System.out.println(hash_pass);
        super.sql= "select * from public.\"Users\" where username=\'"+username+"\' and hash_password=\'"+hash_pass+"\'";
        List<User> listUser=super.getAll();
        if(listUser==null || listUser.isEmpty())
            throw new UserRepoException("The username or the password is incorrect");
        listUser.get(0).setPassword("");
        return listUser.get(0);
    }
    /**
     * Deletes all the data from the repository
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    protected void deleteAll(){
        super.sql= "delete from public.\"Users\" where id_user != 0";
        super.deleteAll();
    }

    /**
     * Replaces a user with that id with a new user
     * @param id the id of the user to be replaced
     * @param entity the user to be replaced with
     * @return true if the user has been updated with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     * @throws UserRepoException if there is no user with that id
     */
    @Override
    public boolean update(Long id, User entity) {
        if(get(id)==null) throw new UserRepoException("There is no user with that id");
        super.sql= "update public.\"Users\" set id_user=?,username=?,id_pers=? where id_user=?";
        return super.update(id, entity);
    }

    /**
     * Deletes the user from the repository with that id
     * @param id the id of the user to be deleted
     * @return true if it was deleted with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     * @throws UserRepoException if there is no user with that id
     */
    @Override
    public boolean delete(Long id) {
        if(get(id)==null) throw new UserRepoException("There is no user with that id");
        super.sql="delete from public.\"Users\" where id_user=?";
        return super.delete(id);
    }

    /**
     * Gives a list with all the users stored in repository
     * @return a list of users
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public List<User> getAll() {
        super.sql="select * from public.\"Users\"";
        return super.getAll();
    }

    @Override
    public Page<User> getAll(Pageble pageble) {
        super.sql="select * from ( select * ,ROW_NUMBER() over (order by id_user ASC) as rowss from public.\"Users\")as Foo where rowss>=? and rowss<? ";
        return super.getAll(pageble);
        //return super.getAll();
    }


    /**
     * Gives a list with all the ids store din repository
     * @return a list of ids
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public List<Long> getAllIds() {
        super.sql= "select id_user from public.\"Users\"";
        return super.getAllIds();
    }

    /**
     * Checks if it is a user stored with some distinguishable components
     * @param other a list of string with distinguishable components
     * @return the user to be found or null if there is no object with that components
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public User getByOther(String... other) {
       return getByUserName(other[0]);
    }

    /**
     * Checks if it is a user stored with that username
     * @param username the username of the user to be found
     * @return the user to be found or null if there is no user with that username
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public User getByUserName(String username) {
        super.sql= "select * from public.\"Users\" where username=?";
        return super.getByOther(username);
    }



    /**
     * Generates an id for an entity
     */
    @Override
    public Long generateId() {
        return Generator.generateId(getAllIds());
    }

    /**
     * Gives the current Number of users stored in repository
     * @return the current Number of users
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public int getSize() {
        super.sql="select count(*) as \"size\" from public.\"Users\"";
        return super.getSize();
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for saving a user to repository
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param entity the user to be stored in repository
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setSaveStatement(PreparedStatement ps, User entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getUsername());
        ps.setLong(3, entity.getPers().getId());
        if(entity.getPassword()==null || entity.getPassword().isEmpty()){
            ps.setString(4, "default");
        }
        else ps.setString(4,entity.getPassword());
    }

    /**
     * This function converts the data from a result statement into a user,
     * in this case for retrieving a user from the repository
     * @param ps the result statements which contains the date from request
     * @return a user that is stored in repository or null if there is no user to be retrieved
     * @throws SQLException when there are problems with the result statements
     */
    @Override
    protected User getGetStatement(ResultSet ps)throws SQLException {
        User user=null;
        while (ps.next()) {
            Long id = ps.getLong("id_user");
            String username = ps.getString("username");
            Long id_pers = ps.getLong("id_pers");
            user = new User(id,username, new Persone(id_pers,"",""));
        }
        return user;
    }

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving a list with all the ids of the users from a table
     * @param ps the result statements which contains the date from request
     * @return a list with all the ids from a table
     * @throws SQLException when there are problems with the result statements
     */
    @Override
    protected List<Long> getAllIdStatement(ResultSet ps)throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_user");
            listId.add(id);
        }
        return listId;
    }

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving a list with all the users from a table
     * @param ps the result statements which contains the date from request
     * @return a list with all the users from a table
     * @throws SQLException when there are problems with the result statements
     */
    @Override
    protected List<User> getAllStatement(ResultSet ps) throws SQLException{
        List<User> users=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_user");
            String username = ps.getString("username");
            Long id_pers = ps.getLong("id_pers");
            User user = new User(id,username, new Persone(id_pers,"",""));
            users.add(user);
        }
        return users;
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for deleting a user from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the user to be deleted from the repository
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long id)throws SQLException {
        ps.setLong(1, id);
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for updating a user from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the user to be modified from the repository
     * @param entity the entity to be replaces with
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long id, User entity)throws SQLException{
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getUsername());
        ps.setLong(3, entity.getPers().getId());
        ps.setLong(4, id);
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for getting a user using other distinguishable components
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param other an array of strings with distinguishable components
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {
        ps.setString(1,other[0]);
    }

}
