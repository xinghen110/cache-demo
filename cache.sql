-- ----------------------------
-- Table structure for t_limited
-- ----------------------------
DROP TABLE IF EXISTS `t_limited`;
CREATE TABLE `t_limited` (
  `symbol` varchar(64) NOT NULL,
  `symbolName` varchar(64) NOT NULL,
  `up` varchar(64),
  `one` varchar(64),
  `date` varchar(64),
  `temp` varchar(64),
  PRIMARY KEY (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;