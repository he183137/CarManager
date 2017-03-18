/*
Navicat SQLite Data Transfer

Source Server         : sqllite
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2017-03-10 22:28:12
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for t_conf
-- ----------------------------
DROP TABLE IF EXISTS "main"."t_send_state";
CREATE TABLE "t_send_state" (
"c_id"  INTEGER NOT NULL,
"c_phone"  TEXT(500) NOT NULL,
"c_message"  TEXT(5000) NOT NULL,
"c_state"  TEXT(500),
"c_create_date"  DATETIME NOT NULL,
"c_send_date"  DATETIME,
"c_info_id"  INTEGER,
"c_send_year"  TEXT(50),
PRIMARY KEY ("c_id" ASC)
);

-- ----------------------------
-- Records of t_conf
-- ----------------------------
