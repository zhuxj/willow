package com.willow.codegen.util;

import org.apache.commons.lang.StringUtils;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class FileUtil {

    /**
     * 保存文件
     *
     * @param fileFullName 文件名
     * @param fileContent  文件内容
     */
    public static void saveFile(String fileFullName, String fileContent, String encoding) {
        if (StringUtils.isEmpty(fileContent) || StringUtils.isEmpty(fileFullName)) {
            return;
        }
        FileOutputStream out = null;
        OutputStreamWriter output = null;
        try {
            String encode = System.getProperty("file.encoding");
            if (StringUtils.isNotEmpty(encoding)) {
                encode = encoding;
            }
            out = new FileOutputStream(fileFullName);
            output = new OutputStreamWriter(out, encode);
            output.write(fileContent);
            output.flush();


        } catch (Exception e) {
            throw new RuntimeException("输出文件" + fileFullName + "异常", e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
            }
        }
    }

}
