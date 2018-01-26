DROP PROCEDURE IF EXISTS IsAuthorValidate;

DELIMITER ##
CREATE PROCEDURE IsAuthorValidate(in uid int(11), IN aid int(11))
  BEGIN
    SELECT count(*) from article
    WHERE article_id = aid and (private = 0 or (private = 1 and author = uid));
  END ##

DELIMITER ;