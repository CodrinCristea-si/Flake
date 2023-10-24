package com.example.Repository;


import com.example.Domain.Entity;
import com.example.Repository.PagingRepo.Page;
import com.example.Repository.PagingRepo.Pageble;

import java.util.List;


public interface Repository<Id, E extends Entity<Id>> {
     /**
      * Saves an entity to repository
      * @param entity the object to be saved
      * @return true if it has been saved with succes, flse otherwise
      */
     boolean save(E entity);

     /**
      * Retrives the corespondent object with that id
      * @param id the id of the object to be found
      * @return the object that has that id or null if there is no object with that id
      */
     E get(Id id);

     /**
      * Replaces and Entity with that id with a new Entity
      * @param id the id of the object to be replased
      * @param entity the entity to be replased with
      * @return true if the entity has been updated with succes, false otherwise
      */
     boolean update (Id id,E entity);

     /**
      * Deletes the object from the repository with that id
      * @param id the id of the object to be deleted
      * @return true if it was deleted with succes, false otherwise
      */
     boolean delete (Id id);

     /**
      * Gives the current Number of entities stored in repository
      * @return the current Number of entities
      */
     int getSize();

     /**
      * Gives a list with all the entities stored in repository
      * @return a list of entities
      */
     List<E> getAll();

     Page<E> getAll(Pageble pageble);

     Page<E> getCurrentPage();

     Page<E> getNextPage();

     Page<E> getPreviousPage();

     void openConnection();

     void closeConnection();

     /**
      * Gives a list with all the ids store din repository
      * @return a list of ids
      */
     public List<Id> getAllIds();

     /**
      * Generates an id for an entity
      */
      Id generateId();

     /**
      * Checks if it is an object stored with some distinguishable components
      * @param other a list of string with distinguishable components
      * @return the object to be found or null if there is no object with that components
      */
     E getByOther(String... other);
}
