package com.example.Repository.Memory;
import com.example.Domain.Entity;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.Pageble;
import com.example.Repository.Repository;

import java.util.ArrayList;
import java.util.List;

public abstract class MemoryRepo<Id, E extends Entity<Id>> implements Repository<Id,E> {

    protected List<E> list;
    protected int size=0;

    /**
     * Initializez the fields of the class
     */
    protected void initiateRepo(){
        list= new ArrayList<E>();
        size=0;
    }

    /**
     * Saves an entity to repository
     * @param entity the object to be saved
     * @return true if it has been saved with succes, flse otherwise
     */
    @Override
    public abstract boolean save(E entity);

    /**
     * Saves an entity to repository
     * @param entity the object to be saved
     * @return true if it has been saved with succes, flse otherwise
     */
    protected boolean saveToRepo(E entity) {
        if(entity.getId()==null) entity.setId(generateId());
        else if(get(entity.getId())!=null) return false;
        list.add(entity);
        size++;
        return true;
    }

    /**
     * Retrives the corespondent object of that id
     * @param id the id of the object to be found
     * @return the object that has that id or null if there is no object with that id
     */
    @Override
    public E get(Id id) {
        E entity = null;
        for (E ent : list) {
            if (ent.getId() == id)
                entity = ent;
        }
        return entity;
    }

    /**
     * Replaces and Entity with that id with a new Entity
     * @param id the id of the object to be replased
     * @param entity the entity to be replased with
     * @return true if the entity has been updated with succes, false otherwise
     */
    @Override
    public boolean update(Id id,E entity) {
        E entity_copy=get(id);
        if(!delete(id) && !save(entity)){
            save(entity_copy);
            return false;
        }
        save(entity);
        return true;
    }

    /**
     * Deletes the object from the repository with that id
     * @param id the id of the object to be deleted
     * @return true if it was deleted with succes, false otherwise
     */
    @Override
    public boolean delete(Id id) {
        int index=getIndexOf(id);
        if(index==-1) return false;
        list.remove(index);
        size--;
        return true;
    }

    /**
     * Gives the current Number of entities stored in repository
     * @return the current Number of entities
     */
    @Override
    public int getSize() {
        return size;
    }

    /**
     * Gives a list with the entities stored in repository
     * @return a list of entities
     */
    @Override
    public List<E> getAll() {
        return list;
    }

    @Override
    public Page<E> getAll(Pageble pageble) {
        return null;
    }

    @Override
    public Page<E> getCurrentPage() {
        return null;
    }

    @Override
    public Page<E> getNextPage() {
        return null;
    }

    @Override
    public Page<E> getPreviousPage() {
        return null;
    }

    @Override
    public void openConnection() {

    }

    @Override
    public void closeConnection() {

    }

    /**
     * Gives the index of the object stored in repository with that id
     * @param id the id of the object to be found
     * @return the index of the object in repository or -1 if there is not an object iwth that id
     */
    private int getIndexOf(Id id){
        int index=0;
        for (E ent : list) {
            if (ent.getId() == id)
                return index;
            index++;
        }
        return -1;
    }

    /**
     * Gives a list with all the ids store din repository
     * @return a list of ids
     */
    @Override
    public List<Id> getAllIds(){
        List<Id> listId= new ArrayList<Id>();
        for (E ent: list){
            listId.add(ent.getId());
        }
        return listId;
    }

    /**
     * Generates an id for an entity
     */
    public abstract Id generateId();

    /**
     * Checks if it is an object stored with some distinguishable components
     * @param other a list of string with distinguishable components
     * @return the object to be found or null if there is no object with that components
     */
    public abstract E getByOther(String... other);
}
