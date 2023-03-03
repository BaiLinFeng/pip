// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.model;

import java.util.ArrayList;
import java.util.List;

import cn.pirrip.pip.base.util.PageUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 带汇总的分页数据
 *
 * @param <T> 被封装的类型
 * @author Li Yunsheng(liyunsheng@focusmedia.cn)
 */
@Data
public class TotalPageResult<T> extends PageResult<T> {
    @ApiModelProperty("本页结果")
    private T totalResult;

    public static <T> TotalPageResult<T> withResult(T totalResult, List<T> result, int totalCount, int pageNo,
                                                    int pageSize, String order, String orderBy
    ) {
        TotalPageResult<T> page = new TotalPageResult<>();
        page.setTotalResult(totalResult);
        page.setTotalCount(totalCount);
        page.setTotalPage(PageUtils.getTotalPage(totalCount, pageSize));
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setResult((result == null) ? new ArrayList<>() : result);
        page.setOrder(order);
        page.setOrderBy(orderBy);
        return page;
    }

    public static <T> TotalPageResult<T> withResult(T totalResult, List<T> result, int totalCount, int pageNo,
                                                    int pageSize) {
        return withResult(totalResult, result, totalCount, pageNo, pageSize, null, null);
    }
}
