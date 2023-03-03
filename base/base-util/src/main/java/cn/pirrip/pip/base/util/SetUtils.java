// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.util.Set;

import com.google.common.collect.Sets;

import lombok.experimental.UtilityClass;

/**
 * set utils
 *
 * @author ZhongQin
 */

@UtilityClass
public class SetUtils {

    public static <T> boolean isIntersect(Set<T> setA, Set<T> setB) {
        if (setA == null || setB == null) {
            return false;
        }
        Set<T> tmp = Sets.intersection(setA, setB);
        return !tmp.isEmpty();
    }

}
