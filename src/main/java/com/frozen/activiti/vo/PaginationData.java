package com.frozen.activiti.vo;

import lombok.Data;

import java.util.List;

@Data
public class PaginationData<T>{

    /** 每页大小 */
    protected int pageSize;
    /** 总页数 */
    protected int totalPages;
    /** 当前页索引(从1开始) */
    protected int currentPage;
    /** 分页列表数据 */
    protected List<T> dataList;
    /** 全部数据记录数量*/
    protected int totalCount;

}
