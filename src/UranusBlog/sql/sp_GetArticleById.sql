DROP PROCEDURE IF EXISTS GetArticleById;

DELIMITER ##
CREATE PROCEDURE GetArticleById(in uid int(11), IN aid int(11))
  BEGIN
    SELECT * from article
    WHERE active = 1 and article_id = aid and (private = 0 or (private = 1 and author = uid));
  END ##

DELIMITER ;