/*产品分类*/
DROP TABLE IF EXISTS t_door_product_catalog;
CREATE TABLE t_door_product_catalog (
  id int(10) unsigned NOT NULL AUTO_INCREMENT,
  obj_id varchar(36) COMMENT '表主键',
  create_time varchar(14) COMMENT '创建时间',
  update_time varchar(14) COMMENT '更新时间',
  user_id varchar(36) COMMENT '操作人',
  catalog_name varchar(50) DEFAULT NULL COMMENT '分类名称',
  catalog_name_en varchar(50) DEFAULT NULL COMMENT '分类英文名称',
  PRIMARY KEY (id)
) COMMENT='产品分类';

create index idx_uk_product_catalog_id on t_door_product_catalog(obj_id);

