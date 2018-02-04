DROP PROCEDURE IF EXISTS InsertAccount;

DELIMITER ##
CREATE PROCEDURE InsertAccount(
  IN new_username VARCHAR(45),
  IN new_password BLOB,
  IN new_firstname VARCHAR(45),
  IN new_lastname VARCHAR(45),
  IN new_middlename VARCHAR(45),
  IN new_email VARCHAR(45),
  IN new_birthday DATE,
  IN new_nation VARCHAR(45),
  IN new_avatar_path VARCHAR(200),
  IN new_role int(11),
  IN new_salt BLOB,
  IN new_iters int(11)
)

  BEGIN

    insert into `account`
    (username, `password`, firstname, lastname, middlename, email, birthday, nation, avatar_path, role, salt, iters)
      value (new_username, new_password, new_firstname, new_lastname, new_middlename, new_email, new_birthday, new_nation, new_avatar_path, new_role, new_salt, new_iters);
  END ##

DELIMITER ;