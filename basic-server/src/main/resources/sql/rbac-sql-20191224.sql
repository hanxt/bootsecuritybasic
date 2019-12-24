-- --------------------------------------------------------
-- 主机:                           192.168.161.3
-- 服务器版本:                        5.7.26 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- 导出  表 devicedb.sys_menu 结构
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_pid` int(11) NOT NULL COMMENT '父菜单ID',
  `menu_pids` varchar(64) NOT NULL COMMENT '当前菜单所有父菜单',
  `is_leaf` tinyint(4) NOT NULL COMMENT '0:不是叶子节点，1:是叶子节点',
  `menu_name` varchar(16) NOT NULL COMMENT '菜单名称',
  `url` varchar(64) DEFAULT NULL COMMENT '跳转URL',
  `icon` varchar(45) DEFAULT NULL,
  `icon_color` varchar(16) DEFAULT NULL,
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `level` tinyint(4) NOT NULL COMMENT '菜单层级',
  `status` tinyint(4) NOT NULL COMMENT '0:启用,1:禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统菜单表';

-- 正在导出表  devicedb.sys_menu 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` (`id`, `menu_pid`, `menu_pids`, `is_leaf`, `menu_name`, `url`, `icon`, `icon_color`, `sort`, `level`, `status`) VALUES
	(1, 0, '0', 0, '系统管理', NULL, NULL, NULL, 1, 1, 0),
	(2, 1, '1', 1, '用户管理', '/sys_user', NULL, NULL, 1, 2, 0),
	(3, 1, '1', 1, '日志管理', '/sys_log', NULL, NULL, 2, 2, 0),
	(4, 1, '1', 1, '业务一', '/biz1', NULL, NULL, 3, 2, 0),
	(5, 1, '1', 1, '业务二', '/biz2', NULL, NULL, 4, 2, 0);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;


-- 导出  表 devicedb.sys_org 结构
CREATE TABLE IF NOT EXISTS `sys_org` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `org_pid` int(11) NOT NULL COMMENT '上级组织编码',
  `org_pids` varchar(64) NOT NULL COMMENT '所有的父节点id',
  `is_leaf` tinyint(4) NOT NULL COMMENT '0:不是叶子节点，1:是叶子节点',
  `org_name` varchar(32) NOT NULL COMMENT '组织名',
  `address` varchar(64) DEFAULT NULL COMMENT '地址',
  `phone` varchar(13) DEFAULT NULL COMMENT '电话',
  `email` varchar(32) DEFAULT NULL COMMENT '邮件',
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  `level` tinyint(4) NOT NULL COMMENT '组织层级',
  `status` tinyint(4) NOT NULL COMMENT '0:启用,1:禁用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统组织结构表';

-- 正在导出表  devicedb.sys_org 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `sys_org` DISABLE KEYS */;
INSERT INTO `sys_org` (`id`, `org_pid`, `org_pids`, `is_leaf`, `org_name`, `address`, `phone`, `email`, `sort`, `level`, `status`) VALUES
	(1, 0, '0', 0, '总部', NULL, NULL, NULL, 1, 1, 0),
	(2, 1, '1', 0, '研发部', NULL, NULL, NULL, 1, 2, 0),
	(3, 2, '1,2', 1, '研发一部', NULL, NULL, NULL, 1, 3, 0),
	(4, 2, '1,2', 1, '研发二部', NULL, NULL, NULL, 2, 3, 0);
/*!40000 ALTER TABLE `sys_org` ENABLE KEYS */;


-- 导出  表 devicedb.sys_role 结构
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL DEFAULT '0' COMMENT '角色名称(汉字)',
  `role_desc` varchar(128) NOT NULL DEFAULT '0' COMMENT '角色描述',
  `role_code` varchar(32) NOT NULL DEFAULT '0' COMMENT '角色的英文code.如：ADMIN',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '角色顺序',
  `status` int(11) DEFAULT NULL COMMENT '0表示可用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '角色的创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统角色表';

-- 正在导出表  devicedb.sys_role 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` (`id`, `role_name`, `role_desc`, `role_code`, `sort`, `status`, `create_time`) VALUES
	(1, '管理员', '管理员', 'admin', 1, 0, '2019-12-23 22:56:48'),
	(2, '普通用户', '普通用户', 'common', 2, 0, '2019-12-23 22:57:22');
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;


-- 导出  表 devicedb.sys_role_menu 结构
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色id',
  `menu_id` int(11) NOT NULL DEFAULT '0' COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='角色权限关系表';

-- 正在导出表  devicedb.sys_role_menu 的数据：~4 rows (大约)
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` (`id`, `role_id`, `menu_id`) VALUES
	(1, 1, 2),
	(2, 1, 3),
	(3, 2, 4),
	(4, 2, 5);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;


-- 导出  表 devicedb.sys_user 结构
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL DEFAULT '0' COMMENT '用户名',
  `password` varchar(64) NOT NULL DEFAULT '0' COMMENT '密码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `org_id` int(11) NOT NULL COMMENT '组织id',
  `enabled` int(11) DEFAULT NULL COMMENT '0无效用户，1是有效用户',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) DEFAULT NULL COMMENT 'email',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

-- 正在导出表  devicedb.sys_user 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` (`id`, `username`, `password`, `create_time`, `org_id`, `enabled`, `phone`, `email`) VALUES
	(1, 'yanfa1', '$2a$10$xPNoI0sBxOY6Y5Nj1bF6iO6OePqJ8tAJUsD5x5wh6G1BPphhSLcae', '2019-12-24 01:10:14', 3, 1, NULL, NULL),
	(2, 'admin', '$2a$10$xPNoI0sBxOY6Y5Nj1bF6iO6OePqJ8tAJUsD5x5wh6G1BPphhSLcae', '2019-12-24 01:10:18', 1, 1, NULL, NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;


-- 导出  表 devicedb.sys_user_role 结构
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL DEFAULT '0' COMMENT '角色自增id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户自增id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';

-- 正在导出表  devicedb.sys_user_role 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` (`id`, `role_id`, `user_id`) VALUES
	(1, 2, 1),
	(2, 1, 2);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
