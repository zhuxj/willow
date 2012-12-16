/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-12
 */
package com.willow.platform.core.base.domian;

import java.io.Serializable;

/**
 * <pre>
 *  领域对象基类
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class BaseObject implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键ID
    private String objId;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
    //创建者
    private String userId;

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
