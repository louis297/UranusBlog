DROP PROCEDURE IF EXISTS GetUserRole;

DELIMITER //
CREATE PROCEDURE GetUserRole(IN userId int(11))
  BEGIN
    SELECT description
    FROM role
    join `account`
      on role_id = `account`.role
    WHERE `account`.uid = userId;
  END //
DELIMITER ;