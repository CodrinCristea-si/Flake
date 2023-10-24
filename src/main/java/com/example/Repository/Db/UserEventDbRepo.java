package com.example.Repository.Db;

import com.example.Domain.UserEvent;
import com.example.Repository.Repository;
import com.example.Utils.Generator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserEventDbRepo extends DbRepoId<Long, UserEvent> implements Repository<Long,UserEvent> {


    /**
     * Basic constructor of a Db Repository
     *
     * @param url      to the database
     * @param username the user of the database
     * @param password the password of the user
     */
    public UserEventDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public boolean save(UserEvent entity) {
        if(entity.getId()==null) entity.setId(generateId());
        super.sql= "insert into public.\"UserEvents\" values (?, ?, ?)";
        return super.save(entity);
    }

    @Override
    public UserEvent get(Long aLong) {
        super.sql= "select * from public.\"UserEvents\" where id_ue="+aLong.toString();
        return super.get(aLong);
    }

    @Override
    public boolean update(Long aLong, UserEvent entity) {
        super.sql= "update public.\"UserEvents\" set id_ue=?,id_u=?,id_e=?, where id_ue=?";

        return super.update(aLong, entity);
    }

    @Override
    public boolean delete(Long aLong) {
        super.sql="delete from public.\"UserEvents\" where id_ue=?";

        return super.delete(aLong);
    }

    @Override
    protected void deleteAll() {
        super.sql= "delete from public.\"UserEvents\" where id_ue != 0";
        super.deleteAll();
    }

    @Override
    public int getSize() {
        super.sql="select count(*) as \"size\" from public.\"UserEvents\"";
        return super.getSize();
    }

    @Override
    public List<UserEvent> getAll() {
        super.sql="select * from public.\"UserEvents\"";
        return super.getAll();
    }

    @Override
    public List<Long> getAllIds() {
        super.sql= "select id_ue from public.\"UserEvents\"";
        return super.getAllIds();
    }

    @Override
    public Long generateId() {
        return  Generator.generateId(getAllIds());

    }

    @Override
    protected void setSaveStatement(PreparedStatement ps, UserEvent entity) throws SQLException {

        ps.setLong(1, entity.getId());
        ps.setLong(2, entity.getId_user());
        ps.setLong(3, entity.getId_event());

    }

    @Override
    protected UserEvent getGetStatement(ResultSet ps) throws SQLException {
        UserEvent Uevent=null;
        while (ps.next()) {
            Long id = ps.getLong("id_ue");
            Long id_u= ps.getLong("id_u");
            Long id_e = ps.getLong("id_e");

            Uevent =new UserEvent(id,id_u,id_e);

        }
        return Uevent;
    }

    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_ue");
            listId.add(id);
        }
        return listId;
    }

    @Override
    public UserEvent getByOther(String... other) {
        return getByUserAnEvent(Long.parseLong(other[0]),Long.parseLong(other[1]));
    }

    public UserEvent getByUserAnEvent(Long id_u,Long id_e)
    {
        super.sql= "select * from public.\"UserEvents\" where id_u=? and id_e=?";
        UserEvent ev=super.getByOther(id_e.toString(),id_e.toString());


        return ev==null ? super.getByOther(id_u.toString(),id_e.toString()) : ev;

    }


    @Override
    protected List<UserEvent> getAllStatement(ResultSet ps) throws SQLException {
        List<UserEvent> Uevents=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_ue");
            Long id_u = ps.getLong("id_u");
            Long id_e = ps.getLong("id_e");


            UserEvent Uevent =new UserEvent(id,id_u,id_e);

            Uevents.add(Uevent);
        }
        return Uevents;
    }

    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long aLong) throws SQLException {
        ps.setLong(1, aLong);
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long aLong, UserEvent entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setLong(2, entity.getId_user());
        ps.setLong(3, entity.getId_event());
    }

    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {
        ps.setString(1,other[0].toString());
        ps.setString(2,other[0].toString());
    }


}
