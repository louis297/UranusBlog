DROP PROCEDURE IF EXISTS GetCommentById;

DELIMITER ##
CREATE PROCEDURE GetCommentById(IN cid INT(11))
  BEGIN
    SELECT c.comment_id ,c.content, c.created_time, c.active, a.uid, a.username, a.avatar_path, c.article_id
    FROM comment c, `account` a
    WHERE c.comment_id = cid AND c.author = a.uid
    GROUP BY c.comment_id;
  END ##

DELIMITER ;