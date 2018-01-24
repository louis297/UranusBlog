DROP PROCEDURE IF EXISTS GetArticleListOwn;

DELIMITER ##
CREATE PROCEDURE GetArticleListOwn(IN uid int(11), IN startNum int(11), IN amount int(11))
  BEGIN
    SELECT * from article
    WHERE active = 1 and author = uid and post_time < current_time
    limit startNum, amount ;
  END ##

DELIMITER ;