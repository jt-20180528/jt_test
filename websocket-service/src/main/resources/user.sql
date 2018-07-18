CREATE DATABASE `jt_test` DEFAULT CHARACTER SET utf8;

USE `jt_test`;
SET FOREIGN_KEY_CHECKS=0;

--单库分表见表脚本，t_user表t_user$1-10
CREATE TABLE `t_user1` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `lastLoginAddr` varchar(20) DEFAULT NULL,
  `lastLoginIp` varchar(20) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPasswd` varchar(40) NOT NULL,
  `logoUrl` varchar(80) DEFAULT NULL,
  `mobilePhone` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` varchar(20) DEFAULT NULL,
  `userName` varchar(20) NOT NULL,
  `userStatus` tinyint(1) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210106 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `lastLoginAddr` varchar(20) DEFAULT NULL,
  `lastLoginIp` varchar(20) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPasswd` varchar(40) NOT NULL,
  `logoUrl` varchar(80) DEFAULT NULL,
  `mobilePhone` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` varchar(20) DEFAULT NULL,
  `userName` varchar(20) NOT NULL,
  `userStatus` tinyint(1) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210106 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user3` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `lastLoginAddr` varchar(20) DEFAULT NULL,
  `lastLoginIp` varchar(20) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPasswd` varchar(40) NOT NULL,
  `logoUrl` varchar(80) DEFAULT NULL,
  `mobilePhone` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` varchar(20) DEFAULT NULL,
  `userName` varchar(20) NOT NULL,
  `userStatus` tinyint(1) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210106 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user4` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `lastLoginAddr` varchar(20) DEFAULT NULL,
  `lastLoginIp` varchar(20) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPasswd` varchar(40) NOT NULL,
  `logoUrl` varchar(80) DEFAULT NULL,
  `mobilePhone` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` varchar(20) DEFAULT NULL,
  `userName` varchar(20) NOT NULL,
  `userStatus` tinyint(1) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210106 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user5` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `lastLoginAddr` varchar(20) DEFAULT NULL,
  `lastLoginIp` varchar(20) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPasswd` varchar(40) NOT NULL,
  `logoUrl` varchar(80) DEFAULT NULL,
  `mobilePhone` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` varchar(20) DEFAULT NULL,
  `userName` varchar(20) NOT NULL,
  `userStatus` tinyint(1) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210106 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user6` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `lastLoginAddr` varchar(20) DEFAULT NULL,
  `lastLoginIp` varchar(20) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPasswd` varchar(40) NOT NULL,
  `logoUrl` varchar(80) DEFAULT NULL,
  `mobilePhone` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` varchar(20) DEFAULT NULL,
  `userName` varchar(20) NOT NULL,
  `userStatus` tinyint(1) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210106 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user7` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `lastLoginAddr` varchar(20) DEFAULT NULL,
  `lastLoginIp` varchar(20) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPasswd` varchar(40) NOT NULL,
  `logoUrl` varchar(80) DEFAULT NULL,
  `mobilePhone` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` varchar(20) DEFAULT NULL,
  `userName` varchar(20) NOT NULL,
  `userStatus` tinyint(1) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210106 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user8` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `lastLoginAddr` varchar(20) DEFAULT NULL,
  `lastLoginIp` varchar(20) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPasswd` varchar(40) NOT NULL,
  `logoUrl` varchar(80) DEFAULT NULL,
  `mobilePhone` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` varchar(20) DEFAULT NULL,
  `userName` varchar(20) NOT NULL,
  `userStatus` tinyint(1) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210106 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user9` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `lastLoginAddr` varchar(20) DEFAULT NULL,
  `lastLoginIp` varchar(20) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPasswd` varchar(40) NOT NULL,
  `logoUrl` varchar(80) DEFAULT NULL,
  `mobilePhone` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` varchar(20) DEFAULT NULL,
  `userName` varchar(20) NOT NULL,
  `userStatus` tinyint(1) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210106 DEFAULT CHARSET=utf8;

CREATE TABLE `t_user10` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(80) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  `createUser` varchar(20) DEFAULT NULL,
  `email` varchar(40) DEFAULT NULL,
  `lastLoginAddr` varchar(20) DEFAULT NULL,
  `lastLoginIp` varchar(20) DEFAULT NULL,
  `lastLoginTime` datetime DEFAULT NULL,
  `loginName` varchar(20) NOT NULL,
  `loginPasswd` varchar(40) NOT NULL,
  `logoUrl` varchar(80) DEFAULT NULL,
  `mobilePhone` int(11) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `telephone` varchar(11) DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  `updateUser` varchar(20) DEFAULT NULL,
  `userName` varchar(20) NOT NULL,
  `userStatus` tinyint(1) DEFAULT '1' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=210106 DEFAULT CHARSET=utf8;