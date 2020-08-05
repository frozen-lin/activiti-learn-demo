package com.frozen.activiti.util;

import com.frozen.activiti.vo.PaginationData;

import java.util.List;

/**
 * <program> activiti </program>
 * <description>  </description>
 *
 * @author : wlin
 * @date : 2020-08-03 14:57
 **/
public class WebUtil {
    public static <T> PaginationData<T> getPaginationData(int currentPage, List<T> dataList, int pageSize, int totalCount) {
        PaginationData<T> paginationData = new PaginationData<>();
        paginationData.setCurrentPage(currentPage);
        paginationData.setDataList(dataList);
        paginationData.setPageSize(pageSize);
        paginationData.setTotalCount(totalCount);
        paginationData.setTotalPages((totalCount / pageSize) + 1);
        return paginationData;
    }
}
