package com.example.Utils;

import java.util.List;
import java.util.Random;

public class Generator {
    /**
     * Generates an id that isnt in listId
     * @param listId the list of the taken ids
     * @return an id that isnt in the list
     */
    public static Long generateId(List<Long> listId){
        Random rand=new Random();
        Long id= rand.nextLong();
        while(isIn(listId,id) || id<=0){
            id= rand.nextLong();
        }
        return id;
    }

    /**
     * Checks if an element is in the list
     * @param listId the list where the search will take place
     * @param elem the element to be found
     * @return true if elem is in the listId, false otherwise
     */
    private static boolean isIn(List<Long> listId,Long elem){
        for (Long el:listId)
            if(el==elem) return true;
        return false;
    }

}
