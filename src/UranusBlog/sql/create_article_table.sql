drop table if EXISTS `article`;

CREATE TABLE `article` (
  `article_id` int(11) NOT NULL AUTO_INCREMENT,
  `author` int(11) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` longtext NOT NULL,
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `post_time` datetime DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '1',
  `private` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`article_id`),
  KEY `author_idx` (`author`),
  CONSTRAINT `author` FOREIGN KEY (`author`) REFERENCES `account` (`uid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8