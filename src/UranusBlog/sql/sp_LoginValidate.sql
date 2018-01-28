DROP PROCEDURE IF EXISTS LoginValidate;

DELIMITER ##
CREATE PROCEDURE LoginValidate(IN Username VARCHAR(45), IN Password VARCHAR(45))
  BEGIN
    SELECT count(*) from `account`
    WHERE username = Username and password = Password;
  END ##

DELIMITER ;