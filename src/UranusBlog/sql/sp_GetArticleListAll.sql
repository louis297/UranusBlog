DROP PROCEDURE IF EXISTS GetArticleListAll;

DELIMITER ##
CREATE PROCEDURE GetArticleListAll(in uid int(11), IN startNum int(11), IN amount int(11))
  BEGIN
    SELECT * from article
    WHERE active = 1 and post_time < current_time and (private = 0 or (private = 1 and author = uid))
    limit startNum, amount ;
  END ##

DELIMITER ;