// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip;

import lombok.experimental.UtilityClass;

/**
 * 标记应用的 base package
 *
 * @author gongshiwei@focusmedia.cn
 */
@UtilityClass
public class BasePackage {
    public static final String NAME = BasePackage.class.getPackage().getName();
}
