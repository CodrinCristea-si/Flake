package com.example.Controller.NewController;

import com.example.Domain.Relationship;
import com.example.Repository.Db.RelationshipDbRepo;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.PageType;
import com.example.Utils.Algoritms.Graph;
import com.example.Utils.Exceptions.EntityException;

import java.util.ArrayList;
import java.util.List;

public class RelationshipController extends Controller<Long, Relationship> {
    private RelationshipDbRepo backupRepo;
    public RelationshipController(RelationshipDbRepo rep) {
        super.repo=rep;
        backupRepo= rep;
    }

    /**
     * Deletes all the relationships that contains the username
     * @param username the string to be used to delete those relationships
     * @return the number of relationships deleted
     * @throws EntityException if there is an error from the repository
     */
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

    /**
     * Gives the number of network
     * @param numOfUsers the current number of the users in repository
     * @return the number of current communities
     * @throws EntityException if there is an error from the repository
     */
    public int getNumberOfCommunities(int numOfUsers){
        Graph graph= new Graph(numOfUsers,repo.getSize());
        return graph.numberOfCommunities((RelationshipDbRepo) repo);
    }

    /**
     * Gives the most sociable community
     * @param numOfUsers the current number of the users in repository
     * @return a list with usernames of that users that belong to that community
     * @throws EntityException if there is an error from the repository
     */
    public List<String> getTheMostSociableCommunity(int numOfUsers){
        Graph graph= new Graph(numOfUsers,repo.getSize());
        return graph.theMostSociableCommunity((RelationshipDbRepo) repo);
    }

    public Page<Relationship> getPageFriends(String username, PageType type){
        return backupRepo.getPageFriends(username, type);
    }

    public Page<Relationship> getFirstPageFriends(String username, PageType type){
        return backupRepo.getFirstPageFriends(username, type);
    }
}
