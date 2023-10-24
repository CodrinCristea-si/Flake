package com.example.Repository.PagingRepo;

import java.util.stream.Stream;

public class Page<E> implements PageCore<E>  {
    private Pageble currentPage;
    private Stream<E> elements;

    public Page() {
    }

    public Page(Pageble currentPageble, Stream<E> elements) {
        this.currentPage = currentPageble;
        this.elements = elements;
    }

    @Override
    public PagebleCore getCurrentPage() {
        return currentPage;
    }

    @Override
    public PagebleCore getNextPage() {
        return new Pageble(this.currentPage.getPageNumber()+1,this.currentPage.getPageSize());
    }

    @Override
    public PagebleCore getPreviousPage() {
        if(this.currentPage.getPageNumber()>0)
            return new Pageble(this.currentPage.getPageNumber()-1,this.currentPage.getPageSize());
        return currentPage;
    }

    @Override
    public Stream<E> getPageContent() {
        return elements;
    }
}
