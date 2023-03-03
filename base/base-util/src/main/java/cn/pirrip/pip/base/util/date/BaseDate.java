// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util.date;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import com.google.common.collect.Range;


/**
 * BaseDate
 *
 * @author ZhongQin
 */
public interface BaseDate {

    static BaseDate of(LocalDate startDate, LocalDate endDate) {
        return new BasicDate(startDate, endDate);
    }

    LocalDate getStartDate();

    void setStartDate(LocalDate startDate);

    LocalDate getEndDate();

    void setEndDate(LocalDate endDate);

    /**
     * 判断两段时间是否有重叠
     */
    @JsonIgnore
    default boolean isOverlapped(BaseDate dateModel) {
        return Range.closed(this.getStartDate(), this.getEndDate())
                .isConnected(Range.closed(dateModel.getStartDate(), dateModel.getEndDate()));
    }

    default BaseDate intersectionRange(BaseDate dateModel) {
        Preconditions.checkArgument(this.isOverlapped(dateModel), "there is no intersection between two time range");

        Range<LocalDate> dateIntersect = Range.closed(this.getStartDate(), this.getEndDate())
                .intersection(Range.closed(dateModel.getStartDate(), dateModel.getEndDate()));

        return BaseDate.of(
                dateIntersect.lowerEndpoint(), dateIntersect.upperEndpoint());
    }
}
