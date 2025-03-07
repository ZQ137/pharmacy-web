package com.example.pharmacy.entity.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageDTO<T> {
    private List<T> items;
    private long total;
    private int page;
    private int limit;

    public PageDTO(Page<T> pageData) {
        this.items = pageData.getContent();
        this.total = pageData.getTotalElements();
        this.page = pageData.getNumber();
        this.limit = pageData.getSize();
    }

    public List<T> getItems() { return items; }
    public long getTotal() { return total; }
    public int getPage() { return page; }
    public int getLimit() { return limit; }
}
