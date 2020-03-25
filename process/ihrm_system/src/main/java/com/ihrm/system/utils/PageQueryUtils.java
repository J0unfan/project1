package com.ihrm.system.utils;

import java.util.Map;

/**
 * @author caorui
 * @date 2020/3/23
 */
public class PageQueryUtils {
    private Integer pageSize;
    private Integer pageNum;
    private String query;


    public PageQueryUtils(Integer pageSize, Integer pageNum, String query) {
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.query = query;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * 初始化PageQuery
     *
     * @param params
     * @return
     */
    public static PageQueryUtils init(Map<String, Object> params) {
        Integer pagesize = params.get("pagesize") == null ? 10 : Integer.valueOf(params.get("pagesize").toString());
        Integer page = params.get("pagenum") == null ? 0 : (Integer.valueOf(params.get("pagenum").toString()) - 1) * pagesize;
        String query = params.get("query").toString();
        PageQueryUtils pageQuery = new PageQueryUtils(pagesize, page, query);
        return pageQuery;
    }

}
