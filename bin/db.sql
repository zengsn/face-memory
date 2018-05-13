/*
Navicat MySQL Data Transfer

Source Server         : 腾讯云_MySQL
Source Server Version : 50717
Source Host           : 119.29.152.182:3306
Source Database       : face

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-05-13 12:18:41
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for persistent_logins
-- ----------------------------
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
`username`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`series`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`token`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`last_used`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`series`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Records of persistent_logins
-- ----------------------------
BEGIN;
INSERT INTO `persistent_logins` VALUES ('13669564119', 'V4JHjOy8rEyEegldHWcb3Q==', 'bCK+h/lnLVkwVCs6E2LVeQ==', '2018-05-12 10:43:25');
COMMIT;

-- ----------------------------
-- Table structure for t_app
-- ----------------------------
DROP TABLE IF EXISTS `t_app`;
CREATE TABLE `t_app` (
`id`  bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT ,
`app_key`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`app_secret`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`app_desc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'app描述' ,
`user_id`  bigint(20) UNSIGNED NULL DEFAULT NULL ,
`gmt_create`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
`gmt_modified`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`),
FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
UNIQUE INDEX `uk_app_key` (`app_key`) USING BTREE ,
INDEX `fk_user_id` (`user_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=10

;

-- ----------------------------
-- Records of t_app
-- ----------------------------
BEGIN;
INSERT INTO `t_app` VALUES ('2', '4344f36a-f3b2-4bdb-9cea-b250e6ca1a73', 'fd844b7e-2974-4daf-be59-73fbb078d488', '我的人脸识别演示', '4', '2017-09-01 02:02:28', '2018-05-11 08:58:40'), ('3', '39d7c69e-a56b-4670-ab34-90347735144a', '9a9b0889-fafd-4f10-ad5f-ba77c139839c', '111', '4', '2017-09-01 02:06:02', null), ('4', 'e75b570a-1ab5-4ccd-b937-aba65a4bd33c', '4141d5be-29b9-441f-bd6c-9292e9c8e497', '222', '4', '2017-09-01 02:28:17', null), ('7', 'ffe087a1-b86f-43fa-93dc-75094509476f', 'ed2ca67d-8f26-426f-bddc-a72129f38be4', '666', '4', '2017-09-01 04:08:33', null), ('8', '4a9a8bcc-61ef-4ab4-ad7d-4e06d55cc65d', 'ab15f101-519b-448d-b01f-cefc523c376f', '77', '4', '2017-09-01 04:08:42', null), ('9', '94f0879c-17aa-463f-8b1f-ddc843c839df', '13b149ec-2e84-4290-a132-98b41d82d0ef', '888', '4', '2017-09-01 04:08:50', null);
COMMIT;

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
`id`  bigint(11) NOT NULL AUTO_INCREMENT ,
`group_name`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`max_num`  int(11) NULL DEFAULT NULL ,
`app_key`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`gmt_create`  date NULL DEFAULT NULL ,
`gmt_modified`  date NULL DEFAULT NULL ,
`user_id`  bigint(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`app_key`) REFERENCES `t_app` (`app_key`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `appkey` (`app_key`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=7

;

-- ----------------------------
-- Records of t_group
-- ----------------------------
BEGIN;
INSERT INTO `t_group` VALUES ('5', '分组1', '100', '4344f36a-f3b2-4bdb-9cea-b250e6ca1a73', '2018-04-30', '2018-04-30', '4'), ('6', '分组2', '100', '4344f36a-f3b2-4bdb-9cea-b250e6ca1a73', '2018-05-10', null, '4');
COMMIT;

-- ----------------------------
-- Table structure for t_log
-- ----------------------------
DROP TABLE IF EXISTS `t_log`;
CREATE TABLE `t_log` (
`ID`  int(11) NOT NULL AUTO_INCREMENT ,
`time`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
`message`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`user`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`operation`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`ip`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`params`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`gmt_create`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
`gmt_modified`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`ID`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=25

;

-- ----------------------------
-- Records of t_log
-- ----------------------------
BEGIN;
INSERT INTO `t_log` VALUES ('6', '2018-05-11 07:31:33', '登录成功', '13669564119', 'login', '0:0:0:0:0:0:0:1', '', '2018-05-11 07:31:33', null), ('7', '2018-05-11 07:38:31', '登录成功', '13669564119', 'login', '0:0:0:0:0:0:0:1', '', '2018-05-11 07:38:31', null), ('8', '2018-05-11 07:41:35', '登录成功', '13669564119', 'login', '0:0:0:0:0:0:0:1', '', '2018-05-11 07:41:35', null), ('9', '2018-05-11 07:41:51', '退出成功', '13669564119', 'logout', '0:0:0:0:0:0:0:1', '', '2018-05-11 07:41:51', null), ('10', '2018-05-11 07:47:08', '登录成功', 'admin', 'login', '0:0:0:0:0:0:0:1', '', '2018-05-11 07:47:08', null), ('11', '2018-05-11 07:50:27', '登录成功', 'admin', 'login', '0:0:0:0:0:0:0:1', '', '2018-05-11 07:50:27', null), ('12', '2018-05-11 08:01:44', '登录成功', 'admin', 'login', '0:0:0:0:0:0:0:1', '', '2018-05-11 08:01:44', null), ('13', '2018-05-11 08:02:05', '退出成功', null, 'logout', '0:0:0:0:0:0:0:1', '', '2018-05-11 08:02:05', null), ('14', '2018-05-11 08:02:15', '登录成功', '13669564119', 'login', '0:0:0:0:0:0:0:1', '', '2018-05-11 08:02:15', null), ('15', '2018-05-11 08:02:26', '退出成功', null, 'logout', '0:0:0:0:0:0:0:1', '', '2018-05-11 08:02:26', null), ('16', '2018-05-11 08:02:36', '登录成功', 'admin', 'login', '0:0:0:0:0:0:0:1', '', '2018-05-11 08:02:36', null), ('17', '2018-05-11 08:39:58', '退出成功', null, 'logout', '0:0:0:0:0:0:0:1', '', '2018-05-11 08:39:58', null), ('18', '2018-05-11 08:41:01', '登录成功', '13669564119', 'login', '0:0:0:0:0:0:0:1', '', '2018-05-11 08:41:01', null), ('19', '2018-05-11 08:57:41', '退出成功', null, 'logout', '0:0:0:0:0:0:0:1', '', '2018-05-11 08:57:41', null), ('20', '2018-05-11 08:58:00', '登录成功', '13669564119', 'login', '0:0:0:0:0:0:0:1', '', '2018-05-11 08:58:00', null), ('21', '2018-05-11 20:18:26', '登录成功', '13669564119', 'login', '113.81.106.187', '', '2018-05-11 20:18:26', null), ('22', '2018-05-11 21:33:52', '登录成功', '13669564119', 'login', '113.81.106.187', '', '2018-05-11 21:33:52', null), ('23', '2018-05-12 10:43:00', '退出成功', null, 'logout', '223.104.65.21', '', '2018-05-12 10:43:00', null), ('24', '2018-05-12 10:43:25', '登录成功', '13669564119', 'login', '223.104.65.21', '', '2018-05-12 10:43:25', null);
COMMIT;

-- ----------------------------
-- Table structure for t_person
-- ----------------------------
DROP TABLE IF EXISTS `t_person`;
CREATE TABLE `t_person` (
`id`  bigint(11) NOT NULL AUTO_INCREMENT ,
`identification`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`name`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`birth`  date NULL DEFAULT NULL ,
`phone`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`identity`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`group_id`  bigint(11) NULL DEFAULT NULL ,
`gmt_create`  datetime NULL DEFAULT NULL ,
`gmt_modified`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
`user_id`  bigint(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`group_id`) REFERENCES `t_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `group_id` (`group_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=8

;

-- ----------------------------
-- Records of t_person
-- ----------------------------
BEGIN;
INSERT INTO `t_person` VALUES ('1', '008772b9-116e-46b9-985e-be53514d39b8', '刘昊然', '2019-02-19', '', '', '5', '2018-04-30 21:52:12', '2018-05-07 19:03:36', '4'), ('3', '024e83c7-2927-4f20-919a-985d87120b32', '姚明', null, '', '', '6', '2018-05-10 16:55:05', null, '4'), ('4', '3f4aed17-b2ff-4369-82ce-d94dbefe90a1', '易建联', null, '', '', '6', '2018-05-11 03:46:11', null, '4'), ('5', 'cd6d7102-7e72-40e3-be51-81e127d17cfe', '张国荣', null, '', '', '6', '2018-05-11 04:18:57', null, '4'), ('6', 'f28819b5-3c4c-441b-88c7-a591d685e8f1', '奥巴马', null, '', '', '6', '2018-05-11 04:21:27', null, '4'), ('7', '3c93cb80-cdb2-4c78-9742-79146eb6f831', '特朗普', null, '', '', '6', '2018-05-11 04:27:25', null, '4');
COMMIT;

-- ----------------------------
-- Table structure for t_photo
-- ----------------------------
DROP TABLE IF EXISTS `t_photo`;
CREATE TABLE `t_photo` (
`id`  bigint(11) NOT NULL AUTO_INCREMENT ,
`person_id`  bigint(11) NULL DEFAULT NULL ,
`path`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`suffix`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`gmt_modified`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
`gmt_create`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
`user_id`  bigint(11) NULL DEFAULT NULL ,
PRIMARY KEY (`id`),
FOREIGN KEY (`person_id`) REFERENCES `t_person` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
INDEX `person_id` (`person_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=61

;

-- ----------------------------
-- Records of t_photo
-- ----------------------------
BEGIN;
INSERT INTO `t_photo` VALUES ('49', '1', 'cca4b336-b9b5-4cb6-acb3-45c82f86dddd', '.jpg', null, '2018-05-03 01:09:05', '4'), ('52', '1', 'a290dd6b-182f-40cf-8357-1a4bc3fecfbd', '.jpg', null, '2018-05-07 19:16:14', '4'), ('54', '4', 'bd8707da-5092-4695-8ee8-d58081e00d81', '.jpg', null, '2018-05-11 03:48:01', '4'), ('55', '5', '1eaf351d-b6f0-4948-af40-2f107de06fd3', '.jpg', null, '2018-05-11 04:20:55', '4'), ('56', '6', '6cd83c59-a40c-4694-b881-a02f818dddfc', '.jpg', null, '2018-05-11 04:21:53', '4'), ('57', '3', '24d3f4be-2b0f-4e08-ae22-7ae78c70f808', '.jpg', null, '2018-05-11 04:25:27', '4'), ('58', '7', 'dd6ee5b2-a6b4-4900-9d1e-258a68b9d041', '.jpg', null, '2018-05-11 04:27:35', '4');
COMMIT;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
`id`  bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT ,
`username`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`password`  varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`email`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`code`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`enabled`  tinyint(3) UNSIGNED NOT NULL ,
`gmt_create`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
`gmt_modified`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`),
UNIQUE INDEX `uk_username` (`username`) USING BTREE ,
UNIQUE INDEX `uk_email` (`email`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=5

;

-- ----------------------------
-- Records of t_user
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1', 'admin', '$2a$10$0mWaJ7PJcNvDhSVmF8UrSOHpiQF1hf3wVT.zMYMJfdmA59MO2cOAK', 'admin@it138.top', null, '1', '2018-05-11 04:56:54', '2018-05-11 04:56:54'), ('4', '13669564119', '$2a$10$6LfH5ExskgdlWz5m4UtpZu9cMHMkZa/pXYLSVZvJD.V67tzKDXzgu', '13692787683@163.com', 'dd101446-e963-4f7e-8f84-fb18bbabe6e2', '1', '2017-08-19 16:22:38', '2018-05-11 08:56:02');
COMMIT;

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`user_id`  bigint(20) UNSIGNED NOT NULL ,
`role`  varchar(45) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`gmt_create`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
`gmt_modified`  datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`id`),
FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
UNIQUE INDEX `uk_role__user_id` (`role`, `user_id`) USING BTREE ,
INDEX `fk_t_user_role__t_user` (`user_id`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=4

;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
BEGIN;
INSERT INTO `t_user_role` VALUES ('2', '4', 'ROLE_USER', '2017-08-19 16:22:38', null), ('3', '1', 'ROLE_ADMIN', '2017-08-19 16:22:38', null);
COMMIT;

-- ----------------------------
-- Auto increment value for t_app
-- ----------------------------
ALTER TABLE `t_app` AUTO_INCREMENT=10;

-- ----------------------------
-- Auto increment value for t_group
-- ----------------------------
ALTER TABLE `t_group` AUTO_INCREMENT=7;

-- ----------------------------
-- Auto increment value for t_log
-- ----------------------------
ALTER TABLE `t_log` AUTO_INCREMENT=25;

-- ----------------------------
-- Auto increment value for t_person
-- ----------------------------
ALTER TABLE `t_person` AUTO_INCREMENT=8;

-- ----------------------------
-- Auto increment value for t_photo
-- ----------------------------
ALTER TABLE `t_photo` AUTO_INCREMENT=61;

-- ----------------------------
-- Auto increment value for t_user
-- ----------------------------
ALTER TABLE `t_user` AUTO_INCREMENT=5;

-- ----------------------------
-- Auto increment value for t_user_role
-- ----------------------------
ALTER TABLE `t_user_role` AUTO_INCREMENT=4;
