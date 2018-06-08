package com.jt.app.util;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @Auther: Administrator
 * @Date: 2018/5/23
 * @Description: 分页帮助类
 */
public class PageUtil implements Pageable {

    //页面传递数据
    private String sortAttribute;   //排序列
    private int pageNumber; //当前页
    private int pageSize; //每页行数
    private int offset; //从那行开始
    private Sort sort;  //排序

    //如需要，这里可以新添加需要处理的分页对象

    public PageUtil() {
    }

    @Override
    public int getPageNumber() {
        return this.pageNumber;
    }

    @Override
    public int getPageSize() {
        return this.pageSize;
    }

    @Override
    public int getOffset() {
        return this.offset;
    }

    @Override
    public Sort getSort() {
        return this.sort;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }

    public String getSortAttribute() {
        return sortAttribute;
    }

    public void setSortAttribute(String sortAttribute) {
        this.sortAttribute = sortAttribute;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
