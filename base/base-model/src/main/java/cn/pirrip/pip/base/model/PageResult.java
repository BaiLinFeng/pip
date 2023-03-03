// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.fasterxml.jackson.annotation.JsonInclude;

import cn.pirrip.pip.base.util.PageUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分页查询结果.
 *
 * @param <T> 被封装的类型
 * @author gongshiwei@focusmedia.cn
 */
@Data
@ApiModel(description = "分页查询的响应")
public class PageResult<T> {
    @ApiModelProperty("命中总数")
    private int totalCount;

    @ApiModelProperty("总页数")
    private int totalPage;

    @ApiModelProperty("当前页数(1-based)")
    private int pageNo;

    @ApiModelProperty("分页大小")
    private int pageSize;

    @ApiModelProperty("本页结果")
    private List<T> result;

    @ApiModelProperty("排序字段")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String orderBy;

    @ApiModelProperty("排序顺序")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String order;

    public static <T> PageResult<T> withResult(
            List<T> result, int totalCount, int pageNo, int pageSize, String order, String orderBy
    ) {
        PageResult<T> page = new PageResult<>();
        page.setTotalCount(totalCount);
        page.setTotalPage(PageUtils.getTotalPage(totalCount, pageSize));
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setResult((result == null) ? new ArrayList<>() : result);
        page.setOrder(order);
        page.setOrderBy(orderBy);
        return page;
    }

    public static <T> PageResult<T> withResult(List<T> result, int totalCount, int pageNo, int pageSize) {
        return withResult(result, totalCount, pageNo, pageSize, null, null);
    }

    public static <T> PageResult<T> withResult(List<T> result) {
        int totalCount = result.size();
        int pageSize = Math.max(1, result.size());
        return withResult(result, totalCount, 1, pageSize);
    }

    /**
     * 将list结果按照pageNum,pageSize分页
     */
    public static <T> PageResult<T> getPageResult(List<T> list, int pageNum, int pageSize) {
        int size = CollectionUtils.isEmpty(list) ? 0 : list.size();
        if (pageNum == 0) {
            pageNum = 1;
        }
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = pageNum * pageSize - 1;
        if (size == 0 || startIndex > size - 1) {
            return PageResult.withResult(Collections.emptyList(), size, pageNum, pageSize);
        }
        if (endIndex > size - 1) {
            return PageResult.withResult(list.subList(startIndex, size),
                    size, pageNum, pageSize);
        }
        return PageResult.withResult(list.subList(startIndex, endIndex + 1),
                size, pageNum, pageSize);
    }

    public static <T> PageResult<T> empty() {
        return withResult(Collections.emptyList());
    }
}
