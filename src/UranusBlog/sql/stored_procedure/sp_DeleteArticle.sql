DROP PROCEDURE IF EXISTS DeleteArticle;

DELIMITER ##
CREATE PROCEDURE DeleteArticle(IN aid int(11))
  BEGIN
    Update article set active = 0
      where article_id = aid;
  END ##

DELIMITER ;