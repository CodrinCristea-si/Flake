package com.example.Domain;

import java.util.Objects;



public class Persone extends Entity<Long> {

    String firstName; //prenume
    String lastName;  //nume de familie


    public Persone(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Persone(Long id,String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        setId(id);
    }

    /**
     * Gives the First name of a persone
     * @return string that contains the first name of that persone
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Replaces the first name of a persone
     * @param firstName the string to be replaced with
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gives the Last name of a persone
     * @return string that contains the last name of that persone
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Replaces the last name of a persone
     * @param lastName the string to be replaced with
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        Persone persone = (Persone) o;
        return Objects.equals(firstName, persone.firstName) && Objects.equals(lastName, persone.lastName);
    }

    /**
     * Gives the hash code of that object
     * @return hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    /**
     * Return the persone in an elegant way
     * @return a string that contains the data of that persone
     */
    @Override
    public String toString() {
        return "Persone{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
