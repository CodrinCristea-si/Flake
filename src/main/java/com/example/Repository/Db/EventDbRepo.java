package com.example.Repository.Db;

import com.example.Domain.Event;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.PageType;
import com.example.Repository.PagingRepo.Pageble;
import com.example.Repository.Repository;
import com.example.Utils.Generator;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventDbRepo extends DbRepoId<Long,Event> implements Repository<Long, Event> {
    /**
     * Basic constructor of a Db Repository
     *
     * @param url      to the database
     * @param username the user of the database
     * @param password the password of the user
     */
    public EventDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    public EventDbRepo(String url, String username, String password,int size) {
        super(url, username, password);
        super.page=new Page(new Pageble(0,size), new ArrayList().stream());
    }

    @Override
    public boolean save(Event entity) {
        if(entity.getId()==null) entity.setId(generateId());
        if(super.sql==null)super.sql= "insert into public.\"Events\" values (?, ?, ?,?)";
        return super.save(entity);
    }

    @Override
    public Event get(Long id) {
        if(super.sql==null)super.sql= "select * from public.\"Events\" where id="+id.toString();
        return super.get(id);
    }

    @Override
    public boolean update(Long id, Event entity) {
        //if(get(id)==null) throw new RelationshipRepoException("There is no event with that id");

        if(super.sql==null)super.sql= "update public.\"Events\" set id=?,name=?,the_date=?, description=? where id=?";

        return super.update(id, entity);

    }

    @Override
    public boolean delete(Long id) {
        //if(get(id)==null) throw new RelationshipRepoException("There is no event with that id");
        if(super.sql==null)super.sql="delete from public.\"Events\" where id=?";
        return super.delete(id);
    }

    @Override
    protected void deleteAll() {
        if(super.sql==null)super.sql= "delete from public.\"Events\" where id != 0";
        super.deleteAll();
    }

    @Override
    public int getSize() {
        if(super.sql==null)super.sql="select count(*) as \"size\" from public.\"Events\"";
        return super.getSize();
    }

    @Override
    public List<Event> getAll() {
        if(super.sql==null)super.sql="select * from public.\"Events\"";
        return super.getAll();
    }

    @Override
    public Page<Event> getAll(Pageble pageble) {

        if(super.sql==null)super.sql="select * from ( select * ,ROW_NUMBER() over (order by id_ue ASC) as rowss from public.\"Events\" E inner join public.\"UserEvents\" UE on E.id=UE.id_e)as Foo where rowss>=? and rowss<? ";
       return super.getAll(pageble);
        //return super.getAll();
    }


    public Page<Event> getPageEvents(PageType type) {

        if(super.sql==null)super.sql="select * from ( select * ,ROW_NUMBER() over (order by id ASC) as rowss from public.\"Events\" ) as Foo where rowss>=? and rowss<? ";

        switch(type){
            case CURRENT -> {return super.getCurrentPage();}
            case NEXT -> {return super.getNextPage();}
            case PREVIOUS -> {return super.getPreviousPage();}
        };
        return null;
    }


    public Page<Event> getPageEventsSUBSCRIBE(PageType type,Long id_u) {
        if(super.sql==null)sql="select * from ( select * ,ROW_NUMBER() over (order by id ASC) as rowss from (select * from public.\"Events\" E inner join public.\"UserEvents\" UE on E.id=UE.id_e where  UE.id_u=\'"+id_u.toString()+"\')as Foo1 )as Foo where rowss>=? and rowss<? ";
        switch(type){
            case CURRENT -> {return super.getCurrentPage();}
            case NEXT -> {return super.getNextPage();}
            case PREVIOUS -> {return super.getPreviousPage();}
        };
        return null;
    }



    public Page<Event> getFirstPageEvents(PageType type) {
        super.page=super.page=new Page(new Pageble(0,super.page.getCurrentPage().getPageSize()), new ArrayList().stream());
        return getPageEvents(type);
    }

    public Page<Event> getFirstPageEventsSUBSCRIBE(PageType type,Long id) {
        super.page=super.page=new Page(new Pageble(0,super.page.getCurrentPage().getPageSize()), new ArrayList().stream());
        return getPageEventsSUBSCRIBE(type,id);
    }

    public List<Event> getUserEvents(Long id){
        if(super.sql==null)super.sql="select * from public.\"Events\" E inner join public.\"UserEvents\" UE on E.id=UE.id_e where UE.id_u="+id.toString();
        return super.getAll();
    }



    @Override
    public List<Long> getAllIds() {
        if(super.sql==null)super.sql= "select id from public.\"Events\"";
        return super.getAllIds();
    }

    @Override
    public Long generateId() {
        return  Generator.generateId(getAllIds());
    }

    @Override
    public Event getByOther(String... other) {

        return getByNameEvent(other[0]);
    }

    public Event getByNameEvent(String name)
    {
        if(super.sql==null)super.sql= "select * from public.\"Events\" where name=?";
        Event ev=super.getByOther(name);
        return ev;//==null ? super.getByOther(name) : ev;

    }

    @Override
    protected void setSaveStatement(PreparedStatement ps, Event entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getName());
        ps.setString(3, entity.getDescription());
        if(entity.getDtf()!=null)
            ps.setDate(4, Date.valueOf(entity.getDtf()));
        else
            ps.setNull(4, Types.DATE);
    }

    @Override
    protected Event getGetStatement(ResultSet ps) throws SQLException {
        Event event=null;
        while (ps.next()) {
            Long id = ps.getLong("id");
            String name_event= ps.getString("name");
            String desc_event= ps.getString("description");
            LocalDate the_data;
            try {
                the_data= ps.getDate("the_date").toLocalDate();
            }catch(PSQLException e){
                the_data=null;
            }
            event =new Event(id,name_event,desc_event,the_data);

        }
        return event;
    }

    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id");
            listId.add(id);
        }
        return listId;
    }

    @Override
    protected List<Event> getAllStatement(ResultSet ps) throws SQLException {
        List<Event> events=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id");
            String name = ps.getString("name");
            String des = ps.getString("description");
            LocalDate lu;
            try {
                lu=ps.getDate("the_date").toLocalDate();
            }catch(PSQLException e){
                lu=null;
            }
            catch(NullPointerException e){
                lu=null;
            }
            Event event =new Event(id,name,des,lu);

            events.add(event);
        }
        return events;
    }

    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long aLong) throws SQLException {
        ps.setLong(1, aLong);
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long aLong, Event entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getName());
        ps.setString(3, entity.getDescription());

        if(entity.getDtf()!=null)
            ps.setDate(4, Date.valueOf(entity.getDtf()));
        else
            ps.setNull(4, Types.DATE);
        ps.setLong(5, aLong);
    }

    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {
        ps.setString(1,other[0]);
    }

}
