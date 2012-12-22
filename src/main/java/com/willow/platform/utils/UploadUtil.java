/**
 * @(#)com.willow.util.UploadUtil
 * 2009-5-3
 * Copyright 2009
 * 软件公司, 版权所有 违者必究
 */
package com.willow.platform.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * <per>文件上传工具类</per>
 *
 * @author 朱贤俊
 * @version 1.0
 */
public class UploadUtil {


    private static final long UPLOAD_FILE_MAX_SIZE = 5 * 1024;

    // 工具路径
    private static final String UPLOAD_PATH_SOFTWARE = "/software";

    // 插件路径
    private static final String UPLOAD_PATH_APPPLUGIN = UPLOAD_PATH_SOFTWARE + "/appplugin";

    /**
     * 根据文件名获得文件的类型，不是获得文件底层的物理类型。
     *
     * @param fileName
     * @return
     */
    public static String getFileType(String fileName) {
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }

    /**
     * 删除指定的文件或者文件夹
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists() && file.isDirectory()) {
            File[] subFileArray = file.listFiles();
            for (File subFile : subFileArray) {
                deleteFile(subFile);
            }
        }

        FileUtils.deleteQuietly(file);
    }


    /**
     * 删除当前文件
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        FileUtils.deleteQuietly(file);
    }

    /**
     * 根据ID获取插件在服务器上的位置
     *
     * @param id 插件ID
     * @return
     */
    public static String getAppPluginUrl(String id) {
        return UPLOAD_PATH_APPPLUGIN + "/" + id;
    }

}