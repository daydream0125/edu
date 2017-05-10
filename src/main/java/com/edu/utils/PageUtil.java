package com.edu.utils;

/**
 * Created by dev on 2017/5/9.
 */
public class PageUtil {
    public static int getFirst(Page page) {
        return page.getPageNow() == 1 ? 0 : (page.getPageNow() - 1) * page.getPageSize();
    }

    public static int getMax(Page page) {
        return page.getPageNow() * page.getPageSize() > page.getCount() ? page.getCount() : page.getPageNow() * page.getPageSize();
    }

}
