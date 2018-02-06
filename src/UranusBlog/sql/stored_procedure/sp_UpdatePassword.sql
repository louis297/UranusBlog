DROP PROCEDURE IF EXISTS UpdatePassword;

DELIMITER ##
CREATE PROCEDURE UpdatePassword(
  IN userID int(11),
  IN new_password BLOB,
  IN new_salt BLOB,
  IN new_iters int(11)
)

  BEGIN

    update `account`
    set `password` = new_password,
      iters = new_iters,
      salt = new_salt
    WHERE uid = userID;

  END ##

DELIMITER ;