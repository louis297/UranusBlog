DROP PROCEDURE IF EXISTS DeleteAccount;

DELIMITER ##
CREATE PROCEDURE DeleteAccount(IN userId int(11))
  BEGIN
    DELETE FROM `account` WHERE uid = userId;
  END ##

DELIMITER ;