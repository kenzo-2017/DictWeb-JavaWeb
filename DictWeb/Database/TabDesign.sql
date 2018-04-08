-- 创建db_9griddiary数据库
CREATE DATABASE IF NOT EXISTS db_9griddiary CHARACTER SET utf8;
USE db_9griddiary;

-- 创建tb_user表，用于存储用户的注册信息
DROP TABLE IF EXISTS `tb_diary`;
CREATE TABLE `tb_diary` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(60) NOT NULL,
  `address` VARCHAR(50) NOT NULL COMMENT '日记保存的地址',
  `writeTime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  `userid` INT(10) UNSIGNED NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- 插入数据至tb_diary
INSERT INTO `tb_diary` (`id`, `title`, `address`, `writeTime`, `userid`) VALUES
  (6, '请在此输入标题', -1625618782951844312, '17-05-26 09:40:10', 1),
  (8, '请在此输入标题', 5039247624120215442, '2017-05-27 10:26:47', 1),
  (9, '心情不错', 241344336121425196, '2017-05-28 14:00:43', 1),
  (10, '无题', 2030326276105596531, '2017-05-28 14:03:08', 1),
  (11, '无尽的牵挂', -2201304326449572193, '2017-05-28 14:09:18', 2),
  (14, '心情很好', 7076008621496558673, '2017-06-09 16:31:07', 1);

-- 创建tb_user表，用于存储日记的相关信息
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `pwd` VARCHAR(50) NOT NULL COMMENT '密码',
  `email` VARCHAR(100) DEFAULT NULL COMMENT 'E-mail',
  `question` VARCHAR(45) DEFAULT NULL COMMENT '密码提示问题',
  `answer` VARCHAR(45) DEFAULT NULL COMMENT '提示密码答案',
  `city` VARCHAR(30) DEFAULT NULL COMMENT '所在地',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 插入数据至tb_user
INSERT INTO `tb_user` (`id`, `username`, `pwd`, `email`, `question`, `answer`, `city`) VALUES
  (1, 'mr', 'mrsoft', NULL, '', '', ''),
  (2, 'wgh', '111', NULL, '我的工作单位', '广东', '佛山'),
  (3, 'qq', 'w123456', 'wgh@sohu.com', '6', '6', '佛山'),
  (4, 'h', 'hhhhhh', 'wgh717@sohu.com', '', '', '北京'),
  (5, 'w', 'wwwwww', 'www@sina.com', '', '', '北京'),
  (6, 'qiqi', 'qq123456', 'wgh8007@163.com', '我的工作单位', '广东', '广州'),
  (7, 'kk', 'kkkkkk', 'wgh@gggg.com', '', '', '长春'),
  (8, '888', 'wwwwww', 'www@si.com', '', '', '葫芦岛');