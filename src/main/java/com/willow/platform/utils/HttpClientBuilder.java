/**
 * 版权声明：软件公司 版权所有 违者必究 2011
 * 日    期：11-4-27
 */
package com.willow.platform.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 陈文炎
 * @version 1.0
 *          功能说明：http请求工具类
 */
public class HttpClientBuilder {

    private String url;
    private HttpResponse httpResponse;
    private Map<String, String> params = new HashMap<String, String>();

    private HttpClient httpClient = new DefaultHttpClient();
    private HttpContext httpContext = new BasicHttpContext();

    public HttpClientBuilder(String url) {
        this.url = url;
    }

    public static HttpClientBuilder setUrl(String url) {
        return new HttpClientBuilder(url);
    }

    public HttpClientBuilder addParam(String paramName, String paramValue) {
        params.put(paramName, paramValue);
        return this;
    }

    public String getResult() {
        InputStream is = this.getInputStream();
        if (is != null) {
            try {
                return IOUtils.toString(is);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public HttpClientBuilder doGet() {
        String paramSplit = "";
        List<String> paramList = new ArrayList<String>();
        if (!params.isEmpty()) {
            for (String key : params.keySet()) {
                paramList.add(key + "=" + params.get(key));
            }
            paramSplit = "?";
        }
        HttpGet httpGet = new HttpGet(url + paramSplit + StringUtils.join(paramList, "&"));
        try {
            this.httpResponse = httpClient.execute(httpGet, httpContext);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public HttpClientBuilder doPost() {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        if (!params.isEmpty()) {
            for (String key : params.keySet()) {
                parameters.add(new BasicNameValuePair(key, params.get(key)));
            }
        }
        UrlEncodedFormEntity sendEntity = null;
        try {
            sendEntity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);

            httpPost.setEntity(sendEntity);
           this.httpResponse = httpClient.execute(httpPost, httpContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public InputStream getInputStream() {
        if(this.httpResponse==null){
            return null;
        }
        if (org.apache.http.HttpStatus.SC_OK == this.httpResponse.getStatusLine().getStatusCode()) {
            HttpEntity httpEntity = this.httpResponse.getEntity();
            try {
                return httpEntity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getContent() {
        try {
            InputStream is = this.getInputStream();
            byte[] b = IOUtils.toByteArray(is);
            return new String(ZipUtil.unZipString(b), "GBK");
        } catch (Throwable e) {
            return "";
        }
    }
}


