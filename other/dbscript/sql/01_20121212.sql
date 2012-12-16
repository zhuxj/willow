-- 系统菜单信息表
DROP TABLE IF EXISTS sys_menu;
create table sys_menu(
   id int UNSIGNED AUTO_INCREMENT ,
   obj_id varchar(36) COMMENT '菜单ID',
   create_time varchar(14) COMMENT '创建时间',
   update_time varchar(14) COMMENT '更新时间',
   user_id varchar(36) COMMENT '操作人',
   menu_name varchar(36) COMMENT '菜单名称',
   menu_code varchar(36) COMMENT '菜单编码',
   parent_menu_id varchar(36) COMMENT '父菜单',
   order_no int(20) COMMENT '排序号',
   menu_type varchar(10) COMMENT '菜单类型',
   node_type varchar(36) COMMENT '节点类型',
   url varchar(36) COMMENT '菜单链接',
   icon varchar(36) COMMENT '节点图标',
   PRIMARY KEY (id)
 )comment '系统菜单';
 create unique index idx_sys_menu_id on sys_menu(obj_id);

 -- 系统用户信息表
DROP TABLE IF EXISTS sys_user;
create table sys_user(
   id int UNSIGNED AUTO_INCREMENT ,
   obj_id varchar(36) COMMENT '用户ID',
   create_time varchar(14) COMMENT '创建时间',
   update_time varchar(14) COMMENT '更新时间',
   user_id varchar(36) COMMENT '操作人',
   user_name varchar(50) COMMENT '用户名称',
   password varchar(50) COMMENT '用户密码',
   real_name varchar(50) COMMENT '正式姓名',
   order_no int(20) COMMENT '排序号',
   dept_id varchar(36) COMMENT '部门',
   email varchar(100) COMMENT '邮箱',
   telphone varchar(36) COMMENT '电话',
   mobile varchar(36) COMMENT '手机',
   admin_state char(5) COMMENT '管理员',
   last_logon_ip varchar(30) COMMENT '登陆IP',
   session_id  varchar(36) COMMENT '会话ID',
   remark varchar(500) COMMENT '备注',
   PRIMARY KEY (id)
 )comment '系统用户';
 create unique index idx_sys_user_id on sys_user(obj_id);




