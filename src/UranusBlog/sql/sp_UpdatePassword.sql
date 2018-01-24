DROP PROCEDURE IF EXISTS UpdatePassword;

DELIMITER ##
CREATE PROCEDURE UpdatePassword(
  IN uid int(11),
  IN password VARCHAR(45)
)

  BEGIN

    update `account`
    set `password` = password
    WHERE uid = uid;

  END ##

DELIMITER ;