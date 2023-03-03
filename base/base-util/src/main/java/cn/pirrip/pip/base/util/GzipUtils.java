// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import static org.apache.commons.lang3.exception.ExceptionUtils.rethrow;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GzipUtils
 *
 * @author Zhang Hang(zhanghang@focusmedia.cn)
 */
public class GzipUtils {
    /**
     * gzip data
     *
     * @param data input data
     * @return gzipped data
     */
    public static byte[] gzip(byte[] data) {
        ByteArrayOutputStream byteArrayBuffer = new ByteArrayOutputStream();
        try (GZIPOutputStream compressedStream = new GZIPOutputStream(byteArrayBuffer)) {
            compressedStream.write(data);
        } catch (IOException e) {
            return rethrow(e);
        }
        return byteArrayBuffer.toByteArray();
    }

    /**
     * un gzip data
     *
     * @param gzippedData gzipped data
     * @return origin data
     */
    public static byte[] unGzip(byte[] gzippedData) {
        ByteArrayOutputStream byteArrayBuffer = new ByteArrayOutputStream();
        try (GZIPInputStream stream = new GZIPInputStream(new ByteArrayInputStream(gzippedData))) {
            FileUtils.readInputStream(stream, byteArrayBuffer);
        } catch (IOException e) {
            return rethrow(e);
        }
        return byteArrayBuffer.toByteArray();
    }
}
