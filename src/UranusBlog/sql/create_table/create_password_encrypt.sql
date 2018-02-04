drop TABLE IF EXISTS `password_encrypt`;

CREATE TABLE `password_encrypt` (
  `uid` int(11) NOT NULL,
  `salt` BLOB NOT NULL ,
  `iters` int(11) NOT NULL,
  PRIMARY KEY (`uid`),
  CONSTRAINT `uid_password_encrypt` FOREIGN KEY (`uid`) REFERENCES `account` (`uid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;