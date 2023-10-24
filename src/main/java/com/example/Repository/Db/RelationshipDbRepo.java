package com.example.Repository.Db;

import com.example.Domain.Relationship;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.PageType;
import com.example.Repository.PagingRepo.Pageble;
import com.example.Repository.RelationshipRepo;
import com.example.Utils.Exceptions.EntityRepoException;
import com.example.Utils.Exceptions.RelationshipRepoException;
import com.example.Utils.Generator;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RelationshipDbRepo<Messages> extends DbRepoId<Long, Relationship> implements RelationshipRepo {
    public RelationshipDbRepo(String url, String username, String password) {
        super(url, username, password);
    }
    //int size;
    public RelationshipDbRepo(String url, String username, String password,int size) {
        super(url, username, password);
        //this.size=size;
        super.page=new Page(new Pageble(0,size), new ArrayList().stream());
    }

    /**
     * Saves a relationship to repository
     * @param entity the relationship to be saved
     * @return true if it has been saved with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     * @throws RelationshipRepoException if there is already a relationship with the same id or usernames
     */
    @Override
    public boolean save(Relationship entity) {
        if(entity.getId()==null) entity.setId(generateId());
        try{
            Relationship rel;
            rel=getByOther(entity.getFirstUserName(),entity.getSecondUserName());
            if(rel!=null)throw new RelationshipRepoException("There is a relationship with the same usernames");
            else{
                if(super.sql==null)super.sql= "insert into public.\"Relationship\" values (?, ?, ?,?)";
                return super.save(entity);
            }
        }
        catch(EntityRepoException e){
            if(super.sql==null)super.sql= "insert into public.\"Relationship\" values (?, ?, ?,?)";

        }

        return super.save(entity);
    }

    /**
     * Retrieves the corespondent relationship with that id
     * @param id the id of the relationship to be found
     * @return the relationship that has that id or null if there is no relationship with that id
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public Relationship get(Long id) {
        if(super.sql==null)sql= "select * from public.\"Relationship\" where id_rel="+id.toString();
        return super.get(id);
    }

    /**
     * Replaces a relationship with that id with a new relationship
     * @param id the id of the relationship to be replaced
     * @param entity the relationship to be replaced with
     * @return true if the relationship has been updated with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     * @throws RelationshipRepoException if there is no relationship with that id
     */
    @Override
    public boolean update(Long id, Relationship entity) {
        if(get(id)==null) throw new RelationshipRepoException("There is no relationship with that id");
        if(super.sql==null)sql= "update public.\"Relationship\" set id_rel=?,first_username=?,second_username=?, the_data=? where id_rel=?";

        return super.update(id, entity);

    }

    /**
     * Deletes the relationship from the repository with that id
     * @param id the id of the relationship to be deleted
     * @return true if it was deleted with success, false otherwise
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     * @throws RelationshipRepoException if there is no relationship with that id
     */
    @Override
    public boolean delete(Long id) {
        if(get(id)==null) throw new RelationshipRepoException("There is no relationship with that id");
        if(super.sql==null)sql="delete from public.\"Relationship\" where id_rel=?";
        return super.delete(id);
    }

    /**
     * Deletes all the data from the repository
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    protected void deleteAll() {

        if(super.sql==null)sql= "delete from public.\"Relationship\" where id_rel != 0";
        super.deleteAll();
    }
    /**
     * Gives the current Number of relationships stored in repository
     * @return the current Number of relationships
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public int getSize() {
        if(super.sql==null)sql="select count(*) as \"size\" from public.\"Relationship\"";

        return super.getSize();
    }

    /**
     * Gives a list with all the relationships stored in repository
     * @return a list of relationships
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on processing the data
     */
    @Override
    public List<Relationship> getAll() {

        if(super.sql==null)sql="select * from public.\"Relationship\"";

        return super.getAll();
    }

    @Override
    public Page<Relationship> getAll(Pageble pageble) {
        if(super.sql==null)sql="select * from ( select * ,ROW_NUMBER() over (order by id_rel ASC) as rowss from public.\"Relationship\")as Foo where rowss>=? and rowss<? ";
        return super.getAll(pageble);
        //return super.getAll();
    }

    @Override
    public Page<Relationship> getUsersFriends(String username,Pageble pageble) {
        if(super.sql==null)sql="select * from ( select * ,ROW_NUMBER() over (order by id_rel ASC) as rowss from public.\"Relationship\")as Foo where rowss>=? and rowss<? and (first_username="+ username +" or second_username="+username+")";
        return super.getAll(pageble);
        //return super.getAll();
    }

    public Page<Relationship> getPageFriends(String username, PageType type) {

        if(super.sql==null)sql="select * from ( select * ,ROW_NUMBER() over (order by id_rel ASC) as rowss from (select * from public.\"Relationship\" where (first_username=\'"+username+"\' or second_username=\'"+username+"\' ) ) as Fooo ) as Foo where rowss>=? and rowss<?";

        switch(type){
            case CURRENT -> {return super.getCurrentPage();}
            case NEXT -> {return super.getNextPage();}
            case PREVIOUS -> {return super.getPreviousPage();}
        };
        return null;
        //return super.getAll();
    }

    public Page<Relationship> getFirstPageFriends(String username, PageType type) {
        super.page=super.page=new Page(new Pageble(0,super.page.getCurrentPage().getPageSize()), new ArrayList().stream());
        return getPageFriends(username,type);
    }

    /**
     * Gives a list with all the ids store din repository
     * @return a list of ids
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public List<Long> getAllIds() {
        if(super.sql==null)super.sql= "select id_rel from public.\"Relationship\"";
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
     * Checks if it is a relationship stored with some distinguishable components
     * @param other a list of string with distinguishable components
     * @return the relationship to be found or null if there is no object with that components
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public Relationship getByOther(String... other) {

        return getByUserNames(other[0],other[1]);
    }

    /**
     * Checks if it is a relationship stored with that username
     * @param username1 the first username of the relationship to be found
     * @param username2 the second username of the relationship to be found
     * @return the relationship to be found or null if there is no relationship with that username
     * @throws EntityRepoException if the connection to the repository fails or there ar other
     * problems on retrieving the data
     */
    @Override
    public Relationship getByUserNames(String username1, String username2) {

        if(super.sql==null)sql= "select * from public.\"Relationship\" where first_username=? and second_username= ?";

        Relationship rel=super.getByOther(username1,username2);
        if(super.sql==null)sql= "select * from public.\"Relationship\" where first_username=? and second_username= ?";
        if (rel==null)
            return super.getByOther(username2,username1);
        else sql=null;
        return rel;
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for saving a relationship to repository
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param entity the relationship to be stored in repository
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setSaveStatement(PreparedStatement ps, Relationship entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstUserName());
        ps.setString(3, entity.getSecondUserName());
        if(entity.getDtf()!=null)
        ps.setDate(4, Date.valueOf(entity.getDtf()));
        else
            ps.setNull(4, Types.DATE);

    }

    /**
     * This function converts the data from a result statement into a relationship,
     * in this case for retrieving a relationship from the repository
     * @param ps the result statements which contains the date from request
     * @return a relationship that is stored in repository or null if there is no relationship to be retrieved
     * @throws SQLException when there are problems with the result statements
     */
    @Override
    protected Relationship getGetStatement(ResultSet ps) throws SQLException {
        Relationship rel=null;
        while (ps.next()) {
            Long id = ps.getLong("id_rel");
            String fU = ps.getString("first_username");
            String sU = ps.getString("second_username");

            LocalDate the_data;
            try {
                the_data= ps.getDate("the_data").toLocalDate();
            }catch(PSQLException e){
                the_data=null;
            }
            rel =new Relationship(id,fU,sU,the_data);

        }
        return rel;
    }

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving a list with all the ids of the relationships from a table
     * @param ps the result statements which contains the date from request
     * @return a list with all the ids from a table
     * @throws SQLException when there are problems with the result statements
     */
    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_rel");
            listId.add(id);
        }
        return listId;
    }

    /**
     * This function converts the data from a result statement into an entity,
     * in this case for retrieving a list with all the relationships from a table
     * @param ps the result statements which contains the date from request
     * @return a list with all the relationships from a table
     * @throws SQLException when there are problems with the result statements
     */
    @Override
    protected List<Relationship> getAllStatement(ResultSet ps) throws SQLException {
        List<Relationship> relations=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_rel");
            String fU = ps.getString("first_username");
            String sU = ps.getString("second_username");
            LocalDate lu;
            try {
               lu=ps.getDate("the_data").toLocalDate();
            }catch(PSQLException e){
               lu=null;
            }
            catch(NullPointerException e){
                lu=null;
            }
            Relationship rel =new Relationship(id,fU,sU,lu);

            relations.add(rel);
        }
        return relations;
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for deleting a relationship from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the relationship to be deleted from the repository
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long id) throws SQLException {
        ps.setLong(1, id);
    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for updating a relationship from repository using his id
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param id the id of the relationship to be modified from the repository
     * @param entity the entity to be replaces with
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long id, Relationship entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFirstUserName());
        ps.setString(3, entity.getSecondUserName());

        if(entity.getDtf()!=null)
            ps.setDate(4, Date.valueOf(entity.getDtf()));
        else
            ps.setNull(4, Types.DATE);
        ps.setLong(5, id);

    }

    /**
     * This function fills the request( prepared statement) with actual data for the sql
     * command for getting a relationship using other distinguishable components
     * @param ps a PreparedStatement which will be used to fill the sql request for the db
     * @param other an array of strings with distinguishable components
     * @throws SQLException when there are problems with the prepared statements
     */
    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {
        ps.setString(1,other[0]);
        ps.setString(2,other[1]);
    }

    public void deleteRelationshipByUsername(String username){
        super.sql= "delete from public.\"Relationship\" where first_username =? or second_username=? ";

    }


}
