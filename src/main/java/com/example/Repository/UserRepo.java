package com.example.Repository;


import com.example.Domain.User;

public interface UserRepo extends Repository<Long,User>{

    /**
     * Checks if there is any user that has the corespondent username
     * @param username the username of the user to be found
     * @return the corespondent object or null otherwise
     */
    User getByUserName(String username);

    User getUserLogin(String username, String hash_pass);
}
