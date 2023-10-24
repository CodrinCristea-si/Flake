package com.example.Controller.NewController;

import com.example.Domain.Persone;
import com.example.Repository.Db.PersoneDbRepo;

public class PersoneController extends  Controller<Long, Persone>{
    public PersoneController(PersoneDbRepo repo) {
        super.repo = repo;
    }
}
