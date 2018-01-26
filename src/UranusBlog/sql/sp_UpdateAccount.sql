DROP PROCEDURE IF EXISTS UpdateAccount;

DELIMITER ##
CREATE PROCEDURE UpdateAccount(
  IN uid int(11),
  IN username VARCHAR(45),
  IN firstname VARCHAR(45),
  IN lastname VARCHAR(45),
  IN middlename VARCHAR(45),
  IN email VARCHAR(45),
  IN birtyday DATE,
  IN nation VARCHAR(45),
  IN avatar_path VARCHAR(100)
)

  BEGIN

    update `account`
      set username = username,
        firstname = firstname,
        lastname = lastname,
        middlename = middlename,
        email = email,
        birtyday = birtyday,
        nation = nation,
        avatar_path = avatar_path
      WHERE uid = uid;

  END ##

DELIMITER ;