package com.example.Repository.PagingRepo;

import com.example.Domain.Entity;

public class Pageble<E extends Entity<Long>> implements PagebleCore<E> {
    private int pageNumber = 0;
    private int pageSize = 10;

    public Pageble() {
    }

    public Pageble(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
