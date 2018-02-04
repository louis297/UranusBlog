DROP PROCEDURE IF EXISTS DeleteAccount;

DELIMITER ##
CREATE PROCEDURE DeleteAccount(IN userId int(11))
  BEGIN
    DELETE FROM `account` WHERE uid = userId;
    DELETE FROM `article` WHERE author = userId;
    DELETE FROM `comment` WHERE author = userId;
  END ##

DELIMITER ;