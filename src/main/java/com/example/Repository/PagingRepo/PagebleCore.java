package com.example.Repository.PagingRepo;

import com.example.Domain.Entity;

public interface PagebleCore <E extends Entity<Long>>{
    int getPageNumber();
    int getPageSize();
}
