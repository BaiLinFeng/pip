// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import static org.apache.commons.lang3.exception.ExceptionUtils.rethrow;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.experimental.UtilityClass;

/**
 * Json parser util.
 *
 * @author gongshw1992@gmail.com
 */
@UtilityClass
public class JsonUtils {

    private static final ObjectMapper MAPPER = newMapper();


    /**
     * 基于默认配置, 创建一个新{@link ObjectMapper},
     * 随后可以定制化这个新{@link ObjectMapper}.
     */
    public static ObjectMapper newMapper() {
        ObjectMapper mapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        mapper.setDateFormat(dateFormat);
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        mapper.registerModule(new JavaTimeModule());
        mapper.registerModule(new Jdk8Module());
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);
        return mapper;
    }

    /**
     * 获取默认{@link ObjectMapper}.
     * 直接使用默认{@link ObjectMapper}时需要小心,
     * 因为{@link ObjectMapper}类是可变的,
     * 对默认 ObjectMapper 的改动会影响所有默认ObjectMapper的依赖方.
     * 如果需要在当前上下文定制化{@link ObjectMapper},
     * 建议使用{@link #newMapper()}方法创建一个新的{@link ObjectMapper}.
     *
     * @see #newMapper()
     */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

    public static String toJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return rethrow(e);
        }
    }

    public static byte[] toJsonAsBytes(Object obj) {
        try {
            return MAPPER.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            return rethrow(e);
        }
    }

    public static <T> T parse(String json, TypeReference<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            return rethrow(e);
        }
    }

    public static <T> T parse(String json, Type type) {
        JavaType javaType = MAPPER.getTypeFactory().constructType(type);
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            return rethrow(e);
        }
    }

    public static <T> T parse(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            return rethrow(e);
        }
    }

    public static <T> T parse(String json, JavaType type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            return rethrow(e);
        }
    }

    public static <T> T parse(byte[] json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (IOException e) {
            return rethrow(e);
        }
    }

    public static <T> T parse(byte[] bytes, JavaType type) {
        try {
            return MAPPER.readValue(bytes, type);
        } catch (IOException e) {
            return rethrow(e);
        }
    }

    public static Object parse(byte[] bytes, Type type) {
        JavaType javaType = MAPPER.getTypeFactory().constructType(type);
        try {
            return MAPPER.readValue(bytes, javaType);
        } catch (IOException e) {
            return rethrow(e);
        }
    }

    public static <T> List<T> parseList(String json, Class<T> type) {
        return parseCollection(json, List.class, type);
    }

    public static <T> Set<T> parseSet(String json, Class<T> type) {
        return parseCollection(json, Set.class, type);
    }

    private static <V, C extends Collection<?>, T> V parseCollection(
            String json, Class<C> collectionType, Class<T> elementType) {
        try {
            TypeFactory typeFactory = MAPPER.getTypeFactory();
            CollectionType javaType = typeFactory.constructCollectionType(collectionType, elementType);
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            return rethrow(e);
        }
    }
}
