DROP PROCEDURE IF EXISTS InsertComment;

DELIMITER ##
CREATE PROCEDURE InsertComment(
  IN new_author int(11),
  IN new_article_id int(11),
  IN new_content longtext
)

  BEGIN

    insert into comment
    (author, article_id, content)
      value (new_author, new_article_id, new_content);
  END ##

DELIMITER ;