package com.example.Repository.Db;

import com.example.Domain.Message;
import com.example.Domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MessagesUsersDbRepo extends DbRepoId<Long, Message>{
    /**
     * Basic constructor of a Db Repository
     *
     * @param url      to the database
     * @param username the user of the database
     * @param password the password of the user
     */
    public MessagesUsersDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public boolean save(Message entity){
        if(entity.getReceivers().size()==1){
            super.sql="insert into public.\"Messages_Users\" values ( ?, ?)";
        }
        else {
            super.sql="insert into public.\"Messages_Users\" values ";
            for (User el: entity.getReceivers())
                super.sql+="(?,?),";
            super.sql=super.sql.substring(0,super.sql.length()-1);//for removing the last ,
        }
        return super.save(entity);
    }

    @Override
    public boolean delete(Long id){
        super.sql="delete from public.\"Messages_Users\" where id_mess="+id.toString();
        return super.delete(id);
    }

    @Override
    public int getSize() {
        super.sql="select count(*) as \"size\" from public.\"Messages_Users\"";
        return super.getSize();
    }

    @Override
    public Long generateId() {
        return null;
    }


    @Override
    protected void setSaveStatement(PreparedStatement ps, Message entity) throws SQLException {
        if(entity.getReceivers().size()==1) setSaveOneStatement(ps,entity);
        else setSaveAllStatement(ps,entity);
    }

    @Override
    protected Message getGetStatement(ResultSet ps) throws SQLException {
        return null;
    }

    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        return null;
    }

    @Override
    protected List<Message> getAllStatement(ResultSet ps) throws SQLException {
        return null;
    }

    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long aLong) throws SQLException {

    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long aLong, Message entity) throws SQLException {

    }

    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {

    }
    public void setSaveOneStatement(PreparedStatement ps, Message mess) throws SQLException {
        ps.setLong(1,mess.getId());
        ps.setString(2,mess.getReceivers().get(0).getUsername());
    }

    public void setSaveAllStatement(PreparedStatement ps, Message mess) throws SQLException {
        int i=1;
        for(User el:mess.getReceivers()) {
            ps.setLong(i, mess.getId());
            i++;
            ps.setString(i, el.getUsername());
            i++;
        }
    }

}
