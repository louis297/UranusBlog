DROP PROCEDURE IF EXISTS InsertArticle;

DELIMITER ##
CREATE PROCEDURE InsertArticle(
  IN author int(11),
  IN title VARCHAR(100),
  IN content longtext,
  IN post_time DATETIME,
  IN private tinyint(1))

  BEGIN

  insert into article
    (author, title, content, post_time, private)
      value (author, title, content, post_time, private);
  END ##

DELIMITER ;