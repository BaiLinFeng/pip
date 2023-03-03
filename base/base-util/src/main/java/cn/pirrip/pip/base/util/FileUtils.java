// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import static org.apache.commons.compress.archivers.tar.TarArchiveOutputStream.LONGFILE_POSIX;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import com.google.common.base.Preconditions;

import lombok.experimental.UtilityClass;

/**
 * File utils
 *
 * @author Zhang Hang(zhanghang@focusmedia.cn)
 */
@UtilityClass
public class FileUtils {
    private static final Charset CHARSET_GBK = Charset.forName("GBK");
    private static final int BUFFER_SIZE = 1024;

    /**
     * calculate vid of filename
     *
     * @param filename filename
     * @return vid of filename
     */
    public static String calculateFileVid(String filename) {
        Preconditions.checkNotNull(filename);

        byte[] bytes = filename.getBytes(CHARSET_GBK);
        return DigestUtils.md5Hex(bytes);
    }

    /**
     * compress input map into tgz, leave OutputStream open after return
     *
     * @param inputMap key:path -> value:input byte array
     * @param output   OutputStream
     * @throws IOException IOException
     */
    public static void tgz(Map<String, byte[]> inputMap, OutputStream output) throws IOException {
        Preconditions.checkNotNull(inputMap);
        Preconditions.checkState(!inputMap.isEmpty(), "inputMap must have at least one item");
        Preconditions.checkNotNull(output);

        try (GzipCompressorOutputStream gzo = new GzipCompressorOutputStream(output);
             TarArchiveOutputStream to = new TarArchiveOutputStream(gzo)) {
            to.setLongFileMode(LONGFILE_POSIX);
            for (Map.Entry<String, byte[]> entry : inputMap.entrySet()) {
                addToTar(entry.getKey(), entry.getValue(), to);
            }
        }
    }

    /**
     * add inputStream to tar, leave InputStream and TarArchiveOutputStream open after return
     *
     * @param path  path
     * @param input inputStream
     * @param to    TarArchiveOutputStream
     * @throws IOException IOException
     */
    public static void addToTar(String path, InputStream input, long inputSize, TarArchiveOutputStream to)
            throws IOException {
        Preconditions.checkNotNull(path);
        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(to);

        to.setLongFileMode(LONGFILE_POSIX);

        TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(path);
        tarArchiveEntry.setSize(inputSize);
        to.putArchiveEntry(tarArchiveEntry);
        readInputStream(input, to);
        to.closeArchiveEntry();
    }

    /**
     * add input to tar, leave TarArchiveOutputStream open after return
     *
     * @param path  path
     * @param input input
     * @param to    TarArchiveOutputStream
     * @throws IOException IOException
     */
    public static void addToTar(String path, byte[] input, TarArchiveOutputStream to) throws IOException {
        Preconditions.checkNotNull(input);

        try (ByteArrayInputStream bi = new ByteArrayInputStream(input)) {
            addToTar(path, bi, input.length, to);
        }
    }

    /**
     * zip
     *
     * @param map file content bytes map
     * @return byte[] data array
     * @throws IOException IOException
     */
    public static byte[] zip(Map<String, byte[]> map) throws IOException {
        Preconditions.checkNotNull(map);
        Preconditions.checkState(!map.isEmpty(), "map must have at least one item");

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (ZipOutputStream zip = new ZipOutputStream(out)) {
                Set<Map.Entry<String, byte[]>> entries = map.entrySet();
                for (Map.Entry<String, byte[]> entry : entries) {
                    zip.putNextEntry(new ZipEntry(entry.getKey()));
                    zip.write(entry.getValue());
                }
            }
            return out.toByteArray();
        }
    }


    /**
     * unzip
     *
     * @param input inputStream of file content
     * @return result map
     * @throws IOException IOException
     */
    public static Map<String, byte[]> unzip(InputStream input) throws IOException {
        Preconditions.checkNotNull(input);

        final Map<String, byte[]> map = new HashMap<>();
        try (ZipInputStream zip = new ZipInputStream(input)) {
            ZipEntry ze;
            while ((ze = zip.getNextEntry()) != null) {
                if (!ze.isDirectory()) {
                    map.put(ze.getName(), readInputStream(zip));
                }
            }
        }
        return map;
    }

    /**
     * unzip
     *
     * @param input input data
     * @return result map
     * @throws IOException IOException
     */
    public static Map<String, byte[]> unzip(byte[] input) throws IOException {
        try (ByteArrayInputStream bi = new ByteArrayInputStream(input)) {
            return unzip(bi);
        }
    }

    public static void readInputStream(InputStream input, OutputStream output) throws IOException {
        int read;
        byte[] buffer = new byte[BUFFER_SIZE];
        while ((read = input.read(buffer)) > 0) {
            output.write(buffer, 0, read);
        }
    }

    private static byte[] readInputStream(InputStream input) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            readInputStream(input, out);
            return out.toByteArray();
        }
    }

    public static byte[] readBytesFromPath(Path file) throws IOException {
        return readInputStream(Files.newInputStream(file));
    }
}
