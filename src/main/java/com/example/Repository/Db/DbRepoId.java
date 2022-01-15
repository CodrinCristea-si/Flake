package com.example.Repository.Db;

import com.example.Domain.Entity;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.Pageble;
import com.example.Repository.Repository;
import com.example.Utils.Exceptions.EntityRepoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class DbRepoId<Id,E extends Entity<Id>>implements Repository<Id,E> {

    protected String url;
    protected String username;
    protected String password;
    protected String sql;
    protected Page page;
    Connection connection;

    /**
     * Basic constructor of a Db Repository
     * @param url to the database
     * @param username the user of the database
     * @param password the password of the user
     */
    public DbRepoId(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
        page=new Page(new Pageble(0,10), new ArrayList().stream());
        openConnection();
    }

    public void openConnection(){
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * Saves an entity to repository
     * @param entity the object to be saved
     * @return true if it has been saved with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public boolean save(E entity) {

        try {
             if(connection.isClosed()) openConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             setSaveStatement(ps,entity);
             ps.executeUpdate();
            sql=null;
             return true;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * Retrieves the corespondent object with that id
     * @param id the id of the object to be found
     * @return the object that has that id or null if there is no object with that id
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public E get(Id id) {
        try {
             if(connection.isClosed()) openConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery();
            sql=null;
             return getGetStatement(resultSet);
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * Replaces an Entity with that id with a new Entity
     * @param id the id of the object to be replaced
     * @param entity the entity to be replaced with
     * @return true if the entity has been updated with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public boolean update(Id id, E entity) {
        try {
            if(connection.isClosed()) openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            setUpdateStatement(ps,id,entity);
            ps.executeUpdate();
            sql=null;
            return true;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * Deletes the object from the repository with that id
     * @param id the id of the object to be deleted
     * @return true if it was deleted with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public boolean delete(Id id) {
        try{
            if(connection.isClosed()) openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            setDeleteStatement(ps,id);
            ps.executeUpdate();
            sql=null;
            return true;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * Deletes all the data from the repository
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    protected void deleteAll(){
        try{
            if(connection.isClosed()) openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
            sql=null;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * Gives the current Number of entities stored in repository
     * @return the current Number of entities
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public int getSize() {
        int size;
        try{
            if(connection.isClosed()) openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();
            size=getSizeStatement(resultSet);
            sql=null;
            return size;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * Gives a list with all the entities stored in repository
     * @return a list of entities
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public List<E> getAll() {
        List<E> list ;
        try{
             if(connection.isClosed()) openConnection();
             PreparedStatement ps = connection.prepareStatement(sql);

             ResultSet resultSet = ps.executeQuery();
             list=getAllStatement(resultSet);
             sql=null;
             return list;
        } catch (SQLException e) {

            throw new EntityRepoException(e.getMessage());

        }
    }
    @Override
    public Page<E> getAll(Pageble pageble){
        List<E> list ;
        try{
            if(connection.isClosed()) openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            setGetAllPageStatement(ps, pageble);

            ResultSet resultSet = ps.executeQuery();
            list=getAllStatement(resultSet);

            sql=null;
            return new Page<E>(pageble,list.stream());
        } catch (SQLException e) {

            throw new EntityRepoException(e.getMessage());
        }
    }
    @Override
    public Page<E> getCurrentPage(){
        page=new Page((Pageble) page.getCurrentPage(),new ArrayList().stream());
       if(page.getPageContent()!=null && page.getPageContent().toList().isEmpty())
           page=this.getAll((Pageble) page.getCurrentPage());
       return page;
    }

    @Override
    public Page<E> getNextPage(){
        page=new Page((Pageble) page.getCurrentPage(),new ArrayList().stream());
        page=new Page((Pageble) page.getNextPage(), new ArrayList().stream());
        page=this.getAll((Pageble) page.getCurrentPage());
        return page;
    }

    @Override
    public Page<E> getPreviousPage(){
        page=new Page((Pageble) page.getPreviousPage(), new ArrayList().stream());
        page=this.getAll((Pageble) page.getCurrentPage());
        return page;
    }


    /**
     * Gives a list with all the ids store din repository
     * @return a list of ids
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public List<Id> getAllIds() {
        List<Id> list ;
        try{
             if(connection.isClosed()) openConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery();
             list=getAllIdStatement(resultSet);
            sql=null;
             return list;
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * Deletes all the data from the database tables
     * This function should be used only in tests
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    public void restoreToDefault(){
        deleteAll();
    }

    /**
     * Generates an id for an entity
     */
    @Override
    public abstract Id generateId();


    /**
     * Checks if it is an object stored with some distinguishable components
     * @param other a list of string with distinguishable components
     * @return the object to be found or null if there is no object with that components
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public E getByOther(String... other){
        try{
            if(connection.isClosed()) openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            setGetOtherStatement(ps,other);

            ResultSet resultSet = ps.executeQuery();
            sql=null;
            return getGetOtherStatement(resultSet);
        } catch (SQLException e) {
            throw new EntityRepoException(e.getMessage());
        }
    }

    /**
     * This function converts the data from a result statement into an integer that
     * represents the total number of entities stored in repository
     * @param ps the result statements which contains the date from request
     * @return an integer which represents the total number of entities stored in repository
     * @throws SQLException when there are problems with the result statements
     */
    protected int getSizeStatement(ResultSet ps) throws SQLException{
        int size=0;
        if (ps.next()) {
            size = ps.getInt("size");
        }
        return size;
    }

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving an entity from the repository with other distinguishable
     * components
     * @param ps the result statements which contains the date from request
     * @return an entity that is stored in repository
     * @throws SQLException when there are problems with the result statements
     */
    protected E getGetOtherStatement(ResultSet ps) throws SQLException{
        return getGetStatement(ps);
    }

    protected void setGetAllPageStatement(PreparedStatement ps, Pageble pageble) throws SQLException {
        ps.setInt(1,pageble.getPageNumber()*pageble.getPageSize() +1);
        ps.setInt(2,(pageble.getPageNumber()+1)*pageble.getPageSize() +1);
    }
    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for saving an entity to repository
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param entity the entity to be stored in repository
     * @throws SQLException when there are problems with the prepared statements
     */
    protected abstract void setSaveStatement(PreparedStatement ps,E entity) throws SQLException;

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving an entity from the repository
     * @param ps the result statements which contains the date from request
     * @return an entity that is stored in repository
     * @throws SQLException when there are problems with the result statements
     */
    protected abstract E getGetStatement(ResultSet ps) throws SQLException;

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving a list with all the ids from a table
     * @param ps the result statements which contains the date from request
     * @return a list with all the ids from a table
     * @throws SQLException when there are problems with the result statements
     */
    protected abstract List<Id> getAllIdStatement(ResultSet ps) throws SQLException;

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving a list with all the entities from a table
     * @param ps the result statements which contains the date from request
     * @return a list with all the entities from a table
     * @throws SQLException when there are problems with the result statements
     */
    protected abstract List<E> getAllStatement(ResultSet ps) throws SQLException;

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for deleting an entity from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the entity to be deleted from the repository
     * @throws SQLException when there are problems with the prepared statements
     */
    protected abstract void setDeleteStatement(PreparedStatement ps,Id id) throws SQLException;

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for updating an entity from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the entity to be modified from the repository
     * @param entity the entity to be replaces with
     * @throws SQLException when there are problems with the prepared statements
     */
    protected abstract void setUpdateStatement(PreparedStatement ps,Id id,E entity) throws SQLException;

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for getting an entity using other distinguishable components
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param other an array of strings with distinguishable components
     * @throws SQLException when there are problems with the prepared statements
     */
    protected abstract void setGetOtherStatement(PreparedStatement ps, String... other)throws SQLException;

    @Override
    protected void finalize() throws Throwable
    {
        try { connection.close(); }
        catch (SQLException e) {

        }

        super.finalize();
    }
}

