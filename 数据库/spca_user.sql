/*
Navicat MySQL Data Transfer

Source Server         : mysql-124
Source Server Version : 80021
Source Host           : 192.168.1.124:3306
Source Database       : spca_user

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2021-01-27 11:15:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_bonus_event_log
-- ----------------------------
DROP TABLE IF EXISTS `t_bonus_event_log`;
CREATE TABLE `t_bonus_event_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `value` varchar(255) NOT NULL,
  `event` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_bonus_event_log
-- ----------------------------
INSERT INTO `t_bonus_event_log` VALUES ('21', '1', '50', 'share-content', '投稿加积分', '2020-12-07 22:59:41');
INSERT INTO `t_bonus_event_log` VALUES ('22', '1', '50', 'share-content', '投稿加积分', '2020-12-07 23:07:34');
INSERT INTO `t_bonus_event_log` VALUES ('23', '1', '50', 'share-content', '投稿加积分', '2020-12-07 23:10:27');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `wx_id` varchar(64) DEFAULT NULL,
  `wx_nickname` varchar(255) DEFAULT NULL,
  `roles` varchar(100) DEFAULT NULL,
  `avatar_url` varchar(128) DEFAULT NULL,
  `bonus` int DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', 'xxx-wx1', 'xxx-wx-nickname', 'user', '123abc.jpg', '150', '2020-12-07 22:58:13', '2020-12-07 22:58:13');
INSERT INTO `t_user` VALUES ('2', 'xxx-wx2', 'xxx-wx-nickname', 'admin', '123abc.jpg', '0', '2020-12-03 23:48:37', '2020-12-03 23:48:37');
INSERT INTO `t_user` VALUES ('3', 'xxx-wx3', 'xxx-wx-nickname', 'editor', '123abc.jpg', '0', '2020-12-03 23:48:38', '2020-12-03 23:48:38');
INSERT INTO `t_user` VALUES ('4', 'xxx-wx4', 'xxx-wx-nickname', 'user', '123abc.jpg', '0', '2020-12-03 23:48:39', '2020-12-03 23:48:39');
INSERT INTO `t_user` VALUES ('5', 'xxx-wx5', 'xxx-wx-nickname', 'user', '123abc.jpg', '0', '2020-12-03 23:48:40', '2020-12-03 23:48:40');
INSERT INTO `t_user` VALUES ('6', 'xxx-wx6', 'xxx-wx-nickname', 'user', '123abc.jpg', '0', '2020-12-03 23:48:41', '2020-12-03 23:48:41');
