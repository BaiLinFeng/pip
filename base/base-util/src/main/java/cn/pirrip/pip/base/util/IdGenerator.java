// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.util.UUID;

import lombok.experimental.UtilityClass;

/**
 * IdGenerator
 *
 * @author Zhang Hang(zhanghang@focusmedia.cn)
 */
@UtilityClass
public class IdGenerator {
    public static String getUuid32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
