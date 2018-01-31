DROP PROCEDURE IF EXISTS InsertArticle;

DELIMITER ##
CREATE PROCEDURE InsertArticle(
  IN new_author INT(11),
  IN new_title VARCHAR(100),
  IN new_content LONGTEXT,
  IN new_post_time DATETIME,
  IN new_private TINYINT(1))

  BEGIN

  INSERT INTO article
    (author, title, content, post_time, private)
      VALUE (new_author, new_title, new_content, new_post_time, new_private);
  SELECT LAST_INSERT_ID();
  END ##

DELIMITER ;