// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import static org.apache.commons.lang3.exception.ExceptionUtils.rethrow;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.experimental.UtilityClass;

/**
 * XmlUtils
 *
 * @author Zhang Hang(zhanghang@focusmedia.cn)
 */
@UtilityClass
public class XmlUtils {
    private static final ObjectWriter WRITER;
    private static final XmlMapper MAPPER;

    static {
        MAPPER = new XmlMapper();
        MAPPER.registerModule(new JavaTimeModule());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        WRITER = MAPPER.writerWithDefaultPrettyPrinter();
    }

    public static String toXml(Object o) {
        try {
            return WRITER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            return rethrow(e);
        }
    }

    public static byte[] toXmlAsBytes(Object o) {
        try {
            return MAPPER.writeValueAsBytes(o);
        } catch (JsonProcessingException e) {
            return rethrow(e);
        }
    }

    public static <T> T parse(String xml, Class<T> type) {
        try {
            return MAPPER.readValue(xml, type);
        } catch (IOException e) {
            return rethrow(e);
        }
    }

    public static <T> T parse(byte[] xml, Class<T> type) {
        try {
            return MAPPER.readValue(xml, type);
        } catch (IOException e) {
            return rethrow(e);
        }
    }
}
