package com.example.Controller.NewController;

import com.example.Domain.Entity;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.Repository;
import com.example.Utils.Exceptions.EntityException;
import com.example.Utils.Exceptions.EntityRepoException;

import java.util.List;

public abstract class Controller<T,E extends Entity> {
    //FileRepo repo;
    Repository repo;
    /**
     * Adds an entity to the repository
     * @param entity the object to be added, must inherite from Entity object
     * @return true if it was added with succes, false otherwise
     * @throws EntityRepoException if there is am entity with that id or other distinguishable components
     * @throws EntityException if there is an error from the repository
     */
    public boolean add(E entity){
        if(!repo.save(entity))
            throw new EntityRepoException("This entity is already in data base\n");
        return true;
    }

    /**
     * Removes an entity from the repository with the id of the object
     * @param id the id of the object to be removed
     * @return true if it was removed with success, false otherwise
     * @throws EntityException if there is an error from the repository
     */
    public boolean removeById(T id){
        if (!repo.delete(id))
            throw new EntityRepoException("There is not an entity with that id\n");
        return true;
    }

    /**
     * Removes an entity from the repository with other distinguishable components
     * @param others the list of strings with distinguishable components
     * @return true if it was removed with success, false otherwise
     * @throws EntityException if there is an error from the repository
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
     * @throws EntityException if there is an error from the repository
     */
    public E getById(T id){
        return (E) repo.get(id);
    }

    /**
     * it returns the object from the repository using some distinguishable components of that entity
     * @param others the list of strings with distinguishable components
     * @return the object with that id
     * @throws EntityException if there is an error from the repository
     */
    public E getByOther(String... others){
        return (E) repo.getByOther(others);
    };

    /**
     * Returns a list of Entity object from the repository
     * @return a list of Entity object
     * @throws EntityException if there is an error from the repository
     */
    public List<E> getAll(){
        return repo.getAll();
    }

    public Page<E> getCurrentPage(){
        return repo.getCurrentPage();
    }

    public Page<E> getNextPage(){
        return repo.getNextPage();
    }

    public Page<E> getPreviousPage(){
        return repo.getPreviousPage();
    }
    /**
     * Gives the current number of entities in the repository
     * @return number of entities in the repository
     * @throws EntityException if there is an error from the repository
     */
    public int getSize(){
        return repo.getSize();
    }

    public void openConnection(){
        repo.openConnection();
    }

    public void closeConnection(){
        repo.closeConnection();
    }
}
