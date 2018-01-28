DROP PROCEDURE IF EXISTS GetUserNation;

DELIMITER ##
CREATE PROCEDURE GetUserNation(IN userId int(11))
  BEGIN
    SELECT fullname
    FROM nation
      join `account`
        on abbreviation = `account`.nation
    WHERE `account`.uid = userId;
  END ##

DELIMITER ;