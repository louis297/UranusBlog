DROP PROCEDURE IF EXISTS GetUserInfo;

DELIMITER ##
CREATE PROCEDURE GetUserInfo(IN userId int(11))
  BEGIN
    SELECT a.uid, a.username, a.password, a.firstname, a.lastname, a.middlename, a.email,
      a.birthday, a.nation, a.avatar_path, a.role,
      replace(a.avatar_path, '.', '_thumbnail.') as thumbnail,
      nation.fullname, `role`.description
    FROM `account` a, nation, `role`
    WHERE uid = userId AND
          nation.abbreviation = a.nation AND
          `role`.role_id = a.role
    GROUP BY a.uid;
  END ##

DELIMITER ;