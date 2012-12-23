/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-23
 */
package com.willow.door.front.templates;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 频道对象
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class Channel {
    public static final List<Channel> channels = new ArrayList<Channel>();

    static {
        channels.add(new Channel("网站首页", "HOME", "home", "/"));
        channels.add(new Channel("关于公司", "ABOUT", "home", "/about"));
        channels.add(new Channel("产品展示", "PRODUCT", "product", "/product"));
        channels.add(new Channel("质检中心", "QUALITY", "quality", "/quality"));
        channels.add(new Channel("下载中心", "DOWNLOAD", "download", "/download"));
        channels.add(new Channel("OEM/ODM合作", "OEM/ODM", "oem", "/oem"));
        channels.add(new Channel("在线订购", "ORDER", "order", "/order"));
    }

    private String channelName;
    private String channelNameEn;
    private String channelCode;
    private String channelUrl;

    public Channel() {
    }

    public Channel(String channelName, String channelNameEn, String channelCode, String channelUrl) {
        this.channelName = channelName;
        this.channelNameEn = channelNameEn;
        this.channelCode = channelCode;
        this.channelUrl = channelUrl;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelNameEn() {
        return channelNameEn;
    }

    public void setChannelNameEn(String channelNameEn) {
        this.channelNameEn = channelNameEn;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }
}
