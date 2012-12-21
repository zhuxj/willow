/*产品分类*/
DROP TABLE IF EXISTS t_door_product_catalog;
CREATE TABLE t_door_product_catalog (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  obj_id varchar(36) COMMENT '表主键',
  create_time varchar(14) COMMENT '创建时间',
  update_time varchar(14) COMMENT '更新时间',
  user_id varchar(36) COMMENT '操作人',
  catalog_name varchar(50) NOT NULL COMMENT '分类名称',
  catalog_name_en varchar(50) NOT NULL COMMENT '分类英文名称',
  PRIMARY KEY (id)
) COMMENT='产品分类';

create index idx_uk_product_catalog_id on t_door_product_catalog(obj_id);

/*产品信息*/
DROP TABLE IF EXISTS t_door_product;
CREATE TABLE t_door_product (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  obj_id varchar(36) COMMENT '表主键',
  create_time varchar(14) COMMENT '创建时间',
  update_time varchar(14) COMMENT '更新时间',
  user_id varchar(36) COMMENT '操作人',
  product_sn varchar(30) comment '产品编号',
  product_name varchar(50) comment '产品中文名称',
  product_name_en varchar(50) comment '产品英文名称',
  product_norms varchar(50) comment '产品中文规格',
  product_norms_en varchar(50) comment '产品英文规格',
  product_version varchar(50) comment '产品型号',
  catalog_id varchar(36) COMMENT '产品分类',
  product_desc text comment '产品中文详情',
  product_desc_en text comment '产品英文详情',
  product_image varchar(36) comment '产品图片',
  browse_time int(10)  comment '浏览次数',
  PRIMARY KEY (id)
) COMMENT='产品';
create index idx_uk_product_id on t_door_product(obj_id);

/*文章信息*/
DROP TABLE IF EXISTS t_door_article;
CREATE TABLE t_door_article (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  obj_id varchar(36) COMMENT '表主键',
  create_time varchar(14) COMMENT '创建时间',
  update_time varchar(14) COMMENT '更新时间',
  user_id varchar(36) COMMENT '操作人',
  article_code varchar(50) DEFAULT NULL COMMENT '文章编码',
  article_title varchar(50) DEFAULT NULL COMMENT '文章中文标题',
  article_title_sn varchar(50) DEFAULT NULL COMMENT '文章英文标题',
  article_content text DEFAULT NULL COMMENT '文章中文内容',
  article_content_en text DEFAULT NULL COMMENT '文章英文内容',
  order_no int DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (id)
) COMMENT='文章信息';

create index idx_uk_article_id on t_door_article(obj_id);








