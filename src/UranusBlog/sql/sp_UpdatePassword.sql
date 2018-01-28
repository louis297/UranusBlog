DROP PROCEDURE IF EXISTS UpdatePassword;

DELIMITER ##
CREATE PROCEDURE UpdatePassword(
  IN userID int(11),
  IN new_password VARCHAR(45)
)

  BEGIN

    update `account`
    set `password` = new_password
    WHERE uid = userID;

  END ##

DELIMITER ;