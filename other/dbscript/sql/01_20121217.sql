/*网站信息配置*/
DROP TABLE IF EXISTS website;
CREATE TABLE website (
  id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '表主键',
  obj_id varchar(36) COMMENT '网站ID',
  create_time varchar(14) COMMENT '创建时间',
  update_time varchar(14) COMMENT '更新时间',
  user_id varchar(36) COMMENT '操作人',
  company_id char(36) NOT NULL COMMENT '公司ID',
  template_id char(36) NOT NULL COMMENT '公司ID',
  website_name varchar(200) DEFAULT NULL COMMENT '商城名称',
  website_title varchar(100) DEFAULT NULL COMMENT '商城标题',
  website_desc varchar(500) DEFAULT NULL COMMENT '商城描述',
  logo_image_id char(36) DEFAULT NULL COMMENT '网站Logo图片ID',
  website_keyword varchar(200) DEFAULT NULL COMMENT '商城关键字',
  service_email varchar(100) DEFAULT NULL COMMENT '客服邮件地址',
  service_tel varchar(30) DEFAULT NULL COMMENT '客服电话',
  service_fax varchar(30) DEFAULT NULL COMMENT '客服传真',
  work_time varchar(200) DEFAULT NULL COMMENT '工作时间',
  shut_down char(1) NOT NULL DEFAULT 'N' COMMENT '是否暂时关闭网站',
  website_icp varchar(100) DEFAULT NULL COMMENT 'ICP备案证书号',
  PRIMARY KEY (id)
) COMMENT='网站参数设置';

create index idx_uk_website_id on website(obj_id);

