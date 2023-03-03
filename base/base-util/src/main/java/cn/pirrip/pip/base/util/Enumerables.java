// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import static java.lang.reflect.Modifier.isFinal;
import static java.lang.reflect.Modifier.isPublic;
import static java.lang.reflect.Modifier.isStatic;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

/**
 * Utility Class for Enumerable
 *
 * @author gongshiwei@focusmedia.cn
 */
@UtilityClass
class Enumerables {
    private final Cache<Class<? extends Enumerable>, Map<String, Enumerable>> cache =
            CacheBuilder.newBuilder().build();

    @SneakyThrows(ExecutionException.class)
    <T extends Enumerable> Map<String, T> mapOf(Class<T> type) {
        @SuppressWarnings("unchecked")
        Map<String, T> map = (Map<String, T>) cache.get(type, () -> load(type));
        return map;
    }

    private static Map<String, Enumerable> load(Class<? extends Enumerable> type) {
        Map<String, Enumerable> map = new TreeMap<>();
        for (Field field : type.getDeclaredFields()) {
            Optional<Enumerable> value = getEnumValue(field, type);
            value.ifPresent(enumerable -> map.put(field.getName(), enumerable));
        }
        return Collections.unmodifiableMap(map);
    }

    private static Optional<Enumerable> getEnumValue(Field field, Class<? extends Enumerable> type) {
        int modifiers = field.getModifiers();
        if (!isFinal(modifiers) || !isStatic(modifiers) || !isPublic(modifiers)) {
            return Optional.empty();
        }
        Object value = getStaticField(field);
        if (value == null) {
            return Optional.empty();
        }
        if (!type.isInstance(value)) {
            return Optional.empty();
        }
        Enumerable item = type.cast(value);
        if (item.name().equals(field.getName())) {
            return Optional.of(item);
        } else {
            return Optional.empty();
        }
    }

    @SneakyThrows(IllegalAccessException.class)
    private Object getStaticField(Field field) {
        return field.get(null);
    }
}
