package com.example.Repository.Db;

import com.example.Domain.Message;
import com.example.Domain.Persone;
import com.example.Domain.User;
import com.example.Repository.MessageRepo;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.Pageble;
import com.example.Utils.Exceptions.EntityRepoException;
import com.example.Utils.Exceptions.MessageRepoException;
import com.example.Utils.Generator;
import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MessageDbRepo extends DbRepoId<Long,Message> implements MessageRepo {
    /**
     * Basic constructor of a Db Repository
     * @param url      to the database
     * @param username the user of the database
     * @param password the password of the user
     */
    public MessageDbRepo(String url, String username, String password) {
        super(url, username, password);
    }

    @Override
    public boolean save(Message entity) {
        if(entity.getId()==null)entity.setId(generateId());
        super.sql= "insert into public.\"Messages\" values (?, ?, ?, ?, ?);";
        super.save(entity);
        MessagesUsersDbRepo repoAux=new MessagesUsersDbRepo(url,username,password);
        repoAux.save(entity);
        repoAux.closeConnection();
        return true;
    }


    @Override
    public Message get(Long id) {
        super.sql= "select * from public.\"Messages\" M inner join public.\"Messages_Users\" MU on M.id_mess=MU.id_mess where MU.id_mess="+id.toString() ;
        return super.get(id);
    }

    @Override
    public boolean update(Long id, Message entity) {
        if(get(id)==null) throw new MessageRepoException("There is no message with that id");
        if(entity.getId()!=id){
            return super.delete(id) && super.save(entity);
        }
        super.sql= "update public.\"Messages\" set sender=?,date_time=?,description=?,message_reply=? where id_mess=?";
        super.update(id, entity);
        MessagesUsersDbRepo repoAux=new MessagesUsersDbRepo(url,username,password);
        repoAux.delete(id);
        repoAux.save(entity);
        repoAux.closeConnection();
        return true;
    }

    @Override
    public boolean delete(Long id) {
        if(get(id)==null) throw new MessageRepoException("There is no message with that id");
        super.sql="delete from public.\"Messages_Users\" where id_mess=?;update public.\"Messages\" set message_reply=NULL where id_mess=?;delete from public.\"Messages\" where id_mess=?;";
        return super.delete(id);
    }

    @Override
    protected void deleteAll() {
        super.sql= "delete from public.\"Messages_Users\";delete from public.\"Messages\" where message_reply is not null;delete from public.\"Messages\";";
        super.deleteAll();
    }

    @Override
    public int getSize() {
        MessagesUsersDbRepo repoAux=new MessagesUsersDbRepo(url,username,password);
        int Nr=repoAux.getSize();
        repoAux.closeConnection();
        return Nr;
    }

    @Override
    public List<Message> getAll() {
        super.sql="select * from public.\"Messages\" M inner join public.\"Messages_Users\" MU on M.id_mess=MU.id_mess";
        return super.getAll();
    }

    @Override
    public Page<Message> getAll(Pageble pageble) {
        super.sql="select * from ( select * ,ROW_NUMBER() over (order by id_mess ASC) as rowss from public.\"Messages\" M inner join public.\"Messages_Users\" MU on M.id_mess=MU.id_mess\")as Foo where rowss>=? and rowss<? ";
        return super.getAll(pageble);
        //return super.getAll();
    }
    /*
    @Override
    public Page<Message> getConversation(String username1, String username2,Pageble pageble) {
        sql="select * from ( select * ,ROW_NUMBER() over (order by id_mess ASC) as rowss from public.\"Messages\")as Foo where rowss>=? and rowss<? ";
        return super.getAll(pageble);
        //return super.getAll();
    }
     */

    @Override
    public List<Long> getAllIds() {
        super.sql= "select id_mess from public.\"Messages\"";
        return super.getAllIds();
    }

    @Override
    public Message getByOther(String... other) {
       List<Message> mess=getBySR(other[0],other[1]);
       if(mess.isEmpty()) mess=getBySR(other[1],other[0]);
       return mess.isEmpty() ? null : mess.get(mess.size()-1);
    }

    @Override
    public Long generateId() {
        return Generator.generateId(getAllIds());
    }

    @Override
    protected void setSaveStatement(PreparedStatement ps, Message entity) throws SQLException {
        ps.setLong(1, entity.getId());
        ps.setString(2, entity.getFrom().getUsername());
        ps.setObject(3,entity.getDate());
        ps.setString(4, entity.getMessage());
        if (entity.getReply()!=null){
            ps.setLong(5,entity.getReply().getId());
        }
        else ps.setNull(5, Types.BIGINT);
    }

    @Override
    protected Message getGetStatement(ResultSet ps) throws SQLException {
        Message mess=null;
        List<User> listUsers=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_mess");
            String from = ps.getString("sender");
            String to = ps.getString("receiver");
            listUsers.add(new User(to,new Persone("","")));
            LocalDateTime dateTime= ps.getObject("date_time",LocalDateTime.class);
            String message= ps.getString("description");
            Long id_replay;
            try {
                id_replay = ps.getLong("message_replay");
            }catch(PSQLException e){
                id_replay=null;
            }
            Message reply=null;
            if(id_replay!=null) reply=get(id_replay);
            mess=new Message(id,new User(from,new Persone("","")), message,listUsers,dateTime,reply);
        }
        return mess;
    }

    @Override
    protected List<Long> getAllIdStatement(ResultSet ps) throws SQLException {
        List<Long> listId=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_mess");
            listId.add(id);
        }
        return listId;
    }

    @Override
    protected List<Message> getAllStatement(ResultSet ps) throws SQLException {
        List<Message> messages=new ArrayList<>();
        List<User> listUsers=new ArrayList<>();
        while (ps.next()) {
            Long id = ps.getLong("id_mess");
            String from = ps.getString("sender");
            String to = ps.getString("receiver");
            listUsers.add(new User(to,new Persone("","")));
            LocalDateTime dateTime= ps.getObject("date_time",LocalDateTime.class);
            String message= ps.getString("description");
            Long id_replay;
            try {
                id_replay = ps.getLong("message_replay");
            }catch(PSQLException e){
                id_replay=null;
            }
            Message reply=null;
            if(id_replay!=null) reply=get(id_replay);
            Message mess=new Message(id,new User(from,new Persone("","")), message,listUsers,dateTime,reply);
            messages.add(mess);
        }
        return messages;
    }

    @Override
    protected void setDeleteStatement(PreparedStatement ps, Long id) throws SQLException {
        ps.setLong(1, id);
        ps.setLong(2, id);
        ps.setLong(3, id);
    }

    @Override
    protected void setUpdateStatement(PreparedStatement ps, Long id, Message entity) throws SQLException {
        ps.setString(1, entity.getFrom().getUsername());
        ps.setObject(2,entity.getDate());
        ps.setString(3, entity.getMessage());
        if (entity.getReply()!=null){
            ps.setLong(4,entity.getReply().getId());
        }
        else ps.setNull(4, Types.BIGINT);
        ps.setLong(5,id);
    }

    @Override
    protected void setGetOtherStatement(PreparedStatement ps, String... other) throws SQLException {
        ps.setString(1,other[0]);
        ps.setString(2,other[1]);
    }

    @Override
    public List<Message> getBySR(String sender, String Receiver) {
        List<Message> list;
        super.sql = "select * from public.\"Messages\" M inner join public.\"Messages_Users\" MU on M.id_mess=MU.id_mess where M.sender=? and MU.receiver=?";
        try {
            if(connection.isClosed())openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            setGetOtherStatement(ps, sender, Receiver);
            ResultSet resultSet = ps.executeQuery();
            list=getAllStatement(resultSet);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityRepoException(e.getMessage());
        }
    }

    @Override
    public List<Message> getByDateTime(LocalDateTime dateTime) {
        super.sql= "select * from public.\"Messages\" M inner join public.\"Messages_Users\" MU on M.id_mess=MU.id_mess where M.date_time=?";
        List<Message> list;
        try {
            if(connection.isClosed())openConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setObject(1,dateTime);
            ResultSet resultSet = ps.executeQuery();
            list=getAllStatement(resultSet);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new EntityRepoException(e.getMessage());
        }
    }


}
