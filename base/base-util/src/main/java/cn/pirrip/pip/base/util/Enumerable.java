// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.util.Map;
import java.util.Set;

/**
 * enum interface
 *
 * @author gongshiwei@focusmedia.cn
 */
public interface Enumerable {
    static <T extends Enumerable> T of(Class<T> type, String name) {
        Map<String, T> map = Enumerables.mapOf(type);
        if (map.containsKey(name)) {
            return map.get(name);
        } else {
            throw new IllegalArgumentException(name);
        }

    }

    static <T extends Enumerable> Set<String> names(Class<T> type) {
        Map<String, T> map = Enumerables.mapOf(type);
        return map.keySet();
    }

    String name();
}
