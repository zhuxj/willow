delete from  sys_menu;
INSERT INTO sys_menu(obj_id,create_time,update_time,user_id,menu_name,menu_code,parent_menu_id,order_no,menu_type,node_type,url,icon)
VALUES ('68B001FA-F675-4470-888B-89F7FAFF15D7','20121216202800', '20121216202800', '1','门户管理','DOORMANAGER', 'ROOT', '1', '1','1','/admin/doorarticle/listPage', null);
INSERT INTO sys_menu(obj_id,create_time,update_time,user_id,menu_name,menu_code,parent_menu_id,order_no,menu_type,node_type,url,icon)
VALUES ('68B001FA-F675-4470-888B-89F7FAFF15E7','20121216202800', '20121216202800', '1','门户管理','DOORMANAGER1', '68B001FA-F675-4470-888B-89F7FAFF15D7', '1', '1','1',null, null);
INSERT INTO sys_menu(obj_id,create_time,update_time,user_id,menu_name,menu_code,parent_menu_id,order_no,menu_type,node_type,url,icon)
VALUES ('68B001FA-F675-4470-888B-89F7FAFF15A7','20121216202800', '20121216202800', '1','公司信息','ARTICLE', '68B001FA-F675-4470-888B-89F7FAFF15E7', '1', '1','1','/admin/doorarticle/listPage', null);
INSERT INTO sys_menu(obj_id,create_time,update_time,user_id,menu_name,menu_code,parent_menu_id,order_no,menu_type,node_type,url,icon)
VALUES ('68B001FA-F675-4470-888B-89F7FAFF15B7','20121216202800', '20121216202800', '1','商品分类','PRODUCT_CAT', '68B001FA-F675-4470-888B-89F7FAFF15E7', '1', '1','1','/admin/productcatalog/listPage', null);
INSERT INTO sys_menu(obj_id,create_time,update_time,user_id,menu_name,menu_code,parent_menu_id,order_no,menu_type,node_type,url,icon)
VALUES ('68B001FA-F675-4470-888B-89F7FAFF15C7','20121216202800', '20121216202800', '1','商品管理','PRODUCT_MANAGER', '68B001FA-F675-4470-888B-89F7FAFF15E7', '1', '1','1','/admin/product/listPage', null);



INSERT INTO sys_menu(obj_id,create_time,update_time,user_id,menu_name,menu_code,parent_menu_id,order_no,menu_type,node_type,url,icon)
VALUES ('F27160A0-DDE5-40C9-BDF5-DBC5A831E5C7','20121216202800', '20121216202800', '1','系统配置','SYSCONFIG', 'ROOT', '2', '1','1','/admin/website/showWebSiteEdit', null);
INSERT INTO sys_menu(obj_id,create_time,update_time,user_id,menu_name,menu_code,parent_menu_id,order_no,menu_type,node_type,url,icon)
VALUES ('77F23F0D-FE55-412E-8F6D-9DB9E8B97435','20121216202800', '20121216202800', '1','系统设置','SYSCONFIG1', 'F27160A0-DDE5-40C9-BDF5-DBC5A831E5C7', '1', '1','1',null, null);
INSERT INTO sys_menu(obj_id,create_time,update_time,user_id,menu_name,menu_code,parent_menu_id,order_no,menu_type,node_type,url,icon)
VALUES ('221535F9-9A90-46CD-A6D1-BE51D6BB8C20','20121216202800', '20121216202800', '1','网站设置','WEBSITE_SETTING', '77F23F0D-FE55-412E-8F6D-9DB9E8B97435', '1', '1','1','/admin/website/showWebSiteEdit', null);
INSERT INTO sys_menu(obj_id,create_time,update_time,user_id,menu_name,menu_code,parent_menu_id,order_no,menu_type,node_type,url,icon)
VALUES ('221535F9-9A90-46CD-A6D1-BE51D6BB8CE0','20121216202800', '20121216202800', '1','用户管理','USER_MANAGER', '77F23F0D-FE55-412E-8F6D-9DB9E8B97435', '2', '1','1','/admin/sysuser/listPage', null);
