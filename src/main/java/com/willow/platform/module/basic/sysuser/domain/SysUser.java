/**
* 版权声明：贤俊工作室 版权所有 违者必究
* 日    期：2012-12-17
*/
package com.willow.platform.module.basic.sysuser.domain;

import com.willow.platform.core.base.domian.BaseObject;

/**
 * <pre>
 *   系统用户领域对象
 * </pre>
 * @author 朱贤俊
 * @version 1.0
 */
public class SysUser extends BaseObject{
    //用户ID
    private String objId;
    //创建时间
    private String createTime;
    //更新时间
    private String updateTime;
    //操作人
    private String userId;
    //用户名称
    private String userName;
    //用户密码
    private String password;
    //正式姓名
    private String realName;
    //排序号
    private Integer orderNo;
    //部门
    private String deptId;
    //邮箱
    private String email;
    //电话
    private String telphone;
    //手机
    private String mobile;
    //管理员
    private String adminState;
    //登陆IP
    private String lastLogonIp;
    //会话ID
    private String sessionId;
    //备注
    private String remark;

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
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public Integer getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
    public String getDeptId() {
        return deptId;
    }
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelphone() {
        return telphone;
    }
    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getAdminState() {
        return adminState;
    }
    public void setAdminState(String adminState) {
        this.adminState = adminState;
    }
    public String getLastLogonIp() {
        return lastLogonIp;
    }
    public void setLastLogonIp(String lastLogonIp) {
        this.lastLogonIp = lastLogonIp;
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
