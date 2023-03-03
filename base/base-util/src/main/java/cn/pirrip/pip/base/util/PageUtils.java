// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import static com.google.common.base.Preconditions.checkArgument;

import lombok.experimental.UtilityClass;

/**
 * Page Utils.
 *
 * @author Xiong Chao(xiongchao@focusmedia.cn)
 */
@UtilityClass
public class PageUtils {
    public static int getOffset(int pageNo, int pageSize) {
        checkArgument(pageNo >= 0, "pageNo(%s) must not be negative", pageNo);
        checkArgument(pageSize > 0, "pageSize(%s) must be positive", pageSize);

        if (pageNo == 0) {
            pageNo = 1;
        }
        return pageSize * (pageNo - 1);
    }

    public static int getTotalPage(int totalCount, int pageSize) {
        checkArgument(totalCount >= 0, "totalCount(%s) must not be negative", totalCount);
        checkArgument(pageSize > 0, "pageSize(%s) must be positive", pageSize);

        if (totalCount == 0) {
            return 1;
        }
        return (totalCount - 1) / pageSize + 1;
    }
}
