DROP PROCEDURE IF EXISTS UpdateArticle;

DELIMITER ##
CREATE PROCEDURE UpdateArticle(
  IN aid int(11),
  in new_title VARCHAR(100),
  in new_content longtext,
  in new_post_time DATETIME,
  in new_private tinyint(1)
)
  BEGIN
    update article
      set title = new_title,
        content = new_content,
        post_time = new_post_time,
        private = new_private
      where article_id = aid;
    SELECT aid;
  END ##

DELIMITER ;