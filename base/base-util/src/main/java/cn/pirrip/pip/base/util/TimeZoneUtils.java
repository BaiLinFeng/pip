// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.time.ZoneOffset;
import java.util.Objects;

import lombok.experimental.UtilityClass;

/**
 * static methods for time zone
 *
 * @author gongshiwei@focusmedia.cn
 */
@UtilityClass
public class TimeZoneUtils {
    /**
     * 默认北京时区.
     */
    private static final ZoneOffset TIME_ZONE_BEIJING = ZoneOffset.ofHours(8);

    private volatile ZoneOffset localTimeZone = TIME_ZONE_BEIJING;

    /**
     * current local time zone
     */
    public static ZoneOffset localTimeZone() {
        return localTimeZone;
    }

    public static void setLocalTimeZone(ZoneOffset localTimeZone) {
        Objects.requireNonNull(localTimeZone, "null time zone");
        TimeZoneUtils.localTimeZone = localTimeZone;
    }
}
