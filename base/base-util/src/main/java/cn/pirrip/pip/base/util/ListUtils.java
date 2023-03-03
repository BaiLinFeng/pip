// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import lombok.experimental.UtilityClass;

/**
 * ListUtils
 *
 * @author ZhongQin
 */
@UtilityClass
public class ListUtils {

    /**
     * 剔除两个list中重复的元素，无序返回
     */
    public static <T> void kickSameObject(@Nonnull List<T> listA, @Nonnull List<T> listB) {
        Set<T> keys = new HashSet<>(listA);
        keys.addAll(listB);

        Map<T, Long> mapA = listA.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Map<T, Long> mapB = listB.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        keys.forEach(key -> {
            long countA = mapA.getOrDefault(key, 0L);
            long countB = mapB.getOrDefault(key, 0L);

            if (countA >= countB) {
                mapA.put(key, countA - countB);
                mapB.put(key, 0L);
            } else {
                mapA.put(key, 0L);
                mapB.put(key, countB - countA);
            }
        });

        rebuildListFromCountMap(listA, mapA);
        rebuildListFromCountMap(listB, mapB);
    }

    private static <T> void rebuildListFromCountMap(List<T> list, Map<T, Long> map) {
        list.clear();
        map.keySet().forEach(key -> addElementByCount(list, key, map.getOrDefault(key, 0L)));
    }

    private static <T> void addElementByCount(List<T> list, T element, long count) {
        if (count <= 0) {
            return;
        }
        for (long i = 0; i < count; i++) {
            list.add(element);
        }
    }

}
