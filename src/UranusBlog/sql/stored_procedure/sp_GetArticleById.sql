DROP PROCEDURE IF EXISTS GetArticleById;

DELIMITER ##
CREATE PROCEDURE GetArticleById(IN userId INT(11), IN aid INT(11))
  BEGIN
    SELECT ar.*, a.username AS author_name FROM article ar, `account` a
    WHERE article_id = aid AND
          (
           (ar.private = 0 AND ar.post_time < DATE_ADD(now(), INTERVAL 18 HOUR) AND ar.active = 1) OR
           (ar.private = 1 AND ar.author = userId AND ar.active=1) OR
           (a.role = 1)
          )
    AND ar.author = a.uid
    GROUP BY `article_id`;
  END ##

DELIMITER ;