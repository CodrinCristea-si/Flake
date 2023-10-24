package com.example.Repository.Db;

import com.example.Domain.Persone;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.Pageble;
import com.example.Repository.Repository;
import com.example.Utils.Exceptions.EntityRepoException;
import com.example.Utils.Exceptions.PersoneRepoException;
import com.example.Utils.Generator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersoneDbRepo extends DbRepoId<Long, Persone> implements Repository<Long,Persone> {
    public PersoneDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    /**
     * Saves a person to repository
     * @param entity the person to be saved
     * @return true if it has been saved with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public boolean save(Persone entity) {
        if(entity.getId()==null) entity.setId(generateId());
        super.sql= "insert into public.\"Persone\" values (?, ?, ?)";
        return super.save(entity);
    }

    /**
     * Retrieves the corespondent person with that id
     * @param id the id of the person to be found
     * @return the person that has that id or null if there is no person with that id
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public Persone get(Long id) {
        super.sql= "select * from public.\"Persone\" where id_pers="+id.toString();
        return super.get(id);
    }

    /**
     * Deletes all the data from the repository
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    protected void deleteAll(){
        super.sql= "delete from public.\"Persone\" where id_pers != 0";
        super.deleteAll();
    }

    /**
     * Replaces a person with that id with a new person
     * @param id the id of the person to be replaced
     * @param entity the person to be replaced with
     * @return true if the person has been updated with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     * @throws PersoneRepoException if there is no person with that id
     */
    @Override
    public boolean update(Long id, Persone entity) {
        if(get(id)==null) throw new PersoneRepoException("There is no persone with that id");
        super.sql= "update public.\"Persone\" set id_pers=?,first_name=?,last_name=? where id_pers=?";
        return super.update(id, entity);
    }

    /**
     * Deletes the person from the repository with that id
     * @param id the id of the person to be deleted
     * @return true if it was deleted with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     * @throws PersoneRepoException if there is no person with that id
     */
    @Override
    public boolean delete(Long id) {
        if(get(id)==null) throw new PersoneRepoException("There is no persone with that id");
        super.sql="delete from public.\"Persone\" where id_pers=?";
        return super.delete(id);
    }

    /**
     * Gives the current Number of users stored in repository
     * @return the current Number of persons
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public int getSize() {
        super.sql="select count(*) as \"size\" from public.\"Persone\"";
        return super.getSize();
    }

    /**
     * Gives a list with all the persons stored in repository
     * @return a list of persons
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public List<Persone> getAll() {
        super.sql="select * from public.\"Persone\"";
        return super.getAll();
    }

    @Override
    public Page<Persone> getAll(Pageble pageble) {
        super.sql="select * from ( select * ,ROW_NUMBER() over (order by id_pers ASC) as rowss from public.\"Persone\")as Foo where rowss>=? and rowss<? ";
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
        super.sql= "select id_pers from public.\"Persone\"";
        return super.getAllIds();
    }

    /**
     * Generates an id for an entity
     */
    @Override
    public Long generateId() {
        return Generator.generateId(getAllIds());
    }

    /**
     * Checks if it is a person stored with some distinguishable components
     * @param other a list of string with distinguishable components
     * @return the person to be found or null if there is no object with that components
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public Persone getByOther(String... other) {
        return getByNames(other[0],other[1]);
    }

    /**
     * Checks if it is a person stored with that names
     * @param first the first name of the person to be found
     * @param last the last name of the person to be found
     * @return the person to be found or null if there is no person with that names
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    private Persone getByNames(String first,String last){
        super.sql= "select * from public.\"Persone\" where first_name=? and last_name=?";
        return super.getByOther(first,last);
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for saving a person to repository
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param entity the person to be stored in repository
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setSaveStatement(PreparedStatement ps, Persone entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstName());
        ps.setString(3, entity.getLastName());
    }

    /**
     * This function converts the data from a result statement into a person,
     * in this case for retrieving a person from the repository
     * @param ps the result statements which contains the date from request
     * @return a person that is stored in repository or null if there is no person to be retrieved
     * @throws SQLException when there are problems with the result statements
     */
    @Override
    protected Persone getGetStatement(ResultSet ps) throws SQLException {
        Persone pers=null;
        while (ps.next()) {
            Long id = ps.getLong("id_pers");
            String fN = ps.getString("first_name");
            String lN = ps.getString("last_name");
            pers = new Persone(id,fN,lN);    }
        return pers;
    }

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving a list with all the ids of the persons from a table
     * @param ps the result statements which contains the date from request
     * @return a list with all the ids from a table
     * @throws SQLException when there are problems with the result statements
     */
    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_pers");
            listId.add(id);
        }
        return listId;
    }

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving a list with all the persons from a table
     * @param ps the result statements which contains the date from request
     * @return a list with all the persons from a table
     * @throws SQLException when there are problems with the result statements
     */
    @Override
    protected List<Persone> getAllStatement(ResultSet ps) throws SQLException {
        List<Persone> people=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_pers");
            String fN = ps.getString("first_name");
            String lN = ps.getString("last_name");
            Persone pers = new Persone(id,fN,lN);
            people.add(pers);
        }
        return people;
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for deleting a person from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the person to be deleted from the repository
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long id) throws SQLException {
        ps.setLong(1, id);
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for updating a person from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the person to be modified from the repository
     * @param entity the entity to be replaces with
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long id, Persone entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstName());
        ps.setString(3, entity.getLastName());
        ps.setLong(4, id);
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for getting a person using other distinguishable components
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param other an array of strings with distinguishable components
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {
        ps.setString(1,other[0]);
        ps.setString(2,other[1]);
    }


}
