package com.example.Domain;

public class UserEvent extends Entity<Long> {
    Long id_user;
    Long id_event;
    Long id_ue;

    public UserEvent(Long id_ue,Long id_user, Long id_event) {
        this.id_ue=id_ue;
        this.id_user = id_user;
        this.id_event = id_event;
       // setId(id_ue);
    }

    public Long getId_user() {
        return id_user;
    }

    public Long getId_event() {
        return id_event;
    }

    public Long getId_ue() {
        return id_ue;
    }
}
