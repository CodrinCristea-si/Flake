package com.example.Controller.NewController;

import com.example.Domain.Relationship;
import com.example.Repository.Db.RequestsDbRepo;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.PageType;

import java.util.ArrayList;
import java.util.List;

public class RequestsController  extends Controller<Long, Relationship>{

    private RequestsDbRepo backupRepo;
    public RequestsController(RequestsDbRepo rep) {
        super.repo=rep;
        backupRepo=rep;
    }

    public void UpdateStatus(Long id,Relationship entity){
        repo.update(id,entity);
    }


    public int deleteAllRelationsByUsername(String username){
        List<Long> listId= new ArrayList<>();
        for(Relationship rel: (List<Relationship>) repo.getAll()){
            if(rel.getFirstUserName().equals(username) || rel.getSecondUserName().equals(username)){
                listId.add(rel.getId());
            }
        }
        for(Long el: listId){
            repo.delete(el);
        }
        return listId.size();
    }

    public void deleteRequestFromSenderToReceiver(String username,String username2){
        List<Long> listId= new ArrayList<>();
        Long id = null;
        for(Relationship rel: (List<Relationship>) repo.getAll()){
            if(rel.getFirstUserName().equals(username) && rel.getSecondUserName().equals(username2)){
                id=rel.getId();
            }
        }

        repo.delete(id);
    }

    public Page<Relationship> getPageRequests(String username, PageType type){
        return backupRepo.getPageRequest(username, type);
    }

    public Page<Relationship> getFirstPageRequests(String username, PageType type){
        return backupRepo.getFirstPageRequest(username, type);
    }

    public List<Relationship> getAllPending(){
        return backupRepo.getAllPending();
    }
}
