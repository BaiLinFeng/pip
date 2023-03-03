// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util.date;

import java.time.LocalTime;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BasicTime
 *
 * @author likaiqiang@focusmedia.cn
 */
@Data
@NoArgsConstructor
public class BasicTime implements BaseTime {
    private LocalTime startTime;

    private LocalTime endTime;

    public BasicTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
