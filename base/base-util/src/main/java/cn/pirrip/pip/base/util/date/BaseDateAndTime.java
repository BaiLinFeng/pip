// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import com.google.common.collect.Range;

/**
 * BaseDateAndTime
 *
 * @author Xiong Chao(xiongchao@focusmedia.cn)
 */
public interface BaseDateAndTime extends BaseDate, BaseTime {
    static BaseDateAndTime of(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return new BaseDateTime(startDate, startTime, endDate, endTime);
    }

    static BaseDateAndTime copy(BaseDateAndTime timeModel) {
        return BaseDateAndTime.of(timeModel.getStartDate(), timeModel.getStartTime(),
                timeModel.getEndDate(), timeModel.getEndTime());
    }

    @JsonIgnore
    default LocalDateTime getStartDateTime() {
        return getStartDate().atTime(getStartTime());
    }

    @JsonIgnore
    default LocalDateTime getEndDateTime() {
        return getEndDate().atTime(getEndTime());
    }

    /**
     * 判断dateTimeModel是否有效,时间面积必须大于0
     */
    @JsonIgnore
    default boolean isValid() {
        return !this.getStartDate().isAfter(this.getEndDate())
                && this.getStartTime().isBefore(this.getEndTime());
    }

    /**
     * 判断当前时间是否覆盖参数时间
     */
    @JsonIgnore
    default boolean isCover(BaseDateAndTime timeModel) {
        return !this.getStartDate().isAfter(timeModel.getStartDate())
                && !this.getStartTime().isAfter(timeModel.getStartTime())
                && !this.getEndDate().isBefore(timeModel.getEndDate())
                && !this.getEndTime().isBefore(timeModel.getEndTime());
    }

    /**
     * 判断两段时间是否有重叠
     */
    @JsonIgnore
    default boolean isOverlapped(BaseDateAndTime timeModel) {
        return Range.closed(this.getStartDate(), this.getEndDate())
                .isConnected(Range.closed(timeModel.getStartDate(), timeModel.getEndDate()))
                && Range.closed(this.getStartTime(), this.getEndTime())
                .isConnected(Range.closed(timeModel.getStartTime(), timeModel.getEndTime()));
    }

    default BaseDateAndTime intersectionRange(BaseDateAndTime timeModel) {
        Preconditions.checkArgument(this.isOverlapped(timeModel), "there is no intersection between two time range");

        Range<LocalDate> dateIntersect = Range.closed(this.getStartDate(), this.getEndDate())
                .intersection(Range.closed(timeModel.getStartDate(), timeModel.getEndDate()));
        Range<LocalTime> timeIntersect = Range.closed(this.getStartTime(), this.getEndTime())
                .intersection(Range.closed(timeModel.getStartTime(), timeModel.getEndTime()));

        return BaseDateAndTime.of(
                dateIntersect.lowerEndpoint(), timeIntersect.lowerEndpoint(),
                dateIntersect.upperEndpoint(), timeIntersect.upperEndpoint());
    }
}
