package com.edu.utils;

/**
 * Created by dev on 2017/5/9.
 */
public final class Page {
    private Integer pageSize = 10;
    private Integer pageNow = 0;
    private Integer count;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
