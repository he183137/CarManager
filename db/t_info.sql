/*
Navicat SQLite Data Transfer

Source Server         : sqllite
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2017-02-12 22:28:22
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for t_info
-- ----------------------------
DROP TABLE IF EXISTS "main"."t_info";
CREATE TABLE "t_info" (
"c_id"  TEXT(1000) NOT NULL,
"c_name"  TEXT(1000) NOT NULL,
"c_identification_card"  TEXT(100) NOT NULL,
"c_address"  TEXT(2000) NOT NULL,
"c_phone"  TEXT(100) NOT NULL,
"c_Insurance_expirationTime"  TEXT(200) NOT NULL,
"c_car_id"  TEXT(200) NOT NULL,
"c_Inspection_expirationTime"  TEXT(200),
PRIMARY KEY ("c_id" ASC)
);


