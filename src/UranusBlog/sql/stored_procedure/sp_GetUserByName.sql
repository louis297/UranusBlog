DROP PROCEDURE IF EXISTS GetUserByName;

DELIMITER ##
CREATE PROCEDURE GetUserByName(IN in_username varchar(45))
  BEGIN
    SELECT a.uid, a.username, a.password, a.firstname, a.lastname, a.middlename, a.email,
      a.birthday, a.nation, a.avatar_path, a.role,
      replace(a.avatar_path, '.', '_thumbnail.') as thumbnail,
      nation.fullname, `role`.description, a.salt, a.iters, a.description
    FROM `account` a, nation, `role`
    WHERE username = in_username AND
          nation.abbreviation = a.nation AND
          `role`.role_id = a.role
    GROUP BY a.uid;
  END ##

DELIMITER ;