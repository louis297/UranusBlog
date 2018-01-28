DROP PROCEDURE IF EXISTS IsAuthorValidate;

DELIMITER ##
CREATE PROCEDURE IsAuthorValidate(in userID int(11), IN articleID int(11))
  BEGIN
    SELECT count(*) from article
    WHERE article_id = articleID and (private = 0 or (private = 1 and author = userID));
  END ##

DELIMITER ;