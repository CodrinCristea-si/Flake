package com.example.Controller.NewController;

import com.example.Domain.Event;
import com.example.Domain.Relationship;
import com.example.Repository.Db.EventDbRepo;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.PageType;

import java.util.List;

public class EventController extends Controller<Long,Event>{
    private EventDbRepo backupRepo;
    public EventController(EventDbRepo repo) {
        super.repo=repo;

        backupRepo=repo;
    }

    public List<Event> getUserEvents(Long id){
        return backupRepo.getUserEvents(id);
    }

    public Page<Event> getPageEvents( PageType type){
        return backupRepo.getPageEvents(type);
    }

    public Page<Event> getFirstPageEvents( PageType type){
        return backupRepo.getFirstPageEvents(type);
    }

    public Page<Event> getPageEventsSUBB( PageType type,Long id){
        return backupRepo.getPageEventsSUBSCRIBE(type,id);
    }

    public Page<Event> getFirstPageEventsSUBB( PageType type,Long id){
        return backupRepo.getFirstPageEventsSUBSCRIBE(type,id);
    }



}