-- 数据库初始化脚本

-- 创建数据库
CREATE DATABASE seckill;

-- 使用数据库
use seckill;

-- 创建秒杀数据库
-- 重点是出现多个TIMESTAMP的解决
CREATE  TABLE seckill(
  /*bigint
  从 -2^63 -1(-9223372036854775808) 到 2^63-1 (9223372036854775807) 的整型数据（所有数字）
  无符号的范围是0到18446744073709551615（2^64 - 1）。一位为 8 个字节*/
  `seckill_id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  `name` VARCHAR (120) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL DEFAULT  '0000-00-00 00:00:00' COMMENT '秒杀时间开始',
  `end_time` TIMESTAMP NOT NULL DEFAULT  '0000-00-00 00:00:00' COMMENT '秒杀时间结束',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  KEY inx_start_time(start_time),
  KEY inx_end_time(end_time),
  KEY inx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT = '秒杀库存表';
-- 可以支持事物的引擎只有InooDB
-- MySQL
-- Administrator建数据库的时候，表缺省是InnoDB类型。
-- MySQL默认采用的是MyISAM


-- 初始化数据
INSERT INTO
  seckill(name, number, start_time, end_time)
VALUES
  ('1000元秒杀iPhone6',100,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('500元秒杀iPad2',200,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('300元秒杀小米4',300,'2015-11-01 00:00:00','2015-11-02 00:00:00'),
  ('200元秒杀红米note',400,'2015-11-01 00:00:00','2015-11-02 00:00:00');


-- 秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE success_killed(
  `seckill_id` bigint NOT NULL COMMENT '秒杀商品ID',
  `user_phone` bigint NOT NULL COMMENT '用户手机号',
  `state` tinyint NOT NULL DEFAULT -1 COMMENT '状态标识：-1：无效 0：成功 1：已付款 2：已发货',
  `create_time` TIMESTAMP NOT NULL COMMENT '创建时间',
  -- 联合主键 表示只允许一个id，一个手机号
  PRIMARY KEY (seckill_id,user_phone),
  KEY idx_create_time(create_time)
)ENGINE = InnoDB  DEFAULT charset=utf8 COMMENT = '秒杀成功明细表';

-- 连接数据库控制台
/*mysql -uroot -p*/