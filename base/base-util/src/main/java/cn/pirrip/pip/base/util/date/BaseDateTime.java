// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util.date;

import java.time.LocalDate;
import java.time.LocalTime;

import com.google.common.base.Preconditions;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BaseDateTime
 *
 * @author ZhongQin
 */
@Data
@NoArgsConstructor
public class BaseDateTime implements BaseDateAndTime {

    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    public BaseDateTime(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        Preconditions.checkArgument(isValid(), "time model is invalid");
    }

    public BaseDateTime(BaseDateAndTime timeModel) {
        this(timeModel.getStartDate(), timeModel.getStartTime(), timeModel.getEndDate(), timeModel.getEndTime());
    }
}
