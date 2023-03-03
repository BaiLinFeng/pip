// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util.date;


import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;

import lombok.experimental.UtilityClass;

/**
 * BaseDateUtils
 *
 * @author ZhongQin
 */
@UtilityClass
public class BaseDateUtils {

    /**
     * 判断日期是否在某段日期的中间
     */
    public static boolean isMiddleDate(LocalDate midDate, LocalDate startDate, LocalDate endDate) {
        com.google.common.collect.Range<LocalDate> dateRange =
                com.google.common.collect.Range.closed(startDate, endDate);
        return dateRange.contains(midDate);
    }

    /**
     * 求两个日期区间的交集
     */
    public static <T extends BaseDate> Optional<Pair<LocalDate, LocalDate>> intersectDate(
            final T dateA, final T dateB) {
        LocalDate maxStartDate = ObjectUtils.max(dateA.getStartDate(), dateB.getStartDate());
        LocalDate minEndDate = ObjectUtils.min(dateA.getEndDate(), dateB.getEndDate());

        if (maxStartDate.compareTo(minEndDate) > 0) {
            return Optional.empty();
        }

        return Optional.of(Pair.of(maxStartDate, minEndDate));
    }
}
