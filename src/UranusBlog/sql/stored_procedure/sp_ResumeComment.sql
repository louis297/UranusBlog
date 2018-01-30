DROP PROCEDURE IF EXISTS ResumeComment;

DELIMITER ##
CREATE PROCEDURE ResumeComment(IN cid int(11))
  BEGIN
    Update comment set active = 1
    where comment_id = cid;
  END ##

DELIMITER ;