package com.example.GUIController;

public class PrintedUsers {
    String Username;
    String FirstName;
    String LastName;

    public PrintedUsers(String username, String firstName, String lastName) {
        Username = username;
        FirstName = firstName;
        LastName = lastName;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
