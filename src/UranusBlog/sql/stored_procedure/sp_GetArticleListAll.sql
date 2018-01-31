DROP PROCEDURE IF EXISTS GetArticleListAll;

DELIMITER ##
CREATE PROCEDURE GetArticleListAll(in userId int(11), IN startNum int(11), IN amount int(11))
  BEGIN
    SELECT `article`.*, `account`.username as author_name from article, `account`
    WHERE active = 1 and
          (
            (private = 0 and post_time < DATE_ADD(now(), INTERVAL 18 HOUR)) or
            (private = 1 and author = userId))
          and `article`.author = `account`.uid
    GROUP BY article_id
    ORDER BY `article`.post_time desc
    limit startNum, amount ;
  END ##

DELIMITER ;