DROP PROCEDURE IF EXISTS ResumeArticle;

DELIMITER ##
CREATE PROCEDURE ResumeArticle(IN aid int(11))
  BEGIN
    Update article set active = 1
    where article_id = aid;
  END ##

DELIMITER ;