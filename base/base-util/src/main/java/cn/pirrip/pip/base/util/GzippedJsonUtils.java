// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import static org.apache.commons.lang3.exception.ExceptionUtils.rethrow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.experimental.UtilityClass;

/**
 * Gzipped json parser util.
 *
 * @author gongshiwei@focusmedia.cn
 */
@UtilityClass
public class GzippedJsonUtils {
    private static final ObjectMapper MAPPER = JsonUtils.mapper();

    public static byte[] toGzippedJson(Object obj) {
        ByteArrayOutputStream byteArrayBuffer = new ByteArrayOutputStream();
        try (GZIPOutputStream compressedStream = new GZIPOutputStream(byteArrayBuffer)) {
            MAPPER.writeValue(compressedStream, obj);
            // ensure the GZIPOutputStream closed with a "try with resource" block.
            // though the jackson ObjectMapper will try to close the GZIPOutputStream by default.
            // but we can close a GZIPOutputStream twice safely.
        } catch (IOException e) {
            return rethrow(e);
        }
        return byteArrayBuffer.toByteArray();
    }

    public static <T> T parseGzippedJson(byte[] gzipJson, Class<T> type) {
        JavaType javaType = JsonUtils.mapper().getTypeFactory().constructType(type);
        return parseGzippedJson(gzipJson, javaType);
    }

    public static <T> T parseGzippedJson(byte[] gzipJson, Type type) {
        JavaType javaType = JsonUtils.mapper().getTypeFactory().constructType(type);
        return parseGzippedJson(gzipJson, javaType);
    }

    public static <T> T parseGzippedJson(byte[] gzipJson, JavaType javaType) {
        try (GZIPInputStream stream = new GZIPInputStream(new ByteArrayInputStream(gzipJson))) {
            return MAPPER.readValue(stream, javaType);
        } catch (IOException e) {
            return rethrow(e);
        }
    }
}
