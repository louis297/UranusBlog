DROP PROCEDURE IF EXISTS InsertAccount;

DELIMITER ##
CREATE PROCEDURE InsertAccount(
  IN uid int(11),
  IN username VARCHAR(45),
  IN password VARCHAR(45),
  IN firstname VARCHAR(45),
  IN lastname VARCHAR(45),
  IN middlename VARCHAR(45),
  IN email VARCHAR(45),
  IN birtyday DATE,
  IN nation VARCHAR(45),
  IN avatar_path VARCHAR(100),
  IN role int(11)
)

  BEGIN

    insert into `account`
    (uid, username, `password`, firstname, lastname, middlename, email, birtyday, nation, avatar_path, role)
      value (uid, username, password, firstname, lastname, middlename, email, birtyday, nation, avatar_path, role);
  END ##

DELIMITER ;