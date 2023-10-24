package com.example.Repository.Memory;

import com.example.Domain.User;
import com.example.Repository.UserRepo;
import com.example.Utils.Generator;

public class UserMemoryRepo extends MemoryRepo<Long, User> implements UserRepo {

    public UserMemoryRepo(){
        initiateRepo();
    }

    /**
     * Checks if there is any user that has the corespondent username
     * @param username the username of the user to be found
     * @return the corespondent object or null otherwise
     */
    public User getByUserName(String username){
        User user=null;
        for(User use:getAll()){
            if(use.getUsername().equals(username))
                user=use;
        }
        return user;
    }

    @Override
    public User getUserLogin(String username, String hash_pass) {
        return null;
    }

    /**
     * Saves an user to repository
     * @param entity the object to be saved
     * @return true if it has been saved with succes, flse otherwise
     */
    @Override
    public boolean save(User entity) {
        if(getByUserName(entity.getUsername())!=null)return false;
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
     * Checks if there is any user that has the corespondent username
     * @param other the username of the user to be found
     * @return the corespondent object or null otherwise
     */
    @Override
    public User getByOther(String... other) {
        return getByUserName(other[0]);
    }
}
