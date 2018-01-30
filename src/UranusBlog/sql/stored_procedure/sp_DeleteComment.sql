DROP PROCEDURE IF EXISTS DeleteComment;

DELIMITER ##
CREATE PROCEDURE DeleteComment(IN cid int(11))
  BEGIN
    Update comment set active = 0
    where comment_id = cid;
  END ##

DELIMITER ;