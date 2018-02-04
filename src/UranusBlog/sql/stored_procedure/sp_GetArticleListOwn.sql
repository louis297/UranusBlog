DROP PROCEDURE IF EXISTS GetArticleListOwn;

DELIMITER ##
CREATE PROCEDURE GetArticleListOwn(IN userId int(11), IN startNum int(11), IN amount int(11))
  BEGIN
    SELECT `article`.*, `account`.username as author_name from article, `account`
    WHERE active = 1 and author = userId and `account`.uid = userId
      GROUP BY `article`.article_id
      ORDER BY post_time desc
    -- limit startNum, amount
    ;
  END ##

DELIMITER ;