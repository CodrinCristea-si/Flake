package com.example.Domain;

public class Entity<T> {
    private T id =null;

    /**
     * Gives the ID of the entity
     * @return the id of the entity
     */
    public T getId() {
        return id;
    }

    /**
     * Sets the id of an entity
     * @param id the id to be replaced with
     */
    public void setId(T id) {
        this.id = id;
    }
}
