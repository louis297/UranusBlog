DROP PROCEDURE IF EXISTS InsertArticle;

DELIMITER ##
CREATE PROCEDURE InsertArticle(
  IN new_author int(11),
  IN new_title VARCHAR(100),
  IN new_content longtext,
  IN new_post_time DATETIME,
  IN new_private tinyint(1))

  BEGIN

  insert into article
    (author, title, content, post_time, private)
      value (new_author, new_title, new_content, new_post_time, new_private);
  END ##

DELIMITER ;