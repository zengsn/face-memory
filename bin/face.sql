-- 数据库名：face

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_app
-- ----------------------------
DROP TABLE IF EXISTS `t_app`;
CREATE TABLE `t_app` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `app_key` varchar(40) NOT NULL,
  `app_secret` varchar(40) NOT NULL,
  `app_desc` varchar(255) DEFAULT NULL COMMENT 'app描述',
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_app_key` (`app_key`) USING BTREE,
  KEY `fk_user_id` (`user_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_app
-- ----------------------------
INSERT INTO `t_app` VALUES ('2', '4344f36a-f3b2-4bdb-9cea-b250e6ca1a73', 'fd844b7e-2974-4daf-be59-73fbb078d488', '111', '4', '2017-09-01 02:02:28', null);
INSERT INTO `t_app` VALUES ('3', '39d7c69e-a56b-4670-ab34-90347735144a', '9a9b0889-fafd-4f10-ad5f-ba77c139839c', '111', '4', '2017-09-01 02:06:02', null);
INSERT INTO `t_app` VALUES ('4', 'e75b570a-1ab5-4ccd-b937-aba65a4bd33c', '4141d5be-29b9-441f-bd6c-9292e9c8e497', '222', '4', '2017-09-01 02:28:17', null);
INSERT INTO `t_app` VALUES ('7', 'ffe087a1-b86f-43fa-93dc-75094509476f', 'ed2ca67d-8f26-426f-bddc-a72129f38be4', '666', '4', '2017-09-01 04:08:33', null);
INSERT INTO `t_app` VALUES ('8', '4a9a8bcc-61ef-4ab4-ad7d-4e06d55cc65d', 'ab15f101-519b-448d-b01f-cefc523c376f', '77', '4', '2017-09-01 04:08:42', null);
INSERT INTO `t_app` VALUES ('9', '94f0879c-17aa-463f-8b1f-ddc843c839df', '13b149ec-2e84-4290-a132-98b41d82d0ef', '888', '4', '2017-09-01 04:08:50', null);
INSERT INTO `t_app` VALUES ('10', '955cdf6a-d836-4e71-be65-f5877c31ff0e', '95256ba2-6aff-412b-bb73-7eb7b360991c', '999', '4', '2017-09-01 04:26:59', null);

-- ----------------------------
-- Table structure for t_person
-- ----------------------------
DROP TABLE IF EXISTS `t_person`;
CREATE TABLE `t_person` (
  `ID` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `person_code` varchar(40) NOT NULL,
  `app_id` bigint(20) unsigned NOT NULL,
  `name` varchar(30) DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_person_code` (`person_code`) USING BTREE,
  KEY `fk_app_id` (`app_id`),
  CONSTRAINT `fk_app_id` FOREIGN KEY (`app_id`) REFERENCES `t_app` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_person
-- ----------------------------
INSERT INTO `t_person` VALUES ('1', '29cb5075-2593-4f19-a979-a0d7ca1dc447', '2', '黄拒接', '客户1', '2017-09-05 15:53:14', null);
INSERT INTO `t_person` VALUES ('2', '20b75375-6a24-40a1-9704-0088176d9ec8', '2', '孙悟空', '女娲补天留下的一颗神石', '2017-09-10 21:38:33', null);
INSERT INTO `t_person` VALUES ('4', '5cf65ba4-1904-48bf-801a-bdc46f246e04', '2', 'App', 'face Unlock', '2017-09-11 06:19:16', null);
INSERT INTO `t_person` VALUES ('5', '04f0db38-047d-4d66-9932-b0aa1be74f7e', '2', '1345', 'fsdfsdf', '2017-09-11 06:25:15', null);
INSERT INTO `t_person` VALUES ('6', 'af2d469d-67d6-41a5-99f6-6cbaed5c2854', '2', '1345', 'fsdfsdf', '2017-09-11 07:02:01', null);

-- ----------------------------
-- Table structure for t_photo
-- ----------------------------
DROP TABLE IF EXISTS `t_photo`;
CREATE TABLE `t_photo` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `person_id` bigint(20) unsigned DEFAULT NULL,
  `suffix_name` varchar(255) NOT NULL,
  `gmt_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `person_id` (`person_id`),
  CONSTRAINT `t_photo_ibfk_1` FOREIGN KEY (`person_id`) REFERENCES `t_person` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_photo
-- ----------------------------
INSERT INTO `t_photo` VALUES ('10', '1', 'png', '2017-09-06 03:53:24', null);
INSERT INTO `t_photo` VALUES ('11', '1', 'png', '2017-09-06 03:53:48', null);
INSERT INTO `t_photo` VALUES ('19', '4', 'jpg', '2017-09-11 08:00:58', null);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(60) NOT NULL,
  `email` varchar(40) NOT NULL,
  `code` varchar(40) DEFAULT NULL,
  `enabled` tinyint(3) unsigned NOT NULL,
  `gmt_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`) USING BTREE,
  UNIQUE KEY `uk_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('4', '13669564119', '$2a$10$0mWaJ7PJcNvDhSVmF8UrSOHpiQF1hf3wVT.zMYMJfdmA59MO2cOAK', '13692787683@163.com', 'dd101446-e963-4f7e-8f84-fb18bbabe6e2', '1', '2017-08-19 16:22:38', '2017-08-19 16:22:59');

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL,
  `role` varchar(45) NOT NULL,
  `gmt_create` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_role__user_id` (`role`,`user_id`) USING BTREE,
  KEY `fk_t_user_role__t_user` (`user_id`),
  CONSTRAINT `fk_t_user_role__t_user` FOREIGN KEY (`user_id`) REFERENCES `t_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES ('2', '4', 'ROLE_USER', '2017-08-19 16:22:38', null);
SET FOREIGN_KEY_CHECKS=1;
