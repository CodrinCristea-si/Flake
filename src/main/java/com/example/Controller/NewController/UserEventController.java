package com.example.Controller.NewController;

import com.example.Domain.UserEvent;
import com.example.Repository.Db.UserEventDbRepo;

public class UserEventController extends Controller<Long, UserEvent> {
    public UserEventController(UserEventDbRepo repo) {
        super.repo=repo;
    }
}
