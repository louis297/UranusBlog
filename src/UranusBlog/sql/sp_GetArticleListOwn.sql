DROP PROCEDURE IF EXISTS GetArticleListOwn;

DELIMITER ##
CREATE PROCEDURE GetArticleListOwn(IN userId int(11), IN startNum int(11), IN amount int(11))
  BEGIN
    SELECT * from article
    WHERE active = 1 and author = userId
      ORDER BY post_time desc
    limit startNum, amount ;
  END ##

DELIMITER ;