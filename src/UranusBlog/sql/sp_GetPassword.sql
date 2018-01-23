DROP PROCEDURE IF EXISTS GetPassword;

DELIMITER ##
CREATE PROCEDURE GetPassword(IN userId int(11))
  BEGIN
    SELECT password
    FROM `account`
    WHERE uid = userId;
  END ##

DELIMITER ;