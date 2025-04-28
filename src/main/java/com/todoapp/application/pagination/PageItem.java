package com.todoapp.application.pagination;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageItem<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private Long totalElement;
    private int totalPages;

    public PageItem(List<T> content, int pageNumber, int pageSize, Long totalElement, int totalPages) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElement = totalElement;
        this.totalPages = totalPages;
    }

    public <R> PageItem<R> map(Function<T, R> mapper) {
        List<R> newContent = content.stream()
                .map(mapper)
                .collect(Collectors.toList());
        return new PageItem<>(
                newContent,
                this.pageNumber,
                this.pageSize,
                this.totalElement,
                this.totalPages);
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(Long totalElement) {
        this.totalElement = totalElement;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}