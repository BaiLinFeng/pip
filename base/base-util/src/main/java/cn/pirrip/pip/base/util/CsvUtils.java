// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import org.apache.commons.io.IOUtils;

import com.opencsv.CSVWriter;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

/**
 * csv utils
 *
 * @author gongshiwei@focusmedia.cn
 */
@UtilityClass
public class CsvUtils {

    static final byte[] UTF8_BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    public static byte[] generateCsvWithUtf8Bom(Object[][] data) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeCsvWithUtf8Bom(data, outputStream);
        return outputStream.toByteArray();
    }

    @SneakyThrows(IOException.class)
    private static void writeCsvWithUtf8Bom(Object[][] data, ByteArrayOutputStream outputStream) {
        IOUtils.write(UTF8_BOM, outputStream);
        writeCsv(data, outputStream, StandardCharsets.UTF_8);
    }

    @SneakyThrows(IOException.class)
    public static void writeCsv(Object[][] data, ByteArrayOutputStream outputStream, Charset cs) {
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream, cs))) {
            for (Object[] lineObjs : data) {
                String[] line = Stream.of(lineObjs).map(CsvUtils::valueToString).toArray(String[]::new);
                writer.writeNext(line, false);
            }
        }
    }

    private static String valueToString(Object value) {
        if (value == null) {
            return "-";
        }
        return value.toString();
    }
}
