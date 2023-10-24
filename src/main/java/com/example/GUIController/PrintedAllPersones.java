package com.example.GUIController;

import java.time.LocalDate;

public class PrintedAllPersones {
    private String First_Name;
    private String Last_Name;
    private String username;

    public PrintedAllPersones(String Name,String LastName,String Username ) {
        First_Name = Name;
        Last_Name= LastName;
        username=Username;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }
    public String getUsername() {
        return username;
    }

    public void setFirst_Name(String name) {
        First_Name = name;
    }

    public void setLast_Name(String lastName) {
        Last_Name = lastName;
    }
    public void setUsername(String username) { username=username;}
}
