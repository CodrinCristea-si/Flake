package com.example.GUIController;

import java.time.LocalDate;

public class PrintedRelationship {
    private String From;
    private LocalDate Date;

    public PrintedRelationship(String from, LocalDate date) {
        From = from;
        Date = date;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }
}
