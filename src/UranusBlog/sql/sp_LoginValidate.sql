DROP PROCEDURE IF EXISTS LoginValidate;

DELIMITER ##
CREATE PROCEDURE LoginValidate(IN username VARCHAR(45), IN password VARCHAR(45))
  BEGIN
    SELECT count(*) from `account`
    WHERE username = username and password = password;
  END ##

DELIMITER ;