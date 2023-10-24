package com.example.GUIController;

public class PrintedStats1 {
    String Username;
    String No_Messages;

    public PrintedStats1(String username, String no_Messages) {
        Username = username;
        No_Messages = no_Messages;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getNo_Messages() {
        return No_Messages;
    }

    public void setNo_Messages(String no_Messages) {
        No_Messages = no_Messages;
    }
}
