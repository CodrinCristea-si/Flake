package com.example.Domain;


import java.time.LocalDate;
import java.util.Objects;

public class Relationship extends Entity<Long>{
    private String userName1;
    private String userName2;

    private LocalDate dtf;
    private String status;


    public Relationship(String firstUserName, String secondUserName) {
        initialize(firstUserName,secondUserName);
    }

    public Relationship(Long id,String firstUserName, String secondUserName) {
        initialize(firstUserName,secondUserName);
        super.setId(id);
    }


    public Relationship(Long id,String firstUserName, String secondUserName,LocalDate dtf){
        initialize2(firstUserName,secondUserName,dtf);
        super.setId(id);
    }



    public Relationship(String firstUserName, String secondUserName,LocalDate dtf){
        initialize2(firstUserName,secondUserName,dtf);
    }

    public Relationship(Long id,String firstUserName, String secondUserName,LocalDate dtf,String status){
        initialize3(firstUserName,secondUserName,dtf,status);
        super.setId(id);
    }

    public Relationship(String firstUserName, String secondUserName,LocalDate dtf,String status){
        initialize3(firstUserName,secondUserName,dtf,status);

    }

    /**
     * Initializez the fields of the class
     * @param firstUserName
     * @param secondUserName
     */
    private void initialize(String firstUserName, String secondUserName){
        this.userName1 = firstUserName;
        this.userName2 = secondUserName;
    }

    private void initialize2(String firstUserName, String secondUserName,LocalDate dtf){
        this.userName1 = firstUserName;
        this.userName2 = secondUserName;
        this.dtf=dtf;
    }

    private void initialize3(String firstUserName, String secondUserName,LocalDate dtf,String status){
        this.userName1 = firstUserName;
        this.userName2 = secondUserName;
        this.dtf=dtf;
        this.status=status;
    }


    /**
     * Gives the First username of the relationship
     * @return string that contains the first username of the relationship
     */
    public String getFirstUserName() {
        return userName1;
    }

    /**
     * Replaces the first username of a relationship
     * @param userName1 the string to be replaced with
     */
    public void setFirstUserName(String userName1) {
        this.userName1 = userName1;
    }

    /**
     * Gives the Second username of the relationship
     * @return string that contains the second username of the relationship
     */
    public String getSecondUserName() {
        return userName2;
    }

    /**
     * Replaces the second username of a relationship
     * @param userName2 the string to be replaced with
     */
    public void setSecondUserName(String userName2) {
        this.userName2 = userName2;
    }

    /**
     * Checks The Equality of 2 objects
     * @param o the object to be compared with
     * @return true, the the objects are equal, false otherwise
     */

    /**
     * Gives the time of the relationship
     * @return time of the relationship
     */
    public LocalDate getDtf() {
        return dtf;
    }

    public void setDtf(LocalDate dtf) {
        this.dtf = dtf;
    }

    public String getStatus() { return status;}

    public void setStatus(String status) { this.status = status;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Relationship that = (Relationship) o;
        return (getId()!=null && getId()==((Relationship) o).getId()) || (Objects.equals(userName1, that.userName1) && Objects.equals(userName2, that.userName2));
    }

    /**
     * Gives the hash code of that object
     * @return hash code of the object
     */
    @Override
    public int hashCode() {
        return Objects.hash(userName1, userName2,getId());
    }

    /**
     * Return the relationship in an elegant way
     * @return a string that contains the data of that relationship
     */
    @Override
    public String toString() {

        if (getId()!=null )

        if (getId()!=null && getStatus()==null)

            return "Relationship{" +
                "id= "+ super.getId().toString() + " between "
                +'\''+ userName1 + '\'' + " and " +
                '\'' + userName2 + '\'' +

                ' '+ "started at " + ' '+
                 dtf +
                '}';

        if (getId()!=null && getStatus()!=null)
            return "Relationship{" +
                    "id= "+ super.getId().toString() + " between "
                    +'\''+ userName1 + '\'' + " and " +
                    '\'' + userName2 + '\'' +
                    ' '+ "started at " + ' '+
                    dtf + " status: " + status+
                    '}';



        return "Relationship{" + " between "
                +'\''+ userName1 + '\'' + " and " +
                '\'' + userName2 + '\''+
                '}';
    }



            /*    '}';
        return "Relationship{" + " between "
                +'\''+ userName1 + '\'' + " and " +
                '\'' + userName2 + '\'' +
                '}';
    }*/


}
