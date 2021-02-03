/*
Navicat MySQL Data Transfer

Source Server         : mysql-124
Source Server Version : 80021
Source Host           : 192.168.1.124:3306
Source Database       : spca_content

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2021-01-27 11:14:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for rocketmq_transaction_log
-- ----------------------------
DROP TABLE IF EXISTS `rocketmq_transaction_log`;
CREATE TABLE `rocketmq_transaction_log` (
  `id` int NOT NULL AUTO_INCREMENT,
  `transaction_id` varchar(45) NOT NULL,
  `log` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rocketmq_transaction_log
-- ----------------------------
INSERT INTO `rocketmq_transaction_log` VALUES ('1', '7aa776ad-5c94-4387-83e3-6e67efba2b66', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('2', 'c43de2df-311e-4518-9d03-69546fbed490', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('3', '7a39f667-fd0e-40c9-a26f-e5ca11651b4e', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('4', 'ade07770-736a-4e93-8a6d-5fbb1a2f4f33', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('5', 'ee33b2b0-53c0-4283-8007-52ab69282a13', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('6', '455db7ca-57a4-4a62-8aa4-ce4586b25bd4', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('7', 'c685b887-40b9-4466-a229-fecd02045b7e', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('8', '1150dd35-916b-43c0-b057-aef865d12618', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('9', '5ac4ba0c-bdb1-4969-8340-63b68fc41490', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('10', '65e3fb2d-4a36-4caa-8200-678bdc6008d9', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('11', '3a3eafbf-cd3a-4d89-8d0e-c956a7612e6a', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('12', '8a74a1eb-4f00-434a-9480-2d6a7f63ae08', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('13', 'c511e8bb-8d2d-4188-adf9-bf213c94169e', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('14', 'c878486f-7a54-4000-96ec-dd1f6310af9b', '事件类型：分享审核');
INSERT INTO `rocketmq_transaction_log` VALUES ('15', 'c098ea94-22bf-4105-9f03-e137bc00ccc2', '事件类型：分享审核；审核时间：Mon Dec 07 23:10:27 CST 2020');

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `show_flag` tinyint(1) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice
-- ----------------------------

-- ----------------------------
-- Table structure for t_share
-- ----------------------------
DROP TABLE IF EXISTS `t_share`;
CREATE TABLE `t_share` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `author` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `summary` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `download_url` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `buy_count` int DEFAULT NULL,
  `is_original` tinyint DEFAULT NULL,
  `audit_status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `show_flag` tinyint DEFAULT NULL,
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_share
-- ----------------------------
INSERT INTO `t_share` VALUES ('1', '1', '标题-fd6e4d49-029f-4026-ba11-8eec97f68389', null, null, null, null, null, null, null, 'PASS', '升级后的测试。', null, '2020-12-07 23:10:25', '2020-12-07 23:10:25');
INSERT INTO `t_share` VALUES ('2', '1', '标题-4c89c11a-d1aa-4520-ade7-31ef6f26778f', null, null, null, null, null, null, null, 'NOT_YET', '', null, '2020-12-07 22:58:31', '2020-12-07 22:58:31');
INSERT INTO `t_share` VALUES ('4', '1', '标题-4d7fceae-6f9e-4801-91c9-c75dac6dfce1', null, null, null, null, null, null, null, 'NOT_YET', '', null, '2020-11-27 22:53:43', '2020-11-27 22:53:43');
INSERT INTO `t_share` VALUES ('5', '1', '标题-c39080f2-7a87-4298-a2a6-31689b7a8cbc', null, null, null, null, null, null, null, 'NOT_YET', '', null, '2020-11-27 22:53:43', '2020-11-27 22:53:43');
INSERT INTO `t_share` VALUES ('6', '1', '标题-87095826-e549-49b3-b057-f1f621056fbf', null, null, null, null, null, null, null, 'NOT_YET', '', null, '2020-11-27 22:53:43', '2020-11-27 22:53:43');
INSERT INTO `t_share` VALUES ('7', '1', '标题-a39e96eb-97b4-4f95-8c35-c3e10f6430cc', null, null, null, null, null, null, null, 'NOT_YET', '', null, '2020-11-27 22:53:43', '2020-11-27 22:53:43');
INSERT INTO `t_share` VALUES ('8', '1', '标题-49ed261b-3f38-46be-af83-e0123ab4cc69', null, null, null, null, null, null, null, 'NOT_YET', '', null, '2020-11-27 22:53:43', '2020-11-27 22:53:43');
INSERT INTO `t_share` VALUES ('9', '1', '标题-636deaa3-5bd5-4822-a21b-0f8f4670e20c', null, null, null, null, null, null, null, 'NOT_YET', '', null, '2020-11-27 22:53:43', '2020-11-27 22:53:43');
INSERT INTO `t_share` VALUES ('10', '1', '标题-e5ec2cd9-4651-4ede-9cab-0f9cc98ba001', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('11', '1', '标题-394f664b-803c-48fa-ad13-bc3ab98d456c', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('12', '1', '标题-43af4bce-9837-4ee3-a51a-81c8637f48b4', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('13', '1', '标题-2f750e70-2e4b-4850-a32f-e3c83ca0d7be', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('14', '1', '标题-3c43f5c1-a3af-4b5c-a46d-0e5b6bcd82c4', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('15', '1', '标题-ac7559a4-b5cc-40dd-94c0-b559cf938d87', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('16', '1', '标题-f66746a1-5b56-45fb-a2e1-bc67299d6b10', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('17', '1', '标题-23c2337d-3c0d-41ab-a854-3c1b9d3eda54', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('18', '1', '标题-7e739753-8d4e-4739-b245-ca3ad1c02923', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('19', '1', '标题-51fcc8b9-7b1a-4d7d-aea5-d333605bbe22', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('20', '1', '标题-b37ae179-a98f-418f-9d53-8eb95b92a55f', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');
INSERT INTO `t_share` VALUES ('21', '1', '标题-c3549e7f-8d06-4973-b859-c489e9568b12', null, null, null, null, null, null, null, 'NOT_YET', null, null, '2020-11-26 15:25:14', '2020-11-26 15:25:14');

-- ----------------------------
-- Table structure for t_user_share
-- ----------------------------
DROP TABLE IF EXISTS `t_user_share`;
CREATE TABLE `t_user_share` (
  `id` int NOT NULL AUTO_INCREMENT,
  `share_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_share
-- ----------------------------
