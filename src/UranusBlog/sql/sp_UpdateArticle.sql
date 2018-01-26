DROP PROCEDURE IF EXISTS UpdateArticle;

DELIMITER ##
CREATE PROCEDURE UpdateArticle(
  IN aid int(11),
  in title VARCHAR(100),
  in content longtext,
  in post_time DATETIME,
  in private tinyint(1)
)
  BEGIN
    update article
      set title = title,
        content = content,
        post_time = post_time,
        private = private
      where article_id = aid;
  END ##

DELIMITER ;