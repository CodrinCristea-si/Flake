package com.example.Repository.PagingRepo;

import com.example.Domain.Entity;

public class Paginator<E extends Entity<Long>> {
    private Pageble pageble;
    private Iterable<E> elements;


}
