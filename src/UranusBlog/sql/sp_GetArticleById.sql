DROP PROCEDURE IF EXISTS GetArticleById;

DELIMITER ##
CREATE PROCEDURE GetArticleById(IN userId INT(11), IN aid INT(11))
  BEGIN
    SELECT article.*, `account`.username AS author_name FROM article, `account`
    WHERE active = 1 AND article_id = aid AND ((private = 0 AND post_time < current_time) OR (private = 1 AND author = userId))
    AND author = `account`.uid
    GROUP BY `article_id`;
  END ##

DELIMITER ;