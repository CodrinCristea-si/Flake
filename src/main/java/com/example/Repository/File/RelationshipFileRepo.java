package com.example.Repository.File;

import com.example.Domain.Relationship;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.Pageble;
import com.example.Repository.RelationshipRepo;
import com.example.Utils.Exceptions.EntityRepoException;
import com.example.Utils.Generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class RelationshipFileRepo extends FileRepo<Long,Relationship> implements RelationshipRepo {
    private String delimiter=new String(",");

    public RelationshipFileRepo() {
        initiateRepo();
    }

    /**
     * Loads data from files
     * @param other a list of strings with the name of the files
     */
    @Override
    public void loadData(String... other){
        loadRelationship(other[0]);
    }

    /**
     * Loads all the relationships from a file
     * @param filename the name of the file
     */
    public void loadRelationship(String filename){
        try{
            File file=new File(filename);
            Scanner scan=new Scanner(file);
            while(scan.hasNextLine()){
                String data=scan.nextLine();
                List<String> values= List.of(data.split(delimiter));
                Relationship rel= new Relationship(Long.parseLong(values.get(0)),values.get(1), values.get(2));
                save(rel);
            }
            scan.close();
        } catch (FileNotFoundException e) {
            throw(new EntityRepoException("The data cannot be loaded"));
        }
    }

    /**
     * Saves data to files
     * @param other a list of strings with the name of the files
     */
    @Override
    public void saveData(String... other){
        saveRelationship(other[0]);
    }
    /**
     * Saves all the relationships to a file
     * @param filename the name of the file
     */
    public void saveRelationship(String filename){
        try {
            FileWriter file=new FileWriter(filename);
            for(Relationship el: (List<Relationship>)getAll()){
                file.write(el.getId().toString()+delimiter+el.getFirstUserName()+
                        delimiter+el.getSecondUserName()+"\n");
            }
            file.close();
        } catch (IOException e) {
            throw(new EntityRepoException("The data cannot be saved"));
        }
    }

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
        for(Relationship rel:(List<Relationship>)getAll()){
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
