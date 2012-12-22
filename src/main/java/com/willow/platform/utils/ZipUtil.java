package com.willow.platform.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


public class ZipUtil {

    /**
     * 压缩字符串
     *
     * @param str
     * @return
     */
    public static byte[] zipString(byte[] src) {

        try {
            //建立字节数组输出流
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // 建立gzip压缩输出流
            GZIPOutputStream gzout = new GZIPOutputStream(out);
            gzout.write(src);
            gzout.close();
            out.close();

            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解压缩字符串
     *
     * @param str
     * @return
     */
    public static byte[] unZipString(byte[] b) {
        if (b.length == 0) {
            return new byte[0];
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = new ByteArrayInputStream(b);
            GZIPInputStream gunzip = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gunzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
