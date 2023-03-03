// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.pirrip.pip.base.util.PageUtils;

/**
 * PageRequest
 *
 * @author Xiong Chao(xiongchao@focusmedia.cn)
 */
public interface PageRequest {
    int getPageNo();

    int getPageSize();

    @JsonIgnore
    default int getOffset() {
        return PageUtils.getOffset(getPageNo(), getPageSize());
    }
}
