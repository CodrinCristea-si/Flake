package com.example.GUIController;

import java.time.LocalDate;

public class PrintedPersones {
    private String First_Name;
    private String Last_Name;

    public PrintedPersones(String Name,String LastName ) {
        First_Name = Name;
        Last_Name= LastName;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setFirst_Name(String name) {
        First_Name = name;
    }

    public void setLast_Name(String lastName) {
        Last_Name = lastName;
    }
}
