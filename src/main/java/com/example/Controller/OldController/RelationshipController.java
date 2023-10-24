package com.example.Controller.OldController;

import com.example.Domain.Relationship;
import com.example.Repository.Memory.RelationshipMemoryRepo;
import com.example.Repository.RelationshipRepo;
import com.example.Utils.Algoritms.Graph;

import java.util.ArrayList;
import java.util.List;

public class RelationshipController extends Controller<Long, Relationship>{

    public RelationshipController(RelationshipMemoryRepo rep) {
        super.repo=rep;
    }

    /**
     * Deletes all the relationships that contains the username
     * @param username the string to be used to delete those relationships
     * @return the number of relationships deleted
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
     * @return the number of current cummunities
     */
    public int getNumberOfCommunities(int numOfUsers){
        Graph graph= new Graph(numOfUsers,repo.getSize());
        return graph.numberOfCommunities((RelationshipRepo) repo);
    }

    /**
     * Gives the most sociable community
     * @param numOfUsers the current number of the users in repository
     * @return a list with usernames of that users that belong to that community
     */
    public List<String> getTheMostSociableCommunity(int numOfUsers){
        Graph graph= new Graph(numOfUsers,repo.getSize());
        return graph.theMostSociableCommunity((RelationshipRepo) repo);
    }

    /**
     * Loads the data from a file
     * @param filename the file where the data needs to be loaded
     */
   // public void loadData(String filename){repo.loadData(filename);}

    /**
     * Saveds the data from a file
     * @param filename the file where the data needs to be saved
     */
   // public void saveData(String filename){repo.saveData(filename);}

}
