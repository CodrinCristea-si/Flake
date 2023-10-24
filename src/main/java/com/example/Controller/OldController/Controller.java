package com.example.Controller.OldController;

import com.example.Domain.Entity;
import com.example.Repository.Repository;
import com.example.Utils.Exceptions.EntityRepoException;

import java.util.List;

public abstract class Controller<T,E extends Entity> {
    Repository repo;
    //DbRepoId repo;
    /**
     * Adds an entity to the repository
     * @param entity the object to be added, must inherite from Entity object
     * @return true if it was added with succes, false otherwise
     */
    public boolean add(E entity){
        if(repo.save(entity)==false)
            throw new EntityRepoException("This entity is already in data base\n");
        return true;
    }

    /**
     * Removes an entity from the repository with the id of the object
     * @param id the id of the object to be removed
     * @return true if it was removed with succes, false otherwise
     */
    public boolean removeById(T id){
        if (!repo.delete(id))
            throw new EntityRepoException("There is not an entity with that id\n");
        return true;
    }

    /**
     * Removes an entity from the repository with other distinguishable components
     * @param others the list of strings with distinguishable components
     * @return true if it was removed with succes, false otherwise
     */
    public boolean removeByOthers(String... others){
        E entity= (E) repo.getByOther(others);
        if(entity==null)
            throw new EntityRepoException("There is not an entity with that id\n");
        repo.delete(entity.getId());
        return true;
    };

    /**
     * it returns the object from the repository with a corespondent id
     * @param id the id of the object to be found
     * @return the object with that id
     */
    public E getById(T id){
        return (E) repo.get(id);
    }

    /**
     * it returns the object from the repository using some distinguishable components of that entity
     * @param others the list of strings with distinguishable components
     * @return the object with that id
     */
    public E getByOther(String... others){
        return (E) repo.getByOther(others);
    };

    /**
     * Returns a list of Entity object from the repository
     * @return a list of Entity object
     */
    public List<E> getAll(){
        return repo.getAll();
    }

    /**
     * Gives the current number of entities in the repository
     * @return number of entities in the repository
     */
    public int getSize(){
        return repo.getSize();
    }

}
