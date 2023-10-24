package com.example.Utils.Algoritms;

import java.util.Objects;

public class Pair<E,T> {
    E first;
    T second;

    public Pair(E first, T second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Gives the first element of the pair
     * @return the first element of the pair
     */
    public E getFirst(){
        return first;
    }

    /**
     * Gives the second element of the pair
     * @return the second element of the pair
     */
    public T getSecond(){
        return second;
    }

    /**
     * Replaces the first element of the pair with a new one
     * @param first the element to be replaced with
     */
    public void setFirst(E first) {
        this.first = first;
    }

    /**
     * Replaces the second element of the pair with a new one
     * @param second the element to be replaced with
     */
    public void setSecond(T second) {
        this.second = second;
    }

    /**
     * Checks The Equality of 2 objects
     * @param o the object to be compared with
     * @return true, the the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    /**
     * Gives the hash code of that object
     * @return hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
