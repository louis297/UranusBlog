DROP PROCEDURE IF EXISTS GetAvatarThumbnail;

DELIMITER ##
CREATE PROCEDURE GetAvatarThumbnail(IN userId int(11))
  BEGIN
    SELECT replace(avatar_path, '.', '_thumbnail.') as thumbnail
    FROM `account`
    WHERE `account`.uid = userId;
  END ##

DELIMITER ;