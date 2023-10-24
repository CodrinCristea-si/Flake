package com.example.Repository;

import com.example.Domain.Relationship;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.Pageble;

public interface RelationshipRepo extends Repository<Long, Relationship>{
    /**
     * Checks if there is any relationship that has the corespondent usernames
     * @param username1 the first username to be found
     * @param username2 the second username to be found
     * @return the corespondent object or null otherwise
     */
    Relationship getByUserNames(String username1, String username2);

    public Page<Relationship> getUsersFriends(String username, Pageble pageble);
}
