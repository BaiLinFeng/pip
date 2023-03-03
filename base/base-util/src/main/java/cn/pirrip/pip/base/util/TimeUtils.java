// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.ImmutableSortedSet;

import cn.pirrip.pip.base.util.date.BaseTime;
import lombok.experimental.UtilityClass;

/**
 * TimeUtils
 *
 * @author ZhongQin
 */
@UtilityClass
public class TimeUtils {

    /**
     * 一天的开始时间
     */
    public static final LocalTime START_OF_DAY = LocalTime.MIDNIGHT;

    /**
     * 一天的结束时间
     */
    public static final LocalTime END_OF_DAY = LocalTime.of(23, 59, 59);

    /**
     * get current time. 可以配合 {@code cn.pirrip.pip.base.server.test.time.TimeMock} mock 当前时间.
     */
    public static Instant now() {
        return NowProvider.getInstance().now();
    }

    /**
     * get current date. 可以配合 {@code cn.pirrip.pip.base.server.test.time.TimeMock} mock 当前时间.
     */
    public static LocalDate today() {
        Instant now = NowProvider.getInstance().now();
        return LocalDateTime.ofInstant(now, TimeZoneUtils.localTimeZone()).toLocalDate();
    }

    /**
     * get current date time. 可以配合 {@code cn.pirrip.pip.base.server.test.time.TimeMock} mock 当前时间.
     */
    public static LocalDateTime localNow() {
        Instant now = NowProvider.getInstance().now();
        return LocalDateTime.ofInstant(now, TimeZoneUtils.localTimeZone());
    }

    public static LocalDateTime toLocal(Instant instant) {
        return LocalDateTime.ofInstant(instant, TimeZoneUtils.localTimeZone());
    }

    public static LocalDate toLocalDate(Instant instant) {
        return toLocal(instant).toLocalDate();
    }

    public static LocalDateTime toLocalStartOfDay(Instant instant) {
        return toLocal(instant).toLocalDate().atTime(START_OF_DAY);
    }

    public static LocalDateTime toLocalStartOfDay(LocalDate date) {
        return date.atTime(START_OF_DAY);
    }

    public static LocalDateTime toLocalEndOfDay(LocalDate date) {
        return date.atTime(END_OF_DAY);
    }

    public static LocalDateTime toLocalEndOfDay(Instant instant) {
        return toLocal(instant).toLocalDate().atTime(END_OF_DAY);
    }

    public static Instant toInstant(LocalDateTime dateTime) {
        return dateTime.toInstant(TimeZoneUtils.localTimeZone());
    }

    public static Instant toInstant(LocalDate date) {
        return date.atStartOfDay().atZone(TimeZoneUtils.localTimeZone()).toInstant();
    }

    public static long getSecDiff(Instant endTime, Instant startTime) {
        return Math.subtractExact(endTime.getEpochSecond(), startTime.getEpochSecond());
    }

    public static LocalDate getMondayOfCurrentWeek() {
        return today().with(DayOfWeek.MONDAY);
    }

    public static LocalDate getSundayOfCurrentWeek() {
        return today().with(DayOfWeek.SUNDAY);
    }

    public static LocalTime toStartOfHour(LocalTime time) {
        return time.withMinute(0).withSecond(0).truncatedTo(ChronoUnit.SECONDS);
    }

    public static LocalTime toEndOfHour(LocalTime time) {
        return time.withMinute(59).withSecond(59).truncatedTo(ChronoUnit.SECONDS);
    }

    /**
     * 将set小时集合转为连续小时区间的离散时间段
     *
     * @param hours 离散小时
     * @return 连续小时区间的离散时间段
     */
    public List<BaseTime> convert(Set<Integer> hours) {
        List<BaseTime> baseTimeList = new ArrayList<>();
        if (CollectionUtils.isEmpty(hours)) {
            baseTimeList.add(
                    BaseTime.of(LocalTime.of(0, 0, 0),
                            LocalTime.of(23, 59, 59)));
            return baseTimeList;
        }

        SortedSet<Integer> sortedHours = ImmutableSortedSet.copyOf(hours);

        int start = sortedHours.first();
        int end = sortedHours.first();

        for (Integer hour : sortedHours) {
            if (hour - end > 1) {
                baseTimeList.add(buildTime(start, end));
                start = hour;
            }
            end = hour;
        }
        baseTimeList.add(buildTime(start, end));

        return baseTimeList;
    }

    private BaseTime buildTime(int start, int end) {
        LocalTime startHour = LocalTime.of(start, 0, 0);
        LocalTime endHour = LocalTime.of(end, 59, 59);
        return BaseTime.of(startHour, endHour);
    }
}
