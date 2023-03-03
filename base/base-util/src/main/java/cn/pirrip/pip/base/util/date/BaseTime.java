// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util.date;

import java.time.LocalTime;

/**
 * BaseTime
 *
 * @author ZhongQin
 */
public interface BaseTime {

    static BaseTime of(LocalTime startTime, LocalTime endTime) {
        return new BasicTime(startTime, endTime);
    }

    LocalTime getStartTime();

    void setStartTime(LocalTime startTime);

    LocalTime getEndTime();

    void setEndTime(LocalTime endTime);
}
