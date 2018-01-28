DROP PROCEDURE IF EXISTS UpdateAccount;

DELIMITER ##
CREATE PROCEDURE UpdateAccount(
  IN userID int(11),
  IN new_username VARCHAR(45),
  IN new_firstname VARCHAR(45),
  IN new_lastname VARCHAR(45),
  IN new_middlename VARCHAR(45),
  IN new_email VARCHAR(45),
  IN new_birthday DATE,
  IN new_nation VARCHAR(45),
  IN new_avatar_path VARCHAR(100)
)

  BEGIN

    update `account`
      set username = new_username,
        firstname = new_firstname,
        lastname = new_lastname,
        middlename = new_middlename,
        email = new_email,
        birthday = new_birthday,
        nation = new_nation,
        avatar_path = new_avatar_path
      WHERE uid = userID;

  END ##

DELIMITER ;