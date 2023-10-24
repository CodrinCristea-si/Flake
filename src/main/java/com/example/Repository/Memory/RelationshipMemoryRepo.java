package com.example.Repository.Memory;

import com.example.Domain.Relationship;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.Pageble;
import com.example.Repository.RelationshipRepo;
import com.example.Utils.Generator;

public class RelationshipMemoryRepo extends MemoryRepo<Long, Relationship> implements RelationshipRepo {

    public RelationshipMemoryRepo(){initiateRepo();}

    /**
     * Saves a relationship to repository
     * @param entity the object to be saved
     * @return true if it has been saved with succes, flse otherwise
     */
    @Override
    public boolean save(Relationship entity) {
        if(getByUserNames(entity.getFirstUserName(), entity.getSecondUserName())!=null)return false;
        return saveToRepo(entity);
    }
    /**
     *Generates an id for an entity
     * @return and id that there isnt in repository
     */
    @Override
    public Long generateId() {
        return Generator.generateId(getAllIds());
    }

    /**
     * Checks if it is an object stored with some distinguishable components
     * @param other a list of string with distinguishable components
     * @return the object to be found or null if there is no object with that components
     */
    @Override
    public Relationship getByOther(String... other) {
        return getByUserNames(other[0],other[1]);
    }

    /**
     * Checks if there is any relationship that has the corespondent usernames
     * @param username1 the first username to be found
     * @param username2 the second username to be found
     * @return the corespondent object or null otherwise
     */
    public Relationship getByUserNames(String username1, String username2){
        Relationship relation=null;
        for(Relationship rel:getAll()){
            if(rel.getFirstUserName().equals(username1) && rel.getSecondUserName().equals(username2))
                relation=rel;
        }
        return relation;
    }

    @Override
    public Page<Relationship> getUsersFriends(String username, Pageble pageble) {
        return null;
    }


}
