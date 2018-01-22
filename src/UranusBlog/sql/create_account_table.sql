drop table if EXISTS `account`;

CREATE TABLE `account` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `lastname` varchar(45) NOT NULL,
  `middlename` varchar(100) DEFAULT NULL,
  `email` varchar(70) NOT NULL,
  `birthday` date NOT NULL,
  `nation` varchar(45) NOT NULL,
  `avatar_path` varchar(100) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `nation_idx` (`nation`),
  KEY `role_idx` (`role`),
  CONSTRAINT `nation` FOREIGN KEY (`nation`) REFERENCES `nation` (`abbreviation`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `role` FOREIGN KEY (`role`) REFERENCES `role` (`role_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8