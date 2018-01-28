DROP PROCEDURE IF EXISTS GetUserList;

DELIMITER ##
CREATE PROCEDURE GetUserList()
  BEGIN
    SELECT a.uid, a.username, a.password, a.firstname, a.lastname, a.middlename, a.email,
      a.birthday, a.nation, a.avatar_path, a.role,
      replace(a.avatar_path, '.', '_thumbnail.') as thumbnail,
      nation.fullname, `role`.description
    FROM `account` a, nation, `role`
    WHERE nation.abbreviation = a.nation AND
          `role`.role_id = a.role
    GROUP BY a.uid
    ORDER BY a.uid;
  END ##

DELIMITER ;