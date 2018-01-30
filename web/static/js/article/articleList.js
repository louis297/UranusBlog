function getArticleList(own) {
    var params = {own: own=='true'};
    $.ajax({
        url: 'api/articleList',
        method: 'get',
        data: params,
        dataType: 'json',
        success: function(ret) {
            parsedData = [];
            console.log(ret);
            if(ret.result != 'fail') {
                updateArticles(ret);
                lock = false;
            } else {
                // todo: provide link to create blog if list is empty
                $('#articleList').html('<h1>No articles found.</h1>');
                lock = false;
            }
        },
        error: function(ret){
            console.log('getArticleList ajax fail')
            console.log(ret)
        }
    });
}

function updateArticles(articleList){
    $('#articleList').html("");
    var i=0;
    articleList.forEach(function(article){
        var ad = $('<div id="article' + i + '" class="articleBox"></div>')
        ad.append('<div class="articleTitle" id="articleTitle' + i +'">' +
            '<a href="api/article.html?aid='+ article.article_id +'">' + article.title + '</a>' +
            '</div>');
        ad.append('<div id="authorName"' + i + '>' +
            article.author_name +
            '</div>')
        ad.append('<div id="created_time"' + i + '>' +
            article.created_time +
            '</div>')
        ad.append('<div id="modified_time"' + i + '>' +
            article.modified_time +
            '</div>')
        ad.append('<div class="articleContent" id="articleContent' + i +'">' +
            article.content +
            '</div>');

        i++;
        $('#articleList').append(ad)
    });
}