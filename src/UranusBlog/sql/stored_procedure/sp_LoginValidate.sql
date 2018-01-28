DROP PROCEDURE IF EXISTS LoginValidate;

DELIMITER ##
CREATE PROCEDURE LoginValidate(IN iUsername VARCHAR(45), IN iPassword VARCHAR(45))
  BEGIN
    SELECT a.uid, a.username, a.password, a.firstname, a.lastname, a.middlename, a.email,
      a.birthday, a.nation, a.avatar_path, a.role,
      replace(a.avatar_path, '.', '_thumbnail.') as thumbnail,
      nation.fullname, `role`.description
    FROM `account` a, nation, `role`
    WHERE a.username = iUsername AND a.password = iPassword AND a.nation = nation.abbreviation AND a.role = `role`.role_id;
  END ##

DELIMITER ;