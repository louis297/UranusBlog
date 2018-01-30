DROP PROCEDURE IF EXISTS GetCommentList;

DELIMITER ##
CREATE PROCEDURE GetCommentList(IN articleID int(11), IN userID int(11))
  BEGIN
    SELECT c.content, c.created_time, a.username, a.avatar_path, c.active FROM comment c, `account` a, `article` ar
    WHERE c.active = 1 AND c.article_id = articleID AND a.uid = c.author AND
          ((ar.author = userID) OR (ar.private = 0 AND ar.post_time <= current_time) OR a.role=1)
    GROUP BY c.comment_id
    ORDER BY c.created_time desc;
  END ##

DELIMITER ;