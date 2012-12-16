/**
 * @(#)$CurrentFile
 * 版权声明 BOOK软件公司, 版权所有 违者必究
 *
 *<br> Copyright:  Copyright (c) 2009
 *<br> Company:BOOK软件公司
 *<br> @author 朱贤俊
 *<br> 2009-4-23
 *<br> @version 1.0
 *————————————————————————————————
 *修改记录
 *    修改者：
 *    修改时间：
 *    修改原因：
 *————————————————————————————————
 */
package com.willow.platform.core.base.web;

import com.willow.platform.core.WillowException;
import com.willow.platform.core.base.freemarker.FtlParser;
import com.willow.platform.utils.LocalStringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * <pre>
 *    所有Spring MVC控制器的基类
 * </pre>
 */
public abstract class BaseController {


    /*日志记录器*/
    /*日志记录器*/
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FtlParser ftlParser;

    /**
     * 让浏览器不缓存本页面
     *
     * @param response add by 朱贤俊 100319
     */
    protected void noCache(HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");
    }

    /**
     * 根据FreeMarker模板格式化数据模型
     *
     * @param ftlBaseName 基于classpath:ftl/下的模板基名，如你的模板位于
     *                    ftl/mail/aaa.ftl,则对应的ftpBaseName为mail/aaa
     * @param map
     * @return
     */
    public String formatContent(String ftlBaseName, Map map) {
        return ftlParser.formatContent(ftlBaseName, map);
    }


    /**
     * Method to flush a String as response.
     *
     * @param response
     * @param responseContent
     * @throws java.io.IOException
     */
    protected void flushResponse(HttpServletResponse response, String responseContent) {
        try {
            response.setCharacterEncoding("GBK");
            /*针对ajax中页面编码为GBK的情况，一定要加上以下两句*/
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/html;charset=GBK");
            PrintWriter writer = response.getWriter();
            if (LocalStringUtils.isEmpty(responseContent)) {
                writer.write("");
            } else {
                writer.write(responseContent);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new WillowException(e);
        }
    }

    /**
     * Method to return data as response.
     *
     * @param response
     * @param request
     * @param responseContent
     * @throws java.io.IOException
     */
    protected void returnData(HttpServletRequest request, HttpServletResponse response, String responseContent) {
        try {
            String callback = request.getParameter("callback");
            if (LocalStringUtils.isNotBlank(callback)) {
                StringBuilder builder = new StringBuilder(callback);
                builder.append("(").append(responseContent).append(")");
                responseContent = builder.toString();
            }
            response.setCharacterEncoding("UTF-8");
            /*针对ajax中页面编码为GBK的情况，一定要加上以下两句*/
            response.setHeader("Cache-Control", "no-cache");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            if (LocalStringUtils.isEmpty(responseContent)) {
                writer.write("");
            } else {
                writer.write(responseContent);
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new WillowException(e);
        }
    }

    /**
     * Method to flush a hole page as response.
     *
     * @param response
     * @param responseContent
     * @throws java.io.IOException
     */
    protected void flushPage(HttpServletResponse response, String responseContent) {
        try {
            response.resetBuffer();
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(responseContent);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new WillowException(e);
        }
    }

    /**
     * 输出XML文档
     *
     * @param response
     * @param data
     */
    public void outputData(HttpServletResponse response, byte[] data) {
        try {
            response.setContentType("text/xml; charset=UTF-8");
            IOUtils.write(data, response.getOutputStream());
        } catch (IOException e) {
            throw new WillowException(e);
        }
    }

    /**
     * 返回ResponseEntity
     *
     * @param obj
     * @param <T>
     * @return
     */
    protected <T> ResponseEntity<T> makeResponseEntity(T obj) {
        HttpHeaders headers = new HttpHeaders();
        MediaType mt = new MediaType("text", "html", Charset.forName("UTF-8"));
        headers.setContentType(mt);
        ResponseEntity<T> responseEntity =
                new ResponseEntity<T>(obj, headers, HttpStatus.OK);
        return responseEntity;
    }


}
