/*
Navicat SQLite Data Transfer

Source Server         : sqllite
Source Server Version : 30808
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30808
File Encoding         : 65001

Date: 2017-02-12 22:28:12
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for t_conf
-- ----------------------------
DROP TABLE IF EXISTS "main"."t_conf";
CREATE TABLE "t_conf" (
"c_id"  INTEGER NOT NULL,
"c_key"  TEXT(500) NOT NULL,
"c_value"  TEXT(5000),
PRIMARY KEY ("c_id" ASC)
);

-- ----------------------------
-- Records of t_conf
-- ----------------------------
INSERT INTO "main"."t_conf" VALUES (1, 'countDown', 5);
INSERT INTO "main"."t_conf" VALUES (2, 'msgTemplete', '${user} 您好：\n\n 您的${carID}车险将要到期，到期时间为${InsuranceExTime},请及时办理。');
INSERT INTO "main"."t_conf" VALUES (3, 'port', 'COM3');
INSERT INTO "main"."t_conf" VALUES (4, 'baudRate', 9600);
INSERT INTO "main"."t_conf" VALUES (5, 'pinCode', '0000');
INSERT INTO "main"."t_conf" VALUES (6, 'manufacturer', 'wavecom');
INSERT INTO "main"."t_conf" VALUES (7, 'model', 'M1306B');
INSERT INTO "main"."t_conf" VALUES (8, 'testPhoneNo', 13012345678);
